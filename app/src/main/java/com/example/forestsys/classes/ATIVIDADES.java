package com.example.forestsys.classes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)})

public class ATIVIDADES {
    @PrimaryKey
    private int ID_ATIVIDADE;

    @ColumnInfo (name = "DESCRICAO")
    private String DESCRICAO;

    private int ATIVO;

    public ATIVIDADES(int ID_ATIVIDADE, String DESCRICAO, int ATIVO) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_ATIVIDADE() {
        return ID_ATIVIDADE;
    }

    public void setID_ATIVIDADE(int ID_ATIVIDADE) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
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
