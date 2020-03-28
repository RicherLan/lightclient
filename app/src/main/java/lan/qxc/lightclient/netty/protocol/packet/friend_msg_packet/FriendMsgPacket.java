package lan.qxc.lightclient.netty.protocol.packet.friend_msg_packet;

import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.netty.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.Friend_GUANZHU_MSG;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendMsgPacket extends Packet {

    FriendMsgVO friendMsgVO;

    @Override
    public int getCommand() {
        return Friend_GUANZHU_MSG;
    }

}
