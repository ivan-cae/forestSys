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
    private int ATIVO;

    @ColumnInfo (name = "DESCRICAO")
    private String DESCRICAO;

    public ATIVIDADES(int ID_ATIVIDADE, int ATIVO, String DESCRICAO) {
        this.ID_ATIVIDADE = ID_ATIVIDADE;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
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

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }
}
