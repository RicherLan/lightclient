package lan.qxc.lightclient.service.service_callback;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DongtaiFreshExecutor {

    private static class DongtaiFreshExecutorExecutorHolder{
        private static DongtaiFreshExecutor instance = new DongtaiFreshExecutor();
    }

    public static DongtaiFreshExecutor getInstance(){
        return DongtaiFreshExecutor.DongtaiFreshExecutorExecutorHolder.instance;
    }

    public interface DongtaiFreshListener {
        void getResult(String message);
    }

    //下拉刷新请求动态
    public void requestNewDongtai(DongtaiFreshListener listener){

        Call<Result> call = DongtaiServicce.getInstance().getDongtai_New_List(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    Dongtai_catch_util.updateNewTJDTList(dongtailVOs);
                }else{

                }
                listener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });

    }


    //上拉刷新
    public void requestOldDongtai(Long olddtid,DongtaiFreshListener listener){
        Call<Result> call = DongtaiServicce.getInstance().getDongtai_Back_List(GlobalInfoUtil.personalInfo.getUserid(),olddtid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    Dongtai_catch_util.updateOldTJDTList(Dongtai_catch_util.tjDongtailVOS,dongtailVOs);

                    if(dongtailVOs.size()==0){
                        listener.getResult("SUCCESS_END");
                    }else{
                        listener.getResult("SUCCESS_COMPLATE");
                    }
                }else{
                    listener.getResult(message);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }




    //下拉刷新  某指定用户的动态
    public void requestUserNewDongtai(Long userid,DongtaiFreshListener listener){

        Call<Result> call = DongtaiServicce.getInstance().getUserDongtai_New_List(GlobalInfoUtil.personalInfo.getUserid(),userid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    Dongtai_catch_util.updateNewTJDTList(userid,dongtailVOs);
                }else{

                }
                listener.getResult(message);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });

    }


    //上拉刷新 某指定用户的动态
    public void requestUserOldDongtai(Long userid,DongtaiFreshListener listener){

        if(Dongtai_catch_util.tjDongtailVOS.size()<=0){
            return;
        }

        Long olddtid = Dongtai_catch_util.tjDongtailVOS.get(Dongtai_catch_util.tjDongtailVOS.size()-1).getDtid();

        Call<Result> call = DongtaiServicce.getInstance().getUserDongtai_Back_List(GlobalInfoUtil.personalInfo.getUserid(),userid,olddtid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    Dongtai_catch_util.updateOldTJDTList(userid,dongtailVOs);
                    if(dongtailVOs.size()==0){
                        listener.getResult("SUCCESS_END");
                    }else{
                        listener.getResult("SUCCESS_COMPLATE");
                    }
                }else{
                    listener.getResult(message);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });

    }



}
