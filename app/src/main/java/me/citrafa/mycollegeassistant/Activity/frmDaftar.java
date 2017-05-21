package me.citrafa.mycollegeassistant.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import me.citrafa.mycollegeassistant.WebService.WebUrl;

public class frmDaftar extends AppCompatActivity {
    @BindView(R.id.daftarEmail) etMuseo txtEmail;
    @BindView(R.id.daftarPassword) etMuseo txtPass;
    @BindView(R.id.daftarNama) etMuseo txtNama;
    @BindView(R.id.daftarbtnDaftar) btnMuseo btnDaftar;
    @BindView(R.id.daftarbtnKembali) btnMuseo btnKembali;
    private ProgressDialog pDialog;
    private static String TAG = "LOG";

    SessionManager session;
    String Email,Password,Nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_daftar);
        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmDaftar.this,frmLogin.class));
            }
        });
    }

    private void registerUser(){
        Email = txtEmail.getText().toString();
        Password = txtPass.getText().toString();
        Nama = txtNama.getText().toString();

        if (Email !=null&&Password!=null&&Nama!=null){
            String tag_string_req = "req_register";

            pDialog.setMessage("Registering ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    WebUrl.URL_DAFTAR, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite

                            String name = jObj.getString("name");
                            String email = jObj.getString("email");
                            String status = jObj.getString("status_verifikasi");
                            session.setVerifyStat(false);

                            Intent intent = new Intent(frmDaftar.this,frmVerifikasi.class);
                            intent.putExtra("status",status);
                            intent.putExtra("email",email);
                            intent.putExtra("nama",name);
                            startActivity(intent);
                            finish();
                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            String errorMsg = jObj.getString("message");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(frmDaftar.this, ""+e, Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Tidak Ada Koneksi Internet", Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nama", Nama);
                    params.put("email", Email);
                    params.put("password", Password);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }else {
            jToast.makeText(frmDaftar.this,"Tidak boleh ada yang kosong!!", Toast.LENGTH_SHORT).show();
        }

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
