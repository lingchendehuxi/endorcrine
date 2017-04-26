package cn.incongress.endorcrinemagazine.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jacky on 2016/1/15.
 * 弹出工具类
 */
public class ToastUtils {
    public static void showShortToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(int msgId, Context context) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(int msgId, Context context) {
        Toast.makeText(context, msgId, Toast.LENGTH_LONG).show();
    }
}
