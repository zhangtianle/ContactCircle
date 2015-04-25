package com.cqupt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.contactcircle.R;

import java.util.List;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mData;
    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    private boolean isFooter;

    public RecyclerAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        Context mContext = viewGroup.getContext();
        switch (i) {
            case VIEW_TYPE_NORMAL:
                View recyclerViewItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, viewGroup, false);
                viewHolder = RecyclerViewHolder.newInstance(recyclerViewItem);
                break;
            case VIEW_TYPE_FOOTER:
                View recyclerViewFooterItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_footer, viewGroup, false);
                viewHolder = RecyclerViewHolder.newInstance(recyclerViewFooterItem);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("Adapter", "onBindViewHolder  " + i);
        switch (getItemViewType(i)){
            case VIEW_TYPE_NORMAL:
                bindItemViewHolder();
                break;
            case VIEW_TYPE_FOOTER:
                bindFooterViewHolder();
                break;
        }
    }

    private void bindFooterViewHolder() {



    }

    private void bindItemViewHolder() {

    }

    @Override
    public int getItemCount() {
        return getContentItemCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("Adapter", "position>=getItemCount()" + position);
        if (position == getContentItemCount())
            return VIEW_TYPE_FOOTER;
        return VIEW_TYPE_NORMAL;

    }

    public int getContentItemCount() {
        return 10;
    }

}
