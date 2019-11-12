package com.example.forestsys;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

public class RepositorioUsers {

    private DAO dao;
    private LiveData<List<ClasseUsers>> users;

    public RepositorioUsers(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        users = dao.todosUsers();
    }

    public LiveData<ClasseUsers> getUser(int id) {
        return dao.selecionaUser(id);
    }

    public LiveData<List<ClasseUsers>>getTodosUsers() {
        return users;
    }

    public ClasseUsers valida(String login, String senha){
        return dao.valida(login, senha);
    }

    public ClasseUsers validaAdmin(String login, String senha){
        return dao.validaAdmin(login, senha);
    }

    public ClasseUsers validaLogin(String login){
        return dao.validaLogin(login);
    }

    public ClasseUsers validaMatricula(String matricula){
        return dao.validaMatricula(matricula);
    }

    public void insert(ClasseUsers classeUsers) {
        new InsertAsyncTask(dao).execute(classeUsers);
    }

    public void update(ClasseUsers classeUsers) {
        new UpdateAsyncTask(dao).execute(classeUsers);
    }

    public void delete(ClasseUsers classeUsers) {
        new DeleteAsyncTask(dao).execute(classeUsers);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseUsers, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUsers... users) {
            dao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseUsers, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUsers... users) {
            dao.update(users[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasseUsers, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseUsers... users) {
            dao.delete(users[0]);
            return null;
        }
    }
}