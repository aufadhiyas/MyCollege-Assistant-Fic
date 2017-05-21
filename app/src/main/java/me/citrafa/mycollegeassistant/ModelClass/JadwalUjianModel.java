package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 15/04/2017.
 */

public class JadwalUjianModel extends RealmObject{

    @PrimaryKey
    private int no_ju;
    private String uid;
    private String nama_makul;
    private Date waktu;
    private String jenis;
    private String deskripsi;
    private String ruangan;
    private Boolean status_ju;
    private String Author;
    private Date created_at;
    private Date updated_at;
	

    public JadwalUjianModel() {
    }

    public JadwalUjianModel(int no_ju, String uid, String nama_makul, Date waktu, String jenis, String deskripsi, String ruangan, boolean status_ju, String author, Date created_at, Date updated_at) {
        this.no_ju = no_ju;
        this.uid = uid;
        this.nama_makul = nama_makul;
        this.waktu = waktu;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.ruangan = ruangan;
        this.status_ju = status_ju;
        Author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public JadwalUjianModel(int no_ju, String nama_makul, Date waktu, String jenis, String deskripsi, String ruangan, boolean status_ju, String author, Date updated_at) {
        this.no_ju = no_ju;
        this.nama_makul = nama_makul;
        this.waktu = waktu;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.ruangan = ruangan;
        this.status_ju = status_ju;
        Author = author;
        this.updated_at = updated_at;
    }

    public int getNo_ju() {
        return no_ju;
    }

    public void setNo_ju(int no_ju) {
        this.no_ju = no_ju;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama_makul() {
        return nama_makul;
    }

    public void setNama_makul(String nama_makul) {
        this.nama_makul = nama_makul;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public boolean isStatus_ju() {
        return status_ju;
    }

    public void setStatus_ju(boolean status_ju) {
        this.status_ju = status_ju;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
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
}
