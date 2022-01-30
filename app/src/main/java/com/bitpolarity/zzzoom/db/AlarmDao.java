package com.bitpolarity.zzzoom.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {

    @Query("SELECT * FROM Alarm")
    List<Alarm> getAllAlarms();

    @Insert
    void insertAlarm(Alarm...alarms);

    @Delete
    void deleteNote(Alarm alarm);

//    @Query("UPDATE Note SET title = :new_title , `desc` = :new_desc WHERE note_id == :nid")
//    void update(int nid , String new_title, String new_desc );
//
//



}
