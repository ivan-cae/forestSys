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
    private Integer ID_DEPARTAMENTO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;

    public GGF_DEPARTAMENTOS(Integer ID_DEPARTAMENTO, String DESCRICAO, Integer ATIVO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_DEPARTAMENTO() {
        return ID_DEPARTAMENTO;
    }

    public void setID_DEPARTAMENTO(Integer ID_DEPARTAMENTO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
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
}
