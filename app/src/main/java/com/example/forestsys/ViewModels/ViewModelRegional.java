package com.example.forestsys.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.Classes.GEO_REGIONAIS;
import com.example.forestsys.Repositorios.RepositorioRegional;

import java.util.List;

public class ViewModelRegional extends AndroidViewModel {
    private RepositorioRegional repositorioRegional;
    private LiveData<List<GEO_REGIONAIS>> listaRegional;
    private LiveData<GEO_REGIONAIS> Consulta;

    public ViewModelRegional(@NonNull Application application) {
        super(application);
        repositorioRegional = new RepositorioRegional(application);
        listaRegional = repositorioRegional.getTodosRegionais();
    }


    //inclui uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void insert(GEO_REGIONAIS GEOREGIONAIS) {
        repositorioRegional.insert(GEOREGIONAIS);
    }

    //atualiza uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void update(GEO_REGIONAIS GEOREGIONAIS) {
        repositorioRegional.update(GEOREGIONAIS);
    }

    //apaga uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void delete(GEO_REGIONAIS GEOREGIONAIS) {
        repositorioRegional.delete(GEOREGIONAIS);
    }
}