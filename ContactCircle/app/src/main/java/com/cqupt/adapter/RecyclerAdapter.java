package com.cqupt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cqupt.app.App;
import com.cqupt.bean.AcceptArticle;
import com.cqupt.bean.Attachment;
import com.cqupt.bean.Comment;
import com.cqupt.contactcircle.R;
import com.cqupt.tool.ArticleBitmapHandlerUtils;
import com.cqupt.tool.ArticleDBUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AcceptArticle> articles;
    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    private BitmapUtils mBitmapUtils;
    private ArticleDBUtils mArticleDBUtils;

    private RecyclerViewHolder.MyClickListener myClickListener;

    public RecyclerAdapter(List<AcceptArticle> data, Context context) {
        LogUtils.e("articles is : " + data);
        this.articles = data;
        mBitmapUtils = ArticleBitmapHandlerUtils.getBitmapUtils(context);
        mArticleDBUtils = App.getAppInstance().getArticleDBUtils();
    }


    public void addArticles(List<AcceptArticle> articles) {
        this.articles.addAll(0, articles);
        notifyItemInserted(0);//更新数据
    }


    public void addOtherCirclesArticles(List<AcceptArticle> articles) {
        this.articles.addAll(articles);
        notifyDataSetChanged();//更新数据
    }

    public void removeAllDatas() {
        articles.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        Context mContext = viewGroup.getContext();
        switch (i) {
            case VIEW_TYPE_NORMAL:
                View recyclerViewItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, viewGroup, false);
                viewHolder = new RecyclerViewHolder(recyclerViewItem, VIEW_TYPE_NORMAL, myClickListener);
                break;
            case VIEW_TYPE_FOOTER:
                View recyclerViewFooterItem = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_footer, viewGroup, false);
                viewHolder = new RecyclerViewHolder(recyclerViewFooterItem, VIEW_TYPE_FOOTER, myClickListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        LogUtils.e("onBindViewHolder position : " + i);
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
        //  List<Comment> comments = mArticleDBUtils.getCommentsFromDb(article.getId());
        // List<Attachment> attachments = mArticleDBUtils.getAttachmentsFromDb(article.getId());
        //   if (comments != null && comments.size() > 0)
        mRecyclerViewHolder.mArticleComments.setText(article.getComments() + "");
        //  if (attachments != null && attachments.size() > 0)
        mRecyclerViewHolder.mArticleAttachments.setText(article.getAttachments() + "");
        mRecyclerViewHolder.mCircle.setText(article.getCircle());
        mRecyclerViewHolder.mArticlePraise.setText(article.getZanCount() + "");
        //mRecyclerViewHolde250.153.203r.mArticleShares.setText(article.get);
        mBitmapUtils.display(mRecyclerViewHolder.mArticleImage, article.getPhotoURL());
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


    /**
     * 处理图片下载
     */
    public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
        private final RecyclerViewHolder holder;

        public CustomBitmapLoadCallBack(RecyclerViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
//            this.holder.imgPb.setProgress((int) (current * 100 / total));
        }

        @Override
        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            //super.onLoadCompleted(container, uri, bitmap, config, from);
            fadeInDisplay(container, bitmap);
//            this.holder.imgPb.setProgress(100);
        }
    }

    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)
                });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    public void setOnItemClickListener(RecyclerViewHolder.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


}
