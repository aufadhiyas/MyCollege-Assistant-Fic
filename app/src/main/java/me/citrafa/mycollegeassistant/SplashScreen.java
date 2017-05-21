package me.citrafa.mycollegeassistant;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import butterknife.BindView;
import butterknife.ButterKnife;
import me.citrafa.mycollegeassistant.Activity.Dashboard;
import me.citrafa.mycollegeassistant.Activity.frmLogin;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.CustomWidget.tvRoboto;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sessionManager;
    @BindView(R.id.splashContentTitle)
    tvMuseo contentTitle;
    @BindView(R.id.splashContentSubTitle) tvMuseo contentSubTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(SplashScreen.this);
        ButterKnife.bind(this);
        progressBar();
    }
    private void progressBar(){
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if (!sessionManager.isLoggedIn()){
                    Intent i = new Intent(SplashScreen.this, frmLogin.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

}
