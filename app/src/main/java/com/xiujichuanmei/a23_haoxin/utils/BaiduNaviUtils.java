package com.xiujichuanmei.a23_haoxin.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;

import java.net.URISyntaxException;

/**
 * @author: X_Meteor
 * @description: 百度地图跳转工具类
 * @version: V_1.0.0
 * @date: 2017/6/27 22:25
 * @company:
 * @email: lx802315@163.com
 */
public class BaiduNaviUtils {

    private static CoordType mCoordType;
    /**
     * 启动百度地图导航(Native)
     */
    public static void startNavi(LatLng start, LatLng end, String startName, String endName, Context context) {

        mCoordType = SDKInitializer.getCoordType();//获取全局设置的坐标类型
        SDKInitializer.setCoordType(CoordType.BD09LL);
//        SDKInitializer.setCoordType(CoordType.GCJ02);

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(start).endPoint(end)
                .startName(startName).endName(endName);

        try {
            BaiduMapNavigation.openBaiduMapNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图导航(Web)
     */
    public static void startWebNavi(LatLng start, LatLng end, String startName, String endName, Context context) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(start).endPoint(end)
                .startName(startName).endName(endName);

        BaiduMapNavigation.openWebBaiduMapNavi(para, context);
    }

    /**
     * 启动百度地图步行导航(Native)
     */
    public static void startWalkingNavi(LatLng start, LatLng end, String startName, String endName, Context context) {

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(start).endPoint(end);

        try {
            BaiduMapNavigation.openBaiduMapWalkNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图步行AR导航(Native)
     */
    public static void startWalkingNaviAR(LatLng start, LatLng end, String startName, String endName, Context context) {

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(start).endPoint(end);

        try {
            BaiduMapNavigation.openBaiduMapWalkNaviAR(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图骑行导航(Native)
     */
    public static void startBikingNavi(LatLng start, LatLng end, String startName, String endName, Context context) {

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(start).endPoint(end);

        try {
            BaiduMapNavigation.openBaiduMapBikeNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图Poi周边检索
     */
    public static void startPoiNearbySearch(LatLng start, LatLng end, String startName, String endName, Context context) {
        PoiParaOption para = new PoiParaOption()
                .key("c")
                .center(start)
                .radius(2000);

        try {
            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图Poi详情页面
     */
    public static void startPoiDetails(LatLng start, LatLng end, String startName, String endName, Context context) {
        PoiParaOption para = new PoiParaOption().uid("65e1ee886c885190f60e77ff"); // 天安门

        try {
            BaiduMapPoiSearch.openBaiduMapPoiDetialsPage(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图POI全景页面
     */
    public static void startPoiPanoShow(LatLng start, LatLng end, String startName, String endName, Context context) {
        try {
            BaiduMapPoiSearch.openBaiduMapPanoShow(endName, context); // 天安门
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图步行路线规划
     */
    public static void startRoutePlanWalking(LatLng start, LatLng end, String startName, String endName, Context context) {

        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(start)
                .startName(startName)
                .endPoint(end)
                .endName(endName);

//      RouteParaOption para = new RouteParaOption()
//      .startName("天安门").endName("百度大厦");

//      RouteParaOption para = new RouteParaOption()
//      .startPoint(pt_start).endPoint(pt_end);

        try {
            BaiduMapRoutePlan.openBaiduMapWalkingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图驾车路线规划
     */
    public static void startRoutePlanDriving(LatLng start, LatLng end, String startName, String endName, Context context) {

        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(start)
                .startName(startName)
                .endPoint(end)
                .endName(endName);

//        RouteParaOption para = new RouteParaOption()
//                .startName("天安门").endName("百度大厦");

//        RouteParaOption para = new RouteParaOption()
//        .startPoint(pt_start).endPoint(pt_end);

        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    /**
     * 启动百度地图公交路线规划
     */
    public static void startRoutePlanTransit(LatLng start, LatLng end, String startName, String endName, Context context) {
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(start)
                .startName(startName)
                .endPoint(end)
                .endName(endName)
                .busStrategyType(RouteParaOption.EBusStrategyType.bus_recommend_way);

//        RouteParaOption para = new RouteParaOption()
//                .startName("天安门").endName("百度大厦").busStrategyType(EBusStrategyType.bus_recommend_way);

//        RouteParaOption para = new RouteParaOption()
//        .startPoint(ptStart).endPoint(ptEnd).busStrategyType(EBusStrategyType.bus_recommend_way);

        try {
            BaiduMapRoutePlan.openBaiduMapTransitRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(context);
        }

    }

    public static void onDestroy(Context context) {
        BaiduMapNavigation.finish(context);
        BaiduMapRoutePlan.finish(context);
        BaiduMapPoiSearch.finish(context);
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public static void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(context);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    public static LatLng baiduStringToLatLng(String s) {
        /** "coordinate":"113.609788,34.797935" */
        String[] item = s.split(",");
        LatLng latLng = null;
        if (item.length > 1) {
            latLng = new LatLng(Double.valueOf(item[0]), Double.valueOf(item[1]));
        }
        return latLng;
    }

    public static LatLng stringToLatLng(String s) {
        /** "coordinate":"113.609788,34.797935" */
        String[] item = s.split(",");
        LatLng latLng = null;
        if (item.length > 1) {
            latLng = new LatLng(Double.valueOf(item[1]), Double.valueOf(item[0]));
        }
        return latLng;
    }

    public static String latLngToString(LatLng latLng) {
        /** "coordinate":"113.609788,34.797935" */
        StringBuffer s = new StringBuffer();
        s.append(latLng.longitude);
        s.append(",");
        s.append(latLng.latitude);
        return s.toString();
    }

    public static Intent openGaode(LatLng latLng) {
        Intent intent = null;
        try {
            intent = Intent.getIntent("androidamap://navi?sourceApplication=我的位置&poiname=我的目的地&lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&dev=0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return intent;
    }

}
