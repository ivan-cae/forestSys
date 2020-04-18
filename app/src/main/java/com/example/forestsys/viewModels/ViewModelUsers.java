package com.example.forestsys.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.repositorios.RepositorioUsers;

import java.util.List;

public class ViewModelUsers extends AndroidViewModel {
    private RepositorioUsers repositorioUsers;
    private LiveData<List<GGF_USUARIOS>> listaUsers;

    public ViewModelUsers(@NonNull Application application) {
        super(application);
        repositorioUsers = new RepositorioUsers(application);
        listaUsers = repositorioUsers.getTodosUsers();
    }

    //inclui uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void insert(GGF_USUARIOS GGFUSUARIOS) {
        repositorioUsers.insert(GGFUSUARIOS);
    }

    //atualiza uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void update(GGF_USUARIOS GGFUSUARIOS) {
        repositorioUsers.update(GGFUSUARIOS);
    }

    //apaga uma instância da GGF_USUARIOS no DB
//parâmetro de entrada: instancia da GGF_USUARIOS
    public void delete(GGF_USUARIOS GGFUSUARIOS) {
        repositorioUsers.delete(GGFUSUARIOS);
    }

    //retorna uma instância da GGF_USUARIOS
//parâmetro de entrada: id e senha para busca na tabela GGF_USUARIOS
    public GGF_USUARIOS consulta(String login, String senha){
        return repositorioUsers.valida(login, senha);
    }

    //Verifica se já existe um User com um determinado login cadastrado e o retorna
    //parâmetros de entrada: login para validação
    public LiveData<GGF_USUARIOS> consultaLogin(String login){
        return repositorioUsers.validaLogin(login);
    }
}