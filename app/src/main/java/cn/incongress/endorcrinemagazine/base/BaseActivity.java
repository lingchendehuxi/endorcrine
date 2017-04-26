package cn.incongress.endorcrinemagazine.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import cn.incongress.endorcrinemagazine.utils.ActivityUtils;
import cn.incongress.endorcrinemagazine.utils.StringUtils;


/**
 * Created by Jacky on 2015/12/17.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected SharedPreferences mSharedPreference;

    protected static final int LOAD_DATA_COMPLETE = 0x0001;
    protected static final int LOAD_DATA_NO_DATA = 0x0002;
    protected static final int LOAD_DATA_ERROR = 0x0003;
    protected static final int LOAD_REFRESH_SHOW = 0x0004;

    private InputMethodManager manager;
    /**
     * 个人信息sp文件名
     **/
    private static final String SP_PERSON_INFO = "personInfo";
    /**
     * 基类维护的Handler助手
     */
    protected Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handleDetailMsg(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mSharedPreference = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);

        ActivityUtils.addActivity(this);

        setContentView(savedInstanceState);
        ButterKnife.bind(this);

        initializeViews(savedInstanceState);
        initializeData(savedInstanceState);
        initializeEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ActivityUtils.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
//        mBroadcastReceiver.stopWatch(this, getSPValue(Constant.SP_USER_UUID));
    }

    protected abstract void setContentView(Bundle savedInstanceState);

    protected abstract void initializeViews(Bundle savedInstanceState);

    protected abstract void initializeEvents();

    protected abstract void initializeData(Bundle savedInstanceState);

    protected abstract void handleDetailMsg(android.os.Message msg);

    /**
     * 重新计算listview的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * SwipeRefresh 自动刷新
     *
     * @param refreshLayout
     * @param refreshing
     * @param notify
     */
    protected void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refreshing, boolean notify) {
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout.getClass();
        if (refreshLayoutClass != null) {
            try {
                Method setRefreshing = refreshLayoutClass.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(refreshLayout, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    protected void setSPStringValue(String key, String value) {
        SharedPreferences sp = getSharedPreferences(SP_PERSON_INFO, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    protected String getSPStringValue(String key) {
        SharedPreferences sp = getSharedPreferences(SP_PERSON_INFO, MODE_PRIVATE);
        return sp.getString(key, "");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
