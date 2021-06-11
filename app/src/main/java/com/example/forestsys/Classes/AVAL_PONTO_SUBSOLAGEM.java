package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.forestsys.Assets.Ferramentas;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(entity = O_S_ATIVIDADES.class,
                parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                childColumns  = "ID_PROGRAMACAO_ATIVIDADE",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = ATIVIDADES.class,
                        parentColumns = "ID_ATIVIDADE",
                        childColumns  = "ID_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = ATIVIDADE_INDICADORES.class,
                        parentColumns = "ID_INDICADOR",
                        childColumns  = "ID_INDICADOR",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA", "PONTO", "ID_ATIVIDADE", "ID_INDICADOR"})

public class AVAL_PONTO_SUBSOLAGEM implements Serializable {

    @ColumnInfo(name="ID_PROGRAMACAO_ATIVIDADE")
    @NonNull
    private Integer ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name="DATA")
    @NonNull
    private String DATA;

    @ColumnInfo(name="PONTO")
    @NonNull
    private Integer PONTO;

    @ColumnInfo(name="ID_ATIVIDADE")
    @NonNull
    private Integer ID_ATIVIDADE;

    @ColumnInfo(name="ID_INDICADOR")
    @NonNull
    private Integer ID_INDICADOR;

    private double VALOR_INDICADOR;

    private double COORDENADA_X;

    private double COORDENADA_Y;

    private Integer NC_TRATADA;

    private String UPDATED_AT;

    private Integer editou;

    public AVAL_PONTO_SUBSOLAGEM(Integer ID_PROGRAMACAO_ATIVIDADE, @NonNull String DATA, Integer PONTO, Integer ID_ATIVIDADE,
                                 Integer ID_INDICADOR, double VALOR_INDICADOR, double COORDENADA_X,
                                 double COORDENADA_Y, Integer NC_TRATADA, String UPDATED_AT, Integer editou) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.PONTO = PONTO;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_INDICADOR = ID_INDICADOR;
        this.VALOR_INDICADOR = VALOR_INDICADOR;
        this.COORDENADA_X = COORDENADA_X;
        this.COORDENADA_Y = COORDENADA_Y;
        this.NC_TRATADA = NC_TRATADA;
        this.UPDATED_AT = UPDATED_AT;
        this.editou = editou;
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

    public Integer getPONTO() {
        return PONTO;
    }

    public void setPONTO(Integer PONTO) {
        this.PONTO = PONTO;
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

    public double getVALOR_INDICADOR() {
        return VALOR_INDICADOR;
    }

    public void setVALOR_INDICADOR(double VALOR_INDICADOR) {
        this.VALOR_INDICADOR = VALOR_INDICADOR;
    }

    public double getCOORDENADA_X() {
        return COORDENADA_X;
    }

    public void setCOORDENADA_X(double COORDENADA_X) {
        this.COORDENADA_X = COORDENADA_X;
    }

    public double getCOORDENADA_Y() {
        return COORDENADA_Y;
    }

    public void setCOORDENADA_Y(double COORDENADA_Y) {
        this.COORDENADA_Y = COORDENADA_Y;
    }

    public Integer getNC_TRATADA() {
        return NC_TRATADA;
    }

    public void setNC_TRATADA(Integer NC_TRATADA) {
        this.NC_TRATADA = NC_TRATADA;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public void setUPDATED_AT(String UPDATED_AT) {
        this.UPDATED_AT = UPDATED_AT;
    }

    public Integer getEditou() {
        return editou;
    }

    public void setEditou(Integer editou) {
        this.editou = editou;
    }

    @Override
    public String toString() {
        Ferramentas ferramentas = new Ferramentas();
        return "Ponto: "+PONTO.toString() +" Data: "+ ferramentas.formataDataTextView(DATA);
    }
}
