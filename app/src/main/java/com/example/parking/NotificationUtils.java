package com.example.parking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {

    private static final int SIMPLE_MSG_TEXT_INTENT_ID = 1;
    private static final String DAILY_REMINDER_NOTIFICATION_CHANNEL_ID = "Channel";

    public static void notifyText(Context context, String text, Word word) {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        DAILY_REMINDER_NOTIFICATION_CHANNEL_ID,
                        DAILY_REMINDER_NOTIFICATION_CHANNEL_ID,
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, DAILY_REMINDER_NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(text)
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                            text))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(getIntent(context,word))
                    .setAutoCancel(true);


            // set the notification's priority to PRIORITY_HIGH.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }

            notificationManager.notify(SIMPLE_MSG_TEXT_INTENT_ID, notificationBuilder.build());
        }

    public static Notification getServiceNotification(Context context, String text) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    DAILY_REMINDER_NOTIFICATION_CHANNEL_ID,
                    DAILY_REMINDER_NOTIFICATION_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, DAILY_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(text)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        text))
                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setContentIntent(getIntent(context,word))
                .setAutoCancel(true);


        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        return notificationBuilder.build();
    }

    private static PendingIntent getIntent(Context context, Word word) {
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
