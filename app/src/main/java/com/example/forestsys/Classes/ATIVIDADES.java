package com.example.forestsys.Classes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true),
        @Index(value = "ID_ATIVIDADE", unique =true)})

public class ATIVIDADES implements Serializable {
    @PrimaryKey
    @ColumnInfo (name = "ID_ATIVIDADE")
    private Integer ID_ATIVIDADE;

    @ColumnInfo (name = "DESCRICAO")
    private String DESCRICAO;

    private Integer ATIVO;

    public ATIVIDADES(Integer ID_ATIVIDADE, String DESCRICAO, Integer ATIVO) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public Integer getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(Integer ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }
}
