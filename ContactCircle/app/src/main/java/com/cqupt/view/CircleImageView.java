package com.cqupt.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.cqupt.contactcircle.R;


/**
 * Created by ls on 2015/1/2 0002.
 */
public class CircleImageView extends ImageView {
    //circle show type
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    //the circleimage  src
    private Bitmap mSrc;
    //corner radius
    private int mRadius;
    //this view's width
    private int mWidth;
    //this view's height
    private int mHeight;
    private Context mContext;


    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        int n = array.getIndexCount();//attributeSet's count
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CircleView_type:
                    type = array.getInt(attr, 0);//default is circle
                    break;
                case R.styleable.CircleView_src:
                    mSrc = BitmapFactory.decodeResource(context.getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.CircleView_borderRadius:
                    mRadius = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));//default size is 10dp
                    break;
            }
        }
        array.recycle();
    }

    public CircleImageView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (type) {
            case TYPE_CIRCLE:
                int min = Math.min(mHeight, mWidth);
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);//scaled bitmap
                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
               // createCircleImage(mSrc, min);
                break;
            case TYPE_ROUND:
                mSrc = Bitmap.createScaledBitmap(mSrc, mWidth, mHeight, false);
                canvas.drawBitmap(createRoundCornerImage(mSrc), 0, 0, null);
                break;
        }


    }

    private Bitmap createRoundCornerImage(Bitmap mSrc) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rectF = new RectF(0, 0, mSrc.getWidth(), mSrc.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSrc, 0, 0, paint);
        return target;


    }
/***/
    private Bitmap createCircleImage(Bitmap mSrc, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);//create a min bitmap
        Canvas canvas = new Canvas(target);//create a canvas as the same as bitmap
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);//canvas drawer a cricle
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//set  SRC_IN
        canvas.drawBitmap(mSrc, 0, 0, paint);//drawer bitmap
        return target;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //set this view's width
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//ensure width
            mWidth = specSize;
        } else {
            //the view self decide width
            int desireByImg = getPaddingLeft() + getWidth() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(desireByImg, specSize);
            } else {//un
                mWidth = desireByImg;
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            int desireHeight = getPaddingBottom() + getPaddingTop() + getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desireHeight, specSize);
            } else {
                mHeight = desireHeight;
            }

        }

        setMeasuredDimension(mWidth, mHeight);//save size
    }

}
