package lan.qxc.lightclient.service;

import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.SingleChatMsgRequestService;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import retrofit2.Call;

public class SingleChatMsgService {


    private static class  SingleChatMsgServiceHolder{
        static SingleChatMsgService instance = new SingleChatMsgService();
    }

    public static SingleChatMsgService getInstance(){
        return SingleChatMsgServiceHolder.instance;
    }


    public Call<Result> deleteMsgByMsgid(Long msgid){
        SingleChatMsgRequestService service = RetrofitHelper.getInstance().create(SingleChatMsgRequestService.class);
        return service.deleteMsgByMsgid(msgid);
    }

    public Call<Result> getMsgNotReadOfUid(Long userid){
        SingleChatMsgRequestService service = RetrofitHelper.getInstance().create(SingleChatMsgRequestService.class);
        return service.getMsgNotReadOfUid(userid);
    }

    public Call<Result> getMsgByMsgid(Long msgid){
        SingleChatMsgRequestService service = RetrofitHelper.getInstance().create(SingleChatMsgRequestService.class);
        return service.getMsgByMsgid(msgid);
    }

    public Call<Result> setAllMsgHadReadByuid(Long senduid,Long receiveuid){
        SingleChatMsgRequestService service = RetrofitHelper.getInstance().create(SingleChatMsgRequestService.class);
        return service.setAllMsgHadReadByuid(senduid,receiveuid);
    }

}
