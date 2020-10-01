package com.example.forestsys.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.Classes.IMPLEMENTOS;
import com.example.forestsys.Repositorios.RepositorioImplementos;

import java.util.List;

public class ViewModelImplementos extends AndroidViewModel {
    private RepositorioImplementos repositorioImplementos;
    private LiveData<List<IMPLEMENTOS>> listaImplementos;
    private IMPLEMENTOS Consulta;

    public ViewModelImplementos(@NonNull Application application) {
        super(application);
        repositorioImplementos = new RepositorioImplementos(application);
        listaImplementos = repositorioImplementos.getImplementos();
    }

    public IMPLEMENTOS selecionaImplemento(int id){
        return repositorioImplementos.getImplemento(id);
    }
    //inclui uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void insert(IMPLEMENTOS implementos) {
        repositorioImplementos.insert(implementos);
    }

    //atualiza uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void update(IMPLEMENTOS implementos) {
        repositorioImplementos.update(implementos);
    }

    //apaga uma instância da IMPLEMENTOS no DB
//parâmetro de entrada: instancia da IMPLEMENTOS
    public void delete(IMPLEMENTOS implementos) {
        repositorioImplementos.delete(implementos);
    }
}
