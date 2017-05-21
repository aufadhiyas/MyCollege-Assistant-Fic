package me.citrafa.mycollegeassistant.OperationRealm;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.ModelClass.DateStorageModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;

/**
 * Created by SENSODYNE on 27/04/2017.
 */

public class DateStorageOperation {
    private static String TAG = DateStorageOperation.class.getSimpleName();

    Realm realm;


    public void insertDatetoStorage(final DateStorageModel obj){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(obj);
            }
        }, new Realm.Transaction.OnSuccess() {
            public void onSuccess() {
                Log.d(TAG, "Berhasil Menyimpan di Date Storage");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "Gagal Menyimpan di Date Storage");
            }
        });


    }
    public int getNextId() {
        realm = Realm.getDefaultInstance();
        Number currentID = realm.where(DateStorageModel.class).max("id");
        int nextID;
        if (currentID == null){
            nextID = 1;
        }else{
            nextID = currentID.intValue() + 1;
        }
        return nextID;
    }
}
