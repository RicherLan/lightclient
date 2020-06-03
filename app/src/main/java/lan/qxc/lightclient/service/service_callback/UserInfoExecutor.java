package lan.qxc.lightclient.service.service_callback;

import android.content.Context;

import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoExecutor {

    private static class UserInfoExecutorHolder{
        private static UserInfoExecutor instance = new UserInfoExecutor();
    }

    public static UserInfoExecutor getInstance(){
        return UserInfoExecutor.UserInfoExecutorHolder.instance;
    }

    public interface UserListener {
        void getResult(String message);
    }


    //
    public void getUserDetailInfo(Context context, Long userid, UserListener listener){

        Call<Result> call = UserService.getInstance().getUserDetailInfo(GlobalInfoUtil.personalInfo.getUserid(),userid);
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
                    FriendCatcheUtil.userInfoMap.put(userid,(FriendVO)JsonUtils.jsonToObj(FriendVO.class,jsonstr));

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

}
