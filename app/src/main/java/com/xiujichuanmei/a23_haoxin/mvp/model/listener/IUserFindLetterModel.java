package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import android.content.Context;

import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 9:34
 * @company:
 * @email: lx802315@163.com
 */
public interface IUserFindLetterModel extends BaseModel {

    void getLetterList(ShowMyLetterBody showMyLetterBody, OnShowMyLetterListener onShowMyLetterListener);

    void getDemandList(ShowMyLetterBody showMyDemandBody, OnShowMyDemandListener onShowMyDemandListener);
}
