package com.example.lkh.printec;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
/**
 * Created by sayali on 26/3/18.
 */

public class FirebaseMsgService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //create notification
        System.out.println("Cloudmsg");
        createNotification(remoteMessage.getNotification().getBody());
    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


    Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Notif")
            .setContentText(messageBody)
            .setAutoCancel( true )
            .setSound(notificationSoundURI)
            .setContentIntent(resultIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
}
}
