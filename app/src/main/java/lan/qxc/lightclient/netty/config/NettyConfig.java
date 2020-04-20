package lan.qxc.lightclient.netty.config;

public class NettyConfig {

    //是否登陆   掉线重连时用到
    public static boolean isLogined = false;

    //掉线重连后  请求登陆的间隔
    public static int loginIntervalTime = 9000;

}
