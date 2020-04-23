package lan.qxc.lightclient.service;

import android.widget.Toast;

import lan.qxc.lightclient.service.service_callback.FreshFriendListExecutor;
import lan.qxc.lightclient.service.service_callback.FriendMsgExecutor;
import lan.qxc.lightclient.service.service_callback.SingleChatMsgExecutor;
import lan.qxc.lightclient.ui.fragment.friend_menu.FensiMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.FriendMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.GuanzhuMenuContactFragment;

public class RequestService {

    public static void loginOkRequestMsg(){
        //请求单人聊天未读信息
        SingleChatMsgExecutor.getInstance().getSingleChatMsgNotReadOfUid(new SingleChatMsgExecutor.SingleChatMsgListener() {
            @Override
            public void getResult(String message) {
//                if(message.equals("SUCCESS")){
//
//                }else{
//
//                }
            }
        });

        //请求好友请求信息
        FriendMsgExecutor.getInstance().getUserFriendMsgNotRead(new FriendMsgExecutor.FriendMsgListener() {
            @Override
            public void getResult(String message) {

//                if(message.equals("SUCCESS")){
//
//                }else{
//
//                }
            }
        });

        //刷新好友信息
        FreshFriendListExecutor.getInstance().freshGuanzhuListData(new FreshFriendListExecutor.FreshListListener() {
            @Override
            public void getResult(String message) {
            }
        });
        FreshFriendListExecutor.getInstance().freshFriendListData(new FreshFriendListExecutor.FreshListListener() {
            @Override
            public void getResult(String message) {
            }
        });
        FreshFriendListExecutor.getInstance().freshFensiListData(new FreshFriendListExecutor.FreshListListener() {
            @Override
            public void getResult(String message) {
            }
        });


    }

}
