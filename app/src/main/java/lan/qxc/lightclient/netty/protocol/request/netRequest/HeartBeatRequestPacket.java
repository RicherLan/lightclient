package lan.qxc.lightclient.netty.protocol.request.netRequest;


import lan.qxc.lightclient.netty.protocol.Packet;


import static lan.qxc.lightclient.netty.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
	public HeartBeatRequestPacket() {

	}

	@Override
	public int getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
