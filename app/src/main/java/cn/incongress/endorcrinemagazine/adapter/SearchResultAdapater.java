package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.SearchColumnBean;

/**
 * Created by Admin on 2017/4/21.
 */

public class SearchResultAdapater extends RecyclerView.Adapter<SearchResultAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<ChooseBean> mArrayList = new ArrayList<>();

    public SearchResultAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<ChooseBean> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public SearchResultAdapater.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchResultAdapater.SearchViewHolder holder = new SearchResultAdapater.SearchViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_serach_result, parent,
                false));
        return holder;
    }


    //...
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);

    }

    private SearchResultAdapater.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchResultAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final SearchResultAdapater.SearchViewHolder holder, final int position) {

        holder.search_lanmu.setText(mArrayList.get(position).getLanmu().substring(1,mArrayList.get(position).getLanmu().length()-1));
        holder.notesTitle.setText(mArrayList.get(position).getNotesTitle());
        holder.search_authors.setText(mArrayList.get(position).getAuthors());
        holder.notesType.setText(mArrayList.get(position).getNotesType());

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

        TextView notesType,notesTitle,search_lanmu,search_authors;

        public SearchViewHolder(View view) {
            super(view);
            search_lanmu = (TextView) view.findViewById(R.id.search_lanmu);
            notesTitle = (TextView) view.findViewById(R.id.search_notesTitle);
            search_authors = (TextView) view.findViewById(R.id.search_authors);
            notesType = (TextView) view.findViewById(R.id.search_notesType);

        }
    }
}

