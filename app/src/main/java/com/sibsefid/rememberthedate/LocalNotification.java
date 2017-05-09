package com.sibsefid.rememberthedate;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.sibsefid.R;
import com.sibsefid.rememberthedate.ui.MainActivity;
import com.zendesk.util.StringUtils;

public class LocalNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String message = intent.getExtras().getString("message", StringUtils.EMPTY_STRING);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        final Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setSmallIcon(R.mipmap.app_icon)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(context)
                .notify(0, notification);
    }
}