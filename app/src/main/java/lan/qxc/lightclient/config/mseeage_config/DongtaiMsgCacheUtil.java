package lan.qxc.lightclient.config.mseeage_config;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtaiMsg;
import lan.qxc.lightclient.entity.DongtaiMsgVO;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;

public class DongtaiMsgCacheUtil {

    public static int msgNotReadNum = 0;

    public static List<DongtaiMsgVO> dongtaiMsgList = new ArrayList<>();
    public static List<DongtailVO> dongtaiList = new ArrayList<>();


    public static void setMsgNotReadNum(int num){
        msgNotReadNum = num;
    }

    public static int getMsgNotRead(){

        return msgNotReadNum;

    }


    public static void addDongtaiMsg(DongtailVO dongtai,DongtaiMsgVO dongtaiMsg){
        dongtaiMsgList.add(dongtaiMsg);
        dongtaiList.add(dongtai);
    }


    public static void setMsgHadRead(){

        dongtaiMsgList.clear();
        dongtaiList.clear();

//        if(dongtaiMsgNotReadList.size()!=0){
//
//            dongtaiMsgHadReadList.addAll(0,dongtaiMsgNotReadList);
//            dongtaiHadReadList.addAll(0,dongtaiNotReadList);
//
//            dongtaiMsgNotReadList.clear();
//            dongtaiNotReadList.clear();
//
//        }

    }





}
