package com.example.forestsys.Classes;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO_IMPLEMENTO", unique = true)}
)
public class IMPLEMENTOS implements Serializable {

    @PrimaryKey
    private Integer ID_IMPLEMENTO;

    @ColumnInfo(name = "DESCRICAO_IMPLEMENTO")
    private String DESCRICAO;

    private Integer ATIVO;

    public IMPLEMENTOS(Integer ID_IMPLEMENTO, String DESCRICAO, Integer ATIVO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(Integer ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
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
