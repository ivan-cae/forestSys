package com.example.forestsys;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelOs extends AndroidViewModel {
    
    private RepositorioOs repositorioOs;
    private LiveData<List<ClasseOs>> listaOs;
    private LiveData<ClasseOs> Consulta;

    public ViewModelOs(@NonNull Application application) {
        super(application);
        repositorioOs = new RepositorioOs(application);
        listaOs = repositorioOs.getTodasOs();
    }

    public void insert(ClasseOs ClasseOs) {
        repositorioOs.insert(ClasseOs);
    }

    public void update(ClasseOs ClasseOs) {
        repositorioOs.update(ClasseOs);
    }

    public void delete(ClasseOs ClasseOs) {
        repositorioOs.delete(ClasseOs);
    }

    public LiveData<List<ClasseOs>> getTodasOS() {
        return listaOs;
    }

    public LiveData<ClasseOs> getOs(int i){
        return repositorioOs.getOs(i);
    }
}