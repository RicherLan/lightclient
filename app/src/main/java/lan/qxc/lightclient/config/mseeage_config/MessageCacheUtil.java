package lan.qxc.lightclient.config.mseeage_config;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.entity.message.Message;

/**
 *  消息队列
 */
public class MessageCacheUtil {

    //所有的消息放在里面
    public static List<Message> messages = new ArrayList<>();

    //好友请求信息
    public static List<FriendMsgVO> friendMsgs = new ArrayList<>();

    //
    public static void updateFriendMsgs(List<FriendMsgVO> messages){

        //首先更新  同一用户的同一类型请求的  只保留最新的
        //取关的话都要删除
        List<FriendMsgVO> del = new ArrayList<>();
        for(FriendMsgVO friendMsgVO : friendMsgs){

            for(FriendMsgVO message : messages){
                //取关
                if(message.getMsgtype()==2&&friendMsgVO.getUserid()==message.getUserid()
                        &&(friendMsgVO.getMsgtype()==1||friendMsgVO.getMsgtype()==2)){
                    del.add(friendMsgVO);
                    break;
                }

                if(friendMsgVO.getMsgtype()==message.getMsgtype()&&friendMsgVO.getUserid()==message.getUserid()){
                    del.add(friendMsgVO);
                    break;
                }
            }

        }

        for(FriendMsgVO friendMsgVO : del){
            friendMsgs.remove(friendMsgVO);
        }
        del = null;

        friendMsgs.addAll(messages);

        freshMessages();
    }

    public static void freshMessages(){
        messages.clear();
        messages.addAll(friendMsgs);
        sortMessageList();
    }

    public static void sortMessageList(){

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {

                String time1 = o1.getTime();
                String time2 = o2.getTime();

                return (time1.compareTo(time2)<0) ? 1:-1;
            }
        });

    }


}
