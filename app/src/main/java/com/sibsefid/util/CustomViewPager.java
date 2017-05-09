package com.sibsefid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ubuntu on 3/11/16.
 */
public class CustomViewPager extends android.support.v4.view.ViewPager {
    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled ? super.onInterceptTouchEvent(event) : false;
    }

    public boolean isPagingEnabled() {
        return enabled;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
