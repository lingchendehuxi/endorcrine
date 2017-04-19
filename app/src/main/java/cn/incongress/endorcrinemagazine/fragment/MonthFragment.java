package cn.incongress.endorcrinemagazine.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.PastAdapter;

public class MonthFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> mArrayList;
    private PastAdapter mAdapter;
    /*private MonthFragment instance=null;

    public   MonthFragment newInstance() {
        return new MonthFragment();
    }
*/
    public MonthFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_month_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.past_recycler);

        //设置布局管理器 --GridLayout效果--2行
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //实例化适配器--  
        mAdapter = new PastAdapter(getActivity());
        //将适配器和RecyclerView绑定  
        recyclerView.setAdapter(mAdapter);
        //给适配器设置数据源  
        mAdapter.setData(mArrayList);
        mAdapter.setOnItemClickLitener(new PastAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), mArrayList.get(position) ,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public  void setData(ArrayList<String> list) {
        mArrayList = new ArrayList<>();
        mArrayList = list;
    }
}
