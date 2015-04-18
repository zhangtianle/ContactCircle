package com.cqupt.tool;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Window;

/**
 * Created by ls on 15-4-16.
 */
public class Utils {

    public static int getToolbarHeight(Context context) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{android.support.v7.appcompat.R.attr.actionBarSize});
        int mToolbarHeight = (int) typedArray.getDimension(0, 0);
        typedArray.recycle();
        return mToolbarHeight;

    }

    public static int getScreentHeight(ActionBarActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getStatusBarHeight(ActionBarActivity activity) {
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getDisplayLayoutHight(ActionBarActivity activity) {
        return getScreentHeight(activity) - getStatusBarHeight(activity) - getToolbarHeight(activity) * 2;


    }


}
