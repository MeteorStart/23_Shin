package com.xiujichuanmei.a23_haoxin.mvp.contract;

import android.widget.EditText;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;
import com.project.lx.baseproject.widget.PowerfulEditText;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 22:14
 * @company:
 * @email: lx802315@163.com
 */
public class ISingInContract {
    public interface ISingInView extends BaseView<ISingInPresenter>{

        PowerfulEditText getmActSingInPhoneNumber();

        PowerfulEditText getmActSingInPhonePassword();

        EditText getmActSingInPhoneCode();

    }
    public interface ISingInPresenter extends BasePresenter<ISingInView>{
        /**
         * 获取验证码
         */
        void getCode(int type);

        /**
         * 注册
         */
        void singIn();

        /**
         * 通过短信验证码修改密码
         */
        void changePwdForCode();
    }


}
