package lan.qxc.lightclient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guanzhu implements Serializable {

    private Long gzid;
    private Long userid;
    private Long gzuid;

    private String createtime;

    private String remarkname;
    private Byte is_deleted;
    private Byte is_blacked;



}
