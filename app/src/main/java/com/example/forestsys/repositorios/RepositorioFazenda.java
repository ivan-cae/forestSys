package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.ClasseFazenda;

import java.util.List;

public class RepositorioFazenda {
    private DAO dao;
    private LiveData<List<ClasseFazenda>> fazendas;

    public RepositorioFazenda(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        fazendas = dao.todasFazendas();
    }

    //retorna uma instância da ClasseFazenda
    //parâmetro de entrada: id inteiro para busca na tabela ClasseFazenda
    public LiveData<ClasseFazenda> getFazenda(int id) {
        return dao.selecionaFazenda(id);
    }


    //retorna uma lista com todos os itens cadastrados na tabela ClasseFazenda
    public LiveData<List<ClasseFazenda>> getTodasFazendas() {
        return fazendas;
    }

    //inclui uma instância da ClasseFazenda no DB
    //parâmetro de entrada: instancia da ClasseFazenda
    public void insert(ClasseFazenda classeFazenda) {
        new RepositorioFazenda.InsertAsyncTask(dao).execute(classeFazenda);
    }

    //atualiza uma instância da ClasseFazenda no DB
    //parâmetro de entrada: instancia da ClasseFazenda
    public void update(ClasseFazenda classeFazenda) {
        new UpdateAsyncTask(dao).execute(classeFazenda);
    }


    //apaga uma instância da ClasseFazenda no DB
    //parâmetro de entrada: instancia da ClasseFazenda
    public void delete(ClasseFazenda classeFazenda) {
        new DeleteAsyncTask(dao).execute(classeFazenda);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.insert(classeFazenda[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.update(classeFazenda[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasseFazenda, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseFazenda... classeFazenda) {
            dao.delete(classeFazenda[0]);
            return null;
        }
    }
}