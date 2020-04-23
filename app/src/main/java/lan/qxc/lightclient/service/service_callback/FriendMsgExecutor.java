package lan.qxc.lightclient.service.service_callback;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.FriendMsgService;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendMsgExecutor {


    private static class FriengMsgExecutorHolder{
        private static FriendMsgExecutor instance = new FriendMsgExecutor();
    }

    public static FriendMsgExecutor getInstance(){
        return FriendMsgExecutor.FriengMsgExecutorHolder.instance;
    }

    public interface FriendMsgListener {
        void getResult(String message);
    }

    //获得未读好友请求信息
    public void getUserFriendMsgNotRead(FriendMsgListener listener){
        Call<Result> call = FriendMsgService.getInstance().getUserFriendMsgNotRead(GlobalInfoUtil.personalInfo.getUserid());
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
                    List<FriendMsgVO> friendMsgVOS = new Gson().fromJson(jsonstr,new TypeToken<List<FriendMsgVO>>(){}.getType());

                    MessageCacheUtil.updateFriendMsgs(friendMsgVOS);

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

    //设置好友消息已读
    public void setFriendMsgHadRead(Long msgid,FriendMsgListener listener){
        Call<Result> call = FriendMsgService.getInstance().setMsgHadRead(msgid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();

                if(result==null){
                    listener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                listener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }

}
