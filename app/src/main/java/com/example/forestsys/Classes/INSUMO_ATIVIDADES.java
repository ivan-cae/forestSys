package com.example.forestsys.Classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(entity = ATIVIDADES.class,
                parentColumns = "ID_ATIVIDADE",
                childColumns = "ID_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = INSUMOS.class,
                        parentColumns = "ID_INSUMO",
                        childColumns = "ID_INSUMO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_INSUMO", "ID_ATIVIDADE"})

public class INSUMO_ATIVIDADES implements Serializable {
    @ColumnInfo(name = "ID_INSUMO")
    @NonNull
    private Integer ID_INSUMO;

    @ColumnInfo(name = "ID_ATIVIDADE")
    @NonNull
    private Integer ID_ATIVIDADE;

    private Integer ATIVO;

    public INSUMO_ATIVIDADES(Integer ID_INSUMO, Integer ID_ATIVIDADE, Integer ATIVO) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ATIVO = ATIVO;
    }

    public Integer getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(Integer ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public Integer getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(Integer ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }
}
