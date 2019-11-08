package com.example.forestsys;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class BaseUsers extends RoomDatabase {

    private static BaseUsers instance;

    public abstract UsersDAO usersDAO();

    public static synchronized BaseUsers getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BaseUsers.class, "base_OS")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsereDados(instance).execute();
        }
    };

    private static class InsereDados extends AsyncTask<Void, Void, Void>{
        private UsersDAO dao;
        private InsereDados(BaseUsers db){
            dao = db.usersDAO();
        }

        @Override
        protected Void doInBackground(Void... voids){
            dao.insert(new Users("ForestSys1", "01", "ForestSys", Enumeraveis.nivelAcesso.Admin, "usu1", "usu1"));
            dao.insert(new Users("ForestSys2", "02", "ForestSys", Enumeraveis.nivelAcesso.Admin, "usu2", "usu2"));
            dao.insert(new Users("ForestSys3", "03", "ForestSys", Enumeraveis.nivelAcesso.Admin, "usu3", "usu3"));

            return null;
        }
    }
}

