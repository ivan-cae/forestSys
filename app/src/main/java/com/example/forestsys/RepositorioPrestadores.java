package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioPrestadores {
    private DAO dao;
    private LiveData<List<ClassePrestadores>> prestadores;

    public RepositorioPrestadores(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        prestadores = dao.todosPrestadores();
    }

    public LiveData<ClassePrestadores> getPrestador(int id) {
        return dao.selecionaPrestador(id);
    }

    public LiveData<List<ClassePrestadores>> getTodosPrestadores() {
        return prestadores;
    }


    public void insert(ClassePrestadores classePrestadores) {
        new RepositorioPrestadores.InsertAsyncTask(dao).execute(classePrestadores);
    }

    public void update(ClassePrestadores classePrestadores) {
        new RepositorioPrestadores.UpdateAsyncTask(dao).execute(classePrestadores);
    }

    public void delete(ClassePrestadores classePrestadores) {
        new RepositorioPrestadores.DeleteAsyncTask(dao).execute(classePrestadores);
    }


    private static class InsertAsyncTask extends AsyncTask<ClassePrestadores, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClassePrestadores... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClassePrestadores, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClassePrestadores... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClassePrestadores, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClassePrestadores... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}
