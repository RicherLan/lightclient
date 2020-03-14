package lan.qxc.lightclient.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/*
    动态权限申请
 */
public class PermissionUtil {

    public boolean requestPermission(Activity activity,final int requestCode,String permissionName){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionCheck = ContextCompat.checkSelfPermission(activity,permissionName);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                return true;
            }
            String[] strings = new String[]{permissionName};
            ActivityCompat.requestPermissions(activity,strings,requestCode);
            return false;
        }

        return true;
    }

    public boolean requestPermission(Activity activity,final int requestCode,String[] permissionNames){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(permissionNames.length>0){
                ActivityCompat.requestPermissions(activity,permissionNames,requestCode);
            }
            return false;
        }

        return true;
    }



    /*
        用法  在activity中

    void requestPer(){
       // requestPermissions();
        String[] strings = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
         new PermissionUtil().requestPermission(this,1, strings);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(LoginActivity.this,"拒绝权限无法使用",Toast.LENGTH_SHORT).show();
                        }else {
                            int i=0;
                        }
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
        }
    }
s
     */

}
