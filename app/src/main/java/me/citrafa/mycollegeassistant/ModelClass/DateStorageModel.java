package me.citrafa.mycollegeassistant.ModelClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SENSODYNE on 27/04/2017.
 */

public class DateStorageModel extends RealmObject {

    @PrimaryKey
    private int id;
    private int id_model;
    private String modelName;
    private Date dateS;
    private Date dateF;
    private Boolean status;

    public DateStorageModel() {
    }

    public DateStorageModel(int id, int id_model, String modelName, Date dateS, Date dateF, Boolean status) {
        this.id = id;
        this.id_model = id_model;
        this.modelName = modelName;
        this.dateS = dateS;
        this.dateF = dateF;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id_model) {
        this.id_model = id_model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getDateS() {
        return dateS;
    }

    public void setDateS(Date dateS) {
        this.dateS = dateS;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }
}
