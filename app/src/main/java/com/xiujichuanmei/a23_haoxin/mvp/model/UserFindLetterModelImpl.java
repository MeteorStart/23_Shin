package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.responses.DemandInfo;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IUserFindLetterModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnShowMyDemandListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnShowMyLetterListener;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 9:37
 * @company:
 * @email: lx802315@163.com
 */
public class UserFindLetterModelImpl implements IUserFindLetterModel {
    Context context;

    public UserFindLetterModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {

    }

    @Override
    public void getLetterList(final ShowMyLetterBody showMyLetterBody, final OnShowMyLetterListener onShowMyLetterListener) {
        Observable<BaseEntity<LetterInfo>> observable = RetrofitFactory.getInstance().showMyLetter(showMyLetterBody);
        observable.compose(RxSchedulers.<BaseEntity<LetterInfo>>compose())
                .subscribe(new BaseObserver<LetterInfo>(context) {
                    @Override
                    protected void onHandleSuccess(LetterInfo letterInfo) {
                        onShowMyLetterListener.OnSearchSuccess(letterInfo, showMyLetterBody.getPage());
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        onShowMyLetterListener.OnSearchError(msg);
                    }
                });
    }

    @Override
    public void getDemandList(final ShowMyLetterBody showMyDemandBody, final OnShowMyDemandListener onShowMyDemandListener) {
        Observable<BaseEntity<DemandInfo>> observable = RetrofitFactory.getInstance().showMyDemand(showMyDemandBody);
        observable.compose(RxSchedulers.<BaseEntity<DemandInfo>>compose())
                .subscribe(new BaseObserver<DemandInfo>(context) {
                    @Override
                    protected void onHandleSuccess(DemandInfo demandInfo) {
                        onShowMyDemandListener.OnSearchSuccess(demandInfo, showMyDemandBody.getPage());
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        onShowMyDemandListener.OnSearchError(msg);
                    }
                });
    }
}
