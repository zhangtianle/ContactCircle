package com.cqupt.contactcircle;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import com.cqupt.adapter.FullyLinearLayoutManager;
import com.cqupt.adapter.RecyclerAdapter;
import com.cqupt.view.ObservableScrollView;
import com.cqupt.view.ScrollViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by ls on 15-4-21.
 */
public class UserInformation extends ActionBarActivity implements ScrollViewListener {

    @ViewInject(R.id.user_information_activity_tb)
    private Toolbar mToolbar;
    @ViewInject(R.id.user_information_activity_rv)
    private RecyclerView nestedRecyclerView;
    @ViewInject(R.id.user_information_activity_sv)
    private ObservableScrollView mScrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ViewUtils.inject(this);
        initToolbar();
        initNestedRecyclerView();
        initScrollView();
    }

    private void initScrollView() {
        mScrollView.setScrollViewListener(this);
    }

    private void initNestedRecyclerView() {
        nestedRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        nestedRecyclerView.setAdapter(new RecyclerAdapter(createDataList()));
    }

    private List<String> createDataList() {
        return null;
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_navigation_up);
        setTitle("");
        mToolbar.setSubtitle("李双");
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
}
