package lan.qxc.lightclient.ui.activity.user_activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.util.NumberUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPhoneActivity extends BaseForCloseActivity implements View.OnClickListener {

    private EditText phone_et;
    private Button submit_button;
    private ImageView back_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        initView();
        initEvent();
    }

    void initView(){
        phone_et = this.findViewById(R.id.et_regis_phone);

        submit_button = this.findViewById(R.id.bt_regis_next);
        back_iv = this.findViewById(R.id.iv_register_back_lable);
    }

    void initEvent(){
        back_iv.setOnClickListener(this);
        submit_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_back_lable:
                this.finish();
                break;
            case R.id.bt_regis_next:
                doSubmitPhone();
                break;
                default:
                    break;
        }
    }


    void doSubmitPhone(){

        final String phone = phone_et.getText().toString();

        if(phone==null||phone.trim().isEmpty()){
            Toast.makeText(RegisterPhoneActivity.this,"请输入电话号码",Toast.LENGTH_SHORT).show();
            return ;
        }

        if(!NumberUtil.isPhone(phone)){
            Toast.makeText(RegisterPhoneActivity.this,"请输入正确格式的电话号码",Toast.LENGTH_SHORT).show();
            return ;
        }


        Call<Result> call = UserService.getInstance().regis_isPhoneExist(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    Intent intent  = new Intent(RegisterPhoneActivity.this,RegisSubmitActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);

                }else{
                    Toast.makeText(RegisterPhoneActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(RegisterPhoneActivity.this,"error!!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
