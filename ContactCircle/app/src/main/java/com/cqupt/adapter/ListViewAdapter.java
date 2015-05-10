package com.cqupt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.app.App;
import com.cqupt.bean.Comment;
import com.cqupt.contactcircle.R;
import com.cqupt.tool.ArticleDBUtils;
import com.cqupt.tool.UserInforBitmapHandlerUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-30.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<Comment> comments;
    //   private ArticleDBUtils articleDBUtils;
    //  private String articleUUID;
    private BitmapUtils mUserBitmapUtils;

    public ListViewAdapter(List<Comment> comments, Context context) {
        //    this.articleUUID = articleUUID;
        //     articleDBUtils = App.getAppInstance().getArticleDBUtils();
        this.comments = comments;
        mUserBitmapUtils = UserInforBitmapHandlerUtils.getBitmapUtils(context.getApplicationContext());
    }

    public void addComments(List<Comment> comments) {
        if (this.comments != null) {
            this.comments.addAll(comments);
            notifyDataSetChanged();
        }
    }

    public void addComment(Comment comment) {
        if (this.comments != null) {
            this.comments.add(comment);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return comments == null ? 0 : comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments == null ? null : comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (comments == null) {
            return convertView;
        }
        Comment comment = comments.get(position);
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, null);
            mViewHolder.mUserName = (TextView) convertView.findViewById(R.id.list_view_item_user_name);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.list_view_item_time);
            mViewHolder.mComments = (TextView) convertView.findViewById(R.id.list_view_item_comment);
            mViewHolder.mUserHead = (ImageView) convertView.findViewById(R.id.list_view_item_user_head);
            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();
        // LogUtils.e("Adapter  comment is " + comment + "  position : " + position);
        mViewHolder.mUserName.setText(comment.getPubuserName());
        mViewHolder.mTime.setText(comment.getTime());
        mUserBitmapUtils.display(mViewHolder.mUserHead, comment.getPubuserHead());
        String reuserUUID = comment.getReuserUUID();
        if (reuserUUID != null && !reuserUUID.equals("")) {
            mViewHolder.mComments.setText("回复+" + comment.getReuserName()  + comment.getContent());
        } else
            mViewHolder.mComments.setText(comment.getContent());
        return convertView;
    }


    static class ViewHolder {
        TextView mTime;
        ImageView mUserHead;
        TextView mUserName;
        TextView mComments;
    }
}
