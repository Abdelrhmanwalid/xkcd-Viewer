package com.abdelrhman.xkcd.data.local.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return date == null ? 0 : date.getTime();
    }

    @TypeConverter
    public static Date fromTimeStamp(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
