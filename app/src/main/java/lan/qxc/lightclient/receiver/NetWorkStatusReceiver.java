package lan.qxc.lightclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.service.NettyService;

/*
    监听网络变化
 */
public class NetWorkStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {   //恢复链接
                    if(!NettyClient.getInstance().getConnectStatus()){
                        if(NettyService.nettyService!=null){
                            NettyService.nettyService.connect();
                        }

                    }
                }
            }
        }
    }
}
