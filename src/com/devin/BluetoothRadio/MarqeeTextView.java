// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.devin.BluetoothRadio;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqeeTextView extends TextView
{

    public MarqeeTextView(Context context)
    {
        super(context);
    }

    public MarqeeTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public MarqeeTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    protected void onFocusChanged(boolean flag, int i, Rect rect)
    {
        super.onFocusChanged(flag, i, rect);
    }

    protected void onMeasure(int i, int j)
    {
        super.onMeasure(i, j);
    }

    public boolean onPreDraw()
    {
        return super.onPreDraw();
    }

    public void setText(CharSequence charsequence, BufferType buffertype)
    {
        super.setText(charsequence, buffertype);
    }

    private static final String TAG = "MarqeeTextView";
}
