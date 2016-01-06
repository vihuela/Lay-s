package com.hadlink.library.util.recyclerViewUtils;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by lyao on 2015/8/14.
 */
public class NoHandlerScrollView extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public NoHandlerScrollView(Context context) {
        super(context);
        init(context);
    }

    public NoHandlerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoHandlerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return false;
    }
}
