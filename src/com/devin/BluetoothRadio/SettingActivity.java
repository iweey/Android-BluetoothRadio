package com.devin.BluetoothRadio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;
import android.content.pm.ActivityInfo;

public class SettingActivity extends Activity  {

    public static boolean settingControlFlag;

    public static int mlist_preference_eqIndex = 0;
    public static LinearLayout mset_eq;
    public static TextView mset_text_eq;

    public static boolean mbalanceSendFlag;
    public static TextView mset_text_balance;
    public static SeekBar mset_seekbar_balance;
    public static int mbalance_preferenceIndex = 0;

    public static boolean mfaderSendFlag;
    public static TextView mset_text_fader;
    public static SeekBar mseek_seekbar_fader;
    public static int mfader_preferenceIndex = 0;

    public static TextView mset_text_back;
    public static SeekBar mset_seekbar_back;

    public static Switch mswitch_loudness;
    public static boolean loudnessFlag;
    public static boolean mswitch_loudnessSendFlag;
    private int mswitch_loudnessSendFlag2 = 0;

    String[] eq_array = new String[]{"off","Flat","Classic","Rock","Pop"};
    private View.OnClickListener set_eq_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(SettingActivity.this)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle(R.string.alert_dialog_single_choice_eq)
                    .setItems(eq_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mset_text_eq.setText(eq_array[which]);

                            //FF 77 0E 00 00 00 00 00 0D 0A
                            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x00, (byte)0x00, (byte)0x00
                                    , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                            if(which == 0) {
                                BluetoothDemo.sendBlueOrder(blueOrder);
                            }
                            else if(which == 1) {
                                //FF 77 8B 00 01 00 00 00 0D 0A
                                blueOrder[4] = (byte)0x01;
                                BluetoothDemo.sendBlueOrder(blueOrder);
                            }
                            else if(which == 2) {
                                blueOrder[4] = (byte)0x02;
                                BluetoothDemo.sendBlueOrder(blueOrder);
                            }
                            else if(which == 3) {
                                blueOrder[4] = (byte)0x03;
                                BluetoothDemo.sendBlueOrder(blueOrder);
                            }
                            else if(which == 4) {
                                blueOrder[4] = (byte)0x04;
                                BluetoothDemo.sendBlueOrder(blueOrder);
                            }

                        }
                    })

                    .create().show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.setting_ly);
        initView();

        settingControlFlag = true;
        mswitch_loudnessSendFlag = true;
        Log.e("gallery", "mswitch_loudnessSendFlag2 === " + mswitch_loudnessSendFlag2);
    }

    private void initView() {
        mset_eq = (LinearLayout)findViewById(R.id.set_eq);
        mset_eq.setOnClickListener(set_eq_click);
        mset_text_eq = (TextView)findViewById(R.id.set_text_eq);
        if(mlist_preference_eqIndex == 0)
            mset_text_eq.setText("off");
        else if(mlist_preference_eqIndex == 1)
            mset_text_eq.setText("Flat");
        else if(mlist_preference_eqIndex == 2)
            mset_text_eq.setText("Classic");
        else if(mlist_preference_eqIndex == 3)
            mset_text_eq.setText("Rock");
        else if(mlist_preference_eqIndex == 4)
            mset_text_eq.setText("Pop");

        mset_text_balance = (TextView)findViewById(R.id.set_text_balance);
        mset_seekbar_balance = (SeekBar)findViewById(R.id.set_seekbar_balance);
        mset_seekbar_balance.setMax(14);
        mset_seekbar_balance.setProgress(7);
        mset_seekbar_balance.setProgress(mbalance_preferenceIndex);
        if(mbalance_preferenceIndex == 7) {
            mset_text_balance.setText("00");
        }
        else if(mbalance_preferenceIndex > 7) {
            mset_text_balance.setText("R0" + (mbalance_preferenceIndex - 7));
        }
        else if(mbalance_preferenceIndex < 7) {
            mset_text_balance.setText("L0" + (7 - mbalance_preferenceIndex));
        }
        mset_seekbar_balance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mbalanceSendFlag = true;
                return false;
            }
        });
        mset_seekbar_balance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mbalanceSendFlag) {
                    mbalance_preferenceIndex = i;
                    byte valueByte = (byte) i;
                    Log.e("gallery", "balance value === " + valueByte);

                    if(mbalance_preferenceIndex == 7) {
                        mset_text_balance.setText("00");
                    }
                    else if(mbalance_preferenceIndex > 7) {
                        mset_text_balance.setText("R0" + (mbalance_preferenceIndex-7));
                    }
                    else if(mbalance_preferenceIndex < 7) {
                        mset_text_balance.setText("L0" + (7-mbalance_preferenceIndex));
                    }

                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x03, valueByte, (byte)0x00
                            , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mset_text_fader = (TextView)findViewById(R.id.set_text_fader);
        mseek_seekbar_fader = (SeekBar)findViewById(R.id.seek_seekbar_fader);
        mseek_seekbar_fader.setMax(14);
        mseek_seekbar_fader.setProgress(7);
        mseek_seekbar_fader.setProgress(mfader_preferenceIndex);
        if(mfader_preferenceIndex == 7) {
            mset_text_fader.setText("00");
        }
        else if(mfader_preferenceIndex > 7) {
            mset_text_fader.setText("F0" + (mfader_preferenceIndex-7));
        }
        else if(mfader_preferenceIndex < 7) {
            mset_text_fader.setText("R0" + (7-mfader_preferenceIndex));
        }
        mseek_seekbar_fader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mfaderSendFlag = true;
                return false;
            }
        });
        mseek_seekbar_fader.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mfaderSendFlag) {
                    mfader_preferenceIndex = i;
                    byte valueByte = (byte) i;
                    Log.e("gallery", "fader value === " + valueByte);

                    if(mfader_preferenceIndex == 7) {
                        mset_text_fader.setText("00");
                    }
                    else if(mfader_preferenceIndex > 7) {
                        mset_text_fader.setText("F0" + (mfader_preferenceIndex-7));
                    }
                    else if(mfader_preferenceIndex < 7) {
                        mset_text_fader.setText("R0" + (7-mfader_preferenceIndex));
                    }

                    byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x04, valueByte, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                    BluetoothDemo.sendBlueOrder(blueOrder);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //mset_text_back = (TextView)findViewById(R.id.set_text_back);
        mset_seekbar_back = (SeekBar)findViewById(R.id.set_seekbar_back);
        mset_seekbar_back.setMax(255);
        mset_seekbar_back.setProgress((int) (android.provider.Settings.System.getInt(SettingActivity.this.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS, 255)));
        mset_seekbar_back.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Integer tmpInt = mset_seekbar_back.getProgress();
                if(tmpInt > 50) {
                    System.out.println(tmpInt);
                    // 51 (seek scale) * 5 = 255 (max brightness)
                    // Old way
                    android.provider.Settings.System.putInt(SettingActivity.this.getContentResolver(),
                            android.provider.Settings.System.SCREEN_BRIGHTNESS,
                            tmpInt); // 0-255
                    tmpInt = Settings.System.getInt(SettingActivity.this.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, -1);

                    Window localWindow = SettingActivity.this.getWindow();
                    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
                    float f = tmpInt / 255.0F;
                    localLayoutParams.screenBrightness = f;
                    localWindow.setAttributes(localLayoutParams);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mswitch_loudness = (Switch)findViewById(R.id.switch_Loudness);
        mswitch_loudness.setTextOn("On");
        mswitch_loudness.setTextOff("Off");
        mswitch_loudnessSendFlag = true;
        if(loudnessFlag) {
            mswitch_loudness.setChecked(true);
        }
        else {
            mswitch_loudness.setChecked(false);
        }
        mswitch_loudness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mswitch_loudnessSendFlag = true;
            }
        });
        mswitch_loudness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mswitch_loudnessSendFlag2 == 0) {
                    Log.e("gallery", "ischeck === " + b);
                    if(b) {
                        loudnessFlag = true;
                        //FF 77 0E 06 01 00 00 00 0D 0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x01, (byte)0x00
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                    }
                    else {
                        loudnessFlag = false;
                        //FF 77 0E 06 00 00 00 00 0D 0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                    }
                    mswitch_loudnessSendFlag2 = mswitch_loudnessSendFlag2 + 1;
                }
                if(mswitch_loudnessSendFlag) {
                    Log.e("gallery", "ischeck === " + b);
                    if(b) {
                        loudnessFlag = true;
                        //FF 77 0E 06 01 00 00 00 0D 0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x01, (byte)0x00
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                    }
                    else {
                        loudnessFlag = false;
                        //FF 77 0E 06 00 00 00 00 0D 0A
                        byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x00, (byte)0x00
                                , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                        BluetoothDemo.sendBlueOrder(blueOrder);
                    }
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        settingControlFlag = false;
    }
}