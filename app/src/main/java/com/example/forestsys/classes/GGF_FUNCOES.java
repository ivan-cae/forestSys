package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_FUNCOES {
    @PrimaryKey
    private int ID_FUNCAO;
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public GGF_FUNCOES(int ID_FUNCAO, int ATIVO, String DESCRICAO) {
        this.ID_FUNCAO = ID_FUNCAO;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_FUNCAO() {
        return ID_FUNCAO;
    }

    public void setID_FUNCAO(int ID_FUNCAO) {
        this.ID_FUNCAO = ID_FUNCAO;
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
