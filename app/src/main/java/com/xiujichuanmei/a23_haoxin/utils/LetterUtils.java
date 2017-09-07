package com.xiujichuanmei.a23_haoxin.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 14:19
 * @company:
 * @email: lx802315@163.com
 */
public class LetterUtils {

    public static String setTitle(ShowShopInfo.LetterBean item) {
        String result = "";
        if (item.getLetterTitle() != null && item.getLetterTitle().length() > 0) {
            result = item.getLetterTitle();
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        } else if (item.getTopicName() != null && item.getTopicName().length() > 0) {
            result = item.getTopicName();
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        } else {
            result = "一封来自陌生人的信";
        }
        return result;
    }

    public static void setTitle(TextView textView, LetterInfo.LetterBean item) {
        if (item.getLetterTitle() != null && item.getLetterTitle().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_title);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getLetterTitle());
        } else if (item.getTopicName() != null && item.getTopicName().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_huati);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getTopicName());
        } else {
            textView.setCompoundDrawables(null, null, null, null);
            textView.setText("一封来自陌生人的信");
        }
    }

    public static void setTitle(TextView textView, ShopLetterInfo.LetterBean item) {
        if (item.getLetterTitle() != null && item.getLetterTitle().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_title);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getLetterTitle());
        } else if (item.getTopicName() != null && item.getTopicName().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_huati);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getTopicName());
        } else {
            textView.setCompoundDrawables(null, null, null, null);
            textView.setText("一封来自陌生人的信");
        }
    }

    public static void setTitle(TextView textView, ShowUserInformationInfo.LetterBean item) {
        if (item.getLetterTitle() != null && item.getLetterTitle().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_title);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getLetterTitle());
        } else if (item.getTopicName() != null && item.getTopicName().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_huati);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getTopicName());
        } else {
            textView.setCompoundDrawables(null, null, null, null);
            textView.setText("一封来自陌生人的信");
        }
    }

    public static void setTitle(TextView textView, ShowShopInfo.LetterBean item) {
        if (item.getLetterTitle() != null && item.getLetterTitle().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_title);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getLetterTitle());
        } else if (item.getTopicName() != null && item.getTopicName().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().context.getResources(), R.drawable.icon_xinjian_label_huati);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setText(item.getTopicName());
        } else {
            textView.setCompoundDrawables(null, null, null, null);
            textView.setText("一封来自陌生人的信");
        }
    }


    public static String setNickName(String nickName) {
        if (nickName != null && nickName.length() > 0) {
            return nickName;
        }
        if (MyApplication.getInstance().getCurrentUser() != null) {
            return MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName();
        }
        return "";
    }
}
