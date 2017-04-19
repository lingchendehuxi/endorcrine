package cn.incongress.endorcrinemagazine.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.MyItemClickListener;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;

/**
 * Created by Admin on 2017/4/7.
 */

public class ChooseAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChooseBean> list;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private MyItemClickListener mItemClickListener;

    /**
     * 设置Item点击监听  自定义 让子类实现
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public List<ChooseBean> getList() {
        return list;
    }

    public ChooseAdapater() {
        list = new ArrayList<ChooseBean>();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position  == getItemCount()+1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).notesTitle.setText(list.get(position).getNotesTitle());
            ((ItemViewHolder) holder).notesType.setText(list.get(position).getNotesType());
            ((ItemViewHolder) holder).authors.setText(list.get(position).getAuthors());
            ((ItemViewHolder) holder).lanmu.setText(list.get(position).getLanmu());
            ((ItemViewHolder) holder).readCount.setText(list.get(position).getReadCount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_list, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view,mItemClickListener);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footerview, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
        return null;
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }


    /**
     * ItemViewHolder 实现点击事件的接口
     */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView notesTitle,notesType,authors,lanmu,readCount;
        private MyItemClickListener mListener;

        /**
         *
         * @param view
         * @param listener 条目的点击事件
         */
        public ItemViewHolder(View view,MyItemClickListener listener) {
            super(view);
            notesTitle = (TextView) view.findViewById(R.id.notesTitle);
            notesType = (TextView) view.findViewById(R.id.notesType);
            authors = (TextView) view.findViewById(R.id.authors);
            lanmu = (TextView) view.findViewById(R.id.lanmu);
            readCount = (TextView) view.findViewById(R.id.readCount);
            this.mListener = listener;

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view,getPosition());
            }
        }
    }


}

