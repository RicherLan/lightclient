package lan.qxc.lightclient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendVO {


    private Long userid;
    private String username;
    private Byte sex;
    private String icon;

    private String birthday;

    private String introduce;
    private String location;
    private String hometown;
    private String job;


    private String remark;
    private Byte is_blacked;


    //1代表我关注了他    2代表他关注了我   0代表好友
    private Integer guanzhu_type;

}
