package com.example.forestsys;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TimestampConverter {
    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                TimeZone timeZone = TimeZone.getTimeZone("GMT");
                df.setTimeZone(timeZone);
                return (Date) df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }


    @TypeConverter
    public static String dateToTimestamp(Date value) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        df.setTimeZone(timeZone);
        return value == null ? null : df.format(value);
    }
}
