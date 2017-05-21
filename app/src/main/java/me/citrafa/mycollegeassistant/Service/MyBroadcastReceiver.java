package me.citrafa.mycollegeassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by SENSODYNE on 22/05/2017.
 */

public class MyBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent service  = new Intent(context,LocalService.class);
            startWakefulService(context,service);
        }else if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction())){
            Intent service  = new Intent(context,LocalService.class);
            context.stopService(service);
            startWakefulService(context,service);
        }
    }
}
