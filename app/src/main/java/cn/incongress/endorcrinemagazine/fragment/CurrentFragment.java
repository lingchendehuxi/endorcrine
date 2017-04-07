package cn.incongress.endorcrinemagazine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;

/**
 * Created by Jacky on 2017/4/7.
 * 当期
 */

public class CurrentFragment extends BaseLazyFragment {
    
    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        return view;
    }
}
