package me.citrafa.mycollegeassistant.OperationRealm;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;

/**
 * Created by SENSODYNE on 18/04/2017.
 */

public class JadwalLainOperation {
    private static String TAG = JadwalLainOperation.class.getSimpleName();
    RealmBaseActivity rba;
    Realm realm;
    JadwalLainModel jum;

    public void tambahJadwalLain(final JadwalLainModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                if (obj.getStatus_jl()!=null) {
                    DateStorageModel dso = new DateStorageModel(getIdDate(), obj.getNo_jl(), "JadwalLainModel", obj.getWaktus_jl(), obj.getWaktuf_jl(), true);
                    realm.copyToRealm(dso);
                }

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
    public void editJadwalLain(final JadwalLainModel obj){
        realm = Realm.getDefaultInstance();
        final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("id_model",obj.getNo_jl()).equalTo("modelName","JadwalLainModel").findFirst();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                if (dso !=null) {
                    dso.setDateS(obj.getWaktus_jl());
                    dso.setDateF(obj.getWaktuf_jl());
                }
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
    public void delete(final JadwalLainModel obj){
        realm = Realm.getDefaultInstance();
        final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("id_model",obj.getNo_jl()).equalTo("modelName","JadwalLainModel").findFirst();
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
        realm.beginTransaction();
                obj.setStatus_jl(false);
                obj.setUpdated_at(getCurrentTimeStamp());
                if (dso!=null){
                    dso.setStatus(false);
                }
        realm.commitTransaction();
//            }
//        });
    }
    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(JadwalLainModel.class).max("no_jl");
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
        Number number = realm.where(JadwalLainModel.class).max("no_jk");
        lastID = number.intValue();
        return lastID;

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

}
