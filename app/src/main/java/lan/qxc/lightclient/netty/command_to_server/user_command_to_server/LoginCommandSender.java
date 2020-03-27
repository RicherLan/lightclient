package lan.qxc.lightclient.netty.command_to_server.user_command_to_server;

import lan.qxc.lightclient.netty.command_to_server.CommandSender;
import lan.qxc.lightclient.netty.protocol.request.user_request.LoginRequestPacket;

public class LoginCommandSender extends CommandSender {

    private static class LoginCommandSenderHolder{
        private static LoginCommandSender instance = new LoginCommandSender();
    }

    public static LoginCommandSender getInstance(){
        return LoginCommandSenderHolder.instance;
    }

    public LoginCommandSender getPacket(String phonenumber,String password){
        packet = new LoginRequestPacket(phonenumber,password);
        return this;
    }



}
