package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.CollectionAdapater;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.collection_recl)
    RecyclerView mRecyclerView;

    private List<ChooseBean> mResultList;
    private CollectionAdapater collectionAdapater;
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_collection);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitle.setText(getString(R.string.mycollection));

        collectionAdapater = new CollectionAdapater(getApplication());
        //将适配器和RecyclerView绑定
        mRecyclerView.setAdapter(collectionAdapater);
        //给适配器设置数据源
        collectionAdapater.setData(mResultList);
        collectionAdapater.setOnItemClickLitener(new CollectionAdapater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplication(), DetailsActivity.class);
                intent.putExtra("notesid",mResultList.get(position).getNotesId());
                intent.putExtra("notestitle",mResultList.get(position).getNotesTitle());
                startActivity(intent);
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

    public void back(View view){
        finish();
    }
}
