package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(
        foreignKeys = {
        @ForeignKey(entity = GGF_USUARIOS.class,
                        parentColumns = "ID_USUARIO",
                        childColumns = "ID_RESPONSAVEL",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = ATIVIDADES.class,
                        parentColumns = "ID_ATIVIDADE",
                        childColumns = "ID_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)})
/*        @ForeignKey(entity = CADASTRO_FLORESTAL.class,
                parentColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                childColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)
                */

public class O_S_ATIVIDADES {

    @PrimaryKey
    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    private int ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name = "ID_REGIONAL")
    private int ID_REGIONAL;

    @ColumnInfo(name = "ID_SETOR")
    private int ID_SETOR;

    @ColumnInfo(name = "TALHAO")
    private String TALHAO;

    @ColumnInfo(name = "CICLO")
    private int CICLO;

    @ColumnInfo(name = "ID_MANEJO")
    private int ID_MANEJO;

    @ColumnInfo(name = "ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    @ColumnInfo(name = "ID_RESPONSAVEL")
    private int ID_RESPONSAVEL;

    private String DATA_PROGRAMADA;

    private double AREA_PROGRAMADA;
    private int PRIORIDADE;
    private int EXPERIMENTO;
    private int MADEIRA_NO_TALHAO;
    private String OBSERVACAO;

    private String DATA_INICIAL;

    private String DATA_FINAL;

    private double AREA_REALIZADA;

    private String STATUS;

    private int STATUS_NUM;

    public O_S_ATIVIDADES(int ID_PROGRAMACAO_ATIVIDADE, int ID_REGIONAL, int ID_SETOR, String TALHAO, int CICLO, int ID_MANEJO, int ID_ATIVIDADE, int ID_RESPONSAVEL, String DATA_PROGRAMADA, double AREA_PROGRAMADA, int PRIORIDADE, int EXPERIMENTO, int MADEIRA_NO_TALHAO, String OBSERVACAO, String DATA_INICIAL, String DATA_FINAL, double AREA_REALIZADA, String STATUS, int STATUS_NUM) {
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
        this.STATUS = STATUS;
        this.STATUS_NUM = STATUS_NUM;
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

    public String getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(String TALHAO) {
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

    public String getDATA_PROGRAMADA() {
        return DATA_PROGRAMADA;
    }

    public void setDATA_PROGRAMADA(String DATA_PROGRAMADA) {
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

    public String getDATA_INICIAL() {
        return DATA_INICIAL;
    }

    public void setDATA_INICIAL(String DATA_INICIAL) {
        this.DATA_INICIAL = DATA_INICIAL;
    }

    public String getDATA_FINAL() {
        return DATA_FINAL;
    }

    public void setDATA_FINAL(String DATA_FINAL) {
        this.DATA_FINAL = DATA_FINAL;
    }

    public double getAREA_REALIZADA() {
        return AREA_REALIZADA;
    }

    public void setAREA_REALIZADA(double AREA_REALIZADA) {
        this.AREA_REALIZADA = AREA_REALIZADA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public int getSTATUS_NUM() {
        return STATUS_NUM;
    }

    public void setSTATUS_NUM(int STATUS_NUM) {
        this.STATUS_NUM = STATUS_NUM;
    }
}