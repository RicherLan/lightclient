package lan.qxc.lightclient.netty.netty_client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lan.qxc.lightclient.netty.codec.PacketCodecHandler;
import lan.qxc.lightclient.netty.codec.Spliter;
import lan.qxc.lightclient.netty.handler.IMHandler;
import lan.qxc.lightclient.netty.handler.netHandler.HeartBeatResponseHandler;
import lan.qxc.lightclient.netty.handler.netHandler.HeartBeatTimerHandler;
import lan.qxc.lightclient.netty.handler.netHandler.IMIdleStateHandler;
import lan.qxc.lightclient.retrofit_util.api.BaseAPI;

public class NettyClient {

    private static NioEventLoopGroup workerGroup;
    private static Bootstrap bootstrap;
    public static Channel channel;

    private int MAX_RETRY = Integer.MAX_VALUE;

    private NettyListener listener = null;

    private boolean isConnected = false;   //是否已经连接
    private boolean isConnecting = false;   //是否正在尝试与服务器连接中

    private static class NettyClientHolder{
        private static  NettyClient instance = new NettyClient();
    }

    public static NettyClient getInstance(){
        return NettyClientHolder.instance;
    }


    private NettyClient(){
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {

                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketCodecHandler());

                        ch.pipeline().addLast(new HeartBeatResponseHandler());
                        //业务处理器
                        ch.pipeline().addLast(new IMHandler());
                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });
    }

    public boolean isInitOK(){
        if(listener!=null){
            return true;
        }
        return false;
    }

    public void connect() {
        System.out.println("netty  connect 操作......");
        if(isConnected||isConnecting){
            return;
        }

        connect(bootstrap, BaseAPI.nettyServerIP, BaseAPI.nettyServerPort, MAX_RETRY);
    }

    private void connect(Bootstrap bootstrap, String host, int port, int retry) {

        if(isConnected||isConnecting){
            return;
        }
        isConnecting = true;
        System.out.println("链接中……");
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                isConnecting = false;
                //System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                channel = ((ChannelFuture) future).channel();
                // startConsoleThread(channel);
                setConnectStatus(true);
                listener.onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);


            } else if (retry == 0) {
                isConnecting = false;
                //  System.err.println("重试次数已用完，放弃连接！");
                setConnectStatus(false);
                listener.onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
            } else {
                isConnecting = false;
                setConnectStatus(false);
                listener.onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                if(order>=5){
                    delay=15;
                }
                //  System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    public void disconnect() {
        workerGroup.shutdownGracefully();
        setConnectStatus(false);
        listener.onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
    }

    public boolean getConnectStatus(){
        return isConnected;
    }
    public void setConnectStatus(boolean status){
        this.isConnected = status;
    }

    public void setReconnectNum(int reconnectNum) {
        this.MAX_RETRY = reconnectNum;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }

    public NettyListener getListener() {
        return listener;
    }

    public Channel getChannel() {
        return channel;
    }


}
