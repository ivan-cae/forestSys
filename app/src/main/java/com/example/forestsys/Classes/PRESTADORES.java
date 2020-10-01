package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class PRESTADORES {
    @PrimaryKey
    private int ID_PRESTADOR;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public PRESTADORES(int ID_PRESTADOR, String DESCRICAO, int ATIVO) {
        this.ID_PRESTADOR = ID_PRESTADOR;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_PRESTADOR() {
        return ID_PRESTADOR;
    }

    public void setID_PRESTADOR(int ID_PRESTADOR) {
        this.ID_PRESTADOR = ID_PRESTADOR;
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
