package com.devin.BluetoothRadio;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class SeekBarPreferenceLight extends DialogPreference implements SeekBar.OnSeekBarChangeListener
{
    private static final String androidns="http://schemas.android.com/apk/res/android";

    private SeekBar mSeekBar;
    private TextView mSplashText,mValueText;
    private Context mContext;

    private String mDialogMessage, mSuffix;
    private int mDefault, mMax, mValue = 0;

    public SeekBarPreferenceLight(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;

        mDialogMessage = attrs.getAttributeValue(androidns,"dialogMessage");
        mSuffix = attrs.getAttributeValue(androidns,"text");
        mDefault = attrs.getAttributeIntValue(androidns,"defaultValue", 0);
        mMax = attrs.getAttributeIntValue(androidns,"max", 100);
        mMax = 255;
    }
    @Override
    protected View onCreateDialogView() {
        LinearLayout.LayoutParams params;
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(6,6,6,6);

        mSplashText = new TextView(mContext);
        if (mDialogMessage != null)
            mSplashText.setText(mDialogMessage);
        layout.addView(mSplashText);

        mValueText = new TextView(mContext);
        mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
        mValueText.setTextSize(32);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(mValueText, params);

        mSeekBar = new SeekBar(mContext);
        mSeekBar.setOnSeekBarChangeListener(this);
        layout.addView(mSeekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        if (shouldPersist())
            mValue = getPersistedInt(mDefault);

        mSeekBar.setMax(mMax);
        mSeekBar.setProgress(mValue);
        return layout;
    }
    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        mSeekBar.setMax(mMax);
        //mSeekBar.setProgress(mValue);
        mSeekBar.setProgress((int) (android.provider.Settings.System.getInt( Activity02.galleryActivity.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS, 255) ));

        this.setSummary("Back Light: " + mSeekBar.getProgress());
    }
    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue)
    {
        super.onSetInitialValue(restore, defaultValue);
        if (restore)
            mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
        else
            mValue = (Integer)defaultValue;
    }

    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
    {
        String t = String.valueOf(value);
        mValueText.setText(mSuffix == null ? t : t.concat(mSuffix));

        System.out.println("gallery mValueText Value ===============> " + mValueText.getText());
        String temporaryStr = mValueText.getText().toString().trim();
        this.setSummary("Back Light: " + temporaryStr);
        //int temporaryInt = Integer.parseInt(temporaryStr);

        if (fromTouch) {
            Integer tmpInt = mSeekBar.getProgress();
            System.out.println(tmpInt);
            // 51 (seek scale) * 5 = 255 (max brightness)
            // Old way
            android.provider.Settings.System.putInt(Activity02.galleryActivity.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                    tmpInt); // 0-255
            tmpInt = Settings.System.getInt(Activity02.galleryActivity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, -1);

            Window localWindow = Activity02.galleryActivity.getWindow();
            WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
            float f = tmpInt / 255.0F;
            localLayoutParams.screenBrightness = f;
            localWindow.setAttributes(localLayoutParams);
        }

        if (shouldPersist())
            persistInt(value);
        callChangeListener(new Integer(value));
    }
    public void onStartTrackingTouch(SeekBar seek) {}
    public void onStopTrackingTouch(SeekBar seek) {}

    public void setMax(int max) { mMax = max; }
    public int getMax() { return mMax; }

    public void setProgress(int progress) {
        mValue = progress;
        if (mSeekBar != null)
            mSeekBar.setProgress(progress);
    }
    public int getProgress() { return mValue; }
}