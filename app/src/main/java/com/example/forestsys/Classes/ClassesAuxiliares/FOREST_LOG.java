package com.example.forestsys.Classes.ClassesAuxiliares;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FOREST_LOG implements Serializable {
    @PrimaryKey(autoGenerate = true)
    Integer ID;

    String DATA;
    String DISPOSITIVO;
    String USUARIO;
    String MODULO;
    String ACAO;
    String VALOR;
    String CREATED_AT;


    public FOREST_LOG(String DATA, String DISPOSITIVO, String USUARIO, String MODULO, String ACAO,
                      String VALOR) {
        this.DATA = DATA;
        this.DISPOSITIVO = DISPOSITIVO;
        this.USUARIO = USUARIO;
        this.MODULO = MODULO;
        this.ACAO = ACAO;
        this.VALOR = VALOR;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getDISPOSITIVO() {
        return DISPOSITIVO;
    }

    public void setDISPOSITIVO(String DISPOSITIVO) {
        this.DISPOSITIVO = DISPOSITIVO;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getMODULO() {
        return MODULO;
    }

    public void setMODULO(String MODULO) {
        this.MODULO = MODULO;
    }

    public String getACAO() {
        return ACAO;
    }

    public void setACAO(String ACAO) {
        this.ACAO = ACAO;
    }

    public String getVALOR() {
        return VALOR;
    }

    public void setVALOR(String VALOR) {
        this.VALOR = VALOR;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(String CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }
}

