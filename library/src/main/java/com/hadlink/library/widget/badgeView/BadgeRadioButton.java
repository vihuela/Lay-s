/**
 * Copyright 2015 bingoogolapple
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hadlink.library.widget.badgeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/6 下午3:04
 * 描述:
 */
public class BadgeRadioButton extends AppCompatRadioButton implements Badgeable {
    private BadgeViewHelper mBadgeViewHeler;

    public BadgeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBadgeViewHeler = new BadgeViewHelper(this, context, attrs, BadgeViewHelper.BadgeGravity.RightTop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mBadgeViewHeler.onTouchEvent(event);
    }

    @Override
    public boolean callSuperOnTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHeler.drawBadge(canvas);
    }

    @Override
    public void showCirclePointBadge() {
        mBadgeViewHeler.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
        if ("0".equalsIgnoreCase(badgeText))
            mBadgeViewHeler.hiddenBadge();
        else
            mBadgeViewHeler.showTextBadge(badgeText);

    }

    @Override
    public void hiddenBadge() {
        mBadgeViewHeler.hiddenBadge();
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHeler.showDrawable(bitmap);
    }

    @Override
    public void setDragDismissDelegage(DragDismissDelegate delegate) {
        mBadgeViewHeler.setDragDismissDelegage(delegate);
    }

    public void setBgColor(@ColorInt int color) {
        mBadgeViewHeler.setBgColor(color);
    }
}