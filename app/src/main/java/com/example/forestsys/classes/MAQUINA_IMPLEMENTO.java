package com.example.forestsys.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

public class MAQUINA_IMPLEMENTO {

    @PrimaryKey
    private int ID_MAQUINA_IMPLEMENTO;

    @ColumnInfo (name = "ID_MAQUINA")
    private int ID_MAQUINA;

    @ColumnInfo (name = "ID_IMPLEMENTO")
	private int ID_IMPLEMENTO;


    public MAQUINA_IMPLEMENTO(int ID_MAQUINA_IMPLEMENTO, int ID_MAQUINA, int ID_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
        this.ID_MAQUINA = ID_MAQUINA;
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }

    @Ignore
    public MAQUINA_IMPLEMENTO(int ID_MAQUINA, int ID_IMPLEMENTO) {
        this.ID_MAQUINA = ID_MAQUINA;
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }

    public int getID_MAQUINA_IMPLEMENTO() {
        return ID_MAQUINA_IMPLEMENTO;
    }

    public void setID_MAQUINA_IMPLEMENTO(int ID_MAQUINA_IMPLEMENTO) {
        this.ID_MAQUINA_IMPLEMENTO = ID_MAQUINA_IMPLEMENTO;
    }

    public int getID_MAQUINA() {
        return ID_MAQUINA;
    }

    public void setID_MAQUINA(int ID_MAQUINA) {
        this.ID_MAQUINA = ID_MAQUINA;
    }

    public int getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(int ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
    }
}
