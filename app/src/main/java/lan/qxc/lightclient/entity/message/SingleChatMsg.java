package lan.qxc.lightclient.entity.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SingleChatMsg {

    private Long msgid;

    private Long sendUid;
    private String sendUername;
    private String sendUicon;


    private Long receiveUid;

    private Byte msgtype;        //2:文字  3:图片 4:语音

    private String textbody;
    private String picbody;
    private String voicebody;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date updatetime;


    private Byte readstate;     //读取状态  0 未读   1已读
    private Byte is_deleted;

}
