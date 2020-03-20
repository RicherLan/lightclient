package lan.qxc.lightclient.retrofit_util;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RetrofitTools {

    public static MultipartBody.Part getMulPart(File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return part;
    }

}
