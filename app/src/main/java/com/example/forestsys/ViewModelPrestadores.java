package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelPrestadores extends AndroidViewModel {
    
    private RepositorioPrestadores repositorioPrestadores;
    private LiveData<List<ClassePrestadores>> listaPrestadores;
    private LiveData<ClassePrestadores> Consulta;

    public ViewModelPrestadores(@NonNull Application application) {
        super(application);
        repositorioPrestadores = new RepositorioPrestadores(application);
        listaPrestadores = repositorioPrestadores.getTodosPrestadores();
    }

    public void insert(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.insert(classeMaquinas);
    }

    public void update(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.update(classeMaquinas);
    }

    public void delete(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.delete(classeMaquinas);
    }
}