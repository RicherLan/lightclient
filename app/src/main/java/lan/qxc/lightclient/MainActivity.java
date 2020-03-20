package lan.qxc.lightclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import lan.qxc.lightclient.util.UIUtils;

public class MainActivity extends AppCompatActivity {


    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };

        setContentView(R.layout.activity_main);
        UIUtils.makeStatusBarTransparent(this);
    }
}
