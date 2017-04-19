package cn.incongress.endorcrinemagazine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.activity.DetailsActivity;
import cn.incongress.endorcrinemagazine.adapter.ChooseAdapater;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.base.MyItemClickListener;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;

/**
 * Created by Jacky on 2017/4/7.
 * 精选
 */

public class ChooseFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    private String LASTNOTESID = "-1";
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ChooseAdapater mAdapter;
    private ProgressBar mpgb;
    private int lastVisibleItem;
    private List<ChooseBean> list;
    private boolean FIRST = true;

@Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        initHttp(LASTNOTESID);
        /**
         * 设置RecyclerView 的滚动监听事件onScrollStateChanged 滚动状态改变时调用这个回掉函数
         * onScrolled滚动时调用这个回调函数
         *
         * 上拉加载   就是监听RecyclerView的滚动事件 判断是不是最后一条，如果是最后一条就去加载数据
         * -------->然后给适配器 添加数据
         * */
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyItemClickListener() {

            /**
             * item点击事件
             * @param view
             * @param postion
             */
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("notesid",mAdapter.getList().get(postion).getNotesId());
                intent.putExtra("notestitle",mAdapter.getList().get(postion).getNotesTitle());
                startActivity(intent);
            }
        });
       }
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
        mSwipeRefreshWidget = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.lisr);
        mpgb = (ProgressBar) view.findViewById(R.id.choose_pgb);
        list = new ArrayList<ChooseBean>();
        /**
         * 设置刷新图标的颜色
         */
        mSwipeRefreshWidget.setColorSchemeResources(R.color.red, R.color.red, R.color.red, R.color.red);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mAdapter = new ChooseAdapater();

        return view;
    }
    private void initHttp(final String lastNotesId) {
        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("proId", "18");
                params.put("lastNotesId", lastNotesId);
                params.put("lan", "cn");
                try {
                    JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE+Constants.SELECTED,params, "utf8"));
                    /*String console = "proId=18&lastNotesId=-1&lan=cn";
                    String result = HttpUtils.doPost(Constants.TEST_SERVICE+Constants.SELECTED,console);
                    JSONObject jsonObject = new JSONObject(result);*/
                    if(!"0".equals(jsonObject.getString("pageState"))){
                    JSONArray array = jsonObject.getJSONArray("notesArray");
                    for(int i = 0;i<array.length();i++){
                        ChooseBean chooseBean = new ChooseBean();
                        JSONObject object = array.getJSONObject(i);
                        chooseBean.setNotesId(object.getString("notesId"));
                        chooseBean.setNotesTitle(object.getString("notesTitle"));
                        chooseBean.setAuthors(object.getString("authors"));
                        chooseBean.setNotesType(object.getString("notesType")+getActivity().getString(R.string.choose_piecemeal_text2));
                        chooseBean.setReadCount(getActivity().getString(R.string.choose_piecemeal_text3)+object.getString("readCount")+getActivity().getString(R.string.choose_piecemeal_text4));
                        chooseBean.setLanmu(object.getString("lanmu"));
                        list.add(chooseBean);
                        if(i == array.length()-1){
                            LASTNOTESID = object.getString("notesId");
                            Log.e("GYW","===="+LASTNOTESID);
                        }
                    }}else{
                       handler.sendEmptyMessage(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
                handler.sendEmptyMessage(2);
            }
        }.start();

    }
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mpgb.setVisibility(View.VISIBLE);
                    mSwipeRefreshWidget.setRefreshing(false);
                    list.clear();
                    mAdapter.getList().clear();
                    mAdapter.notifyDataSetChanged();
                    initHttp("-1");
                    break;
                case 1:
                    if(FIRST) {
                        FIRST = false;
                        mpgb.setVisibility(View.VISIBLE);
                        list.clear();
                        initHttp(LASTNOTESID);
                    }else {
                        Toast.makeText(getActivity(),"正在加载数据请稍后···",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    FIRST = true;
                    mpgb.setVisibility(View.GONE);
                    mAdapter.getList().addAll(list);
                    mAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    Toast.makeText(getActivity(),"暂无数据敬请期待···",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }

    };
    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(0);
    }
}
