package com.xiujichuanmei.a23_haoxin.mvp.contract;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;

/**
 * @author: X_Meteor
 * @description: MainActivity 契约类、使view 和 Presenter 之前的方法清晰
 * @version: V_1.0.0
 * @date: 2017/5/26 9:56
 * @company:
 * @email: lx802315@163.com
 */
public class IMainContract {

    public interface IMainView extends BaseView<IMainPresenter>{

    }

    public interface IMainPresenter extends BasePresenter<IMainView>{

    }
}
