package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 倒计时弹窗
 * @version: V_1.0.0
 * @date: 2017/6/7 9:55
 * @company:
 * @email: lx802315@163.com
 */
public class ShowTimePopupWindow extends PopupWindow {

    Context context;
    TimeInfo timeInfo;
    String avarImg;
    String name;
    String number;

    long startTime;
    long endTime;
    long downTime;

    ImageView avare;
    TextView UserName, LetterNumber, hour, minute, second;

    String hourStr, minuteStr, secondStr;

    ShowTimePopupWindow showTimePopupWindow;

    private View mPopView;

    public ShowTimePopupWindow(Context context, TimeInfo timeInfo, String avarImg, String name, String number) {
        super(context);
        this.context = context;
        this.timeInfo = timeInfo;
        this.avarImg = avarImg;
        this.name = name;
        this.number = number;

        init(context);
        setPopupWindow();
        showTimePopupWindow = this;
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(final Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.pop_show_time, null);
        avare = (ImageView) mPopView.findViewById(R.id.imv_pop_show_time_avatar);
        UserName = (TextView) mPopView.findViewById(R.id.tv_pop_show_scan_username);
        LetterNumber = (TextView) mPopView.findViewById(R.id.tv_pop_show_time_number);
        hour = (TextView) mPopView.findViewById(R.id.tv_pop_show_time_hour);
        minute = (TextView) mPopView.findViewById(R.id.tv_pop_show_time_minute);
        second = (TextView) mPopView.findViewById(R.id.tv_pop_show_time_second);

        Glide.with(context).
                load(avarImg)
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(context))
                .into(avare);

        if (UserName.length() > 0) {
            UserName.setText(name);
        }

        if (number.length() > 0 && number.length() <= 20) {
            LetterNumber.setText("编号:" + number);
        } else {
            LetterNumber.setVisibility(View.GONE);
        }

        if (timeInfo != null) {
            startTime = timeInfo.getSysTime();
            endTime = timeInfo.getDestroyTime();
            downTime = endTime - startTime;
        }

        if (downTime > 0) {
            new CountDownTimer(downTime, 1000) {//总时间， 间隔时间

                public void onTick(long millisUntilFinished) {
                    setTiemText(millisUntilFinished);
                }

                public void onFinish() {
                    ToastUtils.showToast(context, "计时结束");
                    ShowTimePopupWindow.this.dismiss();
                }
            }.start();
        } else {
            ToastUtils.showToast(context, "该信件已超时");
            this.dismiss();
        }

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.center_pop_anim_style);// 设置动画
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.getRootView().getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setTiemText(long time) {
        secondStr = time / 1000 % 60 + "";
        if (secondStr.length() < 2) {
            secondStr = "0" + secondStr;
        }
        second.setText(secondStr);

        minuteStr = time / 1000 / 60 % 60 + "";
        if (minuteStr.length() < 2) {
            minuteStr = "0" + minuteStr;
        }
        minute.setText(minuteStr);

        hourStr = time / 1000 / 60 / 60 + "";
        if (hourStr.length() < 2) {
            hourStr = "0" + hourStr;
        }
        hour.setText(hourStr);
    }
}
