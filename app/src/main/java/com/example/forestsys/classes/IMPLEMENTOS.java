package com.example.forestsys.classes;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO_IMPLEMENTO", unique = true)}
)
public class IMPLEMENTOS {

    @PrimaryKey
    private int ID_IMPLEMENTO;

    @ColumnInfo(name = "DESCRICAO_IMPLEMENTO")
    private String DESCRICAO;

    private int ATIVO;

    public IMPLEMENTOS(int ID_IMPLEMENTO, String DESCRICAO, int ATIVO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
        this.DESCRICAO = DESCRICAO;
        this.ATIVO = ATIVO;
    }

    public int getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(int ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
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

    @Override
    public String toString() {
        return DESCRICAO;
    }
}
