package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.classes.IMPLEMENTOS;

import java.util.List;

public class RepositorioImplementos {
    private DAO dao;
    private LiveData<List<IMPLEMENTOS>> implementos;

    public RepositorioImplementos(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        implementos = dao.todosImplementos();
    }

    //retorna uma instância da IMPLEMENTOS
//parâmetro de entrada: id inteiro para busca na tabela IMPLEMENTOS
    public IMPLEMENTOS getImplemento(int id) {
        return dao.selecionaImplemento(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela IMPLEMENTOS
    public LiveData<List<IMPLEMENTOS>> getImplementos() {
        return implementos;
    }

    public List<IMPLEMENTOS> listaImplementos(){return dao.listaImplementos();}

    //inclui uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void insert(IMPLEMENTOS implementos) {
        new RepositorioImplementos.InsertAsyncTask(dao).execute(implementos);
    }

    //atualiza uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void update(IMPLEMENTOS implementos) {
        new RepositorioImplementos.UpdateAsyncTask(dao).execute(implementos);
    }

    //apaga uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void delete(IMPLEMENTOS implementos) {
        new RepositorioImplementos.DeleteAsyncTask(dao).execute(implementos);
    }


    private static class InsertAsyncTask extends AsyncTask<IMPLEMENTOS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(IMPLEMENTOS... implementos) {
            dao.insert(implementos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<IMPLEMENTOS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(IMPLEMENTOS... implementos) {
            dao.update(implementos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<IMPLEMENTOS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(IMPLEMENTOS... implementos) {
            dao.delete(implementos[0]);
            return null;
        }
    }
}
