package lan.qxc.lightclient.service.service_callback;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.FriendMsgService;
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
