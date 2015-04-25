package com.cqupt.tool;

import android.content.Context;
import android.util.Log;

import com.cqupt.bean.Article;
import com.cqupt.bean.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class ArticleInforDBUtils {

    private DbUtils mArticleDbUtils;

    public ArticleInforDBUtils(Context context) {
        Log.d("DBUtils", "getInstance()");
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("article.db");
        mArticleDbUtils = DbUtils.create(config);

    }

//    public void saveUserToDb(Context context, User user) {
//        Log.d("DBUtils", "saveUserToDb()");
//        try {
//            mUserInforDbUtils.save(user); // 使用saveBindingId保存实体时会为实体的id赋值
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//
//        getUserId();
//    }


//    public String getUserId() {
//
//        try {
//            List<User> userList = mUserInforDbUtils.findAll(User.class);
//            Log.d("DBUtils", "userList.size" + userList.size());
//            return userList.get(0).getId();
//        } catch (DbException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    public void saveArticleToDb(Context context, List<Article> articleList) {
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
            mArticleDbUtils.deleteAll(Article.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void closeUserInforDbUtils() {
        mArticleDbUtils.close();
    }

}
