package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = @Index(value = "ID_INDICADOR", unique = true),
        foreignKeys = {@ForeignKey(entity = ATIVIDADES.class,
                parentColumns = "ID_ATIVIDADE",
                childColumns = "ID_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_INDICADOR", "ID_ATIVIDADE"})

public class ATIVIDADE_INDICADORES implements Serializable {

    @ColumnInfo(name = "ID_ATIVIDADE")
    @NonNull
    private Integer ID_ATIVIDADE;

    @ColumnInfo(name = "ID_INDICADOR")
    @NonNull
    private Integer ID_INDICADOR;

    private Integer ORDEM_INDICADOR;

    private String REFERENCIA;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;
    private String VERION;
    private String FORMULA;
    private Integer INDICADOR_CORRIGIVEL;
    private Integer LIMITE_INFERIOR;
    private Integer LIMITE_SUPERIOR;
    private Integer CASAS_DECIMAIS;

    public ATIVIDADE_INDICADORES(Integer ID_INDICADOR, Integer ID_ATIVIDADE, Integer ORDEM_INDICADOR, String REFERENCIA, String DESCRICAO,
                                 Integer ATIVO, String VERION, Integer LIMITE_SUPERIOR, Integer LIMITE_INFERIOR, Integer CASAS_DECIMAIS, Integer INDICADOR_CORRIGIVEL, String FORMULA) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_INDICADOR = ID_INDICADOR;
        this.ORDEM_INDICADOR = ORDEM_INDICADOR;
        this.REFERENCIA = REFERENCIA;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
        this.VERION = VERION;
        this.LIMITE_SUPERIOR = LIMITE_SUPERIOR;
        this.LIMITE_INFERIOR = LIMITE_INFERIOR;
        this.CASAS_DECIMAIS = CASAS_DECIMAIS;
        this.INDICADOR_CORRIGIVEL = INDICADOR_CORRIGIVEL;
        this.FORMULA = FORMULA;
    }

    public Integer getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(Integer ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public Integer getID_INDICADOR() {
        return ID_INDICADOR;
    }

    public void setID_INDICADOR(Integer ID_INDICADOR) {
        this.ID_INDICADOR = ID_INDICADOR;
    }

    public Integer getORDEM_INDICADOR() {
        return ORDEM_INDICADOR;
    }

    public void setORDEM_INDICADOR(Integer ORDEM_INDICADOR) {
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

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getVERION() {
        return VERION;
    }

    public void setVERION(String VERION) {
        this.VERION = VERION;
    }

    public String getFORMULA() {
        return FORMULA;
    }

    public void setFORMULA(String FORMULA) {
        this.FORMULA = FORMULA;
    }

    public Integer getINDICADOR_CORRIGIVEL() {
        return INDICADOR_CORRIGIVEL;
    }

    public void setINDICADOR_CORRIGIVEL(Integer INDICADOR_CORRIGIVEL) {
        this.INDICADOR_CORRIGIVEL = INDICADOR_CORRIGIVEL;
    }

    public Integer getLIMITE_INFERIOR() {
        return LIMITE_INFERIOR;
    }

    public void setLIMITE_INFERIOR(Integer LIMITE_INFERIOR) {
        this.LIMITE_INFERIOR = LIMITE_INFERIOR;
    }

    public Integer getLIMITE_SUPERIOR() {
        return LIMITE_SUPERIOR;
    }

    public void setLIMITE_SUPERIOR(Integer LIMITE_SUPERIOR) {
        this.LIMITE_SUPERIOR = LIMITE_SUPERIOR;
    }

    public Integer getCASAS_DECIMAIS() {
        return CASAS_DECIMAIS;
    }

    public void setCASAS_DECIMAIS(Integer CASAS_DECIMAIS) {
        this.CASAS_DECIMAIS = CASAS_DECIMAIS;
    }
}
