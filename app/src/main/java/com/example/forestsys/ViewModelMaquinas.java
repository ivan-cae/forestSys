package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelMaquinas extends AndroidViewModel {
    
    private RepositorioMaquinas repositorioMaquinas;
    private LiveData<List<ClasseMaquinas>> listaMaquinas;
    private LiveData<ClasseMaquinas> Consulta;

    public ViewModelMaquinas(@NonNull Application application) {
        super(application);
        repositorioMaquinas = new RepositorioMaquinas(application);
        listaMaquinas = repositorioMaquinas.getTodasMaquinas();
    }

    public void insert(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.insert(classeMaquinas);
    }

    public void update(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.update(classeMaquinas);
    }

    public void delete(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.delete(classeMaquinas);
    }
}
