package com.example.forestsys.Assets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.forestsys.Classes.ATIVIDADES;
import com.example.forestsys.Classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.ESPACAMENTOS;
import com.example.forestsys.Classes.GEO_LOCALIZACAO;
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
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityInicializacao.HOST_PORTA;
import static com.example.forestsys.Activities.ActivityInicializacao.conectado;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;

public class ClienteWeb<client> {

    private static DAO dao;

    private final Ferramentas ferramentas = new Ferramentas();

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new OkHttpProfilerInterceptor())
            .callTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build();

    public static boolean finalizouSinc = false;

    private static BaseDeDados baseDeDados;

    private static Context activity;

    public static Integer contadorDeErros;

    public static boolean erroNoOracle;
    private static Configs configs;

    private static Date dataMaximaIntervalo;

    private SimpleDateFormat sdf;
    private Calendar cal;
    private String dataAtual;

    @SuppressLint("LongLogTag")
    public ClienteWeb(Context context) throws ParseException {
        activity = context;
        baseDeDados = BaseDeDados.getInstance(activity);
        dao = baseDeDados.dao();

        configs = dao.selecionaConfigs();

        if (configs != null) {

            sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = Calendar.getInstance();
            dataAtual = sdf.format(cal.getTime()).trim();

            cal.add(Calendar.DATE, (configs.getPermanenciaDosDados()) - 1);

            dataMaximaIntervalo = cal.getTime();

            //Log.wtf("Data intervalo",sdf.format(dataMaximaIntervalo.getTime()));
            //Log.wtf("Permanencia", String.valueOf(configs.getPermanenciaDosDados()));

            /*boolean jaApagouHoje = false;
            if (configs.getDataParaApagarDados() != null) {
                if (configs.getDataParaApagarDados().trim().equals(dataAtual)) {
                    jaApagouHoje = true;
                }
            }

            if (date1.getTime() >= date2.getTime()) {
                if (jaApagouHoje == false) {
                        chegouDataApagarTudo = true;
                        baseDeDados.clearAllTables();

                        configs.setDataParaApagarDados(calculaDataParaApagarDados(configs.getPermanenciaDosDados().toString()));
                        configs.setUltimaDataQueApagou(dataAtual);
                        Log.wtf("Próxima data para apagar", configs.getDataParaApagarDados());
                        dao.insert(configs);
                    } else {
                        chegouDataApagarTudo = false;
                    }
                }

                if (chegouDataApagarTudo == true) {
                    Log.wtf("Apagou?", " Sim");
                } else {
                    Log.wtf("Apagou?", " Não");
                }*/
        }
    }


    private static String requisicaoPOST(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            response.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String requisicaoGET(String url) throws IOException {

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        okhttp3.Response response = client.newCall(request).execute();

        String jsonDeResposta = response.body().string();

        response.close();
        return jsonDeResposta;
    }


    private static String requisicaoPUT(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            response.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String ignoraHoras(String data) {
        if (data == null || data.length() == 0) return null;

        if (data.length() == 10) return data;

        String s = data.substring(0, 10);
        return s;
    }

    private static void calculaAreaRealizada(int id, Context context) {
        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        double calcula = 0;
        List<O_S_ATIVIDADES_DIA> listaReg = dao.listaAtividadesDia(id);
        for (int i = 0; i < listaReg.size(); i++) {
            String s = listaReg.get(i).getAREA_REALIZADA().replace(',', '.');
            double d = 0;
            try {
                d = Double.valueOf(s);
            } catch (Exception e) {
                e.printStackTrace();
                d = 0;
            }
            calcula += d;
        }
        O_S_ATIVIDADES atividade = dao.selecionaOs(id);

        BigDecimal bd = BigDecimal.valueOf(calcula);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        atividade.setAREA_REALIZADA(bd.doubleValue());
        dao.update(atividade);
        //Log.wtf("Area Realizada", String.valueOf(bd.doubleValue()));

    }

    @SuppressLint("LongLogTag")
    public static void sincronizaWebService() throws Exception, IOException {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    super.onPostExecute(aVoid);
                }
            }
        }.execute();

    }

    public static double corrigeDec(String s) {
        s = s.replace(".", ",");

        if (s.contains(",")) {
            String[] antesDaVirgula = s.split(",");
            if (antesDaVirgula.length > 1) {
                int casasDecimais = antesDaVirgula[1].length();
                if (casasDecimais > 2) {
                    String corrigeDecimais = antesDaVirgula[1].substring(0, 2);
                    s = antesDaVirgula[0] + "," + corrigeDecimais;
                }
            }
        }
        s = s.replace(",", ".");
        //Log.wtf("Valor indicador", s);
        return Double.valueOf(s);
    }

    @SuppressLint("LongLogTag")
    public static void populaBdComWebService() throws IOException, Exception {
        finalizouSinc = false;
        conectado = false;
        erroNoOracle = false;
        JSONArray response;

        try {
            URL myUrl = new URL(HOST_PORTA);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(300000);
            connection.connect();
            Log.wtf("Conectado a", myUrl.toString());
            conectado = true;
            finalizouSinc = false;
        } catch (Exception e) {
            Log.wtf("Erro", e.toString() + " ao conectar a: " + HOST_PORTA);
            conectado = false;
            finalizouSinc = true;
        }

        if (conectado == true && finalizouSinc == false) {

            okhttp3.Request request = new okhttp3.Request.Builder().url(HOST_PORTA + "ggfusuarios").build();

            okhttp3.Response resposta = client.newCall(request).execute();

            if (resposta.code() == 200) {
                erroNoOracle = false;
            } else {
                erroNoOracle = true;
                conectado = false;
                finalizouSinc = true;
            }
            resposta.close();
        }


        if (conectado == true && finalizouSinc == false && erroNoOracle == false) {
            try {
                List<CALIBRAGEM_SUBSOLAGEM> todasCalibragens = dao.todasCalibragens();
                for (Integer i = 0; i < todasCalibragens.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("ID_PROGRAMACAO_ATIVIDADE", todasCalibragens.get(i).getID_PROGRAMACAO_ATIVIDADE());
                    obj.put("DATA", todasCalibragens.get(i).getDATA() + " 00:00:00");
                    obj.put("TURNO", todasCalibragens.get(i).getTURNO());
                    obj.put("ID_MAQUINA_IMPLEMENTO", todasCalibragens.get(i).getID_MAQUINA_IMPLEMENTO());
                    obj.put("ID_OPERADOR", todasCalibragens.get(i).getID_OPERADOR());
                    obj.put("P1_MEDIA", todasCalibragens.get(i).getP1_MEDIA());
                    obj.put("P1_DESVIO", todasCalibragens.get(i).getP1_DESVIO());
                    obj.put("P2_MEDIA", todasCalibragens.get(i).getP2_MEDIA());
                    obj.put("P2_DESVIO", todasCalibragens.get(i).getP2_DESVIO());

                    if (todasCalibragens.get(i).getUPDATED_AT() == null) {
                        requisicaoPOST(HOST_PORTA + "silvcalibragemsubsolagens", obj.toString());
                    }
                }
            } catch (Exception ex) {
                // Log.wtf("CALIBRAGEM_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST");
                //ex.printStackTrace();
                //contadorDeErros ++;
            }

            try {
                List<O_S_ATIVIDADES_DIA> todasOsAtividadesDia = dao.todasOsAtividadesDia();
                boolean naoFazPut = false;
                for (Integer i = 0; i < todasOsAtividadesDia.size(); i++) {
                    if (todasOsAtividadesDia.get(i).isEXPORT_PROXIMA_SINC() == true) {
                        JSONObject obj = new JSONObject();
                        if (todasOsAtividadesDia.get(i).getID() == null || todasOsAtividadesDia.get(i).getID() == JSONObject.NULL) {
                            obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadesDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                            obj.put("DATA", todasOsAtividadesDia.get(i).getDATA() + " 00:00:00");
                            obj.put("AREA_REALIZADA", todasOsAtividadesDia.get(i).getAREA_REALIZADA());
                            obj.put("HH", todasOsAtividadesDia.get(i).getHH());
                            obj.put("HM", todasOsAtividadesDia.get(i).getHM());
                            obj.put("HO", todasOsAtividadesDia.get(i).getHO());
                            obj.put("ID_PRESTADOR", todasOsAtividadesDia.get(i).getID_PRESTADOR());
                            obj.put("ID_RESPONSAVEL", todasOsAtividadesDia.get(i).getID_RESPONSAVEL());
                            obj.put("OBSERVACAO", todasOsAtividadesDia.get(i).getOBSERVACAO());
                            obj.put("REGISTRO_DESCARREGADO", todasOsAtividadesDia.get(i).getREGISTRO_DESCARREGADO());


                            if (todasOsAtividadesDia.get(i).getACAO_INATIVO() == null) {
                                obj.put("ACAO_INATIVO", null);
                            } else {
                                obj.put("ACAO_INATIVO", todasOsAtividadesDia.get(i).getACAO_INATIVO());
                            }
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

                            String POST = null;
                            boolean fezOPost = false;
                            try {
                                POST = requisicaoPOST(HOST_PORTA + "silvosatividadesdias", obj.toString());
                                fezOPost = true;
                            } catch (Exception ex) {
                                fezOPost = false;
                            }
                            if (fezOPost == true && POST != null) {
                                JSONObject updateId = new JSONObject(POST);
                                Integer idRetornado = updateId.getInt("ID");
                                if (idRetornado != null || idRetornado != JSONObject.NULL) {
                                    todasOsAtividadesDia.get(i).setID(idRetornado);
                                    dao.update(todasOsAtividadesDia.get(i));
                                }
                            }
                        } else {
                            naoFazPut = false;
                            obj.put("ID", todasOsAtividadesDia.get(i).getID());
                            obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadesDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                            obj.put("DATA", todasOsAtividadesDia.get(i).getDATA() + " 00:00:00");
                            obj.put("AREA_REALIZADA", todasOsAtividadesDia.get(i).getAREA_REALIZADA());
                            obj.put("HH", todasOsAtividadesDia.get(i).getHH());
                            obj.put("HM", todasOsAtividadesDia.get(i).getHM());
                            obj.put("HO", todasOsAtividadesDia.get(i).getHO());
                            obj.put("HM_ESCAVADEIRA", todasOsAtividadesDia.get(i).getHM_ESCAVADEIRA());
                            obj.put("HO_ESCAVADEIRA", todasOsAtividadesDia.get(i).getHO_ESCAVADEIRA());
                            obj.put("ID_PRESTADOR", todasOsAtividadesDia.get(i).getID_PRESTADOR());
                            obj.put("ID_RESPONSAVEL", todasOsAtividadesDia.get(i).getID_RESPONSAVEL());
                            obj.put("OBSERVACAO", todasOsAtividadesDia.get(i).getOBSERVACAO());
                            obj.put("REGISTRO_DESCARREGADO", todasOsAtividadesDia.get(i).getREGISTRO_DESCARREGADO());

                            String ACAO_INATIVO = todasOsAtividadesDia.get(i).getACAO_INATIVO();

                            obj.put("ACAO_INATIVO", ACAO_INATIVO);

                            if (ACAO_INATIVO == null || ACAO_INATIVO.trim().equals("null") || obj.getString("REGISTRO_DESCARREGADO").trim().equals("S") ||
                                    ACAO_INATIVO.trim().equals("null")) {
                                naoFazPut = true;
                            }

                            if (naoFazPut == false) {
                                requisicaoPUT(HOST_PORTA + "silvosatividadesdias" + "/" +
                                        String.valueOf(todasOsAtividadesDia.get(i).getID()), obj.toString());
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                //Log.wtf("ATIVIDADES_DIA", "Erro ao instanciar objeto para requisição POST ou PUT");
                //ex.printStackTrace();
                //contadorDeErros ++;
            }


            try {
                List<O_S_ATIVIDADE_INSUMOS_DIA> todasOsAtividadeInsumoDia = dao.todasOsAtvInsumosDia();
                boolean naoFazPut = false;

                for (Integer i = 0; i < todasOsAtividadeInsumoDia.size(); i++) {
                    if (todasOsAtividadeInsumoDia.get(i).isEXPORT_PROXIMA_SINC() == true) {
                        JSONObject obj = new JSONObject();

                        if (todasOsAtividadeInsumoDia.get(i).getID() == null || todasOsAtividadeInsumoDia.get(i).getID() == JSONObject.NULL) {
                            obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadeInsumoDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                            obj.put("DATA", todasOsAtividadeInsumoDia.get(i).getDATA() + " 00:00:00");
                            obj.put("ID_INSUMO", todasOsAtividadeInsumoDia.get(i).getID_INSUMO());
                            obj.put("QTD_APLICADO", todasOsAtividadeInsumoDia.get(i).getQTD_APLICADO());
                            obj.put("REGISTRO_DESCARREGADO", todasOsAtividadeInsumoDia.get(i).getREGISTRO_DESCARREGADO());
                            obj.put("OBSERVACAO", todasOsAtividadeInsumoDia.get(i).getOBSERVACAO());
                            obj.put("ACAO_INATIVO", todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO());

                            String POST = null;
                            boolean fezOPost;
                            try {
                                POST = requisicaoPOST(HOST_PORTA + "silvosatividadeinsumosdias", obj.toString());
                                fezOPost = true;
                            } catch (Exception ex) {
                                fezOPost = false;
                            }
                            if (fezOPost == true && POST != null) {
                                JSONObject updateId = new JSONObject(POST);
                                Integer idRetornado = updateId.getInt("ID");
                                if (idRetornado != null || idRetornado != JSONObject.NULL) {
                                    todasOsAtividadeInsumoDia.get(i).setID(idRetornado);
                                    dao.update(todasOsAtividadeInsumoDia.get(i));
                                }
                            }
                        } else {
                            naoFazPut = false;
                            obj.put("ID_PROGRAMACAO_ATIVIDADE", todasOsAtividadeInsumoDia.get(i).getID_PROGRAMACAO_ATIVIDADE());
                            obj.put("DATA", todasOsAtividadeInsumoDia.get(i).getDATA() + " 00:00:00");
                            obj.put("ID_INSUMO", todasOsAtividadeInsumoDia.get(i).getID_INSUMO());
                            obj.put("QTD_APLICADO", todasOsAtividadeInsumoDia.get(i).getQTD_APLICADO());
                            obj.put("REGISTRO_DESCARREGADO", todasOsAtividadeInsumoDia.get(i).getREGISTRO_DESCARREGADO());
                            obj.put("OBSERVACAO", todasOsAtividadeInsumoDia.get(i).getOBSERVACAO());
                            obj.put("ID", todasOsAtividadeInsumoDia.get(i).getID());

                            String ACAO_INATIVO = todasOsAtividadeInsumoDia.get(i).getACAO_INATIVO();

                            obj.put("ACAO_INATIVO", ACAO_INATIVO);

                            if (ACAO_INATIVO == null || ACAO_INATIVO.trim().equals("null") || obj.getString("REGISTRO_DESCARREGADO").trim().equals("S") ||
                                    ACAO_INATIVO.trim().equals("null")) {
                                naoFazPut = true;
                                obj.put("ACAO_INATIVO", null);
                            }


                            if (!naoFazPut) {
                                requisicaoPUT(HOST_PORTA + "silvosatividadeinsumosdias" + "/" +
                                        todasOsAtividadeInsumoDia.get(i).getID(), obj.toString());
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                if (ex != null) {
                    //Log.wtf("INSUMOS_ATIVIDADES_DIA", ex.getMessage());
                }
                ex.printStackTrace();
            }


            try {
                List<O_S_ATIVIDADES> todasOsAtividades = dao.todasOs();

                //Log.wtf("Tamanho lista OS", String.valueOf(todasOsAtividades.size()));

                Ferramentas ferramentas = new Ferramentas();
                for (Integer i = 0; i < todasOsAtividades.size(); i++) {

                    JSONObject objeto = new JSONObject(requisicaoGET(HOST_PORTA +
                            "silvosatividades" + "/" +
                            String.valueOf(todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE())));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date updateOracle = null;
                    Date updateApp = null;


                    try {
                        updateOracle = sdf.parse(objeto.getString("UPDATED_AT"));
                       // Log.wtf("Update do oracle", String.valueOf(updateOracle));
                    } catch (Exception exception) {
                        updateOracle = null;
                       // Log.wtf("Erro ao converter data Oracle", exception.getMessage());
                    }

                    try {
                        updateApp = sdf.parse(todasOsAtividades.get(i).getUPDATED_AT());
                     //   Log.wtf("Update do app", String.valueOf(updateApp));

                    } catch (Exception exception) {
                        updateApp = sdf.parse(ferramentas.dataHoraMinutosSegundosAtual());
                      //  Log.wtf("Erro ao converter data App", exception.getMessage());
                    }

                    /*try {
                        Log.wtf("Compare To", String.valueOf(updateApp.compareTo(updateOracle)));
                    }catch(Exception exception){
                        Log.wtf("Erro ao comparar datas Oracle e App", exception.getMessage());
                    }*/

                    if (objeto.getString("UPDATED_AT")== JSONObject.NULL ||
                            updateOracle == null || updateApp.compareTo(updateOracle) > 0){

                      //  Log.wtf("Update do app", "Posterior ao oracle");
                        Integer STATUS_NUM = todasOsAtividades.get(i).getSTATUS_NUM();

                        if (STATUS_NUM != 0) {
                            JSONObject obj = new JSONObject();

                            if (STATUS_NUM == 1) {
                                obj.put("STATUS", "A");
                            }
                            if (STATUS_NUM == 2) {
                                obj.put("STATUS", "F");
                            }


                            if (todasOsAtividades.get(i).getDATA_INICIAL() == null || todasOsAtividades.get(i).getDATA_INICIAL().trim().equals("null")) {
                                obj.put("DATA_INICIAL", JSONObject.NULL);
                            } else {
                                obj.put("DATA_INICIAL", todasOsAtividades.get(i).getDATA_INICIAL() + " 00:00:00");
                            }

                            if (todasOsAtividades.get(i).getOBSERVACAO() == null || todasOsAtividades.get(i).getOBSERVACAO().trim().equals("null")) {
                                obj.put("OBSERVACAO", JSONObject.NULL);
                            } else {
                                obj.put("OBSERVACAO", todasOsAtividades.get(i).getOBSERVACAO());
                            }

                            if (todasOsAtividades.get(i).getDATA_FINAL() == null || todasOsAtividades.get(i).getDATA_FINAL().trim().equals("null")) {
                                obj.put("DATA_FINAL", JSONObject.NULL);
                            } else {
                                obj.put("DATA_FINAL", todasOsAtividades.get(i).getDATA_FINAL() + " 00:00:00");
                            }

                            //  Log.wtf("Area Realizada put", String.valueOf(dao.somaAreaRealizada(todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE())));

                            obj.put("AREA_REALIZADA", todasOsAtividades.get(i).getAREA_REALIZADA());

                            requisicaoPUT(HOST_PORTA + "silvosatividades" + "/" +
                                    String.valueOf(todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE()), obj.toString());

                        }
                    }

                    if (updateOracle != null) {
                        if (updateApp.compareTo(updateOracle) < 0) {
                           // Log.wtf("Update do app", "anterior ao oracle");

                            try {
                                FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                                        "Usuário não logado", "Sincronização", "Sincronizou",
                                        String.valueOf("OS: " + todasOsAtividades.get(i).getID_PROGRAMACAO_ATIVIDADE() + " | Falha: Tentou sincronizar a atividade com o atributo " +
                                                "UPDATED_AT anterior a data do mesmo atributo para a mesma atividade presente no Oracle, quebrando as regras " +
                                                "de sincronização e integridade dos dados"));
                                dao.insert(registroLog);
                            } catch (Exception exx) {
                               // Log.wtf("Erro ao salvar log de erro na sinc", exx.getMessage());
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                //Log.wtf("OS_ATIVIDADES", "Erro ao instanciar objeto para requisição PUT");
                //Log.wtf("Erro ao fazer get ou Put nas OS", ex.getMessage());
                //contadorDeErros ++;
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

                    requisicaoPUT(HOST_PORTA + "silvosatividadeinsumos" + "/" +
                            todasAtvInsumos.get(i).getID_PROGRAMACAO_ATIVIDADE() + "&" +
                            todasAtvInsumos.get(i).getID_INSUMO(), obj.toString());
                }
            } catch (Exception ex) {
                // Log.wtf("O_S_ATIVIDADE_INSUMOS", "Erro ao instanciar objeto para requisição PUT");
                //ex.printStackTrace();
                //contadorDeErros ++;
            }


            try {
                List<INDICADORES_SUBSOLAGEM> listaIndSubs = dao.todosIndicadoresSubsolagem();

                for (Integer i = 0; i < listaIndSubs.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("ID_PROGRAMACAO_ATIVIDADE", listaIndSubs.get(i).getID_PROGRAMACAO_ATIVIDADE());
                    obj.put("DATA", listaIndSubs.get(i).getDATA() + " 00:00:00");
                    obj.put("ID_ATIVIDADE", listaIndSubs.get(i).getID_ATIVIDADE());
                    obj.put("ID_INDICADOR", listaIndSubs.get(i).getID_INDICADOR());
                    obj.put("VALOR_INDICADOR", listaIndSubs.get(i).getVALOR_INDICADOR());

                    requisicaoPUT(HOST_PORTA + "silvindicadoressubsolagens" + "/" + listaIndSubs.get(i).getID_PROGRAMACAO_ATIVIDADE() + "&" +
                                    listaIndSubs.get(i).getDATA() + "&" + listaIndSubs.get(i).getID_ATIVIDADE() + "&" + listaIndSubs.get(i).getID_INDICADOR()
                            , obj.toString());

                    requisicaoPOST(HOST_PORTA + "silvindicadoressubsolagens",
                            obj.toString());

                }
            } catch (Exception ex) {
                // Log.wtf("INDICADOR_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST");
                //  ex.printStackTrace();
                // contadorDeErros ++;
            }


            try {
                List<AVAL_PONTO_SUBSOLAGEM> listaPontos = dao.todosPontos();

                for (Integer i = 0; i < listaPontos.size(); i++) {
                    JSONObject obj = new JSONObject();


                    BigDecimal bd = BigDecimal.valueOf(listaPontos.get(i).getCOORDENADA_X());
                    bd = bd.setScale(5, RoundingMode.HALF_EVEN);
                    String lati = String.valueOf(bd.doubleValue());

                    bd = BigDecimal.valueOf(listaPontos.get(i).getCOORDENADA_Y());
                    bd = bd.setScale(5, RoundingMode.HALF_EVEN);
                    String longi = String.valueOf(bd.doubleValue());

                    if (listaPontos.get(i).getUPDATED_AT() == null) {
                        obj.put("ID_PROGRAMACAO_ATIVIDADE", listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE());
                        obj.put("DATA", listaPontos.get(i).getDATA() + " 00:00:00");
                        obj.put("ID_ATIVIDADE", listaPontos.get(i).getID_ATIVIDADE());
                        obj.put("ID_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                        obj.put("VALOR_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                        obj.put("PONTO", listaPontos.get(i).getPONTO());
                        obj.put("VALOR_INDICADOR", listaPontos.get(i).getVALOR_INDICADOR());
                        obj.put("COORDENADA_X", lati);
                        obj.put("COORDENADA_Y", longi);
                        obj.put("NC_TRATADA", listaPontos.get(i).getNC_TRATADA());

                        requisicaoPOST(HOST_PORTA +
                                "silvindavalpontosubsolagens", obj.toString());
                    } else {
                        if (listaPontos.get(i).getEditou() == 1) {
                            //obj.put("DATA", listaPontos.get(i).getDATA() + " 00:00:00");

                            obj.put("ID_PROGRAMACAO_ATIVIDADE", listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE());
                            obj.put("PONTO", listaPontos.get(i).getPONTO());
                            obj.put("ID_ATIVIDADE", listaPontos.get(i).getID_ATIVIDADE());
                            obj.put("ID_INDICADOR", listaPontos.get(i).getID_INDICADOR());
                            obj.put("VALOR_INDICADOR", listaPontos.get(i).getVALOR_INDICADOR());
                            obj.put("NC_TRATADA", listaPontos.get(i).getNC_TRATADA());

                            requisicaoPUT(HOST_PORTA +
                                            "silvindavalpontosubsolagens" + "/" + listaPontos.get(i).getID_PROGRAMACAO_ATIVIDADE() +
                                            "&" + listaPontos.get(i).getPONTO() + "&" + listaPontos.get(i).getID_ATIVIDADE() +
                                            "&" + listaPontos.get(i).getID_INDICADOR(),
                                    obj.toString());
                        }
                    }
                }
            } catch (Exception ex) {
                //   Log.wtf("AVAL_PONTO_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST ou PUT");
                //   ex.printStackTrace();
                //contadorDeErros ++;
            }

            try {
                List<FOREST_LOG> todosLogs = dao.todosLogs();
                for (Integer i = 0; i < todosLogs.size(); i++) {
                    JSONObject obj = new JSONObject();

                    obj.put("DATA", todosLogs.get(i).getDATA());
                    obj.put("DISPOSITIVO", todosLogs.get(i).getDISPOSITIVO());
                    obj.put("USUARIO", todosLogs.get(i).getUSUARIO());
                    obj.put("MODULO", todosLogs.get(i).getMODULO());
                    obj.put("ACAO", todosLogs.get(i).getACAO());
                    obj.put("VALOR", todosLogs.get(i).getVALOR());

                    String resposta = requisicaoPOST(HOST_PORTA + "forestlogs", obj.toString());

                    JSONObject updateId = new JSONObject(resposta);
                    Integer idRetornado = updateId.getInt("ID");
                    if (idRetornado != null || idRetornado != JSONObject.NULL) {
                        dao.delete(todosLogs.get(i));
                    }


                }
            } catch (Exception ex) {
                // Log.wtf("CALIBRAGEM_SUBSOLAGEM", "Erro ao instanciar objeto para requisição POST");
                //ex.printStackTrace();
                //contadorDeErros ++;
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S24", "Sem resposta nas funcs,json");
                //    ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S1", "Sem resposta nas funcs,json");
                //     ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S2", "Sem resposta nos deps,json");
                //    ex.printStackTrace();
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
                    } catch (Exception ex) {
                        // Log.wtf("Erro ao converter nivel de acesso", "");
                        NIVEL_ACESSO = 0;
                    }
                    Integer ATIVO = obj.getInt("ATIVO");
                    Integer ID_DEPARTAMENTO = obj.getInt("ID_DEPARTAMENTO");
                    Integer ID_FUNCAO = obj.getInt("ID_FUNCAO");
                    try {
                        dao.insert(new GGF_USUARIOS(ID_USUARIO, ID_DEPARTAMENTO, ID_FUNCAO, SENHA, ATIVO, EMAIL, DESCRICAO, NIVEL_ACESSO));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //   Log.wtf("S3", "Sem resposta nos users:json");
                //    ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //   Log.wtf("S4", "Sem resposta nos operadores,json");
                //   ex.printStackTrace();
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
                    } catch (Exception e) {
                    }
                }
            } catch (Exception ex) {
                //   Log.wtf("S5", "Sem resposta maquinas,json");
                //    ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //   Log.wtf("S6", "Sem resposta implementos,json");
                //   ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S8", "Sem resposta Maquina implementos,json");
                //     ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S10", "Sem resposta Prestador,json");
                //     ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        //("Manejo na id", String.valueOf(ID_MANEJO));
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S11", "Sem resposta Manejos,json");
                //    ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S12", "Sem resposta Espacamentos,json");
                //    ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S13", "Sem resposta Geo Regionais,json");
                //     ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S14", "Sem resposta Geo Setores,json");
                //     ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        //("Material Genetico na id", String.valueOf(ID_MATERIAL_GENETICO));
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S15", "Sem resposta Mat Genético,json");
                //    ex.printStackTrace();
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
                    Integer ID_MATERIAL_GENETICO = null;
                    Integer ID_ESPACAMENTO = null;
                    String OBSERVACAO = obj.getString("OBSERVACAO");
                    Integer ATIVO = obj.getInt("ATIVO");

                    if (OBSERVACAO == null || OBSERVACAO.trim().equals("null")) {
                        OBSERVACAO = null;
                    }

                    try {
                        ID_MATERIAL_GENETICO = obj.getInt("ID_MATERIAL_GENETICO");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        ID_ESPACAMENTO = obj.getInt("ID_ESPACAMENTO");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    dao.insert(new CADASTRO_FLORESTAL(ID_REGIONAL, ID_SETOR, TALHAO, CICLO, ID_MANEJO,
                            DATA_MANEJO, DATA_PROGRAMACAO_REFORMA, ID_MATERIAL_GENETICO, ID_ESPACAMENTO, OBSERVACAO, ATIVO));

                }
            } catch (Exception ex) {
                //    Log.wtf("S16", "Sem resposta Cad Florestal,json");
                //    ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividades"));

                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

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
                    } catch (Exception ex) {
                        ID_RESPONSAVEL = null;
                        ex.printStackTrace();
                    }

                    Integer PRIORIDADE = 4;
                    try {
                        PRIORIDADE = obj.getInt("PRIORIDADE");
                    } catch (Exception ex) {
                        PRIORIDADE = 4;
                        ex.printStackTrace();
                    }

                    Integer EXPERIMENTO = null;
                    try {
                        EXPERIMENTO = obj.getInt("EXPERIMENTO");
                    } catch (Exception ex) {
                        EXPERIMENTO = null;
                        ex.printStackTrace();
                    }

                    Integer MADEIRA_NO_TALHAO = 0;
                    try {
                        MADEIRA_NO_TALHAO = obj.getInt("MADEIRA_NO_TALHAO");
                    } catch (Exception ex) {
                        MADEIRA_NO_TALHAO = 0;
                        ex.printStackTrace();
                    }


                    DecimalFormat format = new DecimalFormat(".##");
                    String s;

                    String DATA_PROGRAMADA = obj.getString("DATA_PROGRAMADA");
                    double AREA_PROGRAMADA = obj.getDouble("AREA_PROGRAMADA");

                    s = format.format(AREA_PROGRAMADA).replace(',', '.');
                    AREA_PROGRAMADA = Double.valueOf(s);

                    String STATUS = obj.getString("STATUS");

                    Integer STATUS_NUM = 0;
                    if (STATUS == JSONObject.NULL || STATUS == null || STATUS.trim().equals("null") || STATUS.trim().equals("")) {
                        STATUS_NUM = 0;
                        STATUS = "Não iniciado";
                    }
                    if (STATUS.trim().equals("A")) {
                        STATUS_NUM = 1;
                        STATUS = "Andamento";
                    }
                    if (STATUS.trim().equals("F")) {
                        STATUS_NUM = 2;
                        STATUS = "Finalizado";
                    }

                    String OBSERVACAO = obj.getString("OBSERVACAO");
                    String DATA_INICIAL = obj.getString("DATA_INICIAL");
                    String DATA_FINAL = obj.getString("DATA_FINAL");

                    if (OBSERVACAO == null || OBSERVACAO.trim().equals("null")) {
                        OBSERVACAO = null;
                    }

                    if (DATA_INICIAL == null || DATA_INICIAL.trim().equals("null")) {
                        DATA_INICIAL = null;
                    }

                    if (DATA_FINAL == null || DATA_FINAL.trim().equals("null")) {
                        DATA_FINAL = null;
                    }

                    String UPDATED_AT = null;

                    SimpleDateFormat auxSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dataUpdate;

                    boolean updateNull = false;
                    try {
                        dataUpdate = auxSdf.parse(obj.getString("UPDATED_AT"));
                    }catch(Exception exception){
                        updateNull = true;
                        Ferramentas ferramentas = new Ferramentas();
                        UPDATED_AT = ferramentas.dataHoraMinutosSegundosAtual();
                    }

                    /*if(updateNull == false){
                        UPDATED_AT = obj.getString("UPDATED_AT");
                        Log.wtf("UPDATED_AT do get da atividade",
                                String.valueOf(ID_PROGRAMACAO_ATIVIDADE) + ", " + UPDATED_AT);
                    }*/

                    DATA_FINAL = ignoraHoras(DATA_FINAL);
                    DATA_INICIAL = ignoraHoras(DATA_INICIAL);
                    DATA_PROGRAMADA = ignoraHoras(DATA_PROGRAMADA);

                    boolean ignorarInsert = false;

                    if (STATUS_NUM == 2 && DATA_FINAL != null) {
                        String pattern = ("yyyy-MM-dd");
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        Date dataFinalDate = sdf.parse(DATA_FINAL.trim());
                        //Log.wtf("Data Final", String.valueOf(dataFinalDate.getTime()));
                        //Log.wtf("Data Intervalo", String.valueOf(dataMaximaIntervalo.getTime()));
                        if (dataFinalDate.compareTo(dataMaximaIntervalo) < 0) {
                            ignorarInsert = true;
                        }
                    }

                    if (ignorarInsert == false) {
                        try {
                            dao.insert(new O_S_ATIVIDADES(ID_PROGRAMACAO_ATIVIDADE, ID_REGIONAL, ID_SETOR, TALHAO, CICLO,
                                    ID_MANEJO, ID_ATIVIDADE, ID_RESPONSAVEL, DATA_PROGRAMADA, AREA_PROGRAMADA, PRIORIDADE,
                                    EXPERIMENTO, MADEIRA_NO_TALHAO, OBSERVACAO, DATA_INICIAL, DATA_FINAL, 0,
                                    STATUS, STATUS_NUM, UPDATED_AT));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        dao.apagaAtividade(ID_PROGRAMACAO_ATIVIDADE);
                      //  Log.wtf("Ignorar Atividade", String.valueOf(ID_PROGRAMACAO_ATIVIDADE));
                    }
                }
            } catch (Exception ex) {
                //Log.wtf("S17", "Sem resposta Os_Atividades,json");
                ex.printStackTrace();
                contadorDeErros++;
            }

            String ID_ERRO = null;
            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividadesdias"));
                for (Integer i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                        Integer ID = obj.getInt("ID");
                        ID_ERRO = String.valueOf(ID);
                        String DATA = obj.getString("DATA");
                        Integer ID_PRESTADOR = obj.getInt("ID_PRESTADOR");
                        Integer ID_RESPONSAVEL = obj.getInt("ID_RESPONSAVEL");
                        String HH = String.valueOf(obj.getString("HH"));
                        String HM = String.valueOf(obj.getString("HM"));
                        String HO = String.valueOf(obj.getString("HO"));
                        String HM_ESCAVADEIRA = String.valueOf(obj.getString("HM_ESCAVADEIRA"));
                        String HO_ESCAVADEIRA = String.valueOf(obj.getString("HO_ESCAVADEIRA"));
                        String OBSERVACAO = obj.getString("OBSERVACAO");
                        String REGISTRO_DESCARREGADO = obj.getString("REGISTRO_DESCARREGADO");
                        String ACAO_INATIVO = obj.getString("ACAO_INATIVO");


                        if (OBSERVACAO == null || OBSERVACAO.trim().equals("null")) {
                            OBSERVACAO = null;
                        }

                        if (ACAO_INATIVO == null || ACAO_INATIVO.trim().equals("null")) {
                            ACAO_INATIVO = null;
                        }

                        DATA = ignoraHoras(DATA);

                        double AREA_REALIZADA = 0.0;
                        try {
                            AREA_REALIZADA = obj.getDouble("AREA_REALIZADA");
                        } catch (Exception ex) {
                            AREA_REALIZADA = 0.0;
                            ex.printStackTrace();
                        }
                        DecimalFormat format = new DecimalFormat(".##");
                        String s;
                        s = format.format(AREA_REALIZADA).replace(',', '.');
                        AREA_REALIZADA = Double.valueOf(s);

                        O_S_ATIVIDADES_DIA oSAtividadesDia = new O_S_ATIVIDADES_DIA();
                        oSAtividadesDia.setID_PROGRAMACAO_ATIVIDADE(ID_PROGRAMACAO_ATIVIDADE);
                        oSAtividadesDia.setID(ID);
                        oSAtividadesDia.setDATA(DATA);
                        oSAtividadesDia.setID_PRESTADOR(ID_PRESTADOR);
                        oSAtividadesDia.setID_RESPONSAVEL(ID_RESPONSAVEL);
                        oSAtividadesDia.setAREA_REALIZADA(String.valueOf(AREA_REALIZADA).replace('.', ','));
                        oSAtividadesDia.setHH(HH);
                        oSAtividadesDia.setHM(HM);
                        oSAtividadesDia.setHO(HO);
                        oSAtividadesDia.setHM_ESCAVADEIRA(HM_ESCAVADEIRA);
                        oSAtividadesDia.setHO_ESCAVADEIRA(HO_ESCAVADEIRA);
                        oSAtividadesDia.setOBSERVACAO(OBSERVACAO);
                        oSAtividadesDia.setREGISTRO_DESCARREGADO(REGISTRO_DESCARREGADO);
                        oSAtividadesDia.setACAO_INATIVO(ACAO_INATIVO);
                        oSAtividadesDia.setEXPORT_PROXIMA_SINC(false);

                        dao.insert(oSAtividadesDia);

                    } catch (Exception exe) {
                        //Log.wtf("S18I", "Erro ao fazer insert ATIVIDADES_DIA ID: " + ID_ERRO);
                        //exe.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //Log.wtf("S18", "Sem resposta Atividades_Dia, Json");
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S19", "Sem resposta insumos,json");
                //    ex.printStackTrace();
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
                    String UNIDADE_MEDIDA = obj.getString("UNIDADE_MEDIDA");
                    Integer INDICADOR_CORRIGIVEL;
                    Integer LIMITE_INFERIOR;
                    Integer LIMITE_SUPERIOR;
                    Integer CASAS_DECIMAIS;

                    if (UNIDADE_MEDIDA == null || UNIDADE_MEDIDA == "null") {
                        UNIDADE_MEDIDA = "";
                    }

                    if (FORMULA == null || FORMULA == "null") {
                        FORMULA = null;
                    }
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
                        dao.insert(new ATIVIDADE_INDICADORES(ID_INDICADOR, ID_ATIVIDADE, ORDEM_INDICADOR, REFERENCIA,
                                DESCRICAO, ATIVO, VERION, LIMITE_SUPERIOR, LIMITE_INFERIOR, CASAS_DECIMAIS,
                                INDICADOR_CORRIGIVEL, FORMULA, UNIDADE_MEDIDA));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //    Log.wtf("S20", "Sem resposta atividade_indicadores,json");
                //      ex.printStackTrace();
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
                    String UPDATED_AT = obj.getString("UPDATED_AT");

                    DATA = ignoraHoras(DATA);

                    CALIBRAGEM_SUBSOLAGEM insereCalib = new CALIBRAGEM_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, DATA, TURNO,
                            ID_MAQUINA_IMPLEMENTO, ID_OPERADOR, P1_MEDIA, P1_DESVIO, P2_MEDIA, P2_DESVIO);
                    insereCalib.setUPDATED_AT(UPDATED_AT);
                    try {
                        dao.insert(insereCalib);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S9", "Sem resposta calibração,json");
                //       ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvosatividadeinsumos"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    Integer ID_INSUMO = obj.getInt("ID_INSUMO");
                    Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                    Integer RECOMENDACAO = null;
                    double QTD_HA_RECOMENDADO = 0;
                    double QTD_HA_APLICADO = 0;

                    try {
                        QTD_HA_APLICADO = obj.getDouble("QTD_HA_APLICADO");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        QTD_HA_RECOMENDADO = obj.getDouble("QTD_HA_RECOMENDADO");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        RECOMENDACAO = obj.getInt("RECOMENDACAO");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    DecimalFormat format = new DecimalFormat(".##");

                    String s;
                    s = format.format(QTD_HA_RECOMENDADO).replace(',', '.');
                    QTD_HA_RECOMENDADO = Double.valueOf(s);

                    s = format.format(QTD_HA_APLICADO).replace(',', '.');
                    QTD_HA_APLICADO = Double.valueOf(s);

                    try {
                        dao.insert(new O_S_ATIVIDADE_INSUMOS(ID_INSUMO, ID_PROGRAMACAO_ATIVIDADE, RECOMENDACAO, QTD_HA_RECOMENDADO, QTD_HA_APLICADO));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S21", "Sem resposta atividade_insumos,json");
                //      ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S22", "Sem resposta aval_subsolagem,json");
                //      ex.printStackTrace();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S23", "Sem resposta nas atividades,json");
                //      ex.printStackTrace();
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

                    if (OBSERVACAO == null || OBSERVACAO.trim().equals("null")) {
                        OBSERVACAO = null;
                    }

                    if (ACAO_INATIVO == null || ACAO_INATIVO.trim().equals("null")) {
                        ACAO_INATIVO = null;
                    }

                    DATA = ignoraHoras(DATA);

                    try {
                        O_S_ATIVIDADE_INSUMOS_DIA insereInsumosDia = new O_S_ATIVIDADE_INSUMOS_DIA(ID, ID_PROGRAMACAO_ATIVIDADE, DATA,
                                ID_INSUMO, QTD_APLICADO, ACAO_INATIVO, REGISTRO_DESCARREGADO, OBSERVACAO);
                        insereInsumosDia.setEXPORT_PROXIMA_SINC(false);
                        dao.insert(insereInsumosDia);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //     Log.wtf("S24", "Sem resposta nas atividade_insumos_dia,json");
                //      ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvindicadoressubsolagens"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

                    Integer ID_PROGRAMACAO_ATIVIDADE = obj.getInt("ID_PROGRAMACAO_ATIVIDADE");
                    String DATA = obj.getString("DATA");
                    Integer ID_ATIVIDADE = obj.getInt("ID_ATIVIDADE");
                    Integer ID_INDICADOR = obj.getInt("ID_INDICADOR");
                    double VALOR_INDICADOR = obj.getDouble("VALOR_INDICADOR");

                    VALOR_INDICADOR = corrigeDec(String.valueOf(VALOR_INDICADOR));
                    DATA = ignoraHoras(DATA);

                    try {
                        dao.insert(new INDICADORES_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, ID_ATIVIDADE, ID_INDICADOR, DATA, VALOR_INDICADOR, 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S25", "Sem resposta indicadores_subsolagem,json");
                //      ex.printStackTrace();
                contadorDeErros++;
            }


            try {
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvindavalpontosubsolagens"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

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

                    VALOR_INDICADOR = corrigeDec(String.valueOf(VALOR_INDICADOR));

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

                    String UPDATED_AT = obj.getString("UPDATED_AT");

                    DATA = ignoraHoras(DATA);

                    AVAL_PONTO_SUBSOLAGEM pontoSubsolagem = new AVAL_PONTO_SUBSOLAGEM(ID_PROGRAMACAO_ATIVIDADE, DATA, PONTO, ID_ATIVIDADE, ID_INDICADOR, VALOR_INDICADOR, COORDENADA_X,
                            COORDENADA_Y, NC_TRATADA, UPDATED_AT, 0);

                    pontoSubsolagem.setUPDATED_AT(obj.getString("UPDATED_AT"));
                    try {
                        dao.insert(pontoSubsolagem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S26", "Sem resposta ponto_subsolagem,json");
                //      ex.printStackTrace();
                contadorDeErros++;
            }

            try {
                dao.apagaTodasGeoLocal();
                response = new JSONArray(requisicaoGET(HOST_PORTA + "silvgeolocalizacoes"));
                for (Integer i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);

                    String TALHAO = obj.getString("TALHAO");

                    double LATITUDE = obj.getDouble("LATITUDE");
                    double LONGITUDE = obj.getDouble("LONGITUDE");

                    try {
                        dao.insert(new GEO_LOCALIZACAO(TALHAO, LATITUDE, LONGITUDE));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //      Log.wtf("S27", "Sem resposta geo_localizacoes,json");
                //      ex.printStackTrace();
                contadorDeErros++;
            }

            /*List<O_S_ATIVIDADES> listaOs = dao.todasOs();
            for (Integer i = 0; i < listaOs.size(); i++) {
                listaOs.get(i).setAREA_REALIZADA(dao.somaAreaRealizada(listaOs.get(i).getID_PROGRAMACAO_ATIVIDADE()));
                dao.update(listaOs.get(i));
            }
*/
            List<O_S_ATIVIDADES> listaOs = dao.todasOs();
            for (int i = 0; i < listaOs.size(); i++) {
                calculaAreaRealizada(listaOs.get(i).getID_PROGRAMACAO_ATIVIDADE(), activity);
            }

            finalizouSinc = true;
        }
    }
}