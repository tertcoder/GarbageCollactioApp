package com.example.garbagecollectionapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DISPLAY_DATE_FORMAT = "MMM dd, yyyy";
    public static final String DISPLAY_TIME_FORMAT = "hh:mm a";
    public static final String DISPLAY_DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a";

    public static String formatDate(String dateStr, String inputFormat, String outputFormat) {
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat, Locale.getDefault());
            Date date = inputFormatter.parse(dateStr);

            SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat, Locale.getDefault());
            return outputFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }
}