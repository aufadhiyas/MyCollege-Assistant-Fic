package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 28/03/2017.
 */

public class UserModel extends RealmObject{

    @PrimaryKey
    private int id;
    private String uid;
    private String nama;
    private String email;
    private String password;
    private String kode_verifikasi;
	private int status_verifikasi;
    private String universitas;
    private String jurusan;
    private String kelas;
    private String lokasi;
    private String tanggal_lahir;
    private Date created_at;
    private Date updated_at;
    private Date sync_at;
    private String no_online;
	
	

    public UserModel() {
    }

    public UserModel(int id, String uid, String nama, String email, String password, String kode_verifikasi, int status_verifikasi, String universitas, String jurusan, String kelas, String lokasi, String tanggal_lahir, Date created_at, Date updated_at, Date sync_at, String no_online) {
        this.id = id;
        this.uid = uid;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kode_verifikasi = kode_verifikasi;
        this.status_verifikasi = status_verifikasi;
        this.universitas = universitas;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.lokasi = lokasi;
        this.tanggal_lahir = tanggal_lahir;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.sync_at = sync_at;
        this.no_online = no_online;
    }

    public UserModel(int id, String uid, String nama, String email, String password, String kode_verifikasi, int status_verifikasi) {
        this.id = id;
        this.uid = uid;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kode_verifikasi = kode_verifikasi;
        this.status_verifikasi = status_verifikasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKode_verifikasi() {
        return kode_verifikasi;
    }

    public void setKode_verifikasi(String kode_verifikasi) {
        this.kode_verifikasi = kode_verifikasi;
    }

    public int getStatus_verifikasi() {
        return status_verifikasi;
    }

    public void setStatus_verifikasi(int status_verifikasi) {
        this.status_verifikasi = status_verifikasi;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
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

    public Date getSync_at() {
        return sync_at;
    }

    public void setSync_at(Date sync_at) {
        this.sync_at = sync_at;
    }

    public String getNo_online() {
        return no_online;
    }

    public void setNo_online(String no_online) {
        this.no_online = no_online;
    }
}
