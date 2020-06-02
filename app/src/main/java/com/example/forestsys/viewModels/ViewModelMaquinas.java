package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.repositorios.RepositorioMaquinas;

import java.util.ArrayList;
import java.util.List;

public class ViewModelMaquinas extends AndroidViewModel {
    private RepositorioMaquinas repositorioMaquinas;
    private LiveData<List<MAQUINAS>> todasMaquinas;
    private List<MAQUINAS> listaMaquinas;

    private LiveData<MAQUINAS> Consulta;

    public ViewModelMaquinas(@NonNull Application application) {
        super(application);
        repositorioMaquinas = new RepositorioMaquinas(application);
        todasMaquinas = repositorioMaquinas.todasMaquinas();
    }


    //inclui uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void insert(MAQUINAS maquinas) {
        repositorioMaquinas.insert(maquinas);
    }

    //atualiza uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void update(MAQUINAS maquinas) {
        repositorioMaquinas.update(maquinas);
    }

    //apaga uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void delete(MAQUINAS maquinas) {
        repositorioMaquinas.delete(maquinas);
    }

    //retorna uma lista com todos os itens cadastrados na tabela O_S_ATIVIDADES
    public LiveData<List<MAQUINAS>> todasMaquinas() {
        return todasMaquinas;
    }

    //retorna uma instância da O_S_ATIVIDADES
//parâmetro de entrada: id inteiro para busca na tabela O_S_ATIVIDADES
    public MAQUINAS selecionaMaquina(int i) {
        return repositorioMaquinas.selecionaMaquina(i);
    }

    //retorna uma lista do tipo List com todos os itens cadastrados na tabela O_S_ATIVIDADES
    public List<MAQUINAS> listaMaquinas() {
        return repositorioMaquinas.listaMaquinas();
    }

}
