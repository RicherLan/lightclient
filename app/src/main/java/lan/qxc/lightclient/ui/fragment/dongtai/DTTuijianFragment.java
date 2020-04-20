package lan.qxc.lightclient.ui.fragment.dongtai;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.listener.EndlessRecyclerOnScrollListener;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.service.GuanzhuService;
import lan.qxc.lightclient.service.service_callback.DongtaiFreshExecutor;
import lan.qxc.lightclient.service.service_callback.GuanzhuExecutor;
import lan.qxc.lightclient.ui.fragment.home.ContactFragment;
import lan.qxc.lightclient.ui.widget.bigimage_looker.BigImageLookerActivity;
import lan.qxc.lightclient.ui.widget.imagewarker.MessagePicturesLayout;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTTuijianFragment extends Fragment implements View.OnClickListener , MessagePicturesLayout.Callback{

    public static DTTuijianFragment tuijianFragment;

    private View view;

    private SwipeRefreshLayout layout_refresh_dt_tuijian_frag;
    private RecyclerView recyclerview_dt_tuijian_frag;

    private DongtaiAdapter dongtaiAdapter;


    boolean isTranslucentStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_dt_tuijian,container,false);

            initView();
            initEvent();
            requestNewDongtai();


        }
        tuijianFragment = this;
        return view;
    }

    private void initView(){
        layout_refresh_dt_tuijian_frag = view.findViewById(R.id.layout_refresh_dt_tuijian_frag);
        recyclerview_dt_tuijian_frag = view.findViewById(R.id.recyclerview_dt_tuijian_frag);

    }

    private void initEvent(){

        layout_refresh_dt_tuijian_frag.setColorSchemeResources(R.color.my_green);
        layout_refresh_dt_tuijian_frag.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_orange_light));



        dongtaiAdapter = new DongtaiAdapter(getActivity(),Dongtai_catch_util.tjDongtailVOS);

        recyclerview_dt_tuijian_frag.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_dt_tuijian_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyclerview_dt_tuijian_frag.setAdapter(dongtaiAdapter.setPictureClickCallback(this));

        //下拉刷新
        layout_refresh_dt_tuijian_frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requestNewDongtai();
            }
        });

        //监听上拉刷新
        recyclerview_dt_tuijian_frag.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {

                requestOldDongtai();

            }
        });

        dongtaiAdapter.setTransmitListener(new ClickTransmitListener() {
            @Override
            public void clickTransmit(int position) {
                Toast.makeText(getContext(),"点击转发 "+position,Toast.LENGTH_SHORT).show();
            }
        });

        dongtaiAdapter.setCommonListener(new ClickCommonListener() {
            @Override
            public void clickCommon(int position) {
                Toast.makeText(getContext(),"点击评论 "+position,Toast.LENGTH_SHORT).show();
            }
        });


        dongtaiAdapter.setLikeListener(new ClickLikeListener() {
            @Override
            public void clickLike(int position) {
                Toast.makeText(getContext(),"点击点赞 "+position,Toast.LENGTH_SHORT).show();
            }
        });

        dongtaiAdapter.setGuanzhuMenuListener(new ClickGuanzhuMenuListener() {
            @Override
            public void getPosition(int pos) {
                clickGZMenu(pos);
            }
        });


    }


    private void clickGZMenu(int pos){

        int gzType = Dongtai_catch_util.tjDongtailVOS.get(pos).getGuanzhu_type();

        if(gzType==0||gzType==1){

            AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                    .setTitle("确定取消关注吗")
//                    .setMessage("有多个按钮")
//                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delGuanzhu(pos);
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
            guanzhu(pos);
        }

    }

    Timer guanzhuTimer;
    void startLoading(){
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
        guanzhuTimer = new Timer();
        MyDialog.showBottomLoadingDialog(getContext());
        guanzhuTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"提交失败,请稍后再试!",Toast.LENGTH_SHORT).show();
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
        if(refreshDTTimer!=null){
            refreshDTTimer.cancel();
        }

        if(oldFreshDTTimer!=null){
            oldFreshDTTimer.cancel();
        }

        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
    }
    private void delGuanzhu(int pos){
        DongtailVO dongtailVO = Dongtai_catch_util.tjDongtailVOS.get(pos);
        startLoading();
        GuanzhuExecutor.getInstance().delGuanzhu(getContext(), GlobalInfoUtil.personalInfo.getUserid(), dongtailVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                            dongtaiAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void guanzhu(int pos){
        DongtailVO dongtailVO = Dongtai_catch_util.tjDongtailVOS.get(pos);
        startLoading();

        GuanzhuExecutor.getInstance().guanzhu(getContext(), GlobalInfoUtil.personalInfo.getUserid(), dongtailVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                            dongtaiAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {

    }

    //点击了图片
    @Override
    public void onThumbPictureClick(int pos,ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
       // vImageWatcher.show(i, imageGroupList, urlList);

     //   Toast.makeText(getActivity(),"点击了第"+pos+"张图片",Toast.LENGTH_SHORT).show();

        ArrayList<String> titles  = new ArrayList<>();

        for (String s: urlList){
            System.out.println(s);
        }

        //之前存的是缩略图  现在点击查看大图   所以要请求原图
        urlList = APIUtil.SLUrlListToOrienlUrlList(urlList);

        Intent intent = new Intent(getActivity(), BigImageLookerActivity.class);
        intent.putExtra("pos",pos);
        intent.putStringArrayListExtra("imgPaths", (ArrayList<String>) urlList);
        intent.putStringArrayListExtra("titles",titles);

        startActivity(intent);

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
                        layout_refresh_dt_tuijian_frag.setRefreshing(false);
                        Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },25000);

        DongtaiFreshExecutor.getInstance().requestNewDongtai(new DongtaiFreshExecutor.DongtaiFreshListener() {
            @Override
            public void getResult(String message) {
                refreshDTTimer.cancel();
                layout_refresh_dt_tuijian_frag.setRefreshing(false);
                if(message.equals("SUCCESS")){
                    dongtaiAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //上拉刷新
    public void requestOldDongtai(){

        if(Dongtai_catch_util.tjDongtailVOS.size()<=0){
            return;
        }

        dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING);
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
                        dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING_COMPLETE);
                        Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        },25000);


        Long olddtid = Dongtai_catch_util.tjDongtailVOS.get(Dongtai_catch_util.tjDongtailVOS.size()-1).getDtid();

        DongtaiFreshExecutor.getInstance().requestOldDongtai(olddtid,new DongtaiFreshExecutor.DongtaiFreshListener() {
            @Override
            public void getResult(String message) {
                oldFreshDTTimer.cancel();
                layout_refresh_dt_tuijian_frag.setRefreshing(false);
                if(message.startsWith("SUCCESS")){
                    if(message.contains("END")){
                        dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING_END);
                    }else{
                        dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING_COMPLETE);
                    }
                    dongtaiAdapter.notifyDataSetChanged();
                }else{
                    dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING_COMPLETE);
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public interface ClickGuanzhuMenuListener{
        void getPosition(int pos);
    }

    public interface ClickTransmitListener{
        void clickTransmit(int position);
    }
    public interface ClickCommonListener{
        void clickCommon(int position);
    }
    public interface ClickLikeListener{
        void clickLike(int position);
    }
}
