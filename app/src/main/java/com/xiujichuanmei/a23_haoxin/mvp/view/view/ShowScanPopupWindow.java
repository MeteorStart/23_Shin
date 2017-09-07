package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.project.lx.baseproject.util.Base64Utils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 * @author: X_Meteor
 * @description: 二维码展示弹窗
 * @version: V_1.0.0
 * @date: 2017/6/7 9:55
 * @company:
 * @email: lx802315@163.com
 */
public class ShowScanPopupWindow extends PopupWindow {

    Context context;
    private View mPopView;

    Object obj;

    TextView name, number;
    ImageView avatar, sacn;

    StringBuffer str;

    public ShowScanPopupWindow(Context context, LetterInfo.LetterBean letterBean) {
        super(context);
        this.context = context;
        obj = letterBean;
        str = new StringBuffer();
        init(context);
        setPopupWindow();

    }

    public ShowScanPopupWindow(Context context, ShowUserInformationInfo.LetterBean letterBean) {
        super(context);
        this.context = context;
        obj = letterBean;
        str = new StringBuffer();
        init(context);
        setPopupWindow();

    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.pop_show_scan, null);
        name = (TextView) mPopView.findViewById(R.id.tv_pop_show_scan_username);
        number = (TextView) mPopView.findViewById(R.id.tv_pop_show_scan_number);
        avatar = (ImageView) mPopView.findViewById(R.id.imv_pop_show_scan_avatar);
        sacn = (ImageView) mPopView.findViewById(R.id.imv_pop_show_scan_scan);
        if (obj instanceof LetterInfo.LetterBean) {
            LetterInfo.LetterBean item = (LetterInfo.LetterBean) obj;

            if (item.getOwnerName() != null) {
                name.setText(LetterUtils.setNickName(item.getOwnerName()));
//                str.append(item.getOwnerName() + ",");
//                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName() + ",");
            } else {
//                str.append(" " + ",");
//                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName() + ",");
            }
            if (item.getLetterNumber() != null) {
                number.setText("信件编号:" + item.getLetterNumber());
//                str.append(item.getLetterNumber() + ",");
            } else {
//                str.append(" " + ",");
            }
            if (item.getShopName() != null) {
//                str.append(item.getShopName() + ",");
            } else {
//                str.append(" " + ",");
            }

            if (item.getLetterId() != null) {
                str.append(item.getLetterId() + ",");
                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId());
            } else {
                str.append(" " + ",");
                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId());
            }
//
            if (item.getOwenerHeadImg() != null) {
//                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getHeadImg());
//                str.append(item.getOwenerHeadImg());
            } else {
//                str.append("avatar");
            }

            Glide.with(context).
                    load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .into(avatar);

//            Bitmap bitmap = encodeAsBitmap(Base64Utils.getBase64(str.toString()));
            Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
            Bitmap bitmap = null;

            try {
                bitmap = createCode(str.toString(), logoBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            sacn.setImageBitmap(bitmap);

        } else if (obj instanceof ShowUserInformationInfo.LetterBean) {
            ShowUserInformationInfo.LetterBean item = (ShowUserInformationInfo.LetterBean) obj;

            if (item.getOwnerName() != null) {
                name.setText(LetterUtils.setNickName(item.getOwnerName()));
//                str.append(item.getOwnerName() + ",");
//                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName() + ",");
            }
            if (item.getLetterNumber() != null) {
                number.setText("信件编号:" + item.getLetterNumber());
//                str.append(item.getLetterNumber() + ",");
            }
            if (item.getShopName() != null) {
//                str.append(item.getShopName() + ",");
            }
            if (item.getLetterId() != null) {
                str.append(item.getLetterId() + ",");
//                str.append(item.getLetterState() + ",");
                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId());
            } else {
                str.append(" " + ",");
                str.append(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId());
            }
            if (item.getOwenerHeadImg() != null) {
//                str.append(item.getOwenerHeadImg());
            }
            Glide.with(context).
                    load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .into(avatar);

            Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
            Bitmap bitmap = null;

            try {
                bitmap = createCode(str.toString(), logoBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            sacn.setImageBitmap(bitmap);

        }
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.center_pop_anim_style);// 设置动画
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.getRootView().getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    //生成不带Logo的二维码
    public Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
//            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            result = multiFormatWriter.encode(new String(str.getBytes("UTF-8"), "ISO-8859-1"),
                    BarcodeFormat.QR_CODE, 400, 400);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 黑点颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 正方形二维码宽度
     */
    private static final int CODE_WIDTH = 440;
    /**
     * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
     */
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 5;
    /**
     * LOGO宽度值,最小不能小于二维码10%宽度值,小于影响Logo与二维码的整体搭配
     */
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 5;

    /**
     * 生成带LOGO的二维码
     */

    public Bitmap createCode(String content, Bitmap logoBitmap)
            throws WriterException {
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();
        int logoHaleWidth = logoWidth >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        int logoHaleHeight = logoHeight >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();
        /*
         * 给的源码是,由于CSDN上传的资源不能改动，这里注意改一下
         * float sx = (float) 2*logoHaleWidth / logoWidth;
         * float sy = (float) 2*logoHaleHeight / logoHeight;
         */
        float sx = (float) logoHaleWidth / logoWidth;
        float sy = (float) logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);
        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别,H为最高
        hints.put(EncodeHintType.MAX_SIZE, LOGO_WIDTH_MAX);// 设置图片的最大值
        hints.put(EncodeHintType.MIN_SIZE, LOGO_WIDTH_MIN);// 设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 2);//设置白色边距值
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_WIDTH, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            /*
                 * 取值范围,可以画图理解下
                 * halfW + newLogoWidth / 2 - (halfW - newLogoWidth / 2) = newLogoWidth
                 * halfH + newLogoHeight / 2 - (halfH - newLogoHeight) = newLogoHeight
                 */
                if (x > halfW - newLogoWidth / 2 && x < halfW + newLogoWidth / 2
                        && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
                    /*
                     *  记录图片每个像素信息
                     *  halfW - newLogoWidth / 2 < x < halfW + newLogoWidth / 2
                     *  --> 0 < x - halfW + newLogoWidth / 2 < newLogoWidth
                     *   halfH - newLogoHeight / 2  < y < halfH + newLogoHeight / 2
                     *   -->0 < y - halfH + newLogoHeight / 2 < newLogoHeight
                     *   刚好取值newLogoBitmap。getPixel(0-newLogoWidth,0-newLogoHeight);
                     */
                    pixels[y * width + x] = newLogoBitmap.getPixel(
                            x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                } else {
                    pixels[y * width + x] = matrix.get(x, y) ? BLACK : WHITE;// 设置信息
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
