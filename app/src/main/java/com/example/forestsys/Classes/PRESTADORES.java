package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class PRESTADORES implements Serializable {
    @PrimaryKey
    private Integer ID_PRESTADOR;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;

    public PRESTADORES(Integer ID_PRESTADOR, String DESCRICAO, Integer ATIVO) {
        this.ID_PRESTADOR = ID_PRESTADOR;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_PRESTADOR() {
        return ID_PRESTADOR;
    }

    public void setID_PRESTADOR(Integer ID_PRESTADOR) {
        this.ID_PRESTADOR = ID_PRESTADOR;
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
