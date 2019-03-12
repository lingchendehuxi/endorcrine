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
import cn.incongress.endorcrinemagazine.utils.flow_layout.FlowLayout;
import cn.incongress.endorcrinemagazine.utils.flow_layout.TagAdapter;

/**
 * Created by Admin on 2017/4/20.
 */

//public class SearchColumnAdapater extends RecyclerView.Adapter<SearchColumnAdapater.SearchViewHolder> {
    public class SearchColumnAdapater extends TagAdapter<SearchColumnBean> {
        private List<SearchColumnBean> mBeans;
        private Context mContext;


        public SearchColumnAdapater(Context ctx, List<SearchColumnBean> datas) {
            super(datas);
            mBeans = datas;
            this.mContext = ctx;
        }

    public void setData(List<SearchColumnBean> mArrayList) {
        this.mBeans = mArrayList;
    }

        @Override
        public void setSelectedList(int... pos) {
            super.setSelectedList(pos);
        }

        @Override
        public int getCount() {
            return mBeans.size();
        }

        @Override
        public SearchColumnBean getItem(int position) {
            return mBeans.get(position);
        }



    @Override
        public View getView(FlowLayout parent, int position, SearchColumnBean searchColumnBean) {
            View tv = (View) LayoutInflater.from(mContext).inflate(R.layout.item_search_column, parent, false);
        TextView colimn = (TextView) tv.findViewById(R.id.search_choice_column);

            if (mBeans != null && mBeans.size() > 0) {
                colimn.setText(mBeans.get(position).getLanmu());

                if(mBeans.get(position).isSelected()) {
                    colimn.setTextColor(mContext.getResources().getColor(R.color.white));
                    colimn.setEnabled(true);
                }else{
                    colimn.setTextColor(mContext.getResources().getColor(R.color.search_back));
                    colimn.setEnabled(false);
                }

            }
            return tv;
        }

        /**
         * 获取讲者列表
         *
         * @return
         */
        public List<SearchColumnBean> getSpeakerList() {
            return mBeans;
        }
   /* //
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
        RelativeLayout relativeLayout;

        public SearchViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.search_choice_column);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.search_column_layout);
        }
    }*/
}
