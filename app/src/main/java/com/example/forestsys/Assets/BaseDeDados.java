package com.example.forestsys.Assets;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.GEO_LOCALIZACAO;
import com.example.forestsys.R;
import com.example.forestsys.Classes.ATIVIDADES;
import com.example.forestsys.Classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.ESPACAMENTOS;
import com.example.forestsys.Classes.GEO_REGIONAIS;
import com.example.forestsys.Classes.GEO_SETORES;
import com.example.forestsys.Classes.GGF_DEPARTAMENTOS;
import com.example.forestsys.Classes.GGF_FUNCOES;
import com.example.forestsys.Classes.GGF_USUARIOS;
import com.example.forestsys.Classes.IMPLEMENTOS;
import com.example.forestsys.Classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.Classes.INSUMOS;
import com.example.forestsys.Classes.INSUMO_ATIVIDADES;
import com.example.forestsys.Classes.MANEJO;
import com.example.forestsys.Classes.MAQUINAS;
import com.example.forestsys.Classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.Classes.MATERIAL_GENETICO;
import com.example.forestsys.Classes.OPERADORES;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.Classes.PRESTADORES;

@Database(entities = {MANEJO.class, MAQUINAS.class, IMPLEMENTOS.class, INSUMOS.class, INDICADORES_SUBSOLAGEM.class, AVAL_PONTO_SUBSOLAGEM.class,
        AVAL_SUBSOLAGEM.class, OPERADORES.class, CALIBRAGEM_SUBSOLAGEM.class, MAQUINA_IMPLEMENTO.class,
        O_S_ATIVIDADE_INSUMOS.class, ATIVIDADE_INDICADORES.class, ATIVIDADES.class, CADASTRO_FLORESTAL.class, ESPACAMENTOS.class, GEO_REGIONAIS.class,
        GEO_SETORES.class, GGF_DEPARTAMENTOS.class, GGF_FUNCOES.class, GGF_USUARIOS.class, INSUMO_ATIVIDADES.class, MATERIAL_GENETICO.class, O_S_ATIVIDADE_INSUMOS_DIA.class, O_S_ATIVIDADES.class, O_S_ATIVIDADES_DIA.class,
        PRESTADORES.class, Configs.class, GEO_LOCALIZACAO.class}, version = 1, exportSchema = false)


public abstract class BaseDeDados extends RoomDatabase {

    private static BaseDeDados instance;

    private static Context activity;

    public abstract DAO dao();

    public static synchronized BaseDeDados getInstance(Context context) {
        activity = context.getApplicationContext();

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BaseDeDados.class, "base_forestsys")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}