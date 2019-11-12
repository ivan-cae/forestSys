package com.example.forestsys;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ClasseEncarregados implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String nome;

    @Nullable
    private boolean ativo;


    public ClasseEncarregados(@Nullable String nome, boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getNome() {
        return nome;
    }

    public void setNome(@Nullable String nome) {
        this.nome = nome;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}