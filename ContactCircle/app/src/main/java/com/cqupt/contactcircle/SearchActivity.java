package com.cqupt.contactcircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cqupt.adapter.SearchCircleListViewAdapter;
import com.cqupt.adapter.SearchFriendListViewAdapter;
import com.cqupt.app.App;
import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by ls on 15-5-4.
 */
public class SearchActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, HttpStateListener {

    @ViewInject(R.id.search_activity_tb)
    private Toolbar mToolbar;
    private SearchView mSearchView;
    @ViewInject(R.id.search_activity_lv_friend)
    private ListView mListViewFriend;
    @ViewInject(R.id.search_activity_lv_circle)
    private ListView mListViewCircle;

    @ViewInject(R.id.search_activity_rl_circle_result)
    private ViewGroup mCircleResultViewGroup;
    @ViewInject(R.id.search_activity_rl_friend_result)
    private ViewGroup mFriendResultViewGroup;


    private HttpHandlerUtils mHttpHandlerUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ViewUtils.inject(this);
        initToolbar();
        initSearchView();
        initData();
    }

    private void initData() {
        mHttpHandlerUtils = new HttpHandlerUtils();
    }

    private void initSearchView() {


    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_navigation_up);
        setTitle("");
        mToolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("查找更多好友与联络圈");
        mSearchView.setOnQueryTextListener(this);
        mSearchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        LogUtils.e("onQueryTextSubmit(): " + s);
        getCirclesInfor(s);
        return true;
    }

    private void getCirclesInfor(String s) {
        UserDBUtils mUserDBUtils = App.getAppInstance().getUserDBUtils();
        mHttpHandlerUtils.setHttpStateListener(this);
        mHttpHandlerUtils.searchCirclsInfor(App.downLoadURL, "search", mUserDBUtils.getUserId(), s);

    }

    @Override
    public boolean onQueryTextChange(String s) {
        LogUtils.e("onQueryTextChange(): " + s);
        return true;
    }


    @Override
    public void postState(String loginState) {
        if (!loginState.equals("")) {
            List<User> userList = JSONUtils.parseList(loginState, "users", User.class);
            List<Circle> circleList = JSONUtils.parseList(loginState, "circles", Circle.class);
            if (userList != null && userList.size() > 0) {
                mListViewFriend.setAdapter(new SearchFriendListViewAdapter(userList, this));
                initFriendListView();
            }
            if (circleList != null && circleList.size() > 0) {
                mListViewCircle.setAdapter(new SearchCircleListViewAdapter(circleList, this));
                initCircleListView();
            }
        }
    }

    private void initCircleListView() {
        //mListViewCircle.setOnItemClickListener(this);
        mCircleResultViewGroup.setVisibility(View.VISIBLE);
        ListAdapter listAdapter = mListViewCircle.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mListViewCircle);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListViewCircle.getLayoutParams();
        params.height = totalHeight + (mListViewCircle.getDividerHeight() * (listAdapter.getCount() - 1));
        mListViewCircle.setLayoutParams(params);


    }

    private void initFriendListView() {
        mFriendResultViewGroup.setVisibility(View.VISIBLE);
        ListAdapter listAdapter = mListViewFriend.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mListViewFriend);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListViewFriend.getLayoutParams();
        params.height = totalHeight + (mListViewFriend.getDividerHeight() * (listAdapter.getCount() - 1));
        mListViewFriend.setLayoutParams(params);

    }

    @Override
    public void refreshArticleState(String refreshState) {

    }
}
