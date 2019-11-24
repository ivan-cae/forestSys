package com.example.forestsys;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class RepositorioSetor{
    private DAO dao;
    private LiveData<List<ClasseSetor>> setores;

    public RepositorioSetor(Application application) {
        BaseDeDados baseDeDados = BaseDeDados.getInstance(application);
        dao = baseDeDados.dao();
        setores = dao.todosSetores();
    }

    public ClasseSetor getSetor(int id) {
        return dao.selecionaSetor(id);
    }

    public LiveData<List<ClasseSetor>> getTodosSetores() {
        return setores;
    }


    public void insert(ClasseSetor classeSetor) {
        new RepositorioSetor.InsertAsyncTask(dao).execute(classeSetor);
    }

    public void update(ClasseSetor classeSetor) {
        new UpdateAsyncTask(dao).execute(classeSetor);
    }

    public void delete(ClasseSetor classeSetor) {
        new DeleteAsyncTask(dao).execute(classeSetor);
    }


    private static class InsertAsyncTask extends AsyncTask<ClasseSetor, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseSetor... classeSetor) {
            dao.insert(classeSetor[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasseSetor, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseSetor... classeSetor) {
            dao.update(classeSetor[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasseSetor, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClasseSetor... classeSetor) {
            dao.delete(classeSetor[0]);
            return null;
        }
    }
}