package com.go.capacitor.alarm.notification;


// capacitor defalut
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

// added
import android.Manifest;
import android.content.Intent;
import android.content.Context;
import java.util.Calendar;
import java.util.Date;
import android.app.PendingIntent;
import android.app.Activity;
import android.os.Vibrator ;
import android.os.VibrationEffect ;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.R.drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;


@NativePlugin(
        permissions={
                Manifest.permission.VIBRATE,
                Manifest.permission.SET_ALARM,
                Manifest.permission.WAKE_LOCK
        }
)
public class CapacitorAlarmNotification extends Plugin {
    private Context context;
    private Activity activity;
    private Integer requestCode = 1;
    public static String NOTIFICATION_INTENT_KEY = "CAPACITOR_ALARM_NOTIFICATION";
    // create notification channel
    private final String ID_NOTIFICATION_CH_VIBRATION = "ID_NOTIFICATION_CH_VIBRATION";
    private final String ID_NOTIFICATION_CH_SOUND = "ID_NOTIFICATION_CH_SOUND";
    private NotificationChannel confNotificationChannelVibration = configureNotificationChannel(false, ID_NOTIFICATION_CH_VIBRATION);
    private NotificationChannel confNotificationChannelSound = configureNotificationChannel(true, ID_NOTIFICATION_CH_SOUND);

    @PluginMethod()
    public void setAlarm(PluginCall call) {
        // vibrationTest(); // vibration permission test
        this.activity = getActivity();
        this.context = getActivity();

        Boolean setAlarmResult = false;
        if (!hasRequiredPermissions()) {
            // request permission
            pluginRequestAllPermissions();
            JSObject json = new JSObject();
            json.put("result", setAlarmResult);
            // return { result: false }
            call.success(json);
        } else {
            Integer sec = call.getInt("sec");
            Boolean soundMode = call.getBoolean("sound", false);
            String notificationTitle = call.getString("title", "Alarm");
            String notificationText = call.getString("text", "time up");
            NotificationManager nManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            nManager.createNotificationChannel(confNotificationChannelVibration);
            nManager.createNotificationChannel(confNotificationChannelSound);
            NotificationChannel notificationChannel = confNotificationChannelVibration;
            if (soundMode) {
                notificationChannel = confNotificationChannelSound;
            }
            // set alarm
            setAlarm(sec, notificationChannel, notificationTitle, notificationText);
            setAlarmResult = true;
        }
        JSObject json = new JSObject();
        json.put("result", setAlarmResult);
        call.success(json);
    }

    private void setAlarm(Integer sec, NotificationChannel channel, String title, String text) {
        // create notification
        Notification notification = createNotification(context, channel, title, text);

        // create notification intent from notification
        Intent notificationIntent = new Intent(context, TimedNotificationPublisher.class);
        notificationIntent.putExtra(NOTIFICATION_INTENT_KEY, notification);
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, requestCode, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // clock intent is used when user touch clock icon
        Intent clockIconIntent = new Intent(context, activity.getClass());
        PendingIntent clockIconPendingIntent = PendingIntent.getActivity(context, requestCode, clockIconIntent, PendingIntent.FLAG_CANCEL_CURRENT);

//日時と発行するIntentをAlarmManagerにセットします
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        long trigger = currentTime.getTime() + 1000 * sec;
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(trigger, clockIconPendingIntent);
        manager.setAlarmClock(alarmClockInfo, notificationPendingIntent);
    }


    // for vibration permission test
    private void vibrationTest() {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = new long[]{0, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800};
        vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
    }

    /*-
    修正後は、アプリのアンインストール/再インストールが必要
     */
    private NotificationChannel configureNotificationChannel(Boolean soundMode, String channelId) {
        // user read this channel name in android setting.
        String channelName = "Vibration Alarm";
        if (soundMode) {
            channelName = "Sound Alarm";
        }

        Integer importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(
                channelId,
                channelName,
                importance
        );

        // default no sound / vibration only
        if (!soundMode) {
            notificationChannel.setVibrationPattern(new long[]{0, 800, 800, 800, 800, 800, 800, 800});
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(null, null);
        } else {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            AudioAttributes soundAttr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            notificationChannel.setSound(soundUri, soundAttr);
        }
        notificationChannel.enableLights(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return notificationChannel;
    }

    private Notification createNotification(Context context, NotificationChannel channel, String title, String text) {
        Intent intent = buildIntent(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(getContext(), channel.getId())
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setOngoing(false)
                .setSmallIcon(drawable.star_on)
                .setChannelId(channel.getId())
                .setContentIntent(pendingIntent)
                .build();
        return  notification;
    }

    private Intent buildIntent(Context context) {
        Intent intent = new Intent(context, activity.getClass());
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }
}