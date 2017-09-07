package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ISearchModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSearchLinster;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/20 15:57
 * @company:
 * @email: lx802315@163.com
 */
public class ChooseRecipientModeImpl implements ISearchModel {

    Context context;

    public ChooseRecipientModeImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void getDatas(SearchBody searchBody, final OnSearchLinster onSearchLinster) {
        if (context != null){
            Observable<BaseEntity<ContactsInfo>> observable = RetrofitFactory.getInstance().chooseRecipicent(searchBody);
            observable.compose(RxSchedulers.<BaseEntity<ContactsInfo>>compose())
                    .subscribe(new BaseObserver<ContactsInfo>(context) {
                        @Override
                        protected void onHandleSuccess(ContactsInfo contactsInfo) {
                            onSearchLinster.OnSearchSuccess(contactsInfo);
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onSearchLinster.OnSearchError(msg);
                        }
                    });
        }
    }
}
