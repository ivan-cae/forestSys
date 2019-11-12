package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioOs {
    private DAO dao;
    private LiveData<List<ClasseOs>> os;

    public RepositorioOs(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        os = dao.todasOs();
    }

    public LiveData<ClasseOs> getOs(int id) {
        return dao.selecionaOs(id);
    }

    public LiveData<List<ClasseOs>> getTodasOs() {
        return os;
    }


    public void insert(ClasseOs classeOs) {
        new RepositorioOs.InsertAsyncTask(dao).execute(classeOs);
    }

    public void update(ClasseOs classeOs) {
        new RepositorioOs.UpdateAsyncTask(dao).execute(classeOs);
    }

    public void delete(ClasseOs classeOs) {
        new RepositorioOs.DeleteAsyncTask(dao).execute(classeOs);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}