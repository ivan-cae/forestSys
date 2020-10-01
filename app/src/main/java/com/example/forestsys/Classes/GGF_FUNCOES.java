package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_FUNCOES {
    @PrimaryKey
    private int ID_FUNCAO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public GGF_FUNCOES(int ID_FUNCAO, String DESCRICAO, int ATIVO) {
        this.ID_FUNCAO = ID_FUNCAO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_FUNCAO() {
        return ID_FUNCAO;
    }

    public void setID_FUNCAO(int ID_FUNCAO) {
        this.ID_FUNCAO = ID_FUNCAO;
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
