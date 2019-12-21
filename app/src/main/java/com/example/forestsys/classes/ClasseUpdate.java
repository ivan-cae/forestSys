package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.TimestampConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ClasseUpdate implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @Nullable
    private int id_os;

    @Nullable
    @TypeConverters({TimestampConverter.class})
    private Date data;

    @Nullable
    private String acao;


    public ClasseUpdate(int id_os, @Nullable Date data, @Nullable String acao) {
        this.id_os = id_os;
        this.data = data;
        this.acao = acao;
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

    @Nullable
    public Date getData() {
        return data;
    }

    public void setData(@Nullable Date data) {
        this.data = data;
    }

    @Nullable
    public String getAcao() {
        return acao;
    }

    public void setAcao(@Nullable String acao) {
        this.acao = acao;
    }
}