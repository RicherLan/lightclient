package lan.qxc.lightclient.config.dt_config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtailVO;

public class Dongtai_catch_util {

    public static List<DongtailVO> tjDongtailVOS = new ArrayList<>();
    public static List<DongtailVO> gzDongtailVOS = new ArrayList<>();

    //存储用户的动态   key为用户id
    public static Map<Long,List<DongtailVO>> dtMap = new HashMap<>();

    public static void updateNewTJDTList(Long userid,List<DongtailVO> list){
        if(dtMap.containsKey(userid)){
            updateNewTJDTList(dtMap.get(userid),list);
        }else{
            dtMap.put(userid,list);
        }
    }

    public static void updateOldTJDTList(Long userid,List<DongtailVO> list){
        if(dtMap.containsKey(userid)){
            updateOldTJDTList(dtMap.get(userid),list);
        }else{
            dtMap.put(userid,list);
        }
    }

    //请求服务器新的动态列表
    public static void updateNewTJDTList(List<DongtailVO> orignList,List<DongtailVO> list){

        if(orignList==null){
            orignList = new ArrayList<DongtailVO>();
        }
        //更新头像
        for(DongtailVO newdt : list){
            for(DongtailVO  dongtailVO : orignList){
                if(dongtailVO.getDtid()==newdt.getDtid()){
                    dongtailVO.setIcon(newdt.getIcon());
                }
            }
        }

        List<DongtailVO> del = new ArrayList<>();
        for(DongtailVO  dongtailVO : orignList){

            for(DongtailVO newdt : list){
                if(newdt.getDtid()==dongtailVO.getDtid()){
                    del.add(dongtailVO);
                }
            }
        }

        for(DongtailVO dongtailVO : del){
            orignList.remove(dongtailVO);
        }
        del = null;

        orignList.addAll(list);

        sortDtvos(orignList);
    }

    //请求服务器旧的动态列表
    public static void updateOldTJDTList(List<DongtailVO> orignList,List<DongtailVO> list){

        //更新头像
        for(DongtailVO newdt : list){
            for(DongtailVO  dongtailVO : orignList){
                if(dongtailVO.getDtid()==newdt.getDtid()){
                    dongtailVO.setIcon(newdt.getIcon());
                }
            }
        }

        List<DongtailVO> del = new ArrayList<>();
        for(DongtailVO  dongtailVO : orignList){

            for(DongtailVO newdt : list){
                if(newdt.getDtid()==dongtailVO.getDtid()){
                    del.add(dongtailVO);
                }
            }
        }

        for(DongtailVO dongtailVO : del){
            orignList.remove(dongtailVO);
        }
        del = null;

        orignList.addAll(list);

        sortDtvos(orignList);
    }



    //排序  按照dtid从大到小排序    实质上就等于按照动态发布时间排序了
    public static void sortDtvos(List<DongtailVO> list){

        Collections.sort(list, new Comparator<DongtailVO>() {
            @Override
            public int compare(DongtailVO o1, DongtailVO o2) {
                return o1.getDtid()<o2.getDtid()?1:-1;
            }
        });

    }


}
