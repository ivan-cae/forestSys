package com.example.forestsys.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MATERIAL_GENETICO implements Serializable {
    @PrimaryKey
    private Integer ID_MATERIAL_GENETICO;

    private String DESCRICAO;

    private Integer ATIVO;

    public MATERIAL_GENETICO(Integer ID_MATERIAL_GENETICO, String DESCRICAO, Integer ATIVO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_MATERIAL_GENETICO() {
        return ID_MATERIAL_GENETICO;
    }

    public void setID_MATERIAL_GENETICO(Integer ID_MATERIAL_GENETICO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }
}
