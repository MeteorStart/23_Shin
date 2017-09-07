package com.project.lx.baseproject.util;

import com.google.gson.Gson;
import com.project.lx.baseproject.bean.request.LoginBody;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 13:36
 * @company:
 * @email: lx802315@163.com
 */
public class JsonUtils {

     public static RequestBody getJsonBody(Object object){
         Gson gson = new Gson();
         String jsonObject = gson.toJson(object);
         RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
         return body;
     }
}
