package com.example.forestsys;
import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import java.util.List;

public class RepositorioUsers {

    private UsersDAO dao;
    private LiveData<List<Users>> users;

    public RepositorioUsers(Application application) {
        BaseUsers baseUsers = BaseUsers.getInstance(application);
        dao = baseUsers.usersDAO();
        users = dao.todosUsers();
    }

    public LiveData<Users> getUser(int id) {
        return dao.selecionaTodos(id);
    }

    public LiveData<List<Users>>getTodosUsers() {
        return users;
    }

    public Users valida(String login, String senha){
        return dao.valida(login, senha);
    }

    public Users validaAdmin(String login, String senha){
        return dao.validaAdmin(login, senha);
    }

    public Users validaLogin(String login){
        return dao.validaLogin(login);
    }

    public Users validaMatricula(String matricula){
        return dao.validaMatricula(matricula);
    }

    public void insert(Users users) {
        new InsertAsyncTask(dao).execute(users);
    }

    public void update(Users users) {
        new UpdateOSAsync(dao).execute(users);
    }

    public void delete(Users users) {
        new DeleteOSAsync(dao).execute(users);
    }


    private static class InsertAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDAO dao;

        private InsertAsyncTask(UsersDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            dao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateOSAsync extends AsyncTask<Users, Void, Void> {
        private UsersDAO dao;

        private UpdateOSAsync(UsersDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            dao.update(users[0]);
            return null;
        }
    }

    private static class DeleteOSAsync extends AsyncTask<Users, Void, Void> {
        private UsersDAO dao;

        private DeleteOSAsync(UsersDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            dao.delete(users[0]);
            return null;
        }
    }
}