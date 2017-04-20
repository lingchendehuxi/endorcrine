package cn.incongress.endorcrinemagazine.bean;

import java.io.Serializable;

/**
 * Created by Admin on 2017/4/11.
 */

public class ChooseBean implements Serializable {
    private String notesId;
    private String notesTitle;
    private String smallTitle;
    private String authors;
    private String notesType;
    private String readCount;
    private String lanmu;

    public String getLanmu() {
        return lanmu;
    }

    public void setLanmu(String lanmu) {
        this.lanmu = lanmu;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getNotesTitle()  {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getNotesType() {
        return notesType;
    }

    public void setNotesType(String notesType) {
        this.notesType = notesType;
    }

    public String getNotesId() {
        return notesId;
    }

    public void setNotesId(String notesId) {
        this.notesId = notesId;
    }
}
