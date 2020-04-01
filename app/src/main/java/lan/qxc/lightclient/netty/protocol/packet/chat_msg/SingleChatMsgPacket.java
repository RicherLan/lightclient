package lan.qxc.lightclient.netty.protocol.packet.chat_msg;

import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.protocol.Packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.SINGLE_CHAT_MSG_PACKET;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleChatMsgPacket extends Packet {

    SingleChatMsg singleChatMsg;

    @Override
    public int getCommand() {
        return SINGLE_CHAT_MSG_PACKET;
    }
}
