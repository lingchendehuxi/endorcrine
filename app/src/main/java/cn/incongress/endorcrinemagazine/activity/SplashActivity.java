package cn.incongress.endorcrinemagazine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.utils.ActivityUtils;
import cn.incongress.endorcrinemagazine.utils.TDevice;

public class SplashActivity extends BaseActivity {
    /*@BindView(R.id.tv_appversion)
    TextView mTvVersion;*/

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
       // mTvVersion.setText(getString(R.string.splash_versionName,TDevice.getVersionName()));
    }

    @Override
    protected void initializeEvents() {
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ActivityUtils.startActivity(SplashActivity.this, HomeActivity.class);
            finish();
        }
    };

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        return false;
    }

}
