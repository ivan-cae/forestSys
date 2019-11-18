package com.example.forestsys;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ClassePrestadores implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String empresa;

    @Nullable
    private boolean ativo;

    public ClassePrestadores(int id, @Nullable String empresa, boolean ativo) {
        this.id = id;
        this.empresa = empresa;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(@Nullable String empresa) {
        this.empresa = empresa;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
