package me.citrafa.mycollegeassistant.CustomWidget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

/**
 * Created by SENSODYNE on 22/04/2017.
 */

public class LibraryDateCustom {
    Date FINALDATE;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date dates,dateStart,dateFinish;
    Date dateJKMulai, dateJKSelesai;



    public String getWaktuTanggalBiasa(Date dates){


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        String formatedDate = sdf.format(dates);

        return formatedDate;
    }
    public static Date getCurrentTimeStamp() {
        Date dates = null;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        try {
            dates = sdfDate.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }


    public String getHaridanTanggalUntukListSingle(Date date){
        String Jadi = null;
        String Hari = getHariDariWaktu(date);
        String Tanggal = getWaktuTanggalBiasa(date);
        String Jam;
        Calendar calendar = Calendar.getInstance();
        DateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        Jam = timeFormatter.format(date);
        if (Hari.equals("Hari ini")){
            Jadi = Hari+" - "+Jam;

        }
        return Hari;
    }

    public String getHariDariWaktu(Date dates){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        SimpleDateFormat parsing = new SimpleDateFormat("dd-MM-yy");
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        tomorrow.add(Calendar.DATE, 1);
        String formatedDate = sdf.format(dates);
        DateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Hari ini";
        }else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Kemarin";
        }else if (calendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
            return "Besok";
        }else {
            if (formatedDate.equals("Sun")){
                    return  "Minggu";
                }else if (formatedDate.equals("Mon")){
                    return  "Senin";
                }else if (formatedDate.equals("Tue")){
                    return  "Selasa";
                }else if (formatedDate.equals("Wed")){
                    return  "Rabu";
                }else if (formatedDate.equals("Thu")){
                    return  "Kamis";
                }else if (formatedDate.equals("Fri")){
                    return  "Jum'at";
                }else{
                    return  "Sabtu";
                }
        }

    }

    public void DateTimePickerSingle(final Context context , final EditText txt1){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                        String formatedDate = sdf.format(new Date(year,monthOfYear,dayOfMonth,hourOfDay,minute));
                                        try {
                                            Date date = sdf.parse(formatedDate);
                                            txt1.setText(getHariDariWaktu(date)+", "+getWaktuTanggalBiasa(date));
                                        } catch (ParseException e) {

                                        }

                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void DateTimePickerStart(final Context context , final TextView txt1){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                        String formatedDate = sdf.format(new Date(year,monthOfYear,dayOfMonth,hourOfDay,minute));
                                        try {
                                            Date date = sdf.parse(formatedDate);
                                            SimpleDateFormat Dates = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                                            String Jadi = Dates.format(date);
                                            dates = date;

                                            setDateStart(date);
                                            txt1.setText(getHariDariWaktu(dates)+", "+getWaktuTanggalBiasa(dates));
                                        } catch (ParseException e) {

                                        }

                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void DateTimePickerFinish(final Context context , final TextView txt1){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                        String formatedDate = sdf.format(new Date(year,monthOfYear,dayOfMonth,hourOfDay,minute));
                                        try {
                                            Date date = sdf.parse(formatedDate);
                                            SimpleDateFormat Dates = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                                            String Jadi = Dates.format(date);
                                            dates = date;
                                            setDateFinish(date);
                                            txt1.setText(getHariDariWaktu(dates)+", "+getWaktuTanggalBiasa(dates));
                                        } catch (ParseException e) {

                                        }

                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public Date getFINALDATE() {
        return FINALDATE;
    }

    public void setFINALDATE(Date FINALDATE) {
        this.FINALDATE = FINALDATE;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getDateJKMulai() {
        return dateJKMulai;
    }

    public void setDateJKMulai(Date dateJKMulai) {
        this.dateJKMulai = dateJKMulai;
    }

    public Date getDateJKSelesai() {
        return dateJKSelesai;
    }

    public void setDateJKSelesai(Date dateJKSelesai) {
        this.dateJKSelesai = dateJKSelesai;
    }
}
