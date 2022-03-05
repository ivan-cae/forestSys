package com.example.forestsys.Assets;

import android.content.Context;

/*
 * Classe auxiliar responsável por retornar o Context da atividade atual
 */
public class ApplicationTodos extends android.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //retorna o contexto da activity
    public static Context getAppContext() {
        return context;
    }
}
