package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioRegional {
    private DAO dao;
    private LiveData<List<ClasseRegional>> regionais;

    public RepositorioRegional(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        regionais = dao.todosRegionais();
    }

    //retorna uma instância da ClasseRegional
//parâmetro de entrada: id inteiro para busca na tabela ClasseRegional
    public ClasseRegional getRegional(int id) {
        return dao.selecionaRegional(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseRegional
    public LiveData<List<ClasseRegional>> getTodosRegionais() {
        return regionais;
    }

    //inclui uma instância da ClasseRegional no DB
//parâmetro de entrada: instancia da ClasseRegional
    public void insert(ClasseRegional classeRegional) {
        new RepositorioRegional.InsertAsyncTask(dao).execute(classeRegional);
    }

    //atualiza uma instância da ClasseRegional no DB
//parâmetro de entrada: instancia da ClasseRegional
    public void update(ClasseRegional classeRegional) {
        new UpdateAsyncTask(dao).execute(classeRegional);
    }

    //apaga uma instância da ClasseRegional no DB
//parâmetro de entrada: instancia da ClasseRegional
    public void delete(ClasseRegional classeRegional) {
        new DeleteAsyncTask(dao).execute(classeRegional);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseRegional, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseRegional... classeRegional) {
            dao.insert(classeRegional[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseRegional, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseRegional... classeRegional) {
            dao.update(classeRegional[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasseRegional, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseRegional... classeRegional) {
            dao.delete(classeRegional[0]);
            return null;
        }
    }
}