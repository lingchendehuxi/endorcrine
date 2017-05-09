package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.activity.info.InfoEditHospitalActivity;
import cn.incongress.endorcrinemagazine.activity.info.InfoEditLocationActivity;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.utils.AssetsDatabaseManager;
import cn.incongress.endorcrinemagazine.utils.HttpUtils;
import cn.incongress.endorcrinemagazine.utils.StringUtils;
import cn.incongress.endorcrinemagazine.utils.ToastUtils;
import cn.incongress.endorcrinemagazine.utils.WheelView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    public static final int GET_CITYID = 0x0003;
    public static final int GET_HOSPITAL = 0x0004;
    public static final int GET_NAME = 0x0005;
    public static final int GET_SEX = 0x0006;
    public static final int GET_EMAIL = 0x0007;
    public static final int GET_KESHI = 0x008;
    public static final int GET_ZHICHENG = 0x009;
    public static final int GET_SCHOOL = 0x010;
    public static final int GET_NICKNAME = 0x011;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_IDENTIFY_CODE = 1002;

    @BindView(R.id.activity_title)
    TextView mTitle;
    @BindView(R.id.regist_name)
    EditText mEditName;
    @BindView(R.id.regist_email)
    EditText mEditEmail;
    @BindView(R.id.regist_phone)
    EditText mEditPhone;
    @BindView(R.id.regist_city)
    EditText mCity;
    @BindView(R.id.regist_dw)
    EditText mDanWei;
    @BindView(R.id.regist_ks)
    EditText mKeShi;
    @BindView(R.id.regist_zw)
    EditText mZhiWu;
    @BindView(R.id.btn_regist_next)
    Button mRegister;


    /** 科室选择器 **/
    private PopupWindow mKeShiPopupWindow;
    private ArrayList<String> mKeShiList = new ArrayList<>();
    private String[] mKeShis = {"重症医学科","呼吸内科","麻醉疼痛科","急诊科","内科","小儿呼吸科","大内科","中西医结合科","乳腺外科","产科","介入科","儿科",
            "儿童保健科","全科医学","内分泌科","医学影像科","变态反应科","口腔科","外科","妇产科","小儿免疫科","小儿内分泌遗传代谢科","小儿心内科","小儿心脏外科",
            "小儿急诊科","小儿感染科","小儿泌尿外科","小儿消化科","小儿神经内科","小儿神经外科","小儿综合内科","小儿综合外科","小儿肾内科","小儿胸外科","小儿血液科",
            "小儿骨科","心脏内科","心脏外科","感染科","手外科","放疗科","整形外科","新生儿科","普通外科","核医学科","检验科","泌尿外科","消化内科","烧伤科","物理治疗与康复科",
            "生殖医学科","男科","病理科","皮肤性病科","眼科","神经内科","神经外科","精神心理科","老年医学科","耳鼻咽喉头颈科","肝胆外科","营养科","肾脏内科","肿瘤科","胸外科",
            "药剂科","血液内科","血液外科","计划生育科","超声科","野战外科","风湿免疫科","骨科","中医科","预防保健科","输血科","透析科","其他"};
    private String mCurrentKeshi;
    /** 职务选择器 **/
    private PopupWindow mZhiWuPopupWindow;
    private ArrayList<String> mZhiWuList= new ArrayList<>();
    private String[] mZhiWus = {"主治医师", "主任医生", "副主任医生", "助理医师（有执业证）", "助理医师（未考执业证）", "住院医师（有执业证）", "住院医师（未考执业证）","其他"};
    private String mCurrentZhiWu;

    /** 请求列表变量**/
    private String mName,mPhone,mEmail,province,city,mHospital,mHospitalId,cityId,provinceId,mzhiwu,mKeshi;
    private String USERID ;
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mCity.setOnClickListener(this);
        mKeShi.setOnClickListener(this);
        mZhiWu.setOnClickListener(this);
        mDanWei.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        AssetsDatabaseManager.initManager(RegisterActivity.this);
        if(1 == getIntent().getIntExtra("type",-1)){
            mTitle.setText(R.string.register_rgs);
            hand.sendEmptyMessage(1);
        }else{
            mTitle.setText(R.string.register_user);
            hand.sendEmptyMessage(2);
        }

        //科室初始化
        for (int i = 0; i < mKeShis.length; i++) {
            mKeShiList.add(mKeShis[i]);
        }

        //职务初始化
        for (int i = 0; i < mZhiWus.length; i++) {
            mZhiWuList.add(mZhiWus[i]);
        }
        initKeShiPopupwindow();
        initZhiWuPopupwindow();

    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist_city:
                startActivityForResult(new Intent(RegisterActivity.this, InfoEditLocationActivity.class) ,GET_CITYID);
                break;
            case R.id.regist_dw:
                if (StringUtils.isEmpty(getSPStringValue(Constants.USER_CITY_ID))  || ! (Integer.parseInt(getSPStringValue(Constants.USER_CITY_ID)) > 0)) {
                    ToastUtils.showShortToast("请先选择地区",getApplication());
                } else {
                    startActivityForResult(new Intent(RegisterActivity.this, InfoEditHospitalActivity.class), GET_HOSPITAL);
                }break;
            case R.id.regist_ks:
                mKeShiPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.regist_zw:
                mZhiWuPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.btn_regist_next:
                mName = mEditName.getText().toString().trim();
                mPhone = mEditPhone.getText().toString().trim();
                mEmail = mEditEmail.getText().toString().trim();
                mHospital = getSPStringValue(Constants.USER_HOSPITAL_NAME);
                mzhiwu = getSPStringValue(Constants.USER_ZHICHENG);
                mKeshi = getSPStringValue(Constants.USER_KESHI);
                provinceId = getSPStringValue(Constants.USER_PROVINCE_ID);
                province = getSPStringValue(Constants.USER_PROVINCE_NAME);
                city = getSPStringValue(Constants.USER_CITY_NAME);
                cityId = getSPStringValue(Constants.USER_CITY_ID);
                mHospitalId = getSPStringValue(Constants.USER_HOSPITAL_ID);
                Log.e("GYW","注册"+mName+mPhone+mEmail+city+mHospital+mzhiwu+mKeshi);
                if(!"".equals(mName)&&!"".equals(mPhone)&&!"".equals(mEmail)&&
                        !"".equals(city)&&!"".equals(mHospital)&&!"".equals(mzhiwu)
                        &&!"".equals(mKeshi)){
                    if(isEmail(mEmail)){
                        if(mPhone.length() == 11) {
                            initHttp();
                        }else{
                            Toast.makeText(getApplication(),"手机号码格式不正确",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplication(),"邮箱格式错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplication(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
    private void initHttp() {
        Log.e("GYW","userId"+USERID+"trueName"+mName+"mobilePhone"+mPhone+"email"+ mEmail
        +"province"+provinceId+"provinceName"+ province+"city"+cityId
        +"cityName"+city+"hospital"+mHospitalId+"hospitalName"+mHospital+"keshi"+mKeshi+"zhiwu"+mzhiwu);
        new Thread(){
            @Override
            public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("proId", "18");
                        params.put("lan", "cn");
                        params.put("userId", USERID);
                        params.put("trueName", mName);
                        params.put("mobilePhone", mPhone);params.put("email", mEmail);
                        params.put("province", provinceId);params.put("provinceName", province);
                        params.put("city", cityId);params.put("cityName", city);
                        params.put("hospital", mHospitalId);params.put("hospitalName", mHospital);
                        params.put("keshi", mKeshi);
                        params.put("zhiwu", mzhiwu);
                try {
                            JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(mContext,Constants.TEST_SERVICE + Constants.REGISTER, params, "GBK"));
                            Log.e("GYW",jsonObject.toString());
                            if(1==jsonObject.getInt("state")){
                                hand.sendEmptyMessage(3);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                    hand.sendEmptyMessage(4);
                        }
                super.run();
            }
        }.start();
    }

    Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    USERID = "-1";
                break;
                case 2:
                     USERID = getSPStringValue(Constants.USER_USER_ID);
                     mEditName.setText(getSPStringValue(Constants.USER_TRUE_NAME));
                     mEditEmail.setText(getSPStringValue(Constants.USER_EMAIL));
                     mEditPhone.setText(getSPStringValue(Constants.USER_MOBILE));
                     mCity.setText(getSPStringValue(Constants.USER_CITY_NAME)+getString(R.string.info_blank)+getSPStringValue(Constants.USER_PROVINCE_NAME));
                     mDanWei.setText(getSPStringValue(Constants.USER_HOSPITAL_NAME));
                     mKeShi.setText(getSPStringValue(Constants.USER_KESHI));
                     mZhiWu.setText(getSPStringValue(Constants.USER_ZHICHENG));
                break;
                case 3:
                    setSPStringValue(Constants.USER_USER_ID,USERID);
                    setSPStringValue(Constants.USER_PIC,"");
                    setSPStringValue(Constants.USER_TRUE_NAME,mName);
                    setSPStringValue(Constants.USER_MOBILE,mPhone);
                    setSPStringValue(Constants.USER_EMAIL,mEmail);
                    setSPStringValue(Constants.USER_KESHI,mKeshi);
                    setSPStringValue(Constants.USER_PROVINCE_NAME,province);
                    setSPStringValue(Constants.USER_CITY_NAME,city);
                    setSPStringValue(Constants.USER_CITY_ID,cityId);
                    setSPStringValue(Constants.USER_HOSPITAL_ID,mHospitalId);
                    setSPStringValue(Constants.USER_PROVINCE_ID,provinceId);
                    setSPStringValue(Constants.USER_PROVINCE_NAME,province);
                    setSPStringValue(Constants.USER_HOSPITAL_NAME,mHospital);
                    setSPStringValue(Constants.USER_ZHICHENG,mzhiwu);
                    finish();
                    break;
                case 4:
                    Toast.makeText(mContext,getString(R.string.httpfail),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == GET_CITYID){
                city = getSPStringValue(Constants.USER_CITY_NAME);
                province = getSPStringValue(Constants.USER_PROVINCE_NAME);
                cityId = getSPStringValue(Constants.USER_CITY_ID);
                provinceId = getSPStringValue(Constants.USER_PROVINCE_ID);
                mCity.setText(city+getString(R.string.info_blank)+province);
                mDanWei.setText("");
                mKeShi.setText("");
                mZhiWu.setText("");
            }else if(requestCode == GET_HOSPITAL){
                mDanWei.setText(getSPStringValue(Constants.USER_HOSPITAL_NAME ));
                mHospitalId = getSPStringValue(Constants.USER_HOSPITAL_ID);
            }
        }
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }
    private void initKeShiPopupwindow() {

        View educationPicker = getLayoutInflater().inflate(R.layout.time_select_view, null);

        final WheelView wheel = (WheelView)educationPicker.findViewById(R.id.time_picker);
        TextView mTvSave = (TextView)educationPicker.findViewById(R.id.tv_time_save);
        TextView mTvCancel = (TextView)educationPicker.findViewById(R.id.tv_time_cancel);

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeShiPopupWindow.dismiss();
                mCurrentKeshi = wheel.getSelectedText();

                setSPStringValue(Constants.USER_KESHI, mCurrentKeshi);
                mKeShi.setText(mCurrentKeshi);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeShiPopupWindow.dismiss();
            }
        });

        wheel.setData(mKeShiList);
        wheel.setDefault(0);

        wheel.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {

            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mKeShiPopupWindow = new PopupWindow(educationPicker, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        ColorDrawable dw = new ColorDrawable(0x77000000);
        mKeShiPopupWindow.setBackgroundDrawable(dw);
        mKeShiPopupWindow.setOutsideTouchable(true);
        mKeShiPopupWindow.setFocusable(true);
        mKeShiPopupWindow.setTouchable(true);
        mKeShiPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    private void initZhiWuPopupwindow() {

        View educationPicker = getLayoutInflater().inflate(R.layout.time_select_view, null);

        final WheelView wheel = (WheelView)educationPicker.findViewById(R.id.time_picker);
        TextView mTvSave = (TextView)educationPicker.findViewById(R.id.tv_time_save);
        TextView mTvCancel = (TextView)educationPicker.findViewById(R.id.tv_time_cancel);

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZhiWuPopupWindow.dismiss();
                mCurrentZhiWu = wheel.getSelectedText();

                setSPStringValue(Constants.USER_ZHICHENG, mCurrentZhiWu);
                mZhiWu.setText(mCurrentZhiWu);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZhiWuPopupWindow.dismiss();
            }
        });

        wheel.setData(mZhiWuList);
        wheel.setDefault(0);

        wheel.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {

            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mZhiWuPopupWindow = new PopupWindow(educationPicker, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        ColorDrawable dw = new ColorDrawable(0x77000000);
        mZhiWuPopupWindow.setBackgroundDrawable(dw);
        mZhiWuPopupWindow.setOutsideTouchable(true);
        mZhiWuPopupWindow.setFocusable(true);
        mZhiWuPopupWindow.setTouchable(true);
        mZhiWuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }
    public void back(View view){
        finish();
    }

}
