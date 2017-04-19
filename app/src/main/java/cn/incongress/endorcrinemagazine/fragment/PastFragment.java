package cn.incongress.endorcrinemagazine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.MyViewPagerAdapter;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.PastBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;

/**
 * Created by Jacky on 2017/4/7.
 * 往期
 */

public class PastFragment extends BaseLazyFragment {
    private ViewPager vpg;
    private TabLayout tab;
    private List<PastBean> pastList;
    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        pastList = new ArrayList<>();
        initHttp();

    }
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
         vpg = (ViewPager) view.findViewById(R.id.viewpager);
        tab = (TabLayout) view.findViewById(R.id.tabLayout);
        return view;
    }

    private void initHttp() {
        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("proId", "18");
                params.put("lan", "cn");

                try {
                    JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE+Constants.PREVIOUS_PERIOD,params, "utf8"));
                    JSONArray array = jsonObject.getJSONArray("yearArray");
                    for(int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        PastBean  pastBean = new PastBean();
                        pastBean.setYear(object.getString("year"));
                        Map<String ,List<String>> map =  new HashMap<>();
                        List<String> list = new ArrayList<>();
                        JSONArray jsonArray = object.getJSONArray("notesTypeArray");
                        for (int j = 0; j<jsonArray.length();j++){
                            JSONObject o = jsonArray.getJSONObject(j);
                            list.add(o.getString("notesType"));
                        }
                        map.put(object.getString("year"),list);
                        pastBean.setMonth(map);
                        pastList.add(pastBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.run();
                hand.sendEmptyMessage(1);
            }
        }.start();
    }
    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
                    Log.e("GYW","----1-"+pastList.size());
                    for(int i = 0;i<pastList.size();i++){
                        PastBean bean = pastList.get(i);
                        ArrayList<String> s = (ArrayList<String>) bean.getMonth().get(bean.getYear());
                        MonthFragment demo =  new MonthFragment();
                        demo.setData(s);
                        viewPagerAdapter.addFragment(demo , bean.getYear());//添加Fragment
                        tab.addTab(tab.newTab().setText(bean.getYear()+"年"));
                    }
                    vpg.setAdapter(viewPagerAdapter);
                    tab.setupWithViewPager(vpg);
                    break;
            }
        }
    };
}
