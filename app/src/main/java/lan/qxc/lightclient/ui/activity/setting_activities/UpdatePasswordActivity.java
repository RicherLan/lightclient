package lan.qxc.lightclient.ui.activity.setting_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.base_activitys.ActivityCollector;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.LoginActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.SharePerferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends BaseForCloseActivity implements View.OnClickListener {

    private ImageView iv_back_update_password;
    private EditText et_oldpass_uppass;
    private EditText et_newpass_uppass;
    private EditText et_newpass2_uppass;

    private TextView tv_submit_uppass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initView();
        initEvent();
    }

    private void initView(){
        iv_back_update_password = this.findViewById(R.id.iv_back_update_password);
        et_oldpass_uppass = this.findViewById(R.id.et_oldpass_uppass);
        et_newpass_uppass = this.findViewById(R.id.et_newpass_uppass);
        et_newpass2_uppass = this.findViewById(R.id.et_newpass2_uppass);
        tv_submit_uppass = this.findViewById(R.id.tv_submit_uppass);

    }

    private void initEvent(){
        iv_back_update_password.setOnClickListener(this);
        tv_submit_uppass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back_update_password:
                finish();
                break;

            case R.id.tv_submit_uppass:
                doUpdatePass();
                break;
        }

    }

    private void doUpdatePass(){

        String oldpass = et_oldpass_uppass.getText().toString();
        if(oldpass.isEmpty()||oldpass.trim().isEmpty()){
            Toast.makeText(this,"请正确填写旧密码",Toast.LENGTH_SHORT).show();
            return ;
        }
        String newpass = et_newpass_uppass.getText().toString();
        if(newpass.isEmpty()||newpass.trim().isEmpty()){
            Toast.makeText(this,"请正确填写新密码",Toast.LENGTH_SHORT).show();
            return ;
        }

        String newpass2 = et_newpass2_uppass.getText().toString();
        if(newpass2.isEmpty()||newpass2.trim().isEmpty()){
            Toast.makeText(this,"请再次填写新密码",Toast.LENGTH_SHORT).show();
            return ;
        }

        oldpass = oldpass.trim();
        newpass = newpass.trim();
        newpass2 = newpass2.trim();


        if(!newpass.equals(newpass2)){
            Toast.makeText(this,"两次新密码填写不一致!",Toast.LENGTH_SHORT).show();
            return ;
        }

        final String newpassword = newpass;

        Call<Result> call = UserService.getInstance().updateUserPassword(GlobalInfoUtil.personalInfo.getUserid(),
                oldpass,newpass);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    Toast.makeText(UpdatePasswordActivity.this,"修改成功,请重新登录",Toast.LENGTH_SHORT).show();

                    GlobalInfoUtil.personalInfo.setPassword(newpassword);

                    Map<String ,String > map = new HashMap<>();
                    map.put(SharePerferenceUtil.sh_login_password,"");
                    map.put(SharePerferenceUtil.sh_kip_login,"N");
                    map.put(SharePerferenceUtil.sh_personal_info, JsonUtils.objToJson(GlobalInfoUtil.personalInfo));

                    SharePerferenceUtil.save_User_SP(UpdatePasswordActivity.this,map);

                    GlobalInfoUtil.personalInfo = null;
                    ActivityCollector.finishAll();
                    Intent intent=new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(UpdatePasswordActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(UpdatePasswordActivity.this,"error!!!",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
