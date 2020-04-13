package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.OPERADORES;

import java.util.List;

public class RepositorioOPERADORES {
    private DAO dao;
    private List<OPERADORES> operadores;

    public RepositorioOPERADORES(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        operadores = dao.todosOperadores();
    }

    //retorna uma instância da OPERADORES
//parâmetro de entrada: id inteiro para busca na tabela OPERADORES
    public OPERADORES selecionaOperador(int id) {
        return dao.selecionaOperador(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela OPERADORES
    public List<OPERADORES> listaOperadores() {
        return dao.todosOperadores();
    }


    //inclui uma instância da OPERADORES no DB
//parâmetro de entrada: instancia da OPERADORES
    public void insert(OPERADORES operadores) {
        new RepositorioOPERADORES.InsertAsyncTask(dao).execute(operadores);
    }

    //atualiza uma instância da OPERADORES no DB
//parâmetro de entrada: instancia da OPERADORES
    public void update(OPERADORES operadores) {
        new RepositorioOPERADORES.UpdateAsyncTask(dao).execute(operadores);
    }

    //apaga uma instância da OPERADORES no DB
//parâmetro de entrada: instancia da OPERADORES
    public void delete(OPERADORES operadores) {
        new RepositorioOPERADORES.DeleteAsyncTask(dao).execute(operadores);
    }


    private static class InsertAsyncTask extends AsyncTask<OPERADORES, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OPERADORES... operadores) {
            dao.insert(operadores[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<OPERADORES, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OPERADORES... operadores) {
            dao.update(operadores[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<OPERADORES, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OPERADORES... operadores) {
            dao.delete(operadores[0]);
            return null;
        }
    }
}
