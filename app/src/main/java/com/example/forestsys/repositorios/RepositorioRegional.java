package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.classes.GEO_REGIONAIS;

import java.util.List;

public class RepositorioRegional {
    private DAO dao;
    private LiveData<List<GEO_REGIONAIS>> regionais;

    public RepositorioRegional(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        regionais = dao.todosRegionais();
    }

    //retorna uma instância da GEO_REGIONAIS
//parâmetro de entrada: id inteiro para busca na tabela GEO_REGIONAIS
    public LiveData<GEO_REGIONAIS> getRegional(int id) {
        return dao.selecionaRegional(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela GEO_REGIONAIS
    public LiveData<List<GEO_REGIONAIS>> getTodosRegionais() {
        return regionais;
    }

    //inclui uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void insert(GEO_REGIONAIS GEOREGIONAIS) {
        new RepositorioRegional.InsertAsyncTask(dao).execute(GEOREGIONAIS);
    }

    //atualiza uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void update(GEO_REGIONAIS GEOREGIONAIS) {
        new UpdateAsyncTask(dao).execute(GEOREGIONAIS);
    }

    //apaga uma instância da GEO_REGIONAIS no DB
//parâmetro de entrada: instancia da GEO_REGIONAIS
    public void delete(GEO_REGIONAIS GEOREGIONAIS) {
        new DeleteAsyncTask(dao).execute(GEOREGIONAIS);
    }


    private static class InsertAsyncTask extends AsyncTask<GEO_REGIONAIS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_REGIONAIS... GEOREGIONAIS) {
            dao.insert(GEOREGIONAIS[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<GEO_REGIONAIS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_REGIONAIS... GEOREGIONAIS) {
            dao.update(GEOREGIONAIS[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<GEO_REGIONAIS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_REGIONAIS... GEOREGIONAIS) {
            dao.delete(GEOREGIONAIS[0]);
            return null;
        }
    }
}