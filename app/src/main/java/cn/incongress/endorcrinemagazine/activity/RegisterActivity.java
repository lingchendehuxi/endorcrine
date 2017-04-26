package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.regist_sex)
    EditText mSex;
    @BindView(R.id.regist_email)
    EditText mEditEmail;
    @BindView(R.id.regist_phone)
    EditText mEditPhone;
    @BindView(R.id.regist_city)
    EditText mCity;
    @BindView(R.id.regist_city_qx)
    EditText mCityQX;
    @BindView(R.id.regist_dw)
    EditText mDanWei;
    @BindView(R.id.regist_dwdj)
    EditText mDanWeiDJ;
    @BindView(R.id.regist_ks)
    EditText mKeShi;
    @BindView(R.id.regist_zc)
    EditText mZhiCheng;
    @BindView(R.id.regist_zw)
    EditText mZhiWu;
    @BindView(R.id.btn_regist_next)
    Button mRegister;

    /** 性别选择器 **/
    private PopupWindow mSexPopupWindow;
    private ArrayList<String> mSexList= new ArrayList<>();
    private String[] mSexs = {"男", "女"};
    private String mCurrentSex;
    /** 单位等级选择器 **/
    private PopupWindow mDWGradePopupWindow;
    private ArrayList<String> mDWGradeList= new ArrayList<>();
    private String[] mDWGrades = {"三级甲等", "二级甲等", "一级甲等", "三级乙等", "二级乙等", "一级乙等"
            ,"三级丙等","二级丙等","一级丙等"};
    private String mCurrentDWGrade;

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
    /** 职称选择器 **/
    private PopupWindow mZhiChengPopupWindow;
    private ArrayList<String> mZhiChengList= new ArrayList<>();
    private String[] mZhiChengs = {"初级", "中级", "高级"};
    private String mCurrentZhiCheng;

    /** 职务选择器 **/
    private PopupWindow mZhiWuPopupWindow;
    private ArrayList<String> mZhiWuList= new ArrayList<>();
    private String[] mZhiWus = {"主治医师", "主任医生", "副主任医生", "助理医师（有执业证）", "助理医师（未考执业证）", "住院医师（有执业证）", "住院医师（未考执业证）","其他"};
    private String mCurrentZhiWu;

    /** 请求列表变量**/
    private String mName,mPhone,mEmail,mCityQuXian,province,city,mHospital,mHospitalId,cityId,provinceId;
    private String USERID = "-1";
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitle.setText(R.string.register_rgs);
        mSex.setOnClickListener(this);
        mCity.setOnClickListener(this);
        mDanWei.setOnClickListener(this);
        mDanWeiDJ.setOnClickListener(this);
        mKeShi.setOnClickListener(this);
        mZhiCheng.setOnClickListener(this);
        mZhiWu.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        AssetsDatabaseManager.initManager(RegisterActivity.this);
        if(1 == getIntent().getIntExtra("type",-1)){

        }else{

        }
         //性别初始化
        for (int i = 0; i < mSexs.length; i++) {
            mSexList.add(mSexs[i]);
        }
        //科室初始化
        for (int i = 0; i < mDWGrades.length; i++) {
            mDWGradeList.add(mDWGrades[i]);
        }

        //科室初始化
        for (int i = 0; i < mKeShis.length; i++) {
            mKeShiList.add(mKeShis[i]);
        }
        //职称初始化
        for (int i = 0; i < mZhiChengs.length; i++) {
            mZhiChengList.add(mZhiChengs[i]);
        }

        //职务初始化
        for (int i = 0; i < mZhiWus.length; i++) {
            mZhiWuList.add(mZhiWus[i]);
        }
        initSexPopupwindow();
        initDWGradePopupwindow();
        initKeShiPopupwindow();
        initZhiWuPopupwindow();
        initZhiChengPopupwindow();

    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist_sex:
                mSexPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.regist_city:
                startActivityForResult(new Intent(RegisterActivity.this, InfoEditLocationActivity.class) ,GET_CITYID);
                break;
            case R.id.regist_dw:
                startActivityForResult(new Intent(RegisterActivity.this, InfoEditHospitalActivity.class), GET_HOSPITAL);
                break;
            case R.id.regist_dwdj:
                mDWGradePopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.regist_ks:
                mKeShiPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.regist_zc:
                mZhiChengPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.regist_zw:
                mZhiWuPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll_whole_area),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_regist_next:
                mName = mEditName.getText().toString();
                mPhone = mEditPhone.getText().toString();
                mEmail = mEditEmail.getText().toString();
                mCityQuXian = mCityQX.getText().toString();
                mHospital = mDanWei.getText().toString();
                if(!"".equals(mName)&&!"".equals(mPhone)&&!"".equals(mEmail)&&!"".equals(mCityQuXian)&&
                        !"".equals(city)&&!"".equals(mHospital)&&!"".equals(mCurrentZhiWu)&&
                        !"".equals(mCurrentZhiCheng)&& !"".equals(mCurrentSex)&&!"".equals(mCurrentDWGrade)
                        &&!"".equals(mCurrentKeshi)){
                    initHttp();
                }else{
                    Toast.makeText(getApplication(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void initHttp() {
        new Thread(){
            @Override
            public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("proId", "18");
                        params.put("lan", "cn");
                        params.put("userId", USERID);
                        params.put("trueName", mName);params.put("sex", mCurrentSex);
                        params.put("mobilePhone", mPhone);params.put("email", mEmail);
                        params.put("province", provinceId);params.put("provinceName", province);
                        params.put("city", cityId);params.put("cityName", city);
                        params.put("hospital", mHospitalId);params.put("hospitalName", mHospital);
                        params.put("hospitalLevel", mCurrentDWGrade);params.put("keshi", mCurrentKeshi);
                        params.put("zhicheng", mCurrentZhiCheng);params.put("zhiwu", mCurrentZhiWu);
                Log.e("GYW",Constants.TEST_SERVICE + Constants.REGISTER+"?proId="+"18"+"&lan="+"cn"+"&userId="+"-1"+"&trueName="+mName
                        +"&sex="+mCurrentSex+"&mobilePhone="+mPhone+"&email="+mEmail+"&province="+provinceId+"&provinceName="+province+
                        "&city="+cityId+"&cityName="+city+"&hospital="+mHospitalId+"&hospitalName="+mHospital
                        +"&hospitalLevel="+mCurrentDWGrade+"&keshi="+mCurrentKeshi+"&zhicheng="+mCurrentZhiCheng+"&zhiwu="+mCurrentZhiWu);
                        try {
                            JSONObject jsonObject = new JSONObject(HttpUtils.submitPostData(Constants.TEST_SERVICE + Constants.REGISTER, params, "GBK"));
                            Log.e("GYW",jsonObject.toString());
                            if(0==jsonObject.getInt("state")){
                            }else{
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                super.run();
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            city = getSPStringValue(Constants.USER_CITY_NAME);
            province = getSPStringValue(Constants.USER_PROVINCE_NAME);
            cityId = getSPStringValue(Constants.USER_CITY_ID);
            provinceId = getSPStringValue(Constants.USER_PROVINCE_ID);
            mCity.setText(city+getString(R.string.info_blank)+province);
            mDanWei.setText(getSPStringValue(Constants.USER_HOSPITAL_NAME ));
            mHospitalId = getSPStringValue(Constants.USER_HOSPITAL_ID);
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
    private void initZhiChengPopupwindow() {

        View educationPicker = getLayoutInflater().inflate(R.layout.time_select_view, null);

        final WheelView wheel = (WheelView)educationPicker.findViewById(R.id.time_picker);
        TextView mTvSave = (TextView)educationPicker.findViewById(R.id.tv_time_save);
        TextView mTvCancel = (TextView)educationPicker.findViewById(R.id.tv_time_cancel);

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZhiChengPopupWindow.dismiss();
                mCurrentZhiCheng = wheel.getSelectedText();

                setSPStringValue(Constants.USER_ZHICHENG, mCurrentZhiCheng);
                mZhiCheng.setText(mCurrentZhiCheng);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZhiChengPopupWindow.dismiss();
            }
        });

        wheel.setData(mZhiChengList);
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

        mZhiChengPopupWindow = new PopupWindow(educationPicker, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        ColorDrawable dw = new ColorDrawable(0x77000000);
        mZhiChengPopupWindow.setBackgroundDrawable(dw);
        mZhiChengPopupWindow.setOutsideTouchable(true);
        mZhiChengPopupWindow.setFocusable(true);
        mZhiChengPopupWindow.setTouchable(true);
        mZhiChengPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    private void initSexPopupwindow(){
        View educationPicker = getLayoutInflater().inflate(R.layout.time_select_view, null);

        final WheelView wheel = (WheelView)educationPicker.findViewById(R.id.time_picker);
        TextView mTvSave = (TextView)educationPicker.findViewById(R.id.tv_time_save);
        TextView mTvCancel = (TextView)educationPicker.findViewById(R.id.tv_time_cancel);

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexPopupWindow.dismiss();
                mCurrentSex = wheel.getSelectedText();

                setSPStringValue(Constants.USER_ZHICHENG, mCurrentSex);
                mSex.setText(mCurrentSex);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexPopupWindow.dismiss();
            }
        });

        wheel.setData(mSexList);
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

        mSexPopupWindow = new PopupWindow(educationPicker, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        ColorDrawable dw = new ColorDrawable(0x77000000);
        mSexPopupWindow.setBackgroundDrawable(dw);
        mSexPopupWindow.setOutsideTouchable(true);
        mSexPopupWindow.setFocusable(true);
        mSexPopupWindow.setTouchable(true);
        mSexPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }
    private void  initDWGradePopupwindow(){
        View educationPicker = getLayoutInflater().inflate(R.layout.time_select_view, null);

        final WheelView wheel = (WheelView)educationPicker.findViewById(R.id.time_picker);
        TextView mTvSave = (TextView)educationPicker.findViewById(R.id.tv_time_save);
        TextView mTvCancel = (TextView)educationPicker.findViewById(R.id.tv_time_cancel);

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDWGradePopupWindow.dismiss();
                mCurrentDWGrade = wheel.getSelectedText();

                setSPStringValue(Constants.USER_ZHICHENG, mCurrentDWGrade);
                mDanWeiDJ.setText(mCurrentDWGrade);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDWGradePopupWindow.dismiss();
            }
        });

        wheel.setData(mDWGradeList);
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

        mDWGradePopupWindow = new PopupWindow(educationPicker, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        ColorDrawable dw = new ColorDrawable(0x77000000);
        mDWGradePopupWindow.setBackgroundDrawable(dw);
        mDWGradePopupWindow.setOutsideTouchable(true);
        mDWGradePopupWindow.setFocusable(true);
        mDWGradePopupWindow.setTouchable(true);
        mDWGradePopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }
    public void back(View view){
        finish();
    }

}
