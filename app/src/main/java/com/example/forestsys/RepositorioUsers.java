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

    //retorna uma instância da ClasseUsers
//parâmetro de entrada: id inteiro para busca na tabela ClasseUsers
    public LiveData<ClasseUsers> getUser(int id) {
        return dao.selecionaUser(id);
    }


    //retorna uma lista com todos os itens cadastrados na tabela ClasseUsers
    public LiveData<List<ClasseUsers>>getTodosUsers() {
        return users;
    }

    //Verifica se há um User cadastrado com um determinado login e senha e o retorna
    //parâmetros de entrada: login e senha para validação
    public ClasseUsers valida(String login, String senha){
        return dao.valida(login, senha);
    }

    //Verifica se há um User cadastrado como ADMIN com um determinado login e senha e o retorna
    //parâmetros de entrada: login e senha para validação
    public ClasseUsers validaAdmin(String login, String senha){
        return dao.validaAdmin(login, senha);
    }

    //Verifica se já existe um User com um determinado login cadastrado e o retorna
    //parâmetros de entrada: login para validação
    public ClasseUsers validaLogin(String login){
        return dao.validaLogin(login);
    }

    //Verifica se já existe um User com uma determinada matricula cadastrada e o retorna
    //parâmetros de entrada: matricuça para validação
    public ClasseUsers validaMatricula(String matricula){
        return dao.validaMatricula(matricula);
    }

    //inclui uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
    public void insert(ClasseUsers classeUsers) {
        new InsertAsyncTask(dao).execute(classeUsers);
    }

    //atualiza uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
    public void update(ClasseUsers classeUsers) {
        new UpdateAsyncTask(dao).execute(classeUsers);
    }

    //apaga uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
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