package com.example.forestsys.Repositorios;

import android.app.Application;
import android.os.AsyncTask;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;

import java.util.List;

public class RepositorioAVAL_SUBSOLAGEM {
    private DAO dao;
    private List<AVAL_SUBSOLAGEM> aval_subsolagens;

    public RepositorioAVAL_SUBSOLAGEM(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        aval_subsolagens = dao.listaAvalPontoSubsolagem();
    }

    //retorna uma instância da AVAL_SUBSOLAGEM
//parâmetro de entrada: id inteiro para busca na tabela AVAL_SUBSOLAGEM
    public AVAL_SUBSOLAGEM selecionaMaquina(int id) {
        return dao.selecionaAvalSubsolagem(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela AVAL_SUBSOLAGEM
    public List<AVAL_SUBSOLAGEM> listaMaquinas() {
        return dao.listaAvalPontoSubsolagem();
    }

    //retorna uma lista com todos os itens cadastrados na tabela AVAL_SUBSOLAGEM
    public List<AVAL_SUBSOLAGEM> todasMaquinas() {
        return aval_subsolagens;
    }


    //inclui uma instância da AVAL_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da AVAL_SUBSOLAGEM
    public void insert(AVAL_SUBSOLAGEM aval_subsolagens) {
        new RepositorioAVAL_SUBSOLAGEM.InsertAsyncTask(dao).execute(aval_subsolagens);
    }

    //atualiza uma instância da AVAL_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da AVAL_SUBSOLAGEM
    public void update(AVAL_SUBSOLAGEM aval_subsolagens) {
        new RepositorioAVAL_SUBSOLAGEM.UpdateAsyncTask(dao).execute(aval_subsolagens);
    }

    //apaga uma instância da AVAL_SUBSOLAGEM no DB
//parâmetro de entrada: instancia da AVAL_SUBSOLAGEM
    public void delete(AVAL_SUBSOLAGEM aval_subsolagens) {
        new RepositorioAVAL_SUBSOLAGEM.DeleteAsyncTask(dao).execute(aval_subsolagens);
    }


    private static class InsertAsyncTask extends AsyncTask<AVAL_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(AVAL_SUBSOLAGEM... aval_subsolagens) {
            dao.insert(aval_subsolagens[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<AVAL_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(AVAL_SUBSOLAGEM... aval_subsolagens) {
            dao.update(aval_subsolagens[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<AVAL_SUBSOLAGEM, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(AVAL_SUBSOLAGEM... aval_subsolagens) {
            dao.delete(aval_subsolagens[0]);
            return null;
        }
    }
}
