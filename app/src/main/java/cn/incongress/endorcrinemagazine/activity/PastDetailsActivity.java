package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
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
import cn.incongress.endorcrinemagazine.adapter.CurrentAdapater;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CurrentBean;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;

public class PastDetailsActivity extends BaseActivity {
    @BindView(R.id.activity_title)
    TextView mActivityTitle;
    @BindView(R.id.past_details_expandable)
    ExpandableListView mExpandableListView;
    @BindView(R.id.past_details_pgb)
    ProgressBar mPgb;

    private CurrentAdapater adapter;
    private List<ChooseBean> choose_list;
    private List<CurrentBean> current_list;
    private String notesType ;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_past_details);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        notesType = getIntent().getStringExtra("detailsTitle");
        String a = notesType.substring(0,4);
        String b = "第"+notesType.substring(5)+"期";
        mActivityTitle.setText(a+b);
        mExpandableListView.setGroupIndicator(null);
        adapter = new CurrentAdapater(getApplication());
        current_list = new ArrayList<CurrentBean>();
        choose_list = new ArrayList<ChooseBean>();
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

    private Handler hand = new Handler(){
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

                            Intent intent = new Intent(getApplication(), DetailsActivity.class);
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
    private void initHttp() {

        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("proId", "18");
                params.put("state", "1");
                params.put("notesType",notesType);
                params.put("lan", "cn");
                try {
                    JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE+Constants.PERIOD_LIST,params, "GBK"));
                    JSONArray array = jsonObject.getJSONArray("lanmuArray");
                    Log.e("GYW",jsonObject.toString());
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
                hand.sendEmptyMessage(1);
            }
        }.start();
    }
    public void back(View view){
        finish();
    }
}
