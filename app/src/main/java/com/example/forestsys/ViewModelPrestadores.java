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


    //inclui uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
    public void insert(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.insert(classeMaquinas);
    }
    
    //atualiza uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
    public void update(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.update(classeMaquinas);
    }

    //apaga uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
    public void delete(ClassePrestadores classeMaquinas) {
        repositorioPrestadores.delete(classeMaquinas);
    }
}