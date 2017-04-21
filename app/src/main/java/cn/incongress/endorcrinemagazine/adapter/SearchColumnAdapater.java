package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.bean.SearchColumnBean;

/**
 * Created by Admin on 2017/4/20.
 */

public class SearchColumnAdapater extends RecyclerView.Adapter<SearchColumnAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<SearchColumnBean> mArrayList = new ArrayList<>();

    public SearchColumnAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<SearchColumnBean> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchViewHolder holder = new SearchViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_search_column, parent,
                false));
        return holder;
    }


    //...
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private SearchColumnAdapater.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchColumnAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final SearchColumnAdapater.SearchViewHolder holder, final int position) {

        holder.tv.setText(mArrayList.get(position).getLanmu());

        if(mArrayList.get(position).isSelected()) {
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.details));
        }else{
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.search_back));
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.search_gray));
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
        RelativeLayout relativeLayout;

        public SearchViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.search_choice_column);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.search_column_layout);
        }
    }
}
