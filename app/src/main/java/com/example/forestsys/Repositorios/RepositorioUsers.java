package com.example.forestsys.Repositorios;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.GGF_USUARIOS;

import java.util.List;

public class RepositorioUsers {

    private DAO dao;
    private List<GGF_USUARIOS> users;

    public RepositorioUsers(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        users = dao.todosUsers();
    }

    //retorna uma instância da GGF_USUARIOS
//parâmetro de entrada: id inteiro para busca na tabela GGF_USUARIOS
    public GGF_USUARIOS getUser(int id) {
        return dao.selecionaUser(id);
    }


    //retorna uma lista com todos os itens cadastrados na tabela GGF_USUARIOS
    public List<GGF_USUARIOS>getTodosUsers() {
        return users;
    }

    //Verifica se há um User cadastrado com um determinado login e senha e o retorna
    //parâmetros de entrada: login e senha para validação
    public GGF_USUARIOS valida(String login, String senha){
        return dao.valida(login, senha);
    }

    //Verifica se já existe um User com um determinado login cadastrado e o retorna
    //parâmetros de entrada: login para validação
    public LiveData<GGF_USUARIOS> validaLogin(String login){
        return dao.validaLogin(login);
    }

    public List<GGF_USUARIOS> listaUsuarios(){return dao.listaUsuarios();}

    //inclui uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void insert(GGF_USUARIOS GGFUSUARIOS) {
        new InsertAsyncTask(dao).execute(GGFUSUARIOS);
    }

    //atualiza uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void update(GGF_USUARIOS GGFUSUARIOS) {
        new UpdateAsyncTask(dao).execute(GGFUSUARIOS);
    }

    //apaga uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void delete(GGF_USUARIOS GGFUSUARIOS) {
        new DeleteAsyncTask(dao).execute(GGFUSUARIOS);
    }


    private static class InsertAsyncTask extends AsyncTask<GGF_USUARIOS, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GGF_USUARIOS... users) {
            dao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<GGF_USUARIOS, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GGF_USUARIOS... users) {
            dao.update(users[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<GGF_USUARIOS, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GGF_USUARIOS... users) {
            dao.delete(users[0]);
            return null;
        }
    }
}