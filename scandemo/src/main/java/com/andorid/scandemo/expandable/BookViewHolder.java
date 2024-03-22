package com.andorid.scandemo.expandable;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andorid.scandemo.R;
import com.h4de5ing.expandablerecyclerview.holder.BaseViewHolder;
public class BookViewHolder extends BaseViewHolder {

    public TextView tvName;
    public TextView tvTitle;

    public BookViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx,itemView, viewType);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
    }

    @Override
    public int getGroupViewResId() {
        return R.id.group;
    }

    @Override
    public int getChildViewResId() {
        return R.id.child;
    }
}
