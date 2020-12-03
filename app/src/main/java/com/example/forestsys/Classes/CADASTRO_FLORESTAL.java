package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(entity = GEO_REGIONAIS.class,
                parentColumns = "ID_REGIONAL",
                childColumns = "ID_REGIONAL",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = GEO_SETORES.class,
                        parentColumns = "ID_SETOR",
                        childColumns = "ID_SETOR",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = MANEJO.class,
                        parentColumns = "ID_MANEJO",
                        childColumns = "ID_MANEJO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = MATERIAL_GENETICO.class,
                        parentColumns = "ID_MATERIAL_GENETICO",
                        childColumns = "ID_MATERIAL_GENETICO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = ESPACAMENTOS.class,
                        parentColumns = "ID_ESPACAMENTO",
                        childColumns = "ID_ESPACAMENTO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},

        primaryKeys = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"})

public class CADASTRO_FLORESTAL implements Serializable {
    @NonNull
    @ColumnInfo(name = "ID_REGIONAL")
    private Integer ID_REGIONAL;

    @NonNull
    @ColumnInfo(name = "ID_SETOR")
    private Integer ID_SETOR;

    @NonNull
    @ColumnInfo(name = "TALHAO")
    private String TALHAO;

    @NonNull
    @ColumnInfo(name = "CICLO")
    private Integer CICLO;

    @NonNull
    @ColumnInfo(name = "ID_MANEJO")
    private Integer ID_MANEJO;

    private String DATA_MANEJO;

    private String DATA_PROGRAMACAO_REFORMA;

    @ColumnInfo(name = "ID_MATERIAL_GENETICO")
    private Integer ID_MATERIAL_GENETICO;

    @ColumnInfo(name = "ID_ESPACAMENTO")
    private Integer ID_ESPACAMENTO;

    private String OBSERVACAO;

    private Integer ATIVO;

    public CADASTRO_FLORESTAL(Integer ID_REGIONAL, Integer ID_SETOR, @NonNull String TALHAO, Integer CICLO, Integer ID_MANEJO, String DATA_MANEJO, String DATA_PROGRAMACAO_REFORMA, Integer ID_MATERIAL_GENETICO, Integer ID_ESPACAMENTO, String OBSERVACAO, Integer ATIVO) {
        this.ID_REGIONAL = ID_REGIONAL;
        this.ID_SETOR = ID_SETOR;
        this.TALHAO = TALHAO;
        this.CICLO = CICLO;
        this.ID_MANEJO = ID_MANEJO;
        this.DATA_MANEJO = DATA_MANEJO;
        this.DATA_PROGRAMACAO_REFORMA = DATA_PROGRAMACAO_REFORMA;
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
        this.OBSERVACAO = OBSERVACAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_REGIONAL() {
        return ID_REGIONAL;
    }

    public void setID_REGIONAL(Integer ID_REGIONAL) {
        this.ID_REGIONAL = ID_REGIONAL;
    }

    public Integer getID_SETOR() {
        return ID_SETOR;
    }

    public void setID_SETOR(Integer ID_SETOR) {
        this.ID_SETOR = ID_SETOR;
    }

    @NonNull
    public String getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(@NonNull String TALHAO) {
        this.TALHAO = TALHAO;
    }

    public Integer getCICLO() {
        return CICLO;
    }

    public void setCICLO(Integer CICLO) {
        this.CICLO = CICLO;
    }

    public Integer getID_MANEJO() {
        return ID_MANEJO;
    }

    public void setID_MANEJO(Integer ID_MANEJO) {
        this.ID_MANEJO = ID_MANEJO;
    }

    public String getDATA_MANEJO() {
        return DATA_MANEJO;
    }

    public void setDATA_MANEJO(String DATA_MANEJO) {
        this.DATA_MANEJO = DATA_MANEJO;
    }

    public String getDATA_PROGRAMACAO_REFORMA() {
        return DATA_PROGRAMACAO_REFORMA;
    }

    public void setDATA_PROGRAMACAO_REFORMA(String DATA_PROGRAMACAO_REFORMA) {
        this.DATA_PROGRAMACAO_REFORMA = DATA_PROGRAMACAO_REFORMA;
    }

    public Integer getID_MATERIAL_GENETICO() {
        return ID_MATERIAL_GENETICO;
    }

    public void setID_MATERIAL_GENETICO(Integer ID_MATERIAL_GENETICO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
    }

    public Integer getID_ESPACAMENTO() {
        return ID_ESPACAMENTO;
    }

    public void setID_ESPACAMENTO(Integer ID_ESPACAMENTO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }
}
