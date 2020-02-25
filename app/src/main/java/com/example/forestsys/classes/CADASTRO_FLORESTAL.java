package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.util.Date;

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

public class CADASTRO_FLORESTAL {
    @NonNull
    @ColumnInfo (name = "ID_REGIONAL")
    private int ID_REGIONAL;

    @NonNull
    @ColumnInfo (name ="ID_SETOR")
    private int ID_SETOR;

    @NonNull
    @ColumnInfo (name = "TALHAO")
    private String TALHAO;

    @NonNull
    @ColumnInfo (name = "CICLO")
    private int CICLO;

    @NonNull
    @ColumnInfo (name = "ID_MANEJO")
    private int ID_MANEJO;

    @TypeConverters({TimestampConverter.class})
    private Date DATA_MANEJO;

    @NonNull
    @ColumnInfo (name = "ID_MATERIAL_GENETICO")
    private int ID_MATERIAL_GENETICO;

    @NonNull
    @ColumnInfo (name = "ID_ESPACAMENTO")
    private int ID_ESPACAMENTO;

    private String OBSERVACAO;

	private int ATIVO;

    @TypeConverters({TimestampConverter.class})
	private Date DATA_PROGRAMACAO_REFORMA;


    public CADASTRO_FLORESTAL(int ID_REGIONAL, int ID_SETOR, @NonNull String TALHAO, int CICLO, int ID_MANEJO, Date DATA_MANEJO, int ID_MATERIAL_GENETICO, int ID_ESPACAMENTO, String OBSERVACAO, int ATIVO, Date DATA_PROGRAMACAO_REFORMA) {
        this.ID_REGIONAL = ID_REGIONAL;
        this.ID_SETOR = ID_SETOR;
        this.TALHAO = TALHAO;
        this.CICLO = CICLO;
        this.ID_MANEJO = ID_MANEJO;
        this.DATA_MANEJO = DATA_MANEJO;
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
        this.OBSERVACAO = OBSERVACAO;
        this.ATIVO = ATIVO;
        this.DATA_PROGRAMACAO_REFORMA = DATA_PROGRAMACAO_REFORMA;
    }

    public int getID_REGIONAL() {
        return ID_REGIONAL;
    }

    public void setID_REGIONAL(int ID_REGIONAL) {
        this.ID_REGIONAL = ID_REGIONAL;
    }

    public int getID_SETOR() {
        return ID_SETOR;
    }

    public void setID_SETOR(int ID_SETOR) {
        this.ID_SETOR = ID_SETOR;
    }

    @NonNull
    public String getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(@NonNull String TALHAO) {
        this.TALHAO = TALHAO;
    }

    public int getCICLO() {
        return CICLO;
    }

    public void setCICLO(int CICLO) {
        this.CICLO = CICLO;
    }

    public int getID_MANEJO() {
        return ID_MANEJO;
    }

    public void setID_MANEJO(int ID_MANEJO) {
        this.ID_MANEJO = ID_MANEJO;
    }

    public Date getDATA_MANEJO() {
        return DATA_MANEJO;
    }

    public void setDATA_MANEJO(Date DATA_MANEJO) {
        this.DATA_MANEJO = DATA_MANEJO;
    }

    public int getID_MATERIAL_GENETICO() {
        return ID_MATERIAL_GENETICO;
    }

    public void setID_MATERIAL_GENETICO(int ID_MATERIAL_GENETICO) {
        this.ID_MATERIAL_GENETICO = ID_MATERIAL_GENETICO;
    }

    public int getID_ESPACAMENTO() {
        return ID_ESPACAMENTO;
    }

    public void setID_ESPACAMENTO(int ID_ESPACAMENTO) {
        this.ID_ESPACAMENTO = ID_ESPACAMENTO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }

    public Date getDATA_PROGRAMACAO_REFORMA() {
        return DATA_PROGRAMACAO_REFORMA;
    }

    public void setDATA_PROGRAMACAO_REFORMA(Date DATA_PROGRAMACAO_REFORMA) {
        this.DATA_PROGRAMACAO_REFORMA = DATA_PROGRAMACAO_REFORMA;
    }
}
