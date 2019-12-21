package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)

public class MAQUINAS {
    @PrimaryKey
    private int ID_MAQUINA;
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public MAQUINAS(int ID_MAQUINA, int ATIVO, String DESCRICAO) {
        this.ID_MAQUINA = ID_MAQUINA;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(int ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
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
