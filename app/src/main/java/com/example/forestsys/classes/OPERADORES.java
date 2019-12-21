package com.example.forestsys.classes;

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
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public OPERADORES(int ID_OPERADORES, int ATIVO, String DESCRICAO) {
        this.ID_OPERADORES = ID_OPERADORES;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_OPERADORES() {
        return ID_OPERADORES;
    }

    public void setID_OPERADORES(int ID_OPERADORES) {
        this.ID_OPERADORES = ID_OPERADORES;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }
}
