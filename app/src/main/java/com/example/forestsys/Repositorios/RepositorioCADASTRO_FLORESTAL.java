package com.example.forestsys.Repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;

import java.util.List;

public class RepositorioCADASTRO_FLORESTAL {
    private DAO dao;
    private LiveData<List<CADASTRO_FLORESTAL>> cadastros;
    private List <CADASTRO_FLORESTAL> listaCadFlorestal;

    public RepositorioCADASTRO_FLORESTAL(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        cadastros = dao.todosCadFlorestal();
    }

    //retorna uma instância da CADASTRO_FLORESTAL
//parâmetro de entrada: id inteiro para busca na tabela CADASTRO_FLORESTAL
    public CADASTRO_FLORESTAL getCad(int idReg, int idSet, String talhao, int ciclo, int idManejo) {
        return dao.selecionaCadFlorestal(idReg, idSet, talhao, ciclo, idManejo);
    }

    //retorna uma lista com todos os itens cadastrados na tabela CADASTRO_FLORESTAL
    public LiveData<List<CADASTRO_FLORESTAL>> getTodosCad() {
        return cadastros;
    }


    //inclui uma instância da CADASTRO_FLORESTAL no DB
//parâmetro de entrada: instancia da CADASTRO_FLORESTAL
    public void insert(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.InsertAsyncTask(dao).execute(classeOs);
    }

    //atualiza uma instância da CADASTRO_FLORESTAL no DB
//parâmetro de entrada: instancia da CADASTRO_FLORESTAL
    public void update(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.UpdateAsyncTask(dao).execute(classeOs);
    }

    //apaga uma instância da CADASTRO_FLORESTAL no DB
//parâmetro de entrada: instancia da CADASTRO_FLORESTAL
    public void delete(CADASTRO_FLORESTAL classeOs) {
        new RepositorioCADASTRO_FLORESTAL.DeleteAsyncTask(dao).execute(classeOs);
    }


    private static class InsertAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... cadastros) {
            dao.insert(cadastros[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... cadastros) {
            dao.update(cadastros[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<CADASTRO_FLORESTAL, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CADASTRO_FLORESTAL... cadastros) {
            dao.delete(cadastros[0]);
            return null;
        }
    }
}
