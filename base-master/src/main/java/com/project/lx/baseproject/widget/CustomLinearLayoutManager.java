package com.project.lx.baseproject.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author: X_Meteor
 * @description: 不可滑动了RecycleView
 * @version: V_1.0.0
 * @date: 2017/6/19 14:47
 * @company:
 * @email: lx802315@163.com
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
