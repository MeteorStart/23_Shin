package com.xiujichuanmei.a23_haoxin.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.lx.baseproject.base.BaseFragment;
import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 待收取Fragment
 * @version: V_1.0.0
 * @date: 2017/6/13 14:08
 * @company:
 * @email: lx802315@163.com
 */
public class BeChargedFragment extends BaseFragment {
    @Override
    protected View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_be_charged, null);
        return rootView;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoad() {

    }
}
