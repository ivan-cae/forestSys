package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.ClasseOs;
import com.example.forestsys.repositorios.RepositorioOs;

import java.util.List;

public class ViewModelOs extends AndroidViewModel {
    
    private RepositorioOs repositorioOs;
    private LiveData<List<ClasseOs>> listaOs;
    private LiveData<ClasseOs> Consulta;

    public ViewModelOs(@NonNull Application application) {
        super(application);
        repositorioOs = new RepositorioOs(application);
        listaOs = repositorioOs.getTodasOs();
    }

    //inclui uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void insert(ClasseOs ClasseOs) {
        repositorioOs.insert(ClasseOs);
    }

    //atualiza uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void update(ClasseOs ClasseOs) {
        repositorioOs.update(ClasseOs);
    }
    
    //apaga uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void delete(ClasseOs ClasseOs) {
        repositorioOs.delete(ClasseOs);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseOs
    public LiveData<List<ClasseOs>> getTodasOS() {
        return listaOs;
    }

    //retorna uma instância da ClasseOs
//parâmetro de entrada: id inteiro para busca na tabela ClasseOs
    public LiveData<ClasseOs> getOs(int i){
        return repositorioOs.getOs(i);
    }


    //retorna uma lista do tipo List com todos os itens cadastrados na tabela ClasseOs
    public List<ClasseOs> getListaOs(){return repositorioOs.getListaOs(); }
}