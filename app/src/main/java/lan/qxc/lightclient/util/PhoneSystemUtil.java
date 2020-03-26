package lan.qxc.lightclient.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

/*
    手机系统信息
 */
public class PhoneSystemUtil {

    //语言
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    //系统版本号
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    //手机型号
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    //手机厂商
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }



}
