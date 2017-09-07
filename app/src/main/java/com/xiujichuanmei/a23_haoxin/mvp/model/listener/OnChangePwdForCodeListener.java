package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.bean.responses.ChangePwdForCodeInfo;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/4/19 16:11
 * @company:
 * @email: lx802315@163.com
 */
public interface OnChangePwdForCodeListener {
    void onChangePwdForCodeSuccess(ChangePwdForCodeInfo changePwdForCodeInfo);
    void onChangePwdForCodeError(String msg);
}
