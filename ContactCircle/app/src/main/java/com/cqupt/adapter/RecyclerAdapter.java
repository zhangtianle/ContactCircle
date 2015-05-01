package com.cqupt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.bean.AcceptArticle;
import com.cqupt.contactcircle.R;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AcceptArticle> articles;
    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    private boolean isFooter;

    public RecyclerAdapter(List<AcceptArticle> data) {
        LogUtils.e("articles is : " + data);
        this.articles = data;
    }


    public void addArticles(List<AcceptArticle> articles) {
        this.articles.addAll(0, articles);
        notifyItemInserted(0);//更新数据
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        Context mContext = viewGroup.getContext();
        switch (i) {
            case VIEW_TYPE_NORMAL:
                View recyclerViewItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, viewGroup, false);
                viewHolder = RecyclerViewHolder.newInstance(recyclerViewItem, VIEW_TYPE_NORMAL);
                break;
            case VIEW_TYPE_FOOTER:
                View recyclerViewFooterItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_footer, viewGroup, false);
                viewHolder = RecyclerViewHolder.newInstance(recyclerViewFooterItem, VIEW_TYPE_FOOTER);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("Adapter", "onBindViewHolder  " + i);
        switch (getItemViewType(i)) {
            case VIEW_TYPE_NORMAL:
                bindItemViewHolder(viewHolder, i);
                break;
            case VIEW_TYPE_FOOTER:
                bindFooterViewHolder();
                break;
        }
    }

    private void bindFooterViewHolder() {


    }

    /**
     * 处理数据
     */
    private void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (articles == null)
            return;
        AcceptArticle article = articles.get(i);
        RecyclerViewHolder mRecyclerViewHolder = (RecyclerViewHolder) viewHolder;
        mRecyclerViewHolder.mUserName.setText(article.getName());
        mRecyclerViewHolder.mArticleTitle.setText(article.getTitle());
        mRecyclerViewHolder.mArticleAbstract.setText(article.getContent());
        mRecyclerViewHolder.mUpdateTime.setText(article.getTime());
        mRecyclerViewHolder.mArticleType.setText(article.getType());
        if (article.getComments() != null)
            mRecyclerViewHolder.mArticleComments.setText(article.getComments().size() + "");
        if (article.getAttachments() != null)
            mRecyclerViewHolder.mArticleAttachments.setText(article.getAttachments().size() + "");
        mRecyclerViewHolder.mCircle.setText(article.getCircle());
        mRecyclerViewHolder.mArticlePraise.setText(article.getZanCount() + "");
        //mRecyclerViewHolder.mArticleShares.setText(article.get);
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("Adapter", "position>=getItemCount()" + position);
        if (position == getContentItemCount())
            return VIEW_TYPE_FOOTER;
        return VIEW_TYPE_NORMAL;

    }

    public int getContentItemCount() {
        if (articles == null)
            return 10;
        else
            return articles.size();
    }

}
