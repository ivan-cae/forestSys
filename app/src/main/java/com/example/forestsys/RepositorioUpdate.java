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
    //retorna uma instância da ClasseUpdate
//parâmetro de entrada: id inteiro para busca na tabela ClasseUpdate
    public LiveData<ClasseUpdate> getUpdate(int id) {
        return dao.selecionaUpdate(id);
    }


    //retorna uma lista com todos os itens cadastrados na tabela ClasseUpdate
    public LiveData<List<ClasseUpdate>> getTodosUpdates() {
        return updates;
    }


    //retorna uma lista com todos os updates de uma Ordem de Serviço
    //parâmetro de entrada: id da OS para busca de seus updates
    public LiveData<List<ClasseUpdate>> getTodasUpdatesOs(int i){
        osUpdates = dao.selecionaOsUpdate(i);
        return osUpdates;
    }

    //inclui uma instância da ClasseUpdate no DB
//parâmetro de entrada: instancia da ClasseUpdate
    public void insert(ClasseUpdate classeUpdate) {
        new RepositorioUpdate.InsertAsyncTask(dao).execute(classeUpdate);
    }

    //atualiza uma instância da ClasseUpdate no DB
//parâmetro de entrada: instancia da ClasseUpdate
    public void update(ClasseUpdate classeUpdate) {
        new RepositorioUpdate.UpdateAsyncTask(dao).execute(classeUpdate);
    }

    //apaga uma instância da ClasseUpdate no DB
//parâmetro de entrada: instancia da ClasseUpdate
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