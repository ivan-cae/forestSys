package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelSetor extends AndroidViewModel {
    private RepositorioSetor repositorioSetor;
    private LiveData<List<ClasseSetor>> listaSetor;
    private LiveData<ClasseSetor> Consulta;

    public ViewModelSetor(@NonNull Application application) {
        super(application);
        repositorioSetor = new RepositorioSetor(application);
        listaSetor = repositorioSetor.getTodosSetores();
    }

    public ClasseSetor consulta (int id){return repositorioSetor.getSetor(id);}

    public void insert(ClasseSetor classeSetor) {
        repositorioSetor.insert(classeSetor);
    }

    public void update(ClasseSetor classeSetor) {
        repositorioSetor.update(classeSetor);
    }

    public void delete(ClasseSetor classeSetor) {
        repositorioSetor.delete(classeSetor);
    }
}