package com.bitpolarity.zzzoom.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Alarm.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlarmDao alarmDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){

        if (INSTANCE== null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "ALARM_DB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }


}
