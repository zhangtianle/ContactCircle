package com.cqupt.contactcircle;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.adapter.ExpandableListAdapter;
import com.cqupt.adapter.RecyclerAdapter;
import com.cqupt.adapter.RecyclerViewHolder;
import com.cqupt.app.App;
import com.cqupt.bean.AcceptArticle;
import com.cqupt.bean.Circle;
import com.cqupt.bean.Friend;
import com.cqupt.listener.HidingScrollListener;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.ArticleBitmapHandlerUtils;
import com.cqupt.tool.ArticleDBUtils;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.cqupt.tool.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;


public class MainActivity extends ActionBarActivity implements HttpStateListener {
    @ViewInject(R.id.main_activity_tb)
    private Toolbar mToolbar;
    @ViewInject(R.id.main_activity_rv)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.main_activity_ll_toolbar_container)
    private View mToolbarContainer;
    @ViewInject(R.id.main_activity_rl_send_tool_container)
    private View mSendToolContainer;
    private View mCircleLayout;
    private boolean mCircleLayoutIsShow;
    @ViewInject(R.id.main_activity_fl_root)
    private FrameLayout mRootLayout;
    private ExpandableListView mListView;
    @ViewInject(R.id.main_activity_circle_header_tx)
    private TextView mCircleHeaderName;
    @ViewInject(R.id.main_activity_circle_header_iv)
    private ImageView mCircleHeaderIcon;
    @ViewInject(R.id.main_activity_iv_user_photo)
    private ImageView mUserPhoto;
    @ViewInject(R.id.main_activity_tx_user_name)
    private TextView mUserName;
    @ViewInject(R.id.main_activity_sl)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.main_activity_circle_tx_search)
    private TextView mSearcherTextView;


    private RecyclerAdapter mRecyclerAdapter;
    private UserDBUtils userDBUtils;
    private ArticleDBUtils articleDBUtils;
    private BitmapUtils mBitmapUtils;
    private List<AcceptArticle> articles;
    private HttpHandlerUtils httpHandlerUtils;
    private AcceptArticle praiseAcceptArticle;
    private TextView mArticleZanCounts;
    private List<Friend> friends;
    private List<Circle> circles;
    private boolean otherCircles;
    private String userUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initToolbar();
        initSwipeRefreshLayout();
        initRecycler();
        initCircleLayout();
        initUtils();
        getArticleInfor();
    }

    private void initUtils() {
        App app = App.getAppInstance();
        userDBUtils = app.getUserDBUtils();
        articleDBUtils = app.getArticleDBUtils();
        String userName = userDBUtils.getUserName();
        userUUID = userDBUtils.getUserId();
        mUserName.setText(userName);
        mBitmapUtils = ArticleBitmapHandlerUtils.getBitmapUtils(this.getApplicationContext());
        httpHandlerUtils = new HttpHandlerUtils();
        httpHandlerUtils.setHttpStateListener(this);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setProgressViewOffset(true, 240, 270);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//刷新
                LogUtils.e("去刷新了！");
                getArticleInforFromWeb(articleDBUtils.getArticleNewerTime(), "all");
            }
        });
    }

    private void initExpandableListView() {
        if (mListView == null)
            mListView = (ExpandableListView) findViewById(R.id.main_activity_circle_layout_el);
        mListView.setAdapter(new ExpandableListAdapter(this, createCirclesList(), createFriendsList()));///////////////////////////在这里处理好友
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                LogUtils.e("item click fathre " + i + " children " + i2);
                switch (i) {
                    case 1:
                        getArticleInforFromWeb("0", circles.get(i2).getId());
                        mCircleHeaderName.setText(circles.get(i2).getCircleName());
                        showOrHideCircleLayout();
                        otherCircles = true;
                        break;
                    case 0://处理个人信息展示
                        userUUID = friends.get(i2).getId();
                        otherCircles = false;
                        LogUtils.e("要战士的用户名：" + friends.get(i2).getName() + "  id是：" + userUUID);
                        goToUserInformationActivity(UserInformationActivity.class);
                        break;
                }
                return true;
            }
        });
        findViewById(R.id.main_activity_circle_tx_first_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCircleHeaderName.setText("首页");
                otherCircles = false;
                getArticleInfor();
                showOrHideCircleLayout();
            }
        });
    }

    private List<Friend> createFriendsList() {
        friends = userDBUtils.getUserFriend();
        return friends;
    }

    private List<Circle> createCirclesList() {
        circles = userDBUtils.getUserCircles();
        return circles;

    }

    private void initCircleLayout() {
        mCircleLayout = LayoutInflater.from(this).inflate(R.layout.circle_layout, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.topMargin = Utils.getToolbarHeight(this) * 2;
        mCircleLayout.findViewById(R.id.main_activity_circle_tx_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearchActivity();
            }
        });
        mCircleLayout.setLayoutParams(layoutParams);
        mCircleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }


    private void initRecycler() {
        mRecyclerView.setPadding(0, Utils.getToolbarHeight(this) * 2 + 5, 0, 0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
//        mRecyclerView.setAdapter(new RecyclerAdapter(createList()));
        mRecyclerView.setOnScrollListener(new HidingScrollListener(this) {

            @Override
            protected void onHide(int mToolbarHeight) {

                hide(mToolbarHeight);

            }

            @Override
            protected void onShow() {
                show();

            }

            @Override
            protected void onMove(int mToolbarOffset) {
                mToolbarContainer.setTranslationY(-mToolbarOffset);
                mSendToolContainer.setTranslationY(mToolbarOffset);
            }
        });

    }

    private void show() {
        mToolbarContainer.animate().translationY(0).setInterpolator(new
                AccelerateInterpolator(2)).start();
        mSendToolContainer.animate().translationY(0).setInterpolator(new
                AccelerateInterpolator(2)).start();
    }

    private void hide(int mToolbarHeight) {
        mToolbarContainer.animate().translationY(-mToolbarHeight).setInterpolator(new
                AccelerateInterpolator(2)).start();
        mSendToolContainer.animate().translationY(mToolbarHeight).setInterpolator(new
                AccelerateInterpolator(2)).start();
    }

    private void createList() {

        if (mRecyclerAdapter == null)
            mRecyclerAdapter = new RecyclerAdapter(articles, this.getApplicationContext());
        else
            mRecyclerAdapter.addOtherCirclesArticles(articles);

        mRecyclerAdapter.setOnItemClickListener(new RecyclerViewHolder.MyClickListener() {
            @Override
            public void onClickRootView(View view, int position) {
                LogUtils.e("被点击！！root ：" + position);
                goToArticleInforActivity(position);
            }

            @Override
            public void onClickPraiseView(View view, int position) {
                articles = articleDBUtils.getArticlesFromDb();
                praiseAcceptArticle = articles.get(position);
                mArticleZanCounts = ((TextView) view);
                mArticleZanCounts.setText((praiseAcceptArticle.getZanCount() + 1) + "");
                LogUtils.e("被点击！！praise: " + position + "   articleID is : " + praiseAcceptArticle.getId());
                httpHandlerUtils.postPraise(App.downLoadURL, "zan", articles.get(position).getId());
                mArticleZanCounts.setSelected(true);
                mArticleZanCounts.setClickable(false);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);


    }

    private void goToArticleInforActivity(int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setClass(this, ArticleInforActivity.class);
        startActivity(intent);

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            return true;
        } else if (id == R.id.action_exit) {
            userDBUtils.clearUserInfor();
            articleDBUtils.clearArticleDb();
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.main_activity_circle_header_rl,
            R.id.main_activity_iv_send_message,
            R.id.main_activity_iv_user_photo,
            R.id.main_activity_circle_tx_search,
            R.id.main_activity_circle_tx_first_page
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_circle_header_rl:
                showOrHideCircleLayout();
                break;
            case R.id.main_activity_iv_send_message:
                goSendMessageActivity();
                break;
            case R.id.main_activity_iv_user_photo:
                otherCircles = true;
                userUUID = "";
                goToUserInformationActivity(UserInformationActivity.class);
                break;
            case R.id.main_activity_circle_tx_search:
                goToSearchActivity();
                break;

        }
    }

    private void goToSearchActivity() {
        LogUtils.e("click is : ");
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    private void goToUserInformationActivity(Class activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        intent.putExtra("otherCircles", otherCircles);
        intent.putExtra("userUUID", userUUID);
        startActivity(intent);
    }

    private void goSendMessageActivity() {
        Intent mIntent = new Intent(this, SendArticleActivity.class);
        startActivity(mIntent);
    }

    private void showOrHideCircleLayout() {
        if (mCircleLayoutIsShow) {
            mRootLayout.removeView(mCircleLayout);//卸载circle布局
            mCircleLayoutIsShow = false;
            mCircleHeaderIcon.setBackgroundResource(R.mipmap.ic_arrow_up);
        } else {
            show();
            prepareCircleLayoutInAnim();
            mRootLayout.addView(mCircleLayout);//加载circle布局
            initExpandableListView();
            mCircleLayoutIsShow = true;
            mCircleHeaderIcon.setBackgroundResource(R.mipmap.ic_arrow_down);
        }
    }


    private void prepareCircleLayoutInAnim() {
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimator(LayoutTransition.APPEARING, transition.getAnimator(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING, transition.getAnimator(LayoutTransition.DISAPPEARING));
        mRootLayout.setLayoutTransition(transition);
    }


    @Override
    public void postState(String loginState) {
        if (loginState != null && !loginState.equals("")) {
            LogUtils.e("赞的返回值：" + loginState);
            praiseAcceptArticle.setZanCount(Integer.parseInt(loginState));
            articleDBUtils.updateZanCounts(praiseAcceptArticle);
            mArticleZanCounts.setText(loginState);
        }
    }

    /**
     * 获取文章回调
     */
    @Override
    public void refreshArticleState(String refreshState) {
        articles = JSONUtils.parseList(refreshState, "logicArticles", AcceptArticle.class);
        mSwipeRefreshLayout.setRefreshing(false);
        if (refreshState.equals("false"))
            return;



        if (otherCircles) {
            if (articles != null && articles.size() > 0)
                createList();
            else//处理没数据的情况
                mRecyclerAdapter.removeAllDatas();
            return;
        }

        if (mRecyclerAdapter != null && articles != null && !articles.toString().equals("[]")) {//处理首页数据刷新
            LogUtils.e(" 这时候是处理刷新数据的 " + articles);
            mRecyclerAdapter.addArticles(articles);
        } else if (mRecyclerAdapter == null && articles != null && !articles.toString().equals("[]")) {//处理其他圈子数据的
            LogUtils.e(" 这时候是处理其他圈子数据的 " + articles);
            createList();
        }
        if (articles != null && !articles.toString().equals("[]")) {
            articleDBUtils.saveArticleToDb(articles);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void getArticleInfor() {
        //1.先检查数据库中是否有缓存
        articles = articleDBUtils.getArticlesFromDb();
        if (articles != null && articles.size() > 0) {
            LogUtils.e("数据库中有缓存!!!");
            createList();
            for (AcceptArticle acceptArticle : articles) {
                LogUtils.e("数据库中的文章数据是： " + acceptArticle.toString());
            }
        } else {
            //2.去网络获取数据
            getArticleInforFromWeb("0", "all");
        }
    }

    public void getArticleInforFromWeb(String time, String circle) {
        String userUUID = userDBUtils.getUserId();
        LogUtils.e("去请求网络！！  userUUID is :" + userUUID);
        httpHandlerUtils.postRefreshArticle(App.downLoadURL, "article", userUUID, circle, "0", time);
    }
}
