package com.xiujichuanmei.a23_haoxin.mvp.model;

import android.content.Context;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ILoginModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnLoginListener;

import io.reactivex.Observable;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 9:32
 * @company:
 * @email: lx802315@163.com
 */
public class LoginModelImpl implements ILoginModel {

    Context context;

    public LoginModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setContent(Context context) {
        this.context = context;
    }

    @Override
    public void login(LoginBody loginBody, final OnLoginListener onLoginListener) {
        if (context != null){
            Observable<BaseEntity<UserInfo>> observable= RetrofitFactory.getInstance().login(loginBody);
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
}
