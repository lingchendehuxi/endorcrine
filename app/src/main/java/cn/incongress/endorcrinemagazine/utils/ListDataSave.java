package cn.incongress.endorcrinemagazine.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.bean.CollectionBean;

/**
 * Created by Admin on 2017/4/27.
 */

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public void setDataList(String tag, List<CollectionBean> datalist) {
        if (null == datalist || datalist.size() < 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public List<CollectionBean> getDataList(String tag) {
        List<CollectionBean> datalist=new ArrayList<CollectionBean>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<CollectionBean>>() {}.getType());
        return datalist;

    }
}
