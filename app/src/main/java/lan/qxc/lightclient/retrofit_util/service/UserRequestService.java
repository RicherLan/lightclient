package lan.qxc.lightclient.retrofit_util.service;



import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface UserRequestService {

    //登录
    @POST(UserAPI.LOGIN)
    @FormUrlEncoded
    Call<Result> login(@FieldMap Map<String, Object> map);


    //电话是否已经被注册
    @POST(UserAPI.REGISTER_IS_PHONE_HAS_EXIST)
    @FormUrlEncoded
    Call<Result> isPhoneHasExist(@Field("phone") String phone);

    //注册
    @POST(UserAPI.REGISTER_REGIST_USER_URL)
    @FormUrlEncoded
    Call<Result> resgisUser(@FieldMap Map<String, Object> map);


    @POST(UserAPI.UPDATE_USER_INFO)
    Call<Result> udpateUserInfo(@Body User user);

    @Multipart()
    @POST(UserAPI.UPLOAD_USER_ICON)
    Call<Result> uploadHeadIc(@Part MultipartBody.Part body,@Query("userid") Long userid);

}
