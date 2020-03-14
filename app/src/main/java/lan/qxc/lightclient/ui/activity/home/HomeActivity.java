package lan.qxc.lightclient.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.wakehao.bar.BottomNavigationBar;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.home.WePagerAdapter;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.fragment.home.IndexFragment;
import lan.qxc.lightclient.ui.fragment.home.MimeFragment;
import lan.qxc.lightclient.ui.fragment.home.NotificationFragment;
import lan.qxc.lightclient.ui.fragment.home.SecondFragment;
import lan.qxc.lightclient.util.UIUtils;

public class HomeActivity extends BaseForCloseActivity {

    private List<Fragment> fragmentList;
    private BottomNavigationBar bottomNavigationBar;
    private WePagerAdapter wePagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initview();
        initEvent();

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
        bottomNavigationBar.showNum(1,100);
        bottomNavigationBar.showNum(2,-2);
        bottomNavigationBar.disMissNum(3);
        bottomNavigationBar.getViewPager();

        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
        fragmentList=new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new NotificationFragment());
        fragmentList.add(new MimeFragment());
        wePagerAdapter=new WePagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(wePagerAdapter);
        mViewPager.setCurrentItem(0);

    }

    void initEvent(){

    }



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

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int arg0) {
        }
    }



}
