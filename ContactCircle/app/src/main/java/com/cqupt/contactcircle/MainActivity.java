package com.cqupt.contactcircle;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
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
import com.cqupt.listener.HidingScrollListener;
import com.cqupt.tool.Utils;

import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private View mToolbarContainer;
    private View mSendToolContainer;
    private View mCircleLayout;
    private boolean mCircleLayoutIsShow;
    private FrameLayout mRootLayout;
    private ExpandableListView mListView;
    private TextView mCircleHeaderName;
    private ImageView mCircleHeaderIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initRecycler();
        initOtherView();
        initCircleLayout();


    }

    private void initExpandableListView() {
        if (mListView == null)
            mListView = (ExpandableListView) findViewById(R.id.main_activity_circle_layout_el);
        mListView.setAdapter(new ExpandableListAdapter(this));
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                System.out.println("item click fathre " + i + " children " + i2);
                return true;
            }
        });

    }

    private void initCircleLayout() {
        mCircleLayout = LayoutInflater.from(this).inflate(R.layout.circle_layout, null);
        mRootLayout = (FrameLayout) findViewById(R.id.main_activity_fl_root);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.topMargin = Utils.getToolbarHeight(this) * 2;
        mCircleLayout.setLayoutParams(layoutParams);


        mCircleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


    }

    private void initOtherView() {
        findViewById(R.id.main_activity_circle_header_rl).setOnClickListener(this);
        findViewById(R.id.main_activity_iv_send_message).setOnClickListener(this);

        mCircleHeaderName = (TextView) findViewById(R.id.main_activity_circle_header_tx);
        mCircleHeaderIcon = (ImageView) findViewById(R.id.main_activity_circle_header_iv);

    }

    private void initRecycler() {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_activity_rv);
        mSendToolContainer = findViewById(R.id.main_activity_rl_send_tool_container);
        mRecyclerView.setPadding(0, Utils.getToolbarHeight(this) * 2 + 5, 0, 0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        mRecyclerView.setAdapter(new RecyclerAdapter(createList()));
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

    private List<String> createList() {
        return null;
    }

    private void initToolbar() {
        mToolbarContainer = findViewById(R.id.mian_activity_ll_toolbar_container);
        mToolbar = (Toolbar) findViewById(R.id.main_activity_tb);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_circle_header_rl:
                showOrHideCircleLayout();
                break;
            case R.id.main_activity_iv_send_message:
                goSendMessageActivity();
        }
    }

    private void goSendMessageActivity() {
        Intent mIntent = new Intent(this, SendMessageActivity.class);
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
}
