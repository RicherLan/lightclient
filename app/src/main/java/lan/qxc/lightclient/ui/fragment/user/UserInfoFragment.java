package lan.qxc.lightclient.ui.fragment.user;

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

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.ui.activity.user_activitys.UpdatePersonalInfoActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.MyTimeUtil;

public class UserInfoFragment extends Fragment implements View.OnClickListener{

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

    Long userid;
    public UserInfoFragment(Long userid){
        this.userid = userid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, null);


        initView();
        initEvent();
        setData();
        return view;

    }

    void initView(){
        tv_per_frag_userid = view.findViewById(R.id.tv_user_info_frag_userid);
        tv_per_frag_phone = view.findViewById(R.id.tv_user_info_frag_phone);
        tv_per_frag_username = view.findViewById(R.id.tv_user_info_frag_username);
        image_per_frag_sex = view.findViewById(R.id.image_user_info_frag_sex);
        tv_per_frag_hometown = view.findViewById(R.id.tv_user_info_frag_hometown);
        tv_per_frag_job = view.findViewById(R.id.tv_user_info_frag_job);
        tv_per_frag_location = view.findViewById(R.id.tv_user_info_frag_location);
        tv_per_frag_birthday = view.findViewById(R.id.tv_user_info_frag_birthday);
        tv_per_frag_star = view.findViewById(R.id.tv_user_info_frag_star);
        tv_per_frag_introduce = view.findViewById(R.id.tv_user_info_frag_introduce);

    }

    void initEvent(){
    }

    public void setData(){
        if(FriendCatcheUtil.userInfoMap.containsKey(userid)){
            FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);

            tv_per_frag_userid.setText(friendVO.getUserid()+"");
            tv_per_frag_phone.setText(friendVO.getPhone());
            tv_per_frag_username.setText(friendVO.getUsername());
            if(friendVO.getSex()==null){
                image_per_frag_sex.setVisibility(View.INVISIBLE);
            }else{
                image_per_frag_sex.setVisibility(View.VISIBLE);
                if(friendVO.getSex()==2){
                    image_per_frag_sex.setImageResource(R.drawable.ic_woman);
                }else {
                    image_per_frag_sex.setImageResource(R.drawable.ic_man);
                }
            }

            tv_per_frag_hometown.setText(friendVO.getHometown());
            tv_per_frag_job.setText(friendVO.getJob());
            tv_per_frag_location.setText(friendVO.getLocation());



            if(friendVO.getBirthday()!=null){
                tv_per_frag_birthday.setText(friendVO.getBirthday());
                tv_per_frag_star.setText(MyTimeUtil.getStarByTime(friendVO.getBirthday()));
            }

            tv_per_frag_introduce.setText(friendVO.getIntroduce());

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

}
