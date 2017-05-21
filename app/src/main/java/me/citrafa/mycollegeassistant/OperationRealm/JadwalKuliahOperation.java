package me.citrafa.mycollegeassistant.OperationRealm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;

/**
 * Created by SENSODYNE on 09/04/2017.
 */

public class JadwalKuliahOperation {
    private static String TAG = JadwalKuliahOperation.class.getSimpleName();
    RealmBaseActivity rba;
    Realm realm;
    JadwalKuliahModel jk;

    public int getJadwalKuliahIsEmpty() {
        final int i = 0;
        final int ada = 1;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JadwalKuliahModel> JKM = realm.where(JadwalKuliahModel.class).findAll();
        realm.commitTransaction();
        if (JKM != null) {
            return ada;
        }else{
            return i;
        }

    }

    public void tambahJadwalKuliah(final JadwalKuliahModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
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
    public void editJadwalKuliah(final JadwalKuliahModel obj){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
            }
        });
    }
    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(JadwalKuliahModel.class).max("no_jk");
        int nextID;
        if (currentID == null){
            nextID = 1;
        }else{
            nextID = currentID.intValue() + 1;
        }
        return nextID;
    }
    public RealmResults<JadwalKuliahModel> getAllJadwalKuliah(){
        return realm.where(JadwalKuliahModel.class).findAll();
    }

    public int getLastId(){
        realm.getDefaultInstance();
        int lastID;
        Number number = realm.where(JadwalKuliahModel.class).max("no_jk");
        lastID = number.intValue();
        return lastID;

    }
    public void deleteItemAsync(final Realm realm, final int id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                JadwalKuliahModel jk = realm.where(JadwalKuliahModel.class).equalTo("no_jk", id).findFirst();
                RealmResults jks = realm.where(JadwalKuliahModel.class).findAll();
                if (jk != null) {
                        jk.setStatus_jk(false);
                        jk.setUpdated_at(getCurrentTimeStamp());
                }

            }
            }, new Realm.Transaction.OnSuccess() {
            public void onSuccess() {
                Log.d(TAG, "Berhasil Hapus Data Di Realm ID : " + id);
                Log.d(TAG, "Path : " + realm.getPath());
            }
            }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "gagal " + error);
            }
            }
        );
    }
    public static void deleteMultiItem(Realm realm, Collection<Integer> ids){
        final Integer[] idsToDelete = new Integer[ids.size()];
        ids.toArray(idsToDelete);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Integer id : idsToDelete){
                    JadwalKuliahModel jadwalKuliahModel = realm.where(JadwalKuliahModel.class).equalTo("no_jk",id).findFirst();
                    if(jadwalKuliahModel !=null){
                        jadwalKuliahModel.setStatus_jk(false);
                        jadwalKuliahModel.setUpdated_at(getCurrentTimeStamp());
                    }
                }
            }
        });


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
