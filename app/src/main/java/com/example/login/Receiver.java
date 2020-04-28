package com.example.login;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.login.Channel.CHANNEL_ID;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context , CandidateDetails.class);
        Bundle b = intent.getExtras();
        Bundle b1 = new Bundle();
        b1.putString("name",b.getString("name"));
        b1.putInt("age",b.getInt("age"));
        b1.putLong("mobileno",b.getLong("mobileno"));
        b1.putString("tickettype",b.getString("tickettype"));
        b1.putString("servicetype",b.getString("servicetype"));
        b1.putString("departure",b.getString("departure"));
        b1.putString("arrival",b.getString("arrival"));
        b1.putString("dates",b.getString("dates"));
        b1.putString("times",b.getString("times"));
        i.putExtras(b1);
        PendingIntent pi = PendingIntent.getActivity(context ,0 ,  i , 0);
        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context , CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                .setContentTitle("Success, Your Ticket Has Been Booked!")
                .setContentText("Click for details (for confirmation, please proceed with payment later)")
                .setColor(Color.BLUE)
                .setContentIntent(pi)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManagerCompat.notify(1 , notification);
    }
}
