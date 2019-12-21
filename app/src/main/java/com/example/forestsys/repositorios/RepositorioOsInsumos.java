package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.ClasseOsInsumos;

import java.util.List;

public class RepositorioOsInsumos {
    private DAO dao;
    private LiveData<List<ClasseOsInsumos>> osInsumos;

    public RepositorioOsInsumos(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        osInsumos = dao.todosInsumos();
    }

    //retorna uma instância da ClasseOsInsumos
//parâmetro de entrada: id inteiro para busca na tabela ClasseOsInsumos
    public LiveData<ClasseOsInsumos> getInsumos(int id) {
        return dao.selecionaInsumo(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseOsInsumos
    public LiveData<List<ClasseOsInsumos>> getTodosOsInsumos() {
        return osInsumos;
    }

    //inclui uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void insert(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.InsertAsyncTask(dao).execute(classeOsInsumos);
    }
    //atualiza uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void update(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.UpdateAsyncTask(dao).execute(classeOsInsumos);
    }
    //apaga uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void delete(ClasseOsInsumos classeOsInsumos) {
        new RepositorioOsInsumos.DeleteAsyncTask(dao).execute(classeOsInsumos);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.insert(osInsumos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.update(osInsumos[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseOsInsumos, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOsInsumos... osInsumos) {
            dao.delete(osInsumos[0]);
            return null;
        }
    }
}