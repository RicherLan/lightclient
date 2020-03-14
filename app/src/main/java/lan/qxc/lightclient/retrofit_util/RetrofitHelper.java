package lan.qxc.lightclient.retrofit_util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import lan.qxc.lightclient.retrofit_util.api.BaseAPI;
import okhttp3.*;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final int TIME_OUT = 5;
    private static final int READ_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 10;

    private final Retrofit retrofit;
    private String body;


    private RetrofitHelper(){

        Interceptor tokenRespInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if(isTokenExpired(response)){
//                    String newToken = getNewToken();
//                    if(newToken!=null){
//                        Request newRequest = chain.request()
//                                .newBuilder()
//                                .header("Authorization",   newToken)
//                                .build();
//                        return chain.proceed(newRequest);
//                    }
                }
                return response.newBuilder().body(ResponseBody.create(MediaType.parse("UTF-8"),body)).build();
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addNetworkInterceptor(tokenInterceptor)  //添加网络拦截器
//                .addInterceptor(tokenRespInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BaseAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory()   //添加适配器
                .build();
    }

    private String getNewToken(){
        return "";
    }

    private boolean isTokenExpired(Response response){

        try {
            body = response.body().string();
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    private static class RetrofitHolder{
        private static final RetrofitHelper retrofit = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance(){
        return RetrofitHolder.retrofit;
    }


    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }


}
