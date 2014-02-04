
package com.devin.BluetoothRadio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class ChannelButtonTableGroup extends TableLayout
{
    public static interface OnCheckedChangeListener
    {

        public abstract void onCheckedChanged(ChannelButtonTableGroup channelbuttontablegroup, CompoundButton compoundbutton, int i);
    }

    public static interface OnGroupChannelChangedListener
    {

        public abstract void onChannelChecked(int i, String s);

        public abstract void onChannelDeleted(int i, String s);

        public abstract void onChannelOverwrited(int i, String s);

        public abstract void onChannelSaved(int i, String s);
    }

    class ViewHolder
    {

        public LinearLayout getChannelEditLayout()
        {
            return channelEditLayout;
        }

        public ImageButton getDelBtn()
        {
            return delBtn;
        }

        public TextView getIdText()
        {
            return idText;
        }

        public int getIndex()
        {
            return index;
        }

        public int getMode()
        {
            return currentMode;
        }

        public ImageButton getOverrideBtn()
        {
            return overrideBtn;
        }

        public RadioButton getRadioBtn()
        {
            return radioBtn;
        }

        public ImageButton getSaveBtn()
        {
            return saveBtn;
        }

        public void setChannelEditLayout(LinearLayout linearlayout)
        {
            channelEditLayout = linearlayout;
        }

        public void setDelBtn(ImageButton imagebutton)
        {
            delBtn = imagebutton;
        }

        public void setIdText(TextView textview)
        {
            idText = textview;
        }

        public void setIndex(int i)
        {
            index = i;
        }

        public void setModel(int i)
        {
//            i;
//            JVM INSTR tableswitch 0 2: default 28
//        //                       0 34
//        //                       1 63
//        //                       2 92;
//               goto _L1 _L2 _L3 _L4
//_L1:
//            currentMode = i;
//            return;
//_L2:
//            radioBtn.setVisibility(0);
//            saveBtn.setVisibility(8);
//            channelEditLayout.setVisibility(8);
//            continue; /* Loop/switch isn't completed */
//_L3:
//            radioBtn.setVisibility(8);
//            saveBtn.setVisibility(0);
//            channelEditLayout.setVisibility(8);
//            continue; /* Loop/switch isn't completed */
//_L4:
//            radioBtn.setVisibility(8);
//            saveBtn.setVisibility(8);
//            channelEditLayout.setVisibility(0);
//            if(true) goto _L1; else goto _L5
//_L5:
        }

        public void setOverrideBtn(ImageButton imagebutton)
        {
            overrideBtn = imagebutton;
        }

        public void setRadioBtn(RadioButton radiobutton)
        {
            radioBtn = radiobutton;
        }

        public void setSaveBtn(ImageButton imagebutton)
        {
            saveBtn = imagebutton;
        }

        private static final int CHANNEL_MODE = 0;
        private static final int EDIT_MODE = 2;
        private static final int SAVE_MODE = 1;
        LinearLayout channelEditLayout;
        private int currentMode;
        ImageButton delBtn;
        TextView idText;
        int index;
        ImageButton overrideBtn;
        RadioButton radioBtn;
        ImageButton saveBtn;
        final ChannelButtonTableGroup context;

        ViewHolder()
        {
            context = ChannelButtonTableGroup.this;
            currentMode = 0;
        }
    }


    public ChannelButtonTableGroup(Context context)
    {
        super(context);
        rowNums = 0;
        currentIndex = -1;
    }

    public ChannelButtonTableGroup(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        rowNums = 0;
        currentIndex = -1;
    }
//
//    private ViewHolder getViewHolder(View view)
//    {
//        ViewHolder viewholder = new ViewHolder();
//        viewholder.setRadioBtn((RadioButton)view.findViewById(0x7f0a0029));
//        viewholder.setSaveBtn((ImageButton)view.findViewById(0x7f0a002d));
//        viewholder.setChannelEditLayout((LinearLayout)view.findViewById(0x7f0a002e));
//        viewholder.setOverrideBtn((ImageButton)view.findViewById(0x7f0a002f));
//        viewholder.setDelBtn((ImageButton)view.findViewById(0x7f0a0030));
//        viewholder.setIdText((TextView)view.findViewById(0x7f0a0026));
//        return viewholder;
//    }
//
//    private void setChildrenOnClickListener(int i, TableRow tablerow)
//    {
//        int j = tablerow.getChildCount();
//        int k = 0;
//        do
//        {
//            if(k >= j)
//                return;
//            View view = tablerow.getChildAt(k);
//            int l = k + i * j;
//            final ViewHolder holder = getViewHolder(view);
//            view.setTag(holder);
//            holder.setIndex(l);
//            holder.getIdText().setText(String.valueOf(l + 1));
//            holder.getRadioBtn().setOnClickListener(new OnClickListener() {
//
//                public void onClick(View view1)
//                {
//                    CompoundButton compoundbutton = (CompoundButton)view1;
//                    if(activeCompoundButton != null)
//                        activeCompoundButton.setChecked(false);
//                    compoundbutton.setChecked(true);
//                    activeCompoundButton = compoundbutton;
//                    currentChannel = holder.getRadioBtn().getText().toString();
//                    if(listener != null)
//                        listener.onChannelChecked(holder.getIndex(), currentChannel);
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder val$holder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            holder.getDelBtn().setOnClickListener(new OnClickListener() {
//
//                public void onClick(View view1)
//                {
//                    holder.setModel(1);
//                    groupData.set(holder.getIndex(), "0.0");
//                    if(listener != null)
//                        listener.onChannelDeleted(holder.getIndex(), "0.0");
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder val$holder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            holder.getSaveBtn().setOnClickListener(new OnClickListener() {
//
//                public void onClick(View view1)
//                {
//                    holder.getRadioBtn().setText(currentChannel);
//                    holder.setModel(0);
//                    groupData.set(holder.getIndex(), currentChannel);
//                    if(listener != null)
//                        listener.onChannelSaved(holder.getIndex(), currentChannel);
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder mViewHolder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            holder.getOverrideBtn().setOnClickListener(new OnClickListener() {
//
//                public void onClick(View view1)
//                {
//                    holder.getRadioBtn().setText(currentChannel);
//                    holder.setModel(0);
//                    groupData.set(holder.getIndex(), currentChannel);
//                    if(listener != null)
//                        listener.onChannelOverwrited(holder.getIndex(), currentChannel);
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder mViewHolder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            holder.getChannelEditLayout().setOnLongClickListener(new OnLongClickListener() {
//
//                public boolean onLongClick(View view1)
//                {
//                    holder.setModel(0);
//                    return true;
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder val$holder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            holder.getRadioBtn().setOnLongClickListener(new OnLongClickListener() {
//
//                public boolean onLongClick(View view1)
//                {
////                    LogUtil.v("ChannelButtonTableGroup", (new StringBuilder("currentChannel ")).append(currentChannel).toString());
//                    holder.getRadioBtn().setText(currentChannel);
//                    holder.setModel(0);
//                    groupData.set(holder.getIndex(), currentChannel);
//                    if(listener != null)
//                        listener.onChannelSaved(holder.getIndex(), currentChannel);
//                    return true;
//                }
//
//                final ChannelButtonTableGroup this$0;
//                private final ViewHolder val$holder;
//
//
//            {
//                this$0 = ChannelButtonTableGroup.this;
//                holder = viewholder;
//                super();
//            }
//            }
//);
//            k++;
//        } while(true);
//    }
//
//    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutparams)
//    {
//        super.addView(view, i, layoutparams);
//        int j = rowNums;
//        rowNums = j + 1;
//        setChildrenOnClickListener(j, (TableRow)view);
//    }
//
//    public void setCurrentChannel(String s)
//    {
//        currentChannel = s;
//    }
//
//    public void setCurrentIndex(int i)
//    {
//        currentIndex = i;
//    }
//
//    public void setDataSource(ArrayList arraylist, int i, String s, boolean flag)
//    {
//        int j;
//        LogUtil.v("ChannelButtonTableGroup", (new StringBuilder(" currentIndex ")).append(i).append(" currentChannel ").append(s).toString());
//        currentIndex = i;
//        currentChannel = s;
//        groupData = arraylist;
//        j = 0;
//_L2:
//        if(j >= getChildCount())
//            return;
//        TableRow tablerow = (TableRow)getChildAt(j);
//        int k = 0;
//        do
//        {
//label0:
//            {
//                if(k < tablerow.getChildCount())
//                    break label0;
//                j++;
//            }
//            if(true)
//                continue;
//            ViewHolder viewholder = (ViewHolder)tablerow.getChildAt(k).getTag();
//            int l = viewholder.getIndex();
//            if(((String)groupData.get(l)).equals("0.0") || ((String)groupData.get(l)).equals("0"))
//            {
//                viewholder.setModel(1);
//            } else
//            {
//                viewholder.setModel(0);
//                if(((String)groupData.get(l)).equals("0.0") || ((String)groupData.get(l)).equals("0"))
//                    viewholder.getRadioBtn().setText("empty");
//                else
//                    viewholder.getRadioBtn().setText((CharSequence)groupData.get(l));
//                viewholder.getRadioBtn().setChecked(false);
//                if(flag && i == l && ((String)groupData.get(l)).equals(s))
//                {
//                    viewholder.getRadioBtn().setChecked(true);
//                    activeCompoundButton = viewholder.getRadioBtn();
//                }
//            }
//            k++;
//        } while(true);
//        if(true) goto _L2; else goto _L1
//_L1:
//    }
//
//    public void setOnGroupChannelChangedListener(OnGroupChannelChangedListener ongroupchannelchangedlistener)
//    {
//        listener = ongroupchannelchangedlistener;
//    }

    private static final String EMPTY = "empty";
    private static final String TAG = "ChannelButtonTableGroup";
    private CompoundButton activeCompoundButton;
    private String currentChannel;
    private int currentIndex;
    private ArrayList groupData;
    private OnGroupChannelChangedListener listener;
    private int rowNums;






}
