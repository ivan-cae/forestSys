package com.example.forestsys;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.forestsys.DataHoraAtual.getCurrentDateTime;


@Database(entities = {ClasseUsers.class, ClasseEncarregados.class, ClasseFazenda.class, ClasseSetor.class,
                        ClasseRegional.class, ClasseUpdate.class, ClasseOsInsumos.class,
                            ClasseOs.class, ClasseMaquinas.class, ClassePrestadores.class}, version = 1, exportSchema = false)


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
            auxDao.insert(new ClasseUsers("ForestSys", "01", "ForestSys", Enumeraveis.nivelAcesso.getNivelAcesso(1), "a", "a"));


            auxDao.insert(new ClasseOs(1,1,1,1, 1,1,1,1,1,
                    1,Enumeraveis.manejo.getManejo(1),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(2),"1",false));

            auxDao.insert(new ClasseOs(1,1,1,1,1, 1,1,1,1,
                    1,Enumeraveis.manejo.getManejo(2),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(1),"1",false));

            auxDao.insert(new ClasseOs(1,1,1,1,1,1, 1,1,1,
                    1,Enumeraveis.manejo.getManejo(1),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(2),"1",false));

            auxDao.insert(new ClasseOs(1,1,1,1,1,1,1, 1,1,
                    1,Enumeraveis.manejo.getManejo(2),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(1),"1",false));

            auxDao.insert(new ClasseOs(1,1,1,1,1,1,1,1, 1,
                    1,Enumeraveis.manejo.getManejo(1),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(2),"1",false));

            auxDao.insert(new ClasseOs(1,1,1,1,1,1,1,1, 1,
                    1,Enumeraveis.manejo.getManejo(2),"a",null,1,false
                    ,true,"1",
                    Enumeraveis.status.getStatus(1),"1",false));
            return null;
        }
    }
}