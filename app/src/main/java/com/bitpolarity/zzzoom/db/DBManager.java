package com.bitpolarity.zzzoom.db;

import android.app.Application;
import android.app.PendingIntent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends AndroidViewModel {

    AppDatabase db;
    List<Alarm> alarmList;


    public DBManager(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDbInstance(application.getApplicationContext());
        alarmList = getAlarmList(db);
    }

    public AppDatabase getDb(){
        return db;
    }

    private List<Alarm> getAlarmList(AppDatabase db){
        return  db.alarmDao().getAllAlarms();
    }

   public List<Alarm> getAlarmList(){
        return db.alarmDao().getAllAlarms();
    }

   public Alarm getAlarm(int position){
        return  alarmList.get(position);
    }

    public void setNewAlarm(Alarm alarm){
           db.alarmDao().insertAlarm(alarm);
    }



   public Alarm createNewAlarm(String title , String link, char app , ArrayList<PendingIntent> pList , String repeatOn , String time){
       Alarm alarm = new Alarm();

       alarm.title = title;
       alarm.link = link;
       alarm.app = app;
       alarm.pendingIntent = pList;
       alarm.repeatOn = repeatOn;
       alarm.time = time;

       return alarm;

    }
}



