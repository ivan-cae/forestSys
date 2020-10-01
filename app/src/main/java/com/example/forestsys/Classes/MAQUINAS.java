package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO_MAQUINA", unique = true)}
)

public class MAQUINAS {

    @PrimaryKey
    private int ID_MAQUINA;

    @ColumnInfo(name = "DESCRICAO_MAQUINA")
    private String DESCRICAO;

    private int ATIVO;

    public MAQUINAS(int ID_MAQUINA, String DESCRICAO, int ATIVO) {
        this.ID_MAQUINA = ID_MAQUINA;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(int ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
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
