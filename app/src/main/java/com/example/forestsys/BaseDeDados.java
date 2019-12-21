package com.example.forestsys;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.forestsys.classes.ATIVIDADES;
import com.example.forestsys.classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.classes.CADASTRO_FLORESTAL;
import com.example.forestsys.classes.ClasseEncarregados;
import com.example.forestsys.classes.ClasseFazenda;
import com.example.forestsys.classes.ClasseOs;
import com.example.forestsys.classes.ClasseOsInsumos;
import com.example.forestsys.classes.ClasseUpdate;
import com.example.forestsys.classes.ESPACAMENTOS;
import com.example.forestsys.classes.GEO_REGIONAIS;
import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.classes.GGF_DEPARTAMENTOS;
import com.example.forestsys.classes.GGF_FUNCOES;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.INSUMOS;
import com.example.forestsys.classes.INSUMO_ATIVIDADES;
import com.example.forestsys.classes.LOG;
import com.example.forestsys.classes.MANEJO;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.MAQUINAS_IMPLEMENTO;
import com.example.forestsys.classes.MATERIAL_GENETICO;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.classes.TPRODUTO;
import com.example.forestsys.classes.USUARIO;


@Database(entities = {ATIVIDADE_INDICADORES.class, ATIVIDADES.class, CADASTRO_FLORESTAL.class, ClasseEncarregados.class,
        ClasseFazenda.class, ClasseOs.class, ClasseOsInsumos.class, ClasseUpdate.class, ESPACAMENTOS.class, GEO_REGIONAIS.class,
        GEO_SETORES.class, GGF_DEPARTAMENTOS.class, GGF_FUNCOES.class, GGF_USUARIOS.class, IMPLEMENTOS.class, INSUMO_ATIVIDADES.class,
        INSUMOS.class, LOG.class, MANEJO.class, MAQUINAS.class, MAQUINAS_IMPLEMENTO.class, MATERIAL_GENETICO.class,
        O_S_ATIVIDADE_INSUMOS_DIA.class, O_S_ATIVIDADES.class, O_S_ATIVIDADES_DIA.class, OPERADORES.class,
        PRESTADORES.class, TPRODUTO.class, USUARIO.class}, version = 1, exportSchema = false)


public abstract class BaseDeDados extends RoomDatabase {

    private static BaseDeDados instance;

    public abstract DAO dao();

    public static synchronized BaseDeDados getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BaseDeDados.class, "forestyapp")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    //Método usado para popular o DB programaticamente
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsereDados(instance).execute();
        }
    };

    private static class InsereDados extends AsyncTask<Void, Void, Void> {
        private DAO auxDao;

        private InsereDados(BaseDeDados db) {
            auxDao = db.dao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //inserir usuario
            auxDao.insert(new GGF_USUARIOS(1, "a", "a"));
            return null;
        }
    }
}