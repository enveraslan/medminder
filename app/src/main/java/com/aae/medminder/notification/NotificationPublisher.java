package com.aae.medminder.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;
import static com.aae.medminder.notification.NotificationScheduler.NOTIFICATION_CHANNEL_ID;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATION_CHANNEL_NAME = "Medminder";

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if(notificationManager == null) return;

        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MAX);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(notificationId, notification);
    }
}
