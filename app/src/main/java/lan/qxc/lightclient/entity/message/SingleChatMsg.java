package lan.qxc.lightclient.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lan.qxc.lightclient.entity.message.MessageType.SINGLE_CHAT_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleChatMsg extends Message{

    private Long msgid;

    private Long sendUid;
    private String sendUername;
    private String sendUicon;


    private Long receiveUid;

    private Byte msgtype;        //2:文字  3:图片 4:语音

    private String textbody;
    private String picbody;
    private String voicebody;


    private String createtime;


    private String updatetime;


    private Byte readstate;     //读取状态  0 未读   1已读
    private Byte is_deleted;

    @Override
    public int getType() {
        return SINGLE_CHAT_MSG;
    }


    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.setTime(this.createtime);
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
        this.setTime(updatetime);
    }
}
