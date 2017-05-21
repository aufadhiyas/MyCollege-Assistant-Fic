package me.citrafa.mycollegeassistant.OperationRealm;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;

/**
 * Created by SENSODYNE on 22/04/2017.
 */

public class CatatanOperation {
    private static String TAG = CatatanModel.class.getSimpleName();
    RealmBaseActivity rba;
    Realm realm;
    CatatanModel cm;

    public void tambahCatatan(final CatatanModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                if (obj.getWaktu_c() !=null){
                    DateStorageModel dso = new DateStorageModel(getIdDate(),obj.getNo_c(),"CatatanModel",obj.getWaktu_c(),null,true);
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
    public void updateCatatan(final CatatanModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                if (obj.getWaktu_c() !=null){
                    final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","CatatanModel").equalTo("id_model",obj.getNo_c()).findFirst();
                    if (dso !=null){
                        dso.setDateS(obj.getWaktu_c());
                        dso.setStatus(true);
                    }else{
                        DateStorageModel ds = new DateStorageModel(getIdDate(),obj.getNo_c(),"CatatanModel",obj.getWaktu_c(),null,true);
                        realm.copyToRealmOrUpdate(ds);
                    }
                }else{
                    final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","CatatanModel").equalTo("id_model",obj.getNo_c()).findFirst();
                    if (dso!=null){
                        dso.setStatus(false);
                    }
                }
            }
        });

    }
    public void hapusCatatan(int id){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CatatanModel cm = realm.where(CatatanModel.class).equalTo("no_c",id).findFirst();
        final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","CatatanModel").equalTo("id_model",id).findFirst();
        if (cm !=null){
            cm.setUpdated_at(getCurrentTimeStamp());
            cm.setStatus(false);
            if (cm.getWaktu_c()!=null){
                if (dso!=null){
                    dso.setStatus(false);
                }
            }
        }
        realm.commitTransaction();

    }
    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(CatatanModel.class).max("no_c");
        int nextID;
        if (currentID == null){
            nextID = 1;
        }else{
            nextID = currentID.intValue() + 1;
        }
        return nextID;
    }
    public RealmResults<CatatanModel> getAllJadwalKuliah(){
        return realm.where(CatatanModel.class).findAll();
    }

    public int getLastId(){
        realm.getDefaultInstance();
        int lastID;
        Number number = realm.where(CatatanModel.class).max("no_c");
        lastID = number.intValue();
        return lastID;

    }
    public void deleteItemAsync(final Realm realm, final int id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                              CatatanModel jk = realm.where(CatatanModel.class).equalTo("no_c", id).findFirst();
                                              if (jk !=null){
                                                  jk.deleteFromRealm();
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
