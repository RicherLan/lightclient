package lan.qxc.lightclient.retrofit_util.api;

import android.view.LayoutInflater;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class APIUtil {

    //获得请求url
    public static String getUrl(String path){
        return BaseAPI.BASE_URL+ "/"+path;
    }


    public static List<String> SLUrlListToOrienlUrlList(List<String> list){


        for(int i=0;i<list.size();++i){
            list.set(i,SLUrlToOrienlUrl(list.get(i)));
        }
        return list;
    }

    //判断某图片是不是缩略图
    public static boolean isSLUrl(String path){

        if(path.contains("_thumbnail")){
            return true;
        }
        return false;
    }

    //获得缩略图url
    public static String getSLUrl(String path){

        if(isSLUrl(path)){
            return path;
        }

        String suffix = path.substring(path.lastIndexOf(".")+1);
        String filenameNotSuffix = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));
        String newfilname = filenameNotSuffix+"_thumbnail."+suffix;

        String dir = path.substring(1,path.lastIndexOf("/"));
        String newdir = dir.substring(dir.lastIndexOf("/")+1)+"_sl";

        String newdirname = dir.substring(0,dir.lastIndexOf("/"))+"/"+newdir;
        return  getUrl(newdirname+"/"+newfilname);

    }

    //缩略图转原图
    public static String SLUrlToOrienlUrl(String slpath){

        if(!isSLUrl(slpath)){
            return slpath;
        }

        String suffix = slpath.substring(slpath.lastIndexOf(".")+1);
        String filenameNotSuffix = slpath.substring(slpath.lastIndexOf("/")+1,slpath.lastIndexOf("."));
        String newFilename = filenameNotSuffix.substring(0,filenameNotSuffix.lastIndexOf("_"))+"."+suffix;


        String dir = slpath.substring(0,slpath.lastIndexOf("/"));
        String dir1 = dir.substring(dir.lastIndexOf("/")+1);
        String dir2 = dir1.substring(0,dir1.lastIndexOf("_"));

        String newdirname = dir.substring(0,dir.lastIndexOf("/"))+"/"+dir2;

        return newdirname+"/"+newFilename;
    }



    public static void main(String[] args) {


        String path = "/uploadfile/dongtai_ic/3.jpg /uploadfile/dongtai_ic/4.jpg /uploadfile/dongtai_ic/5.jpg " +
                "/uploadfile/dongtai_ic/6.jpg /uploadfile/dongtai_ic/7.jpg /uploadfile/dongtai_ic/8.jpg " +
                "/uploadfile/dongtai_ic/9.jpg /uploadfile/dongtai_ic/10.jpg /uploadfile/dongtai_ic/11.jpg ";

        List<String > sllist = new ArrayList<>();
        String[] ss = path.split(" ");

        for(String  s : ss){
            sllist.add(getSLUrl(s));
        }
        for(int i=0;i<sllist.size();++i){
            System.out.println(sllist.get(i));
        }
        System.out.println("111111111111111111111111111111111");
        SLUrlListToOrienlUrlList(sllist);

        for(int i=0;i<sllist.size();++i){
            System.out.println(sllist.get(i));
        }

    }

}
