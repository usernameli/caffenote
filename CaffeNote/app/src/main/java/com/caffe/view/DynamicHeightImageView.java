package com.caffe.view;

/**
 * Created by brill on 2016/6/22.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DynamicHeightImageView extends ImageView {

    /**
     * 图片高宽比（高/宽）
     */
    private double hwRatio;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //获取当前ImageView分配的宽度(即Item项的宽度)
//        System.out.println(widthMeasureSpec+"widthMeasureSpec"+heightMeasureSpec);
//      //  MeasureSpec.AT_MOST
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        if (widthSize != 0 && hwRatio != 0) {
//            //根据高宽比，计算出ImagView需要的高度widthSize* hwRatio，并设置其大小
//            setMeasuredDimension(widthSize, (int) (widthSize * hwRatio));
//        } else {
//           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//         //   setMeasuredDimension(widthSize, (int) (widthSize * hwRatio));
//            //setMeasuredDimension(widthSize, (int) (widthSize * hwRatio));
//
//        }
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int speSize = MeasureSpec.getSize(widthMeasureSpec);
        int speMode = MeasureSpec.getMode(widthMeasureSpec);
        Bitmap bitmap =null;
        try {
            bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

        } catch (Exception e) {
            setMeasuredDimension(0, 0);
            return;
        }
        //   Bitmap bitmap =  ((BitmapDrawable)getDrawable()).getBitmap();
        //     System.out.println(bitmap);
        int width = resolveSize(speSize, widthMeasureSpec);
        if (bitmap==null){
   //         super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            setMeasuredDimension(0,0);
            return;
        }
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        System.out.println("width" + imageWidth + "height" + imageHeight);
        setMeasuredDimension(width, (int) ((width * imageHeight) / imageWidth));
        //   setMeasuredDimension(200, 200);
        //     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static int resolveSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

//    @Override
//    public void setImageResource(int resId) {
//        super.setImageResource(resId);
//        //获取图片的高宽比（高/宽）
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resId, options);
//        hwRatio = options.outHeight / (double) options.outWidth;
//        bmp.recycle();
//    }
}
