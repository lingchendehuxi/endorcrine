package cn.incongress.endorcrinemagazine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.ChooseBean;
import cn.incongress.endorcrinemagazine.bean.CurrentBean;
import cn.incongress.endorcrinemagazine.utils.CountDownButtonHelper;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;
import cn.incongress.endorcrinemagazine.widget.ClearEditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.login_register)
    TextView mRegister;
    @BindView(R.id.login_haveyzm)
    Button mHaveYzm;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.login_yzm)
    EditText mYzm;
    @BindView(R.id.login_phone)
    ClearEditText mPhone;
    private String phone,name,code;
    private String mMsg;
    private String mTempCode;
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {

        mHaveYzm.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register:
                Intent it= new Intent(getApplication(),RegisterActivity.class);
                it.putExtra("type",1);
                startActivity(it);
                finish();
                break;
            case R.id.login_haveyzm:
                phone = mPhone.getText().toString();
                if(!"".equals(phone)) {
                    if(phone.length() == 11) {
                        CountDownButtonHelper helper = new CountDownButtonHelper(mHaveYzm, "重新发送", 60, 1);
                        helper.start();
                        initHttp(false);
                    }else{
                        Toast.makeText(getApplication(),"手机号码格式不正确",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplication(),"请填写手机号",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login:
                phone = mPhone.getText().toString();
                code = mYzm.getText().toString();
                if(!"".equals(phone)&&!"".equals(name)&&!"".equals(code)){
                    initHttp(true);
                }else{
                    Toast.makeText(mContext,"请填写完整信息",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setSPStringValue(Constants.USER_USER_ID,userId);
                    setSPStringValue(Constants.USER_PIC,userPic);
                    setSPStringValue(Constants.USER_TRUE_NAME,truenName);
                    setSPStringValue(Constants.USER_MOBILE,mobilePhone);
                    setSPStringValue(Constants.USER_EMAIL,email);
                    setSPStringValue(Constants.USER_KESHI,keshi);
                    setSPStringValue(Constants.USER_PROVINCE_NAME,provinceName);
                    setSPStringValue(Constants.USER_CITY_NAME,cityName);
                    setSPStringValue(Constants.USER_HOSPITAL_NAME,hospitalName);
                    setSPStringValue(Constants.USER_ZHICHENG,zhiwu);
                    setSPStringValue(Constants.USER_CITY_ID,cityId);
                    setSPStringValue(Constants.USER_HOSPITAL_ID,hospitalId);
                    setSPStringValue(Constants.USER_PROVINCE_ID,provinceId);
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 1:
                    Toast.makeText(getApplication(),mMsg,Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext,getString(R.string.httpfail),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private String userId,truenName,cityId,mobilePhone,email,keshi,provinceId,provinceName
            ,cityName,hospitalName,hospitalId,zhiwu,userPic;
    private void initHttp(boolean that) {
        if(that) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobilePhone", phone);
                    params.put("sms", code);
                    params.put("proId", "18");
                    params.put("lan", "cn");
                    try {
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(mContext,Constants.TEST_SERVICE + Constants.LOGIN, params, "GBK"));
                        Log.e("GYW",jsonObject.toString());
                        if(0 ==jsonObject.getInt("state")){
                            mMsg = jsonObject.getString("msg");
                            hand.sendEmptyMessage(1);
                        }else if(1==jsonObject.getInt("state")){
                            Log.e("GYW","zoule 1");
                            userId = jsonObject.getString("userId");
                            userPic = jsonObject.getString("userPic");
                            truenName= jsonObject.getString("trueName");
                            cityId= jsonObject.getString("city");
                            mobilePhone= jsonObject.getString("mobilePhone");
                            email= jsonObject.getString("email");
                            keshi= jsonObject.getString("keshi");
                            provinceId= jsonObject.getString("province");
                            provinceName= jsonObject.getString("provinceName");
                            cityName= jsonObject.getString("cityName");
                            hospitalName= jsonObject.getString("hospitalName");
                            hospitalId= jsonObject.getString("hospital");
                            zhiwu= jsonObject.getString("zhiwu");
                            Log.e("GYW","zoule 2");
                            hand.sendEmptyMessage(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hand.sendEmptyMessage(3);
                    }
                    super.run();
                }
            }.start();
        }else{
            new Thread() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobilePhone", phone);
                    params.put("proId", "18");
                    params.put("lan", "cn");
                    try {
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(mContext,Constants.TEST_SERVICE + Constants.LOGINYZM, params, "GBK"));
                        Log.e("GYW", jsonObject.getString("code"));
                        mMsg = jsonObject.getString("msg");
                        hand.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        hand.sendEmptyMessage(3);
                    }
                    super.run();
                }
            }.start();
        }
    }

    public void back(View view){
        finish();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
