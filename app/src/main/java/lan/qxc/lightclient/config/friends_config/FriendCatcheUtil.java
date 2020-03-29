package lan.qxc.lightclient.config.friends_config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lan.qxc.lightclient.entity.FriendVO;

/*

 */
public class FriendCatcheUtil {

    //存储平时缓存的用户的信息
    public static Map<Long,FriendVO> userInfoMap = new HashMap<>();

    public static List<FriendVO> guanzhuList = new ArrayList<>();
    public static List<FriendVO> friendList = new ArrayList<>();
    public static List<FriendVO> fensiList = new ArrayList<>();

    //我关注了某用户
    //如果是我的粉丝  那么成为我的好友
    public static void meGuanzhuUser(Long userid){
        for(FriendVO friendVO : fensiList){
            if(friendVO.getUserid()==userid){
                friendVO.setGuanzhu_type(0);
                friendList.add(friendVO);
            }
        }
    }


    //我取关了某用户
    //更新我的关注列表和好友列表
    public static void meDelGuanzhuUser(Long userid){
        FriendVO del = null;
        for(FriendVO friendVO : guanzhuList){
            if(friendVO.getUserid()==userid){
                del = friendVO;
            }
        }

        if(del!=null){
            guanzhuList.remove(del);
        }

        del = null;
        for(FriendVO friendVO : friendList){
            if(friendVO.getUserid()==userid){
                del = friendVO;
            }
        }
        if(del!=null){
            friendList.remove(del);
        }

    }


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


    public static void updateUserInfoMap(){
        for(FriendVO friendVO : fensiList){
            userInfoMap.put(friendVO.getUserid(),friendVO);
        }
        for(FriendVO friendVO : guanzhuList){
            userInfoMap.put(friendVO.getUserid(),friendVO);
        }
        for(FriendVO friendVO : friendList){
            userInfoMap.put(friendVO.getUserid(),friendVO);
        }
    }


}
