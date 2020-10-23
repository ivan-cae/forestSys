package com.example.forestsys.Classes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(indices = {@Index(value = "DESCRICAO",unique = true)})

public class GEO_REGIONAIS implements Serializable {
    @PrimaryKey
    private Integer ID_REGIONAL;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private Integer SETOR_TODOS;

    private Integer ATIVO;

    public GEO_REGIONAIS(Integer ID_REGIONAL, String DESCRICAO, Integer SETOR_TODOS, Integer ATIVO) {
        this.ID_REGIONAL = ID_REGIONAL;
        this.DESCRICAO = DESCRICAO;
        this.SETOR_TODOS = SETOR_TODOS;
        this.ATIVO = ATIVO;
    }

    public Integer getID_REGIONAL() {
        return ID_REGIONAL;
    }

    public void setID_REGIONAL(Integer ID_REGIONAL) {
        this.ID_REGIONAL = ID_REGIONAL;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public Integer getSETOR_TODOS() {
        return SETOR_TODOS;
    }

    public void setSETOR_TODOS(Integer SETOR_TODOS) {
        this.SETOR_TODOS = SETOR_TODOS;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }
}
