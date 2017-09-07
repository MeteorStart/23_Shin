package com.project.lx.baseproject.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/4/19 17:12
 * @company: 洛阳魅力文化传媒   http://www.fenglin.tv/
 * @email: lx802315@163.com
 */
public class PhoneUtils {
    /**
     * 获取设备ID
     *
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public static String getDeviceID(Context ctx) {
        String strResult = null;
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            strResult = telephonyManager.getDeviceId();
        }
        if (strResult == null) {
            strResult = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        // Log.e("设备token", strResult);
        return strResult;
    }
}
