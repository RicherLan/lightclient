package lan.qxc.lightclient.netty.command_to_server;

import io.netty.channel.Channel;
import lan.qxc.lightclient.netty.protocol.Packet;

public class CommandSender {

    public Packet packet;
    public void execute(Channel channel){
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(packet);
    }

}
