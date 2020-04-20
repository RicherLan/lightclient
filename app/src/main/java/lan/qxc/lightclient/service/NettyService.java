package lan.qxc.lightclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.netty.command_to_server.SendToServer;
import lan.qxc.lightclient.netty.config.NettyConfig;
import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.netty.netty_client.NettyListener;
import lan.qxc.lightclient.util.GlobalInfoUtil;

public class NettyService extends Service implements NettyListener {

    public static NettyService nettyService;

    public static int connetstatuscode=0;

    private static Timer loginTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nettyService = this;
        NettyClient.getInstance().setListener(this);
        connect();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onNettyServiceStatusConnectChanged(int statusCode) {
        connetstatuscode = statusCode;
        //连接成功
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {

           // 重新登陆   每隔9秒重新登陆   直到登陆成功
            if(NettyConfig.isLogined){
                if(loginTimer!=null){
                    loginTimer.cancel();
                }
                loginTimer = new Timer();
                loginTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SendToServer.login(GlobalInfoUtil.personalInfo.getPhone(),GlobalInfoUtil.personalInfo.getPassword());
                    }
                },1000,NettyConfig.loginIntervalTime);

            }


            //断线
        } else {                     //停止登陆
            if(loginTimer!=null){
                loginTimer.cancel();
            }
        }
    }


    public  void connect(){
        if(!NettyClient.getInstance().isInitOK()){
            NettyClient.getInstance().setListener(this);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyClient.getInstance().connect();//连接服务器

            }
        }).start();

    }

    public  static  void disconnect(){
        if (NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().disconnect();//连接服务器
                }
            }).start();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
        if(loginTimer!=null){
            loginTimer.cancel();
        }
    }

    public void sendCast(Intent intent){
        sendBroadcast(intent);
    }


}
