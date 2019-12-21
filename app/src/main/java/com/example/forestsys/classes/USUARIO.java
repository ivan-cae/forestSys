package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class USUARIO {
    @NonNull
    @PrimaryKey
    private String CHAPA;
    
    private double CODCOLIGADA;
	private String NOME;
    private String SECAO;
    private String FUNCAO;

    public USUARIO(String CHAPA, double CODCOLIGADA, String NOME, String SECAO, String FUNCAO) {
        this.CHAPA = CHAPA;
        this.CODCOLIGADA = CODCOLIGADA;
        this.NOME = NOME;
        this.SECAO = SECAO;
        this.FUNCAO = FUNCAO;
    }

    public String getCHAPA() {
        return CHAPA;
    }

    public void setCHAPA(String CHAPA) {
        this.CHAPA = CHAPA;
    }

    public double getCODCOLIGADA() {
        return CODCOLIGADA;
    }

    public void setCODCOLIGADA(double CODCOLIGADA) {
        this.CODCOLIGADA = CODCOLIGADA;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getSECAO() {
        return SECAO;
    }

    public void setSECAO(String SECAO) {
        this.SECAO = SECAO;
    }

    public String getFUNCAO() {
        return FUNCAO;
    }

    public void setFUNCAO(String FUNCAO) {
        this.FUNCAO = FUNCAO;
    }
}
