package com.example.forestsys.classes;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ClasseOsInsumos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String insumo;

    @Nullable
    private String un_medida;

    @Nullable
    private boolean recomendada;

    @Nullable
    private float qtd_hercare;

    @Nullable
    private float qtd_aplicada;

    public ClasseOsInsumos(@Nullable String insumo, @Nullable String un_medida,
                          boolean recomendada, float qtd_hercare, float qtd_aplicada) {
        this.insumo = insumo;
        this.un_medida = un_medida;
        this.recomendada = recomendada;
        this.qtd_hercare = qtd_hercare;
        this.qtd_aplicada = qtd_aplicada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(@Nullable String insumo) {
        this.insumo = insumo;
    }

    @Nullable
    public String getUn_medida() {
        return un_medida;
    }

    public void setUn_medida(@Nullable String un_medida) {
        this.un_medida = un_medida;
    }

    public boolean isRecomendada() {
        return recomendada;
    }

    public void setRecomendada(boolean recomendada) {
        this.recomendada = recomendada;
    }

    public float getQtd_hercare() {
        return qtd_hercare;
    }

    public void setQtd_hercare(float qtd_hercare) {
        this.qtd_hercare = qtd_hercare;
    }

    public float getQtd_aplicada() {
        return qtd_aplicada;
    }

    public void setQtd_aplicada(float qtd_aplicada) {
        this.qtd_aplicada = qtd_aplicada;
    }
}