package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;

/**
 * Created by Admin on 2017/4/27.
 */

public class CollectionAdapater extends RecyclerView.Adapter<CollectionAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<ChooseBean> mArrayList = new ArrayList<>();

    public CollectionAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<ChooseBean> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public CollectionAdapater.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CollectionAdapater.SearchViewHolder holder = new CollectionAdapater.SearchViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_collection, parent,
                false));
        return holder;
    }


    //...
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);

    }

    private CollectionAdapater.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(CollectionAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final CollectionAdapater.SearchViewHolder holder, final int position) {

        holder.search_lanmu.setText(mArrayList.get(position).getLanmu().substring(1,mArrayList.get(position).getLanmu().length()-1));
        holder.notesTitle.setText(mArrayList.get(position).getNotesTitle());

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView notesTitle,search_lanmu;

        public SearchViewHolder(View view) {
            super(view);
            search_lanmu = (TextView) view.findViewById(R.id.search_lanmu);
            notesTitle = (TextView) view.findViewById(R.id.search_notesTitle);

        }
    }
}


