package lan.qxc.lightclient.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static lan.qxc.lightclient.entity.message.MessageType.FRIEND_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendMsgVO extends Message implements Serializable {

    private Long id;
    private Long userid;

    private String username;
    private String sex;
    private String icon;
    private String introduce;

    private Long touserid;

    private Byte msgtype;
    private String info;
    private Byte readstate;

    private String createtime;

    @Override
    public int getType() {
        return FRIEND_MSG;
    }

}
