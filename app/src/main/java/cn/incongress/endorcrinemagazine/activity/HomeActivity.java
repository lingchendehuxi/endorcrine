package cn.incongress.endorcrinemagazine.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;


import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.HomeFragmentAdapter;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.fragment.ChooseFragment;
import cn.incongress.endorcrinemagazine.fragment.CurrentFragment;
import cn.incongress.endorcrinemagazine.fragment.MeFragment;
import cn.incongress.endorcrinemagazine.fragment.PastFragment;

/**
 * Created by Jacky on 2017/4/6.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.ati_navigation)
    AlphaTabsIndicator mIndicator;
    HomeFragmentAdapter mHomeAdapter;

    List<Fragment> mFragmentList;
    ChooseFragment mChooseFragment;
    CurrentFragment mCurrentFragment;
    PastFragment mPastFragment;
    MeFragment mMeFragment;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {

        mChooseFragment = new ChooseFragment();
        mCurrentFragment = new CurrentFragment();
        mPastFragment = new PastFragment();
        mMeFragment = new MeFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mChooseFragment);
        mFragmentList.add(mCurrentFragment);
        mFragmentList.add(mPastFragment);
        mFragmentList.add(mMeFragment);

        mHomeAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mHomeAdapter);
        mIndicator.setViewPager(mViewPager);

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
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
