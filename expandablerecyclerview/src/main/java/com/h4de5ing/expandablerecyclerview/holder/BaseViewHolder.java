package com.h4de5ing.expandablerecyclerview.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_PARENT = 1;
    public static final int VIEW_TYPE_CHILD = 2;

    public ViewGroup childView;

    public ViewGroup groupView;

    public BaseViewHolder(Context ctx, View itemView, int viewType) {
        super(itemView);
        switch (viewType) {
            case VIEW_TYPE_PARENT -> groupView = itemView.findViewById(getGroupViewResId());
            case VIEW_TYPE_CHILD -> childView = itemView.findViewById(getChildViewResId());
        }
    }

    /**
     * return ChildView root layout id
     */
    public abstract int getChildViewResId();

    /**
     * return GroupView root layout id
     */
    public abstract int getGroupViewResId();


}