package cn.incongress.endorcrinemagazine.base;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;


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

        /** 微信分享初始化 **/
        PlatformConfig.setWeixin("wx959030923745168b", "b01a343ebccbf1107e80c36e098f032c");
    }

    public static BaseApplication getContext() {
        return (BaseApplication) _context;
    }
}
