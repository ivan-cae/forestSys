package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.ATIVIDADE_INDICADORES;

import java.util.List;

public class repositorioATIVIDADE_INDICADORES {
    private DAO dao;

    public repositorioATIVIDADE_INDICADORES(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }


    //inclui uma instância da Classe ATIVIDADE_INDICADORES no DB
//parâmetro de entrada: instancia da Classe ATIVIDADE_INDICADORES
    public void insert(ATIVIDADE_INDICADORES OsAtividadeIndicadores) {
        new repositorioATIVIDADE_INDICADORES.InsertAsyncTask(dao).execute(OsAtividadeIndicadores);
    }

    //atualiza uma instância da Classe ATIVIDADE_INDICADORES no DB
//parâmetro de entrada: instancia da Classe ATIVIDADE_INDICADORES
    public void update(ATIVIDADE_INDICADORES OsAtividadeIndicadores) {
        new repositorioATIVIDADE_INDICADORES.UpdateAsyncTask(dao).execute(OsAtividadeIndicadores);
    }

    //apaga uma instância da Classe ATIVIDADE_INDICADORES no DB
//parâmetro de entrada: instancia da Classe ATIVIDADE_INDICADORES
    public void delete(ATIVIDADE_INDICADORES OsAtividadeIndicadores) {
        new repositorioATIVIDADE_INDICADORES.DeleteAsyncTask(dao).execute(OsAtividadeIndicadores);
    }


    private static class InsertAsyncTask extends AsyncTask<ATIVIDADE_INDICADORES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADE_INDICADORES...OsAtividadeIndicadores) {
            dao.insert(OsAtividadeIndicadores[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ATIVIDADE_INDICADORES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADE_INDICADORES...OsAtividadeIndicadores) {
            dao.update(OsAtividadeIndicadores[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ATIVIDADE_INDICADORES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADE_INDICADORES...OsAtividadeIndicadores) {
            dao.delete(OsAtividadeIndicadores[0]);
            return null;
        }
    }
}
