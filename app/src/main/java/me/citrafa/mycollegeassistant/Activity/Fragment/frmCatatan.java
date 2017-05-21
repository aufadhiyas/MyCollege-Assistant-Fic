package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import me.citrafa.mycollegeassistant.CustomWidget.LibraryDateCustom;
import me.citrafa.mycollegeassistant.CustomWidget.etMuseo;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.OperationRealm.CatatanOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class frmCatatan extends Fragment {
    @BindView(R.id.txtCatatanNama) etMuseo txtNama;
    @BindView(R.id.txtWaktuCatatan)etMuseo txtWaktu;
    @BindView(R.id.txtDeskripsiCatatan) etMuseo txtDeskripsi;
    @BindView(R.id.btnCatatanSimpan) FloatingActionButton btnSimpan;
    @BindView(R.id.lblCatatanFileName) tvMuseo lblFile;
    @BindView(R.id.btnCatatanAttach) AppCompatImageButton btnAttach;
    CatatanOperation CO;
    Realm realm;
    ContentResolver contentResolver;
    int PICKFILE_RESULT_CODE,noC;
    SessionManager session;
    int id,mYear,mMonth,mDay,mHour,mMinute; String Nama,Deskripsi,attLink;
    String Author;
    Date Dates,updated_at,created_at;
    LibraryDateCustom LDC;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("idC");
        CO = new CatatanOperation();
        LDC = new LibraryDateCustom();

        session = new SessionManager(getActivity());
        LDC = new LibraryDateCustom();
        View view =  inflater.inflate(R.layout.fragment_frm_catatan, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (id!=0) {
            initData(id);
        }
        txtWaktu.setOnClickListener(new View.OnClickListener() {
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


    public void simpanData(int idn){
        int ids = ids(idn);
        Nama = txtNama.getText().toString();
        Deskripsi = txtDeskripsi.getText().toString();
        attLink = lblFile.getText().toString();
        updated_at = getCurrentTimeStamp();
        created_at = getCurrentTimeStamp();
        String Author = session.getEmaiUser();
        if (idn == 0){
            CatatanModel cm = new CatatanModel(ids,uuid(),Nama,Deskripsi,Dates,attLink,Author,true,created_at,updated_at);
            CO.tambahCatatan(cm);
            getActivity().getFragmentManager().popBackStack();
        }else{
            CatatanModel cm = new CatatanModel(ids,Nama,Deskripsi,Dates,attLink,Author,true,updated_at);
            CO.updateCatatan(cm);
            getActivity().getFragmentManager().popBackStack();
        }
    }
    private void initData(int i){
        Realm realm;
        realm = Realm.getDefaultInstance();
        CatatanModel cm = realm.where(CatatanModel.class).equalTo("no_c",i).findFirst();
        if (cm!=null){
            txtNama.setText(cm.getNama_c());
            txtWaktu.setText(LDC.getHariDariWaktu(cm.getWaktu_c())+" "+LDC.getWaktuTanggalBiasa(cm.getWaktu_c()));
            Dates = cm.getWaktu_c();
            txtDeskripsi.setText(cm.getDeskripsi_c());
            if (cm.getAttlink_c()!=null){
                lblFile.setText(cm.getAttlink_c());
            }
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
    public int ids(int id) {
        int idn;
        if (id == 0) {
            idn = CO.getNextId();
        } else {
            idn = id;
        }
        return idn;
    }
    public String uuid(){
        return UUID.randomUUID().toString().toString();
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
                                            Date dateF = sdf.parse(formatedDate);
                                            SimpleDateFormat Datess = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                                            String Jadi = Datess.format(dateF);
                                            Dates = dateF;
                                            txtWaktu.setText(LDC.getHariDariWaktu(dateF)+" "+LDC.getWaktuTanggalBiasa(dateF));
                                        } catch (ParseException e) {

                                        }
                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
