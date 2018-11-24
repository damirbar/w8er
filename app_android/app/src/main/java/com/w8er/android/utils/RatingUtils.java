package com.w8er.android.utils;

public class RatingUtils {

    public static float roundToHalf(double d) {

        int res = (int) d;
        int num = (int)((d + 0.01) * 10) % 10;
        if(num >= 5){
            return (float) (res + 0.5);
        }
        return (float) res;
    }

}
