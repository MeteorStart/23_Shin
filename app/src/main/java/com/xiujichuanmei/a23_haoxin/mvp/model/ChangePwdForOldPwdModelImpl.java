package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ChangePwForOldPwdBody;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IChangePwdModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnChangePwdForOldPwdListener;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/16 9:58
 * @company:
 * @email: lx802315@163.com
 */
public class ChangePwdForOldPwdModelImpl implements IChangePwdModel {

    Context context;

    public ChangePwdForOldPwdModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void changePwd(ChangePwForOldPwdBody changePwForOldPwdBody, final OnChangePwdForOldPwdListener onChangePwdForOldPwdListener) {
        if (context != null) {
            Observable<BaseEntity> observable = RetrofitFactory.getInstance().changePwd(changePwForOldPwdBody);
            observable.compose(RxSchedulers.<BaseEntity>compose())
                    .subscribe(new BaseObserver(context) {
                        @Override
                        public void onNext(Object value) {
                            if (((BaseEntity) value).isSuccess()) {
                                onChangePwdForOldPwdListener.onChangePwdForOldPwdSuccess((BaseEntity) value);
                            } else {
                                onChangePwdForOldPwdListener.onChangePwdForOldPwdError(((BaseEntity) value).getMsg());
                            }
                        }

                        @Override
                        protected void onHandleSuccess(Object o) {
                            onChangePwdForOldPwdListener.onChangePwdForOldPwdSuccess((BaseEntity) o);
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onChangePwdForOldPwdListener.onChangePwdForOldPwdError(msg);
                        }
                    });
        }
    }
}
