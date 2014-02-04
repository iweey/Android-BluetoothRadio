// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.devin.BluetoothRadio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class SpinnerTextView extends TextView
{

    public SpinnerTextView(Context context)
    {
        super(context);
    }

    public SpinnerTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public SpinnerTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    private void initGesture()
    {
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            public boolean onSingleTapUp(MotionEvent motionevent)
            {
                super.onSingleTapUp(motionevent);
                return false;
            }

        }
);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        return super.onTouchEvent(motionevent);
    }

    private GestureDetector detector;
}
