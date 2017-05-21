package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 08/04/2017.
 */

public class TugasModel extends RealmObject{
    @PrimaryKey
    private int no_t;
    private String uid;
    private String deskripsi_t;
    private String attlink_t;
    private Date waktu_t;
    private Boolean status_t;
    private String author;
    private Date created_at;
    private Date updated_at;



    public TugasModel() {
    }

    public TugasModel(int no_t, String uid, String deskripsi_t, String attlink_t, Date waktu_t, boolean status_t, String author, Date created_at, Date updated_at) {
        this.no_t = no_t;
        this.uid = uid;
        this.deskripsi_t = deskripsi_t;
        this.attlink_t = attlink_t;
        this.waktu_t = waktu_t;
        this.status_t = status_t;
        this.author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public TugasModel(int no_t, String deskripsi_t, String attlink_t, Date waktu_t, boolean status_t, String author, Date updated_at) {
        this.no_t = no_t;
        this.deskripsi_t = deskripsi_t;
        this.attlink_t = attlink_t;
        this.waktu_t = waktu_t;
        this.status_t = status_t;
        this.author = author;
        this.updated_at = updated_at;
    }

    public int getNo_t() {
        return no_t;
    }

    public void setNo_t(int no_t) {
        this.no_t = no_t;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeskripsi_t() {
        return deskripsi_t;
    }

    public void setDeskripsi_t(String deskripsi_t) {
        this.deskripsi_t = deskripsi_t;
    }

    public String getAttlink_t() {
        return attlink_t;
    }

    public void setAttlink_t(String attlink_t) {
        this.attlink_t = attlink_t;
    }

    public Date getWaktu_t() {
        return waktu_t;
    }

    public void setWaktu_t(Date waktu_t) {
        this.waktu_t = waktu_t;
    }

    public boolean isStatus_t() {
        return status_t;
    }

    public void setStatus_t(boolean status_t) {
        this.status_t = status_t;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
