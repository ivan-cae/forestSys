package com.example.forestsys.classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.sql.Date;

@Entity
public class LOG {
    @PrimaryKey
    private int id;

    private int id_os;

    @TypeConverters({TimestampConverter.class})
    private Date data;
    private String acao;

    private String atividade;
    private String usuario;

    @TypeConverters({TimestampConverter.class})
    private Date hora_acao;

    private String erro;

    public LOG(int id, int id_os, Date data, String acao, String atividade, String usuario,
               Date hora_acao, String erro) {
        this.id = id;
        this.id_os = id_os;
        this.data = data;
        this.acao = acao;
        this.atividade = atividade;
        this.usuario = usuario;
        this.hora_acao = hora_acao;
        this.erro = erro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_os() {
        return id_os;
    }

    public void setId_os(int id_os) {
        this.id_os = id_os;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getHora_acao() {
        return hora_acao;
    }

    public void setHora_acao(Date hora_acao) {
        this.hora_acao = hora_acao;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
