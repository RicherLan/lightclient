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
public class Dongtai implements Serializable {

    private Long dtid;
    private Long userid;

    private String dtcontent;
    private String dtpic;


    //1997-11-13 12:35:22
    private String dtcreatetime;

    private String deviceinfo;

    private Byte is_deleted;
    private Byte is_locked;


}
