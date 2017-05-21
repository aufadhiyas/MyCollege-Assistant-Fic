package me.citrafa.mycollegeassistant.OperationRealm;

import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SENSODYNE on 11/04/2017.
 */

public class RealmBaseActivity extends AppCompatActivity{
    private RealmConfiguration realmConfiguration;
    public RealmConfiguration getRealmConfiguration(){
        if (realmConfiguration == null){
            realmConfiguration = new RealmConfiguration
                    .Builder().deleteRealmIfMigrationNeeded().build();

        }
        return realmConfiguration;

    }
    protected void resetRealm(){
        Realm.deleteRealm(getRealmConfiguration());
    }
}
