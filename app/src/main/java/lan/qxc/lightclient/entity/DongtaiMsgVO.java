package lan.qxc.lightclient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongtaiMsgVO {
    private Long userid;
    private String username;
    private String icon;

    private Long msgid;
    private Long dtid;

    private Byte msgtype;      //1点赞     2评论   3转发

    private String body;
    private Byte readstate;


    private String createtime;


    private String updatetime;

    private Byte is_deleted;

}
