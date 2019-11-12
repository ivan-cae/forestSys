package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioFazenda {
    private DAO dao;
    private LiveData<List<ClasseFazenda>> fazendas;

    public RepositorioFazenda(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        fazendas = dao.todasFazendas();
    }

    public LiveData<ClasseFazenda> getFazenda(int id) {
        return dao.selecionaFazenda(id);
    }

    public LiveData<List<ClasseFazenda>> getTodasFazendas() {
        return fazendas;
    }


    public void insert(ClasseFazenda classeFazenda) {
        new RepositorioFazenda.InsertAsyncTask(dao).execute(classeFazenda);
    }

    public void update(ClasseFazenda classeFazenda) {
        new UpdateAsyncTask(dao).execute(classeFazenda);
    }

    public void delete(ClasseFazenda classeFazenda) {
        new DeleteAsyncTask(dao).execute(classeFazenda);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.insert(classeFazenda[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.update(classeFazenda[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.delete(classeFazenda[0]);
            return null;
        }
    }
}