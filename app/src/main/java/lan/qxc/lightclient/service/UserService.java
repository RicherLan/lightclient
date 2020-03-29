package lan.qxc.lightclient.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.RetrofitTools;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class UserService {

    private static class  UserServiceHolder{
        static UserService instance = new UserService();
    }

    public static UserService getInstance(){
        return UserServiceHolder.instance;
    }


    public Call<Result> login(String phone,String password){

        UserRequestService userRequestService = RetrofitHelper.getInstance().create(UserRequestService.class);
        Map<String ,Object> map = new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);

        return userRequestService.login(map);
    }


    public Call<Result> regis_isPhoneExist(String phone){
        UserRequestService service = RetrofitHelper.getInstance().create(UserRequestService.class);
        return service.isPhoneHasExist(phone);
    }

    public Call<Result> regis_user(String phone,String username,String password){
        UserRequestService service = RetrofitHelper.getInstance().create(UserRequestService.class);
        Map<String ,Object> map = new HashMap<>();
        map.put("phone",phone);
        map.put("username",username);
        map.put("password",password);
        return service.resgisUser(map);
    }

    public Call<Result> udpateUserInfo(User user){
        UserRequestService service = RetrofitHelper.getInstance().create(UserRequestService.class);
        return service.udpateUserInfo(user);
    }


    public Call<Result> uploadHeadic(File file,Long userid){
        UserRequestService service = RetrofitHelper.getInstance().create(UserRequestService.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("headic", file.getName(), requestFile);

        return service.uploadHeadIc(part,userid);
    }

    public Call<Result> updateUserPassword(Long userid,String oldpassword,String newPassword){

        UserRequestService userRequestService = RetrofitHelper.getInstance().create(UserRequestService.class);
        Map<String ,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("oldpassword",oldpassword);
        map.put("newpassword",newPassword);

        return userRequestService.updateUserPassword(map);
    }

    public Call<Result> getUserDetailInfo(Long userid,Long uid){

        UserRequestService userRequestService = RetrofitHelper.getInstance().create(UserRequestService.class);
        return userRequestService.getUserDetailInfo(userid,uid);
    }


}
