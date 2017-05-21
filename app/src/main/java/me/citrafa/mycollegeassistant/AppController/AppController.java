package me.citrafa.mycollegeassistant.AppController;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import me.citrafa.mycollegeassistant.Backup;
import me.citrafa.mycollegeassistant.GoogleDriveBackup;

/**
 * Created by SENSODYNE on 11/05/2017.
 */

public class AppController extends Application{
    private Realm realm;
    private RealmChangeListener realmListener;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("databases.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                Log.d(TAG, "DATA ADA YANG DI UBAH");
            }
        };
        realm.addChangeListener(realmListener);
        realm.setDefaultConfiguration(config);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        RealmInspectorModulesProvider.builder(this)
                .withFolder(getCacheDir())
                .withMetaTables()
                .withDescendingOrder()
                .withLimit(1000)
                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                .build();
    }

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public Backup getBackup() {
        return new GoogleDriveBackup();
    }
}
