package com.aae.medminder.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class NotificationScheduler {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    public static void scheduleNotification(Context context, Notification notification,int notificationId , Calendar date) {
        PendingIntent pendingIntent = createPendingIntent(context,notification,notificationId);
        AlarmManager alarmManager = getAlarmManager(context);
        if(alarmManager!=null) alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }

    public static void scheduleInexactRepeatingNotification(Context context, Notification notification,int notificationId , Calendar date, long interval) {
        PendingIntent pendingIntent = createPendingIntent(context,notification,notificationId);
        AlarmManager alarmManager = getAlarmManager(context);
        if(alarmManager != null) alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), interval, pendingIntent);
    }

    public static void scheduleRepeatingNotification(Context context, Notification notification,int notificationId , Calendar date, long interval) {
        PendingIntent pendingIntent = createPendingIntent(context,notification,notificationId);
        AlarmManager alarmManager = getAlarmManager(context);
        if(alarmManager != null) alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), interval, pendingIntent);
    }

    private static PendingIntent createPendingIntent(Context context, Notification notification,int notificationId){
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private static AlarmManager getAlarmManager(Context context){ return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);}

    public static Notification createNotification(Context context, String title, String content,int smallIconIndex , int largeIconIndex, Class<?> targetClass) {

        return new NotificationCompat.Builder(context,default_notification_channel_id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIconIndex)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIconIndex))
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, targetClass), PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
    }

    public static void cancelNotification(Context context, int notificationId){
        //Since notificationId information is enough we don't add extras.
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
