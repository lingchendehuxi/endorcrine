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

    //个人信息字段
    public static final String SP_PERSON_LOGO = "person_logo";
    public static final String SP_PERSON_NAME = "person_name";
    public static final String SP_PERSON_EMAIL = "person_email";
    public static final String SP_PERSON_BIRTHDAY = "person_birthday";
    public static final String SP_PERSON_BIRTHDAY_YEAR = "person_birthday_year";
    public static final String SP_PERSON_BIRTHDAY_MONTH = "person_birthday_month";
    public static final String SP_PERSON_CIRTIFICATION = "person_cirtifacation";
    public static final String SP_PERSON_MOBILE = "person_mobile";

    public static final String SP_ZHICHENG = "person_zhicheng";
    public static final String SP_ZHIWU = "person_zhiwu";
    public static final String SP_CITY_ID = "person_city_id";
    public static final String SP_PROVINCE_ID = "person_province_id";
    public static final String SP_PROVINCE_CITY_NAME = "person_province_city";
    public static final String SP_PROVINCE_LOCATION = "person_province_location";
    public static final String SP_HOSPITAL = "person_hospital";
    public static final String SP_HOSPITAL_LEVEL = "person_hospital_level";
    public static final String SP_KESHI = "person_keshi";
    public static final String SP_ADJUST_KESHI = "person_adjust_keshi";
    public static final String SP_PHONE = "person_phone";
    public static final String SP_EDUCATION = "person_education";
    public static final String SP_ZIP_CODE = "person_zip_code";
    public static final String SP_ADDRESS = "person_address";

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
