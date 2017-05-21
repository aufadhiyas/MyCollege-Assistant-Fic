package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.etMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;
import me.citrafa.mycollegeassistant.OperationRealm.JadwalLainOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class frmJadwalLain extends Fragment {
    @BindView(R.id.txtNamaJl) etMuseo txtNama;
    @BindView(R.id.txtWaktuJlS) etMuseo txtWaktus;
    @BindView(R.id.txtWaktuJlF) etMuseo txtWaktuf;
    @BindView(R.id.txtTempatJl) etMuseo txtTempat;
    @BindView(R.id.txtDeskripsiJl)etMuseo txtDeskripsi;
    @BindView(R.id.btnSimpanJl) FloatingActionButton btnSimpan;
    JadwalLainOperation JLO;
    SessionManager session;
    int id,mYear,mMonth,mDay,mHour,mMinute;String uid,nama,tempat,deskripsi,author;
    Date dateS,dateF,created_at,updated_at;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("idJL",0);
        JLO = new JadwalLainOperation();
        session = new SessionManager(getActivity());
        return inflater.inflate(R.layout.fragment_frm_jadwal_lain, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        txtWaktus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerS();
            }
        });
        txtWaktuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerF();
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
        String Author = session.getEmaiUser();
        nama = txtNama.getText().toString();
        tempat = txtTempat.getText().toString();
        updated_at = getCurrentTimeStamp();
        created_at = getCurrentTimeStamp();
        deskripsi = txtDeskripsi.getText().toString();
        if (idn == 0) {
            JadwalLainModel jml = new JadwalLainModel(ids, uid, nama, dateS, dateF, tempat, deskripsi, true, Author, created_at, updated_at);
            JLO.tambahJadwalLain(jml);
        }else{
            JadwalLainModel jml = new JadwalLainModel(ids, nama, dateS, dateF, tempat, deskripsi, true, Author, updated_at);
            JLO.editJadwalLain(jml);
        }
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
                                            txtWaktus.setText(Jadi);
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
            idn = JLO.getNextId();
        } else {
            idn = id;
        }
        return idn;
    }

    public void DateTimePickerF(){
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
                                            Date dateFs = sdf.parse(formatedDate);
                                            SimpleDateFormat Dates = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                                            String Jadi = Dates.format(dateFs);
                                            dateF = dateFs;
                                            txtWaktuf.setText(Jadi);
                                        } catch (ParseException e) {

                                        }
                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public String uuid(){
        return UUID.randomUUID().toString().toString();
    }

}
