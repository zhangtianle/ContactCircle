package com.cqupt.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.cqupt.tool.Utils;

/**
 * Created by ls on 15-3-3.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private int mToolbarHeight;
    private int mToolbarOffset;
    private static final int HIDE_DISTANCE = 50;
    private static final int SHOW_DISTANCE = 50;
    private boolean isVisible = true;


    public HidingScrollListener(Context context) {
        mToolbarHeight = Utils.getToolbarHeight(context);

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /**向上为正，向下为负 */
        System.out.println("onScrolled dx :" + dx + "dy: " + dy);
        clipToolbarOffset();
        onMove(mToolbarOffset);
        if (dy > 0 && mToolbarOffset < mToolbarHeight || dy < 0 && mToolbarOffset > 0) {
            mToolbarOffset += dy;
        }


    }


    private void clipToolbarOffset() {
        if (mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        }
        if (mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {//不在用手指滚动
            if (isVisible) {
                if (mToolbarOffset > HIDE_DISTANCE) {
                    setInVisible();
                } else {
                    setVisible();
                }
            } else {
                if (mToolbarHeight - mToolbarOffset > SHOW_DISTANCE) {
                    setVisible();
                } else {
                    setInVisible();
                }
            }
        }
    }

    private void setVisible() {
        if (mToolbarOffset > 0) {
            onShow();
            mToolbarOffset = 0;
        }
        isVisible = true;
    }

    private void setInVisible() {
        if (mToolbarOffset < mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
            onHide(mToolbarHeight);
        } isVisible = false;
    }

    protected abstract void onHide(int mToolbarHeight);

    protected abstract void onShow();

    protected abstract void onMove(int mToolbarOffset);
}
