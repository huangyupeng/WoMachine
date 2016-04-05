package com.example.peng.graduationproject.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.example.peng.graduationproject.R;

/**
 * Created by peng on 2016/3/30.
 */
public class TabItem extends RadioButton {
    public TabItem(Context context) {
        super(context);
    }

    public TabItem(Context context,AttributeSet attrs) {
        super(context,attrs);
    }


    @Override
    public boolean isChecked() {
        return super.isChecked();
    }

    @Override
    public void setButtonDrawable(int resid) {
        super.setButtonDrawable(resid);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }
}
