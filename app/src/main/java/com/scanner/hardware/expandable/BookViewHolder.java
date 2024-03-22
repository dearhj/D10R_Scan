package com.scanner.hardware.expandable;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.h4de5ing.expandablerecyclerview.holder.BaseViewHolder;
import com.scanner.hardware.R;

public class BookViewHolder extends BaseViewHolder {

    public TextView tvName;
    public TextView tvTitle;

    public BookViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx, itemView, viewType);
        tvName = itemView.findViewById(R.id.tv_name);
        tvTitle = itemView.findViewById(R.id.tv_title);
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
