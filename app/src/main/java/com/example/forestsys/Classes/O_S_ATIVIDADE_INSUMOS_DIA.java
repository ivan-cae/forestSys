package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

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
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "ID_INSUMO", "DATA"})

public class O_S_ATIVIDADE_INSUMOS_DIA implements Serializable {

    @ColumnInfo(name = "ID_PROGRAMACAO_ATIVIDADE")
    @NonNull
    private Integer ID_PROGRAMACAO_ATIVIDADE;

    @NonNull
    private String DATA;

    @ColumnInfo(name = "ID_INSUMO")
    @NonNull
    private Integer ID_INSUMO;

    private double QTD_APLICADO;
    private String ACAO_INATIVO;
    private String REGISTRO_DESCARREGADO;
    private String OBSERVACAO;
    private Integer ID;
    private boolean EXPORT_PROXIMA_SINC = false;

    public O_S_ATIVIDADE_INSUMOS_DIA(Integer ID, Integer ID_PROGRAMACAO_ATIVIDADE, @NonNull String DATA, Integer ID_INSUMO,
                                     double QTD_APLICADO, String ACAO_INATIVO, String REGISTRO_DESCARREGADO, String OBSERVACAO) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
        this.DATA = DATA;
        this.ID_INSUMO = ID_INSUMO;
        this.QTD_APLICADO = QTD_APLICADO;
        this.ACAO_INATIVO = ACAO_INATIVO;
        this.REGISTRO_DESCARREGADO = REGISTRO_DESCARREGADO;
        this.OBSERVACAO = OBSERVACAO;
        this.ID = ID;
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

    public Integer getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(Integer ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public double getQTD_APLICADO() {
        return QTD_APLICADO;
    }

    public String getACAO_INATIVO() {
        return ACAO_INATIVO;
    }

    public void setACAO_INATIVO(String ACAO_INATIVO) {
        this.ACAO_INATIVO = ACAO_INATIVO;
    }

    public String getREGISTRO_DESCARREGADO() {
        return REGISTRO_DESCARREGADO;
    }

    public void setREGISTRO_DESCARREGADO(String REGISTRO_DESCARREGADO) {
        this.REGISTRO_DESCARREGADO = REGISTRO_DESCARREGADO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public void setQTD_APLICADO(double QTD_APLICADO) {
        this.QTD_APLICADO = QTD_APLICADO;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public boolean isEXPORT_PROXIMA_SINC() {
        return EXPORT_PROXIMA_SINC;
    }

    public void setEXPORT_PROXIMA_SINC(boolean EXPORT_PROXIMA_SINC) {
        this.EXPORT_PROXIMA_SINC = EXPORT_PROXIMA_SINC;
    }
}

