package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;

import java.util.List;

public class RepositorioO_S_ATIVIDADES_DIA {
    private DAO dao;
    private LiveData<List<O_S_ATIVIDADES_DIA>> osAtividadesDia;

    public RepositorioO_S_ATIVIDADES_DIA(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }

    //retorna uma instância da O_S_ATIVIDADES_DIA
//parâmetro de entrada: id inteiro para busca na tabela O_S_ATIVIDADES_DIA
    public O_S_ATIVIDADES_DIA selecionaOsAtividadesDia(int idProg, String data) {
        return dao.selecionaOsAtividadesDia( idProg,  data);
    }


    //inclui uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void insert(O_S_ATIVIDADES_DIA osAtividadesDia) {
        new RepositorioO_S_ATIVIDADES_DIA.InsertAsyncTask(dao).execute(osAtividadesDia);
    }

    //atualiza uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void update(O_S_ATIVIDADES_DIA osAtividadesDia) {
        new RepositorioO_S_ATIVIDADES_DIA.UpdateAsyncTask(dao).execute(osAtividadesDia);
    }

    //apaga uma instância da O_S_ATIVIDADES_DIA no DB
//parâmetro de entrada: instancia da O_S_ATIVIDADES_DIA
    public void delete(O_S_ATIVIDADES_DIA osAtividadesDia) {
        new RepositorioO_S_ATIVIDADES_DIA.DeleteAsyncTask(dao).execute(osAtividadesDia);
    }


    private static class InsertAsyncTask extends AsyncTask<O_S_ATIVIDADES_DIA, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES_DIA... osAtividadesDia) {
            dao.insert(osAtividadesDia[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<O_S_ATIVIDADES_DIA, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES_DIA... osAtividadesDia) {
            dao.update(osAtividadesDia[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<O_S_ATIVIDADES_DIA, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES_DIA... osAtividadesDia) {
            dao.delete(osAtividadesDia[0]);
            return null;
        }
    }
}