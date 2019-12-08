package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelFazenda extends AndroidViewModel{

    private RepositorioFazenda repositorioFazenda;
    private LiveData<List<ClasseFazenda>> listaFazendas;
    private LiveData<ClasseFazenda> Consulta;

    public ViewModelFazenda(@NonNull Application application) {
        super(application);
        repositorioFazenda = new RepositorioFazenda(application);
        listaFazendas = repositorioFazenda.getTodasFazendas();
    }
//inclui uma instância da ClasseFazenda no DB
    //parâmetro de entrada: instancia da ClasseFazenda
    public void insert(ClasseFazenda classeFazenda) {
        repositorioFazenda.insert(classeFazenda);
    }

    //atualiza uma instância da ClasseFazenda no DB
//parâmetro de entrada: instancia da ClasseFazenda
    public void update(ClasseFazenda classeFazenda) {
        repositorioFazenda.update(classeFazenda);
    }

    //apaga uma instância da ClasseFazenda no DB
//parâmetro de entrada: instancia da ClasseFazenda
    public void delete(ClasseFazenda classeFazenda) {
        repositorioFazenda.delete(classeFazenda);
    }
}