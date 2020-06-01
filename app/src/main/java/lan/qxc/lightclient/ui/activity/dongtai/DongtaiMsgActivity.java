package lan.qxc.lightclient.ui.activity.dongtai;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiMsgAdapter;
import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.mseeage_config.DongtaiMsgCacheUtil;
import lan.qxc.lightclient.entity.DongtaiMsg;
import lan.qxc.lightclient.entity.DongtaiMsgVO;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.listener.EndlessRecyclerOnScrollListener;
import lan.qxc.lightclient.service.service_callback.DongtaiFreshExecutor;
import lan.qxc.lightclient.service.service_callback.DongtaiMsgExecutor;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;

//动态消息的展示界面
public class DongtaiMsgActivity extends BaseForCloseActivity implements View.OnClickListener {


    ImageView iv_back_dt_msg;

    SwipeRefreshLayout layout_refresh_dt_msg_acti;
    RecyclerView recyclerview_dt_msg_acti;
    DongtaiMsgAdapter dongtaiMsgAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dongtaimsg);


        initView();
        initEvent();
        DongtaiMsgCacheUtil.setMsgNotReadNum(0);
        requestNewDongtai();

        DongtaiMsgExecutor.getInstance().set_Dongtai_Msg_Had_Read(new DongtaiMsgExecutor.DongtaiMsgListener() {
            @Override
            public void getResult(String message) {
            }
        });


    }


    private void initView(){

        iv_back_dt_msg = this.findViewById(R.id.iv_back_dt_msg);
        layout_refresh_dt_msg_acti = this.findViewById(R.id.layout_refresh_dt_msg_acti);
        recyclerview_dt_msg_acti = this.findViewById(R.id.recyclerview_dt_msg_acti);

    }

    private void initEvent(){
        iv_back_dt_msg.setOnClickListener(this);

        layout_refresh_dt_msg_acti.setColorSchemeResources(R.color.my_green);
        layout_refresh_dt_msg_acti.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_orange_light));

        dongtaiMsgAdapter = new DongtaiMsgAdapter(this, DongtaiMsgCacheUtil.dongtaiList,DongtaiMsgCacheUtil.dongtaiMsgList);

        recyclerview_dt_msg_acti.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_dt_msg_acti.addItemDecoration(new SpaceItemDecoration(this).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyclerview_dt_msg_acti.setAdapter(dongtaiMsgAdapter);


        //下拉刷新
        layout_refresh_dt_msg_acti.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requestNewDongtai();
            }
        });

        //监听上拉刷新
        recyclerview_dt_msg_acti.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {

                requestOldDongtai();

            }
        });


    }

    Timer refreshDTTimer;   //下拉刷新请求新的
    Timer oldFreshDTTimer;   //上拉刷新请求旧的
    //下拉刷新
    public void requestNewDongtai(){
        if(refreshDTTimer!=null){
            refreshDTTimer.cancel();
        }
        refreshDTTimer = new Timer();

        refreshDTTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        layout_refresh_dt_msg_acti.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },25000);

        DongtaiMsgExecutor.getInstance().requestNewDongtaiMsg(new DongtaiMsgExecutor.DongtaiMsgListener() {
            @Override
            public void getResult(String message) {
                refreshDTTimer.cancel();
                layout_refresh_dt_msg_acti.setRefreshing(false);
                if(message.equals("SUCCESS")){
                    dongtaiMsgAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //上拉刷新
    public void requestOldDongtai(){

        if(DongtaiMsgCacheUtil.dongtaiMsgList.size()<=0){
            return;
        }

        dongtaiMsgAdapter.setLoadState(dongtaiMsgAdapter.LOADING);
        if(oldFreshDTTimer!=null){
            oldFreshDTTimer.cancel();
        }
        oldFreshDTTimer = new Timer();

        oldFreshDTTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dongtaiMsgAdapter.setLoadState(dongtaiMsgAdapter.LOADING_COMPLETE);
                        Toast.makeText(getApplicationContext(),"加载失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        },25000);


        Long oldmsgid = DongtaiMsgCacheUtil.dongtaiMsgList.get(DongtaiMsgCacheUtil.dongtaiMsgList.size()-1).getMsgid();

        DongtaiMsgExecutor.getInstance().requestOldDongtaiMsg(oldmsgid,new DongtaiMsgExecutor.DongtaiMsgListener() {
            @Override
            public void getResult(String message) {
                oldFreshDTTimer.cancel();
                layout_refresh_dt_msg_acti.setRefreshing(false);
                if(message.startsWith("SUCCESS")){
                    if(message.contains("END")){
                        dongtaiMsgAdapter.setLoadState(dongtaiMsgAdapter.LOADING_END);
                    }else{
                        dongtaiMsgAdapter.setLoadState(dongtaiMsgAdapter.LOADING_COMPLETE);
                    }
                    dongtaiMsgAdapter.notifyDataSetChanged();
                }else{
                    dongtaiMsgAdapter.setLoadState(dongtaiMsgAdapter.LOADING_COMPLETE);
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_dt_msg:
                finish();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(refreshDTTimer!=null){
            refreshDTTimer.cancel();
        }

        if(oldFreshDTTimer!=null){
            oldFreshDTTimer.cancel();
        }
    }

}
