package com.phdroid.smsb.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;

/**
 * This checkbox is needed for CheckableRelativeLayout so it gets selected when checkbox is pressed.
 */
public class QuirkyCheckbox extends CheckBox{

    public QuirkyCheckbox(Context context) {
        super(context);
    }

    public QuirkyCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuirkyCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super.onTouchEvent(event);
        return false;
    }
}
