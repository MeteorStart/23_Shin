package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ReportOthersBody;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: ReportActivity
 * @description: 举报页面
 * @version: 1.0
 * @date: 2017/6/12 11:40
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ReportActivity extends BaseActivity {

    Intent intent;
    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_report_name)
    TextView tvActReportName;
    @BindView(R.id.edit_act_report)
    EditText editActReport;
    @BindView(R.id.btn_act_report)
    Button btnActReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_report);
    }

    @Override
    public void initView() {
        textActTop.setText("举报");
        imvActTopRight.setVisibility(View.GONE);

        tvActReportName.setText(intent.getStringExtra("ownerName"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        editActReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btnActReport.setEnabled(true);
                } else {
                    btnActReport.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.imv_act_top_back, R.id.btn_act_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_report:
                ReportOthersBody body = new ReportOthersBody(editActReport.getText().toString(), intent.getStringExtra("ownerId"), MyApplication.getInstance().getCurrentUser().getUserToken());
                Observable<BaseEntity> observable = RetrofitFactory.getInstance().reportOthers(body);
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
                                ToastUtils.showToast(ReportActivity.this, "举报成功");
                                finish();
                            }

                            @Override
                            protected void onHandleError(String msg) {
                                ToastUtils.showToast(ReportActivity.this, "举报失败" + msg);
                            }
                        });
                break;
        }
    }
}
