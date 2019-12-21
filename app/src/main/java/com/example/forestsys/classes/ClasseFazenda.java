package com.example.forestsys.classes;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ClasseFazenda implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String fazenda;


    public ClasseFazenda(@Nullable String fazenda) {
        this.fazenda = fazenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getFazenda() {
        return fazenda;
    }

    public void setFazenda(@Nullable String fazenda) {
        this.fazenda = fazenda;
    }
}
