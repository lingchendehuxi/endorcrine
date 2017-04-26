package cn.incongress.endorcrinemagazine.bean;

/**
 * Created by Administrator on 2015/7/9.
 */
public class HospitalBean {
    private int hospitalId;
    private int cityId;
    private String name;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HospitalBean(int cityId, int hospitalId, String name) {
        this.cityId = cityId;
        this.hospitalId = hospitalId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HospitalBean{" +
                "cityId=" + cityId +
                ", hospitalId=" + hospitalId +
                ", name='" + name + '\'' +
                '}';
    }
}
