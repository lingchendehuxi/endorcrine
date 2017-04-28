package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.incongress.endorcrinemagazine.R;

/**
 * Created by Admin on 2017/4/18.
 */

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.MyViewHolder> {
    //
    private Context mContext;
    private ArrayList<String> mArrayList = new ArrayList<>();

    public PastAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<String> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_past_recycler, parent,
                false));
        return holder;
    }



    public void removeData(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);
    }
    //...
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
               String s = mArrayList.get(position);
        String a = s.substring(0,4);
        String b = s.substring(5);
        holder.year.setText(a);
        holder.moth.setText(b);

        // 如果设置了回调，则设置点击事件
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView year,moth;

        public MyViewHolder(View view) {
            super(view);
            year = (TextView) view.findViewById(R.id.past_itemtxt_year);
            moth = (TextView) view.findViewById(R.id.past_itemtxt_moth);
        }
    }
}

