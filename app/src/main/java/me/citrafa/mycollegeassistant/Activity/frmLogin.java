package me.citrafa.mycollegeassistant.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.citrafa.mycollegeassistant.AppController.AppController;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.btnMuseo;
import me.citrafa.mycollegeassistant.CustomWidget.etMuseo;
import me.citrafa.mycollegeassistant.CustomWidget.jToast;
import me.citrafa.mycollegeassistant.R;
import me.citrafa.mycollegeassistant.Service.GPSTracker;
import me.citrafa.mycollegeassistant.WebService.WebUrl;

public class frmLogin extends AppCompatActivity{
    private static final String TAG = "LOG";
    private int counter = 0;
    @BindView(R.id.loginEmail) etMuseo txtEmail;
    @BindView(R.id.loginPass) etMuseo txtPass;
    @BindView(R.id.loginBtnLogin) btnMuseo btnLogin;
    @BindView(R.id.loginBtnDaftar) btnMuseo btnDaftar;
    ProgressDialog pDialog;
    SessionManager s;
    String provider;
    LocationManager locationManager;
    private static final int REQUEST_PERMISSIONS = 20;
    int MY_PERMISSION = 0;

    String Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_login);
        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(frmLogin.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE


            }, MY_PERMISSION);
        }
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()){

        }else {
            gps.showSettingsAlert();
        }
        s = new SessionManager(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEmail.getText().toString()!=null &&txtPass !=null){
                    checkLogin();
                }else {
                    jToast.makeText(frmLogin.this,"Isi data kamu dengan benar",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmLogin.this, frmDaftar.class));
            }
        });
    }
    public void checkLogin(){
        Email = txtEmail.getText().toString();
        Password = txtPass.getText().toString();
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebUrl.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObjs = new JSONObject(response);
                    boolean error = jObjs.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        String status_verifikasi = jObjs.getString("status_verifikasi");
                        String email = jObjs.getString("email");
                        String nama = jObjs.getString("nama");

                        if (status_verifikasi.equals("0")){
                            s.setVerifyStat(false);
                            s.setEmailUser(email);
                            s.setNamaUser(nama);
                            Intent intent = new Intent(frmLogin.this, frmVerifikasi.class);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        }else{
                            s.setLogin(true);
                            s.setEmailUser(email);
                            s.setNamaUser(nama);
                            Intent intent = new Intent(frmLogin.this,
                                    Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObjs.getString("message");
                        jToast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error, Cek Koneksi Kamu!");
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Email);
                params.put("password", Password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
