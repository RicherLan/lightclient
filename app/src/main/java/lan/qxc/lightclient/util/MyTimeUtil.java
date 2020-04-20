package lan.qxc.lightclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTimeUtil {

    public static void main(String[] args) {

        System.out.println(date.toString());
    }


    public static Date getDateByStr(String time,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateByTime(int year,int month,int day){
        return new Date(year-1900,month-1,day);
    }


    public static String getTimeByTimeStamp(String timestamp,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(timestamp)));
    }

    public static String getTimestrByDate(Date date,String timeformat){
        SimpleDateFormat sdf =new SimpleDateFormat(timeformat );
        return sdf.format(date);
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



    // 把时间自动转成今天、昨天、前天，如果是同一年刚显示月日，不同年的把年也显示
    public static String getTime(Date date) {
        boolean sameYear = false;
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天";
        String beforYesterDaySDF = "前天";
        String otherSDF = "MM-dd HH:mm";
        String otherYearSDF = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }

        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                sfd = new SimpleDateFormat(todySDF);
                time = yesterDaySDF + " " + sfd.format(date);

                //time = yesterDaySDF;
                return time;
            }
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天

                sfd = new SimpleDateFormat(todySDF);
                time = beforYesterDaySDF + " " + sfd.format(date);
                //time = beforYesterDaySDF;
                return time;
            }
        }

        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }


}
