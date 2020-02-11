package com.example.forestsys.classes;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(indices = {@Index(value = "DESCRICAO",unique = true)})

public class GEO_REGIONAIS implements Serializable {
    @PrimaryKey
    private int ID_REGIONAL;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int SETOR_TODOS;

    private int ATIVO;

    public GEO_REGIONAIS(int ID_REGIONAL, String DESCRICAO, int SETOR_TODOS, int ATIVO) {
        this.ID_REGIONAL = ID_REGIONAL;
        this.DESCRICAO = DESCRICAO;
        this.SETOR_TODOS = SETOR_TODOS;
        this.ATIVO = ATIVO;
    }

    public int getID_REGIONAL() {
        return ID_REGIONAL;
    }

    public void setID_REGIONAL(int ID_REGIONAL) {
        this.ID_REGIONAL = ID_REGIONAL;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public int getSETOR_TODOS() {
        return SETOR_TODOS;
    }

    public void setSETOR_TODOS(int SETOR_TODOS) {
        this.SETOR_TODOS = SETOR_TODOS;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }
}
