package lan.qxc.lightclient.netty.protocol.request.chat_request;

import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.SINGLE_CHAT_MSG_REQUEST;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleChatRequestPacket extends Packet {

    SingleChatMsg singleChatMsg;

    @Override
    public int getCommand() {
        return SINGLE_CHAT_MSG_REQUEST;
    }
}
