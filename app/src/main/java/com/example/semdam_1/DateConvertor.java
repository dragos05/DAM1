package com.example.semdam_1;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConvertor {

    @TypeConverter
    public Date fromTimeStamp(Long value)
    {
        return value == null? null: new Date(value);
    }

    @TypeConverter
    public Long dateToTimeStamp(Date date)
    {
        if(date==null)
            return null;
        else
            return date.getTime();

    }
}
