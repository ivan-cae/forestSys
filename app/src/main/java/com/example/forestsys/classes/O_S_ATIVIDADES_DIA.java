package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

@Entity(
        foreignKeys = {@ForeignKey(entity = O_S_ATIVIDADES.class,
                parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                childColumns = "ID_PROGRAMACAO_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

        @ForeignKey(entity = PRESTADORES.class,
                parentColumns = "ID_PRESTADOR",
                childColumns = "ID_PRESTADOR",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

        @ForeignKey(entity = GGF_USUARIOS.class,
                parentColumns = "ID_USUARIO",
                childColumns = "ID_RESPONSAVEL",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)},

        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA"})

public class O_S_ATIVIDADES_DIA {
    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    private int ID_PROGRAMACAO_ATIVIDADE;

    @NonNull
    @TypeConverters({TimestampConverter.class})
    @ColumnInfo(name = "DATA")
    private Date DATA;

    @ColumnInfo(name = "ID_PRESTADOR")
    private int ID_PRESTADOR;

    @ColumnInfo(name = "ID_RESPONSAVEL")
    private int ID_RESPONSAVEL;

    private char STATUS;
    private double AREA_REALIZADA;
    private double HH;
    private double HM;
    private double HO;
    private double HM_ESCAVADEIRA;
    private double HO_ESCAVADEIRA;
    private String OBSERVACAO;
    private char REGISTRO_DESCARREGADO;

    public O_S_ATIVIDADES_DIA(int ID_PROGRAMACAO_ATIVIDADE, Date DATA, int ID_PRESTADOR, int ID_RESPONSAVEL, char STATUS, double AREA_REALIZADA,
                              double HH, double HM, double HO, double HM_ESCAVADEIRA, double HO_ESCAVADEIRA, String OBSERVACAO,
                              char REGISTRO_DESCARREGADO) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.ID_PRESTADOR = ID_PRESTADOR;
        this.ID_RESPONSAVEL = ID_RESPONSAVEL;
        this.STATUS = STATUS;
        this.AREA_REALIZADA = AREA_REALIZADA;
        this.HH = HH;
        this.HM = HM;
        this.HO = HO;
        this.HM_ESCAVADEIRA = HM_ESCAVADEIRA;
        this.HO_ESCAVADEIRA = HO_ESCAVADEIRA;
        this.OBSERVACAO = OBSERVACAO;
        this.REGISTRO_DESCARREGADO = REGISTRO_DESCARREGADO;
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    public Date getDATA() {
        return DATA;
    }

    public void setDATA(Date DATA) {
        this.DATA = DATA;
    }

    public int getID_PRESTADOR() {
        return ID_PRESTADOR;
    }

    public void setID_PRESTADOR(int ID_PRESTADOR) {
        this.ID_PRESTADOR = ID_PRESTADOR;
    }

    public int getID_RESPONSAVEL() {
        return ID_RESPONSAVEL;
    }

    public void setID_RESPONSAVEL(int ID_RESPONSAVEL) {
        this.ID_RESPONSAVEL = ID_RESPONSAVEL;
    }

    public char getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(char STATUS) {
        this.STATUS = STATUS;
    }

    public double getAREA_REALIZADA() {
        return AREA_REALIZADA;
    }

    public void setAREA_REALIZADA(double AREA_REALIZADA) {
        this.AREA_REALIZADA = AREA_REALIZADA;
    }

    public double getHH() {
        return HH;
    }

    public void setHH(double HH) {
        this.HH = HH;
    }

    public double getHM() {
        return HM;
    }

    public void setHM(double HM) {
        this.HM = HM;
    }

    public double getHO() {
        return HO;
    }

    public void setHO(double HO) {
        this.HO = HO;
    }

    public double getHM_ESCAVADEIRA() {
        return HM_ESCAVADEIRA;
    }

    public void setHM_ESCAVADEIRA(double HM_ESCAVADEIRA) {
        this.HM_ESCAVADEIRA = HM_ESCAVADEIRA;
    }

    public double getHO_ESCAVADEIRA() {
        return HO_ESCAVADEIRA;
    }

    public void setHO_ESCAVADEIRA(double HO_ESCAVADEIRA) {
        this.HO_ESCAVADEIRA = HO_ESCAVADEIRA;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public char getREGISTRO_DESCARREGADO() {
        return REGISTRO_DESCARREGADO;
    }

    public void setREGISTRO_DESCARREGADO(char REGISTRO_DESCARREGADO) {
        this.REGISTRO_DESCARREGADO = REGISTRO_DESCARREGADO;
    }
}
