package cn.incongress.endorcrinemagazine.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CurrentBean;
import cn.incongress.endorcrinemagazine.widget.AnimatedExpandableListView;

/**
 * Created by Admin on 2017/4/20.
 */

public class CurrentAdapater implements ExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CurrentBean> list;
    private CurrentBean currentBean;
    private ChooseBean chooseBean;

    public CurrentAdapater(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(List<CurrentBean> items) {
        this.list = items;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        Log.e("GYW","----父级"+list.size());
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("GYW","----子级"+list.get(groupPosition).getList().size());
        return list.get(groupPosition).getList().size();
    }

    @Override
    public CurrentBean getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public ChooseBean getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CurrentHolder holder;
        currentBean  = getGroup(groupPosition);
        if (convertView == null) {
            holder = new CurrentHolder();
            convertView = inflater.inflate(R.layout.item_current, parent, false);
            holder.lanmu_text = (TextView) convertView.findViewById(R.id.current_title);
            convertView.setTag(holder);
        } else {
            holder = (CurrentHolder) convertView.getTag();
        }
       /* String title = context.getString(R.string.info_blank)+currentBean.getLanmu().substring(1,currentBean.getLanmu().length()-1)+context.getString(R.string.info_blank);
        holder.lanmu_text.setText(title);

        Drawable left = context.getResources().getDrawable(R.mipmap.left);
        Drawable right = context.getResources().getDrawable(R.mipmap.right);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        left.setBounds(0, 2, 15, 40);
        right.setBounds(0, 2, 15, 40);
        holder.lanmu_text.setCompoundDrawables(left,null,right,null);*/
        holder.lanmu_text.setText(currentBean.getLanmu().substring(1,currentBean.getLanmu().length()-1));
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChooseHolder holder;
        chooseBean =  getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChooseHolder();
            convertView = inflater.inflate(R.layout.item_current_choose, parent, false);
            holder.notesTitle_text = (TextView) convertView.findViewById(R.id.current_notesTitle);
            holder.authors_text = (TextView) convertView.findViewById(R.id.current_authors);
            holder.notesType_text = (TextView) convertView.findViewById(R.id.current_notesType);
            holder.readCount_text = (TextView) convertView.findViewById(R.id.current_readCount);
            convertView.setTag(holder);
        } else {
            holder = (ChooseHolder) convertView.getTag();
        }

        holder.authors_text.setText(chooseBean.getAuthors());
        holder.notesTitle_text.setText(chooseBean.getNotesTitle());
        holder.notesType_text.setText(chooseBean.getNotesType()+context.getString(R.string.choose_piecemeal_text2));
        holder.readCount_text.setText(chooseBean.getReadCount()+context.getString(R.string.choose_piecemeal_text4));

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }


    private static class ChooseHolder {
        TextView readCount_text,authors_text,notesTitle_text,notesType_text;

    }

    private static class CurrentHolder {
        TextView lanmu_text;
    }

}
