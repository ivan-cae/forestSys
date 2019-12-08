package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelMaquinas extends AndroidViewModel {
    
    private RepositorioMaquinas repositorioMaquinas;
    private LiveData<List<ClasseMaquinas>> listaMaquinas;
    private LiveData<ClasseMaquinas> Consulta;

    public ViewModelMaquinas(@NonNull Application application) {
        super(application);
        repositorioMaquinas = new RepositorioMaquinas(application);
        listaMaquinas = repositorioMaquinas.getTodasMaquinas();
    }

    //inclui uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void insert(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.insert(classeMaquinas);
    }

    //atualiza uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void update(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.update(classeMaquinas);
    }

    //apaga uma instância da ClasseMaquinas no DB
//parâmetro de entrada: instancia da ClasseMaquinas
    public void delete(ClasseMaquinas classeMaquinas) {
        repositorioMaquinas.delete(classeMaquinas);
    }
}
