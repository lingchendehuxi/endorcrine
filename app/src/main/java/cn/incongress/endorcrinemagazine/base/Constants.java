package cn.incongress.endorcrinemagazine.base;

/**
 * Created by Jacky on 2017/4/6.
 * 全局常用字段
 */

public class Constants {
    /**
     * 是否处于调试阶段
     */
    public static boolean DEBUG = true;
    /**
     * 项目Id
     */
    public static final int PROJECT_ID = 1;

    /**
     * 客户端类型
     */
    public static final int CLIENT_TYPE = 3;

    /**
     * 常用信息存储位置
     */
    public static final String DEFAULT_SP_NAME = "endorcrine_magazine_configure";

    /**
     * 用户id
     */
    public static final String SP_USER_UUID = "user_userid";

    /**
     * 分页的长度，全局固定
     */
    public static final int PAGE_SIZE = 8;

    /**
     * 全部缓存文件
     */
    public static final String CACHE_FILE_NAME = "endorcrine_magazine_folder";

    //所有接口再加一个输入参数：lan，中文传cn,英文传en
    //测试服：
    public static final String TEST_SERVICE = "http://incongress.cn";

    /*//正式服：
    public static final String TEST_SERVICE = "http://xhy.incongress.cn";*/

    //意见反馈
    public static final String FEEDBACK = "http://weixin.incongress.cn/xhy/xhyHtml5/html/feedback.html";//  ?userId=&project=

    /*列表精选
      输入参数：int proId,Integer lastNotesId
      返回参数：state，msg,pageState,notesArray；
      notesArray:notesId,notesTitle,smallTitle,authors,notesType,readCount,lanmu
    */
    public static final String SELECTED = "/notesApi.do?method=getNotesByJingxuan";

    /*期次下列表
      输入参数：int proId,int state（0：当期；1，往期）,String notesType（state为0时不需要）
      返回参数：state，msg,notesType，lanmuArray
      lanmuArray：lanmuId，lanmu，notesArray
      notesArray：同接口1
    */
    public static final String PERIOD_LIST  = "/notesApi.do?method=getNotesByNotesType";


    /*往期期次
      输入参数：int proId
      返回参数：state，msg,yearArray
      yearArray:year,notesTypeArray
      notesTypeArray：notesType
    */
    public static final String PREVIOUS_PERIOD = "/notesApi.do?method=getNotesQici";

    /*获取所有栏目、年份（搜索）
      新接口：/notesApi.do?method=getNotesLmnf
      输入参数：int proId
      返回参数：years，lanmuArray
      lanmuArray：lanmuId，lanmu
    */
    public static final String GET_COLUMN_YEAR = "/notesApi.do?method=getNotesLmnf";

    /*搜索
      输入参数：int proId,Integer textType,String text,String lanmus,String years
      返回参数：state,msg，name，notesArray
      notesArray：同接口1
    */
    public static final String SEARCH = "/notesApi.do?method=getNotesBySearch";

    /*登录
    输入参数：int proId,String trueName,String mobilePhone,String sms,String lan*/
    public static final String LOGIN = "/notesApi.do?method=login";

    /*注册、修改个人信息
    输入参数：int proId,int userId,String trueName,String sex,
    String mobilePhone,String email,String province,String provinceName,
    String city,String cityName,String hospital,String hospitalName,
    String hospitalLevel,String keshi,String zhicheng,String zhiwu*/
    public static final String REGISTER = "/notesApi.do?method=regOrUpdateUser";

    /*修改头像
    输入参数：int proId,Integer userId userImg
    返回参数：state,imgUrl*/
    public static final String IMGURL = "/notesApi.do?method=uploadHead";

    /*验证码
    输入参数：String mobilePhone,String lan,Integer proId;*/
    public static final String LOGINYZM = "/notesApi.do?method=getSmsMobile";

    /*详情页
      输入参数：userState(已登录为1；未登录为0),notesId(item ID)
    */
    public static final String DETAILS="/notesApi.do?method=getNotesById";



    /** 个人信息相关的字段 **/
    public static String USER_LOGIN_MOBILE = "loginMobile";
    public static String USER_LOGIN_PWD = "loginPwd";
    public static String USER_USER_ID = "userId";
    public static String USER_TRUE_NAME = "trueName";
    public static String USER_PIC = "userPic";
    public static String USER_NICKNAME = "nickname";
    public static String USER_SEX = "sex";
    public static String USER_MOBILE = "mobilePhone";
    public static String USER_EMAIL = "email";
    public static String USER_KESHI = "keshi";
    public static String USER_ZHICHENG = "zhicheng";
    public static String USER_PROVINCE_ID = "province";
    public static String USER_PROVINCE_NAME = "provinceName";
    public static String USER_CITY_NAME = "cityName";
    public static String USER_CITY_ID = "city";
    public static String USER_HOSPITAL_ID = "hospital";
    public static String USER_HOSPITAL_NAME = "hospitalName";
    public static String USER_HOSPITAL_LEVEL = "hospitalLevel";
    public static String USER_ZHIWU = "zhiwu";
    public static String USER_UNIV = "univ";
    public static String USER_UNIV_ID = "univsId";
    public static String USER_UNIV_YEAR = "univYear";
    public static String USER_HIGHEST_SCHOOL = "highestSchool";
    public static String USER_REMARK = "remark";
    public static final String SP_RECIPIENT_NAME = "person_recipient_name";
    public static final String SP_RECIPIENT_MOBILE ="person_recipient_mobile";
    public static final String SP_RECIPIENT_ZIP_CODE = "person_recipient_zip_code";
    public static final String SP_RECIPIENT_ADDRESS = "person_recipient_address";

    public static final String SP_IS_LOG_OUT = "is_log_out";
    /**
     * 是否第一次进入video
     */
    public static final String SP_IS_FIRST_IN_VIDEO = "is_first_in_video";
}
