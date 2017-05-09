package cn.incongress.endorcrinemagazine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.SearchColumnAdapater;
import cn.incongress.endorcrinemagazine.adapter.SearchResultAdapater;
import cn.incongress.endorcrinemagazine.adapter.SearchYearAdapater;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.SearchColumnBean;
import cn.incongress.endorcrinemagazine.bean.SearchYearBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_column_recycler)
    RecyclerView mColumnRecycler;
    @BindView(R.id.search_year_recycler)
    RecyclerView mYearRecycler;
    @BindView(R.id.search_expandable)
    RecyclerView mRecyclerView;
    @BindView(R.id.choiceLayout)
    ScrollView mLinaearLayout;
    @BindView(R.id.search_pgb)
    ProgressBar mPgb;
    @BindView(R.id.search_edit)
    EditText mEdit;
    @BindView(R.id.search_spinner)
    Spinner mSpinner;
    @BindView(R.id.go_search)
    Button mGoSearch;
    @BindView(R.id.search_result_null)
    TextView mResultNull;
    @BindView(R.id.search_back)
    TextView mBack;

    private SearchColumnAdapater mColumnAdapter;
    private SearchYearAdapater mYearAdapter;
    private SearchResultAdapater mResultAdapater;

    private List<SearchColumnBean> mColumnList = new ArrayList<>();
    private List<SearchYearBean> mYearList = new ArrayList<>();
    private List<ChooseBean> mResultList;

    private String TEXTTYPE;
    private String lanmus = "",years ="",text;
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
    }
    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        initHttp(true);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("取消".equals(mBack.getText().toString())){
                    finish();
                }else if("返回".equals(mBack.getText().toString())){
                    mBack.setText("取消");
                    lanmus = ""; years = "";
                    mLinaearLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        mGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hand.sendEmptyMessage(2);
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TEXTTYPE = position+1+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void initHttp(boolean that) {
        if(that) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("proId", "18");
                    params.put("lan", "cn");
                    try {
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(mContext,Constants.TEST_SERVICE + Constants.GET_COLUMN_YEAR, params, "GBK"));
                        Log.e("GYW", jsonObject.toString());
                        JSONArray array = jsonObject.getJSONArray("years");
                        JSONArray jsonArray = jsonObject.getJSONArray("lanmuArray");
                        for (int i = 0; i < array.length(); i++) {
                            SearchYearBean searchYearBean = new SearchYearBean();
                            searchYearBean.setYear(array.get(i).toString());
                            mYearList.add(searchYearBean);
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            SearchColumnBean searchColumnBean = new SearchColumnBean();
                            searchColumnBean.setLanmu(object.getString("lanmu").substring(1, object.getString("lanmu").length() - 1));
                            searchColumnBean.setLanmuId(object.getString("lanmuId"));
                            mColumnList.add(searchColumnBean);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    super.run();
                    hand.sendEmptyMessage(5);
                    hand.sendEmptyMessage(0);
                    hand.sendEmptyMessage(1);
                }
            }.start();
        }else{
            new Thread() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("proId", "18");
                    params.put("text", text);
                    params.put("textType",TEXTTYPE);
                    params.put("lanmus", lanmus);
                    params.put("years", years);
                    params.put("lan", "cn");
                    try {
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(mContext,Constants.TEST_SERVICE + Constants.SEARCH, params, "GBK"));
                        if(jsonObject.getInt("state")==1){
                            JSONArray array = jsonObject.getJSONArray("notesArray");
                            for(int i = 0;i<array.length();i++){
                                ChooseBean chooseBean = new ChooseBean();
                                JSONObject object = array.getJSONObject(i);
                                chooseBean.setNotesId(object.getString("notesId"));
                                chooseBean.setNotesTitle(object.getString("notesTitle"));
                                chooseBean.setAuthors(object.getString("authors"));
                                chooseBean.setNotesType(object.getString("notesType")+getApplication().getString(R.string.choose_piecemeal_text2));
                                chooseBean.setReadCount(object.getString("readCount"));
                                chooseBean.setLanmu(object.getString("lanmu"));
                                mResultList.add(chooseBean);
                            }
                            hand.sendEmptyMessage(3);
                        }else{
                            hand.sendEmptyMessage(4);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        hand.sendEmptyMessage(6);
                    }
                    super.run();
                }
            }.start();
        }
    }
    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPgb.setVisibility(View.GONE);
            switch (msg.what){
                case 0:
                    //设置布局管理器 --GridLayout效果--2行
                    mColumnRecycler.setLayoutManager(new GridLayoutManager(getApplication(), 3));
                    //实例化适配器--
                    mColumnAdapter = new SearchColumnAdapater(getApplication());
                    //将适配器和RecyclerView绑定
                    mColumnRecycler.setAdapter(mColumnAdapter);
                    //给适配器设置数据源
                    mColumnAdapter.setData(mColumnList);
                    mColumnAdapter.setOnItemClickLitener(new SearchColumnAdapater.OnItemClickLitener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            if(mColumnList.get(position).isSelected()){
                                mColumnList.get(position).setSelected(false);
                            }else{
                                mColumnList.get(position).setSelected(true);
                            }
                            mColumnAdapter.notifyItemChanged(position);
                        }
                    });
                case 1:
                    //设置布局管理器 --GridLayout效果--2行
                    mYearRecycler.setLayoutManager(new GridLayoutManager(getApplication(), 4));
                    //实例化适配器--
                    mYearAdapter = new SearchYearAdapater(getApplication());
                    //将适配器和RecyclerView绑定
                    mYearRecycler.setAdapter(mYearAdapter);
                    //给适配器设置数据源
                    mYearAdapter.setData(mYearList);
                    mYearAdapter.setOnItemClickLitener(new SearchYearAdapater.OnItemClickLitener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            if(mYearList.get(position).isSelected()){
                                mYearList.get(position).setSelected(false);
                            }else{
                                mYearList.get(position).setSelected(true);
                            }
                            mYearAdapter.notifyItemChanged(position);
                        }
                    });
                    break;
                case 2:
                    mResultList = new ArrayList<>();
                    if(mEdit.getText().toString() != null){
                        text = mEdit.getText().toString();
                    }else{
                        text = "";
                    }
                        for (int i = 0; i < mColumnList.size(); i++) {
                            if (mColumnList.get(i).isSelected()) {
                                if ("".equals(lanmus)) {
                                    lanmus = mColumnList.get(i).getLanmuId();
                                } else {
                                    lanmus = lanmus + "," + mColumnList.get(i).getLanmuId();
                                }
                            }
                        }
                    for(int i = 0;i<mYearList.size();i++){
                        if(mYearList.get(i).isSelected()){
                            if("".equals(years)){
                                years = mYearList.get(i).getYear();
                            }else{
                                years = years+","+mYearList.get(i).getYear();
                            }
                        }
                    }
                    if(lanmus == null){
                        lanmus = "";
                    }
                    if(years == null){
                        years = "";
                }
                    Log.e("GYW","--"+text+ "**"+lanmus +"&&"+years+"##"+ TEXTTYPE);
                    if(!"".equals(text)||!"".equals(lanmus)||!"".equals(years)){
                        mBack.setText("返回");
                        mLinaearLayout.setVisibility(View.GONE);
                        mPgb.setVisibility(View.VISIBLE);
                        initHttp(false);
                    }else{
                        Toast.makeText(getApplication(),"请选择或输入搜索内容",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    mPgb.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 1));
                    //实例化适配器--
                    mResultAdapater = new SearchResultAdapater(getApplication(),text,TEXTTYPE);
                    //将适配器和RecyclerView绑定
                    mRecyclerView.setAdapter(mResultAdapater);
                    //给适配器设置数据源
                    mResultAdapater.setData(mResultList);
                    mResultAdapater.setOnItemClickLitener(new SearchResultAdapater.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getApplication(), DetailsActivity.class);
                            intent.putExtra("notesid",mResultList.get(position).getNotesId());
                            intent.putExtra("notestitle",mResultList.get(position).getNotesTitle());
                            intent.putExtra("lanmu",mResultList.get(position).getLanmu());
                            startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    mPgb.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    mResultNull.setVisibility(View.VISIBLE);
                    mResultNull.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hand.sendEmptyMessage(5);
                        }
                    });
                    break;
                case 5:
                    mEdit.setText("");
                    mBack.setText("取消");
                    mResultNull.setVisibility(View.GONE);
                    mLinaearLayout.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    Toast.makeText(mContext,getString(R.string.httpfail),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            hand.sendEmptyMessage(2);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
