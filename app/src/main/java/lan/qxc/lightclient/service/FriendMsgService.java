package lan.qxc.lightclient.service;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.FriendMsgRequestService;
import lan.qxc.lightclient.retrofit_util.service.GuanzhuRequestService;
import retrofit2.Call;

public class FriendMsgService {


    private static class  FriendMsgServiceHolder{
        static FriendMsgService instance = new FriendMsgService();
    }

    public static FriendMsgService getInstance(){
        return FriendMsgService.FriendMsgServiceHolder.instance;
    }

    public Call<Result> setMsgHadRead(Long userid){
        FriendMsgRequestService service = RetrofitHelper.getInstance().create(FriendMsgRequestService.class);
        return service.setMsgHadRead(userid);
    }

    public Call<Result> getUserFriendMsgNotRead(Long userid){
        FriendMsgRequestService service = RetrofitHelper.getInstance().create(FriendMsgRequestService.class);
        return service.getUserFriendMsgNotRead(userid);
    }


}
