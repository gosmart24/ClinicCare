package com.cybertech.cliniccare;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.Date;


public class Utils {


    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getCalculateDuration(Date startDate, Date finishedDate) {


        // int duration = (finishedDate - startDate) + 1;
        return 0;
    }

    public static int getCalMHsPerDay(int manHour, int duration) {

        return manHour / duration;
    }

}
