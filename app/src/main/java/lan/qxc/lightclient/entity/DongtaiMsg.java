package lan.qxc.lightclient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DongtaiMsg {

    private Long msgid;
    private Long dtid;
    private Long userid;

    private Byte msgtype;      //1点赞     2评论   3转发

    private String body;
    private Byte readstate;

    private String createtime;

    private String updatetime;

    private Byte is_deleted;


}
