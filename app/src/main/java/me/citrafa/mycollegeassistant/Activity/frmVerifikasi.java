package me.citrafa.mycollegeassistant.Activity;

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
import me.citrafa.mycollegeassistant.R;
import me.citrafa.mycollegeassistant.WebService.WebUrl;

public class frmVerifikasi extends AppCompatActivity {
    private static final String TAG = "LOG";
    @BindView(R.id.verifikasi_kode) etMuseo txtVerif;
    @BindView(R.id.verifikasibtnVerif) btnMuseo btnVerif;
    SessionManager session;
    String Email,kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verifikasi);
        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());
        Intent intent = new Intent();
        Email = intent.getStringExtra("email");
        btnVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(frmVerifikasi.this, "Kode Verifikasi Salah!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void doVerif(){
        kode = txtVerif.getText().toString();
        String tag_string_req = "req_verif";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebUrl.URL_VERIFIKASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObjs = new JSONObject(response);
                    boolean error = jObjs.getBoolean("error");
                    int status_verifikasi = jObjs.getInt("status_verifikasi");
                    String nama = jObjs.getString("nama");
                    if (!error){
                        if (status_verifikasi==1) {
                            session.setLogin(true);
                            Intent i = new Intent(frmVerifikasi.this, Dashboard.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(frmVerifikasi.this, "Selamat Datang \n"+nama, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        String errorMsg = jObjs.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "JSONNYA ERROR BANG", Toast.LENGTH_LONG).show();
                }

            } }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
        ){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Email);
                params.put("kd_verifikasi", kode);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
