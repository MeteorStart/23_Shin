package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.widget.CustomLinearLayoutManager;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.ChooseRecipiAllAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.ChooseRecipiHistoryAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.SearchTopicAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISearchTopicContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.ChooseRecipientModeImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.SearchTopicModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ISearchModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSearchLinster;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/20 15:53
 * @company:
 * @email: lx802315@163.com
 */
public class ActChooseRecipientPreImpl implements ISearchTopicContract.ISearchTopicPresenter, OnSearchLinster {

    Context context;
    ISearchTopicContract.ISearchTopicView mView;
    ISearchModel mode;

    SearchBody searchBody;

    ChooseRecipiHistoryAdapter historyAdapter;
    ChooseRecipiAllAdapter topicAdapter;

    List<ContactsInfo.FriendBean> topicList;
    List<ContactsInfo.RecentlyFriendBean> historyList;

    public ActChooseRecipientPreImpl(Context context, ISearchTopicContract.ISearchTopicView mView) {
        this.context = context;
        this.mView = mView;
        mode = new ChooseRecipientModeImpl(context);
        initData();
    }

    @Override
    public void initData() {
        searchBody = new SearchBody(SharedPreferencesUtils.getString(context, Constants.USER_TOKNE));
    }

    @Override
    public void getSearchDatas() {
        mView.showLoadingDialog("", "加载中...", true);
        mode.getDatas(searchBody, this);
    }

    @Override
    public void OnSearchSuccess(Object request) {
        mView.canelLoadingDialog();
        if (request instanceof ContactsInfo) {
            topicList = ((ContactsInfo) request).getFriend();
            historyList = ((ContactsInfo) request).getRecentlyFriend();

            if (historyList != null && historyList.size() > 0) {
                initHistoryAdapter(historyList);
            } else {
                mView.getActSearchTopicHistoryLay().setVisibility(View.GONE);
            }

            if (topicList != null && topicList.size() > 0) {
                initTopicAdapter(topicList);
            } else {
                mView.getActSearchTopicHotLay().setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void OnSearchError(String msg) {
        mView.canelLoadingDialog();
    }

    public void initHistoryAdapter(final List<ContactsInfo.RecentlyFriendBean> historyList) {
        historyAdapter = new ChooseRecipiHistoryAdapter(R.layout.item_search_topic, historyList);
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
//                Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                RxBus.getInstance().post(historyList.get(position));
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

    public void initTopicAdapter(final List<ContactsInfo.FriendBean> topicList) {
        topicAdapter = new ChooseRecipiAllAdapter(R.layout.item_choose_recipi_all, topicList);
        topicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
//                Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
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
}
