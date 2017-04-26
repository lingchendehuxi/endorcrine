package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.login_register)
    TextView mRegister;
    @BindView(R.id.login_haveyzm)
    Button mHaveYzm;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.login_yzm)
    EditText mYzm;
    @BindView(R.id.login_name)
    EditText mName;
    @BindView(R.id.login_phone)
    EditText mPhone;
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
                    if(isMobileNO(phone)) {
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
                name = mName.getText().toString();
                code = mYzm.getText().toString();
                if(!"".equals(phone)&&!"".equals(name)&&!"".equals(code)){
                    initHttp(true);
                }else{
                    Toast.makeText(getApplication(),"请填写完整信息",Toast.LENGTH_SHORT).show();
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

                    break;
                case 1:
                    Toast.makeText(getApplication(),mMsg,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void initHttp(boolean that) {
        if(that) {
            new Thread() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobilePhone", phone);
                    params.put("trueName", name);
                    params.put("sms", code);
                    params.put("proId", "18");
                    params.put("lan", "cn");
                    try {
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE + Constants.LOGIN, params, "GBK"));
                        Log.e("GYW",jsonObject.toString());
                        mMsg = jsonObject.getString("msg");
                        if(0==jsonObject.getInt("state")){
                            hand.sendEmptyMessage(1);
                        }else{

                        }
                        hand.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
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
                        JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE + Constants.LOGINYZM, params, "GBK"));
                        Log.e("GYW", jsonObject.getString("code"));
                        mMsg = jsonObject.getString("msg");
                        hand.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
        }
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public void back(View view){
        finish();
    }
}
