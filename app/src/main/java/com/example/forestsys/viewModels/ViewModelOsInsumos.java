package com.example.forestsys.viewModels;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forestsys.classes.ClasseOsInsumos;
import com.example.forestsys.repositorios.RepositorioOsInsumos;

import java.util.List;

public class ViewModelOsInsumos extends AndroidViewModel {
    private RepositorioOsInsumos repositorioOsInsumos;
    private LiveData<List<ClasseOsInsumos>> listaOsInsumos;
    private LiveData<ClasseOsInsumos> Consulta;

    public ViewModelOsInsumos(@NonNull Application application) {
        super(application);
        repositorioOsInsumos = new RepositorioOsInsumos(application);
        listaOsInsumos = repositorioOsInsumos.getTodosOsInsumos();
    }

    //inclui uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void insert(ClasseOsInsumos classeOsInsumos) {
        repositorioOsInsumos.insert( classeOsInsumos);
    }

    //atualiza uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void update(ClasseOsInsumos classeOsInsumos) {
        repositorioOsInsumos.update(classeOsInsumos);
    }

    //apaga uma instância da ClasseOsInsumos no DB
//parâmetro de entrada: instancia da ClasseOsInsumos
    public void delete(ClasseOsInsumos classeOsInsumos) {
        repositorioOsInsumos.delete(classeOsInsumos);
    }
}