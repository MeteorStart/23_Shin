package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.ReleaseBody;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 9:33
 * @company:
 * @email: lx802315@163.com
 */
public interface IReleaseLetterModel extends BaseModel {
    void release(ReleaseBody releaseBody, OnReleaseListener onReleaseListener);
}
