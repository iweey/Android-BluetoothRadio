// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.devin.BluetoothRadio;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;

public class CustomSpinner extends Spinner {
    public CustomSpinner(Context context) {
        super(context);
    }

    /*public CustomSpinner(Context context, int i) {
        super(context, i);
    }*/

    public CustomSpinner(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    public CustomSpinner(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
    }

    /*public CustomSpinner(Context context, AttributeSet attributeset, int i, int j) {
        super(context, attributeset, i, j);
    }*/

    public void onClick(DialogInterface dialoginterface, int i) {
        super.onClick(dialoginterface, i);
        if (listener != null)
            listener.onItemClick(this, getSelectedView(), i, getSelectedItemId());
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        return super.onTouchEvent(motionevent);
    }

    public boolean performItemClick(View view, int i, long l) {
        return super.performItemClick(view, i, l);
    }

    public void setOnItemClickListener(OnItemClickListener onitemclicklistener) {
        listener = onitemclicklistener;
    }

    private OnItemClickListener listener;
}
