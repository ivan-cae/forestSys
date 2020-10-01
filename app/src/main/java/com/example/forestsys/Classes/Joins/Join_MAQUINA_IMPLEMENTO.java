package com.example.forestsys.Classes.Joins;

import androidx.annotation.NonNull;

public class Join_MAQUINA_IMPLEMENTO {
    int ID_MAQUINA_IMPLEMENTO;
    int ID_MAQUINA;
    int ID_IMPLEMENTO;
    String DESCRICAO_MAQUINA;
    String DESCRICAO_IMPLEMENTO;

    public Join_MAQUINA_IMPLEMENTO(int ID_MAQUINA_IMPLEMENTO, int ID_MAQUINA, int ID_IMPLEMENTO, String DESCRICAO_MAQUINA, String DESCRICAO_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
        this.ID_MAQUINA = ID_MAQUINA;
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
        this.DESCRICAO_MAQUINA = DESCRICAO_MAQUINA;
        this.DESCRICAO_IMPLEMENTO = DESCRICAO_IMPLEMENTO;
    }

    public int getID_MAQUINA_IMPLEMENTO() {
        return ID_MAQUINA_IMPLEMENTO;
    }

    public void setID_MAQUINA_IMPLEMENTO(int ID_MAQUINA_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
    }

    public int getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(int ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
    }

    public int getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(int ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }

    public String getDESCRICAO_MAQUINA() {
        return DESCRICAO_MAQUINA;
    }

    public void setDESCRICAO_MAQUINA(String DESCRICAO_MAQUINA) {
        this.DESCRICAO_MAQUINA = DESCRICAO_MAQUINA;
    }

    public String getDESCRICAO_IMPLEMENTO() {
        return DESCRICAO_IMPLEMENTO;
    }

    public void setDESCRICAO_IMPLEMENTO(String DESCRICAO_IMPLEMENTO) {
        this.DESCRICAO_IMPLEMENTO = DESCRICAO_IMPLEMENTO;
    }

    @NonNull
    @Override
    public String toString() {
        return DESCRICAO_IMPLEMENTO;
    }
}

