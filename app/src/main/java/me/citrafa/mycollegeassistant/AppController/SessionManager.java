package me.citrafa.mycollegeassistant.AppController;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by SENSODYNE on 11/05/2017.
 */

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "myCollegeLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_VERIFY = "isVerified";
    private static final String KEY_UID = "UID_USER";
    private static final String KEY_EMAIL= "emailUser";
    private static final String KEY_NAMA = "namaUser";
    private static final String KEY_TIMER = "TimerS";
    private static final String KEY_STAT_TIMER = "StatTimer";
    private static final String KEY_STAT_HARIBESAR = "StatUser";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setVerifyStat(boolean isVerifyStat){
        editor.putBoolean(KEY_IS_VERIFY, isVerifyStat);
        editor.commit();
        Log.d(TAG, "User Verify Status Modified");
    }
    public boolean isVerifyStat(){
        return pref.getBoolean(KEY_IS_VERIFY, false);
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public void setEmailUser(String Email){
        editor.putString(KEY_EMAIL,Email);
        editor.commit();
        Log.d(TAG,"Email User Has Been Set");
    }
    public String getNamaUser(){
        return pref.getString(KEY_NAMA,"Nama User");
    }
    public String getEmaiUser(){
        return pref.getString(KEY_EMAIL,"emaiuser@email.com");
    }
    public void setNamaUser(String Nama){
        editor.putString(KEY_NAMA,Nama);
        editor.commit();
        Log.d(TAG, "User Name Has Been Set");
    }
    public boolean getStatTimer(){
        return pref.getBoolean(KEY_STAT_TIMER,true);
    }
    public void setStatTimer(Boolean statTimer){
        editor.putBoolean(KEY_STAT_TIMER,statTimer);
        editor.commit();
        Log.d(TAG,"Timer Set");
    }
    public boolean getStatHari(){
        return pref.getBoolean(KEY_STAT_HARIBESAR,true);
    }
    public void setStatHari(Boolean statTimer){
        editor.putBoolean(KEY_STAT_HARIBESAR,statTimer);
        editor.commit();
        Log.d(TAG,"Timer Hari Besar Set");
    }
    public void setTimer(int i){
        editor.putInt(KEY_TIMER,i);
        editor.commit();
        Log.d(TAG,"Timer Set : "+i);
    }
    public int getTimer(){
        return pref.getInt(KEY_TIMER,5);
    }

    public void setUIDUSER(String uiduser){
        editor.putString(KEY_UID, uiduser);
        editor.commit();
        Log.d(TAG, "UID FOR USER HAS BEEN SET");
    }
}

