package com.bitpolarity.zzzoom.alarmlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.bitpolarity.zzzoom.AlarmSetActivity;
import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.databinding.ActivityHomeBinding;
import com.bitpolarity.zzzoom.db.Alarm;
import com.bitpolarity.zzzoom.db.AppDatabase;
import com.bitpolarity.zzzoom.db.DBManager;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements AlarmAdapter.ULEventListner {


    ActivityHomeBinding binding;
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;
    List<Alarm> alarmList;
    DBManager dbManager;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbManager =  new ViewModelProvider(this).get(DBManager.class);
        db = dbManager.getDb();

        recyclerView = binding.alarList;
        alarmList = db.alarmDao().getAllAlarms();
        initRecyclerView(alarmList);

        binding.fabAddAlarm.setOnClickListener(view -> {
                startActivity(new Intent(this, AlarmSetActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmList = dbManager.getAlarmList();
        loadNoteList(alarmList);
    }

    public void loadNoteList(List<Alarm> noteList){
            alarmAdapter.setAlarmList(noteList);
    }

    public void initRecyclerView(List<Alarm> alarmList){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);

        alarmAdapter= new AlarmAdapter(this, this);
        alarmAdapter.setAlarmList(alarmList);
        recyclerView.setAdapter(alarmAdapter);
    }

    @Override
    public void onClick(int position) {

    }
}