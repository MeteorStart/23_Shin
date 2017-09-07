package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseEntity;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 9:35
 * @company:
 * @email: lx802315@163.com
 */
public interface OnReleaseListener {
    void OnSearchSuccess(BaseEntity request);
    void OnSearchError(String msg);
}
