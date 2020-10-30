package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class GEO_LOCALIZACAO implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer ID_GEO_LOCAL;

    @ColumnInfo(name = "TALHAO")
    private String TALHAO;

    private double LATITUDE;
    private double LONGITUDE;

    public GEO_LOCALIZACAO(String TALHAO, double LATITUDE, double LONGITUDE) {
        this.TALHAO = TALHAO;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
    }

    public String getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(String TALHAO) {
        this.TALHAO = TALHAO;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public Integer getID_GEO_LOCAL() {
        return ID_GEO_LOCAL;
    }

    public void setID_GEO_LOCAL(Integer ID_GEO_LOCAL) {
        this.ID_GEO_LOCAL = ID_GEO_LOCAL;
    }
}
