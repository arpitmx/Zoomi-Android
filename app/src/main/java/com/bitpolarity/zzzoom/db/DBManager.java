package com.bitpolarity.zzzoom.db;

import android.app.Application;
import android.app.PendingIntent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends AndroidViewModel {

    AppDatabase db;
    List<Alarm> alarmList;
    MutableLiveData<List<Alarm>> mList;

   public LiveData<List<Alarm>> getMAlarmList() {
        if (mList == null) {
            mList = new MutableLiveData<>();
            loadFruits();
        }
        return mList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    private void loadFruits() {
        // do async operation to fetch users
        Handler myHandler = new Handler();
        myHandler.postDelayed(() -> {

            mList.setValue(getAlarmList());
        }, 500);

    }

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

    public void updateAlarm_isActive(int nId, Boolean isActive){
        db.alarmDao().update(nId,isActive);
    }


   public Alarm createNewAlarm(String title , String link, char app , ArrayList<PendingIntent> pList , String repeatOn , String time, Boolean isRepeated){
       Alarm alarm = new Alarm();

       alarm.title = title;
       alarm.link = link;
       alarm.app = app;
       alarm.pendingIntent = pList;
       alarm.repeatOn = repeatOn;
       alarm.time = time;
       alarm.isRepeated = isRepeated;
       alarm.isActive = true;
       return alarm;

    }


    public Alarm createSingleNewAlarm(String setOn , String title , String link, char app , ArrayList<PendingIntent> pList , String time, Boolean isRepeated){
        Alarm alarm = new Alarm();

        alarm.title = title;
        alarm.link = link;
        alarm.app = app;
        alarm.pendingIntent = pList;
        alarm.time = time;
        alarm.isRepeated = isRepeated;
        alarm.isActive = true;
        alarm.repeatOn = setOn;

        return alarm;

    }
}



