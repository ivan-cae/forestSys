package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS;

import java.util.List;

public class RepositorioO_S_ATIVIDADE_INSUMOS {
    private DAO dao;
    private LiveData<List<O_S_ATIVIDADE_INSUMOS>> OsAtividadeInsumos;

    public RepositorioO_S_ATIVIDADE_INSUMOS(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        OsAtividadeInsumos = dao.todosOsAtividadeInsumos();
    }

    //retorna uma instância da O_S_ATIVIDADE_INSUMOS
//parâmetro de entrada: id inteiro para busca na tabela O_S_ATIVIDADE_INSUMOS
    public List<O_S_ATIVIDADE_INSUMOS>getOsAtividadeInsumo(int idProg, int idInsumo) {
        return dao.selecionaOsAtividadeInsumo(idProg, idInsumo);
    }

    //retorna uma lista com todos os itens cadastrados na tabela O_S_ATIVIDADE_INSUMOS
    public LiveData<List<O_S_ATIVIDADE_INSUMOS>> getTodosOsAtividadeInsumos() {
        return OsAtividadeInsumos;
    }



    //inclui uma instância da Classe O_S_ATIVIDADE_INSUMOS no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS
    public void insert(O_S_ATIVIDADE_INSUMOS OsAtividadeInsumo) {
        new RepositorioO_S_ATIVIDADE_INSUMOS.InsertAsyncTask(dao).execute(OsAtividadeInsumo);
    }

    //atualiza uma instância da Classe O_S_ATIVIDADE_INSUMOS no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS
    public void update(O_S_ATIVIDADE_INSUMOS OsAtividadeInsumo) {
        new RepositorioO_S_ATIVIDADE_INSUMOS.UpdateAsyncTask(dao).execute(OsAtividadeInsumo);
    }

    //apaga uma instância da Classe O_S_ATIVIDADE_INSUMOS no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS
    public void delete(O_S_ATIVIDADE_INSUMOS OsAtividadeInsumo) {
        new RepositorioO_S_ATIVIDADE_INSUMOS.DeleteAsyncTask(dao).execute(OsAtividadeInsumo);
    }


    private static class InsertAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS...OsAtividadeInsumo) {
            dao.insert(OsAtividadeInsumo[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS...OsAtividadeInsumo) {
            dao.update(OsAtividadeInsumo[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS...OsAtividadeInsumo) {
            dao.delete(OsAtividadeInsumo[0]);
            return null;
        }
    }
}
