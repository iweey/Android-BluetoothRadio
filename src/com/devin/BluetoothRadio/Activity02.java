package com.devin.BluetoothRadio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.TypedValue;
import android.content.pm.ApplicationInfo;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Activity02 extends Activity implements TabHost.OnTabChangeListener, SeekBar.OnSeekBarChangeListener {

    public static boolean realExit = true;

    public static final  String SAVE_STATE_TAB_SELECT= "tab_select";
    public LinearLayout mBottomControlPanel;
    private ImageButton volumeDownBtn;
    public Preferences mPreferences;
    private ImageButton volumeUpBtn;
    private ImageButton powerBtn;
    private static CustomSpinner cSpinner;
    private static boolean cSpinnerTouchFlag = false;
    private ImageButton settingBtn;
    private Context mContext;
    AlertDialog mAlertOption;
    private LinearLayout mToInputLY;

    private static ProgressDialog progressBar;
    public static Activity02 galleryActivity;
    public static Resources rs;

    private ArrayAdapter<String> adapter;
    private static final String[] volumeArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"
    };

    private static final String[] mFMArray = { "FM1", "FM2", "FM3"};
    public static int volumeValueFromRadio = 0;
    private static Map<String, Integer> positionVolumeMap;

    private static ImageView mMuteBtn;
    private static TextView mStereoText;
    public static boolean mStereoTextEnableFlag = false;
    private LinearLayout mTunerLY;
    private static TabHost mTabHost;
    private static boolean tabHostTouchFlag = false;
    public static boolean tabUSBKillflag = false;
    public static boolean tabSDKillflag = false;

    private TextView mChannelTextView;
    private double mTunerValue;
    private static SeekBar mChannelSeekBar;
    private static boolean mChannelSeekBarTouchFlag = false;

    private static boolean mFMValueChange = false;

    private static Timer mTimer = null;
    private static TimerTask mTimerTask = null;

    private static Timer mTimerNextMusic = null;
    private static TimerTask mTimerTaskNextMusic = null;

    private static TextView mFMValue;
    public static int mFMValueShowState = 0;

    private ImageButton mPreChannelButton;
    private ImageButton mNextChannelButton;
    private ImageButton mTunerScanDownBtn;
    private ImageButton mTunerScanUpBtn;
    private static boolean tunerScanBtnFlag;

    public static int mFMArrayIndex;
    public static int maxFMvalue;
    public static int minFMvalue;
    public static int mFmStepValue;
    public static int mFmCurrentValue;
    public static boolean FMchangeAndSendOrder;

    private static CustomSpinner mFMSpinner;
    private ArrayAdapter mFMSpinnerAdapter;

    private Boolean changeGroup = false;

    private static TextView mChannelText;
    //private ImageButton mSettingBtn;

    private LinearLayout mBottomTabLY;
    private static RadioGroup mBottomLYRadioGroupTop;
    private static RadioGroup mBottomLYRadioGroupBottom;
    private static boolean bottomLYRadioGroupFlag = false;

    private static RadioButton mBottomLYRadio1;
    private static RadioButton mBottomLYRadio2;
    private static RadioButton mBottomLYRadio3;
    private static RadioButton mBottomLYRadio4;
    private static RadioButton mBottomLYRadio5;
    private static RadioButton mBottomLYRadio6;
    public static int whichBtnFocus = 0;
    public static int mFMm1Value = 0;
    public static int mFMm2Value = 0;
    public static int mFMm3Value = 0;
    public static int mFMm4Value = 0;
    public static int mFMm5Value = 0;
    public static int mFMm6Value = 0;

    public Map<String, PInfo> kaijuanyouyi = new HashMap<String, PInfo>();
    public Map<String, PInfo> gongxifacai = new HashMap<String, PInfo>();
    public String aimPackageName = "";
    public Intent xintent;
    public PackageManager xpackageMan;

    private FrameLayout mMusicUSBLY;
    private FrameLayout mMusicSDLY;
    private FrameLayout mA2DPLY;
    private static TextView mUSBTopBarTitle;
    private static TextView toShowTrackListBtn;
    private static TextView mUSBAlbumText;
    public static String mUSBAlbumTextStr = "";
    private static TextView mUSBAuthorText;
    public static String mUSBAuthorTextStr = "";
    private ImageView mUSBAlbumImageView;
    private static ImageView mUSBXunhuanPlay;
    public static int mUSBXunhuanPlayMode = 0;
    private ImageView mUSBsuijiPlay;

    private int mSaveTabSelect = 0;
    private static TextView mSDalbumText1;
    //public static String sdalbumText8A = "";
    private static TextView mSDalbumText2;
    //public static String sdalbumText89 = "";
    private static TextView uSBalbumText1;
    public static String uSBalbumText8A = "";
    private static TextView mUSBalbumText2;
    public static String uSBalbumText89 = "";
    private ImageButton mRadiotunerIntroPlayBtn;

    private static TextView mUSBTrackPlayTimeText;
    private static TextView mUSBtrackTotalTimeText;
    private static MySeekBar mUSBtrackSeekBar;
    private SeekBar mUSBChannelSeekBar;
    private ImageButton mUSBtrackPreBtn;
    private static ImageButton mUSBplayandstopBtn;
    private ImageButton mUSBtrackNextBtn;
    public static boolean mUSBPreNextShortClickEnable = true;
    private boolean mUSBPreNextLongShortClickEnable = true;

    public static boolean usbOrSD = true;

    public static TextView toSDShowTrackListBtn;
    public static int mSDXunhuanPlayMode = 0;
    public static boolean mSDPreNextShortClickEnable = true;
    private boolean mSDPreNextLongShortClickEnable = true;
    private TextView mSDTopBarTitle;
    //private static String mSDAlbumTextStr;
    private static TextView mSDAlbumText;
    private static TextView mSDAuthorText;
    private ImageView mSDAlbumImageView;
    private static ImageView mSDXunhuanPlay;
    private static TextView mSDTrackPlayTimeText;
    private static TextView mSDtrackTotalTimeText;
    private static SeekBar mSDtrackSeekBar;
    private SeekBar mSDChannelSeekBar;
    private ImageButton mSDtrackPreBtn;
    private static ImageButton mSDplayandstopBtn;
    private ImageButton mSDtrackNextBtn;
    private ImageView mSDsuijiPlay;

    public static boolean usbPlayOrPauseFlag = false;
    public static boolean usbStopFlag = false;
    public static int usbPlayMode = 0;
    public static int usbCurrentIndex = 0;
    public static int usbTotalNum = 0;
    public static int usbCurrentTime = 0;
    public static int usbTotalTime = 0;

    public static boolean sdPlayOrPauseFlag = false;
    public static boolean sdStopFlag = false;
    public static int sdPlayMode = 0;
    //public static int sdCurrentIndex = 0;
    //public static int sdTotalNum = 0;
    //public static int sdCurrentTime = 0;
    //public static int sdTotalTime = 0;

    public static Handler handlerRadioInterface = new Handler() {
        public void handleMessage(Message msg) {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1: {
                        //int position = Arrays.binarySearch(volumeArray, volumeValueFromRadio + "");slow
                        cSpinnerTouchFlag = false;
                        int position = positionVolumeMap.get(volumeValueFromRadio + "");
                        cSpinner.setSelection(position);
                        if(mMuteBtn.isSelected()) {
                            mMuteBtn.invalidate();
                            mMuteBtn.setSelected(false);
                            mMuteBtn.invalidate();
                        }
                        Log.e("gallery", volumeValueFromRadio + "===int");
                        break;
                    }
                    case 2: {
                        FMchangeAndSendOrder = false;
                        mFMSpinner.setSelection(mFMArrayIndex);
                        if((maxFMvalue>minFMvalue) && (mFmStepValue>0)) {
                            mChannelSeekBar.setMax((maxFMvalue-minFMvalue)/mFmStepValue);
                        }
                        break;
                    }
                    case 3: {
                        if((mFmCurrentValue<=maxFMvalue) && (mFmCurrentValue>=minFMvalue) && (mFmStepValue>0)) {
                            int currentProgress = (mFmCurrentValue - minFMvalue) / mFmStepValue;
                            mChannelSeekBarTouchFlag = false;
                            mChannelSeekBar.setProgress(currentProgress);
                        }
                        break;
                    }
                    case 4: {
                        if(!mMuteBtn.isSelected()) {
                            //mMuteBtn.performClick();
                            //mMuteBtn.setPressed(true);
                            mMuteBtn.setSelected(true);
                        }
                        break;
                    }
                    case 55: {
                        if((Activity02.mFMValueShowState == 1) || (Activity02.mFMValueShowState == 2)) {
                            Activity02.mFMValueShowState = 0;
                            mFMValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 62);
                            mFMValue.setTextColor(Color.parseColor("#ff32b6e6"));
                            /*if(mTimer != null) {
                                mTimer.cancel();
                                mTimer = null;
                            }
                            if(mTimerTask != null) {
                                mTimerTask.cancel();
                                mTimerTask = null;
                            }*/
                        }
                        break;
                    }
                    case 5: {
                        if(mFMValueShowState == 1) {
                            if(tunerScanBtnFlag) {
                                //mFMValue.setText("Seek Up");
                                //mFMValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                            }
                            else {
                                //mFMValue.setText("Seek Down");
                                //mFMValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                            }
                            ////Activity02.galleryActivity.startTimer();
                        }
                        else if(mFMValueShowState == 2) {
                            //mFMValue.setText("Search");
                            //mFMValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

                            ////Activity02.galleryActivity.startTimer();
                        }
                        if(mStereoTextEnableFlag) {
                            mStereoText.setTextColor(rs.getColor(R.color.indicatorBlueTextNormal));
                        }
                        else {
                            mStereoText.setTextColor(rs.getColor(R.color.indicatorBlueTextDisabled));
                        }

                        if(whichBtnFocus != 0) {
                            bottomLYRadioGroupFlag = false;
                            switch(whichBtnFocus) {
                                case 1:
                                    mBottomLYRadio1.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("1"));
                                    break;
                                case 2:
                                    mBottomLYRadio2.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("2"));
                                    break;
                                case 3:
                                    mBottomLYRadio3.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("3"));
                                    break;
                                case 4:
                                    mBottomLYRadio4.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("4"));
                                    break;
                                case 5:
                                    mBottomLYRadio5.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("5"));
                                    break;
                                case 6:
                                    mBottomLYRadio6.setChecked(true);
                                    mChannelText.setText(new StringBuffer("Preset: ").append("6"));
                                    break;
                            }
                        }
                        break;
                    }
                    case 6: {
                        if(PreferencesFromXml.controlFlag) {
                            Log.e("gallery", "gallery ===> set balance value");
                            if(PreferencesFromXml.loudnessFlag) {
                                PreferencesFromXml.mloudness_preference.setChecked(true);
                            }
                            else {
                                PreferencesFromXml.mloudness_preference.setChecked(false);
                            }

                            switch(PreferencesFromXml.mlist_preference_eqIndex) {
                                case 0:
                                    PreferencesFromXml.mlist_preference_eq.setSummary("off");
                                    break;
                                case 1:
                                    PreferencesFromXml.mlist_preference_eq.setSummary("Flat");
                                    break;
                                case 2:
                                    PreferencesFromXml.mlist_preference_eq.setSummary("Classic");
                                    break;
                                case 3:
                                    PreferencesFromXml.mlist_preference_eq.setSummary("Rock");
                                    break;
                                case 4:
                                    PreferencesFromXml.mlist_preference_eq.setSummary("Pop");
                                    break;
                            }
                            Log.e("gallery", "set balance value === PreferencesFromXml.mbalance_preferenceIndex");
                            PreferencesFromXml.mbalance_preference.setProgress(PreferencesFromXml.mbalance_preferenceIndex);
                            if(PreferencesFromXml.mbalance_preferenceIndex == 7) {
                                PreferencesFromXml.mbalance_preference.setSummary("00");
                            }
                            else if(PreferencesFromXml.mbalance_preferenceIndex > 7) {
                                PreferencesFromXml.mbalance_preference.setSummary("R0" + (PreferencesFromXml.mbalance_preferenceIndex-7));
                            }
                            else if(PreferencesFromXml.mbalance_preferenceIndex < 7) {
                                PreferencesFromXml.mbalance_preference.setSummary("L0" + (7-PreferencesFromXml.mbalance_preferenceIndex));
                            }

                            PreferencesFromXml.mfader_preference.setProgress(PreferencesFromXml.mfader_preferenceIndex);
                            if(PreferencesFromXml.mfader_preferenceIndex == 7) {
                                PreferencesFromXml.mfader_preference.setSummary("00");
                            }
                            else if(PreferencesFromXml.mfader_preferenceIndex > 7) {
                                PreferencesFromXml.mfader_preference.setSummary("F0" + (PreferencesFromXml.mfader_preferenceIndex-7));
                            }
                            else if(PreferencesFromXml.mfader_preferenceIndex < 7) {
                                PreferencesFromXml.mfader_preference.setSummary("R0" + (7-PreferencesFromXml.mfader_preferenceIndex));
                            }
                        }
                        break;
                    }
                    case 7: {
                        if(mFMm1Value != 0) {
                            String temporary1 = ("" + (mFMm1Value/1000.0));
                            if(temporary1.indexOf(".") == (temporary1.length()-2)) {
                                temporary1 = temporary1 + "0";
                            }
                            mBottomLYRadio1.setText(temporary1);
                        }
                        if(mFMm2Value != 0) {
                            String temporary2 = ("" + (mFMm2Value/1000.0));
                            if(temporary2.indexOf(".") == (temporary2.length()-2)) {
                                temporary2 = temporary2 + "0";
                            }
                            mBottomLYRadio2.setText(temporary2);
                        }
                        if(mFMm3Value != 0) {
                            String temporary3 = ("" + (mFMm3Value/1000.0));
                            if(temporary3.indexOf(".") == (temporary3.length()-2)) {
                                temporary3 = temporary3 + "0";
                            }
                            mBottomLYRadio3.setText(temporary3);
                        }
                        if(mFMm4Value != 0) {
                            String temporary4 = ("" + (mFMm4Value/1000.0));
                            if(temporary4.indexOf(".") == (temporary4.length()-2)) {
                                temporary4 = temporary4 + "0";
                            }
                            mBottomLYRadio4.setText(temporary4);
                        }
                        if(mFMm5Value != 0) {
                            String temporary5 = ("" + (mFMm5Value/1000.0));
                            if(temporary5.indexOf(".") == (temporary5.length()-2)) {
                                temporary5 = temporary5 + "0";
                            }
                            mBottomLYRadio5.setText(temporary5);
                        }
                        if(mFMm6Value != 0) {
                            String temporary6 = ("" + (mFMm6Value/1000.0));
                            if(temporary6.indexOf(".") == (temporary6.length()-2)) {
                                temporary6 = temporary6 + "0";
                            }
                            mBottomLYRadio6.setText(temporary6);
                        }
                        break;
                    }
                    case 10:
                        //galleryActivity.setTitle("receive count: " + BluetoothDemo.receiveCount);
                        galleryActivity.setTitle("Enter interface and send ORDER occurs");
                        break;
                    case 11: {
                        if(tabUSBKillflag)
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).setEnabled(false);
                        else
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).setEnabled(true);
                        if(tabSDKillflag)
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).setEnabled(false);
                        else
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).setEnabled(true);
                        break;
                    }
                    case 122: {
                        tabHostTouchFlag = true;
                        break;
                    }
                    case 12: {
                        tabHostTouchFlag = false;
                        if(mTimerTask != null) {
                            mTimerTask.cancel();
                            mTimerTask = null;
                        }
                        if(mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                        if(BluetoothDemo.modeCurrent == 0) {
                            (mTabHost.getTabWidget().getChildTabViewAt(0)).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(0)).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(0)).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt(0)).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 2) {
                            usbOrSD = true;
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt(1)).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 3) {
                            usbOrSD = false;
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt(2)).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 5) {
                            (mTabHost.getTabWidget().getChildTabViewAt((8-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((8-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((8-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((8-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 4) {
                            (mTabHost.getTabWidget().getChildTabViewAt((7-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((7-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((7-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((7-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 7) {
                            (mTabHost.getTabWidget().getChildTabViewAt((4-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((4-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((4-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((4-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 8) {
                            (mTabHost.getTabWidget().getChildTabViewAt((5-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((5-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((5-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((5-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 9) {
                            (mTabHost.getTabWidget().getChildTabViewAt((6-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((6-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((6-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((6-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 10) {
                            (mTabHost.getTabWidget().getChildTabViewAt((9-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((9-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((9-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((9-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 11) {
                            (mTabHost.getTabWidget().getChildTabViewAt((10-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((10-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((10-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((10-1))).setPressed(true);
                        }
                        else if(BluetoothDemo.modeCurrent == 12) {
                            (mTabHost.getTabWidget().getChildTabViewAt((11-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((11-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((11-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((11-1))).setPressed(true);
                        }
                        else {
                            Log.e("gallery", "(BluetoothDemo.modeCurrent-1) === " + (BluetoothDemo.modeCurrent-1));
                            (mTabHost.getTabWidget().getChildTabViewAt((BluetoothDemo.modeCurrent-1))).setSelected(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((BluetoothDemo.modeCurrent-1))).setPressed(true);
                            (mTabHost.getTabWidget().getChildTabViewAt((BluetoothDemo.modeCurrent-1))).performClick();
                            (mTabHost.getTabWidget().getChildTabViewAt((BluetoothDemo.modeCurrent-1))).setPressed(true);
                        }
                        break;
                    }
                    case 13: {
                        if(usbOrSD) {
                            if(!usbStopFlag) {
                                if(usbPlayOrPauseFlag) {
                                    mUSBplayandstopBtn.setSelected(true);
                                    Log.e("gallery", "Music play");
                                }
                                else {
                                    mUSBplayandstopBtn.setSelected(false);
                                    Log.e("gallery", "Music pause");
                                }
                            }
                            Log.e("gallery", "playMusicMode = " + usbPlayMode);
                        }
                        else {
                            if(!sdStopFlag) {
                                if(sdPlayOrPauseFlag) {
                                    mSDplayandstopBtn.setSelected(true);
                                }
                                else {
                                    mSDplayandstopBtn.setSelected(false);
                                }
                            }
                        }
                        break;
                    }
                    case 144: {
                        Activity02.mSDPreNextShortClickEnable = true;
                        break;
                    }
                    case 14: {
                        if(usbOrSD) {
                            toShowTrackListBtn.setText(usbCurrentIndex + "/" + usbTotalNum);
                            //Log.e("gallery", "XX/YY === " + usbCurrentIndex + "/" + usbTotalNum);
                            String temporary1 = (usbCurrentTime%60) + "";
                            String temporary2 = (usbTotalTime%60) + "";
                            if(temporary1.length() == 1)
                                temporary1 = "0" + temporary1;
                            if(temporary2.length() == 1)
                                temporary2 = "0" + temporary2;
                            mUSBTrackPlayTimeText.setText(usbCurrentTime/60 + ":" + temporary1);
                            mUSBtrackTotalTimeText.setText(usbTotalTime/60 + ":" + temporary2);

                            if(usbTotalTime != 0) {
                                int maxLength = mUSBtrackSeekBar.getMax();
                                int currentLength = usbCurrentTime * maxLength / usbTotalTime;
                                mUSBtrackSeekBar.setProgress(currentLength);
                            }
                        }
                        else {
                            toSDShowTrackListBtn.setText(usbCurrentIndex + "/" + usbTotalNum);
                            //Log.e("gallery", "XX/YY === " + usbCurrentIndex + "/" + usbTotalNum);
                            String temporary1 = (usbCurrentTime%60) + "";
                            String temporary2 = (usbTotalTime%60) + "";
                            if(temporary1.length() == 1)
                                temporary1 = "0" + temporary1;
                            if(temporary2.length() == 1)
                                temporary2 = "0" + temporary2;
                            mSDTrackPlayTimeText.setText(usbCurrentTime/60 + ":" + temporary1);
                            mSDtrackTotalTimeText.setText(usbTotalTime/60 + ":" + temporary2);

                            if(usbTotalTime != 0) {
                                int maxLength = mSDtrackSeekBar.getMax();
                                int currentLength = usbCurrentTime * maxLength / usbTotalTime;
                                mSDtrackSeekBar.setProgress(currentLength);
                            }
                        }
                        break;
                    }
                    case 15: {
                        if(usbOrSD) {
                            mUSBAuthorTextStr = mUSBAuthorTextStr.trim();
                            mUSBAuthorText.setText(Activity02.mUSBAuthorTextStr);
                            mUSBAlbumTextStr = mUSBAlbumTextStr.trim();
                            mUSBAlbumText.setText(mUSBAlbumTextStr);
                            uSBalbumText8A = uSBalbumText8A.trim();
                            uSBalbumText1.setText(uSBalbumText8A);
                            uSBalbumText89 = uSBalbumText89.trim();
                            mUSBalbumText2.setText(uSBalbumText89);
                        }
                        else {
                            mUSBAuthorTextStr = mUSBAuthorTextStr.trim();
                            mSDAuthorText.setText(Activity02.mUSBAuthorTextStr);
                            mUSBAlbumTextStr = mUSBAlbumTextStr.trim();
                            mSDAlbumText.setText(mUSBAlbumTextStr);
                            uSBalbumText8A = uSBalbumText8A.trim();
                            mSDalbumText1.setText(uSBalbumText8A);
                            uSBalbumText89 = uSBalbumText89.trim();
                            mSDalbumText2.setText(uSBalbumText89);
                        }
                        break;
                    }
                    case 16: {
                        if(usbOrSD) {
                            if(usbPlayMode == 0)
                                mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_n);
                            else if(usbPlayMode == 1)
                                mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_folder_n);
                            else if(usbPlayMode == 2)
                                mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_one_n);
                            else if(usbPlayMode == 3)
                                mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);
                        }
                        else {
                            if(sdPlayMode == 0)
                                mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_n);
                            else if(sdPlayMode == 1)
                                mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_folder_n);
                            else if(sdPlayMode == 2)
                                mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_one_n);
                            else if(sdPlayMode == 3)
                                mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);
                        }
                        break;
                    }
                    case 100: {
                        /*if(progressBar != null)
                            progressBar.dismiss(); */
                    }
                    case 101: {
                        if(SettingActivity.settingControlFlag) {
                            switch(SettingActivity.mlist_preference_eqIndex) {
                                case 0:
                                    SettingActivity.mset_text_eq.setText("off");
                                    break;
                                case 1:
                                    SettingActivity.mset_text_eq.setText("Flat");
                                    break;
                                case 2:
                                    SettingActivity.mset_text_eq.setText("Classic");
                                    break;
                                case 3:
                                    SettingActivity.mset_text_eq.setText("Rock");
                                    break;
                                case 4:
                                    SettingActivity.mset_text_eq.setText("Pop");
                                    break;
                            }
                        }
                        break;
                    }
                    case 102: {
                        if(SettingActivity.settingControlFlag) {
                            SettingActivity.mbalanceSendFlag = false;
                            SettingActivity.mset_seekbar_balance.setProgress(SettingActivity.mbalance_preferenceIndex);
                            if(SettingActivity.mbalance_preferenceIndex == 7) {
                                SettingActivity.mset_text_balance.setText("00");
                            }
                            else if(SettingActivity.mbalance_preferenceIndex > 7) {
                                SettingActivity.mset_text_balance.setText("R0" + (SettingActivity.mbalance_preferenceIndex-7));
                            }
                            else if(SettingActivity.mbalance_preferenceIndex < 7) {
                                SettingActivity.mset_text_balance.setText("L0" + (7-SettingActivity.mbalance_preferenceIndex));
                            }
                        }
                        break;
                    }
                    case 103: {
                        if(SettingActivity.settingControlFlag) {
                            SettingActivity.mfaderSendFlag = false;
                            SettingActivity.mseek_seekbar_fader.setProgress(SettingActivity.mfader_preferenceIndex);
                            if(SettingActivity.mfader_preferenceIndex == 7) {
                                SettingActivity.mset_text_fader.setText("00");
                            }
                            else if(SettingActivity.mfader_preferenceIndex > 7) {
                                SettingActivity.mset_text_fader.setText("F0" + (SettingActivity.mfader_preferenceIndex-7));
                            }
                            else if(SettingActivity.mfader_preferenceIndex < 7) {
                                SettingActivity.mset_text_fader.setText("R0" + (7-SettingActivity.mfader_preferenceIndex));
                            }
                        }
                        break;
                    }
                    case 104: {
                        if(SettingActivity.settingControlFlag) {
                            SettingActivity.mswitch_loudnessSendFlag = false;
                            if(SettingActivity.loudnessFlag) {
                                SettingActivity.mswitch_loudness.setChecked(true);
                            }
                            else {
                                SettingActivity.mswitch_loudness.setChecked(false);
                            }
                        }
                        break;
                    }
                    case 202: {
                        try {
                            if (BluetoothDemo.outStream != null) {
                                BluetoothDemo.outStream.close();
                                BluetoothDemo.outStream = null;
                            }
                            if (BluetoothDemo.inputStream != null) {
                                BluetoothDemo.inputStream.close();
                                BluetoothDemo.inputStream = null;
                            }
                            if (BluetoothDemo.btSocket != null) {
                                BluetoothDemo.btSocket.close();
                                BluetoothDemo.btSocket = null;
                            }
                        } catch (IOException ioExceptionioException) {

                        }
                        BluetoothDemo.mContext.finish();
                        galleryActivity.finish();
                        System.exit(0);
                        break;
                    }
                    case -1:
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    private RadioGroup.OnCheckedChangeListener BottomLYRadioGroupChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int index = group.getId();
            if (group != null && checkedId > -1 && changeGroup == false){
                if(index == R.id.grouptop){
                    changeGroup = true;
                    Log.e("donghui","checkedId"+checkedId);
                    Log.e("donghui","R.id.group_1"+R.id.group_1);
                    //updateRadioChannel( checkedId);
                    mBottomLYRadioGroupBottom.clearCheck();
                    changeGroup = false;
                }else if(index == R.id.groupbottom){
                    changeGroup = true;
                    mBottomLYRadioGroupTop.clearCheck();
                    //updateRadioChannel( checkedId);
                    changeGroup = false;
                }
            }
        }
    };
    private View.OnLongClickListener TabViewLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            Log.d("tab number", ( v.getTag()).toString());
            if (v.getTag().toString().equalsIgnoreCase("tab9")) {
                showLongClickMenu(v,"tab9");
                return true;

            } else if (v.getTag().toString().equalsIgnoreCase("tab10")) {
                showLongClickMenu(v,"tab10");
                return true;

            } else if (v.getTag().toString().equalsIgnoreCase("tab11")) {
                showLongClickMenu(v,"tab11");
                return true;

            }
            return false;
        }
    };
    private LinearLayout mVolLY;
    private LinearLayout mMapLY;
    private LinearLayout mPhoneLY;
    private LinearLayout mA2DP2LY;
    private HashMap<String,String> mInstalledAppsHashmap;
    private FrameLayout mtab10LY;
    private FrameLayout mtab11LY;
    private FrameLayout mtab9LY;
    private HashMap<String, Drawable> mInstalledAppsIconHashmap;
    private PInfo pinfox;
    private AlertDialog.Builder apppbuilder;
    private ListView mAppListView;
    private ArrayList<HashMap<String,Object>> mylist;
    private MyAdapter mAppAadapter;

    private void showLongClickMenu(View v, final String tabId) {
       if (Build.VERSION.SDK_INT <= 11) {
           new AlertDialog.Builder(Activity02.this)
                   .setItems(R.array.appselect_array, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           String[] items = getResources().getStringArray(R.array.appselect_array);
                           if (items[which].equalsIgnoreCase("Remove")){
                               if(tabId.trim().equalsIgnoreCase("tab9")) {
                                   setTab9APPName("");
                                   inittab9LY();
                               }
                               else if(tabId.trim().equalsIgnoreCase("tab10")) {
                                   setTab10APPName("");
                                   inittab10LY();
                               }
                               else if(tabId.trim().equalsIgnoreCase("tab11")) {
                                   setTab11APPName("");
                                   inittab11LY();
                               }
                               initAddAppNameView();
                               initAddAppIconView();
                           } else if (items[which].equalsIgnoreCase("Application")){
                               if(tabId.trim().equalsIgnoreCase("tab9")) {
                                   showAppSelectDiaglog("tab9");
                                   inittab9LY();
                               }
                               else if(tabId.trim().equalsIgnoreCase("tab10")) {
                                   showAppSelectDiaglog("tab10");
                                   inittab10LY();
                               }
                               else if(tabId.trim().equalsIgnoreCase("tab11")) {
                                   showAppSelectDiaglog("tab11");
                                   inittab11LY();
                               }
                               initAddAppNameView();
                               initAddAppIconView();
                           }
                       }
                   })
                   .create().show();
       }else {
           PopupMenu popup = new PopupMenu(this, v);
           popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

           popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
               public boolean onMenuItemClick(MenuItem item) {
                   if (item.getTitle().toString().equalsIgnoreCase("Remove")){
                       if(tabId.trim().equalsIgnoreCase("tab9")) {
                           setTab9APPName("");
                           inittab9LY();
                       }
                       else if(tabId.trim().equalsIgnoreCase("tab10")) {
                           setTab10APPName("");
                           inittab10LY();
                       }
                       else if(tabId.trim().equalsIgnoreCase("tab11")) {
                           setTab11APPName("");
                           inittab11LY();
                       }
                       initAddAppNameView();
                       initAddAppIconView();
                   } else if (item.getTitle().toString().equalsIgnoreCase("Application")){
                       if(tabId.trim().equalsIgnoreCase("tab9")) {
                           showAppSelectDiaglog("tab9");
                           inittab9LY();
                       }
                       else if(tabId.trim().equalsIgnoreCase("tab10")) {
                           showAppSelectDiaglog("tab10");
                           inittab10LY();
                       }
                       else if(tabId.trim().equalsIgnoreCase("tab11")) {
                           showAppSelectDiaglog("tab11");
                           inittab11LY();
                       }
                       initAddAppNameView();
                       initAddAppIconView();
                   }
//                Toast.makeText(Activity02.this, "Clicked popup menu item " + item.getTitle(),
//                        Toast.LENGTH_SHORT).show();
                   return true;
               }
           });

           popup.show();
       }

    }

    private void updateRadioChannel(int index) {
        switch (index){
            case R.id.group_1:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm1Value-minFMvalue)/mFmStepValue);
                    //FF 77 06 07 XX 00 00 0D 0A
                    byte[] blueOrder1 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x01, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder1);
                    mChannelText.setText(new StringBuffer("Preset: ").append("1"));
                }
                break;
            case R.id.group_2:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm2Value-minFMvalue)/mFmStepValue);
                    byte[] blueOrder2 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x02, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder2);
                    mChannelText.setText(new StringBuffer("Preset: ").append("2"));
                }
                break;
            case R.id.group_3:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm3Value-minFMvalue)/mFmStepValue);
                    byte[] blueOrder3 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x03, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder3);
                    mChannelText.setText(new StringBuffer("Preset: ").append("3"));
                }
                break;
            case R.id.group_4:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm4Value-minFMvalue)/mFmStepValue);
                    byte[] blueOrder4 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x04, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder4);
                    mChannelText.setText(new StringBuffer("Preset: ").append("4"));
                }
                break;
            case R.id.group_5:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm5Value-minFMvalue)/mFmStepValue);
                    byte[] blueOrder5 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x05, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder5);
                    mChannelText.setText(new StringBuffer("Preset: ").append("5"));
                }
                break;
            case R.id.group_6:
                if(bottomLYRadioGroupFlag) {
                    //mChannelSeekBar.setProgress((mFMm6Value-minFMvalue)/mFmStepValue);
                    byte[] blueOrder6 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x06, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A };
                    BluetoothDemo.sendBlueOrder(blueOrder6);
                    mChannelText.setText(new StringBuffer("Preset: ").append("6"));
                }
                break;
        }
    }

    private View.OnLongClickListener BottomLYRadioLongListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int index = v.getId();
             switch (index)  {
                 case R.id.group_1:
                     //showLongClickDiaglog(R.id.group_1);
                     byte[] blueOrder1 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x01, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder1);
                     break;
                 case R.id.group_2:
                     //showLongClickDiaglog(R.id.group_2);
                     byte[] blueOrder2 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x02, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder2);
                     break;
                 case R.id.group_3:
                     //showLongClickDiaglog(R.id.group_3);
                     byte[] blueOrder3 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x03, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder3);
                     break;
                 case R.id.group_4:
                     //showLongClickDiaglog(R.id.group_4);
                     byte[] blueOrder4 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x04, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder4);
                     break;
                 case R.id.group_5:
                     //showLongClickDiaglog(R.id.group_5);
                     byte[] blueOrder5 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x05, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder5);
                     break;
                 case R.id.group_6:
                     //showLongClickDiaglog(R.id.group_6);
                     byte[] blueOrder6 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x06, (byte)0x06, (byte)0x00,
                             (byte)0x00, (byte)0x0D, (byte)0x0A };
                     BluetoothDemo.sendBlueOrder(blueOrder6);
                     break;

             }
            return true;
        }
    };	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("donghui","onCreate");
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.main2);
        mPreferences = Preferences.build(this, "app_config");
        galleryActivity = Activity02.this;
        rs = galleryActivity.getResources();
        xpackageMan = getPackageManager();
        mInstalledAppsHashmap = getInstalledAppsHashmap(false);
        mInstalledAppsIconHashmap = getInstalledAppsIconHashmap(false);
        _a_initData();
        _b_initView();

        String temporaryUSBSD = MathMethod.readSharedPreferences("usbORsd", "USB");
        if(temporaryUSBSD.equals("USB")) {
            usbOrSD = true;
        }
        else if(temporaryUSBSD.equals("SD")) {
            usbOrSD = false;
        }

        /*mTimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable(){
                    public void run() {
                        if(mFMValueChange){
                            mFMValueChange = false;
                            mFMValue.setTextColor(Color.TRANSPARENT); //这个是透明，=看不到文字
                        }else{
                            mFMValueChange = true;
                            mFMValue.setTextColor(Color.parseColor("#ff32b6e6"));
                        }
                    }});
            }
        };*/
    }

    /*private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(mFMValueChange){
                                mFMValueChange = false;
                                mFMValue.setTextColor(Color.TRANSPARENT); //这个是透明，=看不到文字
                            }else{
                                mFMValueChange = true;
                                mFMValue.setTextColor(Color.parseColor("#ff32b6e6"));
                            }
                        }});
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, 1, 500);
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("donghui","oncreate mTabHost.setCurrentTab mSaveTabSelect"+mSaveTabSelect);
//        mTabHost.setCurrentTab(mSaveTabSelect);
        Log.e("donghui","onResume");

        realExit = true;
    }

    private void _a_initData() {
        mTunerValue = 41;
    }

    private View createIndicatorView(TabHost tabHost, Drawable icon, final String tabId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator,
                tabHost.getTabWidget(), // tab widget is the parent
                false); // no inflate params


        final ImageView iconView = (ImageView) tabIndicator.findViewById(R.id.indicator_icon);
        final TextView textView = (TextView) tabIndicator.findViewById(R.id.indicator_text);
        ViewGroup.LayoutParams lp = iconView.getLayoutParams();
        int den =(int) mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            lp.height = 60*den;

        }else if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT){
            lp.width = 68*den;
        }

        iconView.setLayoutParams(lp);
//        Log.e("donghui","iconView.getMeasuredWidth()"+iconView.getMeasuredWidth());
//        Log.e("donghui","iconView.getWidth()"+iconView.getWidth());
        iconView.setImageDrawable(icon);




        return tabIndicator;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("donghui","onConfigurationChanged");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        }
    }
    private void _b_initView() {
        listPackages();
        initMainTopView();

        initAddAppNameView();
        initAddAppIconView();

        initRadioLY();
        /***初始化USB和 SD页面**/
        initMusicLY();
        //initA2DPLY();
        initA2DPLY222();
        initMainBottomView();
        initPhoneLY();
        initMapLY();
        initVolLY();
        inittab9LY();
        inittab10LY();
        inittab11LY();

    }

    private void inittab11LY() {
        mtab11LY = (FrameLayout) findViewById(R.id.include_tab11_ly);
        if (getTab11AppName().equalsIgnoreCase("")){
            mtab11LY.findViewById(R.id.start).setVisibility(View.GONE);
        }   else {
            mtab11LY.findViewById(R.id.start).setVisibility(View.VISIBLE);
        }
        mtab11LY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xintent = xpackageMan.getLaunchIntentForPackage(getTab11AppName());
                if(xintent != null) {
                    realExit = false;
                    startActivity(xintent);
                }else {
                    Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inittab10LY() {

        mtab10LY = (FrameLayout) findViewById(R.id.include_tab10_ly);
        if (getTab10AppName().equalsIgnoreCase("")){
            mtab10LY.findViewById(R.id.start).setVisibility(View.GONE);
        }   else {
            mtab10LY.findViewById(R.id.start).setVisibility(View.VISIBLE);
        }
        mtab10LY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xintent = xpackageMan.getLaunchIntentForPackage(getTab10AppName());
                if(xintent != null) {
                    realExit = false;
                    startActivity(xintent);
                }else {
                    Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inittab9LY() {
        mtab9LY = (FrameLayout) findViewById(R.id.include_tab9_ly);
        if (getTab9AppName().equalsIgnoreCase("")){
            mtab9LY.findViewById(R.id.start).setVisibility(View.GONE);
        }   else {
            mtab9LY.findViewById(R.id.start).setVisibility(View.VISIBLE);
        }
        mtab9LY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xintent = xpackageMan.getLaunchIntentForPackage(getTab9AppName());
                if(xintent != null) {
                    realExit = false;
                    startActivity(xintent);
                }else {
                    Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initVolLY() {
        mVolLY = (LinearLayout) findViewById(R.id.include_vol_ly);
        mVolLY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    PInfo ppp = null;
                    Iterator iter = kaijuanyouyi.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Object key = entry.getKey();
                        Object val = entry.getValue();
                        String appName = (String) key;
                        ppp = (PInfo) val;
                        if(ppp.pname.contains("com.google.android.googlequicksearchbox")) {
                            break;
                        }
                    }
                    Log.e("gallery", "voicesearch === " + ppp.pname);
                    if(ppp.pname.contains("com.google.android.googlequicksearchbox")) {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                        if(xintent != null) {
                            realExit = false;
                            startActivity(xintent);
                        }
                    }
                    else {
                        Toast.makeText(galleryActivity, "Not find Voice Search", 1).show();
                    }
                }
        });

    }

    private void initMapLY() {
        mMapLY = (LinearLayout) findViewById(R.id.include_map_ly);
        mMapLY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean temporaryMapFlag = true;
                PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    //if(appName.contains("地图") || appName.toUpperCase().contains("MAP")) {
                        Log.e("gallery", "MAP1 appname pname === " + ppp.appname + " === " + ppp.pname);
                        if(ppp.pname.equals("com.google.android.apps.maps")) {
                            Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                            if(xintent != null) {
                                realExit = false;
                                temporaryMapFlag = false;
                                startActivity(xintent);
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    //}
                }
                if(temporaryMapFlag) {
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Object key = entry.getKey();
                        Object val = entry.getValue();
                        String appName = (String) key;
                        ppp = (PInfo) val;
                        if(appName.contains("地图") || appName.toUpperCase().contains("MAP")) {
                            Log.e("gallery", "MAP2 appname pname === " + ppp.appname + " === " + ppp.pname);
                            Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                            if(xintent != null) {
                                realExit = false;
                                startActivity(xintent);
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
        });

    }

    private void initPhoneLY() {

        mPhoneLY = (LinearLayout) findViewById(R.id.include_phone_ly);
        mPhoneLY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    if(appName.contains("拨号")) {
                        break;
                    }
                }
                Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                if(xintent != null)
                    startActivity(xintent);*/
                realExit = false;
                Uri telUri = Uri.parse("tel: ");
                Intent returnIt = new Intent(Intent.ACTION_DIAL, telUri);
                startActivity(returnIt);
            }
        });
    }

    private void initMainBottomView() {
        mBottomControlPanel = (LinearLayout) findViewById(R.id.bottomControlPanel);

        volumeDownBtn = (ImageButton) mBottomControlPanel.findViewById(R.id.volumeDownBtn);
        volumeUpBtn = (ImageButton) mBottomControlPanel.findViewById(R.id.volumeUpBtn);
        volumeDownBtn.setOnClickListener(VolumeClickListener);
        volumeUpBtn.setOnClickListener(VolumeClickListener);

        powerBtn = (ImageButton) mBottomControlPanel.findViewById(R.id.powerBtn);
        powerBtn.setOnClickListener(VolumeClickListener);
        settingBtn = (ImageButton) mBottomControlPanel.findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(VolumeClickListener);
        mMuteBtn = (ImageView) mBottomControlPanel.findViewById(R.id.muteBtn);
        mMuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 静音操作
                if (v.isSelected()) {
                    v.setSelected(false);

                    Log.e("gallery", "Not - Mute");

                    String volumeStrHex = Integer.toHexString(Integer.parseInt(volumeArray[cSpinner.getSelectedItemPosition()]));
                    byte volumeByteHex = MathMethod.HexStringToBytes(volumeStrHex);

                    //FF77030103xx000D0A
                    byte[]orderBytes = {(byte)0xFF, (byte)0x77, (byte)0x03, (byte)0x01, (byte)0x03, volumeByteHex,
                            (byte)0x00, (byte)0x0D, (byte)0x0A};

                    BluetoothDemo.sendBlueOrder(orderBytes);

                } else {
                    v.setSelected(true);

                    Log.e("gallery", "Mute");
                    //FF7703000300040D0A
                    byte[]orderBytes = {(byte)0xFF, (byte)0x77, (byte)0x03, (byte)0x00, (byte)0x03, (byte)0x00,
                            (byte)0x04, (byte)0x0D, (byte)0x0A};

                    BluetoothDemo.sendBlueOrder(orderBytes);
                }
            }
        });


        cSpinner = (CustomSpinner) findViewById(R.id.volumeSpinner);
        adapter = new ArrayAdapter<String>(Activity02.galleryActivity, android.R.layout.simple_spinner_item, volumeArray);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        cSpinner.setAdapter(adapter);
        cSpinner.setSelection(0);
        //添加事件Spinner事件监听
        cSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        cSpinner.setVisibility(View.VISIBLE);
        cSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("gallery", "OnTouch");
                cSpinnerTouchFlag = true;
                return false;
            }
        });

        positionVolumeMap = new HashMap<String, Integer>();
        for (int i = 0; i <= 40; i++) {
            positionVolumeMap.put(i + "", (i));
        }

        try {
            //FF7705000100000D0A
            /*byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x05, (byte)0x00,
                (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};*/
            //FF 77 05 00 00 00 00 0D 0A
            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x05, (byte)0x00,
                    (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
            BluetoothDemo.sendBlueOrder(blueOrder);
        } catch(Exception eee) {
            Message msg = new Message();
            msg.what = 10;
            Activity02.handlerRadioInterface.sendMessage(msg);
        }

    }

    private void initRadioLY() {
        /***声音机界面**/
        mTunerLY = (LinearLayout) findViewById(R.id.include_tuner_ly);
        mFMSpinner = (CustomSpinner) findViewById(R.id.tunerSpinner);
        mFMSpinnerAdapter = new ArrayAdapter<String>(Activity02.galleryActivity, android.R.layout.simple_spinner_item, mFMArray);
        //设置下拉列表的风格
        mFMSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        mFMSpinner.setAdapter(mFMSpinnerAdapter);
        mFMSpinner.setSelection(0);
        //添加事件Spinner事件监听
        mFMSpinner.setOnItemSelectedListener(new FMSpinnerSelectedListener());
        mFMSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("gallery", "OnTouch");
                FMchangeAndSendOrder = true;
                return false;
            }
        });

        //设置默认值
        mFMSpinner.setVisibility(View.VISIBLE);

        mChannelText = (TextView) mTunerLY.findViewById(R.id.channelText);
        mChannelSeekBar = (SeekBar) mTunerLY.findViewById(R.id.channelSeekBar);
        mChannelSeekBar.setOnSeekBarChangeListener(this);
        mChannelSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mChannelSeekBarTouchFlag = true;
                return false;
            }
        });

        setAMProgress(mTunerValue);
        mRadiotunerIntroPlayBtn = (ImageButton) mTunerLY.findViewById(R.id.tunerIntroPlayBtn);
        mRadiotunerIntroPlayBtn.setOnClickListener(ChannelChangedListener);
        mPreChannelButton = (ImageButton) mTunerLY.findViewById(R.id.preChannelButton);
        mNextChannelButton = (ImageButton) mTunerLY.findViewById(R.id.nextChannelButton);
        mPreChannelButton.setOnClickListener(ChannelChangedListener);
        mNextChannelButton.setOnClickListener(ChannelChangedListener);
        mFMValue = (TextView) mTunerLY.findViewById(R.id.fmText);

        mTunerScanDownBtn = (ImageButton) mTunerLY.findViewById(R.id.tunerScanDownBtn);
        mTunerScanUpBtn = (ImageButton) mTunerLY.findViewById(R.id.tunerScanUpBtn);
        mTunerScanDownBtn.setOnClickListener(TunerScanListener);
        mTunerScanUpBtn.setOnClickListener(TunerScanListener);

        mStereoText = (TextView) mTunerLY.findViewById(R.id.stereoText);
        mToInputLY = (LinearLayout) getLayoutInflater().inflate(R.layout.channel_digit, (ViewGroup) mTabHost.getRootView(), false);
        mChannelTextView = (TextView) mToInputLY.findViewById(R.id.channelTextView1);
        Button button = (Button) mToInputLY.findViewById(R.id.btn1);
        Button button1 = (Button) mToInputLY.findViewById(R.id.btn2);
        Button button2 = (Button) mToInputLY.findViewById(R.id.btn3);
        Button button3 = (Button) mToInputLY.findViewById(R.id.btn4);
        Button button4 = (Button) mToInputLY.findViewById(R.id.btn5);
        Button button5 = (Button) mToInputLY.findViewById(R.id.btn6);
        Button button6 = (Button) mToInputLY.findViewById(R.id.btn7);
        Button button7 = (Button) mToInputLY.findViewById(R.id.btn8);
        Button button8 = (Button) mToInputLY.findViewById(R.id.btn9);
        Button button9 = (Button) mToInputLY.findViewById(R.id.btn0);
        Button button10 = (Button) mToInputLY.findViewById(R.id.btnDot);
        Button button11 = (Button) mToInputLY.findViewById(R.id.btnDel);
        button.setOnClickListener(ToInputLisenter);
        button1.setOnClickListener(ToInputLisenter);
        button2.setOnClickListener(ToInputLisenter);
        button3.setOnClickListener(ToInputLisenter);
        button4.setOnClickListener(ToInputLisenter);
        button5.setOnClickListener(ToInputLisenter);
        button6.setOnClickListener(ToInputLisenter);
        button7.setOnClickListener(ToInputLisenter);
        button8.setOnClickListener(ToInputLisenter);
        button9.setOnClickListener(ToInputLisenter);
        button10.setOnClickListener(ToInputLisenter);
        button11.setOnClickListener(ToInputLisenter);
        ImageButton mToInputButton = (ImageButton) mTunerLY.findViewById(R.id.toInputButton);
        mToInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToInputDialog();
            }
        });
        mBottomTabLY = (LinearLayout) mTunerLY.findViewById(R.id.bottomTabLayout);
        mBottomLYRadioGroupTop = (RadioGroup) mBottomTabLY.findViewById(R.id.grouptop);
        mBottomLYRadioGroupTop.setOnCheckedChangeListener(BottomLYRadioGroupChangeListener);

        mBottomLYRadioGroupBottom = (RadioGroup) mBottomTabLY.findViewById(R.id.groupbottom);
        mBottomLYRadioGroupBottom.setOnCheckedChangeListener(BottomLYRadioGroupChangeListener);

        mBottomLYRadio1 = (RadioButton) mBottomTabLY.findViewById(R.id.group_1);
        mBottomLYRadio1.setLongClickable(true);
        mBottomLYRadio1.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio1.setOnClickListener(RadioBtnClickListener);

        mBottomLYRadio2 = (RadioButton) mBottomTabLY.findViewById(R.id.group_2);
        mBottomLYRadio2.setLongClickable(true);
        mBottomLYRadio2.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio2.setOnClickListener(RadioBtnClickListener);

        mBottomLYRadio3 = (RadioButton) mBottomTabLY.findViewById(R.id.group_3);
        mBottomLYRadio3.setLongClickable(true);
        mBottomLYRadio3.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio3.setOnClickListener(RadioBtnClickListener);

        mBottomLYRadio4 = (RadioButton) mBottomTabLY.findViewById(R.id.group_4);
        mBottomLYRadio4.setLongClickable(true);
        mBottomLYRadio4.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio4.setOnClickListener(RadioBtnClickListener);

        mBottomLYRadio5 = (RadioButton) mBottomTabLY.findViewById(R.id.group_5);
        mBottomLYRadio5.setLongClickable(true);
        mBottomLYRadio5.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio5.setOnClickListener(RadioBtnClickListener);

        mBottomLYRadio6 = (RadioButton) mBottomTabLY.findViewById(R.id.group_6);
        mBottomLYRadio6.setLongClickable(true);
        mBottomLYRadio6.setOnLongClickListener(BottomLYRadioLongListener);
        mBottomLYRadio6.setOnClickListener(RadioBtnClickListener);
    }

    private void initMainTopView() {
        Resources res = getResources(); // Resource object to get Drawables
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();

        mTabHost.addTab(mTabHost.newTabSpec("tab1")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_tuner),"tab1"))
                .setContent(R.id.tab1));
        /*mTabHost.addTab(mTabHost.newTabSpec("tab2")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_cd)))
                .setContent(R.id.tab2));*/
        mTabHost.addTab(mTabHost.newTabSpec("tab2")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_usb),"tab2"))
                .setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("tab3")
                .setIndicator(createIndicatorView(mTabHost, getResources().getDrawable(R.drawable.comm_mode_tab_sd),"tab3"))
                .setContent(R.id.tab3));
        mTabHost.addTab(mTabHost.newTabSpec("tab4")
                .setIndicator(createIndicatorView(mTabHost, getResources().getDrawable(R.drawable.comm_mode_tab_phone),"tab4"))
                .setContent(R.id.tab4));
        mTabHost.addTab(mTabHost.newTabSpec("tab5")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_map),"tab5"))
                .setContent(R.id.tab5));
        mTabHost.addTab(mTabHost.newTabSpec("tab6")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_vol),"tab6"))
                .setContent(R.id.tab6));
        mTabHost.addTab(mTabHost.newTabSpec("tab7")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_aux),"tab7"))
                .setContent(R.id.tab7));
        mTabHost.addTab(mTabHost.newTabSpec("tab8")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_bt),"tab8"))
                .setContent(R.id.tab8));
        mTabHost.addTab(mTabHost.newTabSpec("tab9")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_add),"tab9"))
                .setContent(R.id.tab9));
        mTabHost.addTab(mTabHost.newTabSpec("tab10")
                .setIndicator(createIndicatorView(mTabHost,getResources().getDrawable(R.drawable.comm_mode_tab_add),"tab10"))
                .setContent(R.id.tab10));
        mTabHost.addTab(mTabHost.newTabSpec("tab11")
                .setIndicator(createIndicatorView(mTabHost, getResources().getDrawable(R.drawable.comm_mode_tab_add),"tab11"))
                .setContent(R.id.tab11));



        int numberOfTabs = mTabHost.getTabWidget().getChildCount();
        for(int t=0; t<numberOfTabs; t++){
            View tabView = mTabHost.getTabWidget().getChildAt(t);
            tabView.setTag( "tab"+(Integer.valueOf(t)+1));
            tabView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    tabHostTouchFlag = true;
                    String tabId = (String) v.getTag();
                    return false;
                }
            });

            tabView.setOnLongClickListener( TabViewLongClickListener);
        }

        boolean flag;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            flag = true;
        else
            flag = false;
        if (flag)  {
            mTabHost.getTabWidget().setOrientation(TabWidget.VERTICAL);
        }
//        Log.e("donghui","mTabHost.getTabWidget().setOrientation "+mTabHost.getTabWidget().getOrientation());
        mTabHost.setOnTabChangedListener(this);
        ////注意这个是在横竖屏读取上次的Tab index,然后设置


    }


    private void initAddAppNameView(){
//        init9AppNameView();
//        init10AppNameView();
//        init11AppNameView();
    }

    private void init11AppNameView() {
        TextView textview11 = (TextView) mTabHost.getTabWidget().getChildAt(10).findViewById(R.id.indicator_text);
        Set set = mInstalledAppsHashmap.entrySet();
        Iterator it = set.iterator();
        String appname = "";
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue().equals(getTab11AppName())) {
                appname = (String) entry.getKey();
                continue;
            }
        }
        textview11.setVisibility(View.GONE);
        //textview.setText(appname);
        String temporaryAppname = appname;
        if (appname.length() > 3)
            temporaryAppname = appname.substring(0, 3) + "...";
        textview11.setText(temporaryAppname);
    }

    private void init10AppNameView() {
        TextView textview10 = (TextView)mTabHost.getTabWidget().getChildAt(9).findViewById(R.id.indicator_text);
        Set set=mInstalledAppsHashmap.entrySet();
        Iterator it=set.iterator();
        String appname = "";
        while(it.hasNext()) {
            Map.Entry entry=(Map.Entry)it.next();
            if(entry.getValue().equals(getTab10AppName())) {
                appname= (String) entry.getKey();
                continue;
            }
        }
        textview10.setVisibility(View.GONE);
        //textview.setText(appname);
        String temporaryAppname = appname;
        if(appname.length() > 3)
            temporaryAppname = appname.substring(0, 3) + "...";
        textview10.setText(temporaryAppname);
    }

    private void init9AppNameView() {
        TextView textview9 = (TextView)mTabHost.getTabWidget().getChildAt(8).findViewById(R.id.indicator_text);
        Set set=mInstalledAppsHashmap.entrySet();
        Iterator it=set.iterator();
        String appname = "";
        while(it.hasNext()) {
            Map.Entry entry=(Map.Entry)it.next();
            if(entry.getValue().equals(getTab9AppName())) {
                appname= (String) entry.getKey();
                continue;
            }
        }
        textview9.setVisibility(View.GONE);
        String temporaryAppname = appname;
        if(appname.length() > 3)
            temporaryAppname = appname.substring(0, 3) + "...";
        textview9.setText(temporaryAppname);
    }

    private void initAddAppIconView(){
        initAddApp9Icon();


        initAppIcon10();
        initAppIcon11();
    }

    private void initAppIcon11() {
        ImageView imageView11 = (ImageView)mTabHost.getTabWidget().getChildTabViewAt(10).findViewById(R.id.indicator_icon);
        imageView11.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showLongClickMenu(v,"tab11");
                    return true;
                }
            });
        imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    blueOrder[3] = (byte)0x0C;
//                    BluetoothDemo.sendBlueOrder(blueOrder);

                    String appname = getTab11AppName();
                    if (appname.equalsIgnoreCase("")){
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0C, (byte)0x00, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        showAppSelectDiaglog("tab11");
                    }   else {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                        if(xintent != null) {
                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0C, (byte)0x00, (byte)0x00, (byte)0x00
                                    , (byte)0x00, (byte)0x0D, (byte)0x0A};
                            BluetoothDemo.sendBlueOrder(blueOrder);
                            realExit = false;
                            startActivity(xintent);
                        } else {
                            Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        if (getTab11AppName().equalsIgnoreCase("")){
            imageView11.setImageDrawable(getResources().getDrawable(R.drawable.comm_mode_tab_add));
        }   else {
            imageView11.setImageDrawable(mInstalledAppsIconHashmap.get(getTab11AppName()));
        }
    }

    private void initAppIcon10() {
        LinearLayout  aaa = (LinearLayout)  mTabHost.getTabWidget().getChildTabViewAt(9);
        ImageView imageView10= (ImageView)aaa.findViewById(R.id.indicator_icon);
        imageView10.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showLongClickMenu(v,"tab10");
                    return true;
                }
            });
        imageView10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    blueOrder[3] = (byte)0x0B;
//                    BluetoothDemo.sendBlueOrder(blueOrder);

                    String appname =  getTab10AppName();
                    if (appname.equalsIgnoreCase("")){
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        showAppSelectDiaglog("tab10");
                    }   else {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                        if(xintent != null) {
                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00
                                    , (byte)0x00, (byte)0x0D, (byte)0x0A};
                            BluetoothDemo.sendBlueOrder(blueOrder);
                            realExit = false;
                            startActivity(xintent);
                        } else {
                            Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        if (getTab10AppName().equalsIgnoreCase("")){
            imageView10.setImageDrawable(getResources().getDrawable(R.drawable.comm_mode_tab_add));
        }   else {
            imageView10.setImageDrawable(mInstalledAppsIconHashmap.get(getTab10AppName()));
        }
    }

    private void initAddApp9Icon() {
        ImageView imageView9 = (ImageView)mTabHost.getTabWidget().getChildTabViewAt(8).findViewById(R.id.indicator_icon);
        imageView9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showLongClickMenu(v,"tab9");
                return true;
            }
        });
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    blueOrder[3] = (byte)0x0A;
//                    BluetoothDemo.sendBlueOrder(blueOrder);

                String appname =  getTab9AppName();
                if (appname.equalsIgnoreCase("")){
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0A, (byte)0x00, (byte)0x00, (byte)0x00
                            , (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                    showAppSelectDiaglog("tab9");
                }   else {
                    Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                    if(xintent != null) {
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x0A, (byte)0x00, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        realExit = false;
                        startActivity(xintent);
                    }else {
                        Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        if (getTab9AppName().equalsIgnoreCase("")){
            imageView9.setImageDrawable(getResources().getDrawable(R.drawable.comm_mode_tab_add));
        }   else {
            imageView9.setImageDrawable(mInstalledAppsIconHashmap.get(getTab9AppName()));
        }
    }

    private void initMusicLY() {
        /***初始化USB页面*****/
//        if (mMusicUSBLY == null){
//            return;
//        }
        mMusicUSBLY = (FrameLayout) findViewById(R.id.include_usb_ly);
//        if (mMusicUSBLY == null){
//            return;
//        }
        mUSBTopBarTitle = (TextView) mMusicUSBLY.findViewById(R.id.topBarTitle);
        toShowTrackListBtn = (TextView) mMusicUSBLY.findViewById(R.id.toShowTrackListBtn);
        mUSBAlbumText = (TextView) mMusicUSBLY.findViewById(R.id.albumText);
        mUSBAuthorText = (TextView) mMusicUSBLY.findViewById(R.id.authorText);
        uSBalbumText1 = (TextView) mMusicUSBLY.findViewById(R.id.albumText1);
        mUSBalbumText2 = (TextView) mMusicUSBLY.findViewById(R.id.albumText2);

        mUSBAlbumImageView = (ImageView) mMusicUSBLY.findViewById(R.id.albumImageView);
        mUSBXunhuanPlay = (ImageView) mMusicUSBLY.findViewById(R.id.xunhuanPlay);
        mUSBXunhuanPlay.setOnClickListener(USBBtnClickListener);
        mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);

        mUSBsuijiPlay = (ImageView) mMusicUSBLY.findViewById(R.id.suijiPlay);
        mUSBsuijiPlay.setVisibility(View.INVISIBLE);
        mUSBsuijiPlay.setOnClickListener(USBBtnClickListener);
        mUSBTrackPlayTimeText = (TextView) mMusicUSBLY.findViewById(R.id.trackPlayTimeText);
        mUSBtrackTotalTimeText = (TextView) mMusicUSBLY.findViewById(R.id.trackTotalTimeText);
        mUSBtrackSeekBar = (MySeekBar) mMusicUSBLY.findViewById(R.id.trackUSBSeekBar);
        mUSBtrackSeekBar.setVisibility(View.VISIBLE);
        mUSBtrackSeekBar.setOnSeekBarChangeListener(this);

        mUSBtrackPreBtn = (ImageButton) mMusicUSBLY.findViewById(R.id.trackPreBtn);
        mUSBtrackPreBtn.setOnClickListener(USBBtnClickListener);
        mUSBtrackPreBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //FF 77 0B 04 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x04, (byte)0x00, (byte)0x00
                        , (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);

                mUSBPreNextShortClickEnable = true;
                mUSBPreNextLongShortClickEnable = false;

                /*byte[] blueOrder1 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x05
                        , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                BluetoothDemo.sendBlueOrder(blueOrder1);*/

                return false;
            }
        });

        mUSBplayandstopBtn = (ImageButton) mMusicUSBLY.findViewById(R.id.playandstopBtn);
        mUSBplayandstopBtn.setOnClickListener(USBBtnClickListener);
        mUSBtrackNextBtn = (ImageButton) mMusicUSBLY.findViewById(R.id.trackNextBtn);
        mUSBtrackNextBtn.setOnClickListener(USBBtnClickListener);
        mUSBtrackNextBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //FF 77 0B 03 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x03, (byte)0x00,
                        (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);

                mUSBPreNextShortClickEnable = true;
                mUSBPreNextLongShortClickEnable = false;

                /*byte[] blueOrder4 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x06
                        , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                BluetoothDemo.sendBlueOrder(blueOrder4);*/

                return false;
            }
        });

        /***初始化SD页面*****/
        mMusicSDLY = (FrameLayout) findViewById(R.id.include_sd_ly);
        mSDTopBarTitle = (TextView) mMusicSDLY.findViewById(R.id.topBarTitle);
        toSDShowTrackListBtn = (TextView) mMusicSDLY.findViewById(R.id.toShowTrackListBtn);
        mSDAlbumText = (TextView) mMusicSDLY.findViewById(R.id.albumText);
        mSDAuthorText = (TextView) mMusicSDLY.findViewById(R.id.authorText);
        mSDalbumText1 = (TextView) mMusicSDLY.findViewById(R.id.albumText1);
        mSDalbumText2 = (TextView) mMusicSDLY.findViewById(R.id.albumText2);
        mSDAlbumImageView = (ImageView) mMusicSDLY.findViewById(R.id.albumImageView);
        mSDXunhuanPlay = (ImageView) mMusicSDLY.findViewById(R.id.xunhuanPlay);
        mSDXunhuanPlay.setOnClickListener(SDBtnClickListener);
        mSDsuijiPlay = (ImageView) mMusicSDLY.findViewById(R.id.suijiPlay);
        mSDsuijiPlay.setOnClickListener(SDBtnClickListener);
        mSDsuijiPlay.setVisibility(View.INVISIBLE);
        mSDTrackPlayTimeText = (TextView) mMusicSDLY.findViewById(R.id.trackPlayTimeText);
        mSDtrackTotalTimeText = (TextView) mMusicSDLY.findViewById(R.id.trackTotalTimeText);
        mSDtrackSeekBar = (SeekBar) mMusicSDLY.findViewById(R.id.trackSDSeekBar);
        mSDtrackSeekBar.setVisibility(View.VISIBLE);
        mSDtrackSeekBar.setOnSeekBarChangeListener(this);
        mSDtrackPreBtn = (ImageButton) mMusicSDLY.findViewById(R.id.trackPreBtn);
        mSDtrackPreBtn.setOnClickListener(SDBtnClickListener);
        mSDtrackPreBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //FF 77 0B 04 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x04, (byte)0x00, (byte)0x00
                        , (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);

                mSDPreNextShortClickEnable = true;
                mSDPreNextLongShortClickEnable = false;

                /*byte[] blueOrder1 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x05
                        , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                BluetoothDemo.sendBlueOrder(blueOrder1);*/

                return false;
            }
        });

        mSDplayandstopBtn = (ImageButton) mMusicSDLY.findViewById(R.id.playandstopBtn);
        mSDplayandstopBtn.setOnClickListener(SDBtnClickListener);
        mSDtrackNextBtn = (ImageButton) mMusicSDLY.findViewById(R.id.trackNextBtn);
        mSDtrackNextBtn.setOnClickListener(SDBtnClickListener);
        mSDtrackNextBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //FF 77 0B 03 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x03, (byte)0x00,
                        (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);

                mSDPreNextShortClickEnable = true;
                mSDPreNextLongShortClickEnable = false;

                /*byte[] blueOrder4 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x06
                        , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                BluetoothDemo.sendBlueOrder(blueOrder4);*/

                return false;
            }
        });
    }

    private void initA2DPLY222() {
        mA2DP2LY = (LinearLayout) findViewById(R.id.include_a2dp_ly);
        mA2DP2LY.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    if(ppp.appname.contains("音乐") || ppp.appname.toUpperCase().contains("MUSIC")
                            || ppp.appname.toUpperCase().contains("WALK") ) {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                        if(xintent != null) {
                            realExit = false;
                            startActivity(xintent);
                            break;
                        }
                        else {
                            continue;
                        }

                    }
                }
            }
        });
    }

    private void initA2DPLY() {
        //mA2DPLY = (FrameLayout) findViewById(R.id.include_a2dp_ly);
        if (mA2DPLY== null){
            return;
        }
        ListView list = (ListView) mA2DPLY.findViewById(R.id.MyListView);
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        /*for(int i=0;i<kaijuanyouyi.size();i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "This is Title.....");
            map.put("ItemText", "This is text.....");
            mylist.add(map);
        }*/

        Iterator iter = kaijuanyouyi.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            PInfo temporaryPInfo = (PInfo) val;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("img", temporaryPInfo.icon);
            map.put("ItemTitle", (String)key);
            mylist.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(this,
                mylist,
                R.layout.a2dp_listitem,
                new String[] {"img", "ItemTitle"},
                new int[] {R.id.img, R.id.ItemTitle});
        list.setAdapter(mSchedule);
        mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view,Object data,String textRepresentation) {
                if(view instanceof ImageView && data instanceof Drawable){
                    ImageView iv = (ImageView)view;
                    iv.setImageDrawable((Drawable)data);
                    return true;
                }
                else return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView)parent;
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String itemTitlex = map.get("ItemTitle");
                //Toast.makeText(galleryActivity, itemTitlex, Toast.LENGTH_LONG).show();
                PInfo pinfox = kaijuanyouyi.get(itemTitlex);
                xintent = xpackageMan.getLaunchIntentForPackage(pinfox.pname);
                if(xintent != null) {
                    realExit = false;
                    startActivity(xintent);
                }
            }
        });
    }

    private void listPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);

            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }

            //filter the system software
            ApplicationInfo itemInfo = p.applicationInfo;
            if(((itemInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ) && (!p.packageName.equals("com.google.android.apps.maps"))
            && (!p.packageName.equals("com.sec.android.app.music"))
            && (!p.packageName.equals("com.google.android.googlequicksearchbox"))
            && (!p.packageName.equals("com.sec.android.app.videoplayer"))
            && (!p.packageName.equals("com.sonyericsson.music"))
            && (!p.packageName.equals("com.sonyericsson.video"))
            || (p.packageName.equals("com.devin.BluetoothRadio"))
            ) {
                continue;
            }

            //Log.e("gallery", " ===============================> " + p.packageName);

            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());

            //gallery----
            res.add(newInfo);
            //kaijuanyouyi.put(newInfo.appname, newInfo);
            gongxifacai.put(newInfo.appname, newInfo);
            if(
            (newInfo.appname.toUpperCase().contains("MUSIC")) ||
            (newInfo.appname.contains("音乐") && (!newInfo.appname.contains("壁纸")) ) ||
            (newInfo.appname.contains("拨号")) ||
            (newInfo.appname.toUpperCase().contains("MAP")) ||
            (newInfo.appname.contains("地图")) ||
            (newInfo.pname.contains("com.google.android.googlequicksearchbox")) ||
            (newInfo.pname.contains("com.google.android.apps.maps")) ||
            (newInfo.pname.contains("com.sec.android.app.videoplayer")) ||
            (newInfo.pname.contains("com.sec.android.app.music")) ||
            (newInfo.pname.contains("com.sonyericsson.music")) ||
            (newInfo.pname.contains("com.sonyericsson.video"))
            ) {
                Log.e("gallery", "Find: " + newInfo.appname.trim());
                aimPackageName = p.packageName;
                //break;
                kaijuanyouyi.put(newInfo.appname, newInfo);
            }
        }
        return res;
    }

    private ArrayList<String> getInstalledAppsString(boolean getSysPackages) {

        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        ArrayList<String> res = new ArrayList<String>() ;
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }

            ApplicationInfo itemInfo = p.applicationInfo;
            if(((itemInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ) && (!p.packageName.equals("com.google.android.apps.maps"))
                    && (!p.packageName.equals("com.sec.android.app.music"))
                    && (!p.packageName.equals("com.google.android.googlequicksearchbox"))
                    && (!p.packageName.equals("com.sec.android.app.videoplayer"))
                    && (!p.packageName.equals("com.sonyericsson.music"))
                    && (!p.packageName.equals("com.sonyericsson.video"))
                    || (p.packageName.equals("com.devin.BluetoothRadio"))
                    ) {
                continue;
            }

            String newInfo = new String();
            newInfo = p.applicationInfo.loadLabel(getPackageManager()).toString();
            if (newInfo.equalsIgnoreCase("")) {
                Log.e("donghui","newInfo.equalsIgnoreCase(\"\")");
                continue;
            }
            if (newInfo == null) {
                Log.e("donghui","newInfo == null");
                continue;
            }
            res.add(newInfo);
        }
        return res;
    }

    private HashMap<String,String> getInstalledAppsHashmap(boolean getSysPackages) {

        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        HashMap<String,String> res = new LinkedHashMap<String, String>();
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }

            //filter the system software
            ApplicationInfo itemInfo = p.applicationInfo;
            if(((itemInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ) && (!p.packageName.equals("com.google.android.apps.maps"))
                    && (!p.packageName.equals("com.sec.android.app.music"))
                    && (!p.packageName.equals("com.google.android.googlequicksearchbox"))
                    && (!p.packageName.equals("com.sec.android.app.videoplayer"))
                    && (!p.packageName.equals("com.sonyericsson.music"))
                    && (!p.packageName.equals("com.sonyericsson.video"))
                    || (p.packageName.equals("com.devin.BluetoothRadio"))
                    ) {
                continue;
            }

            res.put(p.applicationInfo.loadLabel(getPackageManager()).toString(),p.packageName);
        }
        return res;
    }


    private HashMap<String,Drawable> getInstalledAppsIconHashmap(boolean getSysPackages) {

        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        HashMap<String,Drawable> res = new LinkedHashMap<String, Drawable>();
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }

            //filter the system software
            ApplicationInfo itemInfo = p.applicationInfo;
            if(((itemInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ) && (!p.packageName.equals("com.google.android.apps.maps"))
                    && (!p.packageName.equals("com.sec.android.app.music"))
                    && (!p.packageName.equals("com.google.android.googlequicksearchbox"))
                    && (!p.packageName.equals("com.sec.android.app.videoplayer"))
                    && (!p.packageName.equals("com.sonyericsson.music"))
                    && (!p.packageName.equals("com.sonyericsson.video"))
                    || (p.packageName.equals("com.devin.BluetoothRadio"))
                    ) {
                continue;
            }

            res.put(p.packageName,p.applicationInfo.loadIcon(getPackageManager()));
        }
        return res;
    }

    View.OnClickListener USBBtnClickListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int index = v.getId();
             switch (index) {
                 case R.id.xunhuanPlay:
                     if(mUSBXunhuanPlayMode == 3) {
                         mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_n);
                         mUSBXunhuanPlayMode = 0;
                         //FF770B080200000D0A
                         byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x00
                                 , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                         BluetoothDemo.sendBlueOrder(blueOrder);
                         mUSBsuijiPlay.setSelected(false);
                     }
                     else if(mUSBXunhuanPlayMode == 0) {
                         mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_folder_n);
                         mUSBXunhuanPlayMode = 1;
                         //FF770B080000000D0A
                         byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x01
                                 , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                         BluetoothDemo.sendBlueOrder(blueOrder);
                         mUSBsuijiPlay.setSelected(false);
                     }
                     else if(mUSBXunhuanPlayMode == 1) {
                         mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_one_n);
                         mUSBXunhuanPlayMode = 2;
                         //FF770B080300000D0A
                         byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x02
                                 , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                         BluetoothDemo.sendBlueOrder(blueOrder);
                         mUSBsuijiPlay.setSelected(false);
                     }
                     else if(mUSBXunhuanPlayMode == 2) {
                         mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);
                         mUSBXunhuanPlayMode = 3;
                         //FF770B080300000D0A
                         byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x03
                                 , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                         BluetoothDemo.sendBlueOrder(blueOrder);
                     }
                     break;
                 case R.id.suijiPlay:
                     if (mUSBsuijiPlay.isSelected()){
                         mUSBsuijiPlay.setSelected(false);
                         Log.e("gallery", "mUSBsuijiPlay.setSelected(false)");
                     }else {
                         mUSBsuijiPlay.setSelected(true);
                         //mUSBXunhuanPlay.setSelected(false);
                         mUSBXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);
                         Log.e("gallery", "mUSBsuijiPlay.setSelected(true)");
                         //FF 77 0B 07 04 00 00 0D 0A
                         byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x07
                                 , (byte)0x04, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                         BluetoothDemo.sendBlueOrder(blueOrder);
                     }
                     break;
                 case R.id.trackPreBtn:
                     if(mUSBPreNextShortClickEnable) {
                         /*if(mUSBPreShortClic1kShowProgressFlag)
                            startProgressBar();*/
                         byte[] blueOrder1 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x05
                                 , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                         BluetoothDemo.sendBlueOrder(blueOrder1);
                         if(mUSBPreNextLongShortClickEnable) {
                            mUSBPreNextShortClickEnable = false;
                         }
                         mUSBPreNextLongShortClickEnable = true;
                     }
                     break;
                 case R.id.playandstopBtn:
                     if (mUSBplayandstopBtn.isSelected()){
                         mUSBplayandstopBtn.setSelected(false);
                         //FF 77 0B 00 00 00 00 0D 0A
                         byte[] blueOrder2 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00
                                 , (byte)0x00, (byte)0x0D, (byte)0x0A };
                         BluetoothDemo.sendBlueOrder(blueOrder2);
                     }else {
                         mUSBplayandstopBtn.setSelected(true);
                         //FF 77 0B 01 00 00 00 0D 0A
                         byte[] blueOrder3 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x01
                                 , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                         BluetoothDemo.sendBlueOrder(blueOrder3);
                     }
                     break;
                 case R.id.trackNextBtn:
                     if(mUSBPreNextShortClickEnable) {
                         /*if(mUSBPreShortClic1kShowProgressFlag)
                            startProgressBar();*/
                         byte[] blueOrder4 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x06
                                 , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                         BluetoothDemo.sendBlueOrder(blueOrder4);
                         if(mUSBPreNextLongShortClickEnable) {
                             mUSBPreNextShortClickEnable = false;
                         }
                         mUSBPreNextLongShortClickEnable = true;
                     }
                     break;
             }
         }
     };

    View.OnClickListener SDBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = v.getId();
            switch (index) {
                case R.id.xunhuanPlay:
                    if(mSDXunhuanPlayMode == 3) {
                        mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_n);
                        mSDXunhuanPlayMode = 0;
                        //FF770B080200000D0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x00
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        mSDsuijiPlay.setSelected(false);
                    }
                    else if(mSDXunhuanPlayMode == 0) {
                        mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_folder_n);
                        mSDXunhuanPlayMode = 1;
                        //FF770B080000000D0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x01
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        mSDsuijiPlay.setSelected(false);
                    }
                    else if(mSDXunhuanPlayMode == 1) {
                        mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_one_n);
                        mSDXunhuanPlayMode = 2;
                        //FF770B080300000D0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x02
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                        mSDsuijiPlay.setSelected(false);
                    }
                    else if(mSDXunhuanPlayMode == 2) {
                        mSDXunhuanPlay.setBackgroundResource(R.drawable.cd_play_mode_repeat_d);
                        mSDXunhuanPlayMode = 3;
                        //FF770B080300000D0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x08, (byte)0x03
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                    }
                    break;
                case R.id.suijiPlay:
                    if (mSDsuijiPlay.isSelected()){
                        mSDsuijiPlay.setSelected(false);

                    }else {
                        mSDsuijiPlay.setSelected(true);
                        mSDXunhuanPlay.setSelected(false);

                    }
                    break;
                case R.id.trackPreBtn:
                    if(mSDPreNextShortClickEnable) {
                        byte[] blueOrder1 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x05
                                , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder1);
                        if(mSDPreNextLongShortClickEnable) {
                            mSDPreNextShortClickEnable = false;
                            startTimerWaitForNextMusic();
                        }
                        mSDPreNextLongShortClickEnable = true;
                    }
                    break;
                case R.id.playandstopBtn:
                    if (mSDplayandstopBtn.isSelected()){
                        mSDplayandstopBtn.setSelected(false);
                        //FF 77 0B 00 00 00 00 0D 0A
                        byte[] blueOrder2 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x00, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder2);
                    }else {
                        mSDplayandstopBtn.setSelected(true);
                        //FF 77 0B 01 00 00 00 0D 0A
                        byte[] blueOrder3 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x01
                                , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder3);
                    }
                    break;
                case R.id.trackNextBtn:
                    if(mSDPreNextShortClickEnable) {
                        byte[] blueOrder4 = { (byte)0xFF, (byte)0x77, (byte)0x0B, (byte)0x06
                                , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder4);
                        if(mSDPreNextLongShortClickEnable) {
                            mSDPreNextShortClickEnable = false;
                            startTimerWaitForNextMusic();
                        }
                        mSDPreNextLongShortClickEnable = true;
                    }
                    break;
            }
        }
    };
    View.OnClickListener ChannelChangedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = v.getId();
            switch (index) {
                case R.id.preChannelButton:
                    mChannelSeekBarTouchFlag = true;
                    double barvalue1 = mChannelSeekBar.getProgress();
                    if(barvalue1 > 0) {
                        int currentValue1 = (int) (barvalue1 - 1);
                        mChannelSeekBar.setProgress(currentValue1);
                        Log.e("gallery", "current progress: " + mChannelSeekBar.getProgress());
                    }
                    else if(barvalue1 == 0) {
                        int currentValue1 = mChannelSeekBar.getMax();
                        mChannelSeekBar.setProgress(currentValue1);
                        Log.e("gallery", "current progress: " + mChannelSeekBar.getProgress());
                    }
                    //FF7706020000000D0A
                    /*byte[] blueOrderUp = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00
                            , (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrderUp);*/
                    break;
                case R.id.nextChannelButton:
                    mChannelSeekBarTouchFlag = true;
                    double barvalue2 = mChannelSeekBar.getProgress();
                    if(barvalue2 < mChannelSeekBar.getMax()) {
                        int currentValue = (int) (barvalue2 + 1);
                        mChannelSeekBar.setProgress(currentValue);
                        Log.e("gallery", "current progress: " + mChannelSeekBar.getProgress());
                    }
                    else if(barvalue2 == mChannelSeekBar.getMax()) {
                        int currentValue = 0;
                        mChannelSeekBar.setProgress(currentValue);
                        Log.e("gallery", "current progress: " + mChannelSeekBar.getProgress());
                    }
                    //FF7706030000000D0A
                    /*byte[] blueOrderDown = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x03, (byte)0x00, (byte)0x00, (byte)0x00
                            , (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrderDown);*/
                    break;

                case R.id.tunerIntroPlayBtn:
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x05
                            , (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                    break;
            }

        }
    };

    View.OnClickListener VolumeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = v.getId();
            switch (index) {
                case R.id.volumeDownBtn: {
                    if(cSpinner.getSelectedItemPosition() > 0) {
                        byte[] targetOrder = {(byte) 0xFF, (byte) 0x77, (byte) 0x03, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x0A};
                        BluetoothDemo.sendBlueOrder(targetOrder);

                        cSpinner.setSelection(cSpinner.getSelectedItemPosition()-1);
                    }
                    break;
                }

                case R.id.volumeUpBtn: {
                    if(cSpinner.getSelectedItemPosition() < 40) {
                        //FF7703010100000D0A
                        byte[] targetOrder = {(byte) 0xFF, (byte) 0x77, (byte) 0x03, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x0A};
                        BluetoothDemo.sendBlueOrder(targetOrder);

                        cSpinner.setSelection(cSpinner.getSelectedItemPosition()+1);
                    }
                    break;
                }

                case R.id.powerBtn: {
                    Log.e("gallery", "click R.id.volumeUpBtn");

                    AlertDialog mExitAlertDialog = new AlertDialog.Builder(mContext).setTitle(R.string.exit).setMessage("Sure to Exit App ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //try {
                                        //BluetoothDemo.connectFlag = true;
                                        if(usbOrSD) {
                                            MathMethod.writeSharedPreferences("usbORsd", "USB");
                                        }
                                        else {
                                            MathMethod.writeSharedPreferences("usbORsd", "SD");
                                        }

                                        if(!BluetoothDemo.connectFlag) {
                                            BluetoothDemo.mContext.finish();
                                            galleryActivity.finish();
                                            System.exit(0);
                                        }
                                        else {
                                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x7E, (byte)0x00, (byte)0x00, (byte)0x00,
                                                    (byte)0x00, (byte)0x0D, (byte)0x0A};
                                            BluetoothDemo.sendBlueOrder(blueOrder);
                                        }

                                        BluetoothDemo.receiveFlag = false;

                                        /*if (BluetoothDemo.outStream != null) {
                                            BluetoothDemo.outStream.close();
                                        }
                                        if (BluetoothDemo.inputStream != null) {
                                            BluetoothDemo.inputStream.close();
                                        }
                                        if (BluetoothDemo.btSocket != null) {
                                            BluetoothDemo.btSocket.close();
                                        }

                                    } catch (IOException e) {

                                    }

                                    finish();*/
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            }).create();
                    mExitAlertDialog.show();

                    break;
                }

                case R.id.settingBtn: {
                    Log.e("gallery", "click R.id.settingBtn");
                    //FF7705000000000D0A
                    byte[] targetOrder = {(byte)0xFF, (byte)0x77, (byte)0x05, (byte)0x06, (byte)0x01, (byte)0x00,
                            (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(targetOrder);

                    BluetoothDemo.receiveCount = 0;
                    Message msg = new Message();
                    msg.what = 10;
                    Activity02.handlerRadioInterface.sendMessage(msg);
                    Intent intent = new Intent(Activity02.this, SettingActivity.class);
                    realExit = false;
					startActivityForResult(intent, 111);
                    break;
                }
            }
        }
    };

    View.OnClickListener TunerScanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = v.getId();
            switch (index) {
                case R.id.tunerScanDownBtn: {
                    //FF7706010000000D0A
                    tunerScanBtnFlag = false;
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                    break;
                }

                case R.id.tunerScanUpBtn: {
                    //FF7706000000000D0A
                    tunerScanBtnFlag = true;
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                    break;
                }
            }
        }
    };

    @Override
    public void onTabChanged(String tabId) {
        Log.e("gallery", "tabId =============================> " + tabId);
        if(tabHostTouchFlag) {
          /*FF 77 04 00 00 00 00 00 0D 0A  //RADIO
            FF 77 04 01 00 00 00 00 0D 0A  //CD
            FF 77 04 02 00 00 00 00 0D 0A  //USB*/
            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x04, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
                    , (byte)0x00, (byte)0x0D, (byte)0x0A};
            if(tabId.trim().equalsIgnoreCase("tab1")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();
            }
            else if(tabId.trim().equalsIgnoreCase("tab2")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x02;
                BluetoothDemo.sendBlueOrder(blueOrder);
                MathMethod.writeSharedPreferences("usbORsd", "USB");
                usbOrSD = true;

                //gallery++ 10/16
                startTimerWait();
            }
            else if(tabId.trim().equalsIgnoreCase("tab3")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x03;
                BluetoothDemo.sendBlueOrder(blueOrder);
                MathMethod.writeSharedPreferences("usbORsd", "SD");
                usbOrSD = false;

                //gallery++ 10/16
                startTimerWait();
            }
            else if(tabId.trim().equalsIgnoreCase("tab4")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                //FF 77 04 07 00 00 00 0D 0A
                blueOrder[3] = (byte)0x07;
                BluetoothDemo.sendBlueOrder(blueOrder);
                realExit = false;
                /*PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    if(appName.contains("拨号")) {
                        break;
                    }
                }
                Log.e("gallery", "phone === " + ppp.pname);
                Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                if(xintent != null)
                    startActivity(xintent);*/

                //Intent intent= new Intent(Intent.ACTION_DIAL);
                //intent.setClassName("com.android.contacts","com.android.contacts.DialtactsActivity");
                //intent.setData(Uri.parse("123456"));

                //gallery++ 10/16
                startTimerWait();

                Uri telUri = Uri.parse("tel: ");
                Intent returnIt = new Intent(Intent.ACTION_DIAL, telUri);
                startActivity(returnIt);

            }
            else if(tabId.trim().equalsIgnoreCase("tab5")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x08;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                boolean temporaryMapFlag = true;
                PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    //if(appName.contains("地图") || appName.toUpperCase().contains("MAP")) {
                    Log.e("gallery", "MAP1 appname pname === " + ppp.appname + " === " + ppp.pname);
                    if(ppp.pname.equals("com.google.android.apps.maps")) {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                        if(xintent != null) {
                            realExit = false;
                            temporaryMapFlag = false;
                            startActivity(xintent);
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                    //}
                }
                if(temporaryMapFlag) {
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Object key = entry.getKey();
                        Object val = entry.getValue();
                        String appName = (String) key;
                        ppp = (PInfo) val;
                        if(appName.contains("地图") || appName.toUpperCase().contains("MAP")) {
                            Log.e("gallery", "MAP appname pname === " + ppp.appname + " === " + ppp.pname);
                            Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                            if(xintent != null) {
                                realExit = false;
                                startActivity(xintent);
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            else if(tabId.trim().equalsIgnoreCase("tab6")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                //FF7704090000000D0D0A
                //blueOrder[3] = (byte)0x09;
                //BluetoothDemo.sendBlueOrder(blueOrder);

                PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    if(ppp.pname.contains("com.google.android.googlequicksearchbox")) {
                        break;
                    }
                }
                Log.e("gallery", "voicesearch === " + ppp.pname);
                if(ppp.pname.contains("com.google.android.googlequicksearchbox")) {
                    blueOrder[3] = (byte)0x09;
                    BluetoothDemo.sendBlueOrder(blueOrder);

                    //gallery++ 10/16
                    startTimerWait();

                    Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                    if(xintent != null) {
                        realExit = false;
                        startActivity(xintent);
                    }
                }
                else {
                    Toast.makeText(galleryActivity, "Not find Voice Search", 1).show();
                }
            }
            else if(tabId.trim().equalsIgnoreCase("tab7")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x04;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();
            }
            else if(tabId.trim().equalsIgnoreCase("tab8")) {

                //gallery++ 10/16
                tabHostTouchFlag = false;

                //blueOrder[3] = (byte)0x06;

                blueOrder[3] = (byte)0x05;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.intent.action.MUSIC_PLAYER");
                startActivity(intent);

                /* GOOD
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //这里我们先从SDCard文件中获取指定文件的URi
                //File sdcard = Environment.getExternalStorageDirectory(); //这个是获取SDCard路径
                File audioFile = new File("sdcard/music/tt.mp3");
                //然后需要获取该文件的Uri
                Uri audioUri = Uri.fromFile(audioFile);
                //然后指定Uri和MIME
                intent.setDataAndType(audioUri, "audio/mp3");
                startActivity(intent);*/

/*
                //gallery++ 10/16
                tabHostTouchFlag = false;

                //blueOrder[3] = (byte)0x06;

                blueOrder[3] = (byte)0x05;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                PInfo ppp = null;
                Iterator iter = kaijuanyouyi.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    String appName = (String) key;
                    ppp = (PInfo) val;
                    if(ppp.appname.contains("音乐") || ppp.appname.toUpperCase().contains("MUSIC")
                            || ppp.appname.toUpperCase().contains("WALK")) {
                        Intent xintent = xpackageMan.getLaunchIntentForPackage(ppp.pname);
                        if(xintent != null) {
                            realExit = false;
                            startActivity(xintent);
                            break;
                        }
                        else {
                            continue;
                        }

                    }
                }*/
            } else if(tabId.trim().equalsIgnoreCase("tab9")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x0A;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                String appname =  getTab9AppName();
                if (appname.equalsIgnoreCase("")){
                    showAppSelectDiaglog("tab9");
                }   else {
                    Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                    if(xintent != null) {
                        realExit = false;
                        startActivity(xintent);
                    }else {
                        Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                        Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else if(tabId.trim().equalsIgnoreCase("tab10")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x0B;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                String appname =  getTab10AppName();
                if (appname.equalsIgnoreCase("")){
                    showAppSelectDiaglog("tab10");
                }   else {
                    Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                    if(xintent != null) {
                        realExit = false;
                        startActivity(xintent);
                    } else {
                        Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else if(tabId.trim().equalsIgnoreCase("tab11")) {
                //gallery++ 10/16
                tabHostTouchFlag = false;

                blueOrder[3] = (byte)0x0C;
                BluetoothDemo.sendBlueOrder(blueOrder);

                //gallery++ 10/16
                startTimerWait();

                String appname = getTab11AppName();
                if (appname.equalsIgnoreCase("")){
                    showAppSelectDiaglog("tab11");
                }   else {
                    Intent xintent = xpackageMan.getLaunchIntentForPackage(appname);
                    if(xintent != null) {
                        realExit = false;
                        startActivity(xintent);
                    } else {
                        Toast.makeText(Activity02.this, "Clicked app can not be lunched ,Please replace",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    }
    /**
     * listview中点击按键弹出对话框
     */
    public final class ViewHolder {
        public ImageView img;
        public TextView text;
    }
    class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }
        public int getCount() {
            return mylist.size();
        }
        public Object getItem(int arg0) {
            return mylist.get(arg0);
        }
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.app_listitem, null);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.text = (TextView) convertView.findViewById(R.id.ItemTitle);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText((String) mylist.get(position).get("ItemTitle"));
            holder.img.setImageDrawable( (Drawable)mylist.get(position).get("img"));

            if (position == selectItem) {
                convertView.setBackgroundColor(R.color.solid_yellow);
            }
            else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

//            convertView.getBackground().setAlpha(80);

            return convertView;
        }
        public  void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }
        private int  selectItem=-1;
    }

    void showAppSelectDiaglog(final String tabId) {
//        Log.e("donghui","showAppSelectDiaglog");
//        final ArrayList<String> appKey =  getInstalledAppsString(false);
//        String[] items = new String[appKey.size()];
//        items = appKey.toArray(items);
//        final String[] finalItems = items;
//        new AlertDialog.Builder(Activity02.this)
//                .setIconAttribute(android.R.attr.alertDialogIcon)
//                .setTitle(R.string.alert_dialog_single_choice)
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (tabId.trim().equalsIgnoreCase("tab9")) {
//                            setTab9APPName(mInstalledAppsHashmap.get(finalItems[which]));
//                            initAddAppNameView();
//                            inittab9LY();
//                        } else if (tabId.trim().equalsIgnoreCase("tab10")) {
//                            setTab10APPName(mInstalledAppsHashmap.get(finalItems[which]));
//                            initAddAppNameView();
//                            inittab10LY();
//                        } else if (tabId.trim().equalsIgnoreCase("tab11")) {
//                            setTab11APPName(mInstalledAppsHashmap.get(finalItems[which]));
//                            initAddAppNameView();
//                            inittab11LY();
//                        }
//
//                        initAddAppIconView();
////                        new AlertDialog.Builder(Activity02.this)
////                                .setMessage("You selected: " + which + " , " + items[which])
////                                .show();
//                    }
//                })
//
//                .create().show();
        apppbuilder = new AlertDialog.Builder(Activity02.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        mAppListView = (ListView) inflater.inflate(R.layout.app_listview_ly,null);
        mylist = new ArrayList<HashMap<String, Object>>();

        Iterator iter = gongxifacai.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            PInfo temporaryPInfo = (PInfo) val;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("img", temporaryPInfo.icon);
            map.put("ItemTitle", (String)key);
            mylist.add(map);
        }
        mAppAadapter = new MyAdapter(this);
        mAppListView.setAdapter(mAppAadapter);
        mAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("donghui", "你好");
                ListView listView = (ListView) parent;
//                for(int i=0;i<parent.getCount()-1;i++){
//                    View v=parent.getChildAt(i);
//                    if (position == i) {
//                        v.setBackgroundColor(Color.RED);
//                    } else {
//                        v.setBackgroundColor(Color.TRANSPARENT);
//                    }
//                }
                mAppAadapter.setSelectItem(position);
//                System.out.println("-------------------->"+arg2);
                mAppAadapter.notifyDataSetInvalidated();
                //   adapter.notifyDataSetChanged();


                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String itemTitlex = map.get("ItemTitle");
                //Toast.makeText(galleryActivity, itemTitlex, Toast.LENGTH_LONG).show();
                pinfox = gongxifacai.get(itemTitlex);

            }
        });

        apppbuilder.setView(mAppListView)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (tabId.trim().equalsIgnoreCase("tab9")) {
                            if (pinfox!=null&&!pinfox.pname.equalsIgnoreCase("")){
                                setTab9APPName(pinfox.pname);
                                initAddAppNameView();
                                inittab9LY();
                                if (pinfox!=null){
                                    pinfox =null;
                                }
                            } else {
                                Toast.makeText(Activity02.this, "Please select one application", Toast.LENGTH_SHORT).show();
                            }

                        } else if (tabId.trim().equalsIgnoreCase("tab10")) {
                            if (pinfox!=null&&!pinfox.pname.equalsIgnoreCase("")){
                            setTab10APPName(pinfox.pname);
                            initAddAppNameView();
                            inittab10LY();
                                if (pinfox!=null){
                                    pinfox =null;
                                }
                            }
                            else {
                                Toast.makeText(Activity02.this, "Please select one application", Toast.LENGTH_SHORT).show();
                            }
                        } else if (tabId.trim().equalsIgnoreCase("tab11")) {
                            if (pinfox!=null&&!pinfox.pname.equalsIgnoreCase("")){
                            setTab11APPName(pinfox.pname);
                            initAddAppNameView();
                            inittab11LY();
                                if (pinfox!=null){
                                    pinfox =null;
                                }
                            } else {
                                Toast.makeText(Activity02.this, "Please select one application", Toast.LENGTH_SHORT).show();
                            }
                        }

                        initAddAppIconView();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (pinfox!=null){
                            pinfox =null;
                        }

                    }
                });
        apppbuilder.create().show();
    }

    private void setAMProgress(double i) {
        //100/108
/*        if (mTunerValue != 0) {
            double barvalue = mTunerValue * 0.92;
//            int j = FrequencyRangeUtil.GetAMProgress(amregioninfo, i);
            mChannelSeekBar.setProgress((int) barvalue);
//            mChannelSeekBar.setProgressDrawable(getResources().getDrawable(amregioninfo.getResId()));
            mChannelSeekBar.setThumb(getResources().getDrawable(R.drawable.tuner_freq_bar_point));
        }*/

        mChannelSeekBar.setMax(41);
        mFMArrayIndex = 0;
        maxFMvalue = 108000;
        minFMvalue = 87500;
        mFmStepValue = 500;
        if (mTunerValue != 0) {
            double barvalue = mTunerValue;
            mChannelSeekBar.setProgress((int) barvalue);
            mChannelSeekBar.setThumb(getResources().getDrawable(R.drawable.tuner_freq_bar_point));
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e("donghui","seekBar ID   "+seekBar.getId());
        int index = seekBar.getId();
        switch (index) {
            case R.id.channelSeekBar:
                handleRaioSeekBar(progress);
                Log.e("donghui","选中了声音机的seekbar");

                break;
            case R.id.trackUSBSeekBar:
                Log.e("donghui","选中了USB的seekbar") ;
                handleUSBSeekBar(progress);
                break;
            case R.id.trackSDSeekBar:
                Log.e("donghui","选中了SD的seekbar") ;
                handleSDSeekBar(progress);
                break;
        }
    }

    private void handleRaioSeekBar(int progress) {
        if (mFMValue != null) {
            Log.e("donghui", "progress " + progress);
            //double value = 87.5 + (progress * 5) / 10.0;
            double value = minFMvalue / 1000.0 + (progress * mFmStepValue) / 1000.0;
            String textView = "";
            textView = String.valueOf(value);
            if(mFmStepValue < 100) {
                String[] textViewSS = textView.split("\\.");
                if(textViewSS.length > 1) {
                    if(textViewSS[1].length() == 1) {
                        textView = textView + "0";
                    }
                }
            }
            mFMValue.setText(textView);

            int hzValue = (int) (value * 1000);
            String hexStr = Integer.toHexString(hzValue);
            String firstHex = hexStr.substring(1, 3);
            String secondHex = hexStr.substring(3, 5);
            byte hzByteHex1 = MathMethod.hexStr2Bytes(firstHex);
            byte hzByteHex2 = MathMethod.hexStr2Bytes(secondHex);

            if(mChannelSeekBarTouchFlag) {
                //FF770880XXXX000D0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x08, (byte)0x80, hzByteHex1,
                        hzByteHex2, (byte)0x00, (byte)0x0D, (byte)0x0A};

                BluetoothDemo.sendBlueOrder(blueOrder);
            }
        }
    }

    public void handleUSBSeekBar(int progress) {

    }

    public void handleSDSeekBar(int progress) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }



    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (arg1==null){
                return;
            }

            TextView v1 = (TextView)arg1;
            v1.setTextColor(Color.WHITE); //可以随意设置自己要的颜色值
            v1.setTextSize(20);

            Log.e("gallery", volumeArray[arg2]);

            if(cSpinnerTouchFlag) {
                //FF77030100xx000D0A
                String volumeStrHex = Integer.toHexString(Integer.parseInt(volumeArray[arg2]));
                //byte volumeByteHex = Byte.parseByte(volumeStrHex, 16);
                byte volumeByteHex = MathMethod.HexStringToBytes(volumeStrHex);
                byte[] targetOrder = {(byte)0xFF, (byte)0x77, (byte)0x03, (byte)0x01, (byte)0x00, (byte)volumeByteHex,
                        (byte)0x00, (byte)0x0D, (byte)0x0A};

                BluetoothDemo.sendBlueOrder(targetOrder);
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void showToInputDialog() {
        // 弹出对话框
        if (mAlertOption == null) {
            mAlertOption = new AlertDialog.Builder(mContext)
                    .setPositiveButton("Set",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    //TODO 频道选择处理
                                    String FMvalueStr = mChannelTextView.getText().toString().trim();
                                    try {
                                        Double FMvalueDouble = Double.parseDouble(FMvalueStr);
                                        int FMvalueInt = (int) (FMvalueDouble * 1000.0);
                                        if((FMvalueInt>=minFMvalue) && (FMvalueInt<=maxFMvalue)) {
                                            String hexStr = Integer.toHexString(FMvalueInt);
                                            String firstHex = hexStr.substring(1, 3);
                                            String secondHex = hexStr.substring(3, 5);
                                            byte hzByteHex1 = MathMethod.hexStr2Bytes(firstHex);
                                            byte hzByteHex2 = MathMethod.hexStr2Bytes(secondHex);

                                            //FF770880XXXX000D0A
                                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x08, (byte)0x80, hzByteHex1,
                                                    hzByteHex2, (byte)0x00, (byte)0x0D, (byte)0x0A};
                                            BluetoothDemo.sendBlueOrder(blueOrder);

                                            int currentValue = (FMvalueInt-minFMvalue)/mFmStepValue;
                                            mChannelSeekBar.setProgress(currentValue);
                                        }
                                        else {
                                            Toast.makeText(galleryActivity, "频率范围: " + (minFMvalue/1000.0) + " ---> "
                                                    + (maxFMvalue/1000.0), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        mChannelTextView.setText("");
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).create();
        }
        else {
            mChannelTextView.setText("");
        }

        mAlertOption.setView(mToInputLY);

        if (!((Activity) mContext).isFinishing() && mAlertOption.isShowing()) {
            mAlertOption.dismiss();
        }
        if (!((Activity) mContext).isFinishing()) {
            mAlertOption.show();
        }
    }

    private View.OnClickListener ToInputLisenter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = v.getId();
            StringBuffer num = new StringBuffer();
            num.append("");
            switch (index) {
                case R.id.btn1:
                case R.id.btn2:
                case R.id.btn3:
                case R.id.btn4:
                case R.id.btn5:
                case R.id.btn6:
                case R.id.btn7:
                case R.id.btn8:
                case R.id.btn9:
                case R.id.btn0:
                case R.id.btnDot:
                    mChannelTextView.setText((new StringBuilder()).append(mChannelTextView.getText()).append(((Button) v).getText().toString()).toString());
                    break;
                case R.id.btnDel:
                    num.delete(0, num.length());
                    mChannelTextView.setText(num);
                    break;
                default:
                    break;

            }
        }
    };

    public void showLongClickDiaglog(int tunerindex){
        new AlertDialog.Builder(mContext)
                .setTitle("请选择模式")
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                         //TODO tunerindex=频道index
                        /* User clicked so do some stuff */
                        String[] items = getResources().getStringArray(R.array.select_dialog_items);
                        new AlertDialog.Builder(mContext)
                                .setMessage("You selected: " + which + " , " + items[which])
                                .show();
                    }
                })
                .create().show();
    }

    class FMSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (arg1 == null){
                return;
            }
            TextView v1 = (TextView)arg1;
            v1.setTextColor(Color.WHITE); //可以随意设置自己要的颜色值
//            Log.e("donghui","v1.getWidth()"+v1.getWidth());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Log.e("gallery", "choose: " + mFMArray[arg2]);
//            Log.e("donghui","v1.getWidth()2"+v1.getWidth());
            if(FMchangeAndSendOrder) {
                if(arg2 == 0) {
                    //FF 77 06 04 01 00 00 0D 0A
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x04, (byte)0x01, (byte)0x00
                            , (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                }
                else if(arg2 == 1) {
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x04, (byte)0x02, (byte)0x00
                            , (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                }
                else if(arg2 == 2) {
                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x04, (byte)0x03, (byte)0x00
                            , (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                }
            }

    /*
    /FF77030100xx000D0A
                String volumeStrHex = Integer.toHexString(Integer.parseInt(volumeArray[arg2]));
                //byte volumeByteHex = Byte.parseByte(volumeStrHex, 16);
                byte volumeByteHex = MathMethod.HexStringToBytes(volumeStrHex);
                byte[] targetOrder = {(byte)0xFF, (byte)0x77, (byte)0x03, (byte)0x01, (byte)0x00, (byte)volumeByteHex,
                        (byte)0x00, (byte)0x0D, (byte)0x0A};

                //BluetoothDemo.sendBlueOrder(targetOrder);*/

        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class PInfo {
        public String appname = "";
        public String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
        private void prettyPrint() {
            Log.e("gallery", appname + "===" + pname + "===" + versionName + "===" + versionCode + "===");
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("donghui","onRestoreInstanceState");
        mSaveTabSelect = savedInstanceState.getInt(SAVE_STATE_TAB_SELECT,0);
        Log.e("donghui","mSaveTabSelect"+mSaveTabSelect) ;
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("donghui","onSaveInstanceState");
        outState.putInt(SAVE_STATE_TAB_SELECT, mTabHost.getCurrentTab());
        Log.e("donghui","mSaveTabSelect"+mTabHost.getCurrentTab()) ;
        super.onSaveInstanceState(outState);
    }

    //gallery--
    /*public void startProgressBar() {
        if(progressBar == null) {
            progressBar = new ProgressDialog(this);
        }
        //gallery==replace
        //progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setMessage("Pairing ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }*/

    View.OnClickListener RadioBtnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.e("gallery", "button radio click =================================> ");
            bottomLYRadioGroupFlag = true;
            int index = v.getId();
            switch (index){
                case R.id.group_1:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm1Value-minFMvalue)/mFmStepValue);
                        //FF 77 06 07 XX 00 00 0D 0A
                        byte[] blueOrder1 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x01, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder1);
                        mChannelText.setText(new StringBuffer("Preset: ").append("1"));
                    }
                    break;
                case R.id.group_2:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm2Value-minFMvalue)/mFmStepValue);
                        byte[] blueOrder2 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x02, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder2);
                        mChannelText.setText(new StringBuffer("Preset: ").append("2"));
                    }
                    break;
                case R.id.group_3:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm3Value-minFMvalue)/mFmStepValue);
                        byte[] blueOrder3 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x03, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder3);
                        mChannelText.setText(new StringBuffer("Preset: ").append("3"));
                    }
                    break;
                case R.id.group_4:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm4Value-minFMvalue)/mFmStepValue);
                        byte[] blueOrder4 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x04, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder4);
                        mChannelText.setText(new StringBuffer("Preset: ").append("4"));
                    }
                    break;
                case R.id.group_5:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm5Value-minFMvalue)/mFmStepValue);
                        byte[] blueOrder5 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x05, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder5);
                        mChannelText.setText(new StringBuffer("Preset: ").append("5"));
                    }
                    break;
                case R.id.group_6:
                    if(bottomLYRadioGroupFlag) {
                        //mChannelSeekBar.setProgress((mFMm6Value-minFMvalue)/mFmStepValue);
                        byte[] blueOrder6 = {(byte)0xFF, (byte)0x77, (byte)0x06, (byte)0x07, (byte)0x06, (byte)0x00,
                                (byte)0x00, (byte)0x0D, (byte)0x0A };
                        BluetoothDemo.sendBlueOrder(blueOrder6);
                        mChannelText.setText(new StringBuffer("Preset: ").append("6"));
                    }
                    break;
            }
        }
    };

    public void setTab9APPName(String app) {
        mPreferences.putString("tab9", app);
    }

    public String getTab9AppName(){
        return  mPreferences.getString("tab9", "");
    }

    public void setTab10APPName(String app) {
        mPreferences.putString("tab10", app);
    }

    public String getTab10AppName(){
        return  mPreferences.getString("tab10", "");
    }

    public void setTab11APPName(String app) {
        mPreferences.putString("tab11", app);
    }

    public String getTab11AppName(){
        return  mPreferences.getString("tab11", "");
    }

    public void onPause() {
        super.onPause();
        if(realExit) {
            //BluetoothDemo.notifyMeFriWill(100);
            //BluetoothDemo.mContext.finish();
            //galleryActivity.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Sure to Exit App ?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //try {
                                //BluetoothDemo.connectFlag = true;
                                if(usbOrSD) {
                                    MathMethod.writeSharedPreferences("usbORsd", "USB");
                                }
                                else {
                                    MathMethod.writeSharedPreferences("usbORsd", "SD");
                                }

                                if(!BluetoothDemo.connectFlag) {
                                    BluetoothDemo.mContext.finish();
                                    galleryActivity.finish();
                                    System.exit(0);
                                }
                                else {
                                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x7E, (byte)0x00, (byte)0x00, (byte)0x00,
                                            (byte)0x00, (byte)0x0D, (byte)0x0A};
                                    BluetoothDemo.sendBlueOrder(blueOrder);
                                }

                                BluetoothDemo.receiveFlag = false;

                                /*if (BluetoothDemo.outStream != null) {
                                    BluetoothDemo.outStream.close();
                                    BluetoothDemo.outStream = null;
                                }
                                if (BluetoothDemo.inputStream != null) {
                                    BluetoothDemo.inputStream.close();
                                    BluetoothDemo.inputStream = null;
                                }
                                if (BluetoothDemo.btSocket != null) {
                                    BluetoothDemo.btSocket.close();
                                    BluetoothDemo.btSocket = null;
                                }

                            } catch (IOException e) {
                                Log.e("gallery", "=================== exit catch IOException ===================== ");
                            }
                            finish();*/
                        }
                    }).show();
        }
        return true;
    }

    private void startTimerWait() {
        if(mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message msg = new Message();
                            msg.what = 122;
                            Activity02.handlerRadioInterface.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, 1);
    }

    private void startTimerWaitForNextMusic() {
        if(mTimerTaskNextMusic != null) {
            mTimerTaskNextMusic.cancel();
            mTimerTaskNextMusic = null;
        }
        if(mTimerNextMusic != null) {
            mTimerNextMusic.cancel();
            mTimerNextMusic = null;
        }
        mTimerNextMusic = new Timer();
        mTimerTaskNextMusic = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Message msg = new Message();
                            msg.what = 144;
                            Activity02.handlerRadioInterface.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        if(mTimerNextMusic != null && mTimerTaskNextMusic != null )
            mTimerNextMusic.schedule(mTimerTaskNextMusic, 1);
    }
}