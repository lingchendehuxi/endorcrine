package cn.incongress.endorcrinemagazine.activity.info;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.HospitalBean;
import cn.incongress.endorcrinemagazine.bean.HospitalBean2;
import cn.incongress.endorcrinemagazine.bean.SearchColumnBean;
import cn.incongress.endorcrinemagazine.bean.SearchYearBean;
import cn.incongress.endorcrinemagazine.utils.AssetsDBUtils;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;
import cn.incongress.endorcrinemagazine.utils.ToastUtils;

public class InfoEditHospitalActivity extends BaseActivity {
    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.lv_hospitals)
    ListView mListView;
    private List<HospitalBean> mBeans = new ArrayList<>();
    private HospitalAdapter mAdapter;

    private String mCityId = "-1";

    private String mCurrentHospitalId = "7";
    private String mCurrentHospitalName = "";


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_info_edit_hospital);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitle.setText(getString(R.string.info_hospital));
        mCityId = getSPStringValue(Constants.USER_CITY_ID);
        mCurrentHospitalId = getSPStringValue(Constants.USER_HOSPITAL_ID);
        mBeans = AssetsDBUtils.getHospitalsByCityId(Integer.parseInt(mCityId));
        mListView.setFastScrollEnabled(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentHospitalName = mBeans.get(i).getName();
                mCurrentHospitalId = mBeans.get(i).getHospitalId() + "";
                mAdapter.notifyDataSetChanged();

                setSPStringValue(Constants.USER_HOSPITAL_ID, mCurrentHospitalId + "");
                setSPStringValue(Constants.USER_HOSPITAL_NAME, mCurrentHospitalName +"");
                setResult(RESULT_OK);
                finish();
            }
        });
        initHttp();
    }
    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Gson gson = new Gson();
                    HospitalBean2 hospitalBean2 = gson.fromJson(response, HospitalBean2.class);
                    if(hospitalBean2 != null){
                        if(hospitalBean2.getState() == 1) {
                            List<HospitalBean2.HospitalArrayBean> hospitalArray = hospitalBean2.getHospitalArray();
                            for (int i = 0; i < hospitalArray.size(); i++) {
                                HospitalBean2.HospitalArrayBean hospitalArrayBean = hospitalArray.get(i);
                                mBeans.add(new HospitalBean(Integer.parseInt(mCityId), hospitalArrayBean.getHospitalId(), hospitalArrayBean.getHospitalName()));
                            }
                            mAdapter = new HospitalAdapter();
                            mListView.setAdapter(mAdapter);
                        }else {
                            ToastUtils.showShortToast("请先选择您的所在地区", InfoEditHospitalActivity.this);
                        }
                    }else{
                        ToastUtils.showShortToast("医院信息加载失败，请重新进入", InfoEditHospitalActivity.this);
                        finish();
                    }
                    break;
            }
        }
    };
    private String response;
    private void initHttp() {
        new Thread(){
            @Override
            public void run() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("getHospitalList", "");
                params.put("cityId", mCityId);

                try {
                    response  = HttpUtils.submitGetData(getString(R.string.hospget,"",mCityId), "GBK");
                    hand.sendEmptyMessage(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
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

    private class HospitalAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBeans.size();
        }

        @Override
        public HospitalBean getItem(int i) {
            return mBeans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null) {
                view = LayoutInflater.from(InfoEditHospitalActivity.this).inflate(R.layout.item_city,null);
                holder = new ViewHolder();
                holder.hospitalName = (TextView)view.findViewById(R.id.tv_name);
                holder.choose = (ImageView)view.findViewById(R.id.iv_choose);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }

            HospitalBean bean = mBeans.get(i);
            holder.hospitalName.setText(bean.getName());

            if(mCurrentHospitalId.equals(bean.getHospitalId()+"")) {
                holder.choose.setVisibility(View.VISIBLE);
            }else {
                holder.choose.setVisibility(View.GONE);
            }

            return view;
        }

    }

    class ViewHolder {
        private TextView hospitalName;
        private ImageView choose;
    }

    public void back(View view){
        finish();
    }
}
