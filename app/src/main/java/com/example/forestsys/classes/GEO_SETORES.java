package com.example.forestsys.classes;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = GEO_REGIONAIS.class,
                        parentColumns = "ID_REGIONAL",
                        childColumns = "ID_REGIONAL",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},

        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GEO_SETORES implements Serializable {
    @PrimaryKey
    private int ID_SETOR;
    private int ID_REGIONAL;
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public GEO_SETORES(int ID_SETOR, int ID_REGIONAL, int ATIVO, String DESCRICAO) {
        this.ID_SETOR = ID_SETOR;
        this.ID_REGIONAL = ID_REGIONAL;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
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

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }
}
