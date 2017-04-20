package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;

/**
 * Created by Admin on 2017/4/20.
 */

public class SearchAdapater extends RecyclerView.Adapter<SearchAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<String> mArrayList = new ArrayList<>();

    public SearchAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<String> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchViewHolder holder = new SearchViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_search, parent,
                false));
        return holder;
    }


    //...
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private SearchAdapater.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final SearchAdapater.SearchViewHolder holder, final int position) {

        holder.tv.setText(mArrayList.get(position));
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

        TextView tv;

        public SearchViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.search_choice);
        }
    }
}
