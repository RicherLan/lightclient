package lan.qxc.lightclient.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTimeUtil {

    public static void main(String[] args) {

        System.out.println(date.toString());
    }


    public static Date getDateByTime(int year,int month,int day){
        return new Date(year-1900,month-1,day);
    }

    public static String getTimestrByDate(Date date,String timeformat){
        SimpleDateFormat sdf =new SimpleDateFormat(timeformat );
        return sdf.format(GlobalInfoUtil.personalInfo.getBirthday());
    }


    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }

    public static int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DATE);
    }


    private static  String[][] constellations = {{"摩羯座", "水瓶座"}, {"水瓶座", "双鱼座"}, {"双鱼座", "白羊座"}, {"白羊座", "金牛座"}, {"金牛座", "双子座"}, {"双子座", "巨蟹座"}, {"巨蟹座", "狮子座"},
            {"狮子座", "处女座"}, {"处女座", "天秤座"}, {"天秤座", "天蝎座"}, {"天蝎座", "射手座"}, {"射手座", "摩羯座"}};
    //星座分割时间
    private static int[] date = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};

    //星座   1997-11-13
    public static String getStarByTime(String time) {
        if(time==null||time.isEmpty()){
            return null;
        }
        String[] data = time.split("-");
        if(data.length!=3){
            return null;
        }
        int day = date[Integer.parseInt(data[1]) - 1];
        String[] cl1 = constellations[Integer.parseInt(data[1]) - 1];
        if (Integer.parseInt(data[2]) >= day) {
            return (cl1[1]);
        } else {
            return (cl1[0]);
        }
    }

}
