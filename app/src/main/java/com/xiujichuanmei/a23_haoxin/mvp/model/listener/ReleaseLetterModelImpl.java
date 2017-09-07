package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ReleaseBody;
import com.project.lx.baseproject.util.RxSchedulers;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 9:40
 * @company:
 * @email: lx802315@163.com
 */
public class ReleaseLetterModelImpl implements IReleaseLetterModel {

    Context context;

    public ReleaseLetterModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void release(ReleaseBody releaseBody, final OnReleaseListener onReleaseListener) {
        Observable<BaseEntity> observable = RetrofitFactory.getInstance().releaseLetter(releaseBody);
        observable.compose(RxSchedulers.<BaseEntity>compose())
                .subscribe(new BaseObserver(context) {
                    @Override
                    public void onNext(Object value) {
                        if (value instanceof BaseEntity){
                            if (((BaseEntity) value).isSuccess()){
                                onHandleSuccess(value);
                            }else {
                                onHandleError(((BaseEntity) value).getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onHandleSuccess(Object o) {
                        onReleaseListener.OnSearchSuccess((BaseEntity) o);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        onReleaseListener.OnSearchError(msg);
                    }
                });
    }
}
