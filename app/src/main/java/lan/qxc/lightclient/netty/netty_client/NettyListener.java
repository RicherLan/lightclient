package lan.qxc.lightclient.netty.netty_client;


public interface NettyListener {

    public final static byte STATUS_CONNECT_SUCCESS = 1;

    public final static byte STATUS_CONNECT_CLOSED = 0;

    public final static byte STATUS_CONNECT_ERROR = 0;


    /**
     * 当接收到系统消息
     */
//    void onMessageResponse(ByteBuf byteBuf);

    /**
     * 当服务状态发生变化时触发
     */
    void onNettyServiceStatusConnectChanged(int statusCode);
}
