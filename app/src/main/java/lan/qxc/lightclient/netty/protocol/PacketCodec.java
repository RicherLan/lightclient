package lan.qxc.lightclient.netty.protocol;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import lan.qxc.lightclient.netty.protocol.packet.friend_msg_packet.FriendMsgPacket;
import lan.qxc.lightclient.netty.protocol.request.netRequest.HeartBeatRequestPacket;
import lan.qxc.lightclient.netty.protocol.request.user_request.LoginRequestPacket;
import lan.qxc.lightclient.netty.protocol.response.netResponse.HeartBeatResponsePacket;
import lan.qxc.lightclient.netty.protocol.response.user_response.LoginResponsePacket;
import lan.qxc.lightclient.netty.serialize.Serializer;
import lan.qxc.lightclient.netty.serialize.impl.JSONSerializer;

import static lan.qxc.lightclient.netty.protocol.command.Command.Friend_GUANZHU_MSG;
import static lan.qxc.lightclient.netty.protocol.command.Command.HEARTBEAT_REQUEST;
import static lan.qxc.lightclient.netty.protocol.command.Command.HEARTBEAT_RESPONSE;
import static lan.qxc.lightclient.netty.protocol.command.Command.LOGIN_REQUEST;
import static lan.qxc.lightclient.netty.protocol.command.Command.LOGIN_RESPONSE;


public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Integer, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        packetTypeMap.put(Friend_GUANZHU_MSG, FriendMsgPacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {

        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
//        String a = new String(bytes,0,bytes.length);
//        System.out.println("encode  "+a);
        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeInt(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        int command = byteBuf.readInt();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
//        String a = new String(bytes,0,bytes.length);
//        System.out.println("decode  "+a);
        
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {

           return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(int command) {

        return packetTypeMap.get(command);
    }
}
