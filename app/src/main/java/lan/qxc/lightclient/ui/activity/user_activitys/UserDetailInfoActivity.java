package lan.qxc.lightclient.ui.activity.user_activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.adapter.home.AdapterPage;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.service.service_callback.GuanzhuExecutor;
import lan.qxc.lightclient.service.service_callback.UserInfoExecutor;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.fragment.personal.Personal_dongtai_fragment;
import lan.qxc.lightclient.ui.fragment.user.UserInfoFragment;
import lan.qxc.lightclient.ui.view.movingimage.MovingImageView;
import lan.qxc.lightclient.util.DensityUtil;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.ImageUtil;
import lan.qxc.lightclient.util.MyDialog;

public class UserDetailInfoActivity extends BaseForCloseActivity implements View.OnClickListener{


    private MovingImageView moving_image_personal;

    private ImageView iv_back_personal;
    private TextView tv_title_nickname_personal;

    private CircleImageView iv_headicon_personal;
    private TextView tv_nickname_personal;

    private LinearLayout layout_guanzhu_user_detail;
    private ImageView iv_guanzhu_addlable_user_detail;
    private TextView tv_guanzhu_text_user_detail;

    private TextView tv_label_qianming_user_detail;
    private TextView tv_qianming_user_detail;

    private LinearLayout layout_sendmsg_user_detail;

    private SmartRefreshLayout smartrefresh_personal_layout;
    private int mOffset = 0;

    private AppBarLayout appabr_personal_layout;
    private CollapsingToolbarLayout collapsingtoolbar_layout_personal;
    private Toolbar toolbar_peronal;

    private TabLayout tablayout_personal;
    private ViewPager view_pager_personal;

    private Long userid;
    private UserInfoFragment userInfoFragment;
    private Personal_dongtai_fragment personal_dongtai_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        userid = intent.getLongExtra("userid",-1);
        if(userid==-1){
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            finish();
        }

        initView();
        //初始化沉浸式
//        mImmersionBar.titleBar(toolbar_peronal).init();
        initViewPager();
        initEvent();
        setData();
        requestUserInfo();
    }

    void requestUserInfo(){

        UserInfoExecutor.getInstance().getUserDetailInfo(this, userid, new UserInfoExecutor.UserListener() {
            @Override
            public void getResult(String message) {
                if(message.equals("SUCCESS")){
                    setData();
                    userInfoFragment.setData();
                }else{
                    Toast.makeText(UserDetailInfoActivity.this,"加载失败!",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void setData(){
        if(FriendCatcheUtil.userInfoMap.containsKey(userid)){
            FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);
            String name = friendVO.getUsername();
            if(friendVO.getRemark()!=null){
                name = friendVO.getRemark();
            }
            tv_nickname_personal.setText(name);

            int gzType = friendVO.getGuanzhu_type();
            if(gzType==1){        //我已经关注了对方
                layout_guanzhu_user_detail.setBackground(getResources().getDrawable(R.drawable.redis_has_guanzhu_layout));
                iv_guanzhu_addlable_user_detail.setVisibility(View.GONE);
                tv_guanzhu_text_user_detail.setTextColor(getResources().getColor(R.color.my_font_grey));
                tv_guanzhu_text_user_detail.setText("已关注");

            }else if(gzType==0){
                layout_guanzhu_user_detail.setBackground(getResources().getDrawable(R.drawable.redis_has_guanzhu_layout));
                iv_guanzhu_addlable_user_detail.setVisibility(View.GONE);
                tv_guanzhu_text_user_detail.setTextColor(getResources().getColor(R.color.my_font_grey));
                tv_guanzhu_text_user_detail.setText("好友");
            } else{                           //我未关注对方
                layout_guanzhu_user_detail.setBackground(getResources().getDrawable(R.drawable.redis_guanzhu_layout));
                iv_guanzhu_addlable_user_detail.setVisibility(View.VISIBLE);
                tv_guanzhu_text_user_detail.setTextColor(getResources().getColor(R.color.my_orange_light));
                tv_guanzhu_text_user_detail.setText("关注");
            }

            String intro = friendVO.getIntroduce();
            if(intro!=null&&!intro.isEmpty()){
                tv_label_qianming_user_detail.setVisibility(View.VISIBLE);
                tv_qianming_user_detail.setVisibility(View.VISIBLE);
                tv_qianming_user_detail.setText(intro);
            }else{
                tv_label_qianming_user_detail.setVisibility(View.INVISIBLE);
                tv_qianming_user_detail.setVisibility(View.INVISIBLE);
            }


            //加载头像
            String headIcPath = APIUtil.getUrl(friendVO.getIcon());
            ImageUtil.getInstance().setNetImageToView(this,headIcPath,iv_headicon_personal);


        }


    }

    void initView(){
        moving_image_personal = this.findViewById(R.id.moving_image_user_detail);

        iv_back_personal = this.findViewById(R.id.iv_back_user_detail);
        tv_title_nickname_personal = this.findViewById(R.id.tv_title_nickname_user_detail);

        iv_headicon_personal = this.findViewById(R.id.iv_headicon_user_detail);
        tv_nickname_personal = this.findViewById(R.id.tv_nickname_user_detail);

        layout_guanzhu_user_detail = this.findViewById(R.id.layout_guanzhu_user_detail);
        iv_guanzhu_addlable_user_detail = this.findViewById(R.id.iv_guanzhu_addlable_user_detail);
        tv_guanzhu_text_user_detail = this.findViewById(R.id.tv_guanzhu_text_user_detail);

        tv_label_qianming_user_detail = this.findViewById(R.id.tv_label_qianming_user_detail);
        tv_label_qianming_user_detail.setVisibility(View.INVISIBLE);
        tv_qianming_user_detail = this.findViewById(R.id.tv_qianming_user_detail);

        layout_sendmsg_user_detail = this.findViewById(R.id.layout_sendmsg_user_detail);

        smartrefresh_personal_layout = this.findViewById(R.id.smartrefresh_user_detail_layout);

        appabr_personal_layout = this.findViewById(R.id.appabr_user_detail_layout);
        collapsingtoolbar_layout_personal = this.findViewById(R.id.collapsingtoolbar_layout_user_detail);
        toolbar_peronal = this.findViewById(R.id.toolbar_user_detail);
        tablayout_personal = this.findViewById(R.id.tablayout_user_detail);
        view_pager_personal = this.findViewById(R.id.view_pager_user_detail);

    }

    void initViewPager(){
        userInfoFragment = new UserInfoFragment(userid);

        personal_dongtai_fragment = new Personal_dongtai_fragment(userid);
        AdapterPage adapterPage = new AdapterPage(getSupportFragmentManager());
        adapterPage.addFragment(userInfoFragment,"资料卡");
        adapterPage.addFragment(personal_dongtai_fragment,"西西墙");
        view_pager_personal.setAdapter(adapterPage);

        tablayout_personal.setupWithViewPager(view_pager_personal);
        tablayout_personal.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        view_pager_personal.setCurrentItem(1);
        tablayout_personal.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tablayout_personal,30,30);
            }
        });

    }


    void initEvent(){
        iv_back_personal.setOnClickListener(this);

        iv_headicon_personal.setOnClickListener(this);

        smartrefresh_personal_layout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                moving_image_personal.setTranslationY(mOffset);
            }


//            @Override
//            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
//
//                mOffset = offset / 2;
//                movingImageView.setTranslationY(mOffset);
//            }
        });

        smartrefresh_personal_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                Log.e("fsdf","jskdfj舒心");
            }
        });

        appabr_personal_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                moving_image_personal.setTranslationY(verticalOffset);
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(300) - toolbar_peronal.getHeight()) {//关闭
//                    if (iswhite) {//变黑
//                        if (ImmersionBar.isSupportStatusBarDarkFont()) {
//                            mImmersionBar.statusBarDarkFont(true).init();
//                            isblack = true;
//                            iswhite = false;
//                        }
//                    }
                    tv_title_nickname_personal.setVisibility(View.VISIBLE);
                    collapsingtoolbar_layout_personal.setContentScrimResource(R.drawable.fragment_mine_pic);

                    iv_back_personal.setBackgroundResource(R.drawable.ic_back_black_30);

                } else {  //展开

                    tv_title_nickname_personal.setVisibility(View.INVISIBLE);
                    collapsingtoolbar_layout_personal.setContentScrimResource(R.color.transparent);
                    iv_back_personal.setBackgroundResource(R.drawable.ic_back_black_30);
                }
            }
        });


        layout_guanzhu_user_detail.setOnClickListener(this);

        imageCircleRun(iv_headicon_personal);

    }

    /*
        圆形滚动
     */
    private void imageCircleRun(View view){
        //动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        view.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_user_detail:
                finish();
                break;

            case R.id.layout_guanzhu_user_detail:
                clickGZMenu();
                break;
            default:
                break;

        }
    }


    private void clickGZMenu(){
        FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);
        if(friendVO==null){
            return;
        }
        int gzType = friendVO.getGuanzhu_type();

        if(gzType==0||gzType==1){

            AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                    .setTitle("确定取消关注吗")
//                    .setMessage("有多个按钮")
//                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            delGuanzhu();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
            alertDialog2.show();

        }else{
            guanzhu();
        }

    }

    Timer guanzhuTimer;
    void startLoading(){
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
        guanzhuTimer = new Timer();
        MyDialog.showBottomLoadingDialog(this);
        guanzhuTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(UserDetailInfoActivity.this,"提交失败,请稍后再试!",Toast.LENGTH_SHORT).show();
            }
        },20000);
    }

    void cancleLoading(){
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
        MyDialog.dismissBottomLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
    }

    private void delGuanzhu(){
        FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);
        if(friendVO==null){
            return;
        }

        startLoading();
        GuanzhuExecutor.getInstance().delGuanzhu(this, GlobalInfoUtil.personalInfo.getUserid(), friendVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                            setData();
                        }else{
                            Toast.makeText(UserDetailInfoActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void guanzhu(){
        FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);
        if(friendVO==null){
            return;
        }
        startLoading();

        GuanzhuExecutor.getInstance().guanzhu(this, GlobalInfoUtil.personalInfo.getUserid(), friendVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                           setData();
                        }else{
                            Toast.makeText(UserDetailInfoActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    /**
     * 通过反射修改踏遍layout的宽，其实相当于margin
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field slidingTabIndicator=null;
        try {
            slidingTabIndicator = tabLayout.getDeclaredField("slidingTabIndicator");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        slidingTabIndicator.setAccessible(true);

        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) slidingTabIndicator.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}

