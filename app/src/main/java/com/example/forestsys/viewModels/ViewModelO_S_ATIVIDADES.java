package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.repositorios.RepositorioO_S_ATIVIDADES;

import java.util.List;


public class ViewModelO_S_ATIVIDADES extends AndroidViewModel {

    private RepositorioO_S_ATIVIDADES repositorioOs;
    private LiveData<List<O_S_ATIVIDADES>> listaOs;

    public ViewModelO_S_ATIVIDADES(@NonNull Application application) {
        super(application);
        repositorioOs = new RepositorioO_S_ATIVIDADES(application);
        listaOs = repositorioOs.getTodasOs();
    }


    //inclui uma instância da O_S_ATIVIDADES no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES
    public void insert(O_S_ATIVIDADES O_S_ATIVIDADES) {
        repositorioOs.insert(O_S_ATIVIDADES);
    }

    //atualiza uma instância da O_S_ATIVIDADES no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES
    public void update(O_S_ATIVIDADES O_S_ATIVIDADES) {
        repositorioOs.update(O_S_ATIVIDADES);
    }

    //apaga uma instância da O_S_ATIVIDADES no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES
    public void delete(O_S_ATIVIDADES O_S_ATIVIDADES) {
        repositorioOs.delete(O_S_ATIVIDADES);
    }

    //retorna uma lista com todos os itens cadastrados na tabela O_S_ATIVIDADES
    public LiveData<List<O_S_ATIVIDADES>> getTodasOS() {
        return listaOs;
    }

    //retorna uma instância da O_S_ATIVIDADES
//parâmetro de entrada: id inteiro para busca na tabela O_S_ATIVIDADES
    public O_S_ATIVIDADES getOs(int i) {
        return repositorioOs.getOs(i);
    }

}