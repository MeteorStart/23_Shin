package com.xiujichuanmei.a23_haoxin.mvp.model.listener;

import com.project.lx.baseproject.base.BaseModel;
import com.project.lx.baseproject.bean.request.SearchBody;

/**
 * @author: X_Meteor
 * @description: 搜索相关
 * @version: V_1.0.0
 * @date: 2017/6/19 13:40
 * @company:
 * @email: lx802315@163.com
 */
public interface ISearchModel extends BaseModel {

    void getDatas(SearchBody searchBody, OnSearchLinster onSearchLinster);

}
