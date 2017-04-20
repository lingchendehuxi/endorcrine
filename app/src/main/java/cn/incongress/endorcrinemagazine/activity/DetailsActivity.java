package cn.incongress.endorcrinemagazine.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.utils.ProgressWebView;

public class DetailsActivity extends AppCompatActivity {
    private int userState = 1;
    private ProgressWebView mOnlyWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView tv = (TextView) findViewById(R.id.activity_title);
        TextView title = (TextView) findViewById(R.id.details_title);
        tv.setText(getIntent().getStringExtra("notestitle"));
        mOnlyWebview = (ProgressWebView)findViewById(R.id.details_web);
        if(0 == userState){
            title.setText("登录后查看原文");
        }else if(1 == userState){
            title.setText("收藏");
        }
        initialWebViewSetting();
        loadUrl(Constants.TEST_SERVICE+Constants.DETAILS+"&notesId="+getIntent().getStringExtra("notesid")+"&userState="+userState);
//
    }

    private void loadUrl(String url) {
        mOnlyWebview.loadUrl(url);
    }

    /**
     * webview配置
     */
    @SuppressLint("JavascriptInterface")
    private void initialWebViewSetting() {
        //只有在系统版本号低于18的情况下才调用该方法
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mOnlyWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        }

        mOnlyWebview.getSettings().setAllowFileAccess(true);
        mOnlyWebview.getSettings().setLoadWithOverviewMode(true);
        mOnlyWebview.getSettings().setSupportZoom(true);
        mOnlyWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mOnlyWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mOnlyWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mOnlyWebview.getSettings().setLoadsImagesAutomatically(true);
        mOnlyWebview.getSettings().setBuiltInZoomControls(true);
        mOnlyWebview.getSettings().setUseWideViewPort(true);

        /*** 打开本地缓存提供JS调用 这里是因为js需要调用本地缓存而设置的权限**/
        mOnlyWebview.getSettings().setDomStorageEnabled(true);
        mOnlyWebview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        String appCachePath = getApplication().getCacheDir().getAbsolutePath();
        mOnlyWebview.getSettings().setAppCachePath(appCachePath);
        mOnlyWebview.getSettings().setAllowFileAccess(true);
        mOnlyWebview.getSettings().setAppCacheEnabled(true);
        mOnlyWebview.getSettings().setJavaScriptEnabled(true);

        mOnlyWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        //设置回退规则
        mOnlyWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mOnlyWebview.canGoBack()) {
                        mOnlyWebview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        mOnlyWebview.addJavascriptInterface(new Object() {
            @JavascriptInterface
            @SuppressLint("JavascriptInterface")
            public void imgZoom(String imgUrl) {
                String[] imgs = {imgUrl};
            }
        }, "androidJS");

    }

    /**
     * 缓存清理
     */
    private void clearCache() {
        try {
            mOnlyWebview.loadUrl("javascript:clearCachc()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        clearCache();
        mOnlyWebview.reload();
    }
    public void back(View view){
        finish();
    }
}
