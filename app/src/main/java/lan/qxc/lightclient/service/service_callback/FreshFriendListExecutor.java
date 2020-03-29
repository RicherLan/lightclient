package lan.qxc.lightclient.service.service_callback;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.GuanzhuService;
import lan.qxc.lightclient.ui.fragment.friend_menu.FensiMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.FriendMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.GuanzhuMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.home.ContactFragment;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreshFriendListExecutor {

    private static class FreshFriendListExecutorHolder{
        private static FreshFriendListExecutor instance = new FreshFriendListExecutor();
    }

    public static FreshFriendListExecutor getInstance(){
        return FreshFriendListExecutor.FreshFriendListExecutorHolder.instance;
    }

    public interface FreshListListener {
        void getResult(String message);
    }


    //刷新我的关注列表
    public  void freshGuanzhuListData(FreshListListener listListener){
        Call<Result> call = GuanzhuService.getInstance().getMyGuanzhu(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if(result==null){
                    listListener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.guanzhuList.clear();
                    FriendCatcheUtil.guanzhuList.addAll(friendVOList);

                }else{
                }
                listListener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listListener.getResult("error");
            }
        });

    }


    //刷新我的好友列表
    public  void freshFriendListData(FreshListListener listListener){
        Call<Result> call = GuanzhuService.getInstance().getFriendsByUserid(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result==null){
                    listListener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.friendList.clear();
                    FriendCatcheUtil.friendList.addAll(friendVOList);

                }else{
                }
                listListener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listListener.getResult("error");
            }
        });

    }

    //刷新我的粉丝列表
    public  void freshFensiListData(FreshListListener listListener){

        Call<Result> call = GuanzhuService.getInstance().getUsersGuanzhuMe(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if(result==null){
                    listListener.getResult("error");
                    return;
                }
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.fensiList.clear();
                    FriendCatcheUtil.fensiList.addAll(friendVOList);

                }else{
                }
                listListener.getResult(message);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listListener.getResult("error");
            }
        });

    }



}
