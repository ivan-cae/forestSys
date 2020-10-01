package com.example.forestsys.Repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.INSUMOS;

import java.util.List;

public class RepositorioINSUMOS {
    private DAO dao;
    private LiveData<List<INSUMOS>> insumos;

    public RepositorioINSUMOS(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
       insumos = dao.todosInsumos();
    }

    //retorna uma instância da INSUMOS
//parâmetro de entrada: id inteiro para busca na tabela INSUMOS
    public INSUMOS getInsumo(int id) {
        return dao.selecionaInsumo(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela INSUMOS
    public LiveData<List<INSUMOS>> getTodosInsumo() {
        return insumos;
    }



    //inclui uma instância da Classe INSUMOS no DB
//parâmetro de entrada: instancia da Classe INSUMOS
    public void insert(INSUMOS insumo) {
        new RepositorioINSUMOS.InsertAsyncTask(dao).execute(insumo);
    }

    //atualiza uma instância da Classe INSUMOS no DB
//parâmetro de entrada: instancia da Classe INSUMOS
    public void update(INSUMOS insumo) {
        new RepositorioINSUMOS.UpdateAsyncTask(dao).execute(insumo);
    }

    //apaga uma instância da Classe INSUMOS no DB
//parâmetro de entrada: instancia da Classe INSUMOS
    public void delete(INSUMOS insumo) {
        new RepositorioINSUMOS.DeleteAsyncTask(dao).execute(insumo);
    }


    private static class InsertAsyncTask extends AsyncTask<INSUMOS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(INSUMOS...insumo) {
            dao.insert(insumo[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<INSUMOS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(INSUMOS...insumo) {
            dao.update(insumo[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<INSUMOS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(INSUMOS...insumo) {
            dao.delete(insumo[0]);
            return null;
        }
    }
}
