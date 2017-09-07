package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.SearchTopicBody;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.widget.CustomLinearLayoutManager;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.SearchTopicAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISearchTopicContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.SearchTopicModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSearchLinster;

import java.util.List;


/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/19 11:29
 * @company:
 * @email: lx802315@163.com
 */
public class ActSearchTopicPreImpl implements ISearchTopicContract.ISearchTopicPresenter, OnSearchLinster {

    Context context;
    ISearchTopicContract.ISearchTopicView mView;
    SearchTopicModelImpl model;

    SearchBody searchBody;

    SearchTopicAdapter topicAdapter;
    SearchTopicAdapter historyAdapter;

    List<TopicInfo.TopicBean> topicList;
    List<TopicInfo.TopicBean> historyList;

    boolean isFirst;

    public ActSearchTopicPreImpl(ISearchTopicContract.ISearchTopicView mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new SearchTopicModelImpl(context);

        initData();
    }

    @Override
    public void initData() {
        isFirst = true;
    }

    @Override
    public void getSearchDatas() {

        if (mView.getSearchEdit().getText().length() > 0) {
            searchBody = new SearchTopicBody(SharedPreferencesUtils.getString(context, Constants.USER_TOKNE), mView.getSearchEdit().getText().toString(), 0, 0);
        } else {
            searchBody = new SearchTopicBody(SharedPreferencesUtils.getString(context, Constants.USER_TOKNE));
        }

        mView.showLoadingDialog("", "加载中...", true);
        model.getDatas(searchBody, this);
    }

    @Override
    public void OnSearchSuccess(Object request) {
        mView.canelLoadingDialog();
        if (request instanceof TopicInfo) {
            topicList = ((TopicInfo) request).getTopic();
            historyList = ((TopicInfo) request).getRecentlyTopic();

            if (historyList != null && historyList.size() > 0) {
                mView.getActSearchTopicHistoryLay().setVisibility(View.VISIBLE);
                initHistoryAdapter(historyList);
            } else {
                mView.getActSearchTopicHistoryLay().setVisibility(View.GONE);
            }

            if (topicList != null && topicList.size() > 0) {
                mView.getActSearchTopicHotLay().setVisibility(View.VISIBLE);
                if (isFirst) {
                    isFirst = false;
                    mView.getTextSearchTopicHot().setText("热门");
                    initTopicAdapter(topicList);
                } else {
                    mView.getTextSearchTopicHot().setText("搜索结果");
                    topicAdapter.setSupperText(mView.getSearchEdit().getText().toString());
                    topicAdapter.setNewData(topicList);
                }
            } else {
                mView.getActSearchTopicHotLay().setVisibility(View.GONE);
            }
        }


    }

    @Override
    public void OnSearchError(String msg) {
        mView.canelLoadingDialog();
        LogUtils.print(msg);
    }

    public void initTopicAdapter(final List<TopicInfo.TopicBean> topicList) {
        topicAdapter = new SearchTopicAdapter(R.layout.item_search_topic, topicList);
        topicAdapter.setSupperText(mView.getSearchEdit().getText().toString());
        topicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                RxBus.getInstance().post(topicList.get(position));
                mView.jumpActivity(null);
            }
        });

        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        mView.getyActSearchTopicHotLRecy().setLayoutManager(linearLayoutManager);
        mView.getyActSearchTopicHotLRecy().setAdapter(topicAdapter);
        mView.getyActSearchTopicHotLRecy().addItemDecoration(new SimpleDicider());
    }

    public void initHistoryAdapter(List<TopicInfo.TopicBean> historyList) {
        historyAdapter = new SearchTopicAdapter(R.layout.item_search_topic, historyList);
        historyAdapter.setSupperText(mView.getSearchEdit().getText().toString());
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                RxBus.getInstance().post(topicList.get(position));
                mView.jumpActivity(null);
            }
        });
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        mView.getSearchTopicHistoryRecy().setLayoutManager(linearLayoutManager);
        mView.getSearchTopicHistoryRecy().setAdapter(historyAdapter);
        mView.getSearchTopicHistoryRecy().addItemDecoration(new SimpleDicider());
    }
}
