package lan.qxc.lightclient.config.friends_config;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.entity.FriendVO;

/*

 */
public class FriendCatcheUtil {

    public static List<FriendVO> guanzhuList = new ArrayList<>();
    public static List<FriendVO> friendList = new ArrayList<>();
    public static List<FriendVO> fensiList = new ArrayList<>();


    //用户取关了我
    public static void userDelGuanzhu(Long userid){
        List<FriendVO> del = new ArrayList<>();
        for(FriendVO friendVO : friendList){
            if(friendVO.getUserid()==userid){
                del.add(friendVO);
            }
        }

        for(FriendVO friendVO : del){
            friendList.remove(friendVO);
        }


        del.clear();
        for(FriendVO friendVO : fensiList){
            if(friendVO.getUserid()==userid){
                del.add(friendVO);
            }
        }

        for(FriendVO friendVO : del){
            fensiList.remove(friendVO);
        }

    }


}
