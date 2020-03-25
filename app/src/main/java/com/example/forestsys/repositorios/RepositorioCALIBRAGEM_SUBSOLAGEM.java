package com.example.forestsys.repositorios;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;

import java.util.List;

public class RepositorioCALIBRAGEM_SUBSOLAGEM {
    private DAO dao;
    private LiveData<List<CALIBRAGEM_SUBSOLAGEM>> calibragemSubsolagem;

    public RepositorioCALIBRAGEM_SUBSOLAGEM(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }

    //retorna uma instância da CALIBRAGEM_SUBSOLAGEM
//parâmetro de entrada: id inteiro para busca na tabela CALIBRAGEM_SUBSOLAGEM
    public LiveData<CALIBRAGEM_SUBSOLAGEM> getCalibragem(int idProg, String data, String turno, int idMaqImp) {
        return dao.selecionaCalibragem( idProg,  data,  turno,  idMaqImp);
    }

    public CALIBRAGEM_SUBSOLAGEM checaCalibragem(int idProg, String data, String turno) {
        return dao.checaCalibragem( idProg,  data,  turno);
    }

    //inclui uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void insert(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        new RepositorioCALIBRAGEM_SUBSOLAGEM.InsertAsyncTask(dao).execute(calibragemSubsolagem);
    }

    //atualiza uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void update(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        new RepositorioCALIBRAGEM_SUBSOLAGEM.UpdateAsyncTask(dao).execute(calibragemSubsolagem);
    }

    //apaga uma instância da CALIBRAGEM_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da CALIBRAGEM_SUBSOLAGEM
    public void delete(CALIBRAGEM_SUBSOLAGEM calibragemSubsolagem) {
        new RepositorioCALIBRAGEM_SUBSOLAGEM.DeleteAsyncTask(dao).execute(calibragemSubsolagem);
    }


    private static class InsertAsyncTask extends AsyncTask<CALIBRAGEM_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CALIBRAGEM_SUBSOLAGEM... calibragemSubsolagem) {
            dao.insert(calibragemSubsolagem[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<CALIBRAGEM_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CALIBRAGEM_SUBSOLAGEM... calibragemSubsolagem) {
            dao.update(calibragemSubsolagem[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<CALIBRAGEM_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CALIBRAGEM_SUBSOLAGEM... calibragemSubsolagem) {
            dao.delete(calibragemSubsolagem[0]);
            return null;
        }
    }
}
