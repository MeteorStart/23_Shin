package com.xiujichuanmei.a23_haoxin.utils;

import android.content.Context;

import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 16:21
 * @company:
 * @email: lx802315@163.com
 */
public class HistoryUtils {
    public static void setHistory(Context context, String value) {
        Set list = SharedPreferencesUtils.getArray(context);
        list.add(value);
        SharedPreferencesUtils.putArray(context, list);
    }
}
