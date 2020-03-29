package lan.qxc.lightclient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DongtailVO implements Serializable {


    private Long userid;
    private String username;
    private String icon;

    private Long dtid;
    private String dtcontent;
    private String dtpic;


    //1997-11-13 12:35:22
    private String dtcreatetime;
    private String deviceinfo;

    //1代表我关注了他    2代表他关注了我   0代表好友
    private Integer guanzhu_type;

}
