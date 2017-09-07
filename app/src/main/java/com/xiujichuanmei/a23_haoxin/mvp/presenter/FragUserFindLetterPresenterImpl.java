package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.adapter.FragmentAdapter;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IUserFindLetterContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.UserFindLetterModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnShowMyLetterListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.UserFindLetterReceiveFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.UserFindLetterSendFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.UserFindLetterSubFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.UserWaitLetterSendFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.UserWaitLetterSubFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.NotSlipViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/16 16:03
 * @company:
 * @email: lx802315@163.com
 */
public class FragUserFindLetterPresenterImpl implements IUserFindLetterContract.IUserFindLetterPresenter, OnShowMyLetterListener {

    Context context;

    NotSlipViewPager vpActUserFindLetter;

    TabLayout tabActUserFindLetter;

    FragmentManager fragmentManager;

    IUserFindLetterContract.IUserFindLetterView mView;

    UserFindLetterModelImpl model;

    public FragUserFindLetterPresenterImpl(Context context, IUserFindLetterContract.IUserFindLetterView mView) {
        this.mView = mView;
        this.context = context;
        mView.setPresenter(this);
        model = new UserFindLetterModelImpl(context);
    }

    public void setItem(int type) {
        vpActUserFindLetter.setCurrentItem(type, false);
    }

    @Override
    public void initData() {
        tabActUserFindLetter = mView.getmTabLayout();
        vpActUserFindLetter = mView.getmFragUserFindLetterVpContent();
        fragmentManager = mView.getManager();

        List<String> titles = new ArrayList<>();
        titles.add("预约");
        titles.add("发出");
        titles.add("收到");

        for (int i = 0; i < titles.size(); i++) {
            tabActUserFindLetter.addTab(tabActUserFindLetter.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new UserFindLetterSubFragment());
        fragments.add(new UserFindLetterSendFragment());
        fragments.add(new UserFindLetterReceiveFragment());

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(fragmentManager, fragments, titles);
        //给ViewPager设置适配器
        vpActUserFindLetter.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        tabActUserFindLetter.setupWithViewPager(vpActUserFindLetter);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        vpActUserFindLetter.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabActUserFindLetter));

        //TabLayout的监听
        tabActUserFindLetter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                vpActUserFindLetter.setCurrentItem(position, false);
            }

            @Override

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //给TabLayout设置适配器
        tabActUserFindLetter.setTabsFromPagerAdapter(mFragmentAdapteradapter);

        tabActUserFindLetter.post(new Runnable() {
            /**
             * 保证在TabLayout渲染完成后修改
             */
            @Override
            public void run() {
                setIndicator(tabActUserFindLetter, 20, 20);
            }
        });
    }

    public void initWaitData() {
        tabActUserFindLetter = mView.getmTabLayout();
        vpActUserFindLetter = mView.getmFragUserFindLetterVpContent();
        fragmentManager = mView.getManager();

        List<String> titles = new ArrayList<>();
        titles.add("发起");
        titles.add("完成");

        for (int i = 0; i < titles.size(); i++) {
            tabActUserFindLetter.addTab(tabActUserFindLetter.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new UserWaitLetterSendFragment());
        fragments.add(new UserWaitLetterSubFragment());

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(fragmentManager, fragments, titles);
        //给ViewPager设置适配器
        vpActUserFindLetter.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        tabActUserFindLetter.setupWithViewPager(vpActUserFindLetter);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        vpActUserFindLetter.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabActUserFindLetter));

        //TabLayout的监听
        tabActUserFindLetter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                vpActUserFindLetter.setCurrentItem(position, false);
            }

            @Override

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //给TabLayout设置适配器
        tabActUserFindLetter.setTabsFromPagerAdapter(mFragmentAdapteradapter);

        tabActUserFindLetter.post(new Runnable() {
            /**
             * 保证在TabLayout渲染完成后修改
             */
            @Override
            public void run() {
                setIndicator(tabActUserFindLetter, 60, 60);
            }
        });
    }

    /**
     * @name: setIndicator
     * @description: 设置TabLayout每个Tab的左右间距
     * @date: 2017/6/13 14:43
     * @company:
     * @author: Meteor
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    public void OnSearchSuccess(LetterInfo request, int page) {
        mView.canelLoadingDialog();
        mView.setLetterInfo(request);
    }

    @Override
    public void OnSearchError(String msg) {
        mView.canelLoadingDialog();
//        ToastUtils.showToast(context, msg);
    }

    @Override
    public void getLetterList(ShowMyLetterBody showMyLetterBody) {
        mView.showLoadingDialog("", "加载中。。。", true);
        model.getLetterList(showMyLetterBody, this);
    }
}
