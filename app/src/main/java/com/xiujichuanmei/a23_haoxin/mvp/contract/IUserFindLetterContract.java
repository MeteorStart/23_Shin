package com.xiujichuanmei.a23_haoxin.mvp.contract;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.NotSlipViewPager;

/**
 * @author: X_Meteor
 * @description: 用户  我找的信契约类
 * @version: V_1.0.0
 * @date: 2017/6/16 15:10
 * @company:
 * @email: lx802315@163.com
 */
public class IUserFindLetterContract {

    public interface IUserFindLetterView extends BaseView<IUserFindLetterPresenter> {
        /**
         * 获取显示内容的Viewpager
         *
         * @return viewpager 对象
         */
        NotSlipViewPager getmFragUserFindLetterVpContent();

        /**
         * 获取显示内容的TabLayout
         *
         * @return tabLayout 对象
         */
        TabLayout getmTabLayout();

        /**
         * 获取当前activity的Fragment管理器
         *
         * @return Fragment管理器
         */
        FragmentManager getManager();

        void setLetterInfo(LetterInfo letterInfo);
    }

    public interface IUserFindLetterPresenter extends BasePresenter<IUserFindLetterView> {
        void getLetterList(ShowMyLetterBody showMyLetterBody);
    }

}
