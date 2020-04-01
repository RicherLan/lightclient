package lan.qxc.lightclient.retrofit_util.service;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.SingleChatMsgAPI;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SingleChatMsgRequestService {

    @POST(SingleChatMsgAPI.DELETE_MSG_BY_MSGID)
    @FormUrlEncoded
    Call<Result> deleteMsgByMsgid(@Field("msgid") Long msgid);


    @POST(SingleChatMsgAPI.GET_ALL_MSG_NOT_READ_BY_UID)
    @FormUrlEncoded
    Call<Result> getMsgNotReadOfUid(@Field("userid") Long userid);

    @POST(SingleChatMsgAPI.GET_MSG_BY_MSGID)
    @FormUrlEncoded
    Call<Result> getMsgByMsgid(@Field("msgid") Long msgid);

    @POST(SingleChatMsgAPI.SET_ALL_MSG_HAD_READ_BY_UID)
    @FormUrlEncoded
    Call<Result> setAllMsgHadReadByuid(@Field("senduid") Long senduid,@Field("receiveuid") Long receiveuid);



}
