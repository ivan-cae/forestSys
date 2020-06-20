package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.ATIVIDADES;

import java.util.List;

public class RepositorioATIVIDADES {
    private DAO dao;
    private List<ATIVIDADES> atividades;

    public RepositorioATIVIDADES(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }

    //inclui uma instância da ATIVIDADES no DB
//parâmetro de entrada: instancia da ATIVIDADES
    public void insert(ATIVIDADES atividades) {
        new RepositorioATIVIDADES.InsertAsyncTask(dao).execute(atividades);
    }
    //atualiza uma instância da ATIVIDADES no DB
//parâmetro de entrada: instancia da ATIVIDADES
    public void update(ATIVIDADES atividades) {
        new RepositorioATIVIDADES.UpdateAsyncTask(dao).execute(atividades);
    }

    //apaga uma instância da ATIVIDADES no DB
//parâmetro de entrada: instancia da ATIVIDADES
    public void delete(ATIVIDADES atividades) {
        new RepositorioATIVIDADES.DeleteAsyncTask(dao).execute(atividades);
    }


    private static class InsertAsyncTask extends AsyncTask<ATIVIDADES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADES... atividades) {
            dao.insert(atividades[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ATIVIDADES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADES... atividades) {
            dao.update(atividades[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ATIVIDADES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ATIVIDADES... atividades) {
            dao.delete(atividades[0]);
            return null;
        }
    }
    
}
