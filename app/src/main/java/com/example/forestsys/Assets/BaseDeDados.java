package com.example.forestsys.Assets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.forestsys.Activities.ActivityConfiguracoes;
import com.example.forestsys.Activities.ActivityLogin;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.sql.Types.NULL;


@Database(entities = {MANEJO.class, MAQUINAS.class, IMPLEMENTOS.class, INSUMOS.class, INDICADORES_SUBSOLAGEM.class, AVAL_PONTO_SUBSOLAGEM.class,
        AVAL_SUBSOLAGEM.class, OPERADORES.class, CALIBRAGEM_SUBSOLAGEM.class, MAQUINA_IMPLEMENTO.class,
        O_S_ATIVIDADE_INSUMOS.class, ATIVIDADE_INDICADORES.class, ATIVIDADES.class, CADASTRO_FLORESTAL.class, ESPACAMENTOS.class, GEO_REGIONAIS.class,
        GEO_SETORES.class, GGF_DEPARTAMENTOS.class, GGF_FUNCOES.class, GGF_USUARIOS.class, INSUMO_ATIVIDADES.class, MATERIAL_GENETICO.class, O_S_ATIVIDADE_INSUMOS_DIA.class, O_S_ATIVIDADES.class, O_S_ATIVIDADES_DIA.class,
        PRESTADORES.class, Configs.class}, version = 1, exportSchema = false)


public abstract class BaseDeDados extends RoomDatabase {

    public static int qtdRequisicoes;

    private static BaseDeDados instance;

    private static Context activity;

    public abstract DAO dao();

    private static DAO daoInsere;

    private static Ferramentas ferramentas = new Ferramentas();

    private static RequestQueue filaDeRequisicoes;

    private static String HOST_PORTA;

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

            qtdRequisicoes = 0;
            //new InsereDados(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            qtdRequisicoes = 0;
            //new InsereDados(instance).execute();
        }
    };


    private static class InsereDados extends AsyncTask<Void, Void, Void> {

        private InsereDados(BaseDeDados db) {
            db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //populaBdComWebService(activity);


            try {
                populaBdComJson(activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void populaBdComWebService(Context context) {
        daoInsere = getInstance(context).dao();

        Configs configs = daoInsere.selecionaConfigs();

        if (configs == null) {
            daoInsere.insert(new Configs(1, "GELF", "http://sateliteinfo.ddns.net", "3333"));
            configs = daoInsere.selecionaConfigs();
        }

        HOST_PORTA = configs.getEndereçoHost()+":"+configs.getPortaDeComunicacao()+"/";

        filaDeRequisicoes = Volley.newRequestQueue(context);

        JsonArrayRequest requisicaoFuncoes = new JsonArrayRequest(
                Request.Method.GET,
                HOST_PORTA + "ggffuncoes",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                int ID_FUNCAO = obj.getInt("ID_FUNCAO");
                                String DESCRICAO = obj.getString("DESCRICAO");
                                int ATIVO = obj.getInt("ATIVO");
                                Log.e("FUNCAO:", DESCRICAO);
                                try {
                                    daoInsere.insert(new GGF_FUNCOES(ID_FUNCAO, DESCRICAO, ATIVO));
                                } catch (SQLiteConstraintException e) {
                                    Log.e("Função na id", String.valueOf(ID_FUNCAO));
                                }
                            }
                        } catch (JSONException ex) {
                            Log.e("S1", "Sem resposta nas funcs,json");
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("S1", "Sem resposta nas funcs,conexao");
                    }
                }
        );
        //filaDeRequisicoes.add(requisicaoFuncoes);


        JsonArrayRequest requisicaoDepartamentos = new JsonArrayRequest(
                Request.Method.GET,
                HOST_PORTA + "ggfdepartamentos",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                int ID_DEPARTAMENTO = obj.getInt("ID_DEPARTAMENTO");
                                String DESCRICAO = obj.getString("DESCRICAO");
                                int ATIVO = obj.getInt("ATIVO");
                                Log.e("DEPARTAMENTO:", DESCRICAO);
                                try{daoInsere.insert(new GGF_DEPARTAMENTOS(ID_DEPARTAMENTO, DESCRICAO, ATIVO));
                                } catch (SQLiteConstraintException e) {
                                    Log.e("Departamento na id", String.valueOf(ID_DEPARTAMENTO));
                                }}
                        } catch (JSONException ex) {
                            Log.e("S2", "Sem resposta nos deps,json");
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("S2", "Sem resposta nos deps,conexao");
                    }
                }
        );
       // filaDeRequisicoes.add(requisicaoDepartamentos);


        JsonArrayRequest requisicaoUsuarios = new JsonArrayRequest(
                Request.Method.GET,
                HOST_PORTA + "ggfusuarios",
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                int ID_USUARIO = obj.getInt("ID_USUARIO");
                                String EMAIL = obj.getString("EMAIL");
                                String SENHA = obj.getString("SENHA");
                                String DESCRICAO = obj.getString("DESCRICAO");
                                Integer NIVEL_ACESSO = 0;
                                try{ NIVEL_ACESSO = obj.getInt("NIVEL_ACESSO");}
                                catch(JSONException e){
                                    Log.e("Erro ao converter nivel de acesso", "");
                                    NIVEL_ACESSO = 0;
                                }
                                int ATIVO = obj.getInt("ATIVO");
                                int ID_DEPARTAMENTO = obj.getInt("ID_DEPARTAMENTO");
                                int ID_FUNCAO = obj.getInt("ID_FUNCAO");
                                Log.e("Login:", DESCRICAO);
                                Log.e("Senha", SENHA);
                                Log.e("Nivel", String.valueOf(NIVEL_ACESSO));
                                try {
                                    daoInsere.insert(new GGF_USUARIOS(ID_USUARIO, ID_DEPARTAMENTO, ID_FUNCAO, SENHA, ATIVO, EMAIL, DESCRICAO, NIVEL_ACESSO));
                                } catch (SQLiteConstraintException e) {
                                    Log.e("Usuario na id", String.valueOf(ID_USUARIO));
                                }
                            }
                        } catch (JSONException ex) {
                            Log.e("S3", "Sem resposta nos users:json");
                            Log.e("Erro Users", String.valueOf(ex));
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("S3", "Sem resposta nos users,conexao");
                        Log.e("Erro Users", String.valueOf(error));
                    }
                }
        );
        filaDeRequisicoes.add(requisicaoUsuarios);
    }

    @SuppressLint("LongLogTag")
    public static void populaBdComJson(Context context) throws IOException {
        daoInsere = getInstance(context).dao();

        JSONArray dados = null;

        dados = carregaJsonImplementos(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);

                int ID_IMPLEMENTO = obj.getInt("ID_IMPLEMENTO");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new IMPLEMENTOS(ID_IMPLEMENTO, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Implementp na id", String.valueOf(ID_IMPLEMENTO));
                }

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
                try {
                    daoInsere.insert(new MAQUINAS(ID_MAQUINA, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Maquina na id", String.valueOf(ID_MAQUINA));
                }
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
                try {
                    daoInsere.insert(new PRESTADORES(ID_PRESTADORES, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Prestador na id", String.valueOf(ID_PRESTADORES));
                }
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
                try {
                    daoInsere.insert(new OPERADORES(ID_OPERADORES, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Operador na id", String.valueOf(ID_OPERADORES));
                }
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
                try {
                    daoInsere.insert(new ATIVIDADES(ID_ATIVIDADE, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Atividade na id", String.valueOf(ID_ATIVIDADE));
                }
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
                int ID_SETOR = obj.getInt("ID_SETOR");
                String TALHAO = obj.getString("TALHAO");
                int CICLO = obj.getInt("CICLO");
                int ID_MANEJO = obj.getInt("ID_MANEJO");
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

                DATA_PROGRAMADA = ferramentas.formataDataDb(DATA_PROGRAMADA);

                try {
                    daoInsere.insert(new O_S_ATIVIDADES(ID_PROGRAMACAO_ATIVIDADE, ID_REGIONAL, ID_SETOR, TALHAO, CICLO, ID_MANEJO,
                            ID_ATIVIDADE, ID_RESPONSAVEL, DATA_PROGRAMADA, AREA_PROGRAMADA, PRIORIDADE, EXPERIMENTO, MADEIRA_NO_TALHAO, OBSERVACAO, DATA_INICIAL, DATA_FINAL, AREA_REALIZADA, "Aberto", 0, false));
                } catch (SQLiteConstraintException e) {
                    Log.e("Os Atividade na id", String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                }
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
                int ATIVO = obj.getInt("ATIVO");
                String UND_MEDIDA = obj.getString("UND_MEDIDA");
                try {
                    daoInsere.insert(new INSUMOS(ID_INSUMO, ID_INSUMO_RM, CLASSE, DESCRICAO, NUTRIENTE_N,
                            NUTRIENTE_P2O5, NUTRIENTE_K2O, NUTRIENTE_CAO, NUTRIENTE_MGO, NUTRIENTE_B, NUTRIENTE_ZN, NUTRIENTE_S, NUTRIENTE_CU,
                            NUTRIENTE_AF, NUTRIENTE_MN, ATIVO, UND_MEDIDA));
                } catch (SQLiteConstraintException e) {
                    Log.e("Insumo na id", String.valueOf(ID_INSUMO));
                }
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
                try {
                    daoInsere.insert(new O_S_ATIVIDADE_INSUMOS(ID_INSUMO, ID_PROGRAMACAO_ATIVIDADE, RECOMENDACAO, QTD_HA_RECOMENDADO, QTD_HA_APLICADO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Atividade Insumos na id", String.valueOf(ID_INSUMO) + " , " + String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                }
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
                try {
                    daoInsere.insert(new MAQUINA_IMPLEMENTO(ID_MAQUINA_IMPLEMENTO, ID_MAQUINA, ID_IMPLEMENTO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Maquina Implemento na id", String.valueOf(ID_MAQUINA_IMPLEMENTO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonAtividadeIndicadores(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                int ID_INDICADOR = obj.getInt("ID_INDICADOR");
                int ORDEM_INDICADOR = obj.getInt("ORDEM_INDICADOR");
                String REFERENCIA = obj.getString("REFERENCIA");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                String VERION = obj.getString("VERION");
                int INDICADOR_CORRIGIVEL = obj.getInt("INDICADOR_CORRIGIVEL");
                int LIMITE_INFERIOR = obj.getInt("LIMITE_INFERIOR");
                int LIMITE_SUPERIOR = obj.getInt("LIMITE_SUPERIOR");
                int CASAS_DECIMAIS = obj.getInt("CASAS_DECIMAIS");
                String FORMULA = obj.getString("FORMULA");
                try {
                    daoInsere.insert(new ATIVIDADE_INDICADORES(ID_INDICADOR, ID_ATIVIDADE, ORDEM_INDICADOR, REFERENCIA, DESCRICAO, ATIVO, VERION, LIMITE_SUPERIOR, LIMITE_INFERIOR, CASAS_DECIMAIS, INDICADOR_CORRIGIVEL, FORMULA));
                } catch (SQLiteConstraintException e) {
                    Log.e("Atividade Indicadores na id", String.valueOf(ID_INDICADOR) + " , " + String.valueOf(ID_ATIVIDADE));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonEspacamentos(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_ESPACAMENTO = obj.getInt("ID_ESPACAMENTO");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new ESPACAMENTOS(ID_ESPACAMENTO, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Espacamentos na id", String.valueOf(ID_ESPACAMENTO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonAvalSubsolagem(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                double PROFUNDIDADE = obj.getDouble("PROFUNDIDADE");
                double ESTRONDAMENTO_LATERAL_INFERIOR = obj.getDouble("ESTRONDAMENTO_LATERAL_INFERIOR");
                double ESTRONDAMENTO_LATERAL_SUPERIOR = obj.getDouble("ESTRONDAMENTO_LATERAL_SUPERIOR");
                double FAIXA_SOLO_PREPARADA = obj.getDouble("FAIXA_SOLO_PREPARADA");
                double PROFUNDIDADE_ADUBO_INFERIOR = obj.getDouble("PROFUNDIDADE_ADUBO_INFERIOR");
                double PROFUNDIDADE_ADUBO_SUPERIOR = obj.getDouble("PROFUNDIDADE_ADUBO_SUPERIOR");
                double LOCALIZACAO_INSUMO_INFERIOR = obj.getDouble("LOCALIZACAO_INSUMO_INFERIOR");
                double LOCALIZACAO_INSUMO_SUPERIOR = obj.getDouble("LOCALIZACAO_INSUMO_SUPERIOR");

                try {
                    daoInsere.insert(new AVAL_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, PROFUNDIDADE, ESTRONDAMENTO_LATERAL_INFERIOR,
                            ESTRONDAMENTO_LATERAL_SUPERIOR, FAIXA_SOLO_PREPARADA, PROFUNDIDADE_ADUBO_INFERIOR,
                            PROFUNDIDADE_ADUBO_SUPERIOR, LOCALIZACAO_INSUMO_INFERIOR, LOCALIZACAO_INSUMO_SUPERIOR));
                } catch (SQLiteConstraintException e) {
                    Log.e("Aval Sbsolagem na id", String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonGeoRegionais(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_REGIONAL = obj.getInt("ID_REGIONAL");
                String DESCRICAO = obj.getString("DESCRICAO");
                int SETOR_TODOS = obj.getInt("SETOR_TODOS");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new GEO_REGIONAIS(ID_REGIONAL, DESCRICAO, SETOR_TODOS, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Regionais na id", String.valueOf(ID_REGIONAL));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonSetores(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_REGIONAL = obj.getInt("ID_REGIONAL");
                int ID_SETOR = obj.getInt("ID_SETOR");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new GEO_SETORES(ID_REGIONAL, ID_SETOR, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Geo Setores na id", String.valueOf(ID_REGIONAL) + " , " + String.valueOf(ID_SETOR));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonManejo(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_MANEJO = obj.getInt("ID_MANEJO");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new MANEJO(ID_MANEJO, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Manejo na id", String.valueOf(ID_MANEJO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dados = carregaJsonMaterialGenetico(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_MATERIAL_GENETICO = obj.getInt("ID_MATERIAL_GENETICO");
                String DESCRICAO = obj.getString("DESCRICAO");
                int ATIVO = obj.getInt("ATIVO");
                try {
                    daoInsere.insert(new MATERIAL_GENETICO(ID_MATERIAL_GENETICO, DESCRICAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Material Genetico na id", String.valueOf(ID_MATERIAL_GENETICO));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dados = carregaJsonCadFlorestal(context);
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject obj = dados.getJSONObject(i);
                int ID_REGIONAL = obj.getInt("ID_REGIONAL");
                int ID_SETOR = obj.getInt("ID_SETOR");
                String TALHAO = obj.getString("TALHAO");
                int CICLO = obj.getInt("CICLO");
                int ID_MANEJO = obj.getInt("ID_MANEJO");
                String DATA_MANEJO = obj.getString("DATA_MANEJO");
                String DATA_PROGRAMACAO_REFORMA = obj.getString("DATA_PROGRAMACAO_REFORMA");
                int ID_MATERIAL_GENETICO = obj.getInt("ID_MATERIAL_GENETICO");
                int ID_ESPACAMENTO = obj.getInt("ID_ESPACAMENTO");
                String OBSERVACAO = obj.getString("OBSERVACAO");
                int ATIVO = obj.getInt("ATIVO");

                try {
                    daoInsere.insert(new CADASTRO_FLORESTAL(ID_REGIONAL, ID_SETOR, TALHAO, CICLO, ID_MANEJO,
                            DATA_MANEJO, DATA_PROGRAMACAO_REFORMA, ID_MATERIAL_GENETICO, ID_ESPACAMENTO, OBSERVACAO, ATIVO));
                } catch (SQLiteConstraintException e) {
                    Log.e("Cad Florestal na id", String.valueOf(ID_REGIONAL) + " , " + String.valueOf(ID_SETOR));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonFuncoes(Context context) throws
            IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = new URL(HOST_PORTA + "ggffuncoes").openConnection().getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONArray json = new JSONArray(builder.toString());
            return json;

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonDepartamentos(Context context) throws
            IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = new URL(HOST_PORTA + "ggfdepartamentos").openConnection().getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONArray json = new JSONArray(builder.toString());
            return json;

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonUsuarios(Context context) throws
            IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = new URL(HOST_PORTA + "ggfusuarios").openConnection().getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONArray json = new JSONArray(builder.toString());
            return json;

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
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

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonAtividadeIndicadores(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.atividade_indicadores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("ATIVIDADE_INDICADORES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonEspacamentos(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.espacamentos);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("ESPACAMENTOS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonAvalSubsolagem(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.aval_subsolagem);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("AVAL_SUBSOLAGEM");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonGeoRegionais(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.regionais);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("REGIONAIS");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonManejo(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.manejo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("MANEJO");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonSetores(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.setores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("SETORES");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonCadFlorestal(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.cadastro_florestal);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("CADASTRO_FLORESTAL");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Carrega dados de um json para a classe correspondente
    private static JSONArray carregaJsonMaterialGenetico(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.material_genetico);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String linha;

        try {
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("MATERIAL_GENETICO");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}