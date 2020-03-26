package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.CADASTRO_FLORESTAL;

import java.util.List;

public class RepositorioCADASTRO_FLORESTAL {
    private DAO dao;
    private LiveData<List<CADASTRO_FLORESTAL>> os;
    private List <CADASTRO_FLORESTAL> listaCadFlorestal;

    public RepositorioCADASTRO_FLORESTAL(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        os = dao.todosCadFlorestal();
    }

    //retorna uma instância da ClasseOs
//parâmetro de entrada: id inteiro para busca na tabela ClasseOs
    public LiveData<CADASTRO_FLORESTAL> getCad(int id) {
        return dao.selecionaCadFlorestal(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseOs
    public LiveData<List<CADASTRO_FLORESTAL>> getTodosCad() {
        return os;
    }


    //inclui uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void insert(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.InsertAsyncTask(dao).execute(classeOs);
    }

    //atualiza uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void update(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.UpdateAsyncTask(dao).execute(classeOs);
    }

    //apaga uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void delete(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.DeleteAsyncTask(dao).execute(classeOs);
    }


    private static class InsertAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}
