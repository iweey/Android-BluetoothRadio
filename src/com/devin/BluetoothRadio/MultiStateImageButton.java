// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.devin.BluetoothRadio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiStateImageButton
        extends ImageButton
{
    public static interface OnStateChangedListener
    {

        public abstract void onStateChanged(int i, int j, boolean flag);
    }


    public MultiStateImageButton(Context context)
    {
        super(context);
        stateSourceMap = new HashMap();
        statesList = new ArrayList();
        resList = new ArrayList();
        clickListener = null;
        stateListener = null;
        currentStateIndex = 0;
    }

    public MultiStateImageButton(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        stateSourceMap = new HashMap();
        statesList = new ArrayList();
        resList = new ArrayList();
        clickListener = null;
        stateListener = null;
        currentStateIndex = 0;
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, R.styleable.MultiStateImageButton, 0, 0);
        statesNumber = typedarray.getInt(0, 1);
        typedarray.recycle();
        super.setOnClickListener(new OnClickListener() {

            public void onClick(View view)
            {
                if(clickListener != null)
                    clickListener.onClick(view);
                if(statesList != null && statesList.size() != 0)
                {
                    int i = (1 + currentStateIndex) % statesList.size();
                    setState(((Integer)statesList.get(i)).intValue(), true);
                }
            }

        }
);
    }

    public MultiStateImageButton(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        stateSourceMap = new HashMap();
        statesList = new ArrayList();
        resList = new ArrayList();
        clickListener = null;
        stateListener = null;
        currentStateIndex = 0;
    }

    public int getState()
    {
        return state;
    }

    public int getStatesNumber()
    {
        return statesNumber;
    }

    public void setDefaultState(int i)
    {
        if(statesList != null && statesList.size() != 0 && stateSourceMap != null)
        {
            state = i;
            currentStateIndex = statesList.indexOf(Integer.valueOf(i));
            setImageResource(((Integer)stateSourceMap.get(Integer.valueOf(i))).intValue());
        }
    }

    public void setOnClickListener(OnClickListener onclicklistener)
    {
        clickListener = onclicklistener;
    }

    public void setOnStateChangedListener(OnStateChangedListener onstatechangedlistener)
    {
        stateListener = onstatechangedlistener;
    }

    public void setState(int i, boolean flag)
    {
        if(statesList != null && statesList.size() != 0 && stateSourceMap != null)
        {
            if(stateListener != null)
                stateListener.onStateChanged(state, i, flag);
            state = i;
            currentStateIndex = statesList.indexOf(Integer.valueOf(i));
            setImageResource(((Integer)stateSourceMap.get(Integer.valueOf(i))).intValue());
        }
    }

    public void setStatesSource(ArrayList arraylist, ArrayList arraylist1)
    {
        stateSourceMap.clear();
        statesList.clear();
        resList.clear();
        int i = 0;
        int j = 0;
        do
        {
            if(i >= arraylist.size())
            {
                statesList = arraylist;
                resList = arraylist1;
                state = ((Integer)arraylist.get(currentStateIndex)).intValue();
                setState(state, false);
                return;
            }
            stateSourceMap.put((Integer)arraylist.get(i), (Integer)arraylist1.get(j));
            i++;
            j++;
        } while(true);
    }

    private static final String TAG = "MultiStateImageButton";
    private OnClickListener clickListener;
    private int currentStateIndex;
    private ArrayList resList;
    private int state;
    private OnStateChangedListener stateListener;
    private Map stateSourceMap;
    private ArrayList statesList;
    private int statesNumber;



}
