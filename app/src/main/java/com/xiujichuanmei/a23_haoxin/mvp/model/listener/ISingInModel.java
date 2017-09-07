package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.GetCodeBody;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.request.SingInBody;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 9:50
 * @company:
 * @email: lx802315@163.com
 */
public interface ISingInModel extends BaseModel{

    /**
     * 获取验证码
     *
     * @param getCodeBody
     * @param onGetCodeListener
     */
    void getCode(GetCodeBody getCodeBody, OnGetCodeListener onGetCodeListener);

    /**
     * 注册账号
     * @param singInBody
     * @param onSingInListener
     */
    void singIn(SingInBody singInBody, OnSingInListener onSingInListener);

    /**
     * 登录操作
     *
     * @param loginBody
     * @param onLoginListener
     */
    void login(LoginBody loginBody,OnLoginListener onLoginListener);

    /**
     * 通过手机验证码修改密码
     * @param singInBody
     * @param onChangePwdForCodeListener
     */
    void changePwdForCode(SingInBody singInBody,OnChangePwdForCodeListener onChangePwdForCodeListener);
}
