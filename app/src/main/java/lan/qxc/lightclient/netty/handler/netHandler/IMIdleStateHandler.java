package lan.qxc.lightclient.netty.handler.netHandler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.netty.netty_client.NettyListener;

public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 150;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        //System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");

        ctx.channel().close();
        NettyClient.getInstance().getListener().onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyClient.getInstance().getListener().onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
    }

}
