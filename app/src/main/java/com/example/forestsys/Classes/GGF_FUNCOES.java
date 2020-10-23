package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_FUNCOES implements Serializable {
    @PrimaryKey
    private Integer ID_FUNCAO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;

    public GGF_FUNCOES(Integer ID_FUNCAO, String DESCRICAO, Integer ATIVO) {
        this.ID_FUNCAO = ID_FUNCAO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_FUNCAO() {
        return ID_FUNCAO;
    }

    public void setID_FUNCAO(Integer ID_FUNCAO) {
        this.ID_FUNCAO = ID_FUNCAO;
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
