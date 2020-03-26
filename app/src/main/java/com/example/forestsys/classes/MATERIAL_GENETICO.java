package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MATERIAL_GENETICO {
    @PrimaryKey
    private int ID_MATERIAL_GENETICO;

    private String DESCRICAO;
    private int ATIVO;

    public MATERIAL_GENETICO(int ID_MATERIAL_GENETICO, String DESCRICAO, int ATIVO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_MATERIAL_GENETICO() {
        return ID_MATERIAL_GENETICO;
    }

    public void setID_MATERIAL_GENETICO(int ID_MATERIAL_GENETICO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
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
