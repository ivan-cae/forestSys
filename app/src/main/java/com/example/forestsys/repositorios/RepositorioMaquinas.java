package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.MAQUINAS;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMaquinas {
    private DAO dao;
    private LiveData<List<MAQUINAS>> maquinas;

    public RepositorioMaquinas(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        maquinas = dao.todasMaquinas();
    }

    //retorna uma instância da MAQUINAS
//parâmetro de entrada: id inteiro para busca na tabela MAQUINAS
    public LiveData<MAQUINAS> selecionaMaquina(int id) {
        return dao.selecionaMaquina(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela MAQUINAS
    public List<MAQUINAS> listaMaquinas() {
        return dao.listaMaquinas();
    }

    //retorna uma lista com todos os itens cadastrados na tabela MAQUINAS
    public LiveData<List<MAQUINAS>> todasMaquinas() {
        return maquinas;
    }


    //inclui uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void insert(MAQUINAS maquinas) {
        new RepositorioMaquinas.InsertAsyncTask(dao).execute(maquinas);
    }

    //atualiza uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void update(MAQUINAS maquinas) {
        new RepositorioMaquinas.UpdateAsyncTask(dao).execute(maquinas);
    }

    //apaga uma instância da MAQUINAS no DB
//parâmetro de entrada: instancia da MAQUINAS
    public void delete(MAQUINAS maquinas) {
        new RepositorioMaquinas.DeleteAsyncTask(dao).execute(maquinas);
    }


    private static class InsertAsyncTask extends AsyncTask<MAQUINAS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MAQUINAS... maquinas) {
            dao.insert(maquinas[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<MAQUINAS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MAQUINAS... maquinas) {
            dao.update(maquinas[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MAQUINAS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MAQUINAS... maquinas) {
            dao.delete(maquinas[0]);
            return null;
        }
    }
}
