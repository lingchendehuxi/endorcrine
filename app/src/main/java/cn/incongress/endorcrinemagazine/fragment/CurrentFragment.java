package cn.incongress.endorcrinemagazine.fragment;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.activity.DetailsActivity;
import cn.incongress.endorcrinemagazine.activity.SearchActivity;
import cn.incongress.endorcrinemagazine.adapter.CurrentAdapater;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CurrentBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;
import cn.incongress.endorcrinemagazine.widget.AnimatedExpandableListView;

/**
 * Created by Jacky on 2017/4/7.
 * 当期
 */

public class CurrentFragment extends BaseLazyFragment {
    private ExpandableListView mExpandableListView;
    private CurrentAdapater adapter;
    private ProgressBar mPgb;
    private LinearLayout mSearchLayout;

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        initHttp();
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mPgb.setVisibility(View.GONE);
                    adapter.setData(current_list);
                    mExpandableListView.setAdapter(adapter);

                    mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra("notesid",current_list.get(groupPosition).getList().get(childPosition).getNotesId());
                            intent.putExtra("notestitle",current_list.get(groupPosition).getList().get(childPosition).getNotesTitle());
                            intent.putExtra("lanmu",current_list.get(groupPosition).getList().get(childPosition).getLanmu());
                            startActivity(intent);
                            return false;
                        }
                    });
                    for(int i = 0; i < adapter.getGroupCount(); i++){

                        mExpandableListView.expandGroup(i);

                    }
                    break;
            }
        }
    };
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        mExpandableListView = (ExpandableListView)view.findViewById(R.id.listView);
        mExpandableListView.setGroupIndicator(null);
        mPgb = (ProgressBar) view.findViewById(R.id.current_pgb);
        mSearchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        adapter = new CurrentAdapater(getActivity());
        current_list = new ArrayList<CurrentBean>();
        choose_list = new ArrayList<ChooseBean>();
        return view;
    }

    private List<ChooseBean> choose_list;
    private List<CurrentBean> current_list;
    private void initHttp() {
        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("proId", "18");
                params.put("state", "0");
                params.put("lan", "cn");
                try {
                    JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE+Constants.PERIOD_LIST,params, "GBK"));
                    JSONArray array = jsonObject.getJSONArray("lanmuArray");
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        CurrentBean current = new CurrentBean();
                        current.setLanmu(object.getString("lanmu"));
                        current.setLanmuld(object.getString("lanmuId"));
                        JSONArray jsonArray = object.getJSONArray("notesArray");
                        List<ChooseBean> list = new ArrayList<ChooseBean>();
                        for(int j = 0;j<jsonArray.length();j++){
                            ChooseBean choose = new ChooseBean();
                            JSONObject object1 = jsonArray.getJSONObject(j);
                            choose.setNotesId(object1.getString("notesId"));
                            choose.setNotesTitle(object1.getString("notesTitle"));
                            choose.setAuthors(object1.getString("authors"));;
                            choose.setNotesType(object1.getString("notesType"));
                            choose.setReadCount(object1.getString("readCount"));
                            choose.setLanmu(object1.getString("lanmu"));
                            list.add(choose);
                        }
                        current.setList(list);
                        current_list.add(current);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
}
