package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelEncarregados extends AndroidViewModel
{
    private RepositorioEncarregados repositorioEncarregados;
    private LiveData<List<ClasseEncarregados>> listaEncarregados;
    private LiveData<ClasseEncarregados> Consulta;

    public ViewModelEncarregados(@NonNull Application application) {
        super(application);
        repositorioEncarregados = new RepositorioEncarregados(application);
        listaEncarregados = repositorioEncarregados.getTodosEncarregados();
    }

    public void insert(ClasseEncarregados ClasseEncarregados) {
        repositorioEncarregados.insert(ClasseEncarregados);
    }

    public void update(ClasseEncarregados ClasseEncarregados) {
        repositorioEncarregados.update(ClasseEncarregados);
    }

    public void delete(ClasseEncarregados ClasseEncarregados) {
        repositorioEncarregados.delete(ClasseEncarregados);
    }
}
