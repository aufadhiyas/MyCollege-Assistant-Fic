package me.citrafa.mycollegeassistant.Service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalUjianModel;
import me.citrafa.mycollegeassistant.ModelClass.TugasModel;
import me.citrafa.mycollegeassistant.R;

import static java.lang.Long.valueOf;

/**
 * Created by SENSODYNE on 21/05/2017.
 */

public class LocalService extends IntentService {
    SessionManager session;
    Realm realm;
    private final static String TAG = "Service";
    long millisJamJK;
    long time;



    public LocalService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        session = new SessionManager(this);
        getJadwalEarly();
    }


    public void getJadwalEarly() {
        realm = Realm.getDefaultInstance();
        int Time = session.getTimer();
        time = valueOf(Time) * 60 * 1000;
        int noHari = 1;
        final int year0 = 2011;
        final int month0 = 1;
        final int day0 = 1;
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);
        int jam = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        long jadis = 10000;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedDate = sdf.format(new Date(year0, month0, day0, jam, minutes, second));
        String currentDate = sdf.format(new Date(Year, Month, Day, jam, minutes, second));
        try {
            Date dateMowForJK = sdf.parse(formatedDate);
            Date currentDates = sdf.parse(currentDate);
            JadwalKuliahModel jkm = realm.where(JadwalKuliahModel.class).equalTo("nohari", noHari).greaterThan("waktu_jk", dateMowForJK).findFirst();
            DateStorageModel dsm = realm.where(DateStorageModel.class).greaterThan("dateS", currentDates).findFirst();
            Log.d(TAG, "waktu : CEK ");
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            String JK = hour.format(jkm.getWaktu_jk());
            String DSM = hour.format(dsm.getDateS());
            Date dateJK = hour.parse(JK);
            Date dateDSM = hour.parse(DSM);
            if (jkm != null) {
                if (dsm != null) {
                    if (dateJK.getTime() < dateDSM.getTime()) {
                        long jadi = dateJK.getTime();
                        scheduleNotification(getNotification(jkm.getMakul_jk(), "di " + jkm.getKelas_jk()), jadi);
                    } else if (dateJK.getTime() > dateDSM.getTime()) {
                        DSMSELECTOR(dsm, dateDSM.getTime());
                    }
                }
            } else {
                if (dsm != null) {
                    DSMSELECTOR(dsm, dateDSM.getTime());
                } else {
                    scheduleNotification(getNotification("Hari Ini Libur", "Selamat Beristirahat"), 5000);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "waktu : error");
        }
    }

    private void scheduleNotification(Notification notification, long DariDatabase) {

        Intent notificationIntent = new Intent(this, MyBroadcastReceiver.class);
        notificationIntent.putExtra(MyBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyBroadcastReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = (DariDatabase - (System.currentTimeMillis() % 1000)) + time;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String Judul, String content) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(Judul);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setSound(uri);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setSmallIcon(R.drawable.ic_48);
        return builder.build();
    }

    public void DSMSELECTOR(DateStorageModel dsm, long s) {
        String Model = dsm.getModelName();

        int id = dsm.getId_model();
        if (Model.equals("CatatanModel")) {
            CatatanModel cm = realm.where(CatatanModel.class).equalTo("no_c", id).findFirst();
            if (cm != null) {
                scheduleNotification(getNotification(cm.getNama_c(), cm.getDeskripsi_c()), s);
            }
        } else if (Model.equals("JadwalLainModel")) {
            JadwalLainModel cm = realm.where(JadwalLainModel.class).equalTo("no_jl", id).findFirst();
            if (cm != null) {
                scheduleNotification(getNotification(cm.getNama_jl(), cm.getDeskripsi_jl()), s);
            }
        } else if (Model.equals("JadwalUjianModel")) {
            JadwalUjianModel cm = realm.where(JadwalUjianModel.class).equalTo("no_ju", id).findFirst();
            if (cm != null) {
                scheduleNotification(getNotification(cm.getNama_makul(), "di " + cm.getRuangan()), s);
            }
        } else {
            TugasModel cm = realm.where(TugasModel.class).equalTo("no_t", id).findFirst();
            if (cm != null) {
                scheduleNotification(getNotification(cm.getDeskripsi_t(), "Waktunya Kumpul Tugasnya"), s);
            }
        }
    }

}
