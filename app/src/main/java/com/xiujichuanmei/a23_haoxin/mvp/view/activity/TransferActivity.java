package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.FragmentAdapter;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.BeChargedFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.fragment.BeSendFragment;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.NotSlipViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @name: TransferActivity
 * @description: 待中转信件页面
 * @version: 1.0
 * @date: 2017/6/13 10:10
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class TransferActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tab_act_transfer)
    TabLayout tabActTransfer;
    @BindView(R.id.vp_act_transfer)
    NotSlipViewPager vpActTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_transfer);
    }

    @Override
    public void initView() {
        textActTop.setText("待中转信件");
        imvActTopRight.setVisibility(View.GONE);
    }

    /**
     *
     */
    @Override
    public void initData() {
        List<String> titles = new ArrayList<>();
        titles.add("待发送");
        titles.add("待收取");

        for (int i = 0; i < titles.size(); i++) {
            tabActTransfer.addTab(tabActTransfer.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new BeSendFragment());
        fragments.add(new BeChargedFragment());

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        vpActTransfer.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        tabActTransfer.setupWithViewPager(vpActTransfer);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        vpActTransfer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabActTransfer));

        //TabLayout的监听
        tabActTransfer.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                vpActTransfer.setCurrentItem(position);
            }

            @Override

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //给TabLayout设置适配器
        tabActTransfer.setTabsFromPagerAdapter(mFragmentAdapteradapter);

        tabActTransfer.post(new Runnable() {
            /**
             * 保证在TabLayout渲染完成后修改
             */
            @Override
            public void run() {
                setIndicator(tabActTransfer, 60, 60);
            }
        });
    }

    @Override
    public void initListener() {

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
}
