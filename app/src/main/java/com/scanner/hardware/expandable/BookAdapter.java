package com.scanner.hardware.expandable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h4de5ing.expandablerecyclerview.adapter.BaseRecyclerViewAdapter;
import com.h4de5ing.expandablerecyclerview.bean.RecyclerViewData;
import com.scanner.hardware.R;

import java.util.List;

public class BookAdapter extends BaseRecyclerViewAdapter<String, GroupTitle, BookViewHolder> {

    private Context ctx;
    private LayoutInflater mInflater;

    public BookAdapter(Context ctx, List<RecyclerViewData> datas) {
        super(ctx, datas);
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    /**
     * head View数据设置
     *
     * @param holder
     * @param groupPos
     * @param position
     * @param groupData
     */
    @Override
    public void onBindGroupHolder(BookViewHolder holder, int groupPos, int position, String groupData) {
        holder.tvTitle.setText(groupData);
    }

    /**
     * child View数据设置
     *
     * @param holder
     * @param groupPos
     * @param childPos
     * @param position
     * @param childData
     */
    @Override
    public void onBindChildpHolder(BookViewHolder holder, int groupPos, int childPos, int position, GroupTitle childData) {
        holder.tvName.setText(childData.getName());
    }

    @Override
    public View getGroupView(ViewGroup parent) {
        return mInflater.inflate(R.layout.title_item_layout, parent, false);
    }

    @Override
    public View getChildView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_layout, parent, false);
    }

    @Override
    public BookViewHolder createRealViewHolder(Context ctx, View view, int viewType) {
        return new BookViewHolder(ctx, view, viewType);
    }

    /**
     * true 全部可展开
     * false  同一时间只能展开一个
     */
    @Override
    public boolean canExpandAll() {
        return false;
    }
}
