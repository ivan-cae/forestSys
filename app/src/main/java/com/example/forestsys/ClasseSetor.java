package com.example.forestsys;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class ClasseSetor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String setor;


    public ClasseSetor(@Nullable String setor) {
        this.setor = setor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getSetor() {
        return setor;
    }

    public void setSetor(@Nullable String setor) {
        this.setor = setor;
    }
}
