package com.project.lx.baseproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.lx.baseproject.R;

/**
 * @author: X_Meteor
 * @description: 带下划线的按钮
 * @version: V_1.0.0
 * @date: 2017/5/17 16:37
 * @company:
 * @email: lx802315@163.com
 */
public class UnderLineBtn extends RelativeLayout {
    /**
     * 对应属性
     */
    private float lineHeight, lineWeight, textSize, textMarginTop;
    private int unCheckedColor, checkedColor;
    private int unLineColor, lineColor;
    private boolean isChecked;
    private String text;

    private Paint mTextPaint;

    /**
     * 控件
     */
    private TextView textView;//文字
    private View view;//下划线
    private LayoutParams textViewParams, viewParams;

    public UnderLineBtn(Context context) {
        this(context, null);
    }

    public UnderLineBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        /**
         * 获取自定义属性
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.underLineBtn);
        lineHeight = array.getDimension(R.styleable.underLineBtn_lineHeight, 1);
        lineWeight = array.getDimension(R.styleable.underLineBtn_lineWight, LayoutParams.MATCH_PARENT);
        textSize = array.getDimensionPixelSize(R.styleable.underLineBtn_textSize, 14);
        unCheckedColor = array.getColor(R.styleable.underLineBtn_unCheckedColor, Color.WHITE);
        checkedColor = array.getColor(R.styleable.underLineBtn_checkedColor, Color.BLUE);
        unLineColor = array.getColor(R.styleable.underLineBtn_unLineColor, Color.TRANSPARENT);
        lineColor = array.getColor(R.styleable.underLineBtn_lineColor, Color.BLACK);
        isChecked = array.getBoolean(R.styleable.underLineBtn_isChecked, false);
        text = array.getString(R.styleable.underLineBtn_text);
        textMarginTop = array.getDimension(R.styleable.underLineBtn_textMarginTop, 40);
        array.recycle();//属性获取完之后及时回收

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(textSize);

        //给控件赋值
        textView = new TextView(context);
        textView.setId(generateViewId());
        view = new View(context);

        if (isChecked) {
            textView.setTextColor(checkedColor);
            view.setBackgroundColor(checkedColor);
        } else {
            textView.setTextColor(unCheckedColor);
            view.setBackgroundColor(unCheckedColor);
        }
        textView.setText(text);

//        textView.setTextSize(textSize);
        textView.getPaint().setTextSize(textSize);
        //控件位置
        textViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textViewParams.setMargins(0, (int) (-(lineHeight + textMarginTop) / 2), 0, 0);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(textView, textViewParams);

        viewParams = new LayoutParams((int) lineWeight, (int) lineHeight);
//        viewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        viewParams.setMargins(0, (int) textMarginTop, 0, 0);
        int i = textView.getId();
        viewParams.addRule(RelativeLayout.BELOW, textView.getId());
        addView(view, viewParams);
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            /**
             * 保留改变后的显示，执行drawableStateChanged()中的变化
             * 不执行本方法：drawableStateChanged()中设置的改变将被复原
             */
            refreshDrawableState();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        //改变时的切换逻辑
        if (isChecked) {
            textView.setTextColor(checkedColor);
            view.setBackgroundColor(lineColor);
        } else {
            textView.setTextColor(unCheckedColor);
            view.setBackgroundColor(unLineColor);
        }
    }

}
