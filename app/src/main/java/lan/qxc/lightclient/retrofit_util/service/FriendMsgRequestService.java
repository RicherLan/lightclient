package lan.qxc.lightclient.retrofit_util.service;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.FriendMsgAPI;
import lan.qxc.lightclient.retrofit_util.api.GuanzhuAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FriendMsgRequestService {

    @POST(FriendMsgAPI.SET_MSG_HAD_READ)
    @FormUrlEncoded
    Call<Result> setMsgHadRead(@Field("msgid") Long msgid);


    @POST(FriendMsgAPI.GET_USER_FRIEND_MSG_NOT_READ_MSG)
    @FormUrlEncoded
    Call<Result> getUserFriendMsgNotRead(@Field("userid") Long userid);


}
