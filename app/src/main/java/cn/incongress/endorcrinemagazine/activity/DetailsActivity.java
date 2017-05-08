package cn.incongress.endorcrinemagazine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
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

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.analytics.MobclickAgent;

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
    @BindView(R.id.photoview)
    PhotoView mPhotoView;

    private ListDataSave dataSave;
    private List<CollectionBean> mCollectionList;
    private boolean mIsFromCollection;
    private String mLanmu ,mtitle;
    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mIsFromCollection = getIntent().getBooleanExtra("fromCollection",false);

        mOnlyWebview = (ProgressWebView)findViewById(R.id.details_web);
        USERID = getSPStringValue(Constants.USER_USER_ID);
        mtitle = getIntent().getStringExtra("notestitle");
        NOTESID = getIntent().getStringExtra("notesid");
        mLanmu = getIntent().getStringExtra("lanmu");
        mTitle.setText(mtitle);
        mPhotoView.enable();
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
                        mImg.setImageResource(R.mipmap.collection);
                        break;
                    }else {
                        mText.setText("收藏");
                        mImg.setImageResource(R.mipmap.cancelcollection);
                    }
                }

            }else{
                mText.setText("收藏");
            }
        }
        loadUrl(Constants.TEST_SERVICE+Constants.DETAILS+"&notesId="+NOTESID+"&userState="+userState);
        initialWebViewSetting();
//
        mDetailTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("收藏".equals(mText.getText().toString())){
                    mText.setText("取消收藏");
                    mImg.setImageResource(R.mipmap.collection);
                    //新收藏一条数据
                    CollectionBean collectionBean = new CollectionBean();
                    collectionBean.setNotesTitle(mtitle);
                    collectionBean.setNotesId(NOTESID);
                    collectionBean.setLanmu(mLanmu);
                    collectionBean.setWhether(true);
                    mCollectionList.add(collectionBean);

                    dataSave.setDataList("All",mCollectionList);
                }else if("取消收藏".equals(mText.getText().toString())){
                    mText.setText("收藏");
                    mImg.setImageResource(R.mipmap.cancelcollection);
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
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoView.setVisibility(View.GONE);
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

        Log.e("GYW",mOnlyWebview.getTitle().toString());
        mTitle.setText(mOnlyWebview.getTitle());
        String appCachePath = getApplication().getCacheDir().getAbsolutePath();
        mOnlyWebview.getSettings().setAppCachePath(appCachePath);
        mOnlyWebview.getSettings().setAllowFileAccess(true);
        mOnlyWebview.getSettings().setAppCacheEnabled(true);
        mOnlyWebview.getSettings().setJavaScriptEnabled(true);

        mOnlyWebview.addJavascriptInterface(new LanmuInterface(), "LanmuInterface");
        mOnlyWebview.addJavascriptInterface(new GetImgUrl(),"GetImgUrl");

        mOnlyWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mOnlyWebview.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mTitle.setText(view.getTitle());
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
                                mImg.setImageResource(R.mipmap.collection);
                                break;
                            }else {
                                mText.setText("收藏");
                                mImg.setImageResource(R.mipmap.cancelcollection);
                            }
                        }

                    }else{
                        mText.setText("收藏");
                    }
                }
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
    }

    /**
     * 缓存清理
     */
    /*private void clearCache() {
        try {
            mOnlyWebview.loadUrl("javascript:clearCachc()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public class LanmuInterface{
        @JavascriptInterface
        public void getLanmu(String id_,String title_,String lanmu_){
            mLanmu = lanmu_;
            NOTESID = id_;
            mtitle = title_;

        }
    }
    public class GetImgUrl{
        @JavascriptInterface
        public void getUrl(String Imgurl){
            mImgUrl = Imgurl;
            hand.sendEmptyMessage(1);
        }
    }
private String mImgUrl;
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(Constants.UMENG_DETAILS); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(Constants.UMENG_DETAILS);
        MobclickAgent.onResume(this);
    }

    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("GYW","地址："+mImgUrl);
                    mPhotoView.setVisibility(View.VISIBLE);
                    Glide.with(DetailsActivity.this).load(mImgUrl).placeholder(R.mipmap.default_load_bg).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            mPhotoView.setImageDrawable(resource);
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void setContentView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_details);
    }


    @Override
    protected void handleDetailMsg(Message msg) {

    }

    public void back(View view){
       /* if(mIsFromCollection)
            setResult(RESULT_OK);*/
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();/*
        if(mIsFromCollection)
            setResult(RESULT_OK);*/
    }
}
