package lan.qxc.lightclient.config.mseeage_config;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtaiMsg;

public class DongtaiMsgCacheUtil {

    private static List<DongtaiMsg> dongtaiMsgNotReadList = new ArrayList<>();
    private static List<Dongtai> dongtaiNotReadList = new ArrayList<>();


    private static List<DongtaiMsg> dongtaiMsgHadReadList = new ArrayList<>();
    private static List<Dongtai> dongtaiHadReadList = new ArrayList<>();


    public static int getMsgNotRead(){

        return dongtaiMsgNotReadList.size();

    }


    public static void addDongtaiMsg(Dongtai dongtai,DongtaiMsg dongtaiMsg){
        dongtaiMsgNotReadList.add(dongtaiMsg);
        dongtaiNotReadList.add(dongtai);
    }


    public static void setMsgHadRead(){

        if(dongtaiMsgNotReadList.size()!=0){

            dongtaiMsgHadReadList.addAll(0,dongtaiMsgNotReadList);
            dongtaiHadReadList.addAll(0,dongtaiNotReadList);

            dongtaiMsgNotReadList.clear();
            dongtaiNotReadList.clear();

        }

    }





}
