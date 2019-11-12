package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

public class ViewModelRegional extends AndroidViewModel {
    private RepositorioRegional repositorioRegional;
    private LiveData<List<ClasseRegional>> listaRegional;
    private LiveData<ClasseRegional> Consulta;

    public ViewModelRegional(@NonNull Application application) {
        super(application);
        repositorioRegional = new RepositorioRegional(application);
        listaRegional = repositorioRegional.getTodosRegionais();
    }

    public void insert(ClasseRegional classeRegional) {
        repositorioRegional.insert(classeRegional);
    }

    public void update(ClasseRegional classeRegional) {
        repositorioRegional.update(classeRegional);
    }

    public void delete(ClasseRegional classeRegional) {
        repositorioRegional.delete(classeRegional);
    }
}