package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 08/04/2017.
 */

public class JadwalKuliahModel extends RealmObject{


    @PrimaryKey
    private int no_jk;
    private String uid_jk;
    private String hari_jk;
    private int nohari;
    private Date waktu_jk;
    private Date waktu_jkf;
    private String makul_jk;
    private String ruangan_jk;
    private String dosen_jk;
    private String kelas_jk;
    private Boolean status_jk;
    private String Author;
    private Date created_at,updated_at;
    public RealmList<TugasModel> Tugas;

	
    public JadwalKuliahModel() {
    }

    public JadwalKuliahModel(int no_jk, String uid_jk, String hari_jk, int nohari, Date waktu_jk, Date waktu_jkf, String makul_jk, String ruangan_jk, String dosen_jk, String kelas_jk, Boolean status_jk, String author, Date created_at, Date updated_at) {
        this.no_jk = no_jk;
        this.uid_jk = uid_jk;
        this.hari_jk = hari_jk;
        this.nohari = nohari;
        this.waktu_jk = waktu_jk;
        this.waktu_jkf = waktu_jkf;
        this.makul_jk = makul_jk;
        this.ruangan_jk = ruangan_jk;
        this.dosen_jk = dosen_jk;
        this.kelas_jk = kelas_jk;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status_jk = status_jk;
        Author = author;
    }

    public JadwalKuliahModel(int no_jk, String hari_jk, int nohari, Date waktu_jk, Date waktu_jkf, String makul_jk, String ruangan_jk, String dosen_jk, String kelas_jk, Boolean status_jk, String author, Date updated_at) {
        this.no_jk = no_jk;
        this.hari_jk = hari_jk;
        this.nohari = nohari;
        this.waktu_jk = waktu_jk;
        this.waktu_jkf = waktu_jkf;
        this.makul_jk = makul_jk;
        this.ruangan_jk = ruangan_jk;
        this.dosen_jk = dosen_jk;
        this.kelas_jk = kelas_jk;
        this.status_jk = status_jk;
        Author = author;
        this.updated_at = updated_at;
    }

    public int getNo_jk() {
        return no_jk;
    }

    public void setNo_jk(int no_jk) {
        this.no_jk = no_jk;
    }

    public String getUid_jk() {
        return uid_jk;
    }

    public void setUid_jk(String uid_jk) {
        this.uid_jk = uid_jk;
    }

    public String getHari_jk() {
        return hari_jk;
    }

    public void setHari_jk(String hari_jk) {
        this.hari_jk = hari_jk;
    }

    public int getNohari() {
        return nohari;
    }

    public void setNohari(int nohari) {
        this.nohari = nohari;
    }

    public Date getWaktu_jk() {
        return waktu_jk;
    }

    public void setWaktu_jk(Date waktu_jk) {
        this.waktu_jk = waktu_jk;
    }

    public Date getWaktu_jkf() {
        return waktu_jkf;
    }

    public void setWaktu_jkf(Date waktu_jkf) {
        this.waktu_jkf = waktu_jkf;
    }

    public String getMakul_jk() {
        return makul_jk;
    }

    public void setMakul_jk(String makul_jk) {
        this.makul_jk = makul_jk;
    }

    public String getRuangan_jk() {
        return ruangan_jk;
    }

    public void setRuangan_jk(String ruangan_jk) {
        this.ruangan_jk = ruangan_jk;
    }

    public String getDosen_jk() {
        return dosen_jk;
    }

    public void setDosen_jk(String dosen_jk) {
        this.dosen_jk = dosen_jk;
    }

    public String getKelas_jk() {
        return kelas_jk;
    }

    public void setKelas_jk(String kelas_jk) {
        this.kelas_jk = kelas_jk;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getStatus_jk() {
        return status_jk;
    }

    public void setStatus_jk(Boolean status_jk) {
        this.status_jk = status_jk;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public RealmList<TugasModel> getTugas() {
        return Tugas;
    }

    public void setTugas(RealmList<TugasModel> tugas) {
        Tugas = tugas;
    }
}
