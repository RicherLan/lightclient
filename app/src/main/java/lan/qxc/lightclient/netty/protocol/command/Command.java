package lan.qxc.lightclient.netty.protocol.command;

public interface Command {

    //心跳
    int HEARTBEAT_REQUEST = 1;
    int HEARTBEAT_RESPONSE = 2;

}
