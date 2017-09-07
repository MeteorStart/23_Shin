package com.project.lx.baseproject.constants;


import com.project.lx.baseproject.R;

/**
 * Created by Administrator on 2016/9/20.
 */
public class

Constants {

    //是否打印log
    public static final boolean isShowLog = true;
    //Log的TAG
    public static final String TAG = "X_Meteor";
    //程序是否出现异常
    public static final boolean isCollectException = false;

    //首次引导页指示器图片(0为选中状态，1为非选中状态）
    public static final int[] app_start_icon = {R.drawable.page_indicator_focused, R.drawable.page_indicator};
    //首次引导页图片地址
    public static final String [] app_start_img_res = {
            "http://img2.3lian.com/2014/f4/100/d/10.jpg", "http://img2.3lian.com/2014/f4/100/d/11.jpg", "http://img2.3lian.com/2014/f4/100/d/12.jpg"};
    //地图缩放级别
    public static final float MAP_ZOOM = 13.5f;
    //百度地图类型值
    public static final int BAIDU_MAP_TYPE = 0;
    //高德地图类型值
    public static final int GAODE_MAP_TYPE = 1;
    //当前位置:省
    public static final String ADREESS_PROVINCE = "Province";
    //当前位置:市
    public static final String ADREESS_CITY = "City";
    //当前位置:区
    public static final String ADREESS_DISTRICT = "District";
    //百度地图包名
    public static final String BAIDU_MAP_NAME = "com.baidu.BaiduMap";
    //高德地图类包名
    public static final String GAODE_MAP_NAME = "com.autonavi.minimap";
    //设备类型2代表Android
    public static final int DEVICE_TYPE = 2;
    //商店id
    public static final String SHOP_ID = "shop_id";
    //商店名
    public static final String SHOP_NAME = "shop_name";
    //网络访问根地址
//    public static final String BASE_URL = "http://192.168.1.107:8080/letter/";
    public static final String BASE_URL = "http://47.92.2.216:8080/letter/";
    //分享地址
    public static final String SHARE_URL = "http://47.92.2.216:8080/letter/share/letters.html?id=";
    //用户UserToken
    public static final String USER_TOKNE = "User_Token";
    //判断用户是否需要登录
    public static final String IS_NEED_LOGIN = "IS_NEED_LOGIN";

    //用户关注
    public static final String ATTENTION = "user_attention";
    //用户关注type
    public static final int USER_ATTENTION = 1;
    //用户被关注type
    public static final int USER_BE_ATTENTION = 2;

    //首页Type 1 为找信  2为等信
    public static final String HOME_TYPE = "home_type";

    //用户找信类型 1预约 2发出 3 收到
    public static final int USER_FIND_LETTER_SUB = 1;
    public static final int USER_FIND_LETTER_SEND = 2;
    public static final int USER_FIND_LETTER_RECEIVE = 3;

    //用户等信类型 1发起 2完成
    public static final int USER_WAIT_LETTER_START = 1;
    public static final int USER_WAIT_LETTER_FINISH = 2;

    //信件状态 0：未预约  1：已预约  2：中转  3：完成  4：销毁
    public static final int LETTER_STATE_WEIYUYUE = 0;
    public static final int LETTER_STATE_YIYUYUE = 1;
    public static final int LETTER_STATE_ZHONGZHUAN = 2;
    public static final int LETTER_STATE_WANCHENG = 3;
    public static final int LETTER_STATE_XIAOHUI = 4;

    //历史搜索
    public static final String HISTORY_SEARCH = "history_search";

    //登录类型
    public static final int OTHER_LOGIN_WEIXIN = 1;
    public static final int OTHER_LOGIN_QQ = 2;
    public static final int OTHER_LOGIN_WEIBO = 3   ;
}
