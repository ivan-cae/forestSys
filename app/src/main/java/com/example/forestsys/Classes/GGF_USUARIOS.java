package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        /*foreignKeys = {
                @ForeignKey(entity = GGF_DEPARTAMENTOS.class,
                        parentColumns = "ID_DEPARTAMENTO",
                        childColumns = "ID_DEPARTAMENTO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = GGF_FUNCOES.class,
                        parentColumns = "ID_FUNCAO",
                        childColumns = "ID_FUNCAO",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
*/
        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_USUARIOS implements Serializable {
    @PrimaryKey
    private int ID_USUARIO;

    @ColumnInfo(name = "ID_DEPARTAMENTO")
    private int ID_DEPARTAMENTO;

    @ColumnInfo(name = "ID_FUNCAO")
    private int ID_FUNCAO;

    private String SENHA;

    private int ATIVO;

    private String EMAIL;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private int NIVEL_ACESSO;

    public GGF_USUARIOS(int ID_USUARIO, int ID_DEPARTAMENTO, int ID_FUNCAO, String SENHA, int ATIVO, String EMAIL, String DESCRICAO, int NIVEL_ACESSO) {
        this.ID_USUARIO = ID_USUARIO;
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
        this.ID_FUNCAO = ID_FUNCAO;
        this.SENHA = SENHA;
        this.ATIVO = ATIVO;
        this.EMAIL = EMAIL;
        this.DESCRICAO = DESCRICAO;
        this.NIVEL_ACESSO = NIVEL_ACESSO;
    }


    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getID_DEPARTAMENTO() {
        return ID_DEPARTAMENTO;
    }

    public void setID_DEPARTAMENTO(int ID_DEPARTAMENTO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
    }

    public int getID_FUNCAO() {
        return ID_FUNCAO;
    }

    public void setID_FUNCAO(int ID_FUNCAO) {
        this.ID_FUNCAO = ID_FUNCAO;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public int getATIVO() {
        return ATIVO;
    }

    public void setATIVO(int ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public int getNIVEL_ACESSO() {
        return NIVEL_ACESSO;
    }

    public void setNIVEL_ACESSO(int NIVEL_ACESSO) {
        this.NIVEL_ACESSO = NIVEL_ACESSO;
    }

    @Override
    public String toString() {
        return DESCRICAO;
    }
}