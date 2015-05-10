package com.cqupt.tool;

import android.content.Context;
import android.graphics.Bitmap;

import com.cqupt.contactcircle.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by ls on 15-4-29.
 */
public class ArticleBitmapHandlerUtils {

    private ArticleBitmapHandlerUtils() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
           bitmapUtils.configDefaultLoadingImage(R.drawable.ic_loadimg_default);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_loadimg_default);
            bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
           // bitmapUtils.configDefaultShowOriginal(true);
            bitmapUtils.configMemoryCacheEnabled(true);
            bitmapUtils.configDiskCacheEnabled(true);
        }
        return bitmapUtils;
    }


}
