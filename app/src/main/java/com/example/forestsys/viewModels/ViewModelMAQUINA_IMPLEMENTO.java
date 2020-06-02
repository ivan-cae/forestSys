package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.repositorios.RepositorioMAQUINA_IMPLEMENTO;

import java.util.List;

public class ViewModelMAQUINA_IMPLEMENTO extends AndroidViewModel {
    private RepositorioMAQUINA_IMPLEMENTO repositorioMaquinaImplemento;
    private LiveData<List<MAQUINA_IMPLEMENTO>> todasMaquinaImplemento;
    private List<MAQUINA_IMPLEMENTO> listaMaquinaImplemento;

    private LiveData<MAQUINA_IMPLEMENTO> Consulta;

    public ViewModelMAQUINA_IMPLEMENTO(@NonNull Application application) {
        super(application);
        repositorioMaquinaImplemento = new RepositorioMAQUINA_IMPLEMENTO(application);
        todasMaquinaImplemento = repositorioMaquinaImplemento.getTodosMaquinaImplementos();
    }


    //inclui uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
    public void insert(MAQUINA_IMPLEMENTO maquinaImplementos) {
        repositorioMaquinaImplemento.insert(maquinaImplementos);
    }

    //atualiza uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
    public void update(MAQUINA_IMPLEMENTO maquinaImplementos) {
        repositorioMaquinaImplemento.update(maquinaImplementos);
    }

    //apaga uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
    public void delete(MAQUINA_IMPLEMENTO maquinaImplementos) {
        repositorioMaquinaImplemento.delete(maquinaImplementos);
    }

    //retorna uma lista com todos os itens cadastrados na tabela MAQUINA_IMPLEMENTO
    public LiveData<List<MAQUINA_IMPLEMENTO>> todasMaquinaImplemento() {
        return todasMaquinaImplemento;
    }

    //retorna uma instância da MAQUINA_IMPLEMENTO
//parâmetro de entrada: id inteiro para busca na tabela MAQUINA_IMPLEMENTO
    public MAQUINA_IMPLEMENTO selecionaMaquina(int i) {
        return repositorioMaquinaImplemento.getMaquinaImplemento(i);
    }
}
