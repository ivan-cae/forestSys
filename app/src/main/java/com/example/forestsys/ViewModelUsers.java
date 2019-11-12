package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelUsers extends AndroidViewModel {
    private RepositorioUsers repositorioUsers;
    private LiveData<List<ClasseUsers>> listaUsers;
    private LiveData<ClasseUsers> Consulta;

    public ViewModelUsers(@NonNull Application application) {
        super(application);
        repositorioUsers = new RepositorioUsers(application);
        listaUsers = repositorioUsers.getTodosUsers();
    }

    public void insert(ClasseUsers classeUsers) {
        repositorioUsers.insert(classeUsers);
    }

    public void update(ClasseUsers classeUsers) {
        repositorioUsers.update(classeUsers);
    }

    public void delete(ClasseUsers classeUsers) {
        repositorioUsers.delete(classeUsers);
    }

    public ClasseUsers consulta(String login, String senha){
        return repositorioUsers.valida(login, senha);
    }

    public ClasseUsers consultaAdmin(String login, String senha){
        return repositorioUsers.validaAdmin(login, senha);
    }

    public ClasseUsers consultaLogin(String login){
        return repositorioUsers.validaLogin(login);
    }

    public ClasseUsers consultaMatricula(String matricula){
        return repositorioUsers. validaMatricula(matricula);
    }


}