package com.example.forestsys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositorioMaquinas {
    private DAO dao;
    private LiveData<List<ClasseMaquinas>> maquinas;

    public RepositorioMaquinas(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        maquinas = dao.todasMaquinas();
    }

    //retorna uma instância da ClasseMaquinas
//parâmetro de entrada: id inteiro para busca na tabela ClasseMaquinas
    public LiveData<ClasseMaquinas> getMaquina(int id) {
        return dao.selecionaMaquina(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseMaquinas
    public LiveData<List<ClasseMaquinas>> getTodasMaquinas() {
        return maquinas;
    }

    //inclui uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void insert(ClasseMaquinas classeMaquinas) {
        new RepositorioMaquinas.InsertAsyncTask(dao).execute(classeMaquinas);
    }

    //atualiza uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void update(ClasseMaquinas classeMaquinas) {
        new RepositorioMaquinas.UpdateAsyncTask(dao).execute(classeMaquinas);
    }

    //apaga uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void delete(ClasseMaquinas classeMaquinas) {
        new RepositorioMaquinas.DeleteAsyncTask(dao).execute(classeMaquinas);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseMaquinas, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseMaquinas... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseMaquinas, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseMaquinas... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseMaquinas, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseMaquinas... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}
