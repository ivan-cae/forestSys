package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = @Index(value = "ID_INDICADOR", unique = true),
        foreignKeys = {@ForeignKey(entity = ATIVIDADES.class,
                parentColumns = "ID_ATIVIDADE",
                childColumns = "ID_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_INDICADOR", "ID_ATIVIDADE"})

public class ATIVIDADE_INDICADORES {

    @ColumnInfo(name = "ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    @ColumnInfo(name = "ID_INDICADOR")
    private int ID_INDICADOR;

    private int ORDEM_INDICADOR;

    private String REFERENCIA;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    private String VERION;

    public ATIVIDADE_INDICADORES(int ID_INDICADOR, int ID_ATIVIDADE, int ORDEM_INDICADOR, String REFERENCIA, String DESCRICAO, int ATIVO, String VERION) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_INDICADOR = ID_INDICADOR;
        this.ORDEM_INDICADOR = ORDEM_INDICADOR;
        this.REFERENCIA = REFERENCIA;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
        this.VERION = VERION;
    }

    public int getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(int ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public int getID_INDICADOR() {
        return ID_INDICADOR;
    }

    public void setID_INDICADOR(int ID_INDICADOR) {
        this.ID_INDICADOR = ID_INDICADOR;
    }

    public int getORDEM_INDICADOR() {
        return ORDEM_INDICADOR;
    }

    public void setORDEM_INDICADOR(int ORDEM_INDICADOR) {
        this.ORDEM_INDICADOR = ORDEM_INDICADOR;
    }

    public String getREFERENCIA() {
        return REFERENCIA;
    }

    public void setREFERENCIA(String REFERENCIA) {
        this.REFERENCIA = REFERENCIA;
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

    public String getVERION() {
        return VERION;
    }

    public void setVERION(String VERION) {
        this.VERION = VERION;
    }
}
