package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.GetCodeBody;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.request.SingInBody;
import com.project.lx.baseproject.bean.responses.ChangePwdForCodeInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ISingInModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnChangePwdForCodeListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnGetCodeListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnLoginListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSingInListener;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 10:10
 * @company:
 * @email: lx802315@163.com
 */
public class SingInModeImpl implements ISingInModel {

    Context context;

    public SingInModeImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void getCode(GetCodeBody getCodeBody, final OnGetCodeListener onGetCodeListener) {
        if (context != null) {
            Observable<BaseEntity> observable = RetrofitFactory.getInstance().getCode(getCodeBody);
            observable.compose(RxSchedulers.<BaseEntity>compose())
                    .subscribe(new BaseObserver(context) {
                        @Override
                        public void onNext(Object value) {
                            if (((BaseEntity) value).isSuccess()) {
                                onGetCodeListener.onGetCodeSuccess((BaseEntity) value);
                            } else {
                                onGetCodeListener.onGetCodeError(((BaseEntity) value).getMsg());
                            }
                        }

                        @Override
                        protected void onHandleSuccess(Object o) {
                            onGetCodeListener.onGetCodeSuccess((BaseEntity) o);
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onGetCodeListener.onGetCodeError(msg);
                        }
                    });
        }
    }

    @Override
    public void singIn(SingInBody singInBody, final OnSingInListener onSingInListener) {
        if (context != null) {
            Observable<BaseEntity> observable = RetrofitFactory.getInstance().singIn(singInBody);
            observable.compose(RxSchedulers.<BaseEntity>compose())
                    .subscribe(new BaseObserver(context) {
                        @Override
                        public void onNext(Object value) {
                            if (((BaseEntity) value).isSuccess()) {
                                onSingInListener.onSingInSuccess((BaseEntity) value);
                                LogUtils.print(((BaseEntity) value).getResponse().toString());
                            } else {
                                onSingInListener.onSingInError(((BaseEntity) value).getMsg());
                            }
                        }

                        @Override
                        protected void onHandleSuccess(Object o) {

                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onSingInListener.onSingInError(msg);
                            LogUtils.print(msg);
                        }
                    });
        }
    }

    @Override
    public void login(LoginBody loginBody, final OnLoginListener onLoginListener) {
        if (context != null) {
            Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().login(loginBody);
            observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                    .subscribe(new BaseObserver<UserInfo>(context) {
                        @Override
                        protected void onHandleSuccess(UserInfo userInfo) {
                            onLoginListener.onLoginSuccess(userInfo);
                            LogUtils.print(userInfo.toString());
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onLoginListener.onLoginError(msg);
                            LogUtils.print(msg);
                        }
                    });
        }
    }

    @Override
    public void changePwdForCode(SingInBody singInBody, final OnChangePwdForCodeListener onChangePwdForCodeListener) {
        if (context != null) {
            Observable<BaseEntity<ChangePwdForCodeInfo>> observable = RetrofitFactory.getInstance().changePwdForCode(singInBody);
            observable.compose(RxSchedulers.<BaseEntity<ChangePwdForCodeInfo>>compose())
                    .subscribe(new BaseObserver<ChangePwdForCodeInfo>(context) {
                        @Override
                        protected void onHandleSuccess(ChangePwdForCodeInfo changePwdForCodeInfo) {
                            onChangePwdForCodeListener.onChangePwdForCodeSuccess(changePwdForCodeInfo);
                            LogUtils.print("获取成功" + changePwdForCodeInfo.toString());
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            onChangePwdForCodeListener.onChangePwdForCodeError(msg);
                            LogUtils.print("获取失败" + msg);
                        }
                    });
        }
    }
}
