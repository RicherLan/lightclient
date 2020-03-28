package lan.qxc.lightclient;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.entity.UserVO;
import lan.qxc.lightclient.https.BaseCallBack;
import lan.qxc.lightclient.https.BaseOkHttpClient;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import lan.qxc.lightclient.retrofit_util.service.DongtaiRequestService;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.service.GuanzhuService;
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



    public static void main(String[] args) {


            Call<Result> call = GuanzhuService.getInstance().guanzhu(new Long(3),new Long(1));
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {

                    Result result = response.body();
                    String message = result.getMessage();
                    if(message.equals("SUCCESS")){
                        System.out.println("111111111111111111111111111111111111111");
                        int i = Integer.parseInt(result.getData().toString());
                        System.out.println(i+"     8888888888888888888888888888888888888");
//                    String jsonstr = JsonUtils.objToJson(result.getData());
//                    List<UserVO> userVOS = new Gson().fromJson(jsonstr,new TypeToken<List<UserVO>>(){}.getType());
//                    System.out.println(userVOS.size());
//                    for(UserVO userVO : userVOS){
//                        System.out.println(userVO);
//                    }
                    }else{
                        System.out.println(message+"   2222222222222222222222222222222222222");
                    }


                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeeeee");
                }
            });




    }




}
