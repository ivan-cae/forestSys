package com.example.forestsys.Repositorios;
import android.app.Application;
import android.os.AsyncTask;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.GEO_SETORES;

import java.util.List;

public class RepositorioSetor{
    private DAO dao;
    private List<GEO_SETORES> setores;

    public RepositorioSetor(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        setores = dao.todosSetores();
    }

    //retorna uma instância da GEO_SETORES
//parâmetro de entrada: id inteiro para busca na tabela GEO_SETORES
    public GEO_SETORES getSetor(int id) {
        return dao.selecionaSetor(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela GEO_SETORES
    public List<GEO_SETORES> getTodosSetores() {
        return setores;
    }

    //inclui uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void insert(GEO_SETORES GEOSETORES) {
        new RepositorioSetor.InsertAsyncTask(dao).execute(GEOSETORES);
    }
    //atualiza uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void update(GEO_SETORES GEOSETORES) {
        new UpdateAsyncTask(dao).execute(GEOSETORES);
    }

    //apaga uma instância da GEO_SETORES no DB
//parâmetro de entrada: instancia da GEO_SETORES
    public void delete(GEO_SETORES GEOSETORES) {
        new DeleteAsyncTask(dao).execute(GEOSETORES);
    }


    private static class InsertAsyncTask extends AsyncTask<GEO_SETORES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_SETORES... GEOSETORES) {
            dao.insert(GEOSETORES[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<GEO_SETORES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_SETORES... GEOSETORES) {
            dao.update(GEOSETORES[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<GEO_SETORES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GEO_SETORES... GEOSETORES) {
            dao.delete(GEOSETORES[0]);
            return null;
        }
    }
}