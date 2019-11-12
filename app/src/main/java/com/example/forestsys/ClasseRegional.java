package com.example.forestsys;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class ClasseRegional implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Nullable
    private int id;

    @Nullable
    private String regional;


    public ClasseRegional(@Nullable String regional) {
        this.regional = regional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getRegional() {
        return regional;
    }

    public void setRegional(@Nullable String regional) {
        this.regional = regional;
    }
}
