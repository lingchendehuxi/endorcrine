package cn.incongress.endorcrinemagazine.utils;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.incongress.endorcrinemagazine.R;

/**
 * Created by Jacky on 2017/1/9.
 */

public class ShareUtils {
    /**
     * 分享文字及链接
     * @param title
     * @param content
     * @param listener
     */
    public static void shareTextWithUrl(Activity activity,String title, String content, String url, UMShareListener listener) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(activity, R.mipmap.ic_launcher));  //缩略图
        web.setDescription(content);//描述

        new ShareAction(activity).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(listener).open();
        }
}
