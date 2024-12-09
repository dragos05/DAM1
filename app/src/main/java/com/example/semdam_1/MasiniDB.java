package com.example.semdam_1;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Masina.class}, version =  1, exportSchema = false)
@TypeConverters({DateConvertor.class})
public abstract class MasiniDB extends RoomDatabase {
    public static final String DB_NAME = "masina_db";

    private static MasiniDB instanta;

    public static MasiniDB getInstance(Context context)
    {
        if(instanta == null)
            instanta = Room.databaseBuilder(context,
                    MasiniDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instanta;
    }

    public abstract MasiniDAO getMasiniDao();
}
