package me.citrafa.mycollegeassistant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.vatsal.easyweather.Helper.TempUnitConverter;
import github.vatsal.easyweather.Helper.WeatherCallback;
import github.vatsal.easyweather.WeatherMap;
import github.vatsal.easyweather.retrofit.models.Weather;
import github.vatsal.easyweather.retrofit.models.WeatherResponseModel;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuCatatan;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuJadwalLain;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuJadwalUjian;
import me.citrafa.mycollegeassistant.AppController.AppController;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.R;
import me.citrafa.mycollegeassistant.Service.GPSTracker;
import me.citrafa.mycollegeassistant.Setting;
import me.citrafa.mycollegeassistant.WebService.WebUrl;

import static java.lang.String.valueOf;
import static me.citrafa.mycollegeassistant.BuildConfig.OWM_API_KEY;

public class Dashboard extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    @BindView(R.id.toolbars) Toolbar toolbar;
    @BindView(R.id.rootdashboard) DrawerLayout root;
    @BindView(R.id.toolbarbtn) View btn;
    @BindView(R.id.imgWeather)ImageView imgWeather;
    @BindView(R.id.celciusWeather) tvMuseo lblCelcius;
    @BindView(R.id.TimeRemainingDahsboard) tvMuseo lblTime;
    @BindView(R.id.namaKegiatan) tvMuseo lblKegiatan;

    GPSTracker gps;
    GuillotineAnimation gv;
    View guillotineMenu;
    ImageButton icProfile,icJK,icJU,icJL,icC,icLogout,icSetting;
    tvMuseo tvEmail,tvNama;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        WeatherMap weatherMap = new WeatherMap(this, OWM_API_KEY);
        session = new SessionManager(getApplicationContext());

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
        gps = new GPSTracker(this);
        String Lat = Double.toString(gps.getLatitude());
        String Long = Double.toString(gps.getLongitude());
        weatherMap.getLocationWeather(Lat, Long, new WeatherCallback() {
            @Override
            public void success(WeatherResponseModel response) {
                Weather weather[] = response.getWeather();
                Double temp = TempUnitConverter.convertToCelsius(response.getMain().getTemp());
                Integer i = temp.intValue();


                final String DEGREE  = "\u00b0";
                String iconLink = weather[0].getIconLink();
                lblCelcius.setText(valueOf(i)+DEGREE+"C");
                Picasso.with(Dashboard.this).load(iconLink).resize(100,100).into(imgWeather);
            }

            @Override
            public void failure(String message) {

            }
        });

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menudashboard, null);
        root.addView(guillotineMenu);

        gv = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.btntoolbarkembali), btn)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        showMenu();


    }
    public void showMenu(){
        icJK = (ImageButton)guillotineMenu.findViewById(R.id.navMenuJadwalKuliah);
        icProfile = (ImageButton)guillotineMenu.findViewById(R.id.navMenuIcon);
        icJU = (ImageButton)guillotineMenu.findViewById(R.id.navMenuJadwalUjian);
        icJL = (ImageButton)guillotineMenu.findViewById(R.id.navMenuJadwalLain);
        icC = (ImageButton)guillotineMenu.findViewById(R.id.navMenuCatatan);
        icSetting = (ImageButton)guillotineMenu.findViewById(R.id.navSetting);
        icLogout = (ImageButton)guillotineMenu.findViewById(R.id.navLogOut);
        tvEmail = (tvMuseo)guillotineMenu.findViewById(R.id.navMenuEmail);
        tvNama = (tvMuseo)guillotineMenu.findViewById(R.id.navMenuNama);
        tvEmail.setText(session.getEmaiUser());
        tvNama.setText(session.getNamaUser());

        icJK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,menuJadwalKuliahTab.class));
                gv.close();
            }
        });
        icJU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_dashboard,new menuJadwalUjian()).commit();
                gv.close();
            }
        });
        icJL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_dashboard,new menuJadwalLain()).commit();
                gv.close();
            }
        });
        icC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_dashboard,new menuCatatan()).commit();
                gv.close();
            }
        });
        icLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });
        icSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Setting.class));
            }
        });

    }
    private void doLogout(){
        final String email = session.getEmaiUser();
        String tag_string_req = "req_verif";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebUrl.URL_LOGOUT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObjs = new JSONObject(response);
                    boolean error = jObjs.getBoolean("error");
                    String nama = jObjs.getString("nama");
                    if (!error){
                        session.setLogin(false);
                        startActivity(new Intent(Dashboard.this,frmLogin.class));
                        finish();
                        Toast.makeText(Dashboard.this, "Logout Berhasil "+nama, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "json error"+ e, Toast.LENGTH_LONG).show();
                }

            } }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Cek Koneksi Internet Kamu!"+error, Toast.LENGTH_LONG).show();
            }
        }
        ){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
