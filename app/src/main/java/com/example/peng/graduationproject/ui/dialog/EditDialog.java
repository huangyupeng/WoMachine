package com.example.peng.graduationproject.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.peng.graduationproject.R;

/**
 * Created by Administrator on 2016/4/14.
 */
public class EditDialog extends Dialog {
    private Context context;

    private TextView dialog_title;
    private EditText editText;
    private TextView txt_cancel;
    private TextView txt_ok;

    private View.OnClickListener mOkListener;
    private View.OnClickListener mCancelListener;




    public EditDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_common, null);
        this.setContentView(view);

        dialog_title = (TextView)view.findViewById(R.id.dialog_title);
        editText = (EditText)view.findViewById(R.id.edit_text);
        txt_cancel = (TextView)view.findViewById(R.id.txt_cancel);
        txt_ok = (TextView)view.findViewById(R.id.txt_ok);

        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOkListener!=null){
                    mOkListener.onClick(v);
                    return;
                }
                EditDialog.this.dismiss();
                return ;
            }
        });

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener!=null){
                    mCancelListener.onClick(v);
                    return;
                }
                EditDialog.this.dismiss();
                return ;
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        lp.width = (int) (d.widthPixels * 0.84); // 宽度设置为屏幕的
        dialogWindow.setAttributes(lp);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public void setOkListener(android.view.View.OnClickListener mOkListener) {
        this.mOkListener = mOkListener;
    }

    public void setCancelListener(android.view.View.OnClickListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }

    public void setOkText(String str) {
        txt_ok.setText(str);
    }

    public void setCancelText(String str) {
        txt_cancel.setText(str);
    }

    public String getEditText() {
        return editText.getText().toString().trim();
    }

    public void setDialogTitle(String title) {
        dialog_title.setText(title);
    }

    public void setTextHint(String hint) {
        editText.setHint(hint);
    }
}
