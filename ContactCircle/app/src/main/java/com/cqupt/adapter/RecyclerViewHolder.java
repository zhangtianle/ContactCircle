package com.cqupt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.contactcircle.R;
import com.cqupt.view.CircleImageView;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    CircleImageView mUserHead;
    TextView mUserName;
    TextView mUpdateTime;
    TextView mArticleType;
    TextView mArticleTitle;
    TextView mArticleAbstract;
    ImageView mArticleImage;
    TextView mArticlePraise;
    TextView mArticleAttachments;
    TextView mArticleComments;
    TextView mArticleShares;
    TextView mCircle;


    private RecyclerViewHolder(View itemView, int type) {
        super(itemView);
        if (type == RecyclerAdapter.VIEW_TYPE_NORMAL) {
            mUserHead = (CircleImageView) itemView.findViewById(R.id.recycler_item_cv_user_head);
            mUserName = (TextView) itemView.findViewById(R.id.recycler_item_tx_user_name);
            mUpdateTime = (TextView) itemView.findViewById(R.id.recycler_item_tx_update_time);
            mArticleType = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_type);
            mArticleTitle = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_title);
            mArticleAbstract = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_abstract);
            mArticlePraise = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_praise);
            mArticleAttachments = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_attachment);
            mArticleComments = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_comment);
            //  mArticleShares = (TextView) itemView.findViewById(R.id.recycler_item_tx_article_share);
            mArticleImage = (ImageView) itemView.findViewById(R.id.recycler_item_iv_article_image);
            mCircle = (TextView) itemView.findViewById(R.id.recycler_item_tx_circle);
        }

    }

    public static RecyclerViewHolder newInstance(View itemView, int type) {

        return new RecyclerViewHolder(itemView, type);


    }


}
