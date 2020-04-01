package lan.qxc.lightclient.netty.command_to_server;

import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.command_to_server.chat_command_to_server.SingleChatMsgCommanfSender;
import lan.qxc.lightclient.netty.command_to_server.user_command_to_server.LoginCommandSender;
import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.util.FixedThreadPool;

public class SendToServer {

    public static void login(String phone,String pass){
        FixedThreadPool.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                LoginCommandSender
                        .getInstance()
                        .getPacket(phone, pass)
                        .execute(NettyClient.channel);
            }
        });
    }

    public static void singleChatMsg(SingleChatMsg singleChatMsg){
        FixedThreadPool.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                SingleChatMsgCommanfSender
                        .getInstance()
                        .getPacket(singleChatMsg)
                        .execute(NettyClient.channel);
            }
        });
    }


}
