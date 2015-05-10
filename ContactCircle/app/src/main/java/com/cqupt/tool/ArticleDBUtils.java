package com.cqupt.tool;

import android.content.Context;
import android.util.Log;

import com.cqupt.bean.AcceptArticle;
import com.cqupt.bean.Attachment;
import com.cqupt.bean.Comment;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class ArticleDBUtils {

    private DbUtils mArticleDbUtils;
    private DbUtils mAttachmentDbUtils;
    private DbUtils mCommentDbUtils;

    public ArticleDBUtils(Context context) {
        Log.d("DBUtils", "getInstance()");
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("article.db");
        mArticleDbUtils = DbUtils.create(config);
        DbUtils.DaoConfig config1 = new DbUtils.DaoConfig(context);
        config1.setDbName("attachment.db");
        mAttachmentDbUtils = DbUtils.create(config1);
//        DbUtils.DaoConfig config2 = new DbUtils.DaoConfig(context);
//        config2.setDbName("comment.db");
//        mCommentDbUtils = DbUtils.create(config2);

    }


    public void saveArticleToDb(List<AcceptArticle> articleList) {
        Log.d("DBUtils", "saveArticleToDb()");
        /**存储文章*/
        if (mArticleDbUtils == null) {
            return;
        }
        try {
            mArticleDbUtils.saveAll(articleList);
        } catch (DbException e) {
            e.printStackTrace();
        }

        /**存储附件*/
//        for (AcceptArticle acceptArticle : articleList) {
//            List<Attachment> attachments = acceptArticle.getAttachments();
//            List<Comment> comments = acceptArticle.getComments();
//            try {
//                if (attachments != null && attachments.size() > 0)
//                    mAttachmentDbUtils.saveAll(attachments);
//                if (comments != null && comments.size() > 0)
//                    mCommentDbUtils.saveAll(comments);
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//        }


    }

//    public void saveComments(List<Comment> comments) {
//        try {
//            mCommentDbUtils.saveAll(comments);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveComment(Comment comment) {
//        try {
//            mCommentDbUtils.save(comment);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }

//    public String getCommentsNewerTime(String articleUUID) {
//        Comment comment = null;
//        String time = null;
//        try {
//            comment = mCommentDbUtils.findFirst(Selector.from(Comment.class).where("articleUUID", "=", articleUUID)
//                    .orderBy("time", true));
//            if (comment != null) {
//                time = comment.getTime();
//            } else
//                time = "1999-01-01 00:00:00";
//        } catch (DbException e) {
//            e.printStackTrace();
//
//        }
//        LogUtils.e(" getArticlesFromDb() 最新文章的时间 : " + time);
//        return time;
//    }


    public void clearArticleDb() {
        Log.d("DBUtils", "clearArticleDb()");
        if (mArticleDbUtils == null)
            return;
        try {
            mArticleDbUtils.deleteAll(AcceptArticle.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void closeArticleInforDbUtils() {
        mArticleDbUtils.close();
    }


    public List<AcceptArticle> getArticlesFromDb() {
        List<AcceptArticle> articles = null;
        try {
            articles = mArticleDbUtils.findAll(Selector.from(AcceptArticle.class)
                    .orderBy("time", true));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return articles;
    }

//    public List<Attachment> getAttachmentsFromDb(String articleUUID) {
//        List<Attachment> attachments = null;
//        try {
//            //  LogUtils.e("article id is : "+articleUUID );
//            attachments = mAttachmentDbUtils.findAll(Selector.from(Attachment.class).where("articleUUID", "=", articleUUID));
//            if (attachments != null)
//                LogUtils.e("getAttachmentFromDb attachments size  is : " + attachments.size());
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return attachments;
//    }

//    public List<Comment> getCommentsFromDb(String articleUUID) {
//        List<Comment> comments = null;
//        try {
//            comments = mCommentDbUtils.findAll(Selector.from(Comment.class).where("articleUUID", "=", articleUUID));
//            if (comments != null) {
//                LogUtils.e("getCommentsFromDb comments size  is : " + comments.size());
////                for (Comment comment : comments)
////                   LogUtils.e("获取的评论内容是：" + comment);
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return comments;
//    }


    public String getArticleNewerTime() {
        AcceptArticle article = null;
        String time = null;
        try {
            article = mArticleDbUtils.findFirst(Selector.from(AcceptArticle.class)
                    .orderBy("time", true));
            if (article != null) {
                time = article.getTime();
            }
        } catch (DbException e) {
            e.printStackTrace();

        }
        LogUtils.e(" getArticlesFromDb() 最新文章的时间 : " + time);
        return time;
    }

    public void updateZanCounts(AcceptArticle acceptArticle) {

        try {
            mArticleDbUtils.update(acceptArticle, "zanCount");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateCommentsSize(AcceptArticle acceptArticle) {

        try {
            mArticleDbUtils.update(acceptArticle, "comments");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

//    public void updateAll(AcceptArticle acceptArticle) {
//
//        try {
//            mArticleDbUtils.update(acceptArticle, "comments");
//        } catch (DbException e) {
//            LogUtils.e("数据更新文章错误！" + e);
//            e.printStackTrace();
//        }
//    }


}
