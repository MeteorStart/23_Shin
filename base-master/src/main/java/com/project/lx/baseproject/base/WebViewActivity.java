package com.project.lx.baseproject.base;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.lx.baseproject.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebViewActivity extends BaseActivity {
    private WebView mWebView;

    String Url;
    String Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Url = getIntent().getStringExtra("Url");
        Data = getIntent().getStringExtra("Data");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_web_view);
    }


    public String getHtmlString(String string) {

        String html = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>"
                + "<body>"
                + string
                + "</body></html>";

        return html;
    }

    public void initView() {
        // TODO Auto-generated method stub
        mWebView = (WebView) findViewById(R.id.web_webview);
        mWebView.requestFocusFromTouch();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        /**覆盖调用系统或自带浏览器行为打开网页*/
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
        /**判断加载过程*/
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }
            }
        });
    }

    @Override
    public void initData() {
        if (Data != null && Data.length() > 0) {
            mWebView.loadDataWithBaseURL("about:blank", getHtmlString(Data), "text/html", "utf-8", null);
        }
        if (Url != null && Url.length() > 0) {
            mWebView.loadUrl(Url);
        }
    }

    public void initListener() {
        // TODO Auto-generated method stub
        /**加载javascript*/
        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                new Handler().post(new Runnable() {
                    public void run() {
                        mWebView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");

        /**打开页面时， 自适应屏幕*/
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);

        /**便页面支持缩放*/
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        /**关闭缩放按钮*/
        webSettings.setDisplayZoomControls(false);
    }

}
