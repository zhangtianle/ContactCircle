package com.cqupt.tool;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Window;

import com.alibaba.fastjson.JSON;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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


    private static boolean checkExternalStorageState() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return mExternalStorageAvailable && mExternalStorageWriteable;
    }

    public static File getAlbumStorageDir(String albumName) {
        if (!checkExternalStorageState())
            return null;
        if (!Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).exists()) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).mkdirs();
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static Bitmap getBitmap(Uri uri, Context context, int thumbnailSize) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (options.outWidth == 0 || options.outHeight == 0) {
            return null;
        }
        // 得到图片的宽度、高度；
        float imgWidth = options.outWidth;
        float imgHeight = options.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) thumbnailSize);
        int heightRatio = (int) Math.ceil(imgHeight / (float) thumbnailSize);
        BitmapFactory.Options optionsChoose = new BitmapFactory.Options();
        optionsChoose.inSampleSize = 1;
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                optionsChoose.inSampleSize = widthRatio;
            } else {
                optionsChoose.inSampleSize = heightRatio;
            }
        }

        optionsChoose.inJustDecodeBounds = false;
        inputStream = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, optionsChoose);
        inputStream.close();
        return bitmap;
    }


    public static File saveBitmapToFile(String fileName, Bitmap bitmap) throws IOException {
        File file = getAlbumStorageDir(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return file;
    }




}
