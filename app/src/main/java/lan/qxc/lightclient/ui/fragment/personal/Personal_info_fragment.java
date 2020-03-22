package lan.qxc.lightclient.ui.fragment.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.ui.activity.user_activitys.PersonalActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.UpdatePersonalInfoActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.MyTimeUtil;

public class Personal_info_fragment extends Fragment implements View.OnClickListener {
    View view;

    private TextView tv_per_frag_userid;
    private TextView tv_per_frag_phone;
    private TextView tv_per_frag_username;
    private ImageView image_per_frag_sex;
    private TextView tv_per_frag_hometown;
    private TextView tv_per_frag_job;
    private TextView tv_per_frag_location;
    private TextView tv_per_frag_birthday;
    private TextView tv_per_frag_star;
    private TextView tv_per_frag_introduce;

    private TextView tv_per_frag_edit_info;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_info, null);


        initView();
        initEvent();
        setData();
        return view;

    }

    void initView(){
          tv_per_frag_userid = view.findViewById(R.id.tv_per_frag_userid);
          tv_per_frag_phone = view.findViewById(R.id.tv_per_frag_phone);
          tv_per_frag_username = view.findViewById(R.id.tv_per_frag_username);
          image_per_frag_sex = view.findViewById(R.id.image_per_frag_sex);
          tv_per_frag_hometown = view.findViewById(R.id.tv_per_frag_hometown);
          tv_per_frag_job = view.findViewById(R.id.tv_per_frag_job);
          tv_per_frag_location = view.findViewById(R.id.tv_per_frag_location);
          tv_per_frag_birthday = view.findViewById(R.id.tv_per_frag_birthday);
          tv_per_frag_star = view.findViewById(R.id.tv_per_frag_star);
          tv_per_frag_introduce = view.findViewById(R.id.tv_per_frag_introduce);
        tv_per_frag_edit_info = view.findViewById(R.id.tv_per_frag_edit_info);


    }

    void initEvent(){
        tv_per_frag_edit_info.setOnClickListener(this);
    }

    public void setData(){
        if(GlobalInfoUtil.personalInfo!=null){
            tv_per_frag_userid.setText(GlobalInfoUtil.personalInfo.getUserid()+"");
            tv_per_frag_phone.setText(GlobalInfoUtil.personalInfo.getPhone());
            tv_per_frag_username.setText(GlobalInfoUtil.personalInfo.getUsername());
            if(GlobalInfoUtil.personalInfo.getSex()==null){
                image_per_frag_sex.setVisibility(View.INVISIBLE);
            }else{
                image_per_frag_sex.setVisibility(View.VISIBLE);
                if(GlobalInfoUtil.personalInfo.getSex()==2){
                    image_per_frag_sex.setImageResource(R.drawable.ic_woman);
                }else {
                    image_per_frag_sex.setImageResource(R.drawable.ic_man);
                }
            }

            tv_per_frag_hometown.setText(GlobalInfoUtil.personalInfo.getHometown());
            tv_per_frag_job.setText(GlobalInfoUtil.personalInfo.getJob());
            tv_per_frag_location.setText(GlobalInfoUtil.personalInfo.getLocation());



            if(GlobalInfoUtil.personalInfo.getBirthday()!=null){
                tv_per_frag_birthday.setText(GlobalInfoUtil.personalInfo.getBirthday());
                tv_per_frag_star.setText(MyTimeUtil.getStarByTime(GlobalInfoUtil.personalInfo.getBirthday()));
            }

            tv_per_frag_introduce.setText(GlobalInfoUtil.personalInfo.getIntroduce());

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_per_frag_edit_info:
                Intent intent = new Intent(getContext(), UpdatePersonalInfoActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}
