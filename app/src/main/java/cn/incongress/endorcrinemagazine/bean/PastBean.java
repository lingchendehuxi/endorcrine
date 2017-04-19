package cn.incongress.endorcrinemagazine.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/4/18.
 */

public class PastBean implements Serializable {
    private String year;
    private Map<String , List<String>> month;

    public Map<String, List<String>> getMonth() {
        return month;
    }

    public void setMonth(Map<String, List<String>> month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
