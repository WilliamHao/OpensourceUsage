package com.example.williamhao.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.williamhao.utils.LogUtils;


/**
 * 解决java.lang.IllegalArgumentException: pointerIndex out of range的错误
 */

public class ImageViewPager extends ViewPager {

    public ImageViewPager(Context context) {
        super(context);
    }

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result = false;
        try{
            result = super.onTouchEvent(ev);
        } catch(Exception ex) {
            LogUtils.i("catch error success,deal with android bug ! ");
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        try{
            result = super.onInterceptTouchEvent(ev);
        } catch(Exception ex) {
            LogUtils.i("catch error success,解决java.lang.IllegalArgumentException: pointerIndex out of range的错误");
        }
        return result;
    }
}
