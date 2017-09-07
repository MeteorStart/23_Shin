package com.project.lx.baseproject.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.lx.baseproject.application.MyApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/3/23 0023 15:51
 * @company: 洛阳魅力文化传媒   http://www.fenglin.tv/
 * @email: lx802315@163.com
 */
public class TimeButton extends Button implements View.OnClickListener {

    //倒计时时长(默认为60s)
    private long length = 60 * 1000;
    //设置点击前显示
    private String beforText = "获取验证码";
    //设置点击后显示
    private String afrerText = "秒后重发";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnClickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private Context context;
    Map<String, Long> map = new HashMap<>();

    public TimeButton(Context context) {
        this(context, null);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        this.context = context;
        TimeButton.this.setText(beforText);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TimeButton.this.setText(time / 1000 + afrerText);
            time -= 1000;
            if (time < 0) {
                if (onTimeOverLisenter != null) {
                    onTimeOverLisenter.timeOver();
                }
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(beforText);
                clearTimer();
            }
        }
    };

    private OnTimeOverLisenter onTimeOverLisenter;

    public void setOnTimeOverLisenter(OnTimeOverLisenter onTimeOverLisenter) {
        this.onTimeOverLisenter = onTimeOverLisenter;
    }

    public interface OnTimeOverLisenter {
        void timeStart();

        void timeOver();
    }

    private void initTimer() {
        time = length;
        t = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                Log.i("XXXXXXX", time / 1000 + " ");
                handler.sendEmptyMessage(0);
            }
        };
    }

    private void clearTimer() {
        Log.i("XXXXXXXXX", "计时结束 ");
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null) {
            t.cancel();
        }
        t = null;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else {
            this.mOnClickListener = l;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
        if (onTimeOverLisenter != null){
            onTimeOverLisenter.timeStart();
        }
        initTimer();
        this.setText(time / 1000 + afrerText);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
    }


    /**
     * 和Acticity的onDestroy方法同步
     */
    public void onDestroy() {
        if (MyApplication.map == null) {
            MyApplication.map = new HashMap<>();
        }
        MyApplication.map.put(TIME, time);
        MyApplication.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.i("XXXXXXXX", "onDestroy: ");
    }

    public void onCreate(Bundle bundle) {
        Log.i("XXXXXXXX", MyApplication.map + "");
        if (MyApplication.map == null) {
            return;
        }
        //这里表示有未完成的倒计时
        if (MyApplication.map.size() > 0) {
            return;
        }
        long time = System.currentTimeMillis() - MyApplication.map.get(CTIME) - MyApplication.map.get(TIME);
        MyApplication.map.clear();
        if (time > 0) {
            return;
        } else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + afrerText);
            this.setEnabled(false);
        }
    }

    /**
     * 设置点击之前的文本
     *
     * @param text0
     * @return
     */
    public TimeButton setTextBefore(String text0) {
        this.beforText = text0;
        this.setText(beforText);
        return this;
    }

    /**
     * 设置计时时候显示的文本
     *
     * @param text1
     * @return
     */
    public TimeButton setTextAfter(String text1) {
        this.afrerText = text1;
        return this;
    }

    /**
     * 设置倒计时长度
     * 时间默认  毫秒
     *
     * @param lenght
     * @return
     */
    public TimeButton setLenght(long lenght) {
        this.length = lenght;
        return this;
    }
}
