package lan.qxc.lightclient.netty.handler;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.netty.handler.user_handler.FriendMsgResponseHandler;
import lan.qxc.lightclient.netty.handler.user_handler.LoginResponseHandler;
import lan.qxc.lightclient.netty.handler.user_handler.SingleChatMsgHandler;
import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.netty.protocol.Packet;

import static lan.qxc.lightclient.netty.protocol.command.Command.Friend_GUANZHU_MSG;
import static lan.qxc.lightclient.netty.protocol.command.Command.LOGIN_RESPONSE;
import static lan.qxc.lightclient.netty.protocol.command.Command.SINGLE_CHAT_MSG_PACKET;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    private Map<Integer, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    public IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(LOGIN_RESPONSE, new LoginResponseHandler());
        handlerMap.put(Friend_GUANZHU_MSG, new FriendMsgResponseHandler());

        handlerMap.put(SINGLE_CHAT_MSG_PACKET, new SingleChatMsgHandler());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyClient.getInstance().setConnectStatus(false);
    }

}
