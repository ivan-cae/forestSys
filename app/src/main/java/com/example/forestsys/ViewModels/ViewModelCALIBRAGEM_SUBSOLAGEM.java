package com.example.forestsys.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Repositorios.RepositorioCALIBRAGEM_SUBSOLAGEM;

public class ViewModelCALIBRAGEM_SUBSOLAGEM {
    private RepositorioCALIBRAGEM_SUBSOLAGEM repositorioCALIBRAGEM_SUBSOLAGEM;
    private LiveData<CALIBRAGEM_SUBSOLAGEM> Consulta;

    public ViewModelCALIBRAGEM_SUBSOLAGEM(@NonNull Application application) {
        super();
        repositorioCALIBRAGEM_SUBSOLAGEM = new RepositorioCALIBRAGEM_SUBSOLAGEM(application);
    }


    //inclui uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void insert(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        repositorioCALIBRAGEM_SUBSOLAGEM.insert(calibragemSubsolagem);
    }

    //atualiza uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void update(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        repositorioCALIBRAGEM_SUBSOLAGEM.update(calibragemSubsolagem);
    }

    //apaga uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void delete(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        repositorioCALIBRAGEM_SUBSOLAGEM.delete(calibragemSubsolagem);
    }


    //retorna uma instância da CALIBRAGEM_SUBSOLAGEM
//parâmetro de entrada: id inteiro para busca na tabela CALIBRAGEM_SUBSOLAGEM
    public LiveData<CALIBRAGEM_SUBSOLAGEM> selecionaCalibragem(int idProg, String data, String turno, int idMaqImp) {
        return repositorioCALIBRAGEM_SUBSOLAGEM.getCalibragem(idProg, data, turno, idMaqImp);
    }

    public CALIBRAGEM_SUBSOLAGEM checaCalibragem(int idProg, String data, String turno) {
        return repositorioCALIBRAGEM_SUBSOLAGEM.checaCalibragem(idProg, data, turno);
    }
}
