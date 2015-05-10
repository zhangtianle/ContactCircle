package com.cqupt.contactcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cqupt.adapter.FullyLinearLayoutManager;
import com.cqupt.adapter.RecyclerAdapter;
import com.cqupt.adapter.RecyclerViewHolder;
import com.cqupt.app.App;
import com.cqupt.bean.AcceptArticle;
import com.cqupt.bean.Friend;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.cqupt.view.ObservableScrollView;
import com.cqupt.listener.ScrollViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by ls on 15-4-21.
 */
public class UserInformationActivity extends ActionBarActivity implements ScrollViewListener, HttpStateListener {

    @ViewInject(R.id.user_information_activity_tb)
    private Toolbar mToolbar;
    @ViewInject(R.id.user_information_activity_rv)
    private RecyclerView nestedRecyclerView;
    @ViewInject(R.id.user_information_activity_sv)
    private ObservableScrollView mScrollView;
    @ViewInject(R.id.user_information_activity_tv_user_name)
    private TextView mUserName;
    @ViewInject(R.id.user_information_activity_tv_user_academy)
    private TextView mUserAcademy;
    @ViewInject(R.id.user_information_activity_tv_user_class)
    private TextView mUserClass;
    @ViewInject(R.id.user_information_activity_tv_user_circle_count)
    private TextView mUserCounts;

    private UserDBUtils mDbUtils;
    private HttpHandlerUtils mHttpHandlerUtils;
    private RecyclerAdapter adapter;
    private List<AcceptArticle> acceptArticles;

    private String userUUID;
    private boolean otherCircles;
    private Friend mFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ViewUtils.inject(this);
        initUtils();
        initToolbar();
        initNestedRecyclerView();
        initScrollView();
        initData();
        initUserInforView();
        getUserArticleInfor(otherCircles, userUUID);

    }

    private void initData() {
        otherCircles = getIntent().getBooleanExtra("otherCircles", true);
        userUUID = getIntent().getStringExtra("userUUID");
        mFriend = mDbUtils.getFriendById(userUUID);
        LogUtils.e("获得的otherCircles：" + otherCircles + "  userUUID:" + userUUID );
    }

    private void getUserArticleInfor(boolean otherCircle, String uuid) {
        String userUUID = null;
        if (otherCircle)
            userUUID = mDbUtils.getUserId();
        else
            userUUID = uuid;
        LogUtils.e("即将去处理的otherCircles：" + otherCircles + "  userUUID:" + userUUID);
        mHttpHandlerUtils.getUserArticleInfor(App.downLoadURL, "myArticle", userUUID);


    }

    private void initUserInforView() {
        if (otherCircles) {
            mUserName.setText(mDbUtils.getUserName());
            mUserAcademy.setText(mDbUtils.getUserAcademy());
            mUserClass.setText(mDbUtils.getUserClass());
            mUserCounts.setText("已加入" + mDbUtils.getUserCircles().size() + "个联络圈");
        } else {
            mUserName.setText(mFriend.getName());
            mUserAcademy.setText(mFriend.getCollege());
            mUserClass.setText(mFriend.getFriendClass());
            mUserCounts.setText("已加入" + mFriend.getCirclesCounts() + "个联络圈");
        }
    }

    private void initUtils() {
        mDbUtils = App.getAppInstance().getUserDBUtils();
        mHttpHandlerUtils = new HttpHandlerUtils();
        mHttpHandlerUtils.setHttpStateListener(this);
    }

    private void initScrollView() {
        mScrollView.setScrollViewListener(this);
        mScrollView.scrollBy(0, 0);
    }

    private void initNestedRecyclerView() {
        nestedRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        //nestedRecyclerView.setAdapter(new RecyclerAdapter(createDataList(), getApplicationContext()));

    }

    private void createDataList(final List<AcceptArticle> acceptArticles) {
        adapter = new RecyclerAdapter(acceptArticles, getApplicationContext());
        nestedRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewHolder.MyClickListener() {
            @Override
            public void onClickRootView(View view, int position) {
                LogUtils.e("被点击！！root ：" + position);
                goToArticleInforActivity(position);

            }

            @Override
            public void onClickPraiseView(View view, int position) {
                ((TextView) view).setText((acceptArticles.get(position).getZanCount() + 1) + "");
                mHttpHandlerUtils.postPraise(App.downLoadURL, "zan", acceptArticles.get(position).getId());
                view.setSelected(true);
                view.setClickable(false);
            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_navigation_up);
        setTitle("");
        mToolbar.setSubtitle(mDbUtils.getUserName());
        mToolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {


    }

    @Override
    public void postState(String loginState) {


    }

    /**
     * 获取User发表的文章
     */
    @Override
    public void refreshArticleState(String refreshState) {
        if (refreshState.equals("false"))
            return;
        List<AcceptArticle> acceptArticles = JSONUtils.parseList(refreshState, "logicArticles", AcceptArticle.class);
        if (acceptArticles != null && acceptArticles.size() > 0) {
            this.acceptArticles = acceptArticles;
            createDataList(acceptArticles);
        }

    }

    private void goToArticleInforActivity(int position) {
        Intent intent = new Intent();
        intent.putExtra("user_article", acceptArticles.get(position));
        intent.setClass(this, ArticleInforActivity.class);
        startActivity(intent);

    }
}
