package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        foreignKeys = {@ForeignKey(entity = O_S_ATIVIDADES.class,
                parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                childColumns  = "ID_PROGRAMACAO_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
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

public class AVAL_PONTO_SUBSOLAGEM {

    @ColumnInfo(name="ID_PROGRAMACAO_ATIVIDADE")
    private int ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name="DATA")
    @NonNull
    private String DATA;

    @ColumnInfo(name="PONTO")
    private int PONTO;

    @ColumnInfo(name="ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    @ColumnInfo(name="ID_INDICADOR")
    private int ID_INDICADOR;

    private double VALOR_INDICADOR;

    private double COORDENADA_X;

    private double COORDENADA_Y;

    private int NC_TRATADA;



    public AVAL_PONTO_SUBSOLAGEM(int ID_PROGRAMACAO_ATIVIDADE, @NonNull String DATA, int PONTO, int ID_ATIVIDADE, int ID_INDICADOR, double VALOR_INDICADOR, double COORDENADA_X, double COORDENADA_Y, int NC_TRATADA) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.PONTO = PONTO;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_INDICADOR = ID_INDICADOR;
        this.VALOR_INDICADOR = VALOR_INDICADOR;
        this.COORDENADA_X = COORDENADA_X;
        this.COORDENADA_Y = COORDENADA_Y;
        this.NC_TRATADA = NC_TRATADA;
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    @NonNull
    public String getDATA() {
        return DATA;
    }

    public void setDATA(@NonNull String DATA) {
        this.DATA = DATA;
    }

    public int getPONTO() {
        return PONTO;
    }

    public void setPONTO(int PONTO) {
        this.PONTO = PONTO;
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

    public int getNC_TRATADA() {
        return NC_TRATADA;
    }

    public void setNC_TRATADA(int NC_TRATADA) {
        this.NC_TRATADA = NC_TRATADA;
    }
}
