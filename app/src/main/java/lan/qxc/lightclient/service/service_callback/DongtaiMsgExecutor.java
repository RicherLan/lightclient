package lan.qxc.lightclient.service.service_callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.service.FriendMsgService;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DongtaiMsgExecutor {


    private static class DongtaiMsgExecutorHolder{
        private static DongtaiMsgExecutor instance = new DongtaiMsgExecutor();
    }

    public static DongtaiMsgExecutor getInstance(){
        return DongtaiMsgExecutor.DongtaiMsgExecutorHolder.instance;
    }

    public interface DongtaiMsgListener {
        void getResult(String message);
    }

    //点赞
    public void likeDongtai(Long dtid,DongtaiMsgExecutor.DongtaiMsgListener listener){
        Call<Result> call = DongtaiServicce.getInstance().likeDongtai(dtid,GlobalInfoUtil.personalInfo.getUserid());
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
                    Dongtai_catch_util.likeDongtai(dtid);
                }
                listener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }
}
