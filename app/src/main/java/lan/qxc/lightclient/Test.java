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
import lan.qxc.lightclient.https.BaseCallBack;
import lan.qxc.lightclient.https.BaseOkHttpClient;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.api.UserAPI;
import lan.qxc.lightclient.retrofit_util.service.DongtaiRequestService;
import lan.qxc.lightclient.retrofit_util.service.UserRequestService;
import lan.qxc.lightclient.service.DongtaiServicce;
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


        DongtaiRequestService service = RetrofitHelper.getInstance().create(DongtaiRequestService.class);

        Dongtai dongtai = new Dongtai();
       // dongtai.setDtid(new Long(1));
        dongtai.setUserid(new Long(1));
        dongtai.setDtcontent("你好哦");
        dongtai.setDtpic("abc.jpg");



            Call<Result> call = DongtaiServicce.getInstance().getDongtai_New_List();
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {

                    Result result = response.body();
                    String message = result.getMessage();
                    if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<DongtailVO> dongtailVOs = new Gson().fromJson(jsonstr,new TypeToken<List<DongtailVO>>(){}.getType());
                    System.out.println(dongtailVOs.size());
                        for(DongtailVO dongtailVO: dongtailVOs){
                            System.out.println(dongtailVO.toString());
                        }
                        System.out.println("111111111111111111111111111111111111111111111111111111111111111");

                    }else{
                        System.out.println(message);
                        System.out.println("222222222222222222222222222222222222222222222222222222222222222222");
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeeeee");
                }
            });




    }




}
