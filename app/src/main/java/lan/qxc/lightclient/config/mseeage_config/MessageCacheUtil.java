package lan.qxc.lightclient.config.mseeage_config;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.entity.message.Message;
import lan.qxc.lightclient.entity.message.MessageType;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;

/**
 *  消息队列
 */
public class MessageCacheUtil {


    //消息界面消息窗口   存储最新的消息    比如userid给我发来了消息  那么存储userid的最新的那一条
    public static List<Message> msgsFrames = new ArrayList<>();

    //单人聊天消息
    public static Map<Long,List<SingleChatMsg>> singleChatMsgMap = new HashMap<>();


    //好友请求信息
    public static List<FriendMsgVO> friendMsgs = new ArrayList<>();


    //发送给对方一条消息
    public static void sendSingleChatMsg(SingleChatMsg singleChatMsg){
        Long userid = singleChatMsg.getReceiveUid();

        //获得昵称和头像
        FriendVO friendVO = FriendCatcheUtil.userInfoMap.get(userid);
        if(friendVO!=null){
            singleChatMsg.setSendUername(friendVO.getUsername());
            singleChatMsg.setSendUicon(friendVO.getIcon());
        }

        if(singleChatMsgMap.containsKey(userid)){
            singleChatMsgMap.get(userid).add(singleChatMsg);
        }else{
            List<SingleChatMsg> list = new ArrayList<>();
            list.add(singleChatMsg);
            singleChatMsgMap.put(userid,list);
        }
        addMsgToFrame(singleChatMsg);
    }

    //收到了一条消息
    // 添加单人聊天消息至未读队列    最后刷新消息窗口
    public static void receiveSingleChatMsg(SingleChatMsg singleChatMsg){
        Long userid = singleChatMsg.getSendUid();
        if(singleChatMsgMap.containsKey(userid)){
            singleChatMsgMap.get(userid).add(singleChatMsg);
        }else{
            List<SingleChatMsg> list = new ArrayList<>();
            list.add(singleChatMsg);
            singleChatMsgMap.put(userid,list);
        }
        freshHomeMsgNum();
        addMsgToFrame(singleChatMsg);
    }

    //接收了 未读的单人聊天消息
    public static void receiveMsgNotRead(List<SingleChatMsg> singleChatMsgs){

        //都接受了谁的未读消息    最后要拿到这些用户的最新的消息加入到消息窗口
        Set<Long> userids = new LinkedHashSet<>();

        for (SingleChatMsg singleChatMsg : singleChatMsgs){
            Long userid = singleChatMsg.getSendUid();
            userids.add(userid);

            if(!singleChatMsgMap.containsKey(userid)){
                singleChatMsgMap.put(userid,new ArrayList<SingleChatMsg>());
            }
            SingleChatMsg del = null;
            for(SingleChatMsg msg : singleChatMsgMap.get(userid)){
                if(msg.getMsgid().equals(singleChatMsg.getMsgid())){
                    del=msg;
                }
            }
            if(del!=null){
                singleChatMsgMap.get(userid).remove(del);
            }
            singleChatMsgMap.get(userid).add(singleChatMsg);

        }

        freshHomeMsgNum();
        //添加到消息窗口
        for(Long userid : userids){
            if(singleChatMsgMap.containsKey(userid)&&singleChatMsgMap.get(userid).size()>0){
                addMsgToFrame(singleChatMsgMap.get(userid).get(singleChatMsgMap.get(userid).size()-1));
            }
        }

    }

    public static void setAllMsgHadRead(){
        //设置单人聊天消息已读
        for(Long userid :singleChatMsgMap.keySet() ){
            for(SingleChatMsg singleChatMsg : singleChatMsgMap.get(userid)){
                singleChatMsg.setReadstate(new Byte("1"));
            }
        }

        //设置好友请求信息已读
        for(FriendMsgVO friendMsgVO : friendMsgs){
            friendMsgVO.setReadstate(new Byte("1"));
        }
    }

    //设置单人聊天消息已读
    public static void setSingleChatMsgHadReadByUserid(Long userid){
        if(singleChatMsgMap.containsKey(userid)){
            for(SingleChatMsg singleChatMsg : singleChatMsgMap.get(userid)){
                singleChatMsg.setReadstate(new Byte("1"));
            }
        }

        freshHomeMsgNum();
    }

    //设置好友消息已读
    public static void setFriendMsgsHadReadByMsgid(Long msgid){
        for(FriendMsgVO friendMsgVO : friendMsgs){
            if(friendMsgVO.getId().equals(msgid)){
                friendMsgVO.setReadstate(new Byte("1"));
            }
        }
    }

    //获得自己和某用户的所有单人聊天消息
    public static List<SingleChatMsg> getSingleChatMsgListByUid(Long userid){
        if(singleChatMsgMap.containsKey(userid)){
            return singleChatMsgMap.get(userid);
        }else{
            singleChatMsgMap.put(userid,new ArrayList<>());
            return singleChatMsgMap.get(userid);
        }
    }

    //获得未读消息数量
    public static int getMsgNotReadNum(){
        int sum = 0;
        for(FriendMsgVO friendMsgVO : friendMsgs){
            if(friendMsgVO.getReadstate()==0){
                ++sum;
            }
        }

        for(Long userid : singleChatMsgMap.keySet()){
            List<SingleChatMsg> singleChatMsgs = singleChatMsgMap.get(userid);
            for(SingleChatMsg singleChatMsg : singleChatMsgs){
                if(singleChatMsg.getReadstate()==0){
                    ++sum;
                }else{
                    break;
                }
            }
        }

        return sum;
    }

    //获得关于某用户的聊天未读信息数量
    public static int getSingleChatMsgNotReadNumOfUid(Long uid){
        int sum = 0;
        if(singleChatMsgMap.containsKey(uid)){
            for(SingleChatMsg singleChatMsg : singleChatMsgMap.get(uid)){
                if(singleChatMsg.getReadstate()==0){
                    ++sum;
                }else{
                    break;
                }
            }
        }
        return sum;
    }

    //好友请求信息到来   刷新一下本地数据  最后刷新消息窗口
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

        for(FriendMsgVO msg : messages){
            if(msg.getMsgtype()!=2){
                friendMsgs.add(msg);
            }
        }

        for(FriendMsgVO message : messages){
            addMsgToFrame(message);
        }
        messages = null;

        freshHomeMsgNum();
    }

    //添加信息到窗口
    public static void addMsgToFrame(Message message){
        int type = message.getType();
        Long senduserid = new Long(-1);
        Long receiveuserid = new Long(-1);

        if(type== MessageType.FRIEND_MSG){
            FriendMsgVO friendMsgVO = (FriendMsgVO)message;
            senduserid = friendMsgVO.getUserid();
            receiveuserid = friendMsgVO.getTouserid();
//            boolean find = false;
            Message del = null;
            for(Message msg : msgsFrames){
                if(msg.getType()==type){
                    FriendMsgVO origmsg = (FriendMsgVO)msg;
                    if(origmsg.getUserid().equals(senduserid)){
                        del = msg;
//                        find = true;
                    }
                }
            }
            if(del!=null){
                msgsFrames.remove(del);
            }

            msgsFrames.add(message);


        }else if(type == MessageType.SINGLE_CHAT_MSG){
            SingleChatMsg singleChatMsg = (SingleChatMsg)message;
            senduserid = singleChatMsg.getSendUid();
            receiveuserid = singleChatMsg.getReceiveUid();
            //对方的id
            Long userid = senduserid;
            if(senduserid==GlobalInfoUtil.personalInfo.getUserid()){
                userid = receiveuserid;
            }
//            boolean find = false;
            Message del = null;
            for(Message msg : msgsFrames){
                if(msg.getType()==type){
                    SingleChatMsg origmsg = (SingleChatMsg)msg;
                    Long oriUid = origmsg.getSendUid();
                    if(oriUid.equals(GlobalInfoUtil.personalInfo.getUserid())){
                        oriUid = origmsg.getReceiveUid();
                    }

                    if(userid.equals(oriUid)){
                        del = msg;
//                        find = true;
                    }
                }
            }

            if(del!=null){
                msgsFrames.remove(del);
            }

            msgsFrames.add(message);

        }

        sortMsgFrames();

    }

    public static void freshMsgFrames(){
        msgsFrames.clear();
        msgsFrames.addAll(friendMsgs);
        sortMsgFrames();
    }

    public static void sortMsgFrames(){

        Collections.sort(msgsFrames, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {

                String time1 = o1.getTime();
                String time2 = o2.getTime();

                return (time1.compareTo(time2)<0) ? 1:-1;
            }
        });

    }


    //更新首界面底部信息数量
    public static void freshHomeMsgNum(){
        HomeActivity.freshMsgNum();
    }


}
