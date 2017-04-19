package cn.incongress.endorcrinemagazine.base;

import android.app.Application;
import android.content.Context;



/**
 * Created by Jacky on 2017/4/6.
 * App入口，初始化位置
 */

public class BaseApplication extends Application {
    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();;
    }

    public static BaseApplication getContext() {
        return (BaseApplication) _context;
    }
}
