package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class ESPACAMENTOS {
    @PrimaryKey
    private int ID_ESPACAMENTO;
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public ESPACAMENTOS(int ID_ESPACAMENTO, int ATIVO, String DESCRICAO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_ESPACAMENTO() {
        return ID_ESPACAMENTO;
    }

    public void setID_ESPACAMENTO(int ID_ESPACAMENTO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
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
