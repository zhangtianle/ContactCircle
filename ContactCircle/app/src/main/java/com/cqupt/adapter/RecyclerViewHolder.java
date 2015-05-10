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
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
    int position;
    MyClickListener myClickListener;

    public RecyclerViewHolder(View itemView, int type, MyClickListener myClickListener) {
        super(itemView);

        if (type == RecyclerAdapter.VIEW_TYPE_NORMAL) {
            this.myClickListener = myClickListener;
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
            itemView.setOnClickListener(this);
            mArticlePraise.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View view) {
        if (myClickListener != null & view.equals(mArticlePraise))//这是处理点赞情形
            myClickListener.onClickPraiseView(view, getAdapterPosition());
        else
            myClickListener.onClickRootView(view, getAdapterPosition());
    }


    public interface MyClickListener {

        public void onClickRootView(View view, int position);

        public void onClickPraiseView(View view, int position);
    }

    public void setMyClickListener(MyClickListener clickListener) {
        this.myClickListener = clickListener;
    }
}
