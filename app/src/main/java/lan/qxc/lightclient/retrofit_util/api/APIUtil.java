package lan.qxc.lightclient.retrofit_util.api;

import java.io.File;

public class APIUtil {

    //获得请求url
    public static String getUrl(String path){
        return BaseAPI.BASE_URL+ File.separator+path;
    }

}
