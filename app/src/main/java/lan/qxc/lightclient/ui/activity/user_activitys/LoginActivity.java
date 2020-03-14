package lan.qxc.lightclient.ui.activity.user_activitys;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.MainActivity;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.https.BaseCallBack;
import lan.qxc.lightclient.https.BaseOkHttpClient;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.MyDialog;
import lan.qxc.lightclient.util.NumberUtil;
import lan.qxc.lightclient.util.SharePerferenceUtil;
import lan.qxc.lightclient.util.UIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
   登录
 */
public class LoginActivity extends BaseForCloseActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private CircleImageView headImage;
    private EditText ed_user_account;
    private EditText ed_user_password;
    private TextView tv_login;

    private TextView tv_signuser;
    private TextView tv_forget_pass;

    private VideoView mVideoView;
    private RelativeLayout progressLay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIUtils.makeStatusBarTransparent(this);
       // UIUtils.setStatusBarLightMode(this,false);
        initView();
        initVideoView();
        initEvent();

        String phone = SharePerferenceUtil.getUserFromSP(this,SharePerferenceUtil.sh_login_username);
        String password = SharePerferenceUtil.getUserFromSP(this,SharePerferenceUtil.sh_login_password);

        if(phone!=null&&!phone.isEmpty()){
            ed_user_account.setText(phone);
        }
        if(password!=null&&!password.isEmpty()){
            ed_user_password.setText(password);
        }

        String userinfo_json = SharePerferenceUtil.getUserFromSP(this,SharePerferenceUtil.sh_user_info);
        GlobalInfoUtil.personalInfo = (User)JsonUtils.jsonToObj(User.class,userinfo_json);
    }

    void initView(){
        headImage = this.findViewById(R.id.user_image_login);

        ed_user_account = this.findViewById(R.id.ed_user_account);
        ed_user_password = this.findViewById(R.id.et_user_passsword);
        tv_login = this.findViewById(R.id.tv_login);

        tv_signuser = this.findViewById(R.id.tv_login_signin);
        tv_forget_pass = this.findViewById(R.id.tv_login_forgetPassword);

        mVideoView = this.findViewById(R.id.video_login_view);
        progressLay=(RelativeLayout)findViewById(R.id.login_progress_lay);
    }

    void initEvent(){
        tv_login.setOnClickListener(this);
        tv_signuser.setOnClickListener(this);
        tv_forget_pass.setOnClickListener(this);
    }

    void initVideoView(){
        //设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.login_back_video));
        //设置相关的监听
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //开始播放    循环
        mVideoView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //开始播放
        mVideoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                doLogin();
                break;
            case R.id.tv_login_signin:
                Intent intent = new Intent(LoginActivity.this,RegisterPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_forgetPassword:
                break;
                default:
                    break;
        }
    }


    void doLogin(){

        final String phone = ed_user_account.getText().toString();
        final String password = ed_user_password.getText().toString();

        if(phone==null||phone.trim().isEmpty()){
            Toast.makeText(LoginActivity.this,"请输入电话号码",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(password==null||password.trim().isEmpty()){
            Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return ;
        }

        if(!NumberUtil.isPhone(phone)){
            Toast.makeText(LoginActivity.this,"请输入正确格式的电话号码",Toast.LENGTH_SHORT).show();
            return ;
        }

        setButtonLogining();

        Call<Result> call = UserService.getInstance().login(phone,password);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                resetLoginButton();

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    User user = (User)JsonUtils.jsonToObj(User.class,jsonstr);
                    System.out.println((user.toString()));

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                    user.setPassword(password);
                    Map<String ,String > map = new HashMap<>();
                    map.put(SharePerferenceUtil.sh_login_username,phone);
                    map.put(SharePerferenceUtil.sh_login_password,password);
                    map.put(SharePerferenceUtil.sh_kip_login,"Y");
                    map.put(SharePerferenceUtil.sh_user_info,JsonUtils.objToJson(user));

                    SharePerferenceUtil.save_User_SP(LoginActivity.this,map);

                    LoginActivity.this.finish();

                }else{
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

                    Map<String ,String > map = new HashMap<>();
                    map.put(SharePerferenceUtil.sh_login_username,phone);
                    map.put(SharePerferenceUtil.sh_login_password,"");
                    map.put(SharePerferenceUtil.sh_kip_login,"N");

                    SharePerferenceUtil.save_User_SP(LoginActivity.this,map);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                resetLoginButton();
                Toast.makeText(LoginActivity.this,"error!!!",Toast.LENGTH_SHORT).show();
            }
        });


    }


    Timer loginTimer;

    //点击登录  登录按钮旋转
    void setButtonLogining(){
        if(loginTimer!=null){
            loginTimer.cancel();
        }
        loginTimer = new Timer();
        loginTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetLoginButton();
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },15000);
        progressLay.setVisibility(View.VISIBLE);
        tv_login.setVisibility(View.GONE);
    }

    void resetLoginButton(){
        if(loginTimer!=null){
            loginTimer.cancel();
        }
        progressLay.setVisibility(View.GONE);
        tv_login.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(loginTimer!=null){
            loginTimer.cancel();
        }

    }



}
