package me.citrafa.mycollegeassistant; /**
 * Created by SENSODYNE on 21/05/2017.
 */

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;

public interface Backup {
    void init(@NonNull final Activity activity);

    void start();

    void stop();

    GoogleApiClient getClient();
}