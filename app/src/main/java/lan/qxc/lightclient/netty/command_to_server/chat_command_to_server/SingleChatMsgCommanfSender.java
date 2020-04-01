package lan.qxc.lightclient.netty.command_to_server.chat_command_to_server;

import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.command_to_server.CommandSender;
import lan.qxc.lightclient.netty.command_to_server.user_command_to_server.LoginCommandSender;
import lan.qxc.lightclient.netty.protocol.request.chat_request.SingleChatRequestPacket;
import lan.qxc.lightclient.netty.protocol.request.user_request.LoginRequestPacket;

public class SingleChatMsgCommanfSender extends CommandSender {

    private static class SingleChatMsgCommanfSenderHolder{
        private static SingleChatMsgCommanfSender instance = new SingleChatMsgCommanfSender();
    }

    public static SingleChatMsgCommanfSender getInstance(){
        return SingleChatMsgCommanfSenderHolder.instance;
    }

    public SingleChatMsgCommanfSender getPacket(SingleChatMsg singleChatMsg){
        packet = new SingleChatRequestPacket(singleChatMsg);
        return this;
    }

}
