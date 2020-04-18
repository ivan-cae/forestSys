package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

@Entity(
        foreignKeys = {@ForeignKey(entity = INSUMOS.class,
                parentColumns = "ID_INSUMO",
                childColumns = "ID_INSUMO",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = O_S_ATIVIDADES.class,
                        parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        childColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA", "ID_INSUMO"})

public class O_S_ATIVIDADE_INSUMOS_DIA {

    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    @NonNull
    private int ID_PROGRAMACAO_ATIVIDADE;

    @NonNull
    private String DATA;

    @ColumnInfo(name = "ID_INSUMO")
    @NonNull
    private int ID_INSUMO;

    private double QTD_APLICADO;

    public O_S_ATIVIDADE_INSUMOS_DIA(int ID_PROGRAMACAO_ATIVIDADE, String DATA, int ID_INSUMO, double QTD_APLICADO) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.ID_INSUMO = ID_INSUMO;
        this.QTD_APLICADO = QTD_APLICADO;
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public double getQTD_APLICADO() {
        return QTD_APLICADO;
    }

    public void setQTD_APLICADO(double QTD_APLICADO) {
        this.QTD_APLICADO = QTD_APLICADO;
    }
}

