package me.citrafa.mycollegeassistant.OperationRealm;

import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.TugasModel;

/**
 * Created by SENSODYNE on 27/04/2017.
 */

public class TugasOperation {
    private static String TAG = TugasOperation.class.getSimpleName();
    Realm realm;

    public void TambahData(final TugasModel obj){
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
                if (obj.getWaktu_t()!=null){
                    DateStorageModel dso =new DateStorageModel(getIdDate(),obj.getNo_t(),"TugasModel",obj.getWaktu_t(),null,true);
                    realm.copyToRealm(dso);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            public void onSuccess() {
                Log.d(TAG, "Berhasil Menyimpan Data Di Realm");
                Log.d(TAG, "Path : " + realm.getPath());
            }
        });
    }
    public void updatedata(final TugasModel obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        if (obj.getWaktu_t() !=null){
            final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","TugasModel").equalTo("id_model",obj.getNo_t()).findFirst();
            if (dso !=null){
                dso.setDateS(obj.getWaktu_t());
                dso.setStatus(true);
            }else{
                DateStorageModel ds = new DateStorageModel(getIdDate(),obj.getNo_t(),"TugasModel",obj.getWaktu_t(),null,true);
                realm.copyToRealmOrUpdate(ds);
            }
        }else{
            final DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","TugasModel").equalTo("id_model",obj.getNo_t()).findFirst();
            if (dso!=null){
                dso.setStatus(false);
            }
        }
        realm.commitTransaction();

    }
    public void hapusData(final int No){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TugasModel tm = realm.where(TugasModel.class).equalTo("no_t",No).findFirst();
                tm.setUpdated_at(getCurrentTimeStamp());
                tm.setStatus_t(false);
                DateStorageModel dso = realm.where(DateStorageModel.class).equalTo("modelName","TugasModel").equalTo("id_model",tm.getNo_t()).findFirst();
                if (dso !=null) {
                        dso.setStatus(false);
                }
            }
        });

    }
    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(TugasModel.class).max("no_t");
        int nextID;
        if (currentID == null){
            nextID = 1;
        }else{
            nextID = currentID.intValue() + 1;
        }
        return nextID;
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
