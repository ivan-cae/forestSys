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
import com.example.forestsys.classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.classes.CADASTRO_FLORESTAL;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@Database(entities = {MANEJO.class, MAQUINAS.class,IMPLEMENTOS.class, INSUMOS.class,INDICADORES_SUBSOLAGEM.class, AVAL_PONTO_SUBSOLAGEM.class,
        AVAL_SUBSOLAGEM.class, OPERADORES.class, CALIBRAGEM_SUBSOLAGEM.class, MAQUINA_IMPLEMENTO.class,
        O_S_ATIVIDADE_INSUMOS.class, ATIVIDADE_INDICADORES.class, ATIVIDADES.class, CADASTRO_FLORESTAL.class, ESPACAMENTOS.class, GEO_REGIONAIS.class,
        GEO_SETORES.class, GGF_DEPARTAMENTOS.class, GGF_FUNCOES.class, GGF_USUARIOS.class,  INSUMO_ATIVIDADES.class,  MATERIAL_GENETICO.class, O_S_ATIVIDADE_INSUMOS_DIA.class, O_S_ATIVIDADES.class, O_S_ATIVIDADES_DIA.class,
        PRESTADORES.class}, version = 1, exportSchema = false)


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

    //Método usado para popular o DB programaticamente
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsereDados1(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
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
            populaBdComJson(activity);
            return null;
        }
    }

    private static void populaBdComJson(Context context) {

        DAO daoInsere = getInstance(context).dao();

        JSONArray dados = carregaJsonUsuarios(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_USUARIO = obj.getInt("ID_USUARIO");
                String EMAIL = obj.getString("EMAIL");
                String SENHA = obj.getString("SENHA");
                String DESCRICAO = obj.getString("DESCRICAO");

                daoInsere.insert(new GGF_USUARIOS(ID_USUARIO, EMAIL, SENHA, DESCRICAO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonImplementos(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_IMPLEMENTO = obj.getInt("ID_IMPLEMENTO");
                        String DESCRICAO = obj.getString("DESCRICAO");
                        int ATIVO = obj.getInt("ATIVO");

                daoInsere.insert(new IMPLEMENTOS(ID_IMPLEMENTO, DESCRICAO, ATIVO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonMaquinas(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_MAQUINA = obj.getInt("ID_MAQUINA");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");

                daoInsere.insert(new MAQUINAS(ID_MAQUINA, DESCRICAO, ATIVO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonPrestadores(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_PRESTADORES = obj.getInt("ID_PRESTADORES");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");

                daoInsere.insert(new PRESTADORES(ID_PRESTADORES, DESCRICAO, ATIVO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonOperadores(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_OPERADORES = obj.getInt("ID_OPERADORES");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");

                daoInsere.insert(new OPERADORES(ID_OPERADORES, DESCRICAO, ATIVO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonAtividades(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");

                daoInsere.insert(new ATIVIDADES(ID_ATIVIDADE, DESCRICAO, ATIVO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonOsAtividades(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                      int ID_REGIONAL = obj.getInt("ID_REGIONAL");
                        String ID_SETOR = obj.getString("ID_SETOR");
                        String TALHAO = obj.getString("TALHAO");
                        int CICLO = obj.getInt("CICLO");
                        String ID_MANEJO = obj.getString("ID_MANEJO");
                       int ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                        int ID_RESPONSAVEL = obj.getInt("ID_RESPONSAVEL");
                        String DATA_PROGRAMADA = obj.getString("DATA_PROGRAMADA");
                     Double AREA_PROGRAMADA = obj.getDouble("AREA_PROGRAMADA");
                   int PRIORIDADE = obj.getInt("PRIORIDADE");
                       int EXPERIMENTO = obj.getInt("EXPERIMENTO");
                int MADEIRA_NO_TALHAO = obj.getInt("MADEIRA_NO_TALHAO");
                        String OBSERVACAO = obj.getString("OBSERVACAO");
                        String DATA_INICIAL = obj.getString("DATA_INICIAL");
                        String DATA_FINAL = obj.getString("DATA_FINAL");
                        Double AREA_REALIZADA = obj.getDouble("AREA_REALIZADA");


                daoInsere.insert(new O_S_ATIVIDADES( ID_PROGRAMACAO_ATIVIDADE,  ID_REGIONAL,  ID_SETOR,  TALHAO,  CICLO,  ID_MANEJO,  ID_ATIVIDADE,  ID_RESPONSAVEL,  DATA_PROGRAMADA,  AREA_PROGRAMADA,  PRIORIDADE,  EXPERIMENTO,  MADEIRA_NO_TALHAO,  OBSERVACAO,  DATA_INICIAL,  DATA_FINAL,  AREA_REALIZADA, "Aberta"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonInsumos(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                 int ID_INSUMO = obj.getInt("ID_INSUMO");
                 String ID_INSUMO_RM = obj.getString("ID_INSUMO_RM");
                 String CLASSE = obj.getString("CLASSE");
                 String DESCRICAO = obj.getString("DESCRICAO");
                 double NUTRIENTE_N = obj.getDouble("NUTRIENTE_N");
                 double NUTRIENTE_P2O5 = obj.getDouble("NUTRIENTE_P2O5");
                 double NUTRIENTE_K2O = obj.getDouble("NUTRIENTE_K2O");
                 double NUTRIENTE_CAO = obj.getDouble("NUTRIENTE_CAO");
                 double NUTRIENTE_MGO = obj.getDouble("NUTRIENTE_MGO");
                 double NUTRIENTE_B = obj.getDouble("NUTRIENTE_B");
                 double NUTRIENTE_ZN = obj.getDouble("NUTRIENTE_ZN");
                 double NUTRIENTE_S = obj.getDouble("NUTRIENTE_S");
                 double NUTRIENTE_CU = obj.getDouble("NUTRIENTE_CU");
                 double NUTRIENTE_AF = obj.getDouble("NUTRIENTE_AF");
                 double NUTRIENTE_MN = obj.getDouble("NUTRIENTE_MN");
                 int ATIVO= obj.getInt("ATIVO");
                 String UND_MEDIDA = obj.getString("UND_MEDIDA");

                daoInsere.insert(new INSUMOS( ID_INSUMO,  ID_INSUMO_RM,  CLASSE,  DESCRICAO,  NUTRIENTE_N,
                 NUTRIENTE_P2O5,  NUTRIENTE_K2O,  NUTRIENTE_CAO,  NUTRIENTE_MGO,  NUTRIENTE_B,  NUTRIENTE_ZN, NUTRIENTE_S, NUTRIENTE_CU,
                 NUTRIENTE_AF,  NUTRIENTE_MN,  ATIVO,  UND_MEDIDA));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonOsatividadeInsumos(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_INSUMO = obj.getInt("ID_INSUMO");
                int ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                int RECOMENDACAO = obj.getInt("RECOMENDACAO");
                double QTD_HA_RECOMENDADO = obj.getDouble("QTD_HA_RECOMENDADO");
                double QTD_HA_APLICADO = obj.getDouble("QTD_HA_APLICADO");

                daoInsere.insert(new O_S_ATIVIDADE_INSUMOS( ID_INSUMO,  ID_PROGRAMACAO_ATIVIDADE,  RECOMENDACAO,  QTD_HA_RECOMENDADO,  QTD_HA_APLICADO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonMaquinaImplemento(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_MAQUINA_IMPLEMENTO = obj.getInt("ID_MAQUINA_IMPLEMENTO");
                int ID_MAQUINA = obj.getInt("ID_MAQUINA");
                int ID_IMPLEMENTO = obj.getInt("ID_IMPLEMENTO");

                daoInsere.insert(new MAQUINA_IMPLEMENTO(ID_MAQUINA_IMPLEMENTO, ID_MAQUINA, ID_IMPLEMENTO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private static JSONArray carregaJsonUsuarios(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.usuarios);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("GGF_USUARIOS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonImplementos(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.implementos);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("IMPLEMENTOS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonMaquinas(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.maquinas);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("MAQUINAS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonPrestadores(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.prestadores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("PRESTADORES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonOperadores(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.operadores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("OPERADORES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonOsAtividades(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.os_atividades);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("O_S_ATIVIDADES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonInsumos(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.insumos);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("INSUMOS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonOsatividadeInsumos(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.os_atividade_insumos);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("O_S_ATIVIDADE_INSUMOS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonMaquinaImplemento(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.maquina_implemento);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("MAQUINA_IMPLEMENTO");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static JSONArray carregaJsonAtividades(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.atividades);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("ATIVIDADES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}