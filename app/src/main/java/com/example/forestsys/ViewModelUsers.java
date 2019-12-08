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

    //inclui uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
    public void insert(ClasseUsers classeUsers) {
        repositorioUsers.insert(classeUsers);
    }

    //atualiza uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
    public void update(ClasseUsers classeUsers) {
        repositorioUsers.update(classeUsers);
    }

    //apaga uma instância da ClasseUsers no DB
//parâmetro de entrada: instancia da ClasseUsers
    public void delete(ClasseUsers classeUsers) {
        repositorioUsers.delete(classeUsers);
    }

    //retorna uma instância da ClasseUsers
//parâmetro de entrada: id e senha para busca na tabela ClasseUsers
    public ClasseUsers consulta(String login, String senha){
        return repositorioUsers.valida(login, senha);
    }

    //Verifica se há um User cadastrado como ADMIN com um determinado login e senha e o retorna
    //parâmetros de entrada: login e senha para validação
    public ClasseUsers consultaAdmin(String login, String senha){
        return repositorioUsers.validaAdmin(login, senha);
    }

    //Verifica se já existe um User com um determinado login cadastrado e o retorna
    //parâmetros de entrada: login para validação
    public ClasseUsers consultaLogin(String login){
        return repositorioUsers.validaLogin(login);
    }

    //Verifica se já existe um User com uma determinada matricula cadastrada e o retorna
    //parâmetros de entrada: matricuça para validação
    public ClasseUsers consultaMatricula(String matricula){
        return repositorioUsers. validaMatricula(matricula);
    }


}