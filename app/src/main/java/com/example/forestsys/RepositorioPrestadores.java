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
    //retorna uma instância da ClassePrestadores
//parâmetro de entrada: id inteiro para busca na tabela ClassePrestadores
    public LiveData<ClassePrestadores> getPrestador(int id) {
        return dao.selecionaPrestador(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClassePrestadores
    public LiveData<List<ClassePrestadores>> getTodosPrestadores() {
        return prestadores;
    }

    //inclui uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
    public void insert(ClassePrestadores classePrestadores) {
        new RepositorioPrestadores.InsertAsyncTask(dao).execute(classePrestadores);
    }

    //atualiza uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
    public void update(ClassePrestadores classePrestadores) {
        new RepositorioPrestadores.UpdateAsyncTask(dao).execute(classePrestadores);
    }


    //apaga uma instância da ClassePrestadores no DB
//parâmetro de entrada: instancia da ClassePrestadores
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
