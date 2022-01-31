package com.bitpolarity.zzzoom.db;


import android.app.PendingIntent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    public int alarm_id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "link")
    public String link ;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "repeatDays")
    public String repeatOn;

    @ColumnInfo(name = "app")
    public char app;

    @ColumnInfo(name = "pendingIntentList")
    public ArrayList<PendingIntent> pendingIntent;

    @ColumnInfo(name = "isRepeated")
    public Boolean isRepeated;

    @ColumnInfo(name = "isActive")
    public  Boolean isActive;

}