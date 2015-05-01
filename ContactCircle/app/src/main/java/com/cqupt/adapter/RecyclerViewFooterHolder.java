package com.cqupt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cqupt.view.CircleImageView;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerViewFooterHolder extends RecyclerView.ViewHolder {








    private RecyclerViewFooterHolder(View itemView) {


        super(itemView);
    }
    public static RecyclerViewFooterHolder newInstance(View itemView){

        return  new RecyclerViewFooterHolder(itemView);


    }





}
