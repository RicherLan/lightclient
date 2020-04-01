package lan.qxc.lightclient.service.service_callback;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.NettyService;
import lan.qxc.lightclient.service.SingleChatMsgService;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleChatMsgExecutor {

    private static class SingleChatMsgExecutorHolder{
        private static SingleChatMsgExecutor instance = new SingleChatMsgExecutor();
    }

    public static SingleChatMsgExecutor getInstance(){
        return SingleChatMsgExecutorHolder.instance;
    }

    public interface SingleChatMsgListener {
        void getResult(String message);
    }


    /**
     * 删除某消息
     * @param msgid
     * @param listener
     */
    public void deleteMsgByMsgid(Long msgid,SingleChatMsgListener listener){
        Call<Result> call = SingleChatMsgService.getInstance().deleteMsgByMsgid(msgid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                }else{
                    String message = result.getMessage();
                    listener.getResult(message);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }

    public void getSingleChatMsgNotReadOfUid( SingleChatMsgListener listener){
        Call<Result> call = SingleChatMsgService.getInstance().getMsgNotReadOfUid(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                }else{
                    String message = result.getMessage();
                    if(message.equals("SUCCESS")){
                        String jsonstr = JsonUtils.objToJson(result.getData());
                        List<SingleChatMsg> singleChatMsgs = new Gson().fromJson(jsonstr,new TypeToken<List<SingleChatMsg>>(){}.getType());
                        MessageCacheUtil.receiveMsgNotRead(singleChatMsgs);

//                        //通知消息界面刷新数据
//                        Intent intent=new Intent(ContextActionStr.notification_msg_frag_action);
//                        intent.putExtra("type","freshadapter");
//                        NettyService.nettyService.sendCast(intent);


//                        //最后通知聊天界面
//                        Intent intent2=new Intent(ContextActionStr.single_chat_activity_action);
//                        intent2.putExtra("type","receivemsg");
//                        intent2.putExtra("userid",singleChatMsg.getSendUid());
//                        NettyService.nettyService.sendCast(intent2);
                    }
                    listener.getResult(message);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }

    public void getMsgByMsgid(Long msgid,SingleChatMsgListener listener){
        Call<Result> call = SingleChatMsgService.getInstance().getMsgByMsgid(msgid);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                }else{
                    String message = result.getMessage();
                    listener.getResult(message);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }


    /**
     * userid给我发的消息设置已读
     * @param userid
     * @param listener
     */
    public void setAllMsgHadReadByuid(Long userid,SingleChatMsgListener listener){
        Call<Result> call = SingleChatMsgService.getInstance().setAllMsgHadReadByuid(userid, GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listener.getResult("error");
                }else{
                    String message = result.getMessage();
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
