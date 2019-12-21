package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO",unique = true)},

        foreignKeys = {@ForeignKey(entity = ATIVIDADES.class,
                parentColumns = "ID_ATIVIDADE",
                childColumns = "ID_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)},

        primaryKeys = {"ID_INDICADOR","ID_ATIVIDADE"})

public class ATIVIDADE_INDICADORES {
    @ColumnInfo (name = "ID_INDICADOR")
    private int ID_INDICADOR;

    @ColumnInfo (name = "ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    private int ORDEM_INDICADOR;
    private int ATIVO;

    @ColumnInfo(name="DESCRICAO")
    private String DESCRICAO;

    public ATIVIDADE_INDICADORES(int ID_INDICADOR, int ID_ATIVIDADE, int ORDEM_INDICADOR, int ATIVO, String DESCRICAO) {
        this.ID_INDICADOR = ID_INDICADOR;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ORDEM_INDICADOR = ORDEM_INDICADOR;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_INDICADOR() {
        return ID_INDICADOR;
    }

    public void setID_INDICADOR(int ID_INDICADOR) {
        this.ID_INDICADOR = ID_INDICADOR;
    }

    public int getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(int ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public int getORDEM_INDICADOR() {
        return ORDEM_INDICADOR;
    }

    public void setORDEM_INDICADOR(int ORDEM_INDICADOR) {
        this.ORDEM_INDICADOR = ORDEM_INDICADOR;
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
