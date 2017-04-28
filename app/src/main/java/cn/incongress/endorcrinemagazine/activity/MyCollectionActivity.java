package cn.incongress.endorcrinemagazine.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.adapter.CollectionAdapater;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CollectionBean;
import cn.incongress.endorcrinemagazine.utils.ListDataSave;

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.collection_recl)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private ListDataSave mDataSave;
    private List<CollectionBean> mResultList;
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
        mLayoutManager = new LinearLayoutManager(MyCollectionActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(collectionAdapater);

        mDataSave = new ListDataSave(mContext, "collection");

        mResultList = mDataSave.getDataList("All");

        //给适配器设置数据源
        collectionAdapater.setData(mResultList);
        collectionAdapater.setOnItemClickLitener(new CollectionAdapater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("GYW",mResultList.get(position).getNotesId());
                Intent intent = new Intent(getApplication(), DetailsActivity.class);
                intent.putExtra("fromCollection",true);
                intent.putExtra("notesid",mResultList.get(position).getNotesId());
                intent.putExtra("notestitle",mResultList.get(position).getNotesTitle());
                startActivityForResult(intent,0x001);
            }
        });
        collectionAdapater.setOnLongItemClickLitener(new CollectionAdapater.onLongItemClickLitener() {
            @Override
            public void onLongItemClick(View view, final int position) {

                new AlertDialog.Builder(MyCollectionActivity.this).setTitle("")
                        .setMessage("确认删除本条收藏记录？")
                        .setPositiveButton("取消",new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                            }
                        }).setNegativeButton("确认",new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        Log.e("GYW",position+"移除数据");
                        mResultList.remove(position);
                        mDataSave.setDataList("All",mResultList);
                        collectionAdapater.notifyItemRemoved(position);
                    }
                }).show();//在按键响应事件中显示此对话框
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 0x001) {
                mResultList.clear();
                mResultList.addAll(mDataSave.getDataList("All"));
                collectionAdapater.notifyDataSetChanged();
            }
        }
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
