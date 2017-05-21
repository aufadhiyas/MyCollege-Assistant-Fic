package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

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
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.TugasModel;
import me.citrafa.mycollegeassistant.OperationRealm.TugasOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class frmTugas extends Fragment {
    @BindView(R.id.lblMakulTugas) tvMuseo lblMakul;
    @BindView(R.id.lblWaktuMakulTugas) tvMuseo lblWaktu;
    @BindView(R.id.txtDeskripsiTugas) etMuseo txtDeskripsi;
    @BindView(R.id.btnBrowseFileTugas) AppCompatImageButton btnBrowser;
    @BindView(R.id.lblFileTugas) tvMuseo lblFile;
    @BindView(R.id.switchWaktuKumpulTugas) Switch swWaktu;
    @BindView(R.id.lblSwitchTugas) tvMuseo lblSwitch;
    @BindView(R.id.txtWaktuKumpulTugas) etMuseo txtWaktu;
    @BindView(R.id.btnSimpanTugas) FloatingActionButton btnSimpan;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private static final String switchOff = "Kumpul tugas saat jam kuliah";
    private static final String switchOn = "Kumpul tugas diluar jam kuliah";
    SessionManager session;
    TugasOperation TO;
    int idt,idjk,mYear,mMonth,mDay,mHour,mMinute;String deskripsi,attlink,author;
    Date dateS,update,create;
    Realm realm;
    LibraryDateCustom LDC;
    FragmentManager fm;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        idt = getArguments().getInt("idT",0);
        idjk = getArguments().getInt("idJK");
        session = new SessionManager(getActivity());
        TO = new TugasOperation();
        realm = Realm.getDefaultInstance();
        LDC = new LibraryDateCustom();
        return inflater.inflate(R.layout.fragment_frm_tugas, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        if (idt!=0){
            initData(idt,idjk);
        }
        fm = getFragmentManager();
        txtWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerS();
            }
        });
        txtWaktu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideSoftKeyboard(getActivity());
                }
            }
        });
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
        initDataJK();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData(idt);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (path != null) {
                lblFile.setText(path);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void simpanData(int idn){
        int ids = ids(idn);
        attlink = lblFile.getText().toString();
        deskripsi = txtDeskripsi.getText().toString();
        author = session.getEmaiUser();
        update = getCurrentTimeStamp();
        create = getCurrentTimeStamp();
        if (ids==0){
            TugasModel tm = new TugasModel(ids,uuid(),deskripsi,attlink,dateS,true,author,create,update);
            JadwalKuliahModel jk = realm.where(JadwalKuliahModel.class).equalTo("no_jk",idjk).findFirst();
            jk.Tugas.add(tm);
            TO.TambahData(tm);
            Toast.makeText(getActivity(), "Data Berhasil Disimpan!!", Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        }else {
            TugasModel tm = new TugasModel(ids,deskripsi,attlink,dateS,true,author,update);
            TO.updatedata(tm);
            Toast.makeText(getActivity(), "Data Berhasil Diubah!!", Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        }
    }
    private void initData(int i,int j){
        Realm realm;
        realm = Realm.getDefaultInstance();
        TugasModel t = realm.where(TugasModel.class).equalTo("no_t",i).findFirst();
        if (t!=null){
            if (t.getWaktu_t()!=null){
                txtWaktu.setText(LDC.getHariDariWaktu(t.getWaktu_t())+" "+LDC.getHariDariWaktu(t.getWaktu_t()));
            }
            txtDeskripsi.setText(t.getDeskripsi_t());
            if (t.getAttlink_t()!=null){
                lblFile.setText(t.getAttlink_t());
            }
            initDataJK();

        }
    }
    public void initDataJK(){
        JadwalKuliahModel jk = realm.where(JadwalKuliahModel.class).equalTo("no_jk",idjk).findFirst();
        lblMakul.setText(jk.getMakul_jk());
        if (jk !=null && idjk !=0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            lblWaktu.setText(jk.getHari_jk() + " " + sdf.format(jk.getWaktu_jk()));
        }else {
            Toast.makeText(getActivity(), "Jadwal Kuliah Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
        }
    }
    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(getActivity())
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(false).withTitle("Lampirkan File")
                .start();
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
            idn = TO.getNextId();
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
}