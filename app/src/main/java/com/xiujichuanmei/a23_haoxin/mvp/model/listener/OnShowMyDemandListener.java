package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.bean.responses.DemandInfo;
import com.project.lx.baseproject.bean.responses.LetterInfo;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 9:36
 * @company:
 * @email: lx802315@163.com
 */
public interface OnShowMyDemandListener {
    void OnSearchSuccess(DemandInfo request, int page);

    void OnSearchError(String msg);
}
