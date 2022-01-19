package com.bitpolarity.zzzoom;

import static com.bitpolarity.zzzoom.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SleepService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//        Intent app = new Intent(getApplicationContext(), MainActivity.class);
//        app.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getApplicationContext().startActivity(app);

        Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_SHORT).show();
        Intent zzi = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,zzi, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Zzzoom")
                .setContentText("Meeting going on...")
                .setSmallIcon(R.drawable.service_icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Uri uri = Uri.parse("https://us04web.zoom.us/j/73445245510?pwd=mu58YHpRBIP9E5JS-a4ubP4atzz9R2.1"); // missing 'http://' will cause crashed
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }, 5000);




        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
