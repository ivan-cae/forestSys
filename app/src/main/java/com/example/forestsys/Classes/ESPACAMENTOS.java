package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class ESPACAMENTOS implements Serializable {
    @PrimaryKey
    private int ID_ESPACAMENTO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public ESPACAMENTOS(int ID_ESPACAMENTO, String DESCRICAO, int ATIVO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_ESPACAMENTO() {
        return ID_ESPACAMENTO;
    }

    public void setID_ESPACAMENTO(int ID_ESPACAMENTO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
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
