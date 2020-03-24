package lan.qxc.lightclient.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;

public class JsonUtils<T> {
    static Gson gson = new Gson();

    public static RequestBody getRequestBody(HashMap<String ,Object> map){

        String str = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),str);
        return requestBody;
    }


    public static RequestBody getRequestBody(Object object){

        String str = gson.toJson(object);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),str);
        return requestBody;
    }

    public static Object jsonToObj(Class objClass, String jsonStr) {

        return gson.fromJson(jsonStr,objClass);
    }

    public static String objToJson(Object obj)  {
        return gson.toJson(obj);
    }

    public static String listToJson(List list){
        return gson.toJson(list);
    }


}
