package cn.incongress.endorcrinemagazine.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 2015/8/26.
 */
public class ActivityUtils {
    public static List<Activity> activitys = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activitys.remove(activity);
    }

    public static void finishAll() {
        for(Activity activity : activitys) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 启动某个Activity
     * @param context
     * @param toActivityClass
     */
    public static void startActivity(Context context, Class toActivityClass) {
        Intent intent = new Intent(context, toActivityClass);
        context.startActivity(intent);
    }
}
