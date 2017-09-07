package com.xiujichuanmei.a23_haoxin.mvp.contract;

import android.widget.EditText;
import android.widget.TextView;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;

/**
 * @author: X_Meteor
 * @description: 发布信件契约类
 * @version: V_1.0.0
 * @date: 2017/6/21 9:14
 * @company:
 * @email: lx802315@163.com
 */
public class IReleaseLetterContract {
    public interface IReleaseLetterView extends BaseView<IReleaseLetterPresenter> {

        EditText getTitles();

        String getLetterId();

        EditText getNumber();

        String  getAdreessId();

        String getTopicId();

        /**
         * Toast数据
         *
         * @param msg
         */
        void showMsg(String msg);

    }

    public interface IReleaseLetterPresenter extends BasePresenter<IReleaseLetterView> {
        void release();
    }
}
