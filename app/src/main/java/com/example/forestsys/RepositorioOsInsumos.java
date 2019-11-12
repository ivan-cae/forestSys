package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioOsInsumos {
    private DAO dao;
    private LiveData<List<ClasseOsInsumos>> osInsumos;

    public RepositorioOsInsumos(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        osInsumos = dao.todosInsumos();
    }

    public LiveData<ClasseOsInsumos> getEncarregado(int id) {
        return dao.selecionaInsumo(id);
    }

    public LiveData<List<ClasseOsInsumos>> getTodosOsInsumos() {
        return osInsumos;
    }


    public void insert(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.InsertAsyncTask(dao).execute(classeOsInsumos);
    }

    public void update(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.UpdateAsyncTask(dao).execute(classeOsInsumos);
    }

    public void delete(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.DeleteAsyncTask(dao).execute(classeOsInsumos);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.insert(osInsumos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.update(osInsumos[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.delete(osInsumos[0]);
            return null;
        }
    }
}