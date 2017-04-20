package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.PastAdapter;
import cn.incongress.endorcrinemagazine.adapter.SearchAdapater;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CurrentBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_column_recycler)
    RecyclerView mColumnRecycler;
    @BindView(R.id.search_year_recycler)
    RecyclerView mYearRecycler;
    @BindView(R.id.search_expandable)
    ExpandableListView mExpandableListView;
    @BindView(R.id.choiceLayout)
    LinearLayout mLinaearLayout;

    private SearchAdapater mAdapter;
    private List<String> mColumnList = new ArrayList<>();
    private List<String> mYearList = new ArrayList<>();

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        initHttp();

    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }

    private void initHttp() {
        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("proId", "18");
                try {
                    JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE+Constants.GET_COLUMN_YEAR,params, "GBK"));
                    Log.e("GYW",jsonObject.toString());
                    JSONArray array = jsonObject.getJSONArray("years");
                    JSONArray jsonArray = jsonObject.getJSONArray("lanmuArray");
                    for(int i = 0 ; i<array.length();i++){
                        mYearList.add(array.get(i).toString());
                    }
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        mColumnList.add(object.getString("lanmu"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
                hand.sendEmptyMessage(0);
                hand.sendEmptyMessage(1);
            }
        }.start();
    }
    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //设置布局管理器 --GridLayout效果--2行
                    mColumnRecycler.setLayoutManager(new GridLayoutManager(getApplication(), 2));
                    //实例化适配器--
                    mAdapter = new SearchAdapater(getApplication());
                    //将适配器和RecyclerView绑定
                    mColumnRecycler.setAdapter(mAdapter);
                    //给适配器设置数据源
                    mAdapter.setData(mColumnList);
                    mAdapter.setOnItemClickLitener(new SearchAdapater.OnItemClickLitener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getApplication(),mColumnList.get(position),Toast.LENGTH_SHORT).show();
                        }
                    });
                case 1:
                    //设置布局管理器 --GridLayout效果--2行
                    mYearRecycler.setLayoutManager(new GridLayoutManager(getApplication(), 2));
                    //实例化适配器--
                    mAdapter = new SearchAdapater(getApplication());
                    //将适配器和RecyclerView绑定
                    mYearRecycler.setAdapter(mAdapter);
                    //给适配器设置数据源
                    mAdapter.setData(mYearList);
                    mAdapter.setOnItemClickLitener(new SearchAdapater.OnItemClickLitener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getApplication(),mYearList.get(position),Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
}
