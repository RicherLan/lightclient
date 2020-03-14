package lan.qxc.lightclient.util;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;

public class JsonUtils {

    public static RequestBody getRequestBody(HashMap<String ,Object> map){
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),str);
        return requestBody;
    }


    public static RequestBody getRequestBody(Object object){
        Gson gson = new Gson();
        String str = gson.toJson(object);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),str);
        return requestBody;
    }

    public static Object jsonToObj(Class objClass, String jsonStr) {

        Gson gson = new Gson();
        return gson.fromJson(jsonStr,objClass);
    }

    public static String objToJson(Object obj)  {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
