package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelSetor extends AndroidViewModel {
    private RepositorioSetor repositorioSetor;
    private LiveData<List<ClasseSetor>> listaSetor;
    private LiveData<ClasseSetor> Consulta;

    public ViewModelSetor(@NonNull Application application) {
        super(application);
        repositorioSetor = new RepositorioSetor(application);
        listaSetor = repositorioSetor.getTodosSetores();
    }

    //retorna uma instância da ClasseSetor
//parâmetro de entrada: id inteiro para busca na tabela ClasseSetor
    public ClasseSetor consulta (int id){return repositorioSetor.getSetor(id);}

    //inclui uma instância da ClasseSetor no DB
//parâmetro de entrada: instancia da ClasseSetor
    public void insert(ClasseSetor classeSetor) {
        repositorioSetor.insert(classeSetor);
    }

    //atualiza uma instância da ClasseSetor no DB
//parâmetro de entrada: instancia da ClasseSetor
    public void update(ClasseSetor classeSetor) {
        repositorioSetor.update(classeSetor);
    }

    //apaga uma instância da ClasseSetor no DB
//parâmetro de entrada: instancia da ClasseSetor
    public void delete(ClasseSetor classeSetor) {
        repositorioSetor.delete(classeSetor);
    }
}