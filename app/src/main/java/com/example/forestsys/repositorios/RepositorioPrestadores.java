package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.classes.PRESTADORES;

import java.util.List;

public class RepositorioPrestadores {
    private DAO dao;
    private LiveData<List<PRESTADORES>> prestadores;

    public RepositorioPrestadores(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        prestadores = dao.todosPrestadores();
    }

    //retorna uma instância da PRESTADORES
//parâmetro de entrada: id inteiro para busca na tabela PRESTADORES
    public PRESTADORES selecionaprestador(int id) {
        return dao.selecionaPrestador(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela PRESTADORES
    public List<PRESTADORES> listaPrestadores() {
        return dao.listaPrestadores();
    }

    //retorna uma lista com todos os itens cadastrados na tabela PRESTADORES
    public LiveData<List<PRESTADORES>> todosPrestadores() {
        return prestadores;
    }


    //inclui uma instância da PRESTADORES no DB
//parâmetro de entrada: instancia da PRESTADORES
    public void insert(PRESTADORES prestadores) {
        new RepositorioPrestadores.InsertAsyncTask(dao).execute(prestadores);
    }

    //atualiza uma instância da PRESTADORES no DB
//parâmetro de entrada: instancia da PRESTADORES
    public void update(PRESTADORES prestadores) {
        new RepositorioPrestadores.UpdateAsyncTask(dao).execute(prestadores);
    }

    //apaga uma instância da PRESTADORES no DB
//parâmetro de entrada: instancia da PRESTADORES
    public void delete(PRESTADORES prestadores) {
        new RepositorioPrestadores.DeleteAsyncTask(dao).execute(prestadores);
    }


    private static class InsertAsyncTask extends AsyncTask<PRESTADORES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PRESTADORES... prestadores) {
            dao.insert(prestadores[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<PRESTADORES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PRESTADORES... prestadores) {
            dao.update(prestadores[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<PRESTADORES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PRESTADORES... prestadores) {
            dao.delete(prestadores[0]);
            return null;
        }
    }
}
