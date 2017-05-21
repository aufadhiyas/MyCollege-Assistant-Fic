package me.citrafa.mycollegeassistant.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import me.citrafa.mycollegeassistant.AppController.SessionManager;

/**
 * Created by SENSODYNE on 22/05/2017.
 */

public class MyBroadcastReceiver extends WakefulBroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    SessionManager session;

    @Override
    public void onReceive(Context context, Intent intent) {
        session = new SessionManager(context);
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent service  = new Intent(context,LocalService.class);
            startWakefulService(context,service);
        }else if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction())){
            Intent service  = new Intent(context,LocalService.class);
            context.stopService(service);
            startWakefulService(context,service);
        }
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        if (session.getStatTimer()){
            notificationManager.notify(id,notification);
            Intent service  = new Intent(context,LocalService.class);
            context.stopService(service);
            startWakefulService(context,service);
        }else {
            notificationManager.cancelAll();
            Intent service  = new Intent(context,LocalService.class);
            context.stopService(service);
            startWakefulService(context,service);
        }
    }
}
