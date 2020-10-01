package com.example.forestsys.Repositorios;

import android.app.Application;
import android.os.AsyncTask;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.MANEJO;


public class RepositorioManejo {
    private DAO dao;

    public RepositorioManejo(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }

    //retorna uma instância da MANEJO
//parâmetro de entrada: id inteiro para busca na tabela MANEJO
    public MANEJO selecionaManejo(int id) {
        return dao.selecionaManejo(id);
    }

    //inclui uma instância da MANEJO no DB
//parâmetro de entrada: instancia da MANEJO
    public void insert(MANEJO manejos) {
        new RepositorioManejo.InsertAsyncTask(dao).execute(manejos);
    }

    //atualiza uma instância da MANEJO no DB
//parâmetro de entrada: instancia da MANEJO
    public void update(MANEJO manejos) {
        new RepositorioManejo.UpdateAsyncTask(dao).execute(manejos);
    }

    //apaga uma instância da MANEJO no DB
//parâmetro de entrada: instancia da MANEJO
    public void delete(MANEJO manejos) {
        new RepositorioManejo.DeleteAsyncTask(dao).execute(manejos);
    }


    private static class InsertAsyncTask extends AsyncTask<MANEJO, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MANEJO... manejos) {
            dao.insert(manejos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<MANEJO, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MANEJO... manejos) {
            dao.update(manejos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MANEJO, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MANEJO... manejos) {
            dao.delete(manejos[0]);
            return null;
        }
    }
}
