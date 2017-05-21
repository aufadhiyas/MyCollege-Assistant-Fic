package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 08/04/2017.
 */

public class JadwalLainModel extends RealmObject {

    @PrimaryKey
    private int no_jl;
    private String uid;
    private String nama_jl;
    private Date waktus_jl;
    private Date waktuf_jl;
    private String tempat_jl;
    private String deskripsi_jl;
    private Boolean status_jl;
    private String Author;
    private Date created_at;
    private Date updated_at;

    public JadwalLainModel() {
    }

    public JadwalLainModel(int no_jl, String uid, String nama_jl, Date waktus_jl, Date waktuf_jl, String tempat_jl, String deskripsi_jl, Boolean status_jl, String author, Date created_at, Date updated_at) {
        this.no_jl = no_jl;
        this.uid = uid;
        this.nama_jl = nama_jl;
        this.waktus_jl = waktus_jl;
        this.waktuf_jl = waktuf_jl;
        this.tempat_jl = tempat_jl;
        this.deskripsi_jl = deskripsi_jl;
        this.status_jl = status_jl;
        Author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public JadwalLainModel(int no_jl, String nama_jl, Date waktus_jl, Date waktuf_jl, String tempat_jl, String deskripsi_jl, Boolean status_jl, String author, Date updated_at) {
        this.no_jl = no_jl;
        this.nama_jl = nama_jl;
        this.waktus_jl = waktus_jl;
        this.waktuf_jl = waktuf_jl;
        this.tempat_jl = tempat_jl;
        this.deskripsi_jl = deskripsi_jl;
        this.status_jl = status_jl;
        Author = author;
        this.updated_at = updated_at;
    }


    public int getNo_jl() {
        return no_jl;
    }

    public void setNo_jl(int no_jl) {
        this.no_jl = no_jl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama_jl() {
        return nama_jl;
    }

    public void setNama_jl(String nama_jl) {
        this.nama_jl = nama_jl;
    }

    public Date getWaktus_jl() {
        return waktus_jl;
    }

    public void setWaktus_jl(Date waktus_jl) {
        this.waktus_jl = waktus_jl;
    }

    public Date getWaktuf_jl() {
        return waktuf_jl;
    }

    public void setWaktuf_jl(Date waktuf_jl) {
        this.waktuf_jl = waktuf_jl;
    }

    public String getTempat_jl() {
        return tempat_jl;
    }

    public void setTempat_jl(String tempat_jl) {
        this.tempat_jl = tempat_jl;
    }

    public String getDeskripsi_jl() {
        return deskripsi_jl;
    }

    public void setDeskripsi_jl(String deskripsi_jl) {
        this.deskripsi_jl = deskripsi_jl;
    }

    public Boolean getStatus_jl() {
        return status_jl;
    }

    public void setStatus_jl(Boolean status_jl) {
        this.status_jl = status_jl;
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
