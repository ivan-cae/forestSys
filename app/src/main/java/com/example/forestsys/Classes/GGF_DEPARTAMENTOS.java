package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_DEPARTAMENTOS implements Serializable {
    @PrimaryKey
    private int ID_DEPARTAMENTO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public GGF_DEPARTAMENTOS(int ID_DEPARTAMENTO, String DESCRICAO, int ATIVO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_DEPARTAMENTO() {
        return ID_DEPARTAMENTO;
    }

    public void setID_DEPARTAMENTO(int ID_DEPARTAMENTO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }
}
