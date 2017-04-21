package cn.incongress.endorcrinemagazine.bean;

import java.io.Serializable;

/**
 * Created by Admin on 2017/4/21.
 */

public class SearchYearBean implements Serializable{
    private String year;
    private boolean selected;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
