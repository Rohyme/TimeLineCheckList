package com.tripl3dev.kotlintest;

public class TimeLineModel implements TimeLineModelI {
    boolean isHeader;
    String title;
    boolean isChecked;
    int type;

    public TimeLineModel(boolean isHeader, String title) {
        this.isHeader = isHeader;
        this.title = title;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}
