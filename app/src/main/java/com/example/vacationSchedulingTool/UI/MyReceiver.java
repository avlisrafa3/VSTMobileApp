package com.example.vacationSchedulingTool.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.vacationSchedulingTool.R;

//BroadcastReceiver to handle scheduled alarms and show notifications
public class MyReceiver extends BroadcastReceiver {
    String channel_id = "test"; // Notification channel ID
    static int notificationID; // Static variable to track notification IDs

    @Override
    public void onReceive(Context context, Intent intent) {
        // Show a toast with the received key (for testing purposes)
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();

        // Create a notification channel
        createNotificationChannel(context, channel_id);

        // Build and show the notification
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key")) // Content text from the intent
                .setContentTitle("NotificationTest") // Notification title
                .build();

        // Get the notification manager and show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification); // Increment notification ID for each new notification
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "mychannelname"; // Name of the notification channel
        String description = "mychanneldescription"; // Description of the notification channel
        int importance = NotificationManager.IMPORTANCE_DEFAULT; // Importance level of the notification channel

        // Create the notification channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        // Register the notification channel with the system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}