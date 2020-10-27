package com.example.forestsys.Assets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.example.forestsys.Classes.ATIVIDADES;
import com.example.forestsys.Classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.example.forestsys.Activities.ActivityInicializacao.HOST_PORTA;
import static com.example.forestsys.Activities.ActivityInicializacao.conectado;

public class ClienteWeb {

    private static DAO dao;

    private final Ferramentas ferramentas = new Ferramentas();

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    public static boolean finalizouSinc = false;

    private BaseDeDados baseDeDados;

    private static Context activity;

    public static Integer contadorDeErros;

    public ClienteWeb(Context context) {
        activity = context;
        baseDeDados = BaseDeDados.getInstance(activity);
        dao = baseDeDados.dao();
    }


    private static String requisicaoPOST(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static String requisicaoGET(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        okhttp3.Response response = client.newCall(request).execute();

        String jsonDeResposta = response.body().string();

        return jsonDeResposta;
    }

    private static String requisicaoPUT(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @SuppressLint("LongLogTag")
    public static void sincronizaWebService() throws JSONException, IOException {
        contadorDeErros = 0;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    populaBdComWebService();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    @SuppressLint("LongLogTag")
    public static void populaBdComWebService() throws IOException, JSONException {
        finalizouSinc = false;
        conectado = false;
        JSONArray response;

        try {
            URL myUrl = new URL(HOST_PORTA);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            Log.e("Conectado a", myUrl.toString());
            conectado = true;
        } catch (Exception e) {
            Log.e("Erro", e.toString() + " ao conectar com: " + HOST_PORTA);
            conectado = false;
            finalizouSinc = true;
        }

        if (conectado == true) {
            try {
                List<CALIBRAGEM_SUBSOLAGEM> todasCalibragens = dao.todasCalibragens();
                for (Integer i = 0; i < todasCalibragens.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("ID_PROGRAMACAO_ATIVIDADE", todasCalibragens.get(i).getID_PROGRAMACAO_ATIVIDADE());
                    obj.put("DATA", todasCalibragens.get(i).getDATA());
                    obj.put("TURNO", todasCalibragens.get(i).getTURNO());
                    obj.put("ID_MAQUINA_IMPLEMENTO", todasCalibragens.get(i).getID_MAQUINA_IMPLEMENTO());
                    obj.put("ID_OPERADOR", todasCalibragens.get(i).getID_OPERADOR());
                    obj.put("P1_MEDIA", todasCalibragens.get(i).getP1_MEDIA());
                    obj.put("P1_DESVIO", todasCalibragens.get(i).getP1_DESVIO());
                    obj.put("P2_MEDIA", todasCalibragens.get(i).getP2_MEDIA());
                    obj.put("P2_DESVIO", todasCalibragens.get(i).getP2_DESVIO());

                    requisicaoPOST(HOST_PORTA + "silvcalibragemsubsolagens", obj.toString());
                    //Log.e("Objeto Calibacao" + i, obj.toString());
                    //Log.e("Post", requisicaoPOST(HOST_PORTA + "silvcalibragemsubsolagens", obj.toString()));
                    //requisicaoPOST(HOST_PORTA + "silvcalibragemsubsolagens", obj.toString());
                }
            } catch (Exception ex) {
                Log.e("CALIBRAGEM_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST");
            }

            try {
                List<O_S_ATIVIDADES_DIA> todasOsAtividadesDia = dao.todasOsAtividadesDia();
                boolean naoFazPut = false;
                for (Integer i = 0; i < todasOsAtividadesDia.size(); i++) {
                    JSONObject obj = new JSONObject();
                    if (todasOsAtividadesDia.get(i).getID() == null) {
                        obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadesDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                        obj.put("DATA", todasOsAtividadesDia.get(i).getDATA());
                        obj.put("AREA_REALIZADA", todasOsAtividadesDia.get(i).getAREA_REALIZADA());
                        obj.put("HH", todasOsAtividadesDia.get(i).getHH());
                        obj.put("HM", todasOsAtividadesDia.get(i).getHM());
                        obj.put("HO", todasOsAtividadesDia.get(i).getHO());
                        obj.put("ID_PRESTADOR", todasOsAtividadesDia.get(i).getID_PRESTADOR());
                        obj.put("ID_RESPONSAVEL", todasOsAtividadesDia.get(i).getID_RESPONSAVEL());
                        obj.put("OBSERVACAO", todasOsAtividadesDia.get(i).getOBSERVACAO());
                        obj.put("ACAO_INATIVO", todasOsAtividadesDia.get(i).getACAO_INATIVO());
                        obj.put("REGISTRO_DESCARREGADO", todasOsAtividadesDia.get(i).getREGISTRO_DESCARREGADO());
                        obj.put("STATUS", "A");

                        String hme = todasOsAtividadesDia.get(i).getHM_ESCAVADEIRA();
                        double converte = 0;
                        try {
                            converte = Double.valueOf(hme);
                        } catch (Exception e) {
                            converte = 0;
                        }
                        hme = String.valueOf(converte);

                        String hoe = todasOsAtividadesDia.get(i).getHO_ESCAVADEIRA();
                        try {
                            converte = Double.valueOf(hoe);
                        } catch (Exception e) {
                            converte = 0;
                        }
                        hoe = String.valueOf(converte);

                        obj.put("HM_ESCAVADEIRA", hme);
                        obj.put("HO_ESCAVADEIRA", hoe);

                        String POST = requisicaoPOST(HOST_PORTA + "silvosatividadesdias", obj.toString());
                        //Log.e("Objeto Post Atv_dia" + i, obj.toString());
                        //Log.e("Post Atv_dia", POST);

                        JSONObject updateId = new JSONObject(POST);
                        Integer idRetornado = updateId.getInt("ID");
                        todasOsAtividadesDia.get(i).setID(idRetornado);
                        dao.update(todasOsAtividadesDia.get(i));


                        Log.e("ID inserido ", String.valueOf(todasOsAtividadesDia.get(i).getID()));
                    } else {
                        obj.put("ID", todasOsAtividadesDia.get(i).getID());
                        obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadesDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                        obj.put("DATA", todasOsAtividadesDia.get(i).getDATA());
                        obj.put("AREA_REALIZADA", todasOsAtividadesDia.get(i).getAREA_REALIZADA());
                        obj.put("HH", todasOsAtividadesDia.get(i).getHH());
                        obj.put("HM", todasOsAtividadesDia.get(i).getHM());
                        obj.put("HO", todasOsAtividadesDia.get(i).getHO());
                        obj.put("HM_ESCAVADEIRA", todasOsAtividadesDia.get(i).getHM_ESCAVADEIRA());
                        obj.put("HO_ESCAVADEIRA", todasOsAtividadesDia.get(i).getHO_ESCAVADEIRA());
                        obj.put("ID_PRESTADOR", todasOsAtividadesDia.get(i).getID_PRESTADOR());
                        obj.put("ID_RESPONSAVEL", todasOsAtividadesDia.get(i).getID_RESPONSAVEL());
                        obj.put("OBSERVACAO", todasOsAtividadesDia.get(i).getOBSERVACAO());
                        obj.put("ACAO_INATIVO", todasOsAtividadesDia.get(i).getACAO_INATIVO());
                        obj.put("REGISTRO_DESCARREGADO", todasOsAtividadesDia.get(i).getREGISTRO_DESCARREGADO());
                        obj.put("STATUS", todasOsAtividadesDia.get(i).getSTATUS());

                        //Log.e("Objeto Put Atv_dia", obj.toString());


                        if (obj.getString("REGISTRO_DESCARREGADO").equals("S")) {
                            naoFazPut = true;
                        }
                        if (obj.getString("ACAO_INATIVO") == null) {
                        }
                        if (naoFazPut == false) {
                            //Log.e("PUT Atv_dia", requisicaoPUT(HOST_PORTA + "silvosatividadesdias" + "/" +
                              //      String.valueOf(todasOsAtividadesDia.get(i).getID()), obj.toString()));
                            requisicaoPUT(HOST_PORTA + "silvosatividadesdias" + "/" +
                                    String.valueOf(todasOsAtividadesDia.get(i).getID()), obj.toString());
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("ATIVIDADES_DIA", "Erro ao instanciar objeto para requisição POST ou PUT");
            }


            try {
                List<O_S_ATIVIDADE_INSUMOS_DIA> todasOsAtividadeInsumoDia = dao.todasOsAtvInsumosDia();
                boolean naoFazPut = false;
                for (Integer i = 0; i < todasOsAtividadeInsumoDia.size(); i++) {
                    JSONObject obj = new JSONObject();
                    if (todasOsAtividadeInsumoDia.get(i).getID() == null || todasOsAtividadeInsumoDia.get(i).getID()==0) {
                        obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadeInsumoDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                        obj.put("DATA", todasOsAtividadeInsumoDia.get(i).getDATA());
                        obj.put("ID_INSUMO", todasOsAtividadeInsumoDia.get(i).getID_INSUMO());
                        obj.put("QTD_APLICADO", todasOsAtividadeInsumoDia.get(i).getQTD_APLICADO());
                        obj.put("REGISTRO_DESCARREGADO", todasOsAtividadeInsumoDia.get(i).getREGISTRO_DESCARREGADO());
                        obj.put("OBSERVACAO", todasOsAtividadeInsumoDia.get(i).getOBSERVACAO());

                        if(todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO() != null){
                            obj.put("ACAO_INATIVO", todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO());
                        }else{
                            obj.put("ACAO_INATIVO", null);
                        }
                        String POST = null;
                        boolean fezOPost = false;
                        try {
                            POST = requisicaoPOST(HOST_PORTA + "silvosatividadeinsumosdias", obj.toString());
                            //Log.e("Objeto Post OsAtividadeInsumoDia" + i, obj.toString());
                            Log.e("Post OsAtividadeInsumoDia", POST);
                            fezOPost = true;
                        }catch(Exception ex){
                            fezOPost=false;
                        }
                        if(fezOPost==true && POST!=null) {
                            JSONObject updateId = new JSONObject(POST);
                            Integer idRetornado = updateId.getInt("ID");
                            todasOsAtividadeInsumoDia.get(i).setID(idRetornado);
                            dao.update(todasOsAtividadeInsumoDia.get(i));

                            Log.e("ID inserido ", String.valueOf(todasOsAtividadeInsumoDia.get(i).getID()));
                        }
                        } if(todasOsAtividadeInsumoDia.get(i).getID() != null && todasOsAtividadeInsumoDia.get(i).getID()!=0) {
                        obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadeInsumoDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                        obj.put("DATA", todasOsAtividadeInsumoDia.get(i).getDATA());
                        obj.put("ID_INSUMO", todasOsAtividadeInsumoDia.get(i).getID_INSUMO());
                        obj.put("QTD_APLICADO", todasOsAtividadeInsumoDia.get(i).getQTD_APLICADO());
                        obj.put("REGISTRO_DESCARREGADO", todasOsAtividadeInsumoDia.get(i).getREGISTRO_DESCARREGADO());
                        obj.put("OBSERVACAO", todasOsAtividadeInsumoDia.get(i).getOBSERVACAO());
                        obj.put("ID", todasOsAtividadeInsumoDia.get(i).getID());
                        //Log.e("Objeto Put OsAtividadeInsumoDia", obj.toString());

                        if (obj.getString("REGISTRO_DESCARREGADO").equals("S")) {
                            naoFazPut = true;
                        }
                        if (todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO() == null) {
                            naoFazPut = true;
                            obj.put("ACAO_INATIVO", null);
                        }else {
                            obj.put("ACAO_INATIVO", todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO());
                        }
                        if (naoFazPut == false) {
                            Log.e("PUT OsAtividadeInsumoDia", requisicaoPUT(HOST_PORTA + "silvosatividadeinsumosdias" + "/" +
                                    String.valueOf(todasOsAtividadeInsumoDia.get(i).getID()), obj.toString()));
                            //requisicaoPUT(HOST_PORTA + "silvosatividadeinsumosdias" + "/" +
                              //      String.valueOf(todasOsAtividadeInsumoDia.get(i).getID()), obj.toString());
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("INSUMOS_ATIVIDADES_DIA", ex.getMessage());
            }


            try {
                List<O_S_ATIVIDADES> todasOsAtividades = dao.todasOs();
                for (Integer i = 0; i < todasOsAtividades.size(); i++) {
                    JSONObject obj = new JSONObject();

                    Integer STATUS_NUM = todasOsAtividades.get(i).getSTATUS_NUM();
                    String STATUS = null;

                    if (STATUS_NUM == 1) {
                        STATUS = "A";
                        obj.put("STATUS", STATUS);
                    }
                    if (STATUS_NUM == 2) {
                        STATUS = "F";
                        obj.put("STATUS", STATUS);
                    }


                    obj.put("DATA_INICIAL", todasOsAtividades.get(i).getDATA_INICIAL());
                    obj.put("OBSERVACAO", todasOsAtividades.get(i).getOBSERVACAO());
                    obj.put("AREA_REALIZADA", todasOsAtividades.get(i).getAREA_REALIZADA());
                    obj.put("DATA_FINAL", todasOsAtividades.get(i).getDATA_FINAL());


                    //Log.e("Objeto os_atividades" + i, obj.toString());

                    //Log.e("Put", requisicaoPUT(HOST_PORTA + "silvosatividades" + "/" +
                      //      String.valueOf(todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE()), obj.toString()));

                    requisicaoPUT(HOST_PORTA + "silvosatividades" + "/" +
                            String.valueOf(todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE()), obj.toString());
                }
            } catch (Exception ex) {
                Log.e("OS_ATIVIDADES", "Erro ao instanciar objeto para requisição PUT");
            }


            try {
                List<O_S_ATIVIDADE_INSUMOS> todasAtvInsumos = dao.todosOsAtividadeInsumos();
                for (Integer i = 0; i < todasAtvInsumos.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("ID_INSUMO", todasAtvInsumos.get(i).getID_INSUMO());
                    obj.put("ID_PROGRAMACAO_ATIVIDADE", todasAtvInsumos.get(i).getID_PROGRAMACAO_ATIVIDADE());
                    obj.put("RECOMENDACAO", todasAtvInsumos.get(i).getRECOMENDACAO());
                    obj.put("QTD_HA_RECOMENDADO", todasAtvInsumos.get(i).getQTD_HA_RECOMENDADO());
                    obj.put("QTD_HA_APLICADO", todasAtvInsumos.get(i).getQTD_HA_APLICADO());

                    //Log.e("Objeto Atv_insumos", obj.toString());
                    Log.e("Put atv_insumos", requisicaoPUT(HOST_PORTA + "silvosatividadeinsumos" + "/" +
                            todasAtvInsumos.get(i).getID_PROGRAMACAO_ATIVIDADE() + "&" +
                            todasAtvInsumos.get(i).getID_INSUMO(), obj.toString()));
                    /*requisicaoPUT(HOST_PORTA + "silvosatividadeinsumos" + "/" +
                            todasAtvInsumos.get(i).getID_PROGRAMACAO_ATIVIDADE() + "&" +
                            todasAtvInsumos.get(i).getID_INSUMO(), obj.toString());*/
                }
            } catch (JSONException ex) {
                Log.e("O_S_ATIVIDADE_INSUMOS", "Erro ao instanciar objeto para requisição PUT");
                ex.printStackTrace();
            }


            try {
                List<INDICADORES_SUBSOLAGEM> listaIndSubs = dao.todosIndicadoresSubsolagem();

                for (Integer i = 0; i < listaIndSubs.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("ID_PROGRAMACAO_ATIVIDADE", listaIndSubs.get(i).getID_PROGRAMACAO_ATIVIDADE());
                    obj.put("DATA", listaIndSubs.get(i).getDATA());
                    obj.put("ID_ATIVIDADE", listaIndSubs.get(i).getID_ATIVIDADE());
                    obj.put("ID_INDICADOR", listaIndSubs.get(i).getID_INDICADOR());
                    obj.put("VALOR_INDICADOR", listaIndSubs.get(i).getID_INDICADOR());

                    //Log.e("POST indicadores subsolagens", requisicaoPOST(HOST_PORTA +"silvindicadoressubsolagens",
                      //      obj.toString()));
                    requisicaoPOST(HOST_PORTA +"silvindicadoressubsolagens",
                            obj.toString());
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
                Log.e("INDICADOR_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST");
            }


            try {
                List<AVAL_PONTO_SUBSOLAGEM> listaPontos = dao.todosPontos();

                for (Integer i = 0; i < listaPontos.size(); i++) {
                    JSONObject obj = new JSONObject();
                   if(listaPontos.get(i).getUPDATED_AT()==null) {
                       obj.put("ID_PROGRAMACAO_ATIVIDADE", listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE());
                       obj.put("DATA", listaPontos.get(i).getDATA());
                       obj.put("ID_ATIVIDADE", listaPontos.get(i).getID_ATIVIDADE());
                       obj.put("ID_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                       obj.put("VALOR_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                       obj.put("PONTO", listaPontos.get(i).getPONTO());
                       obj.put("VALOR_INDICADOR", listaPontos.get(i).getVALOR_INDICADOR());
                       obj.put("COORDENADA_X", listaPontos.get(i).getCOORDENADA_X());
                       obj.put("COORDENADA_Y", listaPontos.get(i).getCOORDENADA_Y());
                       obj.put("NC_TRATADA", listaPontos.get(i).getNC_TRATADA());

                      // Log.e("POST aval_ponto_subsolagens", requisicaoPOST(HOST_PORTA +
                           //            "silvindavalpontosubsolagens", obj.toString()));
                       requisicaoPOST(HOST_PORTA +
                               "silvindavalpontosubsolagens", obj.toString());
                   }else{
                       obj.put("ID_PROGRAMACAO_ATIVIDADE", listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE());
                       obj.put("DATA", listaPontos.get(i).getDATA());
                       obj.put("ID_ATIVIDADE", listaPontos.get(i).getID_ATIVIDADE());
                       obj.put("ID_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                       obj.put("VALOR_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                       obj.put("PONTO", listaPontos.get(i).getPONTO());
                       obj.put("VALOR_INDICADOR", listaPontos.get(i).getVALOR_INDICADOR());
                       obj.put("COORDENADA_X", listaPontos.get(i).getCOORDENADA_X());
                       obj.put("COORDENADA_Y", listaPontos.get(i).getCOORDENADA_Y());
                       obj.put("NC_TRATADA", listaPontos.get(i).getNC_TRATADA());

                       /*Log.e("PUT aval_ponto_subsolagens", requisicaoPUT(HOST_PORTA +
                                       "silvindavalpontosubsolagens"+"/"+listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE()+"&"+
                                       listaPontos.get(i).getDATA()+"&"+listaPontos.get(i).getPONTO()+"&"+listaPontos.get(i).getID_ATIVIDADE()+
                                       "&"+listaPontos.get(i).getID_INDICADOR(),
                               obj.toString()));*/
                       requisicaoPUT(HOST_PORTA +
                                       "silvindavalpontosubsolagens"+"/"+listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE()+"&"+
                                       listaPontos.get(i).getDATA()+"&"+listaPontos.get(i).getPONTO()+"&"+listaPontos.get(i).getID_ATIVIDADE()+
                                       "&"+listaPontos.get(i).getID_INDICADOR(),
                               obj.toString());
                   }
                   }
            } catch (JSONException ex) {
                ex.printStackTrace();
                Log.e("AVAL_PONTO_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST ou PUT");
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvatividades"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new ATIVIDADES(ID_ATIVIDADE, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        //Log.e("Atividade na id", String.valueOf(ID_FUNCAO));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S24", "Sem resposta nas funcs,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "ggffuncoes"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_FUNCAO = obj.getInt("ID_FUNCAO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new GGF_FUNCOES(ID_FUNCAO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        //Log.e("Função na id", String.valueOf(ID_FUNCAO));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S1", "Sem resposta nas funcs,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "ggfdepartamentos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_DEPARTAMENTO = obj.getInt("ID_DEPARTAMENTO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new GGF_DEPARTAMENTOS(ID_DEPARTAMENTO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        //Log.e("Departamento na id", String.valueOf(ID_DEPARTAMENTO));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S2", "Sem resposta nos deps,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "ggfusuarios"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_USUARIO = obj.getInt("ID_USUARIO");
                    String EMAIL = obj.getString("EMAIL");
                    String SENHA = obj.getString("SENHA");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer NIVEL_ACESSO = 0;
                    try {
                        NIVEL_ACESSO = obj.getInt("NIVEL_ACESSO");
                    } catch (JSONException ex) {
                        Log.e("Erro ao converter nivel de acesso", "");
                        NIVEL_ACESSO = 0;
                    }
                    Integer ATIVO = obj.getInt("ATIVO");
                    Integer ID_DEPARTAMENTO = obj.getInt("ID_DEPARTAMENTO");
                    Integer ID_FUNCAO = obj.getInt("ID_FUNCAO");
                    try {
                        dao.insert(new GGF_USUARIOS(ID_USUARIO, ID_DEPARTAMENTO, ID_FUNCAO, SENHA, ATIVO, EMAIL, DESCRICAO, NIVEL_ACESSO));
                    } catch (SQLiteConstraintException e) {
                        //Log.e("Usuario na id", String.valueOf(ID_USUARIO));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S3", "Sem resposta nos users:json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvoperadores"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_OPERADORES = obj.getInt("ID_OPERADORES");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new OPERADORES(ID_OPERADORES, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        // Log.e("Operador na id", String.valueOf(ID_OPERADORES));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S4", "Sem resposta nos operadores,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvmaquinas"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_MAQUINA = obj.getInt("ID_MAQUINA");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new MAQUINAS(ID_MAQUINA, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        // Log.e("Maquina na id", String.valueOf(ID_MAQUINA));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S5", "Sem resposta maquinas,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvimplementos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_IMPLEMENTO = obj.getInt("ID_IMPLEMENTO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new IMPLEMENTOS(ID_IMPLEMENTO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        // Log.e("Implementp na id", String.valueOf(ID_IMPLEMENTO));
                        e.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S6", "Sem resposta implementos,json");
                ex.printStackTrace();
                contadorDeErros++;
            }

            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvmaquinaimplementos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_MAQUINA_IMPLEMENTO = obj.getInt("ID_MAQUINA_IMPLEMENTO");
                    Integer ID_MAQUINA = obj.getInt("ID_MAQUINA");
                    Integer ID_IMPLEMENTO = obj.getInt("ID_IMPLEMENTO");
                    try {
                        dao.insert(new MAQUINA_IMPLEMENTO(ID_MAQUINA_IMPLEMENTO, ID_MAQUINA, ID_IMPLEMENTO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //Log.e("Maquina Implemento na id", String.valueOf(ID_MAQUINA_IMPLEMENTO));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S8", "Sem resposta Maquina implementos,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvprestadores"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

                    Integer ID_PRESTADORES = obj.getInt("ID_PRESTADOR");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new PRESTADORES(ID_PRESTADORES, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        // Log.e("Prestador na id", String.valueOf(ID_PRESTADORES));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S10", "Sem resposta Prestador,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvmanejos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_MANEJO = obj.getInt("ID_MANEJO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new MANEJO(ID_MANEJO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //("Manejo na id", String.valueOf(ID_MANEJO));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S11", "Sem resposta Manejos,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvespacamentos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_ESPACAMENTO = obj.getInt("ID_ESPACAMENTO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new ESPACAMENTOS(ID_ESPACAMENTO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //Log.e("Espacamentos na id", String.valueOf(ID_ESPACAMENTO));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S12", "Sem resposta Espacamentos,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "georegionais"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_REGIONAL = obj.getInt("ID_REGIONAL");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer SETOR_TODOS = obj.getInt("SETOR_TODOS");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new GEO_REGIONAIS(ID_REGIONAL, DESCRICAO, SETOR_TODOS, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //Log.e("Regionais na id", String.valueOf(ID_REGIONAL));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S13", "Sem resposta Geo Regionais,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "geosetores"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_REGIONAL = obj.getInt("ID_REGIONAL");
                    Integer ID_SETOR = obj.getInt("ID_SETOR");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new GEO_SETORES(ID_REGIONAL, ID_SETOR, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //Log.e("Geo Setores na id", String.valueOf(ID_REGIONAL) + " , " + String.valueOf(ID_SETOR));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S14", "Sem resposta Geo Setores,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvmaterialgeneticos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_MATERIAL_GENETICO = obj.getInt("ID_MATERIAL_GENETICO");
                    String DESCRICAO = obj.getString("DESCRICAO");
                    Integer ATIVO = obj.getInt("ATIVO");
                    try {
                        dao.insert(new MATERIAL_GENETICO(ID_MATERIAL_GENETICO, DESCRICAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //("Material Genetico na id", String.valueOf(ID_MATERIAL_GENETICO));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S15", "Sem resposta Mat Genético,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvcadastroflorestals"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_REGIONAL = obj.getInt("ID_REGIONAL");
                    Integer ID_SETOR = obj.getInt("ID_SETOR");
                    String TALHAO = obj.getString("TALHAO");
                    Integer CICLO = obj.getInt("CICLO");
                    Integer ID_MANEJO = obj.getInt("ID_MANEJO");
                    String DATA_MANEJO = obj.getString("DATA_MANEJO");
                    String DATA_PROGRAMACAO_REFORMA = obj.getString("DATA_PROGRAMACAO_REFORMA");
                    Integer ID_MATERIAL_GENETICO = obj.getInt("ID_MATERIAL_GENETICO");
                    Integer ID_ESPACAMENTO = obj.getInt("ID_ESPACAMENTO");
                    String OBSERVACAO = obj.getString("OBSERVACAO");
                    Integer ATIVO = obj.getInt("ATIVO");

                    try {
                        dao.insert(new CADASTRO_FLORESTAL(ID_REGIONAL, ID_SETOR, TALHAO, CICLO, ID_MANEJO,
                                DATA_MANEJO, DATA_PROGRAMACAO_REFORMA, ID_MATERIAL_GENETICO, ID_ESPACAMENTO, OBSERVACAO, ATIVO));
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                        //Log.e("Cad Florestal na id", String.valueOf(ID_REGIONAL) + " , " + String.valueOf(ID_SETOR));
                    }
                }
            } catch (JSONException ex) {
                Log.e("S16", "Sem resposta Cad Florestal,json");
                ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividades"));

                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

                    //Log.e("Objeto "+String.valueOf(i), obj.toString());

                    Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                    Integer ID_REGIONAL = obj.getInt("ID_REGIONAL");
                    Integer ID_SETOR = obj.getInt("ID_SETOR");
                    String TALHAO = obj.getString("TALHAO");
                    Integer CICLO = obj.getInt("CICLO");
                    Integer ID_MANEJO = obj.getInt("ID_MANEJO");
                    Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                    Integer ID_RESPONSAVEL = null;
                    try {
                        ID_RESPONSAVEL = obj.getInt("ID_RESPONSAVEL");
                    } catch (JSONException ex) {
                        ID_RESPONSAVEL = null;
                    }
                    Integer PRIORIDADE = 4;
                    try {
                        PRIORIDADE = obj.getInt("PRIORIDADE");
                    } catch (JSONException ex) {
                        PRIORIDADE = 4;
                    }
                    Integer EXPERIMENTO = null;
                    try {
                        EXPERIMENTO = obj.getInt("EXPERIMENTO");
                    } catch (JSONException ex) {
                        EXPERIMENTO = null;
                    }
                    Integer MADEIRA_NO_TALHAO = 0;
                    try {
                        MADEIRA_NO_TALHAO = obj.getInt("MADEIRA_NO_TALHAO");
                    } catch (JSONException ex) {
                        MADEIRA_NO_TALHAO = 0;
                    }
                    Double AREA_REALIZADA = 0.0;
                    try {
                        AREA_REALIZADA = obj.getDouble("AREA_REALIZADA");
                    } catch (JSONException ex) {
                        AREA_REALIZADA = 0.0;
                    }
                    DecimalFormat format = new DecimalFormat(".##");

                    String s = format.format(AREA_REALIZADA).replace(',', '.');
                    AREA_REALIZADA = Double.parseDouble(s);

                    String DATA_PROGRAMADA = obj.getString("DATA_PROGRAMADA");
                    Double AREA_PROGRAMADA = obj.getDouble("AREA_PROGRAMADA");

                    String OBSERVACAO = obj.getString("OBSERVACAO");
                    String DATA_INICIAL = obj.getString("DATA_INICIAL");
                    String DATA_FINAL = obj.getString("DATA_FINAL");

                    String STATUS = obj.getString("STATUS");

                    Integer STATUS_NUM = 0;
                    if (STATUS == null) {
                        STATUS_NUM = 0;
                        STATUS = "Aberto";
                    }
                    if (STATUS.equals("A")) {
                        STATUS_NUM = 1;
                        STATUS = "Andamento";
                    }
                    if (STATUS.equals("F")) {
                        STATUS_NUM = 2;
                        STATUS = "Finalizado";
                    }


                    if (OBSERVACAO == null) OBSERVACAO = "";

                    try {
                        dao.insert(new O_S_ATIVIDADES(ID_PROGRAMACAO_ATIVIDADE, ID_REGIONAL, ID_SETOR, TALHAO, CICLO,
                                ID_MANEJO, ID_ATIVIDADE, ID_RESPONSAVEL, DATA_PROGRAMADA, AREA_PROGRAMADA, PRIORIDADE,
                                EXPERIMENTO, MADEIRA_NO_TALHAO, OBSERVACAO, DATA_INICIAL, DATA_FINAL, AREA_REALIZADA,
                                STATUS, STATUS_NUM));
                    } catch (SQLiteConstraintException ex) {
                        //Log.e("Os Atividade na id", String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                        ex.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                Log.e("S17", "Sem resposta Os_Atividades,json");
                ex.printStackTrace();
                contadorDeErros++;
            }
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividadesdias"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);

                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                Integer ID = obj.getInt("ID");
                String DATA = obj.getString("DATA");
                Integer ID_PRESTADOR = obj.getInt("ID_PRESTADOR");
                Integer ID_RESPONSAVEL = obj.getInt("ID_RESPONSAVEL");
                String STATUS = obj.getString("STATUS");
                String AREA_REALIZADA = String.valueOf(obj.getString("AREA_REALIZADA"));
                String HH = String.valueOf(obj.getString("HH"));
                String HM = String.valueOf(obj.getString("HM"));
                String HO = String.valueOf(obj.getString("HO"));
                String HM_ESCAVADEIRA = String.valueOf(obj.getString("HM_ESCAVADEIRA"));
                String HO_ESCAVADEIRA = String.valueOf(obj.getString("HO_ESCAVADEIRA"));
                String OBSERVACAO = obj.getString("OBSERVACAO");
                String REGISTRO_DESCARREGADO = obj.getString("REGISTRO_DESCARREGADO");
                String ACAO_INATIVO = obj.getString("ACAO_INATIVO");

                O_S_ATIVIDADES_DIA oSAtividadesDia = new O_S_ATIVIDADES_DIA();
                oSAtividadesDia.setID_PROGRAMACAO_ATIVIDADE(ID_PROGRAMACAO_ATIVIDADE);
                oSAtividadesDia.setID(ID);
                oSAtividadesDia.setDATA(DATA);
                oSAtividadesDia.setID_PRESTADOR(ID_PRESTADOR);
                oSAtividadesDia.setID_RESPONSAVEL(ID_RESPONSAVEL);
                oSAtividadesDia.setSTATUS(STATUS);
                oSAtividadesDia.setAREA_REALIZADA(AREA_REALIZADA);
                oSAtividadesDia.setHH(HH);
                oSAtividadesDia.setHM(HM);
                oSAtividadesDia.setHO(HO);
                oSAtividadesDia.setHM_ESCAVADEIRA(HM_ESCAVADEIRA);
                oSAtividadesDia.setHO_ESCAVADEIRA(HO_ESCAVADEIRA);
                oSAtividadesDia.setOBSERVACAO(OBSERVACAO);
                oSAtividadesDia.setREGISTRO_DESCARREGADO(REGISTRO_DESCARREGADO);
                oSAtividadesDia.setACAO_INATIVO(ACAO_INATIVO);

                if (dao.selecionaAtvDiaOracle(oSAtividadesDia.getID()) != null) {
                    dao.update(oSAtividadesDia);
                } else {
                    dao.insert(oSAtividadesDia);
                }
            }
        } catch (JSONException ex) {
            Log.e("S18", "Sem resposta Atividades_Dia,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvinsumos"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);

                Integer ID_INSUMO = obj.getInt("ID_INSUMO");
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
                Integer ATIVO = obj.getInt("ATIVO");
                String UND_MEDIDA = obj.getString("UND_MEDIDA");
                try {
                    dao.insert(new INSUMOS(ID_INSUMO, ID_INSUMO_RM, CLASSE, DESCRICAO, NUTRIENTE_N,
                            NUTRIENTE_P2O5, NUTRIENTE_K2O, NUTRIENTE_CAO, NUTRIENTE_MGO, NUTRIENTE_B, NUTRIENTE_ZN, NUTRIENTE_S, NUTRIENTE_CU,
                            NUTRIENTE_AF, NUTRIENTE_MN, ATIVO, UND_MEDIDA));
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                    //Log.e("Insumo na id", String.valueOf(ID_INSUMO));
                }
            }
        } catch (JSONException ex) {
            Log.e("S19", "Sem resposta insumos,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvatividadeindicadores"));

            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);

                Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                Integer ID_INDICADOR = obj.getInt("ID_INDICADOR");
                Integer ORDEM_INDICADOR = obj.getInt("ORDEM_INDICADOR");
                String REFERENCIA = obj.getString("REFERENCIA");
                String DESCRICAO = obj.getString("DESCRICAO");
                Integer ATIVO = obj.getInt("ATIVO");
                String VERION = obj.getString("VERION");
                String FORMULA = obj.getString("FORMULA");
                Integer INDICADOR_CORRIGIVEL;
                Integer LIMITE_INFERIOR;
                Integer LIMITE_SUPERIOR;
                Integer CASAS_DECIMAIS;

                try {
                    INDICADOR_CORRIGIVEL = obj.getInt("INDICADOR_CORRIGIVEL");
                } catch (Exception ex) {
                    INDICADOR_CORRIGIVEL = 0;
                }

                try {
                    LIMITE_INFERIOR = obj.getInt("LIMITE_INFERIOR");
                } catch (Exception ex) {
                    LIMITE_INFERIOR = 1;
                }

                try {
                    LIMITE_SUPERIOR = obj.getInt("LIMITE_SUPERIOR");
                } catch (Exception ex) {
                    LIMITE_SUPERIOR = 1000;
                }

                try {
                    CASAS_DECIMAIS = obj.getInt("CASAS_DECIMAIS");
                } catch (Exception ex) {
                    CASAS_DECIMAIS = 0;
                }

                try {
                    dao.insert(new ATIVIDADE_INDICADORES(ID_INDICADOR, ID_ATIVIDADE, ORDEM_INDICADOR, REFERENCIA, DESCRICAO, ATIVO, VERION, LIMITE_SUPERIOR, LIMITE_INFERIOR, CASAS_DECIMAIS, INDICADOR_CORRIGIVEL, FORMULA));
                } catch (SQLiteConstraintException e) {
                    //Log.e("Atividade Indicadores na id", String.valueOf(ID_INDICADOR) + " , " + String.valueOf(ID_ATIVIDADE));
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S20", "Sem resposta atividade_indicadores,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvcalibragemsubsolagens"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                String DATA = obj.getString("DATA");
                String TURNO = obj.getString("TURNO");
                Integer ID_MAQUINA_IMPLEMENTO = obj.getInt("ID_MAQUINA_IMPLEMENTO");
                Integer ID_OPERADOR = obj.getInt("ID_OPERADOR");
                Double P1_MEDIA = obj.getDouble("P1_MEDIA");
                Double P1_DESVIO = obj.getDouble("P1_DESVIO");
                Double P2_MEDIA = obj.getDouble("P2_MEDIA");
                Double P2_DESVIO = obj.getDouble("P2_DESVIO");


                try {
                    dao.insert(new CALIBRAGEM_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, DATA, TURNO,
                            ID_MAQUINA_IMPLEMENTO, ID_OPERADOR, P1_MEDIA, P1_DESVIO, P2_MEDIA, P2_DESVIO));
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                    //Log.e("Calibragem na id", String.valueOf(ID_MAQUINA_IMPLEMENTO) + " , " + DATA);
                }
            }
        } catch (JSONException ex) {
            Log.e("S9", "Sem resposta calibração,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividadeinsumos"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Integer ID_INSUMO = obj.getInt("ID_INSUMO");
                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                Integer RECOMENDACAO = obj.getInt("RECOMENDACAO");
                double QTD_HA_RECOMENDADO = obj.getDouble("QTD_HA_RECOMENDADO");
                double QTD_HA_APLICADO = obj.getDouble("QTD_HA_APLICADO");
                try {
                    dao.insert(new O_S_ATIVIDADE_INSUMOS(ID_INSUMO, ID_PROGRAMACAO_ATIVIDADE, RECOMENDACAO, QTD_HA_RECOMENDADO, QTD_HA_APLICADO));
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                    // Log.e("Atividade Insumos na id", String.valueOf(ID_INSUMO) + " , " + String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                }
            }
        } catch (JSONException ex) {
            Log.e("S21", "Sem resposta atividade_insumos,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosindavalsubsolagens"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                double PROFUNDIDADE = obj.getDouble("PROFUNDIDADE");
                double ESTRONDAMENTO_LATERAL_INFERIOR = obj.getDouble("ESTRONDAMENTO_LATERAL_INFERIOR");
                double ESTRONDAMENTO_LATERAL_SUPERIOR = obj.getDouble("ESTRONDAMENTO_LATERAL_SUPERIOR");
                double FAIXA_SOLO_PREPARADA = obj.getDouble("FAIXA_SOLO_PREPARADA");
                double PROFUNDIDADE_ADUBO_INFERIOR = obj.getDouble("PROFUNDIDADE_ADUBO_INFERIOR");
                double PROFUNDIDADE_ADUBO_SUPERIOR = obj.getDouble("PROFUNDIDADE_ADUBO_SUPERIOR");
                double LOCALIZACAO_INSUMO_INFERIOR = obj.getDouble("LOCALIZACAO_INSUMO_INFERIOR");
                double LOCALIZACAO_INSUMO_SUPERIOR = obj.getDouble("LOCALIZACAO_INSUMO_SUPERIOR");

                try {
                    dao.insert(new AVAL_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, PROFUNDIDADE, ESTRONDAMENTO_LATERAL_INFERIOR,
                            ESTRONDAMENTO_LATERAL_SUPERIOR, FAIXA_SOLO_PREPARADA, PROFUNDIDADE_ADUBO_INFERIOR,
                            PROFUNDIDADE_ADUBO_SUPERIOR, LOCALIZACAO_INSUMO_INFERIOR, LOCALIZACAO_INSUMO_SUPERIOR));
                } catch (SQLiteConstraintException e) {
                    //Log.e("Aval Sbsolagem na id", String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S22", "Sem resposta aval_subsolagem,json");
            ex.printStackTrace();
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvinsumoatividades"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                Integer ID_INSUMO = obj.getInt("ID_INSUMO");
                Integer ATIVO = obj.getInt("ATIVO");
                try {
                    dao.insert(new INSUMO_ATIVIDADES(ID_INSUMO, ID_ATIVIDADE, ATIVO));
                } catch (SQLiteConstraintException e) {
                    // Log.e("Insumo_atividades na id", String.valueOf(ID_ATIVIDADE));
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S23", "Sem resposta nas atividades,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividadeinsumosdias"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);

                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                String DATA = obj.getString("DATA");
                Integer ID_INSUMO = obj.getInt("ID_INSUMO");
                double QTD_APLICADO = obj.getDouble("QTD_APLICADO");
                String ACAO_INATIVO = obj.getString("ACAO_INATIVO");
                String REGISTRO_DESCARREGADO = obj.getString("REGISTRO_DESCARREGADO");
                String OBSERVACAO = obj.getString("OBSERVACAO");
                Integer ID = obj.getInt("ID");

                try {
                    dao.insert(new O_S_ATIVIDADE_INSUMOS_DIA(ID, ID_PROGRAMACAO_ATIVIDADE, DATA,
                            ID_INSUMO, QTD_APLICADO, ACAO_INATIVO, REGISTRO_DESCARREGADO, OBSERVACAO));
                } catch (SQLiteConstraintException e) {
                    //Log.e("atividade_insumos_dia na id", String.valueOf(ID_PROGRAMACAO_ATIVIDADE) + ", " + DATA);
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S24", "Sem resposta nas atividade_insumos_dia,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvindicadoressubsolagens"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
               // Log.e("Objeto indicadores_subsolagem", obj.toString());

                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                String DATA = obj.getString("DATA");
                Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                Integer ID_INDICADOR = obj.getInt("ID_INDICADOR");
                double VALOR_INDICADOR = obj.getDouble("VALOR_INDICADOR");

                try {
                    dao.insert(new INDICADORES_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, ID_ATIVIDADE, ID_INDICADOR, DATA, VALOR_INDICADOR));
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S25", "Sem resposta indicadores_subsolagem,json");
            ex.printStackTrace();
            contadorDeErros++;
        }


        try {
            response = new JSONArray(requisicaoGET(HOST_PORTA + "silvindavalpontosubsolagens"));
            for (Integer i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                //Log.e("Objeto ponto_subsolagem", obj.toString());

                Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                String DATA = obj.getString("DATA");
                Integer PONTO = obj.getInt("PONTO");
                Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                Integer ID_INDICADOR = obj.getInt("ID_INDICADOR");
                double VALOR_INDICADOR;
                try {
                    VALOR_INDICADOR = obj.getDouble("VALOR_INDICADOR");
                } catch (Exception e) {
                    VALOR_INDICADOR = 0;
                }

                double COORDENADA_X;
                try {
                    COORDENADA_X = obj.getDouble("COORDENADA_X");
                } catch (Exception e) {
                    COORDENADA_X = 0;
                }

                double COORDENADA_Y;
                try {
                    COORDENADA_Y = obj.getDouble("COORDENADA_Y");
                } catch (Exception e) {
                    COORDENADA_Y = 0;
                }

                Integer NC_TRATADA = obj.getInt("NC_TRATADA");

                AVAL_PONTO_SUBSOLAGEM pontoSubsolagem = new AVAL_PONTO_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, DATA, PONTO, ID_ATIVIDADE, ID_INDICADOR, VALOR_INDICADOR, COORDENADA_X,
                        COORDENADA_Y, NC_TRATADA);

                pontoSubsolagem.setUPDATED_AT(obj.getString("UPDATED_AT"));
                try {
                    dao.insert(pontoSubsolagem);
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            Log.e("S26", "Sem resposta ponto_subsolagem,json");
            ex.printStackTrace();
            contadorDeErros++;
        }



        finalizouSinc = true;
    }
}