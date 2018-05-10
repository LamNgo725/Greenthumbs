package com.example.j.crop.Room;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by J on 4/8/2018.
 */

public class DateRoomConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }


}
