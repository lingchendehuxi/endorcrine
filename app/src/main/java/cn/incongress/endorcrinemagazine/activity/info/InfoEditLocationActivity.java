package cn.incongress.endorcrinemagazine.activity.info;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.CityBean;
import cn.incongress.endorcrinemagazine.utils.AssetsDBUtils;
import cn.incongress.endorcrinemagazine.utils.IndexableListView;
import cn.incongress.endorcrinemagazine.utils.StringMatcher;


/**
 * Created by Administrator on 2015/7/8.
 */
public class InfoEditLocationActivity extends BaseActivity {
    private List<CityBean> mBeans = new ArrayList<CityBean>();
    private CityAdapter mAdapter;


    private String mChooseCityid = "2";
    private int mChooseProvinceId = -1;
    private String mChooseCityName = "";
    private String mChooseProvinceName = "";


    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.lv_city)
    IndexableListView mListView;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_info_location);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitle.setText(getString(R.string.register_region1));
        mChooseCityid = getSPStringValue(Constants.USER_CITY_ID);
        mBeans = AssetsDBUtils.getAllCity();

        mAdapter = new CityAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setFastScrollEnabled(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mChooseCityid = mBeans.get(i).getCityId() + "";
                mChooseProvinceId = mBeans.get(i).getProvinceId();
                mChooseCityName = mBeans.get(i).getCityName();
                mChooseProvinceName = mBeans.get(i).getProvinceName();
                mAdapter.notifyDataSetChanged();

                setSPStringValue(Constants.USER_PROVINCE_ID, mChooseProvinceId + "");
                setSPStringValue(Constants.USER_PROVINCE_NAME, mChooseProvinceName);
                setSPStringValue(Constants.USER_CITY_ID,mChooseCityid);
                setSPStringValue(Constants.USER_CITY_NAME, mChooseCityName);
                setResult(RESULT_OK);
                finish();
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

    private class CityAdapter extends BaseAdapter implements SectionIndexer {

        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        @Override
        public int getCount() {
            return mBeans.size();
        }

        @Override
        public String getItem(int i) {
            String s = mBeans.get(i).getPinyin().toUpperCase();
            return s;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null) {
                view = LayoutInflater.from(InfoEditLocationActivity.this).inflate(R.layout.item_city,null);
                holder = new ViewHolder();
                holder.cityName = (TextView)view.findViewById(R.id.tv_name);
                holder.choose = (ImageView)view.findViewById(R.id.iv_choose);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }

            CityBean bean = mBeans.get(i);
            holder.cityName.setText(bean.getCityName());
            if(mChooseCityid.equals(String.valueOf(bean.getCityId()))) {
                holder.choose.setVisibility(View.VISIBLE);
            }else {
                holder.choose.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public int getPositionForSection(int section) {
            for (int i = section; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
                                return j;
                        }
                    } else {
                        if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
                            return j;
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }

        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }

    }

    class ViewHolder {
        private TextView cityName;
        private ImageView choose;
    }
    public void back(View view){
        finish();
    }

}
