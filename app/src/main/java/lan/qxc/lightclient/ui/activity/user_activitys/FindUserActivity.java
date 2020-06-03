package lan.qxc.lightclient.ui.activity.user_activitys;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.service.service_callback.UserInfoExecutor;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.NumberUtil;

public class FindUserActivity extends BaseForCloseActivity implements View.OnClickListener {

    ImageView iv_back_find_user;
    LinearLayout layout_search_find_user;
    Button bt_search_find_user;
    EditText et_search_find_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        initView();
        initEvent();

    }

    void initView(){
        iv_back_find_user = this.findViewById(R.id.iv_back_find_user);
        layout_search_find_user = this.findViewById(R.id.layout_search_find_user);
        bt_search_find_user = this.findViewById(R.id.bt_search_find_user);
        et_search_find_user = this.findViewById(R.id.et_search_find_user);
    }

    void initEvent(){
        iv_back_find_user.setOnClickListener(this::onClick);
        bt_search_find_user.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_find_user:
                finish();
            case R.id.bt_search_find_user:
                search();
                break;
        }
    }

    void search(){
        String account = et_search_find_user.getText().toString();
        if(account.isEmpty()){
            return;
        }

        if(!NumberUtil.isNumber(account)){
            Toast.makeText(this,"请正确输入格式！",Toast.LENGTH_SHORT).show();
            return;
        }


            Long userid = Long.parseLong(account);

        if(userid.equals(GlobalInfoUtil.personalInfo.getUserid())){
            Intent intent = new Intent(FindUserActivity.this,PersonalActivity.class);
            startActivity(intent);
            return;
        }

            UserInfoExecutor.getInstance().getUserDetailInfo(this, userid, new UserInfoExecutor.UserListener() {
                @Override
                public void getResult(String message) {
                    if(message.equals("SUCCESS")){

                        Intent intent = new Intent(FindUserActivity.this,UserDetailInfoActivity.class);
                        intent.putExtra("userid",userid);
                        startActivity(intent);

                    }else{
                        Toast.makeText(FindUserActivity.this,"该用户不存在!",Toast.LENGTH_SHORT).show();
                    }
                }
            });





    }

}
