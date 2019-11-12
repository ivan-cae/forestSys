package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioEncarregados {

    private DAO dao;
    private LiveData<List<ClasseEncarregados>> encarregados;

    public RepositorioEncarregados(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        encarregados = dao.todosEncarregados();
    }

    public LiveData<ClasseEncarregados> getEncarregado(int id) {
        return dao.selecionaEncarregado(id);
    }

    public LiveData<List<ClasseEncarregados>> getTodosEncarregados() {
        return encarregados;
    }


    public void insert(ClasseEncarregados classeEncarregados) {
        new InsertAsyncTask(dao).execute(classeEncarregados);
    }

    public void update(ClasseEncarregados classeEncarregados) {
        new UpdateAsyncTask(dao).execute(classeEncarregados);
    }

    public void delete(ClasseEncarregados classeEncarregados) {
        new DeleteAsyncTask(dao).execute(classeEncarregados);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseEncarregados, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseEncarregados... encarregados) {
            dao.insert(encarregados[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseEncarregados, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseEncarregados... encarregados) {
            dao.update(encarregados[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseEncarregados, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseEncarregados... encarregados) {
            dao.delete(encarregados[0]);
            return null;
        }
    }
}