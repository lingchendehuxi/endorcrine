package cn.incongress.endorcrinemagazine.base;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UmengTool;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.utils.GlideImageLoader;


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
        PlatformConfig.setWeixin("wx5847e4cb0685bfbc", "9165983519c2f2236ae02cbdbab9fc36");
//        UmengTool.checkWx(this);

        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(R.color.theme_blue)
                .setTitleBarTextColor(R.color.white)
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(false)
                .setEnableCrop(false)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(9)
                .build();

        //配置imageloader
        GlideImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme).setFunctionConfig(functionConfig).build();

        //相册配置
        GalleryFinal.init(coreConfig);

        MobclickAgent.openActivityDurationTrack(false);
    }

    public static BaseApplication getContext() {
        return (BaseApplication) _context;
    }
}
