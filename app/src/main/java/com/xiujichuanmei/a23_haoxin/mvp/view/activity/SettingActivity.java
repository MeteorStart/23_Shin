package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.EditNickNameBody;
import com.project.lx.baseproject.bean.request.ShowUserInfoBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.DataCleanManager;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @name: SettingActivity
 * @description: 设置页面
 * @version: 1.0
 * @date: 2017/6/13 14:52
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_setting_clear)
    TextView tvActSettingClear;
    @BindView(R.id.rel_act_setting_avatar)
    RelativeLayout relActSettingAvatar;
    @BindView(R.id.rel_act_setting_change_password)
    RelativeLayout relActSettingChangePassword;
    @BindView(R.id.rel_act_setting_msg)
    RelativeLayout relActSettingMsg;
    @BindView(R.id.rel_act_setting_clear)
    RelativeLayout relActSettingClear;
    @BindView(R.id.rel_act_setting_help)
    RelativeLayout relActSettingHelp;
    @BindView(R.id.rel_act_setting_enter)
    RelativeLayout relActSettingEnter;
    @BindView(R.id.rel_act_setting_about)
    RelativeLayout relActSettingAbout;
    @BindView(R.id.btn_act_setting)
    Button btnActSetting;
    @BindView(R.id.rel_act_setting_change_name)
    RelativeLayout relActSettingChangeName;
    @BindView(R.id.imv_act_setting_avatar)
    ImageView imvActSettingAvatar;
    @BindView(R.id.tv_act_setting_avatar)
    TextView tvActSettingAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.imv_right)
    ImageView imvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        textActTop.setText("设置");
        imvActTopRight.setVisibility(View.GONE);
        if (MyApplication.getInstance().getCurrentUser()!= null){
            tvUserName.setText(MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName() + "");
        }
        Glide.with(this).load(MyApplication.getInstance().getCurrentUser().getUserInfor().getHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(this))
                .into(imvActSettingAvatar);
    }

    @Override
    public void initData() {
        String datasize = "";
        try {
            // 查看缓存的大小
            datasize = DataCleanManager
                    .getTotalCacheSize(getApplicationContext());
            // Log.e("缓存大小",
            // DataCleanManager.getTotalCacheSize(MySettingsActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
            datasize = "0MB";
        }
        tvActSettingClear.setText(datasize);
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.imv_act_top_back, R.id.rel_act_setting_change_name, R.id.rel_act_setting_avatar, R.id.rel_act_setting_change_password, R.id.rel_act_setting_msg, R.id.rel_act_setting_clear, R.id.rel_act_setting_help, R.id.rel_act_setting_enter, R.id.rel_act_setting_about, R.id.btn_act_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.rel_act_setting_avatar:
//                ToastUtils.showToast(this, "跳转头像修改");
                initPopuwindow();
                break;
            case R.id.rel_act_setting_change_name:
                intent.setClass(this, ChangeUserNameActivity.class);
                intent.putExtra("name",tvUserName.getText().toString());
                startActivity(intent);
                break;
            case R.id.rel_act_setting_change_password:
                intent.setClass(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_act_setting_msg:
                intent.setClass(this, SettingMsgActivity.class);
                startActivity(intent);

                break;
            case R.id.rel_act_setting_clear:
                try {
                    DataCleanManager.clearAllCache(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "清理完成",
                            Toast.LENGTH_SHORT).show();
                    tvActSettingClear.setText("0MB");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("清理异常", e.getMessage());
                    Toast.makeText(getApplicationContext(), "清理失败",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rel_act_setting_help:
                intent.setClass(this, HelpActivity.class);
                startActivity(intent);

                break;
            case R.id.rel_act_setting_enter:
                intent.setClass(this, StoreEnterActivity.class);
                startActivity(intent);

                break;
            case R.id.rel_act_setting_about:
//                ToastUtils.showToast(this, "跳转关于页面");
                break;
            case R.id.btn_act_setting:
                initSingUpPopuwindow();
                break;
        }
    }

    /**
     * 打开退出登录pop
     */
    public void initSingUpPopuwindow() {
        backgroundAlpha(0.3f);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_sing_up_window, null);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //获取屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        popupWindow.setWidth(dm.widthPixels);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //显示位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new poponDismissListener());

        camera = (Button) view.findViewById(R.id.btn_picture_from_libary);
        clear = (Button) view.findViewById(R.id.btn_picture_from_cancel);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
                singUpLogin();
                clearActicity();
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
            }
        });
    }

    public void singUpLogin() {
        MyApplication.getInstance().setCurrentUser(null);
        SharedPreferencesUtils.putString(this, Constants.USER_TOKNE, "");
    }


    /**
     * 获取照片相关
     */
    private PopupWindow popupWindow;
    Button camera, album, clear;

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CROP_PHOTO = 3;

    private String cachPath;

    private File cacheFile;
    private File cameraFile;

    private Uri imageUri;

    //动态获取权限监听
    private static PermissionListener mListener;

    /**
     * 打开拍照pop
     */
    public void initPopuwindow() {
        backgroundAlpha(0.3f);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_window, null);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //获取屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        popupWindow.setWidth(dm.widthPixels);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //显示位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new poponDismissListener());

        cachPath = getDiskCacheDir(this) + "/crop_image.jpg";
        cacheFile = getCacheFile(new File(getDiskCacheDir(this)), "crop_image.jpg");

        camera = (Button) view.findViewById(R.id.btn_picture_from_camera);
        album = (Button) view.findViewById(R.id.btn_picture_from_libary);
        clear = (Button) view.findViewById(R.id.btn_picture_from_cancel);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
                takePhotoForCamera();
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
                takePhotoForAlbum();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                popupWindow.dismiss();
            }
        });
    }

    private void takePhotoForAlbum() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openAlbum();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //没有获取到权限，什么也不执行，看你心情
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    private void takePhotoForCamera() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openCamera();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //有权限被拒绝，什么也不做好了，看你心情
            }
        });

    }

    private void openCamera() {

        cameraFile = getCacheFile(new File(getDiskCacheDir(this)), "output_image.jpg");


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(cameraFile);
        } else {

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(SettingActivity.this, "com.xiujichuanmei.a23_haoxin.fileprovider", cameraFile);

        }
        // 启动相机程序
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {

                        // 将拍摄的照片显示出来

                        startPhotoZoom(cameraFile, 350);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            case CROP_PHOTO:
                //处理返回图片
                try {
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(cachPath))));
                        File image = new File(cachPath);
                        Glide.with(this)
                                .load(image)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                                .bitmapTransform(new GlideCircleTransform(this))
                                .into(imvActSettingAvatar);
//                        imvActSettingAvatar.setImageBitmap(bitmap);

                        //上传头像
                        upLoadImage(image);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    //上传头像
    public void upLoadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        Observable<BaseEntity> observable = RetrofitFactory.getInstance().uploadFile(body);
        observable.compose(RxSchedulers.<BaseEntity>compose())
                .subscribe(new BaseObserver(this) {
                    @Override
                    public void onNext(Object value) {
                        if (value instanceof BaseEntity) {
                            if (((BaseEntity) value).isSuccess()) {
                                onHandleSuccess(value);
                            } else {
                                onHandleError(((BaseEntity) value).getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onHandleSuccess(Object o) {
                        if (o instanceof BaseEntity) {
                            //上传成功后修该头像
                            String imageUrl = ((BaseEntity) o).getResponse().toString();
                            changeImage(imageUrl);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(SettingActivity.this, "上传失败" + msg);
                    }
                });
    }

    //修改头像
    public void changeImage(String imageUrl) {
        Observable<BaseEntity> observable = RetrofitFactory.getInstance().editUserImg(new EditNickNameBody(imageUrl, MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName(), 0, MyApplication.getInstance().getCurrentUser().getUserToken()));
        observable.compose(RxSchedulers.<BaseEntity>compose())
                .subscribe(new BaseObserver(this) {
                    @Override
                    public void onNext(Object value) {
                        if (value instanceof BaseEntity) {
                            if (((BaseEntity) value).isSuccess()) {
                                onHandleSuccess(value);
                            } else {
                                onHandleError(((BaseEntity) value).getMsg());
                            }

                        }
                    }

                    @Override
                    protected void onHandleSuccess(Object o) {
                        Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().showUserInfo(new ShowUserInfoBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
                        observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                                .subscribe(new BaseObserver<UserInfo>(SettingActivity.this) {
                                    @Override
                                    protected void onHandleSuccess(UserInfo userInfo) {
                                        ToastUtils.showToast(SettingActivity.this, "修改成功");
                                        MyApplication.getInstance().setCurrentUser(userInfo);
                                        dismissProcessDialog();
//                                        finish();
                                    }

                                    @Override
                                    protected void onHandleError(String msg) {
                                        LogUtils.print(msg);
                                        dismissProcessDialog();
                                    }
                                });
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(SettingActivity.this, "修改失败" + msg);
                        dismissProcessDialog();
                    }
                });
    }

    private File getCacheFile(File parent, String child) {
        // 创建File对象，用于存储拍照后的图片
        File file = new File(parent, child);

        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        String imagePath = uriToPath(uri);
//        displayImage(imagePath); // 根据图片路径显示图片

        Log.i("TAG", "file://" + imagePath + "选择图片的URI" + uri);
        startPhotoZoom(new File(imagePath), 350);
    }


    private String uriToPath(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            path = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
        Log.i("TAG", "file://" + imagePath + "选择图片的URI" + uri);
        startPhotoZoom(new File(imagePath), 350);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 剪裁图片
     */
    private void startPhotoZoom(File file, int size) {
        LogUtils.print(getImageContentUri(this, file) + "裁剪照片的真实地址");
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(getImageContentUri(this, file), "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    //andrpoid 6.0 需要写运行时权限
    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {

        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(SettingActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(SettingActivity.this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    public interface PermissionListener {

        /**
         * 成功获取权限
         */
        void onGranted();

        /**
         * 为获取权限
         *
         * @param deniedPermission
         */
        void onDenied(List<String> deniedPermission);

    }

    /**
     * 添加PopupWindow关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
