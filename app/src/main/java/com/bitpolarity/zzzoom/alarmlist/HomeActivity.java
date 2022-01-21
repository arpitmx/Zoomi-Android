package com.bitpolarity.zzzoom.alarmlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.bitpolarity.zzzoom.AlarmSetActivity;
import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {


    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAddAlarm.setOnClickListener(view -> {
                startActivity(new Intent(this, AlarmSetActivity.class));
        });

    }
}