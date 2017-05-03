package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.bean.CollectionBean;

/**
 * Created by Admin on 2017/4/27.
 */

public class CollectionAdapater extends RecyclerView.Adapter<CollectionAdapater.SearchViewHolder> {
    //
    private Context mContext;
    private List<CollectionBean> mArrayList = new ArrayList<>();

    public CollectionAdapater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CollectionBean> mArrayList) {
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
    public interface onLongItemClickLitener{
        void onLongItemClick(View view, int position);
    }

    private CollectionAdapater.OnItemClickLitener mOnItemClickLitener;
    private CollectionAdapater.onLongItemClickLitener mOnLongItemClickLitener;

    public void setOnItemClickLitener(CollectionAdapater.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public void setOnLongItemClickLitener(CollectionAdapater.onLongItemClickLitener mOnLongItemClickLitener){
        this.mOnLongItemClickLitener = mOnLongItemClickLitener;
    }
    @Override
    public void onBindViewHolder(final CollectionAdapater.SearchViewHolder holder, final int position) {

        String title = mContext.getString(R.string.info_blank)+mArrayList.get(position).getLanmu().substring(1,mArrayList.get(position).getLanmu().length()-1)+mContext.getString(R.string.info_blank);
        holder.search_lanmu.setText(title);

        Drawable left = mContext.getResources().getDrawable(R.mipmap.left);
        Drawable right = mContext.getResources().getDrawable(R.mipmap.right);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        left.setBounds(0, 2, 15, 40);
        right.setBounds(0, 2, 15, 40);
        holder.search_lanmu.setCompoundDrawables(left,null,right,null);

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

        if (mOnLongItemClickLitener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnLongItemClickLitener.onLongItemClick(holder.itemView, pos);
                    return true;
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


