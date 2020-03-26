package lan.qxc.lightclient.service;

import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.retrofit_util.RetrofitHelper;
import lan.qxc.lightclient.retrofit_util.service.DongtaiRequestService;
import lan.qxc.lightclient.retrofit_util.service.GuanzhuRequestService;
import retrofit2.Call;

public class GuanzhuService {

    private static class  GuanzhuServiceHolder{
        static GuanzhuService instance = new GuanzhuService();
    }

    public static GuanzhuService getInstance(){
        return GuanzhuService.GuanzhuServiceHolder.instance;
    }

    public Call<Result> guanzhu(Long userid,Long gzuid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.guanzhu(userid,gzuid);
    }

    public Call<Result> del_guanzhu(Long userid,Long gzuid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.del_guanzhu(userid,gzuid);
    }

    public Call<Result> updateRemarkName(Long userid,Long gzuid,String remark){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.updateRemarkName(userid,gzuid,remark);
    }


    /**
     * 获得我的关注
     * @param userid
     * @return
     */
    public Call<Result> getMyGuanzhu(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getMyGuanzhu(userid);
    }

    /**
     * 获得谁关注了我  级我的粉丝
     * @param userid
     * @return
     */
    public Call<Result> getUsersGuanzhuMe(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getUsersGuanzhuMe(userid);
    }

    /**
     * 获得我的好友列表
     * @param userid
     * @return
     */
    public Call<Result> getFriendsByUserid(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getFriendsByUserid(userid);
    }

    /**
     * 获得我的关注的数量
     * @param userid
     * @return
     */
    public Call<Result> getMyGuanzhuNum(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getMyGuanzhuNum(userid);
    }


    /**
     * 获得关注我的数量
     * @param userid
     * @return
     */
    public Call<Result> getGuanzhuMeNum(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getGuanzhuMeNum(userid);
    }

    /**
     * 获得我的好友的数量
     * @param userid
     * @return
     */
    public Call<Result> getMyFriendNum(Long userid){
        GuanzhuRequestService service = RetrofitHelper.getInstance().create(GuanzhuRequestService.class);
        return service.getMyFriendNum(userid);
    }

}
