package cn.incongress.endorcrinemagazine.utils;

import android.util.Log;

import cn.incongress.endorcrinemagazine.base.Constants;

/**
 * @author great.maronghua@gmail.com
 * @since 2012-8-15
 * TODO 发布的时候清空这些方法
 */
public final class LogUtils {

	public static void println(String msg) {
		if (Constants.DEBUG) {
			Log.i("LogUtils", msg);
		}
	}

	public static void printStackTrace(Exception e) {
		if (Constants.DEBUG) {
			e.printStackTrace();
		}
	}
	
	public static void printStackTrace(Error e) {
		if (Constants.DEBUG) {
			e.printStackTrace();
		}
	}
	
	public static void v(String tag, String msg) {
		if (Constants.DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (Constants.DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if (Constants.DEBUG) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable thr) {
		if (Constants.DEBUG) {
			Log.e(tag, msg, thr);
		}
	}
}
