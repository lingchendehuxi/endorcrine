package cn.incongress.endorcrinemagazine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Jacky on 2017/4/7.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public HomeFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if(mFragmentList != null)
            return mFragmentList.size();
        else
            return 0;
    }
}
