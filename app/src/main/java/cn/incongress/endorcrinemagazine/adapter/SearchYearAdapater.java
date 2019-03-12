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
import cn.incongress.endorcrinemagazine.bean.SearchColumnBean;
import cn.incongress.endorcrinemagazine.bean.SearchYearBean;

/**
 * Created by Admin on 2017/4/21.
 */

public class SearchYearAdapater extends RecyclerView.Adapter<SearchYearAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<SearchYearBean> mArrayList = new ArrayList<>();

    public SearchYearAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<SearchYearBean> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public SearchYearAdapater.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchYearAdapater.SearchViewHolder holder = new SearchYearAdapater.SearchViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_search_year, parent,
                false));
        return holder;
    }


    //...
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private SearchYearAdapater.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchYearAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final SearchYearAdapater.SearchViewHolder holder, final int position) {

        holder.tv.setText(mArrayList.get(position).getYear());
        if(mArrayList.get(position).isSelected()) {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.tv.setEnabled(true);
        }else{
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.search_back));
            holder.tv.setEnabled(false);
        }
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
            tv = (TextView) view.findViewById(R.id.search_choice_year);
        }
    }
}
