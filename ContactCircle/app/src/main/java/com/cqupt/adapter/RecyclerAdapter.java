package com.cqupt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public RecyclerAdapter(List<String> data) {

        this.mData = data;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        View recyclerViewItem =LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item,viewGroup,false);
        return  RecyclerViewHolder.newInstance(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
