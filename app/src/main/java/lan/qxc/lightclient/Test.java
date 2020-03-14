package lan.qxc.lightclient;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;

import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.https.BaseCallBack;
import lan.qxc.lightclient.https.BaseOkHttpClient;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import lan.qxc.lightclient.service.UserService;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.LoginActivity;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.MyDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test {



    public static void main2() {


        UserRequestService service = RetrofitHelper.getInstance().create(UserRequestService.class);

        HashMap<String ,Object> map = new HashMap<>();
        map.put("phone","15054190193");
        map.put("username","lan");
        map.put("password","123456");

        User user = new User();
        user.setUserid(new Long(1));
        user.setIntroduce("我是一种态度");
        user.setUsername("qxc");
//        System.out.println(user.toString());
        RequestBody body = (JsonUtils.getRequestBody(user));

        Call<Result> call = UserService.getInstance().login("15054190193","12345");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    User user = (User)JsonUtils.jsonToObj(User.class,jsonstr);
                    System.out.println((user.toString()));

                }else{
                    System.out.println(message);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                System.out.println("eeeeeeeeeeeeeeeeeeeeeeeee");
            }
        });
    }




}
