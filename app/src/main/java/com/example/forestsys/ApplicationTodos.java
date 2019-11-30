package com.example.forestsys;

import android.content.Context;

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
