package com.w8er.android.utils;

public class PhoneUtils {

    public static String getCountryCode(String country) {

        String countryCode;
        if (country.equals("Israel")) {
            countryCode = "il";
        } else
            countryCode = "us";

        return countryCode;
    }


}
