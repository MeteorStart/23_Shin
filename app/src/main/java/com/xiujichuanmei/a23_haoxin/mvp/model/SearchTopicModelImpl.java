package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.SearchTopicBody;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ISearchModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSearchLinster;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 话题搜索相关接口
 * @version: V_1.0.0
 * @date: 2017/6/19 13:58
 * @company:
 * @email: lx802315@163.com
 */
public class SearchTopicModelImpl implements ISearchModel {

    Context context;

    public SearchTopicModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void getDatas(SearchBody searchBody, final OnSearchLinster onSearchLinster) {
        if (context != null) {
            if (searchBody instanceof SearchTopicBody) {
                if (((SearchTopicBody) searchBody).getCondition() != null && ((SearchTopicBody) searchBody).getCondition().length() > 0) {
                    Observable<BaseEntity<TopicInfo>> observable = RetrofitFactory.getInstance().searchTopic(searchBody);
                    observable.compose(RxSchedulers.<BaseEntity<TopicInfo>>compose())
                            .subscribe(new BaseObserver<TopicInfo>(context) {
                                @Override
                                protected void onHandleSuccess(TopicInfo topicInfo) {
                                    onSearchLinster.OnSearchSuccess(topicInfo);
                                    LogUtils.print(topicInfo.toString());
                                }

                                @Override
                                protected void onHandleError(String msg) {
                                    onSearchLinster.OnSearchError(msg);
                                }
                            });
                } else {
                    Observable<BaseEntity<TopicInfo>> observable = RetrofitFactory.getInstance().selectTopic(searchBody);
                    observable.compose(RxSchedulers.<BaseEntity<TopicInfo>>compose())
                            .subscribe(new BaseObserver<TopicInfo>(context) {
                                @Override
                                protected void onHandleSuccess(TopicInfo topicInfo) {
                                    onSearchLinster.OnSearchSuccess(topicInfo);
                                    LogUtils.print(topicInfo.getRecentlyTopic().toString());
                                }

                                @Override
                                protected void onHandleError(String msg) {
                                    onSearchLinster.OnSearchError(msg);
                                }
                            });
                }
            }

        }
    }
}
