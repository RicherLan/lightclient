package lan.qxc.lightclient.ui.activity.base_activitys;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static Context getLastActivity(){
        return activities.get(activities.size()-1);
    }

    public static void finishAll(){
        for (Activity activity:activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        // System.exit(0);
    }


}
