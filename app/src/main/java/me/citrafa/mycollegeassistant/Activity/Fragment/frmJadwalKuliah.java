package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.citrafa.mycollegeassistant.AppController.SessionManager;
import me.citrafa.mycollegeassistant.CustomWidget.AdapterSpinner;
import me.citrafa.mycollegeassistant.CustomWidget.etMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.OperationRealm.JadwalKuliahOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class frmJadwalKuliah extends Fragment implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.spinnerHari) AppCompatSpinner spHari;
    @BindView(R.id.txtMakul) etMuseo txtMakul;
    @BindView(R.id.txtRuangan) etMuseo txtRuangan;
    @BindView(R.id.txtJam) etMuseo txtJam;
    @BindView(R.id.txtKelas) etMuseo txtKelas;
    @BindView(R.id.txtDosen) etMuseo txtDosen;
    @BindView(R.id.btnSimpan) FloatingActionButton btnSimpan;
    String[] hariSS = {"Senin","Selasa","Rabu","Kamis","Jum'at","Sabtu","Minggu"};

    int mHour,mMinute;
    SessionManager session;
    JadwalKuliahOperation opJK;

    private int id,nohari;String uid,hari,makul,ruangan,kelas,dosen,author;
    private Date dates,datef,created_at,updated_at;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("noJK",0);
        return inflater.inflate(R.layout.fragment_frm_jadwalkuliah, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        opJK = new JadwalKuliahOperation();
        AdapterSpinner as = new AdapterSpinner(getActivity(),hariSS);
        spHari.setAdapter(as);
        spHari.setOnItemSelectedListener(this);
        session = new SessionManager(getActivity());
        txtJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker();
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
        uid = uuid();
        String harijadi = hari;
        nohari = noHariMen();
        makul = txtMakul.getText().toString();
        ruangan = txtRuangan.getText().toString();
        kelas = txtKelas.getText().toString();
        dosen = txtDosen.getText().toString();
        author = session.getEmaiUser();
        created_at = getCurrentTimeStamp();
        updated_at = getCurrentTimeStamp();
        if (idn == 0){
            JadwalKuliahModel jk = new JadwalKuliahModel(ids,uid,harijadi,nohari,dates,datef,makul,ruangan,dosen,kelas,true,author,created_at,updated_at);
            opJK.tambahJadwalKuliah(jk);
        }else{
            JadwalKuliahModel jk = new JadwalKuliahModel(ids,harijadi,nohari,dates,datef,makul,ruangan,dosen,kelas,true,author,updated_at);
            opJK.editJadwalKuliah(jk);
        }
    }
    private void TimePicker(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        final int year0 = 2011;
        final int month0 = 1;
        final int day0 = 1;

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String formatedDate = sdf.format(new Date(year0,month0,day0,hourOfDay,minute));
                        try {
                            final Date date = sdf.parse(formatedDate);
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay1,
                                                              int minute1) {
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                            String formatedDate = sdf.format(new Date(year0,month0,day0,hourOfDay1,minute1));
                                            try {
                                                Date date1 = sdf.parse(formatedDate);
                                                SimpleDateFormat sdp = new SimpleDateFormat("HH:mm");
                                                dates = date;
                                                datef = date1;
                                                String prints = sdp.format(date);
                                                String printf = sdp.format(date1);
                                                txtJam.setText(prints+" - "+printf);

                                            } catch (ParseException e) {

                                            }

                                        }
                                    }, mHour, mMinute, true);
                            timePickerDialog.setTitle("Jam Selesai Kuliah");
                            timePickerDialog.show();

                        } catch (ParseException e) {

                        }

                    }
                }, mHour, mMinute, true);
        timePickerDialog.setTitle("Jam Mulai Kuliah");
        timePickerDialog.show();
    }
    public void initExistingData(int id){

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
            idn = opJK.getNextId();
        } else {
            idn = id;
        }
        return idn;
    }
    public String uuid(){
        return UUID.randomUUID().toString().toString();
    }
    public int noHariMen(){
        int no = 0;
        if (hari.equals("Senin")){
            no = 1;
        }else if (hari.equals("Selasa")){
            no = 2;
        }else if (hari.equals("Rabu")){
            no = 3;
        }else if (hari.equals("Kamis")){
            no = 4;
        }else if (hari.equals("Jumat")){
            no = 5;
        }else if (hari.equals("Sabtu")){
            no = 6;
        }else if (hari.equals("Minggu")){
            no = 7;
        }else{
            no = 0;
        }
        return no;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        hari = hariSS[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
