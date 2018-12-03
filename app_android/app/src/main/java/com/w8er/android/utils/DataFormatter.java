package com.w8er.android.utils;

import com.w8er.android.model.TimeSlot;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataFormatter {

    public static String currencyFormat(BigDecimal n) {
        return "₪" + NumberFormat.getCurrencyInstance(Locale.US).format(n).replace("$","");
    }

    public static float roundToHalf(double d) {

        int res = (int) d;
        int num = (int) ((d + 0.01) * 10) % 10;
        if (num >= 5) {
            return (float) (res + 0.5);
        }
        return (float) res;
    }


    public static String getCountryCode(String country) {

        String countryCode;
        if (country.equals("Israel")) {
            countryCode = "il";
        } else
            countryCode = "us";

        return countryCode;
    }

    public static String getRestStatus(List<TimeSlot> timeSlots) {

        DateFormat time = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int ho = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        String strDay = "";
        switch (day) {
            case Calendar.SUNDAY:
                strDay = "Sun";
                break;
            case Calendar.MONDAY:
                strDay = "Mon";
                break;
            case Calendar.TUESDAY:
                strDay = "Tue";
                break;
            case Calendar.WEDNESDAY:
                strDay = "Wed";
                break;
            case Calendar.THURSDAY:
                strDay = "Thu";
                break;
            case Calendar.FRIDAY:
                strDay = "Fri";
                break;
            case Calendar.SATURDAY:
                strDay = "Sat";
                break;
        }
        try {

            Date currentTime = time.parse(ho + ":" + min);

            for (TimeSlot t : timeSlots) {
                if (t.getDays().equalsIgnoreCase(strDay)) {

                    Date open = time.parse(t.getOpen());
                    Date closed = time.parse(t.getClose());

                    if (currentTime.after(open) && currentTime.before(closed)) {
                        return "Open";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Closed";

    }
}
