package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(entity = INSUMOS.class,
                parentColumns = "ID_INSUMO",
                childColumns =  "ID_INSUMO",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = O_S_ATIVIDADES.class,
                        parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                        childColumns =  "ID_PROGRAMACAO_ATIVIDADE",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "ID_INSUMO"})
public class O_S_ATIVIDADE_INSUMOS implements Serializable {
    @ColumnInfo(name="ID_INSUMO")
    private int ID_INSUMO;

    @ColumnInfo(name="ID_PROGRAMACAO_ATIVIDADE")
    private int ID_PROGRAMACAO_ATIVIDADE;

    private int RECOMENDACAO;
    private double QTD_HA_RECOMENDADO;
    private double QTD_HA_APLICADO;

    public O_S_ATIVIDADE_INSUMOS(int ID_INSUMO, int ID_PROGRAMACAO_ATIVIDADE, int RECOMENDACAO, double QTD_HA_RECOMENDADO, double QTD_HA_APLICADO) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.RECOMENDACAO = RECOMENDACAO;
        this.QTD_HA_RECOMENDADO = QTD_HA_RECOMENDADO;
        this.QTD_HA_APLICADO = QTD_HA_APLICADO;
    }

    public O_S_ATIVIDADE_INSUMOS() {

    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    public int getRECOMENDACAO() {
        return RECOMENDACAO;
    }

    public void setRECOMENDACAO(int RECOMENDACAO) {
        this.RECOMENDACAO = RECOMENDACAO;
    }

    public double getQTD_HA_RECOMENDADO() {
        return QTD_HA_RECOMENDADO;
    }

    public void setQTD_HA_RECOMENDADO(double QTD_HA_RECOMENDADO) {
        this.QTD_HA_RECOMENDADO = QTD_HA_RECOMENDADO;
    }

    public double getQTD_HA_APLICADO() {
        return QTD_HA_APLICADO;
    }

    public void setQTD_HA_APLICADO(double QTD_HA_APLICADO) {
        this.QTD_HA_APLICADO = QTD_HA_APLICADO;
    }
}
