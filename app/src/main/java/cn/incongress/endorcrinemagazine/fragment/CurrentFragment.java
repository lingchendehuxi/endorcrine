package cn.incongress.endorcrinemagazine.fragment;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;
import cn.incongress.endorcrinemagazine.widget.AnimatedExpandableListView;

/**
 * Created by Jacky on 2017/4/7.
 * 当期
 */

public class CurrentFragment extends BaseLazyFragment {
    private AnimatedExpandableListView animatedExpandableListView;
    private CurrentAdapater adapter;
    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        initHttp();

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter.setData(current_list);
                    animatedExpandableListView.setAdapter(adapter);
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_current,null);
                    animatedExpandableListView.addFooterView(view);
                    // In order to show animations, we need to use a custom click handler
                    // for our ExpandableListView.
                    animatedExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            // We call collapseGroupWithAnimation(int) and
                            // expandGroupWithAnimation(int) to animate group
                            // expansion/collapse.
                            if (animatedExpandableListView.isGroupExpanded(groupPosition)) {
                                animatedExpandableListView.collapseGroupWithAnimation(groupPosition);
                            } else {
                                animatedExpandableListView.expandGroupWithAnimation(groupPosition);
                            }
                            return true;
                        }

                    });

                    for(int i = 0; i < adapter.getGroupCount(); i++){

                        animatedExpandableListView.expandGroup(i);

                    }
                    break;
            }
        }
    };
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        animatedExpandableListView = (AnimatedExpandableListView)view.findViewById(R.id.listView);
        animatedExpandableListView.setGroupIndicator(null);
        adapter = new CurrentAdapater(getActivity());
        current_list = new ArrayList<CurrentItem>();
        choose_list = new ArrayList<ChooseItem>();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"下拉",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private List<ChooseItem> choose_list;
    private List<CurrentItem> current_list;
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
                    /*String console = "proId=18&state=0&lan=cn";
                    String result = HttpUtils.doPost(Constants.TEST_SERVICE+Constants.PERIOD_LIST,console);
                    JSONObject jsonObject = new JSONObject(result);*/
                    JSONArray array = jsonObject.getJSONArray("lanmuArray");

                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        CurrentItem current = new CurrentItem();
                        current.lanmu1 = object.getString("lanmu");
                        current.lanmuld = object.getString("lanmuId");
                        JSONArray jsonArray = object.getJSONArray("notesArray");
                        Log.e("GYW","====="+object.toString());
                        for(int j = 0;j<jsonArray.length();j++){
                            ChooseItem choose = new ChooseItem();
                            JSONObject object1 = jsonArray.getJSONObject(j);
                            choose.notesId=object1.getString("notesId");
                            choose.notesTitle=object1.getString("notesTitle");
                            choose.authors=object1.getString("authors");
                            choose.notesType=object1.getString("notesType");
                            choose.readCount=object1.getString("readCount");
                            choose.lanmu=object1.getString("lanmu");
                            current.items.add(choose);

                        }
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

    private static class CurrentItem {
         String lanmuld;
         String lanmu1;
        List<ChooseItem> items = new ArrayList<ChooseItem>();
    }

    private static class ChooseItem {
         String notesId;
         String notesTitle;
         String smallTitle;
         String authors;
         String notesType;
         String readCount;
         String lanmu;
    }

    private static class ChooseHolder {
        TextView readCount_text,authors_text,notesTitle_text,notesType_text;

    }

    private static class CurrentHolder {
        TextView lanmu_text;
    }

    /**
     * Adapter for our list of {@link CurrentItem}s.
     */
    private class CurrentAdapater extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<CurrentItem> items;

        public CurrentAdapater(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<CurrentItem> items) {
            this.items = items;
        }

        @Override
        public ChooseItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChooseHolder holder;
            ChooseItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChooseHolder();
                convertView = inflater.inflate(R.layout.item_current_choose, parent, false);
                holder.notesTitle_text = (TextView) convertView.findViewById(R.id.current_notesTitle);
                holder.notesType_text = (TextView) convertView.findViewById(R.id.current_notesType);
                holder.authors_text = (TextView) convertView.findViewById(R.id.current_authors);
                holder.readCount_text = (TextView) convertView.findViewById(R.id.current_readCount);
                convertView.setTag(holder);
            } else {
                holder = (ChooseHolder) convertView.getTag();
            }

            holder.notesTitle_text.setText(item.notesTitle);
            holder.notesType_text.setText(item.notesType+getActivity().getString(R.string.choose_piecemeal_text2));
            holder.authors_text.setText(item.authors);
            holder.readCount_text.setText(item.readCount+getActivity().getString(R.string.choose_piecemeal_text4));
            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public CurrentItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            CurrentHolder holder;
            CurrentItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new CurrentHolder();
                convertView = inflater.inflate(R.layout.item_current, parent, false);
                holder.lanmu_text = (TextView) convertView.findViewById(R.id.current_title);
                convertView.setTag(holder);
            } else {
                holder = (CurrentHolder) convertView.getTag();
            }
            Log.e("GYW","----"+item.lanmu1.substring(1,item.lanmu1.length()-1));
            holder.lanmu_text.setText(item.lanmu1.substring(1,item.lanmu1.length()-1));

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}
