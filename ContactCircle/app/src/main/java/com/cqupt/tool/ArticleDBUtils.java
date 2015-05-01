package com.cqupt.tool;

import android.content.Context;
import android.util.Log;

import com.cqupt.bean.AcceptArticle;
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

    public ArticleDBUtils(Context context) {
        Log.d("DBUtils", "getInstance()");
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("article.db");
        mArticleDbUtils = DbUtils.create(config);

    }


    public void saveArticleToDb(Context context, List<AcceptArticle> articleList) {
        Log.d("DBUtils", "saveArticleToDb()");
        if (mArticleDbUtils == null) {
            return;
        }
        try {
            mArticleDbUtils.saveAll(articleList);
        } catch (DbException e) {

            e.printStackTrace();

        }
    }


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
        try {

            List<AcceptArticle> articles = mArticleDbUtils.findAll(Selector.from(AcceptArticle.class)
                    .orderBy("time", true));
            return articles;
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }


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

}
