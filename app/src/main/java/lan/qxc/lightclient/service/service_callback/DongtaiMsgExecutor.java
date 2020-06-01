package lan.qxc.lightclient.service.service_callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.mseeage_config.DongtaiMsgCacheUtil;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.netty.protocol.packet.dongtai_msg_packet.DongtaiMsgPacket;
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


    //获得未读动态消息数量
    public void getDongtai_Msg_Not_Read_Num(DongtaiMsgExecutor.DongtaiMsgListener listener){
        Call<Result> call = DongtaiServicce.getInstance().getDongtai_Msg_Not_Read_Num(GlobalInfoUtil.personalInfo.getUserid());
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
                    int num = Integer.parseInt(result.getData().toString());
                    DongtaiMsgCacheUtil.msgNotReadNum = num;

                }
                listener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.getResult("error");
            }
        });
    }


    //下拉刷新请求动态消息
    public void requestNewDongtaiMsg(DongtaiMsgExecutor.DongtaiMsgListener listener){

        Call<Result> call = DongtaiServicce.getInstance().getDongtai_Msg_New_List(GlobalInfoUtil.personalInfo.getUserid());
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
                    List<DongtaiMsgPacket> dongtaiMsgPackets = new Gson().fromJson(jsonstr,new TypeToken<List<DongtaiMsgPacket>>(){}.getType());


                    if(dongtaiMsgPackets!=null){
                        DongtaiMsgCacheUtil.dongtaiList.clear();
                        DongtaiMsgCacheUtil.dongtaiMsgList.clear();

                        for(DongtaiMsgPacket dongtaiMsgPacket : dongtaiMsgPackets){
                            DongtaiMsgCacheUtil.dongtaiList.add(dongtaiMsgPacket.getDongtailVO());
                            DongtaiMsgCacheUtil.dongtaiMsgList.add(dongtaiMsgPacket.getDongtaiMsgVO());
                        }
                    }


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
    public void requestOldDongtaiMsg(Long olddtid, DongtaiMsgExecutor.DongtaiMsgListener listener){
        Call<Result> call = DongtaiServicce.getInstance().getDongtai_Msg_Back_List(GlobalInfoUtil.personalInfo.getUserid(),olddtid);
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
                    List<DongtaiMsgPacket> dongtaiMsgPackets = new Gson().fromJson(jsonstr,new TypeToken<List<DongtaiMsgPacket>>(){}.getType());

                    for(DongtaiMsgPacket dongtaiMsgPacket : dongtaiMsgPackets){
                        DongtaiMsgCacheUtil.dongtaiList.add(dongtaiMsgPacket.getDongtailVO());
                        DongtaiMsgCacheUtil.dongtaiMsgList.add(dongtaiMsgPacket.getDongtaiMsgVO());
                    }

                    if(dongtaiMsgPackets.size()==0){
                        listener.getResult("SUCCESS_END");
                    }else{
                        listener.getResult("SUCCESS_COMPLATE");
                    }

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

    //设置自己的动态消息已读
    public void set_Dongtai_Msg_Had_Read(DongtaiMsgExecutor.DongtaiMsgListener listener){
        Call<Result> call = DongtaiServicce.getInstance().set_Dongtai_Msg_Had_Read(GlobalInfoUtil.personalInfo.getUserid());
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
