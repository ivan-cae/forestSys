package com.example.forestsys.Classes.Joins;

import androidx.annotation.NonNull;

import java.io.Serializable;

/*
 * Classe JOIN auxiliar que trata Máquina e Implemento como parte da mesma classe para facilitar a seleção de
 máquina e implemento na calibração
 */
public class Join_MAQUINA_IMPLEMENTO implements Serializable {
    Integer ID_MAQUINA_IMPLEMENTO;
    Integer ID_MAQUINA;
    Integer ID_IMPLEMENTO;
    String DESCRICAO_MAQUINA;
    String DESCRICAO_IMPLEMENTO;

    public Join_MAQUINA_IMPLEMENTO(Integer ID_MAQUINA_IMPLEMENTO, Integer ID_MAQUINA, Integer ID_IMPLEMENTO, String DESCRICAO_MAQUINA, String DESCRICAO_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
        this.ID_MAQUINA = ID_MAQUINA;
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
        this.DESCRICAO_MAQUINA = DESCRICAO_MAQUINA;
        this.DESCRICAO_IMPLEMENTO = DESCRICAO_IMPLEMENTO;
    }

    public Integer getID_MAQUINA_IMPLEMENTO() {
        return ID_MAQUINA_IMPLEMENTO;
    }

    public void setID_MAQUINA_IMPLEMENTO(Integer ID_MAQUINA_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
    }

    public Integer getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(Integer ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
    }

    public Integer getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(Integer ID_IMPLEMENTO) {
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

