package lan.qxc.lightclient.ui.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.wakehao.bar.BottomNavigationBar;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.home.WePagerAdapter;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.service.service_callback.FriendMsgExecutor;
import lan.qxc.lightclient.service.service_callback.SingleChatMsgExecutor;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.fragment.home.IndexFragment;
import lan.qxc.lightclient.ui.fragment.home.MineFragment;
import lan.qxc.lightclient.ui.fragment.home.NotificationFragment;
import lan.qxc.lightclient.ui.fragment.home.ContactFragment;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.SharePerferenceUtil;

public class HomeActivity extends BaseForCloseActivity {

    private List<Fragment> fragmentList;
    private static BottomNavigationBar bottomNavigationBar;
    private WePagerAdapter wePagerAdapter;
    private ViewPager mViewPager;

    public static HomeActivity homeActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initview();
        initEvent();
        homeActivity = this;

    }



    void initview(){

        mViewPager=(ViewPager)findViewById(R.id.view_pager);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.home_bottom_navi_bar);
        bottomNavigationBar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return true;
            }
        });
        //可动态改变item的标题
//        bar.setTitle(0,"home(99)");
        bottomNavigationBar.showNum(0,0);
        bottomNavigationBar.showNum(1,0);
        bottomNavigationBar.showNum(2,0);
        bottomNavigationBar.showNum(3,0);



//        bottomNavigationBar.disMissNum(1);
//        bottomNavigationBar.getViewPager();

        //mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
        fragmentList=new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new ContactFragment());
        fragmentList.add(new NotificationFragment());
        fragmentList.add(new MineFragment());
        wePagerAdapter=new WePagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(wePagerAdapter);
        mViewPager.setCurrentItem(0);

    }

    void initEvent(){

    }


    //设置底部栏  数量
    public static void freshMsgNum(){

        if(bottomNavigationBar!=null){
            int num = MessageCacheUtil.getMsgNotReadNum();
            bottomNavigationBar.showNum(2,num);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GlobalInfoUtil.personalInfo==null){
            SharePerferenceUtil.getPersonalInfo(this);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContextActionStr.home_activity_action);
        registerReceiver(broadcastReceiver, filter);
        freshMsgNum();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ContextActionStr.home_activity_action.equals(intent.getAction())){

                String type = intent.getStringExtra("type");
                //收到了聊天消息
                if(type!=null&&type.equals("receivesinglechatmsg")){



                }

            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK||requestCode!=1)return;
        //不带动画的切换item
        bottomNavigationBar.setItemSelected(3,false);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public BottomNavigationBar getBar(){
        return bottomNavigationBar;
    }







}
