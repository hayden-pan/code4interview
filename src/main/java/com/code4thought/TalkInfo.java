package com.code4thought;

public class TalkInfo {

    private String title;

    private int minute;

    public TalkInfo(String title, Integer minute) {
        this.title = title;
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public int getMinute() {
        return minute;
    }
}
