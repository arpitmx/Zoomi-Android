package com.bitpolarity.zzzoom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
    
public class AlarmReciever extends BroadcastReceiver {

   public AlarmReciever(){
   }
    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {

       Toast.makeText(context.getApplicationContext(), "BroadCast started", Toast.LENGTH_SHORT).show();

       Intent serviceI = new Intent(context,SleepService.class);
       context.startService(serviceI);

   }
}




// we will use vibrator first
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(10000);

//        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//
//        // setting default ringtone
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//
//        // play ringtone
//        ringtone.play();