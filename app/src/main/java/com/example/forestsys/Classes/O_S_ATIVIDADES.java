package com.example.forestsys.Classes;

import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.Assets.Ferramentas;

import java.io.Serializable;

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
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = CADASTRO_FLORESTAL.class,
                        parentColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                        childColumns = {"ID_REGIONAL", "ID_SETOR", "TALHAO", "CICLO", "ID_MANEJO"},
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)})


public class O_S_ATIVIDADES implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    private Integer ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name = "ID_REGIONAL")
    private Integer ID_REGIONAL;

    @ColumnInfo(name = "ID_SETOR")
    private Integer ID_SETOR;

    @ColumnInfo(name = "TALHAO")
    private String TALHAO;

    @ColumnInfo(name = "CICLO")
    private Integer CICLO;

    @ColumnInfo(name = "ID_MANEJO")
    private Integer ID_MANEJO;

    @ColumnInfo(name = "ID_ATIVIDADE")
    private Integer ID_ATIVIDADE;

    private String DATA_PROGRAMADA;

    private double AREA_PROGRAMADA;
    private Integer PRIORIDADE;
    private Integer EXPERIMENTO;
    private Integer MADEIRA_NO_TALHAO;
    private String OBSERVACAO;

    private String DATA_INICIAL;

    private String DATA_FINAL;

    private double AREA_REALIZADA;

    @Nullable
    @ColumnInfo(name = "ID_RESPONSAVEL")
    private Integer ID_RESPONSAVEL;

    private String STATUS;

    private Integer STATUS_NUM;

    private String UPDATED_AT;

    private Integer EXPORT_PROXIMA_SINC;

    public O_S_ATIVIDADES(Integer ID_PROGRAMACAO_ATIVIDADE, Integer ID_REGIONAL, Integer ID_SETOR, String TALHAO,
                          Integer CICLO, Integer ID_MANEJO, Integer ID_ATIVIDADE, Integer ID_RESPONSAVEL,
                          String DATA_PROGRAMADA, double AREA_PROGRAMADA, Integer PRIORIDADE, Integer EXPERIMENTO,
                          Integer MADEIRA_NO_TALHAO, String OBSERVACAO, String DATA_INICIAL, String DATA_FINAL,
                          double AREA_REALIZADA, String STATUS, Integer STATUS_NUM, String UPDATED_AT) {
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
        this.UPDATED_AT = UPDATED_AT;
    }

    public Integer getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(Integer ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
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

    public String getTALHAO() {
        return TALHAO;
    }

    public void setTALHAO(String TALHAO) {
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

    public Integer getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(Integer ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public Integer getID_RESPONSAVEL() {
        return ID_RESPONSAVEL;
    }

    public void setID_RESPONSAVEL(Integer ID_RESPONSAVEL) {
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

    public Integer getPRIORIDADE() {
        return PRIORIDADE;
    }

    public void setPRIORIDADE(Integer PRIORIDADE) {
        this.PRIORIDADE = PRIORIDADE;
    }

    public Integer getEXPERIMENTO() {
        return EXPERIMENTO;
    }

    public void setEXPERIMENTO(Integer EXPERIMENTO) {
        this.EXPERIMENTO = EXPERIMENTO;
    }

    public Integer getMADEIRA_NO_TALHAO() {
        return MADEIRA_NO_TALHAO;
    }

    public void setMADEIRA_NO_TALHAO(Integer MADEIRA_NO_TALHAO) {
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
        setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
        this.DATA_INICIAL = DATA_INICIAL;
    }

    public String getDATA_FINAL() {
        return DATA_FINAL;
    }

    public void setDATA_FINAL(String DATA_FINAL) {
        setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
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

    public Integer getSTATUS_NUM() {
        return STATUS_NUM;
    }

    public void setSTATUS_NUM(Integer STATUS_NUM) {
        this.STATUS_NUM = STATUS_NUM;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public void setUPDATED_AT(String UPDATED_AT) {
        //Log.wtf("Atualizado em", UPDATED_AT);
        this.setEXPORT_PROXIMA_SINC(1);
        this.UPDATED_AT = UPDATED_AT;
    }

    public Integer getEXPORT_PROXIMA_SINC() {
        return EXPORT_PROXIMA_SINC;
    }

    public void setEXPORT_PROXIMA_SINC(Integer EXPORT_PROXIMA_SINC) {
        this.EXPORT_PROXIMA_SINC = EXPORT_PROXIMA_SINC;
    }
}