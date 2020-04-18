package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

@Entity(
        foreignKeys = {@ForeignKey(entity = O_S_ATIVIDADES.class,
                parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                childColumns =  "ID_PROGRAMACAO_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

        @ForeignKey(entity = PRESTADORES.class,
                parentColumns = "ID_PRESTADOR",
                childColumns =  "ID_PRESTADOR",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

        @ForeignKey(entity = GGF_USUARIOS.class,
                parentColumns = "ID_USUARIO",
                childColumns =  "ID_RESPONSAVEL",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION)},
            primaryKeys ={"ID_PROGRAMACAO_ATIVIDADE", "DATA"})

public class O_S_ATIVIDADES_DIA {
    @NonNull
    @ColumnInfo(name="ID_PROGRAMACAO_ATIVIDADE")
    private Integer ID_PROGRAMACAO_ATIVIDADE;

    @NonNull
    @ColumnInfo(name = "DATA")
    private String DATA;

    @ColumnInfo(name = "ID_PRESTADOR")
    private Integer ID_PRESTADOR;

    @ColumnInfo(name = "ID_RESPONSAVEL")
    private Integer ID_RESPONSAVEL;

    private char STATUS;
    private String AREA_REALIZADA;
    private String HH;
    private String HM;
    private String HO;
    private String HM_ESCAVADEIRA;
    private String HO_ESCAVADEIRA;
    private String OBSERVACAO;
    private char REGISTRO_DESCARREGADO;


    public O_S_ATIVIDADES_DIA() {
    }

    public Integer getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(Integer ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    @NonNull
    public String getDATA() {
        return DATA;
    }

    public void setDATA(@NonNull String DATA) {
        this.DATA = DATA;
    }

    public Integer getID_PRESTADOR() {
        return ID_PRESTADOR;
    }

    public void setID_PRESTADOR(Integer ID_PRESTADOR) {
        this.ID_PRESTADOR = ID_PRESTADOR;
    }

    public Integer getID_RESPONSAVEL() {
        return ID_RESPONSAVEL;
    }

    public void setID_RESPONSAVEL(Integer ID_RESPONSAVEL) {
        this.ID_RESPONSAVEL = ID_RESPONSAVEL;
    }

    public char getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(char STATUS) {
        this.STATUS = STATUS;
    }

    public String getAREA_REALIZADA() {
        return AREA_REALIZADA;
    }

    public void setAREA_REALIZADA(String AREA_REALIZADA) {
        this.AREA_REALIZADA = AREA_REALIZADA;
    }

    public String getHH() {
        return HH;
    }

    public void setHH(String HH) {
        this.HH = HH;
    }

    public String getHM() {
        return HM;
    }

    public void setHM(String HM) {
        this.HM = HM;
    }

    public String getHO() {
        return HO;
    }

    public void setHO(String HO) {
        this.HO = HO;
    }

    public String getHM_ESCAVADEIRA() {
        return HM_ESCAVADEIRA;
    }

    public void setHM_ESCAVADEIRA(String HM_ESCAVADEIRA) {
        this.HM_ESCAVADEIRA = HM_ESCAVADEIRA;
    }

    public String getHO_ESCAVADEIRA() {
        return HO_ESCAVADEIRA;
    }

    public void setHO_ESCAVADEIRA(String HO_ESCAVADEIRA) {
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
