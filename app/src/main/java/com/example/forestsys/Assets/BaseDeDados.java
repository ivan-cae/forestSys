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
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
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
        PRESTADORES.class, Configs.class, GEO_LOCALIZACAO.class, FOREST_LOG.class}, version = 10, exportSchema = false)


public abstract class BaseDeDados extends RoomDatabase {

    private static BaseDeDados instance;

    private static Context activity;

    public abstract DAO dao();

    public static synchronized BaseDeDados getInstance(Context context) {
        activity = context.getApplicationContext();

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BaseDeDados.class, "base_forestsys")
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .addMigrations(MIGRATION_5_6)
                    .addMigrations(MIGRATION_6_7)
                    .addMigrations(MIGRATION_7_8)
                    .addMigrations(MIGRATION_8_9)
                    .addMigrations(MIGRATION_9_10)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Configs ADD COLUMN ultimaDataQueApagou TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ATIVIDADE_INDICADORES ADD COLUMN UNIDADE_MEDIDA TEXT");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE AVAL_PONTO_SUBSOLAGEM ADD COLUMN editou INTEGER");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE INDICADORES_SUBSOLAGEM ADD COLUMN fezSinc INTEGER");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `FOREST_LOG` (`ID` INTEGER, `DATA` TEXT, `DISPOSITIVO` TEXT, " +
                    "`USUARIO` TEXT, `ACAO` TEXT, `VALOR` TEXT, `MODULO` TEXT, `CREATED_AT` TEXT, PRIMARY KEY(`ID`))");
        }
    };

    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE O_S_ATIVIDADES ADD COLUMN UPDATED_AT TEXT");
        }
    };

    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE O_S_ATIVIDADES ADD COLUMN EXPORT_PROXIMA_SINC INTEGER");
        }
    };

    static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE O_S_ATIVIDADE_INSUMOS ADD COLUMN EXPORT_PROXIMA_SINC INTEGER");
        }
    };

    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE INDICADORES_SUBSOLAGEM ADD COLUMN EXPORT_PROXIMA_SINC INTEGER");
        }
    };


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