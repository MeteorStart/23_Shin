package com.project.lx.baseproject.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.CrashHandlerUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: Meteor
 * @description: 自定义的Application类
 * @version:
 * @date: 2016/12/28 0028 15:50
 * @company:
 * @email: lx802315@163.com
 */
public class MyApplication extends Application {

    //定义一个标记
    private static String TAG;

    //当前的用户
    private static UserInfo currentUser;

    //用于存放倒计时时间
    public static Map<String, Long> map;

    /**
     * 维护一个全局的context对象
     */
    public Context context;

    //用于存放数据
    private static Map<String, Object> datas = new HashMap<>();
    //单例模式
    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    /**
     * 获取当前的用户对象
     *
     * @return
     */
    public UserInfo getCurrentUser() {
        UserInfo user = currentUser;
        if (user != null) {
            return user;
        }
        return null;
    }

    public void setCurrentUser(UserInfo currentUser) {
        this.currentUser = currentUser;
    }

    public static Object getDatas(String key, boolean delFlag) {
        if (delFlag) {
            return datas.remove(key);
        }
        return datas.get(key);
    }

    public static Object putDatas(String key, Object value) {
        return datas.put(key, value);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        //由于Application类本身已经单例，所以直接按以下处理即可。
        myApplication = this;
        context = getApplicationContext();

        //友盟debug模式，正式发布后关闭
        Config.DEBUG = true;
        //友盟初始化
        UMShareAPI.get(this);

        {
            PlatformConfig.setWeixin("wxeb0045dc7f06d2f5", "78a03ea0ef9e6af1dd8c130015874e68");
            PlatformConfig.setQQZone("1105971831", "DDutIJspYgYrTvdP");
            PlatformConfig.setSinaWeibo("2488327001", "`8203683873da603bd1b947d9e37ed608", "http://sns.whalecloud.com/sina2/callback");
        }

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //全局异常处理
        if (Constants.isCollectException) {
            CrashHandlerUtils crashHandler = CrashHandlerUtils.getInstance();
            crashHandler.init(getApplicationContext());
        }

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
