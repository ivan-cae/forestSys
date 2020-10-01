package com.example.forestsys.Repositorios;
import android.app.Application;
import android.os.AsyncTask;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.ESPACAMENTOS;
import java.util.List;

public class RepositorioESPACAMENTOS {
    private DAO dao;
    private List<ESPACAMENTOS> espacamentos;

    public RepositorioESPACAMENTOS(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        espacamentos = dao.listaEspacamentos();
    }

    //retorna uma instância da ESPACAMENTOS
//parâmetro de entrada: id inteiro para busca na tabela ESPACAMENTOS
    public ESPACAMENTOS selecionaEspacamento(int id) {
        return dao.selecionaEspacamento(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ESPACAMENTOS
    public List<ESPACAMENTOS> listaEspacamentos() {
        return dao.listaEspacamentos();
    }

    //inclui uma instância da ESPACAMENTOS no DB
//parâmetro de entrada: instancia da ESPACAMENTOS
    public void insert(ESPACAMENTOS espacamentos) {
        new RepositorioESPACAMENTOS.InsertAsyncTask(dao).execute(espacamentos);
    }

    //atualiza uma instância da ESPACAMENTOS no DB
//parâmetro de entrada: instancia da ESPACAMENTOS
    public void update(ESPACAMENTOS espacamentos) {
        new RepositorioESPACAMENTOS.UpdateAsyncTask(dao).execute(espacamentos);
    }

    //apaga uma instância da ESPACAMENTOS no DB
//parâmetro de entrada: instancia da ESPACAMENTOS
    public void delete(ESPACAMENTOS espacamentos) {
        new RepositorioESPACAMENTOS.DeleteAsyncTask(dao).execute(espacamentos);
    }


    private static class InsertAsyncTask extends AsyncTask<ESPACAMENTOS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ESPACAMENTOS... espacamentos) {
            dao.insert(espacamentos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ESPACAMENTOS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ESPACAMENTOS... espacamentos) {
            dao.update(espacamentos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ESPACAMENTOS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ESPACAMENTOS... espacamentos) {
            dao.delete(espacamentos[0]);
            return null;
        }
    }
}