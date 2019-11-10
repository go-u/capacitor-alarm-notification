package com.go.capacitor.alarm.notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Class used to create notification from timer event
 * Note: Class is being registered in Android manifest as broadcast reciever
 */
public class TimedNotificationPublisher extends BroadcastReceiver {
    /**
     * Restore and present notification
     */
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent);
        monitorOnAtNotification(context);
    }

    public void showNotification(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(CapacitorAlarmNotification.NOTIFICATION_INTENT_KEY);
        // alway single notification
        Integer notificationID = 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);
    }

    public void monitorOnAtNotification(Context context) {
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,":Alarm");
        wl.acquire(10000);
    }
}