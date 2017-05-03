package cn.incongress.endorcrinemagazine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.CollectionBean;
import cn.incongress.endorcrinemagazine.utils.ListDataSave;
import cn.incongress.endorcrinemagazine.utils.ProgressWebView;

public class DetailsActivity extends BaseActivity {
    private int userState ;
    private String USERID;
    private String NOTESID;
    private ProgressWebView mOnlyWebview;
    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.details_title)
    LinearLayout mDetailTitle;
    @BindView(R.id.details_text)
    TextView mText;
    @BindView(R.id.details_img)
    ImageView mImg;

    private ListDataSave dataSave;
    private List<CollectionBean> mCollectionList;
    private boolean mIsFromCollection;
    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mIsFromCollection = getIntent().getBooleanExtra("fromCollection",false);

        mOnlyWebview = (ProgressWebView)findViewById(R.id.details_web);
        USERID = getSPStringValue(Constants.USER_USER_ID);
        mTitle.setText(getIntent().getStringExtra("notestitle"));
        NOTESID = getIntent().getStringExtra("notesid");

        dataSave = new ListDataSave(mContext, "collection");

        mCollectionList = new ArrayList<CollectionBean>();

        mCollectionList = dataSave.getDataList("All");

        if("".equals(USERID)){
            userState = 0;
            mImg.setVisibility(View.GONE);
            mText.setText("登录后查看原文");
        }else {
            userState = 1;
            if(mCollectionList.size()>0){
                for (int i=0;i<mCollectionList.size();i++){
                    CollectionBean map = mCollectionList.get(i);
                    if( map.getNotesId().equals(NOTESID)){
                        mText.setText("取消收藏");
                        break;
                    }else {
                        mText.setText("收藏");
                    }
                }

            }else{
                mText.setText("收藏");
            }
        }
        initialWebViewSetting();
        loadUrl(Constants.TEST_SERVICE+Constants.DETAILS+"&notesId="+NOTESID+"&userState="+userState);
//
        mDetailTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("收藏".equals(mText.getText().toString())){
                    mText.setText("取消收藏");
                    //新收藏一条数据
                    CollectionBean collectionBean = new CollectionBean();
                    collectionBean.setNotesTitle(getIntent().getStringExtra("notestitle"));
                    collectionBean.setNotesId(NOTESID);
                    collectionBean.setLanmu(getIntent().getStringExtra("lanmu"));
                    collectionBean.setWhether(true);
                    mCollectionList.add(collectionBean);

                    dataSave.setDataList("All",mCollectionList);
                }else if("取消收藏".equals(mText.getText().toString())){
                    mText.setText("收藏");
                    for (int i=0;i<mCollectionList.size();i++){
                        CollectionBean map = mCollectionList.get(i);
                        if(map.getNotesId().equals(NOTESID)){
                            mCollectionList.remove(i);
                        }
                    }
                    dataSave.setDataList("All",mCollectionList);
                }else if("登录后查看原文".equals(mText.getText().toString())){
                    startActivity(new Intent(getApplication(),LoginActivity.class));
                    finish();
                }
            }
        });
    }
    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

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
                mOnlyWebview.loadUrl(url);
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

    @Override
    protected void setContentView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_details);
    }


    @Override
    protected void handleDetailMsg(Message msg) {

    }

    public void back(View view){
        if(mIsFromCollection)
            setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mIsFromCollection)
            setResult(RESULT_OK);
    }
}
