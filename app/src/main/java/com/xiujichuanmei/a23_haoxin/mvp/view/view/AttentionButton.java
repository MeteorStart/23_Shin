package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 关注按钮
 * @version: V_1.0.0
 * @date: 2017/6/12 9:35
 * @company:
 * @email: lx802315@163.com
 */
public class AttentionButton extends Button {

    private boolean isAtten;

    public boolean isAtten() {
        return isAtten;
    }

    public void setAtten(boolean atten) {
        isAtten = atten;
        onClick();
    }

    public AttentionButton(Context context) {
        super(context);
    }

    public AttentionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AttentionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void onClick() {
        if (isAtten) {
            this.setBackground(getRootView().getResources().getDrawable(R.drawable.shape_bg_btn_main));
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.yiguanzhu );
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            this.setCompoundDrawables(drawable, null, null, null);
            this.setText("已关注");
            this.setTextColor(Color.WHITE);
        } else {
            this.setBackground(getRootView().getResources().getDrawable(R.drawable.shape_bg_btn_item_shap_details));
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_gerenzhuye_guanzhu);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            this.setCompoundDrawables(drawable, null, null, null);
            this.setText("关注");
            this.setTextColor(getResources().getColor(R.color.act_main_line));
        }
    }

}
