package com.xiujichuanmei.a23_haoxin.mvp.contract;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;
import com.project.lx.baseproject.bean.request.SearchBody;

/**
 * @author: X_Meteor
 * @description: 话题搜索 契约类
 * @version: V_1.0.0
 * @date: 2017/6/19 11:11
 * @company:
 * @email: lx802315@163.com
 */
public class ISearchTopicContract {

    public interface ISearchTopicView extends BaseView<ISearchTopicPresenter> {

        /**
         * 获取输入框
         *
         * @return
         */
        EditText getSearchEdit();

        /**
         * 获取搜索按钮
         *
         * @return
         */
        TextView getTextEdit();

        /**
         * 获取最近使用布局
         *
         * @return
         */
        LinearLayout getActSearchTopicHistoryLay();

        /**
         * 获取最近使用列表
         *
         * @return
         */
        RecyclerView getSearchTopicHistoryRecy();

        /**
         * 获取热门标题
         *
         * @return
         */
        TextView getTextSearchTopicHot();

        /**
         * 获取热门相关布局
         *
         * @return
         */
        LinearLayout getActSearchTopicHotLay();

        /**
         * 获取热门相关列表
         *
         * @return
         */
        RecyclerView getyActSearchTopicHotLRecy();
    }

    public interface ISearchTopicPresenter extends BasePresenter<ISearchTopicView> {
        /**
         * 查询数据
         */
        void getSearchDatas();
    }

}
