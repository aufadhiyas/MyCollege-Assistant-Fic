package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.AdapterSpinner;
import me.citrafa.mycollegeassistant.CustomWidget.LibraryDateCustom;
import me.citrafa.mycollegeassistant.CustomWidget.etMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalUjianModel;
import me.citrafa.mycollegeassistant.OperationRealm.JadwalUjianOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class frmJadwalUjian extends Fragment implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.spJenisUjian) AppCompatSpinner spJenisUjian;
    @BindView(R.id.txtNamaJU) etMuseo txtNama;
    @BindView(R.id.txtWaktuJU)etMuseo txtWaktu;
    @BindView(R.id.txtDeskripsiJU) etMuseo txtDeskripsi;
    @BindView(R.id.txtRuanganJU) etMuseo txtRuangan;
    @BindView(R.id.btnSimpanJu) FloatingActionButton btnSimpan;
    JadwalUjianOperation JUO;
    SessionManager session;
    LibraryDateCustom LDC;
    String[] item= {"PRETEST","PASTTEST","UTS","UAS"};

    int id,mYear,mMonth,mDay,mHour,mMinute; String Jenis,Jenisie,nama,desskripsi,ruangan,author;
    Date dateS,updated_at,created_at;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("idJU",0);
        session = new SessionManager(getActivity());
        JUO = new JadwalUjianOperation();
        LDC = new LibraryDateCustom();

        return inflater.inflate(R.layout.fragment_frm_jadwal_ujian, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this,view);
        if (id!=0){
            initData(id);
        }
        AdapterSpinner ap = new AdapterSpinner(getActivity(),item);
        spJenisUjian.setAdapter(ap);
        spJenisUjian.setOnItemSelectedListener(this);
        txtWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerS();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData(id);
            }
        });
    }
    private void simpanData(int idn){
        int ids = ids(idn);
        Jenis = Jenisie;
        nama = txtNama.getText().toString();
        desskripsi = txtDeskripsi.getText().toString();
        ruangan = txtRuangan.getText().toString();
        author = session.getEmaiUser();
        updated_at = getCurrentTimeStamp();
        created_at = getCurrentTimeStamp();
        if (idn ==0){
            JadwalUjianModel jum = new JadwalUjianModel(ids,uuid(),nama,dateS,Jenis,desskripsi,ruangan,true,author,created_at,updated_at);
            JUO.tambahJadwalUjian(jum);
        }else {
            JadwalUjianModel jum = new JadwalUjianModel(ids,nama,dateS,Jenis,desskripsi,ruangan,true,author,updated_at);
            JUO.updateJadwalUjian(jum);
        }

    }

    private void initData(int i){
        Realm realm;
        realm = Realm.getDefaultInstance();
        JadwalUjianModel ju = realm.where(JadwalUjianModel.class).equalTo("no_ju",i).findFirst();
        txtNama.setText(ju.getNama_makul());
        txtDeskripsi.setText(ju.getDeskripsi());
        txtRuangan.setText(ju.getRuangan());
        txtWaktu.setText(LDC.getHariDariWaktu(ju.getWaktu())+" "+LDC.getWaktuTanggalBiasa(ju.getWaktu()));
        dateS = ju.getWaktu();
    }

    public static Date getCurrentTimeStamp(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        try {
            return sdfDate.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }
    public void DateTimePickerS(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, final int year,
                                          final int monthOfYear, final int dayOfMonth) {

                        //.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        //DateS = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
                                        String formatedDate = sdf.format(new Date(year,monthOfYear,dayOfMonth,hourOfDay,minute));
                                        try {
                                            Date dateSs = sdf.parse(formatedDate);
                                            SimpleDateFormat Dates = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                                            String Jadi = Dates.format(dateSs);
                                            dateS = dateSs;
                                            txtWaktu.setText(Jadi);
                                        } catch (ParseException e) {

                                        }
                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public int ids(int id) {
        int idn;
        if (id == 0) {
            idn = JUO.getNextId();
        } else {
            idn = id;
        }
        return idn;
    }
    public String uuid(){
        return UUID.randomUUID().toString().toString();
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Jenisie = item[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        btnSimpan.setEnabled(false);
    }
}