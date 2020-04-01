package lan.qxc.lightclient.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.entity.message.MessageType.SINGLE_CHAT_TEXT_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextSingleChatMessage extends Message {


    private Long sendUid;
    private String sendUername;
    private String sendUicon;
    private Byte sex;

    private Long receiveUid;

    private String textbody;
    private String createtime;

    @Override
    public int getType() {
        return SINGLE_CHAT_TEXT_MSG;
    }
}
