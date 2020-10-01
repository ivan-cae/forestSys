package com.example.forestsys.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MANEJO {
    @PrimaryKey
    private int ID_MANEJO;
	private String DESCRICAO;
	private int ATIVO;

    public MANEJO(int ID_MANEJO, String DESCRICAO, int ATIVO) {
        this.ID_MANEJO = ID_MANEJO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_MANEJO() {
        return ID_MANEJO;
    }

    public void setID_MANEJO(int ID_MANEJO) {
        this.ID_MANEJO = ID_MANEJO;
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
