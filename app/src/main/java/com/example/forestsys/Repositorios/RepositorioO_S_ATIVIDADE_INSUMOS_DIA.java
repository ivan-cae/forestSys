package com.example.forestsys.Repositorios;

import android.os.AsyncTask;

import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;

import java.util.List;

public class RepositorioO_S_ATIVIDADE_INSUMOS_DIA {
    private DAO dao;
    private List<O_S_ATIVIDADE_INSUMOS_DIA> OsAtividadeInsumoDias;

   //retorna uma instância da O_S_ATIVIDADE_INSUMOS_DIA
//parâmetro de entrada: id inteiro para busca na tabela O_S_ATIVIDADE_INSUMOS_DIA
    public List<O_S_ATIVIDADE_INSUMOS_DIA>listaOsAtividadeInsumosDia(int idProg, String data) {
        return dao.listaOsAtividadeInsumosDia(idProg,data);
    }

        //inclui uma instância da Classe O_S_ATIVIDADE_INSUMOS_DIA no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS_DIA
    public void insert(O_S_ATIVIDADE_INSUMOS_DIA OsAtividadeInsumoDia) {
        new RepositorioO_S_ATIVIDADE_INSUMOS_DIA.InsertAsyncTask(dao).execute(OsAtividadeInsumoDia);
    }

    //atualiza uma instância da Classe O_S_ATIVIDADE_INSUMOS_DIA no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS_DIA
    public void update(O_S_ATIVIDADE_INSUMOS_DIA OsAtividadeInsumoDia) {
        new RepositorioO_S_ATIVIDADE_INSUMOS_DIA.UpdateAsyncTask(dao).execute(OsAtividadeInsumoDia);
    }

    //apaga uma instância da Classe O_S_ATIVIDADE_INSUMOS_DIA no DB
//parâmetro de entrada: instancia da Classe O_S_ATIVIDADE_INSUMOS_DIA
    public void delete(O_S_ATIVIDADE_INSUMOS_DIA OsAtividadeInsumoDia) {
        new RepositorioO_S_ATIVIDADE_INSUMOS_DIA.DeleteAsyncTask(dao).execute(OsAtividadeInsumoDia);
    }


    private static class InsertAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS_DIA, Void, Void> {
        private DAO dao;

        private InsertAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS_DIA...OsAtividadeInsumoDia) {
            dao.insert(OsAtividadeInsumoDia[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS_DIA, Void, Void> {
        private DAO dao;

        private UpdateAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS_DIA...OsAtividadeInsumoDia) {
            dao.update(OsAtividadeInsumoDia[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<O_S_ATIVIDADE_INSUMOS_DIA, Void, Void> {
        private DAO dao;

        private DeleteAsyncTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(O_S_ATIVIDADE_INSUMOS_DIA...OsAtividadeInsumoDia) {
            dao.delete(OsAtividadeInsumoDia[0]);
            return null;
        }
    }
}
