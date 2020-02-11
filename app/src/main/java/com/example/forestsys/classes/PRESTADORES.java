package com.example.forestsys.classes;

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

    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public PRESTADORES(int ID_PRESTADOR, int ATIVO, String DESCRICAO) {
        this.ID_PRESTADOR = ID_PRESTADOR;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_PRESTADOR() {
        return ID_PRESTADOR;
    }

    public void setID_PRESTADOR(int ID_PRESTADOR) {
        this.ID_PRESTADOR = ID_PRESTADOR;
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
