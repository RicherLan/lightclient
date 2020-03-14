package lan.qxc.lightclient.ui.activity.user_activitys;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.NumberUtil;
import lan.qxc.lightclient.util.SharePerferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisSubmitActivity extends BaseForCloseActivity implements View.OnClickListener {

    private EditText nickname_et;
    private EditText password_et;
    private Button submit_bt;
    private ImageView back_iv;

    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_submit);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        if(phone==null||!NumberUtil.isPhone(phone)){
            Toast.makeText(RegisSubmitActivity.this,"error!!!",Toast.LENGTH_SHORT).show();
            finish();
        }

        initVIew();
        initEvent();


    }

    void initVIew(){
        nickname_et = this.findViewById(R.id.et_regis_submit_nickname);
        password_et = this.findViewById(R.id.et_regis_submit_password);
        submit_bt = this.findViewById(R.id.bt_regis_submit);
        back_iv = this.findViewById(R.id.iv_register_submit_back_lable);

    }

    void initEvent(){
        back_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_submit_back_lable:
                this.finish();
                break;
            case R.id.bt_regis_submit:
                doSubmit();
                break;

        }
    }

    void doSubmit(){

         String username = nickname_et.getText().toString();
         String password = password_et.getText().toString();

        if(username==null||username.trim().isEmpty()){
            Toast.makeText(RegisSubmitActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(password==null||password.trim().isEmpty()){
            Toast.makeText(RegisSubmitActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return ;
        }


        username = username.trim();
        password = password.trim();

        Call<Result> call = UserService.getInstance().regis_user(phone,username,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                String message = result.getMessage();

                if (message.equals("SUCCESS")) {
                    Toast.makeText(RegisSubmitActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisSubmitActivity.this, LoginActivity.class);
                    startActivity(intent);
                    RegisSubmitActivity.this.finish();


                } else {
                    Toast.makeText(RegisSubmitActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(RegisSubmitActivity.this,"error!!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
