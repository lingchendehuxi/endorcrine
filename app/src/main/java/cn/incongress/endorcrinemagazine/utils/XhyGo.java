package cn.incongress.endorcrinemagazine.utils;

import android.content.Context;


import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import cn.incongress.endorcrinemagazine.R;

/**
 * Created by Jacky Chen on 2016/3/29 0029.
 * 调用接口，业务处理类
 */
public class XhyGo {
    /**
     * 网络连接错误
     **/
    public static final int INTERNET_ERROR = 0X0001;
    /**
     * 字段格式错误
     **/
    public static final int FIELD_FORMAT_ERROR = 0X0002;
    /**
     * 请求成功
     **/
    public static final int SUCCESS = 0X0003;


    public static final int goUploadUserIcon(Context context, String userId, String fileName, File headIconFile, StringCallback stringCallback) {

        //先检查是否有网络
        if (NetWorkUtils.isNetworkConnected(context)) {
            //再检查字段是否格式正确
            if (StringUtils.isNotEmpty(userId)) {
                //最后发送请求
                XhyApiClient.uploadUserIcon(userId, fileName, headIconFile, stringCallback);
                return SUCCESS;
            } else {
                return FIELD_FORMAT_ERROR;
            }
        } else {
            return INTERNET_ERROR;
        }
    }


}
