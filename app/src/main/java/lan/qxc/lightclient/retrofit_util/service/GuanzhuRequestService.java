package lan.qxc.lightclient.retrofit_util.service;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.DongtaiAPI;
import lan.qxc.lightclient.retrofit_util.api.GuanzhuAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GuanzhuRequestService {

    @POST(GuanzhuAPI.GUANZHU)
    @FormUrlEncoded
    Call<Result> guanzhu(@Field("userid") Long userid,@Field("gzuid") Long gzuid);

    @POST(GuanzhuAPI.DEL_GUANZHU)
    @FormUrlEncoded
    Call<Result> del_guanzhu(@Field("userid") Long userid,@Field("gzuid") Long gzuid);


    @POST(GuanzhuAPI.UPDATE_REMARK_NAME)
    @FormUrlEncoded
    Call<Result> updateRemarkName(@Field("userid") Long userid,@Field("gzuid") Long gzuid,@Field("remark")String remark);


    @POST(GuanzhuAPI.GET_MY_GUANZHU)
    @FormUrlEncoded
    Call<Result> getMyGuanzhu(@Field("userid") Long userid);

    @POST(GuanzhuAPI.GET_USER_GUANZHU_ME)
    @FormUrlEncoded
    Call<Result> getUsersGuanzhuMe(@Field("userid") Long userid);


    @POST(GuanzhuAPI.GET_FRIEND_LIST)
    @FormUrlEncoded
    Call<Result> getFriendsByUserid(@Field("userid") Long userid);

    @POST(GuanzhuAPI.GET_MY_GUANZHU_NUM)
    @FormUrlEncoded
    Call<Result> getMyGuanzhuNum(@Field("userid") Long userid);

    @POST(GuanzhuAPI.GET_GUANZHU_ME_NUM)
    @FormUrlEncoded
    Call<Result> getGuanzhuMeNum(@Field("userid") Long userid);

    @POST(GuanzhuAPI.GET_MY_FRIEND_NUM)
    @FormUrlEncoded
    Call<Result> getMyFriendNum(@Field("userid") Long userid);


}
