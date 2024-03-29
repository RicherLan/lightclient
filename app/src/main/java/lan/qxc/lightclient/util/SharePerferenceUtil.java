package lan.qxc.lightclient.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import lan.qxc.lightclient.entity.PersonalInfo;
import lan.qxc.lightclient.entity.User;

public class SharePerferenceUtil {

    public static String sh_user = "user";
    public static String sh_login_username = "login_username";
    public static String sh_login_password = "login_password";
    public static String sh_personal_headicon = "personal_headic";

    public static String sh_kip_login = "kip_login";
    public static String sh_personal_info = "user_info";


    /*
        获取用户信息相关的
     */
    public static String getUserFromSP(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sh_user, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    /*
        存储用户相关信息
     */
    public static boolean save_User_SP(Context context, Map<String,String > map){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sh_user, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(String key:map.keySet()){
            String value = map.get(key);
            editor.putString(key,value);
        }
        return editor.commit();
    }


    public static boolean savePeronalInfo(Context context){
        Map<String ,String > map = new HashMap<>();
        map.put(SharePerferenceUtil.sh_personal_info,JsonUtils.objToJson(GlobalInfoUtil.personalInfo));

        return SharePerferenceUtil.save_User_SP(context,map);
    }

    public static boolean getPersonalInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sh_user, Activity.MODE_PRIVATE);
        String json = sharedPreferences.getString(sh_personal_info,"");

        if(!json.isEmpty()){
            GlobalInfoUtil.personalInfo = (PersonalInfo)JsonUtils.jsonToObj(PersonalInfo.class,json);
            return true;
        }
        return false;
    }

}
