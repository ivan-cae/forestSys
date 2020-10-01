package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = GEO_REGIONAIS.class,
                        parentColumns = "ID_REGIONAL",
                        childColumns = "ID_REGIONAL",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},

        indices = {@Index(value = "DESCRICAO", unique = true),
        @Index(value = "ID_SETOR", unique = true)},

        primaryKeys = {"ID_REGIONAL", "ID_SETOR"})

public class GEO_SETORES implements Serializable {

    @ColumnInfo(name = "ID_REGIONAL")
    private int ID_REGIONAL;

    @ColumnInfo(name = "ID_SETOR")
    private int ID_SETOR;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public GEO_SETORES(int ID_REGIONAL, int ID_SETOR, String DESCRICAO, int ATIVO) {
        this.ID_REGIONAL = ID_REGIONAL;
        this.ID_SETOR = ID_SETOR;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_REGIONAL() {
        return ID_REGIONAL;
    }

    public void setID_REGIONAL(int ID_REGIONAL) {
        this.ID_REGIONAL = ID_REGIONAL;
    }

    public int getID_SETOR() {
        return ID_SETOR;
    }

    public void setID_SETOR(int ID_SETOR) {
        this.ID_SETOR = ID_SETOR;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }
}
