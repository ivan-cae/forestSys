package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private RepositorioUsers repositorioUsers;
    private LiveData<List<Users>> listaUsers;
    private LiveData<Users> Consulta;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        repositorioUsers = new RepositorioUsers(application);
        listaUsers = repositorioUsers.getTodosUsers();
    }

    public void insert(Users users) {
        repositorioUsers.insert(users);
    }

    public void update(Users users) {
        repositorioUsers.update(users);
    }

    public void delete(Users users) {
        repositorioUsers.delete(users);
    }

    public Users consulta(String login, String senha){
        return repositorioUsers.valida(login, senha);
    }

    public Users consultaAdmin(String login, String senha){
        return repositorioUsers.validaAdmin(login, senha);
    }

    public Users consultaLogin(String login){
        return repositorioUsers.validaLogin(login);
    }

    public Users consultaMatricula(String matricula){
        return repositorioUsers. validaMatricula(matricula);
    }
}