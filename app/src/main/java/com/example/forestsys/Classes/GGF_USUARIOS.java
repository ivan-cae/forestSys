package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        foreignKeys = {
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

        indices = {@Index(value = "DESCRICAO",unique = true)})

public class GGF_USUARIOS implements Serializable {
    @PrimaryKey
    private Integer ID_USUARIO;

    @ColumnInfo(name = "DESCRICAO")
    private String DESCRICAO;

    private String SENHA;

    @ColumnInfo(name = "ID_DEPARTAMENTO")
    private Integer ID_DEPARTAMENTO;

    @ColumnInfo(name = "ID_FUNCAO")
    private Integer ID_FUNCAO;

    private String EMAIL;

    private Integer NIVEL_ACESSO;

    private Integer ATIVO;

    public GGF_USUARIOS(Integer ID_USUARIO, Integer ID_DEPARTAMENTO, Integer ID_FUNCAO, String SENHA, Integer ATIVO, String EMAIL, String DESCRICAO, Integer NIVEL_ACESSO) {
        this.ID_USUARIO = ID_USUARIO;
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
        this.ID_FUNCAO = ID_FUNCAO;
        this.SENHA = SENHA;
        this.ATIVO = ATIVO;
        this.EMAIL = EMAIL;
        this.DESCRICAO = DESCRICAO;
        this.NIVEL_ACESSO = NIVEL_ACESSO;
    }


    public Integer getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(Integer ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public Integer getID_DEPARTAMENTO() {
        return ID_DEPARTAMENTO;
    }

    public void setID_DEPARTAMENTO(Integer ID_DEPARTAMENTO) {
        this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
    }

    public Integer getID_FUNCAO() {
        return ID_FUNCAO;
    }

    public void setID_FUNCAO(Integer ID_FUNCAO) {
        this.ID_FUNCAO = ID_FUNCAO;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
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

    public Integer getNIVEL_ACESSO() {
        return NIVEL_ACESSO;
    }

    public void setNIVEL_ACESSO(Integer NIVEL_ACESSO) {
        this.NIVEL_ACESSO = NIVEL_ACESSO;
    }

    @Override
    public String toString() {
        return DESCRICAO;
    }
}