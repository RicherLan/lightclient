package lan.qxc.lightclient.ui.fragment.personal;

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
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.listener.EndlessRecyclerOnScrollListener;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.ui.fragment.dongtai.DTTuijianFragment;
import lan.qxc.lightclient.ui.widget.bigimage_looker.BigImageLookerActivity;
import lan.qxc.lightclient.ui.widget.imagewarker.MessagePicturesLayout;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Personal_dongtai_fragment extends Fragment implements View.OnClickListener , MessagePicturesLayout.Callback{

    public static Personal_dongtai_fragment personal_dongtai_fragment;

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
        personal_dongtai_fragment = this;
        return view;
    }

    private void initView(){
        layout_refresh_dt_tuijian_frag = view.findViewById(R.id.layout_refresh_dt_tuijian_frag);
        recyclerview_dt_tuijian_frag = view.findViewById(R.id.recyclerview_dt_tuijian_frag);

    }

    private void initEvent(){

        layout_refresh_dt_tuijian_frag.setColorSchemeResources(R.color.my_green);
        layout_refresh_dt_tuijian_frag.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_orange_light));


        if(!Dongtai_catch_util.dtMap.containsKey(GlobalInfoUtil.personalInfo.getUserid())){
            Dongtai_catch_util.dtMap.put(GlobalInfoUtil.personalInfo.getUserid(),new ArrayList<DongtailVO>());
        }
        dongtaiAdapter = new DongtaiAdapter(getActivity(), Dongtai_catch_util.dtMap.get(GlobalInfoUtil.personalInfo.getUserid()));

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

        dongtaiAdapter.setTransmitListener(new DTTuijianFragment.ClickTransmitListener() {
            @Override
            public void clickTransmit(int position) {
                Toast.makeText(getContext(),"点击转发 "+position,Toast.LENGTH_SHORT).show();
            }
        });

        dongtaiAdapter.setCommonListener(new DTTuijianFragment.ClickCommonListener() {
            @Override
            public void clickCommon(int position) {
                Toast.makeText(getContext(),"点击评论 "+position,Toast.LENGTH_SHORT).show();
            }
        });


        dongtaiAdapter.setLikeListener(new DTTuijianFragment.ClickLikeListener() {
            @Override
            public void clickLike(int position) {
                Toast.makeText(getContext(),"点击点赞 "+position,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    //点击了图片
    @Override
    public void onThumbPictureClick(int pos, ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
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


        Call<Result> call = DongtaiServicce.getInstance().getUserDongtai_New_List(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                refreshDTTimer.cancel();
                layout_refresh_dt_tuijian_frag.setRefreshing(false);

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){


                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());

                    Dongtai_catch_util.updateNewTJDTList(GlobalInfoUtil.personalInfo.getUserid(),dongtailVOs);

                    // Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();


                    dongtaiAdapter.notifyDataSetChanged();


                }else{
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                refreshDTTimer.cancel();
                Toast.makeText(getActivity(),"error!!!",Toast.LENGTH_SHORT).show();
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

        Call<Result> call = DongtaiServicce.getInstance().getUserDongtai_Back_List(GlobalInfoUtil.personalInfo.getUserid(),olddtid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                oldFreshDTTimer.cancel();

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    Dongtai_catch_util.updateOldTJDTList(GlobalInfoUtil.personalInfo.getUserid(),dongtailVOs);

                    if(dongtailVOs.size()==0){
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

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                oldFreshDTTimer.cancel();
                Toast.makeText(getActivity(),"error!!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

//        Toast.makeText(getActivity(),"resume",Toast.LENGTH_SHORT).show();
//        requestNewDongtai();

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
