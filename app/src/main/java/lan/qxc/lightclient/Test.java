package lan.qxc.lightclient;

import lan.qxc.lightclient.result.Result;

import lan.qxc.lightclient.service.GuanzhuService;

import lan.qxc.lightclient.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test {



    public static void main(String[] args) {


            Call<Result> call = UserService.getInstance().login("15054190193","123");
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
