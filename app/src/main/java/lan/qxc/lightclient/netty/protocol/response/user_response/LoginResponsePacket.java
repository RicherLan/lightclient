package lan.qxc.lightclient.netty.protocol.response.user_response;

import lan.qxc.lightclient.entity.PersonalInfo;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.netty.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.netty.protocol.command.Command.LOGIN_RESPONSE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponsePacket extends Packet {

    private String rs;
    private String type;
    private PersonalInfo personalInfo;

    @Override
    public int getCommand() {
        return LOGIN_RESPONSE;
    }

}
