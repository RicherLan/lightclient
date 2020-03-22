package lan.qxc.lightclient.ui.activity.user_activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.util.DensityUtiltwo;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.MyTimeUtil;
import lan.qxc.lightclient.util.SharePerferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePersonalInfoActivity extends BaseForCloseActivity implements View.OnClickListener {

    private ImageView iv_back_up_personal_activity;
    private TextView tv_save_up_personal_activity;

    private EditText et_username_up_personal_activity;
    private TextView tv_sex_up_personal_activity;
    private EditText et_hometown_up_personal_activity;
    private EditText et_location_up_personal_activity;
    private EditText et_job_up_personal_activity;
    private TextView tv_birth_up_personal_activity;
    private TextView et_star_up_personal_activity;
    private EditText et_introduce_up_personal_activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personalinfo);
        initView();
        initEvent();
    }

    void initView(){
          iv_back_up_personal_activity = this.findViewById(R.id.iv_back_up_personal_activity);
          tv_save_up_personal_activity = this.findViewById(R.id.tv_save_up_personal_activity);

          et_username_up_personal_activity = this.findViewById(R.id.et_username_up_personal_activity);
          tv_sex_up_personal_activity = this.findViewById(R.id.tv_sex_up_personal_activity);
          et_hometown_up_personal_activity = this.findViewById(R.id.et_hometown_up_personal_activity);
          et_location_up_personal_activity = this.findViewById(R.id.et_location_up_personal_activity);
          et_job_up_personal_activity = this.findViewById(R.id.et_job_up_personal_activity);
          tv_birth_up_personal_activity = this.findViewById(R.id.tv_birth_up_personal_activity);
          et_star_up_personal_activity = this.findViewById(R.id.et_star_up_personal_activity);
          et_introduce_up_personal_activity = this.findViewById(R.id.et_introduce_up_personal_activity);


        if(GlobalInfoUtil.personalInfo!=null){
            et_username_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getUsername());
                if(GlobalInfoUtil.personalInfo.getSex()==2){
                    tv_sex_up_personal_activity.setText("女");
                }else {
                    tv_sex_up_personal_activity.setText("男");
                }
            }

        et_hometown_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getHometown());
        et_job_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getJob());
        et_location_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getLocation());



            if(GlobalInfoUtil.personalInfo.getBirthday()!=null){

                tv_birth_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getBirthday());
                et_star_up_personal_activity.setText(MyTimeUtil.getStarByTime(GlobalInfoUtil.personalInfo.getBirthday()));
            }

        et_introduce_up_personal_activity.setText(GlobalInfoUtil.personalInfo.getIntroduce());

    }

    void initEvent(){
        iv_back_up_personal_activity .setOnClickListener(this);
        tv_save_up_personal_activity.setOnClickListener(this);
        tv_birth_up_personal_activity.setOnClickListener(this);
        tv_sex_up_personal_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_up_personal_activity:
                finish();
                break;
            case R.id.tv_save_up_personal_activity:
                updateInfo();
                break;
            case R.id.tv_birth_up_personal_activity:
                showChooseBirthDialog();
                break;
            case R.id.tv_sex_up_personal_activity:
                showSelectSexDialog();
                break;
        }
    }


    private void updateInfo(){


        final User user = (User)JsonUtils.jsonToObj(User.class,JsonUtils.objToJson(GlobalInfoUtil.personalInfo));
        user.setPassword(null);

        tv_sex_up_personal_activity = this.findViewById(R.id.tv_sex_up_personal_activity);



        String username = et_username_up_personal_activity.getText().toString();
        user.setUsername(username);
        if(!username.isEmpty()&&!username.trim().isEmpty()){
            user.setUsername(username.trim());
        }

        String sexStr = tv_sex_up_personal_activity.getText().toString();
        if(!sexStr.isEmpty()&&!sexStr.trim().isEmpty()){
            if(sexStr.equals("男")){
                user.setSex(new Byte("1"));
            }else{
                user.setSex(new Byte("2"));
            }

        }

        String hometown = et_hometown_up_personal_activity.getText().toString();
        user.setHometown(hometown);
        if(!hometown.isEmpty()&&!hometown.trim().isEmpty()){
            user.setHometown(hometown);
        }

        String location = et_location_up_personal_activity.getText().toString();
        user.setLocation(location);
        if(!location.isEmpty()&&!location.trim().isEmpty()){
            user.setLocation(location);
        }

        String job = et_job_up_personal_activity.getText().toString();
        user.setJob(job);
        if(!job.isEmpty()&&!job.trim().isEmpty()){
            user.setJob(job);
        }

        String birthstr = tv_birth_up_personal_activity.getText().toString();
        user.setBirthday(birthstr);
        if(!birthstr.isEmpty()&&!birthstr.trim().isEmpty()){

            user.setBirthday(birthstr);
        }
        String introduce = et_introduce_up_personal_activity.getText().toString();
        user.setIntroduce(introduce);
        if(!introduce.isEmpty()&&!introduce.trim().isEmpty()){
            user.setIntroduce(introduce);
        }

//        System.out.println(user.toString());

        startLoadingDialog();

        Call<Result> call = UserService.getInstance().udpateUserInfo(user);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                cancelLoadingDialog();
                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    Toast.makeText(UpdatePersonalInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    GlobalInfoUtil.personalInfo = (User)JsonUtils.jsonToObj(User.class,jsonstr);

                    Map<String ,String > map = new HashMap<>();
                    map.put(SharePerferenceUtil.sh_personal_info,JsonUtils.objToJson(GlobalInfoUtil.personalInfo));
                    SharePerferenceUtil.save_User_SP(UpdatePersonalInfoActivity.this,map);

                    finish();

                }else{
                    Toast.makeText(UpdatePersonalInfoActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                cancelLoadingDialog();
                Toast.makeText(UpdatePersonalInfoActivity.this,"发生错误，请稍后再试!",Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void showChooseBirthDialog(){
        Calendar ca = Calendar.getInstance();
        final int  mYear = ca.get(Calendar.YEAR);
        final int  mMonth = ca.get(Calendar.MONTH);
        final int  mDay = ca.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(year>mYear||year==mYear&&(month)>mMonth||year==mYear&&(month)==mMonth&&dayOfMonth>mDay){

                            Toast.makeText(UpdatePersonalInfoActivity.this,"请正确选择日期",Toast.LENGTH_SHORT).show();
                        }else{
                            tv_birth_up_personal_activity.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                            String starStr = MyTimeUtil.getStarByTime(year+"-"+(month+1)+"-"+dayOfMonth);
                            et_star_up_personal_activity.setText(starStr);
                        }


                    }
                },
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showSelectSexDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_select_sex, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView man=(TextView) contentView.findViewById(R.id.select_man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sex_up_personal_activity.setText("男");
                bottomDialog.dismiss();
            }
        });
        TextView woman=(TextView) contentView.findViewById(R.id.select_woman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sex_up_personal_activity.setText("女");
                bottomDialog.dismiss();
            }
        });
    }

}
