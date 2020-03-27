package lan.qxc.lightclient.netty.protocol.response.netResponse;


import lan.qxc.lightclient.netty.protocol.Packet;

import static lan.qxc.lightclient.netty.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public int getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
