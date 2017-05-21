package me.citrafa.mycollegeassistant.OperationRealm;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalUjianModel;

/**
 * Created by SENSODYNE on 18/04/2017.
 */

public class JadwalUjianOperation {
    private static String TAG = "LOG";
    Realm realm;
    JadwalUjianModel jum;

    public void tambahJadwalUjian(final JadwalUjianModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                DateStorageModel ds = new DateStorageModel(getIdDate(),obj.getNo_ju(),"JadwalUjianModel",obj.getWaktu(),null,true);
                realm.copyToRealm(ds);
            }
        }, new Realm.Transaction.OnSuccess() {
            public void onSuccess() {
                Log.d(TAG, "Berhasil Menyimpan Data Di Realm");
                Log.d(TAG, "Path : " + realm.getPath());
            }
        });
        realm.beginTransaction();
        realm.commitTransaction();
    }
    public void updateJadwalUjian(final JadwalUjianModel obj){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                DateStorageModel ds = realm.where(DateStorageModel.class).equalTo("modelName","JadwalUjianModel").equalTo("id_model",obj.getNo_ju()).findFirst();
                if (ds!=null){
                    ds.setDateS(obj.getWaktu());
                }
            }
        });
    }
    public void hapusJadwalUjian(final int ind){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        JadwalUjianModel ju = realm.where(JadwalUjianModel.class).equalTo("no_ju",ind).findFirst();
        ju.setStatus_ju(false);
        ju.setUpdated_at(getCurrentTimeStamp());
        DateStorageModel ds = realm.where(DateStorageModel.class).equalTo("modelName","JadwalUjianModel").equalTo("id_model",ind).findFirst();
        if (ds!=null){
            ds.setStatus(false);
        }
        realm.commitTransaction();
    }




    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(JadwalUjianModel.class).max("no_ju");
        int nextID;
        if (currentID == null){
            nextID = 1;
        }else{
            nextID = currentID.intValue() + 1;
        }
        return nextID;
    }

    public int getLastId(){
        realm.getDefaultInstance();
        int lastID;
        Number number = realm.where(JadwalUjianModel.class).max("no_jk");
        lastID = number.intValue();
        return lastID;

    }
    public int getIdDate(){
        realm = Realm.getDefaultInstance();
        Number number = realm.where(DateStorageModel.class).max("id");
        int nextid;
        if (number ==null){
            nextid = 1;
        }else{
            nextid = number.intValue()+1;
        }
        return nextid;
    }
    public static Date getCurrentTimeStamp(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        try {
            return sdfDate.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }
}
