package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {@ForeignKey(entity = MAQUINAS.class,
                parentColumns = "ID_MAQUINA",
                childColumns = "ID_MAQUINA",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = IMPLEMENTOS.class,
                        parentColumns = "ID_IMPLEMENTO",
                        childColumns = "ID_IMPLEMENTO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)})

public class MAQUINA_IMPLEMENTO implements Serializable {
    @PrimaryKey
    private Integer ID_MAQUINA_IMPLEMENTO;

    @ColumnInfo (name = "ID_MAQUINA")
    private Integer ID_MAQUINA;

    @ColumnInfo (name = "ID_IMPLEMENTO")
	private Integer ID_IMPLEMENTO;

    public MAQUINA_IMPLEMENTO(Integer ID_MAQUINA_IMPLEMENTO, Integer ID_MAQUINA, Integer ID_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
        this.ID_MAQUINA = ID_MAQUINA;
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }

    public Integer getID_MAQUINA_IMPLEMENTO() {
        return ID_MAQUINA_IMPLEMENTO;
    }

    public void setID_MAQUINA_IMPLEMENTO(Integer ID_MAQUINA_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
    }

    public Integer getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(Integer ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
    }

    public Integer getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(Integer ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }
}
