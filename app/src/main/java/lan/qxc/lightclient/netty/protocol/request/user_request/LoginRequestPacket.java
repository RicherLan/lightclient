package lan.qxc.lightclient.netty.protocol.request.user_request;

import lan.qxc.lightclient.netty.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.LOGIN_REQUEST;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestPacket extends Packet {

    String phone;
    String password;

    @Override
    public int getCommand() {
        return LOGIN_REQUEST;
    }

}
