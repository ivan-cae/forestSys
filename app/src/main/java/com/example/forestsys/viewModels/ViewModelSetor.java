package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.repositorios.RepositorioSetor;

import java.util.List;

public class ViewModelSetor extends AndroidViewModel {
    private RepositorioSetor repositorioSetor;
    private List<GEO_SETORES> listaSetor;
    private GEO_SETORES Consulta;

    public ViewModelSetor(@NonNull Application application) {
        super(application);
        repositorioSetor = new RepositorioSetor(application);
        listaSetor = repositorioSetor.getTodosSetores();
    }

    //retorna uma instância da GEO_SETORES
//parâmetro de entrada: id inteiro para busca na tabela GEO_SETORES
    public GEO_SETORES consulta (int id){return repositorioSetor.getSetor(id);}

    //inclui uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void insert(GEO_SETORES GEOSETORES) {
        repositorioSetor.insert(GEOSETORES);
    }

    //atualiza uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void update(GEO_SETORES GEOSETORES) {
        repositorioSetor.update(GEOSETORES);
    }

    //apaga uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void delete(GEO_SETORES GEOSETORES) {
        repositorioSetor.delete(GEOSETORES);
    }
}