package lan.qxc.lightclient.ui.activity.base_activitys;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.receiver.NetWorkStatusReceiver;
import lan.qxc.lightclient.util.MyDialog;
import lan.qxc.lightclient.util.UIUtils;

public class BaseForCloseActivity extends AppCompatActivity {

    NetWorkStatusReceiver netWorkStatusReceiver;
    private Timer timer;
    private int seconds = 28000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIUtils.makeStatusBarTransparent(this);
        UIUtils.setStatusBarLightMode(this,true);

        ActivityCollector.addActivity(this);
    }

    public void setSeconds(int seconds){
        this.seconds = seconds;
    }

    public void startLoadingDialog(){
        MyDialog.dismissBottomLoadingDialog();
        MyDialog.showBottomLoadingDialog(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                BaseForCloseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyDialog.dismissBottomLoadingDialog();
                        Toast.makeText( BaseForCloseActivity.this,"发生错误,请稍后重试!",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        },seconds);
    }

    public void cancelLoadingDialog(){
        if(timer!=null){
            timer.cancel();
        }
        MyDialog.dismissBottomLoadingDialog();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (netWorkStatusReceiver == null) {
            netWorkStatusReceiver = new NetWorkStatusReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStatusReceiver, filter);
    }

    protected void onDestroy(){
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
        ActivityCollector.removeActivity(this);
        if(netWorkStatusReceiver!=null){
            unregisterReceiver(netWorkStatusReceiver);
        }

    }
}
