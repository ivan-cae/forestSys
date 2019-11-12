package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioUpdate {
    private DAO dao;
    private LiveData<List<ClasseUpdate>> updates;
    private LiveData<List<ClasseUpdate>> osUpdates;

    public RepositorioUpdate(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        updates = dao.todosUpdates();
    }

    public LiveData<ClasseUpdate> getUpdate(int id) {
        return dao.selecionaUpdate(id);
    }

    public LiveData<List<ClasseUpdate>> getTodosUpdates() {
        return updates;
    }

    public LiveData<List<ClasseUpdate>> getTodasUpdatesOs(int i){
        osUpdates = dao.selecionaOsUpdate(i);
        return osUpdates;
    }


    public void insert(ClasseUpdate classeUpdate) {
        new RepositorioUpdate.InsertAsyncTask(dao).execute(classeUpdate);
    }

    public void update(ClasseUpdate classeUpdate) {
        new RepositorioUpdate.UpdateAsyncTask(dao).execute(classeUpdate);
    }

    public void delete(ClasseUpdate classeUpdate) {
        new RepositorioUpdate.DeleteAsyncTask(dao).execute(classeUpdate);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseUpdate, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUpdate... classeUpdate){
            dao.insert(classeUpdate[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseUpdate, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUpdate... classeUpdate) {
            dao.update(classeUpdate[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseUpdate, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUpdate... classeUpdate) {
            dao.delete(classeUpdate[0]);
            return null;
        }
    }
}