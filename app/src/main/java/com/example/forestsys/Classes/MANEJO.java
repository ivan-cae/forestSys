package com.example.forestsys.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MANEJO implements Serializable {
    @PrimaryKey
    private Integer ID_MANEJO;
	private String DESCRICAO;
	private Integer ATIVO;

    public MANEJO(Integer ID_MANEJO, String DESCRICAO, Integer ATIVO) {
        this.ID_MANEJO = ID_MANEJO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_MANEJO() {
        return ID_MANEJO;
    }

    public void setID_MANEJO(Integer ID_MANEJO) {
        this.ID_MANEJO = ID_MANEJO;
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
