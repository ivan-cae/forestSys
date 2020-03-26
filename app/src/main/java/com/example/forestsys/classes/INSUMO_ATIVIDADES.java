package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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

public class INSUMO_ATIVIDADES {
    @ColumnInfo(name = "ID_INSUMO")
    private int ID_INSUMO;

    @ColumnInfo(name = "ID_ATIVIDADE")
    private int ID_ATIVIDADE;

    private int ATIVO;

    public INSUMO_ATIVIDADES(int ID_INSUMO, int ID_ATIVIDADE, int ATIVO) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ATIVO = ATIVO;
    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public int getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(int ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }
}
