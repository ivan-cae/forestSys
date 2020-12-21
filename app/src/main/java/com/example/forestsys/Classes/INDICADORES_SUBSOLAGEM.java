package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

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
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "ID_ATIVIDADE", "ID_INDICADOR"})
public class INDICADORES_SUBSOLAGEM implements Serializable {
    @ColumnInfo(name="ID_PROGRAMACAO_ATIVIDADE")
    @NonNull
    private Integer ID_PROGRAMACAO_ATIVIDADE;

    private String DATA;

    @NonNull
    @ColumnInfo(name="ID_ATIVIDADE")
    private Integer ID_ATIVIDADE;

    @NonNull
    @ColumnInfo(name="ID_INDICADOR")
    private Integer ID_INDICADOR;

    private double VALOR_INDICADOR;

    public INDICADORES_SUBSOLAGEM(Integer ID_PROGRAMACAO_ATIVIDADE, Integer ID_ATIVIDADE, Integer ID_INDICADOR, String DATA, double VALOR_INDICADOR) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ID_INDICADOR = ID_INDICADOR;
        this.DATA = DATA;
        this.VALOR_INDICADOR = VALOR_INDICADOR;
    }

    public Integer getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(Integer ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
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

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public double getVALOR_INDICADOR() {
        return VALOR_INDICADOR;
    }

    public void setVALOR_INDICADOR(double VALOR_INDICADOR) {
        this.VALOR_INDICADOR = VALOR_INDICADOR;
    }
}
