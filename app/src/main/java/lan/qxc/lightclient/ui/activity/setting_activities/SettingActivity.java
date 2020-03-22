package lan.qxc.lightclient.ui.activity.setting_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.ui.activity.base_activitys.ActivityCollector;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.LoginActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;

public class SettingActivity extends BaseForCloseActivity implements View.OnClickListener {

    private ImageView iv_back_setting;
    private LinearLayout layout_uppass_setting;
    private LinearLayout layout_logout_setting;
    private LinearLayout layout_exitapp_setting;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
    }

    private void initView(){
          iv_back_setting = this.findViewById(R.id.iv_back_setting);
          layout_uppass_setting = this.findViewById(R.id.layout_uppass_setting);
          layout_logout_setting = this.findViewById(R.id.layout_logout_setting);
          layout_exitapp_setting = this.findViewById(R.id.layout_exitapp_setting);
    }

    private void initEvent(){
        iv_back_setting.setOnClickListener(this);
        layout_uppass_setting.setOnClickListener(this);
        layout_logout_setting.setOnClickListener(this);
        layout_exitapp_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_setting:
                finish();
                break;

            case R.id.layout_uppass_setting:
                Intent intent2=new Intent(this, UpdatePasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_logout_setting:
                GlobalInfoUtil.personalInfo = null;
                ActivityCollector.finishAll();
                Intent intent=new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_exitapp_setting:
                ActivityCollector.finishAll();
                break;
        }
    }
}
