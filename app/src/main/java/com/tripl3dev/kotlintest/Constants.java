package com.tripl3dev.kotlintest;

import android.support.annotation.IntDef;

public  class Constants {

    @IntDef({ViewStatus.HEADER, ViewStatus.CHECKED, ViewStatus.UNCHECKED})
    public @interface ViewStatus {
        int HEADER = 1;
        int CHECKED = 2;
        int UNCHECKED = 3;

    }
    @IntDef({LineType.NORMAL, LineType.BEGIN, LineType.END,LineType.ONLYONE})
    public @interface LineType {
        int NORMAL = 0;
        int BEGIN = 1;
        int END = 2;
        int ONLYONE = 3;

    }

}
