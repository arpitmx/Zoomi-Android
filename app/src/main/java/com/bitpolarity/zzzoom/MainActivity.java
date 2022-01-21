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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.effect.Effect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
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
    int[] bool;
    Vibrator vibrator;
    VibrationEffect  vibrationEffect ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("zzzoompref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);
        }

        binding.appbar.getRoot().setElevation(5f);
        bool = new int[]{0, 0, 0, 0, 0, 0, 0};
        new Handler().postDelayed(this::showSnack,2000);

        setDayselector();
        binding.timePicker.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_in));
//        final Window win= getWindow();
//        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        linkEd = binding.link;
        builder = new AlertDialog.Builder(this);

        checkOverlayPermission();

        alarmTimePicker =binding.timePicker;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        binding.appbar.save.setOnClickListener(view -> {
              verifyAndSetAlarms();
        });

    }

    void showSnack(){

//        Snackbar snackbar = Snackbar
//                .make(binding.getRoot(), "Sroll down for more options", Snackbar.LENGTH_LONG)
//                .setAction("see", view -> {
//                    binding.scroll.post(() -> binding.scroll.fullScroll(View.FOCUS_DOWN));
//                });
//        snackbar.setActionTextColor(Color.RED);
//        vibrator.vibrate(100);
//        snackbar.show();


        int count = sharedPreferences.getInt("sc",0);
        //Toast.makeText(this,count, Toast.LENGTH_SHORT).show();

        if (count == 0){
           showSnak(0);

        }else if (count<5) {
            count = sharedPreferences.getInt("sc",0);
            showSnak(count);
        }


    }


    void showSnak(int count){

        binding.snakb.getRoot().setVisibility(View.VISIBLE);
        binding.snakb.getRoot().setAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_down));
        binding.snakb.getRoot().setOnClickListener(view -> binding.scroll.post(() -> binding.scroll.fullScroll(View.FOCUS_DOWN)));

        new Handler().postDelayed(() -> {
            binding.snakb.getRoot().setAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_up));
            binding.snakb.getRoot().setVisibility(View.GONE);
        },3000);

        editor.putInt("sc", count +1);
        editor.commit();

    }

    void setDayselector(){

        binding.ds.m.setOnClickListener(view -> {
            if (bool[0]==0){
                binding.ds.m.setTextColor(getColor(R.color.publist_yellow));
                bool[0] = 1;
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }
            }else {
                binding.ds.m.setTextColor(getColor(R.color.unclicked));
                bool[0] = 0;
            }
        });

        binding.ds.t.setOnClickListener(view -> {
            if (bool[1]==0){
                binding.ds.t.setTextColor(getColor(R.color.publist_yellow));
                bool[1] = 1;
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }

            }else {
                binding.ds.t.setTextColor(getColor(R.color.unclicked));
                bool[1] = 0;
            }
        });

        binding.ds.w.setOnClickListener(view -> {
            if (bool[2]==0){
                binding.ds.w.setTextColor(getColor(R.color.publist_yellow));
                bool[2] = 1;
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }
            }else {
                binding.ds.w.setTextColor(getColor(R.color.unclicked));
                bool[2] = 0;
            }
        });

        binding.ds.thurs.setOnClickListener(view -> {
            if (bool[3]==0){
                binding.ds.thurs.setTextColor(getColor(R.color.publist_yellow));
                bool[3] = 1;
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }

            }else {
                binding.ds.thurs.setTextColor(getColor(R.color.unclicked));
                bool[3] = 0;
            }
        });

        binding.ds.fri.setOnClickListener(view -> {
            if (bool[4]==0){
                binding.ds.fri.setTextColor(getColor(R.color.publist_yellow));
                bool[4] = 1;

                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }

            }else {
                binding.ds.fri.setTextColor(getColor(R.color.unclicked));
                bool[4] = 0;
            }
        });

        binding.ds.sat.setOnClickListener(view -> {
            if (bool[5]==0){
                binding.ds.sat.setTextColor(getColor(R.color.publist_yellow));
                bool[5] = 1;
                vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }

            }else {
                binding.ds.sat.setTextColor(getColor(R.color.unclicked));
                bool[5] = 0;
            }
        });

        binding.ds.sun.setOnClickListener(view -> {
            if (bool[6]==0){
                binding.ds.sun.setTextColor(getColor(R.color.publist_yellow));
                bool[6] = 1;

                    vibrator.cancel();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(vibrationEffect);
                }

            }else {
                binding.ds.sun.setTextColor(getColor(R.color.unclicked));
                bool[6] = 0;

            }
        });

    }

    void verifyAndSetAlarms(){
        if(!isrepeated()){
            setOneTimeAlarm();
        }else {
           setRepeatedAlarm(bool);
        }
    }

    void setRepeatedAlarm(int[] arr){
        for (int i= 0; i<7; i++){
            if (arr[i]==1){
                if (i==0) repeatedAlarmOn(Calendar.MONDAY);  // Monday =2
                else if (i==1) repeatedAlarmOn(Calendar.TUESDAY);
                else if (i==2) repeatedAlarmOn(Calendar.WEDNESDAY);
                else if (i==3) repeatedAlarmOn(Calendar.THURSDAY);
                else if (i==4) repeatedAlarmOn(Calendar.FRIDAY);
                else if (i==5) repeatedAlarmOn(Calendar.SATURDAY);
                else repeatedAlarmOn(Calendar.SUNDAY); // Sunday = 1

            }
        }
        Toast.makeText(this, "Repeated alarm set!", Toast.LENGTH_SHORT).show();
    }

    boolean isrepeated() {
        boolean repeated = false;
        int c = 0;
        for (int i : bool) {
            if (i == 1) {
                c++;
            }
        }

        if (c > 0) {
            repeated = true;
            return repeated;
        } else {
            return repeated;
        }
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

    void repeatedAlarmOn(int day){
        Calendar calender= Calendar.getInstance();

        calender.set(Calendar.DAY_OF_WEEK, day);  //here pass week number
        calender.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());  //pass hour which you have select
        calender.set(Calendar.MINUTE, alarmTimePicker.getMinute());  //pass min which you have select
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if (calender.before(now)) {    //this condition is used for future reminder that means your reminder not fire for past time
            calender.add(Calendar.DATE, 7);
        }

        final int _id = (int) System.currentTimeMillis();  //this id is used to set multiple alarms

        Intent intent = new Intent(this, AlarmReciever.class);
        intent.putExtra("link","https://zoom.us/j/92008726162?pwd=NUxScm9tVm5PWm1FUHIzQVJvTHE4Zz09");
        pendingIntent = PendingIntent.getBroadcast(this, _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, pendingIntent);

    }

    public void setOneTimeAlarm() {

        long time;
        String link = linkEd.getText().toString();

            if (!link.equals("")){

            Toast.makeText(MainActivity.this, "Alarm set to ring once!", Toast.LENGTH_SHORT).show();

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

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }else{

            binding.link.setError("Link is required");
            binding.link.hasFocus();
            Toast.makeText(this,"Paste link first", Toast.LENGTH_SHORT).show();
        }}

    }

