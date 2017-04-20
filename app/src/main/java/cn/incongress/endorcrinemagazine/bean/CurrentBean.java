package cn.incongress.endorcrinemagazine.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/4/12.
 */

public  class CurrentBean {
    private String lanmuld;
    private String lanmu;
    private List<ChooseBean> list = new ArrayList<>();

    public String getLanmuld() {
        return lanmuld;
    }

    public void setLanmuld(String lanmuld) {
        this.lanmuld = lanmuld;
    }

    public String getLanmu() {
        return lanmu;
    }

    public void setLanmu(String lanmu) {
        this.lanmu = lanmu;
    }

    public List<ChooseBean> getList() {
        return list;
    }

    public void setList(List<ChooseBean> list) {
        this.list = list;
    }
}
