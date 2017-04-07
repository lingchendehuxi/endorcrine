package cn.incongress.endorcrinemagazine.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jacky on 2015/12/23.
 */
public abstract class BaseFragment extends Fragment {
    protected static final int LOAD_DATA_COMPLETE = 0x0001;
    protected static final int LOAD_DATA_NO_DATA = 0x0002;
    protected static final int LOAD_DATA_ERROR = 0x0003;
    protected static final int LOAD_REFRESH_SHOW = 0x0004;
    protected static final int LOAD_DATA_MORE = 0x0005;
    protected static final int LOAD_DATA_NO_MORE = 0x0006;
    protected static final int SERVER_ERROR = 0x0007;
    protected static final int UPLOAD_IMGURL_SUCCESS = 0x0008;

    protected abstract void handleDetailMsg(android.os.Message msg);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * SwipeRefresh 自动刷新
     * @param refreshLayout
     * @param refreshing
     * @param notify
     */
    protected void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refreshing, boolean notify){
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
}
