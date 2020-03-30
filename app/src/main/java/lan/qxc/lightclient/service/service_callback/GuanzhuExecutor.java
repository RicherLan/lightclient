package lan.qxc.lightclient.service.service_callback;

import android.content.Context;
import android.widget.Toast;

import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.GuanzhuService;
import lan.qxc.lightclient.ui.fragment.home.ContactFragment;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuanzhuExecutor {

    private static class GuanzhuExecutorHolder{
        private static GuanzhuExecutor instance = new GuanzhuExecutor();
    }

    public static GuanzhuExecutor getInstance(){
        return GuanzhuExecutorHolder.instance;
    }

    public interface GuanzhuListener {
        void getResult(String message);
    }

    //关注用户
    public void guanzhu(Context context, Long userid, Long touserid, GuanzhuListener listener){

        Call<Result> call = GuanzhuService.getInstance().guanzhu(userid,touserid);
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
                    //修改动态页面   该用户显示已关注
                    Dongtai_catch_util.setTJDongtaiGZType(touserid,1);
                    FriendCatcheUtil.meGuanzhuUser(touserid);
                    FriendCatcheUtil.updateUserInfoMap();
                    if(ContactFragment.instance!=null){
                        ContactFragment.instance.freshAllList();
                    }

                    listener.getResult(message);
                }else{
                    listener.getResult(message);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error!");
            }
        });

    }


    public void delGuanzhu(Context context, Long userid, Long touserid, GuanzhuListener listener){
        Call<Result> call = GuanzhuService.getInstance().del_guanzhu(userid,touserid);
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

                    //修改动态页面   该用户显示已关注
                    Dongtai_catch_util.setTJDongtaiGZType(touserid,4);
                    FriendCatcheUtil.meDelGuanzhuUser(touserid);
                    FriendCatcheUtil.updateUserInfoMap();
                    if(ContactFragment.instance!=null){
                        ContactFragment.instance.freshAllList();
                    }
                    listener.getResult(message);

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
