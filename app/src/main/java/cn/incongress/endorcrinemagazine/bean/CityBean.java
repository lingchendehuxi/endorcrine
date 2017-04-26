package cn.incongress.endorcrinemagazine.bean;

/**
 * Created by Administrator on 2015/7/9.
 */
public class CityBean {
    private int cityId;
    private int provinceId;
    private String provinceName;
    private String cityName;
    private String pinyin;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public CityBean(int cityId, String cityName, String pinyin, int provinceId, String provinceName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.pinyin = pinyin;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "cityId=" + cityId +
                ", provinceId=" + provinceId +
                ", cityName='" + cityName + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
