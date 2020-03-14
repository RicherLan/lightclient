package lan.qxc.lightclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import lan.qxc.lightclient.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIUtils.makeStatusBarTransparent(this);
    }
}
