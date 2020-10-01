package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class OPERADORES {
    @PrimaryKey
    private int ID_OPERADORES;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public OPERADORES(int ID_OPERADORES, String DESCRICAO, int ATIVO) {
        this.ID_OPERADORES = ID_OPERADORES;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_OPERADORES() {
        return ID_OPERADORES;
    }

    public void setID_OPERADORES(int ID_OPERADORES) {
        this.ID_OPERADORES = ID_OPERADORES;
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

    @Override
    public String toString() {
        return DESCRICAO;
    }
}
