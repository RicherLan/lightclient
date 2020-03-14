package lan.qxc.lightclient.service;

import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
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


}
