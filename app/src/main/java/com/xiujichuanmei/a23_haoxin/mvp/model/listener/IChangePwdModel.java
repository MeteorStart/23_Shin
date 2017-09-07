package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.ChangePwForOldPwdBody;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/16 9:52
 * @company:
 * @email: lx802315@163.com
 */
public interface IChangePwdModel extends BaseModel {
    void changePwd(ChangePwForOldPwdBody changePwForOldPwdBody, OnChangePwdForOldPwdListener onChangePwdForOldPwdListener);
}
