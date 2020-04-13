package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.O_S_ATIVIDADES;


import java.util.List;

public class RepositorioO_S_ATIVIDADES {
    private DAO dao;
    private LiveData<List<O_S_ATIVIDADES>> os;

    public RepositorioO_S_ATIVIDADES(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        os = dao.todasOs();
    }

    //retorna uma instância da ClasseOs
//parâmetro de entrada: id inteiro para busca na tabela ClasseOs
    public O_S_ATIVIDADES getOs(int id) {
        return dao.selecionaOs(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseOs
    public LiveData<List<O_S_ATIVIDADES>> getTodasOs() {
        return os;
    }

    //retorna uma lista do tipo List com todos os itens cadastrados na tabela ClasseOs
    public LiveData<List<O_S_ATIVIDADES>> getListaOs(){return dao.selecionaListaOs();}

    //inclui uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void insert(O_S_ATIVIDADES classeOs) {
        new RepositorioO_S_ATIVIDADES.InsertAsyncTask(dao).execute(classeOs);
    }

    //atualiza uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void update(O_S_ATIVIDADES classeOs) {
        new RepositorioO_S_ATIVIDADES.UpdateAsyncTask(dao).execute(classeOs);
    }

    //apaga uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void delete(O_S_ATIVIDADES classeOs) {
        new RepositorioO_S_ATIVIDADES.DeleteAsyncTask(dao).execute(classeOs);
    }


    private static class InsertAsyncTask extends AsyncTask<O_S_ATIVIDADES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<O_S_ATIVIDADES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<O_S_ATIVIDADES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADES... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}
