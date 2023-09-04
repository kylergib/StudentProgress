package com.domain.studentprogress.util;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kgibs87.studentprogress.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "CourseDate";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel_id);

        createNotification(context, intent.getStringExtra("message"));
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT < 26) return;

        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getResources().getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel("StudentProgress", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void createNotification(Context context, String text) {
        Notification notification = new NotificationCompat.Builder(context, "StudentProgress")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(notificationID++, notification);
    }
}