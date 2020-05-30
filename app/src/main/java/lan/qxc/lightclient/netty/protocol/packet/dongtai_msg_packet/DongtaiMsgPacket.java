package lan.qxc.lightclient.netty.protocol.packet.dongtai_msg_packet;


import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtaiMsg;
import lan.qxc.lightclient.netty.protocol.Packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.DONGTAI_MSG_PACKET;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongtaiMsgPacket extends Packet {

    Dongtai dongtai;
    DongtaiMsg dongtaiMsg;

    @Override
    public int getCommand() {
        return DONGTAI_MSG_PACKET;
    }

}
