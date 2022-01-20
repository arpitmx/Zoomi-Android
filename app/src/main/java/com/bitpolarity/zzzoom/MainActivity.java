package com.bitpolarity.zzzoom;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    AlertDialog.Builder builder;
    EditText linkEd;
    Button toggleButton;
    ActivityMainBinding binding;
    MaterialTimePicker materialTimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        linkEd = binding.link;
        builder = new AlertDialog.Builder(this);
        toggleButton = binding.toggleButton;

        checkOverlayPermission();

        alarmTimePicker =binding.timePicker;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggleButton.setOnClickListener(view -> OnToggleClicked(view));



    }


    void showDialog(Intent intent){

        builder.setMessage("Zzzoom needs 'Draw over apps' permission in order to automatically open Zoom links ") .setTitle("Permission needed")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    startActivityForResult(intent, 0);
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void checkOverlayPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                showDialog(intent);

            }
        }
    }




    // OnToggleClicked() method is implemented the time functionality
    public void OnToggleClicked(View view) {

        long time;
        String link = linkEd.getText().toString();

        if (((ToggleButton) view).isChecked()) {
            if (!link.equals("")){
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

            Intent intent = new Intent(this, AlarmReciever.class);
            intent.putExtra("link",link);

            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);

            }
            Log.d("Time : ", "OnToggleClicked: ");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time,pendingIntent);
        }else{
            toggleButton.setEnabled(false);
            Toast.makeText(this,"Paste link first", Toast.LENGTH_SHORT).show();
        }}
        else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }
}
