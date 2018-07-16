package com.tripl3dev.kotlintest;

public interface TimeLineModelI {

    String getId();

    String getTitle();

    boolean isChecked();

    boolean isHeader();

    void setChecked(boolean isChecked);

    int type();

    void setType(int type);

}
