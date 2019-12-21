package com.example.forestsys.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.forestsys.Enumeraveis;
import com.example.forestsys.TimestampConverter;

import java.io.Serializable;
import java.util.Date;


@Entity
        /*(foreignKeys = {

                @ForeignKey(entity = GGF_USUARIOS.class,
                        parentColumns = "id",
                        childColumns = "id_user",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = GEO_SETORES.class,
                        parentColumns = "id",
                        childColumns = "id_setor",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),

                @ForeignKey(entity = GEO_REGIONAIS.class,
                        parentColumns = "id",
                        childColumns = "id_regional",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
        })
        /*
                @ForeignKey(entity = ClasseEncarregados.class,
                        parentColumns = "id",
                        childColumns = "id_encarregado",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),



                @ForeignKey(entity = ClasseFazenda.class,
                        parentColumns = "id",
                        childColumns = "id_fazenda",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION),
        })
*/
public class ClasseOs implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    //Foreign Keys
    @Nullable
    public int id_user;

    @Nullable
    private int id_ggfos;

    @Nullable
    private int id_cod;

    @Nullable
    private int id_setor;

    @Nullable
    private int id_regional;

    @Nullable
    private int id_talhao;

    @Nullable
    private int id_ciclo;

    @Nullable
    private int id_atividade;

    @Nullable
    private int id_encarregado;

    @Nullable
    private int id_fazenda;


    @Nullable
    @TypeConverters(Enumeraveis.manejo.class)
    private Enumeraveis.manejo manejo;

    @Nullable
    private String projeto_manejo;

    @Nullable
    @TypeConverters({TimestampConverter.class})
    private Date programacao;

    @Nullable
    private float area;

    @Nullable
    private boolean experimento;

    @Nullable
    private boolean madeira_talhao;

    @Nullable
    private String prioridade;

    @Nullable
    @TypeConverters(Enumeraveis.status.class)
    private Enumeraveis.status status;

    @Nullable
    private String observacao;

    @Nullable
    private boolean sincronizado;

    public ClasseOs(int id_user, int id_ggfos, int id_cod, int id_setor, int id_regional, int id_talhao, int id_ciclo, int id_atividade, int id_encarregado, int id_fazenda, @Nullable Enumeraveis.manejo manejo, @Nullable String projeto_manejo, @Nullable Date programacao, float area, boolean experimento, boolean madeira_talhao, @Nullable String prioridade, @Nullable Enumeraveis.status status, @Nullable String observacao, boolean sincronizado) {
        this.id_user = id_user;
        this.id_ggfos = id_ggfos;
        this.id_cod = id_cod;
        this.id_setor = id_setor;
        this.id_regional = id_regional;
        this.id_talhao = id_talhao;
        this.id_ciclo = id_ciclo;
        this.id_atividade = id_atividade;
        this.id_encarregado = id_encarregado;
        this.id_fazenda = id_fazenda;
        this.manejo = manejo;
        this.projeto_manejo = projeto_manejo;
        this.programacao = programacao;
        this.area = area;
        this.experimento = experimento;
        this.madeira_talhao = madeira_talhao;
        this.prioridade = prioridade;
        this.status = status;
        this.observacao = observacao;
        this.sincronizado = sincronizado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_ggfos() {
        return id_ggfos;
    }

    public void setId_ggfos(int id_ggfos) {
        this.id_ggfos = id_ggfos;
    }

    public int getId_cod() {
        return id_cod;
    }

    public void setId_cod(int id_cod) {
        this.id_cod = id_cod;
    }

    public int getId_setor() {
        return id_setor;
    }

    public void setId_setor(int id_setor) {
        this.id_setor = id_setor;
    }

    public int getId_regional() {
        return id_regional;
    }

    public void setId_regional(int id_regional) {
        this.id_regional = id_regional;
    }

    public int getId_talhao() {
        return id_talhao;
    }

    public void setId_talhao(int id_talhao) {
        this.id_talhao = id_talhao;
    }

    public int getId_ciclo() {
        return id_ciclo;
    }

    public void setId_ciclo(int id_ciclo) {
        this.id_ciclo = id_ciclo;
    }

    public int getId_atividade() {
        return id_atividade;
    }

    public void setId_atividade(int id_atividade) {
        this.id_atividade = id_atividade;
    }

    public int getId_encarregado() {
        return id_encarregado;
    }

    public void setId_encarregado(int id_encarregado) {
        this.id_encarregado = id_encarregado;
    }

    public int getId_fazenda() {
        return id_fazenda;
    }

    public void setId_fazenda(int id_fazenda) {
        this.id_fazenda = id_fazenda;
    }

    @Nullable
    public Enumeraveis.manejo getManejo() {
        return manejo;
    }

    public void setManejo(@Nullable Enumeraveis.manejo manejo) {
        this.manejo = manejo;
    }

    @Nullable
    public String getProjeto_manejo() {
        return projeto_manejo;
    }

    public void setProjeto_manejo(@Nullable String projeto_manejo) {
        this.projeto_manejo = projeto_manejo;
    }

    @Nullable
    public Date getProgramacao() {
        return programacao;
    }

    public void setProgramacao(@Nullable Date programacao) {
        this.programacao = programacao;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public boolean isExperimento() {
        return experimento;
    }

    public void setExperimento(boolean experimento) {
        this.experimento = experimento;
    }

    public boolean isMadeira_talhao() {
        return madeira_talhao;
    }

    public void setMadeira_talhao(boolean madeira_talhao) {
        this.madeira_talhao = madeira_talhao;
    }

    @Nullable
    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(@Nullable String prioridade) {
        this.prioridade = prioridade;
    }

    @Nullable
    public Enumeraveis.status getStatus() {
        return status;
    }

    public void setStatus(@Nullable Enumeraveis.status status) {
        this.status = status;
    }

    @Nullable
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(@Nullable String observacao) {
        this.observacao = observacao;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}