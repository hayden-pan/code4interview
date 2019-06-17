package com.code4thought;

public class TalkInfo {

    private String title;

    private Integer minute;

    public TalkInfo(String title, Integer minute) {
        this.title = title;
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}
