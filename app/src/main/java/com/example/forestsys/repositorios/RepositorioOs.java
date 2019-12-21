package com.example.forestsys.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.classes.ClasseOs;

import java.util.List;

public class RepositorioOs {
    private DAO dao;
    private LiveData<List<ClasseOs>> os;
    private List <ClasseOs> listaOs;

    public RepositorioOs(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        os = dao.todasOs();
    }

    //retorna uma instância da ClasseOs
//parâmetro de entrada: id inteiro para busca na tabela ClasseOs
    public LiveData<ClasseOs> getOs(int id) {
        return dao.selecionaOs(id);
    }

    //retorna uma lista com todos os itens cadastrados na tabela ClasseOs
    public LiveData<List<ClasseOs>> getTodasOs() {
        return os;
    }

    //retorna uma lista do tipo List com todos os itens cadastrados na tabela ClasseOs
    public List<ClasseOs> getListaOs(){return dao.selecionaListaOs();}

    //inclui uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void insert(ClasseOs classeOs) {
        new RepositorioOs.InsertAsyncTask(dao).execute(classeOs);
    }

    //atualiza uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void update(ClasseOs classeOs) {
        new RepositorioOs.UpdateAsyncTask(dao).execute(classeOs);
    }

    //apaga uma instância da ClasseOs no DB
//parâmetro de entrada: instancia da ClasseOs
    public void delete(ClasseOs classeOs) {
        new RepositorioOs.DeleteAsyncTask(dao).execute(classeOs);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.insert(os[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.update(os[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<ClasseOs, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseOs... os) {
            dao.delete(os[0]);
            return null;
        }
    }
}