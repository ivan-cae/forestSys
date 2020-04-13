package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(
        foreignKeys = {@ForeignKey(entity = MAQUINA_IMPLEMENTO.class,
                parentColumns = "ID_MAQUINA_IMPLEMENTO",
                childColumns = "ID_MAQUINA_IMPLEMENTO",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = OPERADORES.class,
                        parentColumns = "ID_OPERADORES",
                        childColumns = "ID_OPERADOR",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = O_S_ATIVIDADES.class,
                        parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        childColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA", "TURNO", "ID_MAQUINA_IMPLEMENTO"})

public class CALIBRAGEM_SUBSOLAGEM {

    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    @NonNull
    private int ID_PROGRAMACAO_ATIVIDADE;

    @ColumnInfo(name = "DATA")
    @NonNull
    private String DATA;

    @ColumnInfo(name = "TURNO")
    @NonNull
    private String TURNO;

    @ColumnInfo(name = "ID_MAQUINA_IMPLEMENTO")
    @NonNull
    private int ID_MAQUINA_IMPLEMENTO;

    @ColumnInfo(name = "ID_OPERADOR")
    @NonNull
    private int ID_OPERADOR;

    private Double P1_MEDIA;
    private Double P1_DESVIO;
    private Double P2_MEDIA;
    private Double P2_DESVIO;

    public CALIBRAGEM_SUBSOLAGEM(int ID_PROGRAMACAO_ATIVIDADE, @NonNull String DATA, @NonNull String TURNO, int ID_MAQUINA_IMPLEMENTO, int ID_OPERADOR, Double P1_MEDIA, Double P1_DESVIO, Double P2_MEDIA, Double P2_DESVIO) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.TURNO = TURNO;
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
        this.ID_OPERADOR = ID_OPERADOR;
        this.P1_MEDIA = P1_MEDIA;
        this.P1_DESVIO = P1_DESVIO;
        this.P2_MEDIA = P2_MEDIA;
        this.P2_DESVIO = P2_DESVIO;
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

    @NonNull
    public String getTURNO() {
        return TURNO;
    }

    public void setTURNO(@NonNull String TURNO) {
        this.TURNO = TURNO;
    }

    public int getID_MAQUINA_IMPLEMENTO() {
        return ID_MAQUINA_IMPLEMENTO;
    }

    public void setID_MAQUINA_IMPLEMENTO(int ID_MAQUINA_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
    }

    public int getID_OPERADOR() {
        return ID_OPERADOR;
    }

    public void setID_OPERADOR(int ID_OPERADOR) {
        this.ID_OPERADOR = ID_OPERADOR;
    }

    public Double getP1_MEDIA() {
        return P1_MEDIA;
    }

    public void setP1_MEDIA(Double p1_MEDIA) {
        P1_MEDIA = p1_MEDIA;
    }

    public Double getP1_DESVIO() {
        return P1_DESVIO;
    }

    public void setP1_DESVIO(Double p1_DESVIO) {
        P1_DESVIO = p1_DESVIO;
    }

    public Double getP2_MEDIA() {
        return P2_MEDIA;
    }

    public void setP2_MEDIA(Double p2_MEDIA) {
        P2_MEDIA = p2_MEDIA;
    }

    public Double getP2_DESVIO() {
        return P2_DESVIO;
    }

    public void setP2_DESVIO(Double p2_DESVIO) {
        P2_DESVIO = p2_DESVIO;
    }
}