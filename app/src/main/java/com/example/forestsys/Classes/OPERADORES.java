package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class OPERADORES implements Serializable {
    @PrimaryKey
    private Integer ID_OPERADORES;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;

    public OPERADORES(Integer ID_OPERADORES, String DESCRICAO, Integer ATIVO) {
        this.ID_OPERADORES = ID_OPERADORES;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_OPERADORES() {
        return ID_OPERADORES;
    }

    public void setID_OPERADORES(Integer ID_OPERADORES) {
        this.ID_OPERADORES = ID_OPERADORES;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }

    @Override
    public String toString() {
        return DESCRICAO;
    }
}
