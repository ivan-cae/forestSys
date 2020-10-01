package com.example.forestsys.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Repositorios.RepositorioO_S_ATIVIDADES_DIA;

public class ViewModelO_S_ATIVIDADES_DIA {
    private RepositorioO_S_ATIVIDADES_DIA repositorioO_S_ATIVIDADES_DIA;
    private LiveData<O_S_ATIVIDADES_DIA> Consulta;

    public ViewModelO_S_ATIVIDADES_DIA(@NonNull Application application) {
        super();
        repositorioO_S_ATIVIDADES_DIA = new RepositorioO_S_ATIVIDADES_DIA(application);
    }


    //inclui uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void insert(O_S_ATIVIDADES_DIA osAtividadeDia) {
        repositorioO_S_ATIVIDADES_DIA.insert(osAtividadeDia);
    }

    //atualiza uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void update(O_S_ATIVIDADES_DIA osAtividadeDia) {
        repositorioO_S_ATIVIDADES_DIA.update(osAtividadeDia);
    }

    //apaga uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void delete(O_S_ATIVIDADES_DIA osAtividadeDia) {
        repositorioO_S_ATIVIDADES_DIA.delete(osAtividadeDia);
    }


    //retorna uma instância da O_S_ATIVIDADES_DIA
//parâmetro de entrada: id inteiro e data para busca na tabela O_S_ATIVIDADES_DIA
    public O_S_ATIVIDADES_DIA selecionaOsAtividadesDia(int idProg, String data) {
        return repositorioO_S_ATIVIDADES_DIA.selecionaOsAtividadesDia(idProg, data);
    }
}