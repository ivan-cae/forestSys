package com.example.forestsys.classes;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = "DESCRICAO", unique = true)}
)
public class IMPLEMENTOS {
    @PrimaryKey
    private int ID_IMPLEMENTO;
    private int ATIVO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    public IMPLEMENTOS(int ID_IMPLEMENTO, int ATIVO, String DESCRICAO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
        this.ATIVO = ATIVO;
        this.DESCRICAO = DESCRICAO;
    }

    public int getID_IMPLEMENTO() {
        return ID_IMPLEMENTO;
    }

    public void setID_IMPLEMENTO(int ID_IMPLEMENTO) {
        this.ID_IMPLEMENTO = ID_IMPLEMENTO;
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
