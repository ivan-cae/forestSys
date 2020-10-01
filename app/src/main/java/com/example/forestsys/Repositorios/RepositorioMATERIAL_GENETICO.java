package com.example.forestsys.Repositorios;
import android.app.Application;
import android.os.AsyncTask;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.MATERIAL_GENETICO;


public class RepositorioMATERIAL_GENETICO {
    private DAO dao;

    public RepositorioMATERIAL_GENETICO(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
    }

    //retorna uma instância da MATERIAL_GENETICO
//parâmetro de entrada: id inteiro para busca na tabela MATERIAL_GENETICO
    public MATERIAL_GENETICO selecionaMaterialGenetico(int id) {
        return dao.selecionaMaterialGenetico(id);
    }

    //inclui uma instância da MANEJO no DB
//parâmetro de entrada: instancia da MATERIAL_GENETICO
    public void insert(MATERIAL_GENETICO materiais) {
        new RepositorioMATERIAL_GENETICO.InsertAsyncTask(dao).execute(materiais);
    }

    //atualiza uma instância da MATERIAL_GENETICO no DB
//parâmetro de entrada: instancia da MATERIAL_GENETICO
    public void update(MATERIAL_GENETICO materiais) {
        new RepositorioMATERIAL_GENETICO.UpdateAsyncTask(dao).execute(materiais);
    }

    //apaga uma instância da MATERIAL_GENETICO no DB
//parâmetro de entrada: instancia da MATERIAL_GENETICO
    public void delete(MATERIAL_GENETICO materiais) {
        new RepositorioMATERIAL_GENETICO.DeleteAsyncTask(dao).execute(materiais);
    }


    private static class InsertAsyncTask extends AsyncTask<MATERIAL_GENETICO, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MATERIAL_GENETICO... materiais) {
            dao.insert(materiais[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<MATERIAL_GENETICO, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MATERIAL_GENETICO... materiais) {
            dao.update(materiais[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MATERIAL_GENETICO, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MATERIAL_GENETICO... materiais) {
            dao.delete(materiais[0]);
            return null;
        }
    }
}
