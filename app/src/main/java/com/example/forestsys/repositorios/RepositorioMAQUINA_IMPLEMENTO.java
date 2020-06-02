package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;

import java.util.List;

public class RepositorioMAQUINA_IMPLEMENTO {
    
        private DAO dao;
        private LiveData<List<MAQUINA_IMPLEMENTO>> maquinaImplementos;

        public RepositorioMAQUINA_IMPLEMENTO(Application application) {
            BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
            dao = baseDeDados.dao();
            maquinaImplementos = dao.todosMaquinaImplementos();
        }

        //retorna uma instância da MAQUINA_IMPLEMENTO
//parâmetro de entrada: id inteiro para busca na tabela MAQUINA_IMPLEMENTO
        public MAQUINA_IMPLEMENTO getMaquinaImplemento(int id) {
            return dao.selecionaMaquinaImplemento(id);
        }

        //retorna uma lista com todos os itens cadastrados na tabela MAQUINA_IMPLEMENTO
        public LiveData<List<MAQUINA_IMPLEMENTO>> getTodosMaquinaImplementos() {
            return maquinaImplementos;
        }


        //inclui uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
        public void insert(MAQUINA_IMPLEMENTO maquinaImplementos) {
            new RepositorioMAQUINA_IMPLEMENTO.InsertAsyncTask(dao).execute(maquinaImplementos);
        }

        //atualiza uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
        public void update(MAQUINA_IMPLEMENTO maquinaImplementos) {
            new RepositorioMAQUINA_IMPLEMENTO.UpdateAsyncTask(dao).execute(maquinaImplementos);
        }

        //apaga uma instância da MAQUINA_IMPLEMENTO no DB
//parâmetro de entrada: instancia da MAQUINA_IMPLEMENTO
        public void delete(MAQUINA_IMPLEMENTO maquinaImplementos) {
            new RepositorioMAQUINA_IMPLEMENTO.DeleteAsyncTask(dao).execute(maquinaImplementos);
        }


        private static class InsertAsyncTask extends AsyncTask<MAQUINA_IMPLEMENTO, Void, Void> {
            private DAO dao;

            private InsertAsyncTask(DAO dao) {
                this.dao = dao;
            }

            @Override
            protected Void doInBackground(MAQUINA_IMPLEMENTO... maquinaImplementos) {
                dao.insert(maquinaImplementos[0]);
                return null;
            }
        }

        private static class UpdateAsyncTask extends AsyncTask<MAQUINA_IMPLEMENTO, Void, Void> {
            private DAO dao;

            private UpdateAsyncTask(DAO dao) {
                this.dao = dao;
            }

            @Override
            protected Void doInBackground(MAQUINA_IMPLEMENTO... maquinaImplementos) {
                dao.update(maquinaImplementos[0]);
                return null;
            }
        }

        private static class DeleteAsyncTask extends AsyncTask<MAQUINA_IMPLEMENTO, Void, Void> {
            private DAO dao;

            private DeleteAsyncTask(DAO dao) {
                this.dao = dao;
            }

            @Override
            protected Void doInBackground(MAQUINA_IMPLEMENTO... maquinaImplementos) {
                dao.delete(maquinaImplementos[0]);
                return null;
            }
        }
}