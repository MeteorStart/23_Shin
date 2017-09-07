package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.LoginBody;

/**
 * @author: X_Meteor
 * @description: 登录获取数据接口
 * @version: V_1.0.0
 * @date: 2017/6/14 9:15
 * @company:
 * @email: lx802315@163.com
 */
public interface ILoginModel extends BaseModel {
    /**
     * 登录请求
     *
     * @param loginBody 请求体JSON字符串
     * @param onLoginListener 回调接口
     */
    void login(LoginBody loginBody, OnLoginListener onLoginListener);
}
