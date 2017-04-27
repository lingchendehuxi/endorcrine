package cn.incongress.endorcrinemagazine.utils;


import android.util.Log;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import cn.incongress.endorcrinemagazine.base.Constants;

/**
 * Created by Jacky Chen on 2016/3/24 0024.
 */
public class XhyApiClient {



    /**
     * 上传用户头像
     * @param userId
     * @param fileName
     * @param headIconFile
     * @param stringCallback
     */
    public static void uploadUserIcon(String userId, String fileName, File headIconFile, StringCallback stringCallback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("proId","18");
        params.put("userId", userId);

        Log.e("GYW","*******************发送请求");
        OkHttpUtils.post().url(Constants.TEST_SERVICE+Constants.IMGURL).params(params).addFile("userImg", fileName, headIconFile).build().
                execute(stringCallback);
    }



}
