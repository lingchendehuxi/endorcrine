package cn.incongress.endorcrinemagazine.utils;

import android.widget.Toast;

import cn.incongress.endorcrinemagazine.base.BaseApplication;

/**
 * Created by Jacky on 2016/1/15.
 */
public class ToastUtils {
    public static void showShorToast(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(int msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }


    public static void showLongToast(int msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
