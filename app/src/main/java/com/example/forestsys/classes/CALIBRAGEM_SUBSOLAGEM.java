package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

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
                @ForeignKey(entity = O_S_ATIVIDADE_INSUMOS_DIA.class,
                        parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        childColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA", "TURNO", "ID_MAQUINA_IMPLEMENTO"})

public class CALIBRAGEM_SUBSOLAGEM {

    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    private int ID_PROGRAMACAO_ATIVIDADE;

    @TypeConverters({TimestampConverter.class})
    @ColumnInfo(name = "DATA")
    @NonNull
    private Date DATA;


    @ColumnInfo(name = "TURNO")
    @NonNull
    private String TURNO;

    @ColumnInfo(name = "ID_MAQUINA_IMPLEMENTO")
    @NonNull
    private int ID_MAQUINA_IMPLEMENTO;

    @ColumnInfo(name = "ID_OPERADOR")
    private int ID_OPERADOR;

    private double P1_MEDIA;
    private double P1_DESVIO;
    private double P2_MEDIA;
    private double P2_DESVIO;

    public CALIBRAGEM_SUBSOLAGEM(){
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    @NonNull
    public Date getDATA() {
        return DATA;
    }

    public void setDATA(@NonNull Date DATA) {
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

    public double getP1_MEDIA() {
        return P1_MEDIA;
    }

    public void setP1_MEDIA(double p1_MEDIA) {
        P1_MEDIA = p1_MEDIA;
    }

    public double getP1_DESVIO() {
        return P1_DESVIO;
    }

    public void setP1_DESVIO(double p1_DESVIO) {
        P1_DESVIO = p1_DESVIO;
    }

    public double getP2_MEDIA() {
        return P2_MEDIA;
    }

    public void setP2_MEDIA(double p2_MEDIA) {
        P2_MEDIA = p2_MEDIA;
    }

    public double getP2_DESVIO() {
        return P2_DESVIO;
    }

    public void setP2_DESVIO(double p2_DESVIO) {
        P2_DESVIO = p2_DESVIO;
    }
}
