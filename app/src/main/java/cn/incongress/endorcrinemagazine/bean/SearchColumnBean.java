package cn.incongress.endorcrinemagazine.bean;

import java.io.Serializable;

/**
 * Created by Admin on 2017/4/21.
 */

public class SearchColumnBean implements Serializable{
    private String lanmu;
    private String lanmuId;
    private boolean selected;

    public String getLanmu() {
        return lanmu;
    }

    public void setLanmu(String lanmu) {
        this.lanmu = lanmu;
    }

    public String getLanmuId() {
        return lanmuId;
    }

    public void setLanmuId(String lanmuId) {
        this.lanmuId = lanmuId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
