package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelUpdate extends AndroidViewModel {
    private RepositorioUpdate repositorioUpdate;
    private LiveData<List<ClasseUpdate>> listaUpdate;
    private LiveData<ClasseUpdate> Consulta;

    public ViewModelUpdate(@NonNull Application application) {
        super(application);
        repositorioUpdate = new RepositorioUpdate(application);
        listaUpdate = repositorioUpdate.getTodosUpdates();
    }

    public void insert(ClasseUpdate classeUpdate) {
        repositorioUpdate.insert(classeUpdate);
    }

    public void update(ClasseUpdate classeUpdate) {
        repositorioUpdate.update(classeUpdate);
    }

    public void delete(ClasseUpdate classeUpdate) {
        repositorioUpdate.delete(classeUpdate);
    }
}