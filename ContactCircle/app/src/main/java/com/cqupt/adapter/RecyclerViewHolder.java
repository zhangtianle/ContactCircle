package com.cqupt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {



    private  RecyclerViewHolder(View itemView) {

        super(itemView);
    }
    public static RecyclerViewHolder newInstance(View itemView){

        return  new RecyclerViewHolder(itemView);

    }



}
