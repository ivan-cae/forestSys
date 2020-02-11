package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

@Entity(
        foreignKeys = {@ForeignKey(entity = CADASTRO_FLORESTAL.class,
                parentColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                childColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = ATIVIDADES.class,
                        parentColumns = "ID_ATIVIDADE",
                        childColumns = "ID_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = GGF_USUARIOS.class,
                        parentColumns = "ID_USUARIO",
                        childColumns = "ID_RESPONSAVEL",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)})

public class O_S_ATIVIDADES {

    @PrimaryKey
    private int ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name = "ID_REGIONAL")
    private int ID_REGIONAL;

    @ColumnInfo(name = "ID_SETOR")
    private int ID_SETOR;

    @ColumnInfo(name = "TALHAO")
    private int TALHAO;

    @ColumnInfo(name = "CICLO")
    private int CICLO;

    @ColumnInfo(name = "ID_MANEJO")
    private int ID_MANEJO;

    @ColumnInfo(name = "ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    @ColumnInfo(name = "ID_RESPONSAVEL")
    private int ID_RESPONSAVEL;

    @TypeConverters({TimestampConverter.class})
    private Date DATA_PROGRAMADA;

    private double AREA_PROGRAMADA;
    private int PRIORIDADE;
    private int EXPERIMENTO;
    private int MADEIRA_NO_TALHAO;
    private String OBSERVACAO;

    @TypeConverters({TimestampConverter.class})
    private Date DATA_INICIAL;

    @TypeConverters({TimestampConverter.class})
    private Date DATA_FINAL;

    private double AREA_REALIZADA;

    public O_S_ATIVIDADES(int ID_PROGRAMACAO_ATIVIDADE, int ID_REGIONAL, int ID_SETOR, int TALHAO, int CICLO, int ID_MANEJO, int ID_ATIVIDADE,
                          int ID_RESPONSAVEL, Date DATA_PROGRAMADA, double AREA_PROGRAMADA, int PRIORIDADE, int EXPERIMENTO, int MADEIRA_NO_TALHAO,
                          String OBSERVACAO, Date DATA_INICIAL, Date DATA_FINAL, double AREA_REALIZADA) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.ID_REGIONAL = ID_REGIONAL;
        this.ID_SETOR = ID_SETOR;
        this.TALHAO = TALHAO;
        this.CICLO = CICLO;
        this.ID_MANEJO = ID_MANEJO;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_RESPONSAVEL = ID_RESPONSAVEL;
        this.DATA_PROGRAMADA = DATA_PROGRAMADA;
        this.AREA_PROGRAMADA = AREA_PROGRAMADA;
        this.PRIORIDADE = PRIORIDADE;
        this.EXPERIMENTO = EXPERIMENTO;
        this.MADEIRA_NO_TALHAO = MADEIRA_NO_TALHAO;
        this.OBSERVACAO = OBSERVACAO;
        this.DATA_INICIAL = DATA_INICIAL;
        this.DATA_FINAL = DATA_FINAL;
        this.AREA_REALIZADA = AREA_REALIZADA;
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
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

    public int getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(int TALHAO) {
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

    public int getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(int ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public int getID_RESPONSAVEL() {
        return ID_RESPONSAVEL;
    }

    public void setID_RESPONSAVEL(int ID_RESPONSAVEL) {
        this.ID_RESPONSAVEL = ID_RESPONSAVEL;
    }

    public Date getDATA_PROGRAMADA() {
        return DATA_PROGRAMADA;
    }

    public void setDATA_PROGRAMADA(Date DATA_PROGRAMADA) {
        this.DATA_PROGRAMADA = DATA_PROGRAMADA;
    }

    public double getAREA_PROGRAMADA() {
        return AREA_PROGRAMADA;
    }

    public void setAREA_PROGRAMADA(double AREA_PROGRAMADA) {
        this.AREA_PROGRAMADA = AREA_PROGRAMADA;
    }

    public int getPRIORIDADE() {
        return PRIORIDADE;
    }

    public void setPRIORIDADE(int PRIORIDADE) {
        this.PRIORIDADE = PRIORIDADE;
    }

    public int getEXPERIMENTO() {
        return EXPERIMENTO;
    }

    public void setEXPERIMENTO(int EXPERIMENTO) {
        this.EXPERIMENTO = EXPERIMENTO;
    }

    public int getMADEIRA_NO_TALHAO() {
        return MADEIRA_NO_TALHAO;
    }

    public void setMADEIRA_NO_TALHAO(int MADEIRA_NO_TALHAO) {
        this.MADEIRA_NO_TALHAO = MADEIRA_NO_TALHAO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public Date getDATA_INICIAL() {
        return DATA_INICIAL;
    }

    public void setDATA_INICIAL(Date DATA_INICIAL) {
        this.DATA_INICIAL = DATA_INICIAL;
    }

    public Date getDATA_FINAL() {
        return DATA_FINAL;
    }

    public void setDATA_FINAL(Date DATA_FINAL) {
        this.DATA_FINAL = DATA_FINAL;
    }

    public double getAREA_REALIZADA() {
        return AREA_REALIZADA;
    }

    public void setAREA_REALIZADA(double AREA_REALIZADA) {
        this.AREA_REALIZADA = AREA_REALIZADA;
    }
}