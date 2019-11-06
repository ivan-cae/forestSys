package com.example.forestsys;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity
public class Users implements Serializable {
            @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;
    @NonNull
    private String matricula;
    @NonNull
    private String cargo;

    public Enumeraveis.nivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Enumeraveis.nivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @TypeConverters(Enumeraveis.nivelAcesso.class)
    private Enumeraveis.nivelAcesso nivelAcesso;

    @NonNull
    private String login;
    @NonNull
    private String senha;

    Users(String nome, String matricula, String cargo, Enumeraveis.nivelAcesso nivelAcesso, String login, String senha){
        this.nome = nome;
        this.matricula = matricula;
        this.cargo = cargo;
        this.nivelAcesso = nivelAcesso;
        this.login = login;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}