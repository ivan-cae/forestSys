package com.example.forestsys;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.forestsys.classes.ATIVIDADES;
import com.example.forestsys.classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.classes.CADASTRO_FLORESTAL;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.ClasseUpdate;
import com.example.forestsys.classes.ESPACAMENTOS;
import com.example.forestsys.classes.GEO_REGIONAIS;
import com.example.forestsys.classes.GEO_SETORES;
import com.example.forestsys.classes.GGF_DEPARTAMENTOS;
import com.example.forestsys.classes.GGF_FUNCOES;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.classes.INSUMOS;
import com.example.forestsys.classes.INSUMO_ATIVIDADES;
import com.example.forestsys.classes.LOG;
import com.example.forestsys.classes.MANEJO;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.MATERIAL_GENETICO;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.PRESTADORES;

import java.sql.Time;
import java.util.Date;


@Database(entities = {INDICADORES_SUBSOLAGEM.class, AVAL_PONTO_SUBSOLAGEM.class, AVAL_SUBSOLAGEM.class, OPERADORES.class, CALIBRAGEM_SUBSOLAGEM.class, MAQUINA_IMPLEMENTO.class,
        O_S_ATIVIDADE_INSUMOS.class, ATIVIDADE_INDICADORES.class, ATIVIDADES.class, CADASTRO_FLORESTAL.class, ClasseUpdate.class, ESPACAMENTOS.class, GEO_REGIONAIS.class,
        GEO_SETORES.class, GGF_DEPARTAMENTOS.class, GGF_FUNCOES.class, GGF_USUARIOS.class, IMPLEMENTOS.class, INSUMO_ATIVIDADES.class,
        INSUMOS.class, LOG.class, MANEJO.class, MAQUINAS.class, MATERIAL_GENETICO.class,
        O_S_ATIVIDADE_INSUMOS_DIA.class, O_S_ATIVIDADES.class, O_S_ATIVIDADES_DIA.class,
        PRESTADORES.class}, version = 1, exportSchema = false)


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
            new InsereDados1(instance).execute();
        }
    };

    private static class InsereDados1 extends AsyncTask<Void, Void, Void> {
        private DAO auxDao;

        private InsereDados1(BaseDeDados db) {
            auxDao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //inserir usuario
            auxDao.insert(new GGF_USUARIOS(1, "a", "a"));
            auxDao.insert(new GGF_USUARIOS(2, "usuario 1", "a"));
            auxDao.insert(new GGF_USUARIOS(3, "usuario 2", "a"));

            auxDao.insert(new O_S_ATIVIDADES(1, 1,1,"Talhao1",1,1,1,
                    1,null,1, 1, 1, 1,"",
                    (DateFormat.format("dd-MM-yyyy", new Date()).toString()),null,1));


            auxDao.insert(new O_S_ATIVIDADES(2, 2,2,"Talhao2",2, 2,2,
                    1,null,2, 2,2,2," ",
                    (DateFormat.format("dd-MM-yyyy", new Date()).toString()), null, 2));


            auxDao.insert(new MAQUINAS(1, "Maquina 1", 1));
            auxDao.insert(new MAQUINAS(2, "Maquina 2", 1));
            auxDao.insert(new MAQUINAS(3, "Maquina 3", 1));

            auxDao.insert(new IMPLEMENTOS(1, "Implemento 1", 1));
            auxDao.insert(new IMPLEMENTOS(2, "Implemento 2", 1));
            auxDao.insert(new IMPLEMENTOS(3, "Implemento 3", 1));

            auxDao.insert(new PRESTADORES(1, "Prestador 1", 1));
            auxDao.insert(new PRESTADORES(2, "Prestador 2", 1));
            auxDao.insert(new PRESTADORES(3, "Prestador 3", 1));

            auxDao.insert(new OPERADORES(1, "Operador 1", 1));
            auxDao.insert(new OPERADORES(2, "Operador 2", 1));
            auxDao.insert(new OPERADORES(3, "Operador 3", 1));

            auxDao.insert(new MAQUINA_IMPLEMENTO(1,1,1));

            return null;
        }
    }}
