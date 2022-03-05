package com.example.forestsys.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorInsumos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.GEO_LOCALIZACAO;
import com.example.forestsys.Classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.R;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.Activities.ActivityCalibracao.checaTurno;
import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;

/*
* Activity responsavel por mostrar a tela da atividade e fazer suas interações
*/
public class ActivityAtividades extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DrawerLayout drawer;
    private Button botaoCalibracao;
    private Button botaoQualidade;
    private Button botaoRegistros;
    private Button botaoFinalizarOs;

    private TextView talhaoOs;
    private TextView obsOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView manejoOs;
    private TextView madeiraOs;
    private TextView areaRealizada;
    private TextView dataProgramada;

    private GoogleMap mMap;
    private ImageButton voltar;
    private RecyclerView recyclerView;
    private AdaptadorInsumos adaptador;

    public static List<Join_OS_INSUMOS> joinOsInsumos;

    private BaseDeDados baseDeDados;
    private DAO dao;

    private boolean mPermissionDenied = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private LatLng talhao;

    public static boolean editouRegistro;
    public static O_S_ATIVIDADES_DIA oSAtividadesDiaAtual;

    public static String area;
    public static String ho;
    public static String hm;
    public static String hh;
    public static String hoe;
    public static String hme;
    public static String obs;
    public static boolean erroPrestadorBool;

    public static boolean insumoRec1 = false;
    public static boolean insumoRec2 = false;

    public static boolean editouInsumo1;
    public static boolean editouInsumo2;
    public static Join_OS_INSUMOS insumoInsere;

    public static List<Join_OS_INSUMOS> listaJoinOsInsumosSelecionados;

    public static List<List<AVAL_PONTO_SUBSOLAGEM>> listaPontosCorrecaoAux;
    public static List<List<AVAL_PONTO_SUBSOLAGEM>> listaCorrecoes;

    private boolean abriuDialogoJustificativaEdicaoOs;
    private EditText valorDialogoJustificativaEdicaoOs;
    private AlertDialog dialogoEdicaoOs;

    private AlertDialog dialogoAreaRealizada;
    private boolean abriuDialogoAreaRealizada;
    private EditText valorDialogoAreaRealizada;

    private Bundle auxSavedInstanceState;

    private List<AVAL_PONTO_SUBSOLAGEM> listaPonto;
    private int qtdPontos;

    public static boolean ncInsumo1Justificada;
    public static boolean ncInsumo2Justificada;

    public static boolean editouVerion;
    public static boolean editouPonto;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        abriuDialogoJustificativaEdicaoOs = false;
        abriuDialogoAreaRealizada = false;

        setContentView(R.layout.activity_atividades);
        setTitle(nomeEmpresaPref);

        oSAtividadesDiaAtual = null;
        editouRegistro = false;
        editouInsumo1 = false;
        editouInsumo2 = false;
        insumoInsere = null;
        ncInsumo1Justificada = false;
        ncInsumo2Justificada = false;

        editouVerion = false;
        editouPonto  = false;

        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();

        listaCorrecoes = new ArrayList<>();
        listaPontosCorrecaoAux = new ArrayList<>();

        area = "";
        ho = "";
        hm = "";
        hh = "";
        hoe = "";
        hme = "";
        obs = "";

        erroPrestadorBool = false;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();
        joinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        ferramentas.calculaAreaRealizada(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), getApplicationContext());
        dao.update(osSelecionada);


        recyclerView = findViewById(R.id.os_detalhes_recycler_insumos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorInsumos();
        recyclerView.setAdapter(adaptador);

        adaptador.setInsumos(joinOsInsumos);

        talhaoOs = findViewById(R.id.os_detalhes_talhao);
        obsOs = findViewById(R.id.os_detalhes_obs);
        statusOs = findViewById(R.id.os_detalhes_status);
        areaOs = findViewById(R.id.os_detalhes_area_prog);
        manejoOs = findViewById(R.id.os_detalhes_manejo);
        madeiraOs = findViewById(R.id.os_detalhes_madeira);
        dataProgramada = findViewById(R.id.os_detalhes_data_prog);
        areaRealizada = findViewById(R.id.os_detalhes_area_realizada);


        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));

        obsOs.setText("");
        if (osSelecionada.getOBSERVACAO() != null && !osSelecionada.getOBSERVACAO().trim().equals("null")) {
            obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        }

        if (osSelecionada.getOBSERVACAO() == null || osSelecionada.getOBSERVACAO().trim().equals("null")) {
            obsOs.setText("");
            osSelecionada.setOBSERVACAO("");
        }

        statusOs.setText(osSelecionada.getSTATUS());
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ",") + "ha");
        manejoOs.setText(String.valueOf(dao.selecionaManejo(osSelecionada.getID_MANEJO()).getDESCRICAO()));
        dataProgramada.setText(ferramentas.formataDataTextView(osSelecionada.getDATA_PROGRAMADA()));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace('.', ',') + "ha");

        obsOs.setMovementMethod(new ScrollingMovementMethod());

        String temMadeira = "NÃO";
        if (osSelecionada.getMADEIRA_NO_TALHAO() == 1) temMadeira = "SIM";
        madeiraOs.setText(temMadeira);

        voltar = findViewById(R.id.botao_detalhes_voltar);

        botaoFinalizarOs = findViewById(R.id.botao_finalizar_os);
        botaoCalibracao = findViewById(R.id.botao_detalhes_calibracao);
        botaoQualidade = findViewById(R.id.botao_detalhes_qualidade);
        botaoRegistros = findViewById(R.id.botao_apontamentos_geral);


        if (savedInstanceState != null) {
            auxSavedInstanceState = savedInstanceState;
            abriuDialogoJustificativaEdicaoOs = auxSavedInstanceState.getBoolean("abriuDialogoJustificativaEdicaoOs");
            if (abriuDialogoJustificativaEdicaoOs == true) abreDialogoEdicaoAtividade();

            abriuDialogoAreaRealizada = auxSavedInstanceState.getBoolean("abriuDialogoAreaRealizada");
            if (abriuDialogoAreaRealizada == true) abreDialogoJustificativaAreaRealizada();
        }

        checaCalibracao();

        if (osSelecionada.getSTATUS_NUM() != 2) {
            checaCalibragemRegistro();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_detalhes);

        NavigationView navigationView = findViewById(R.id.nav_view_detalhes);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoQualidade.setEnabled(true);
            botaoQualidade.setBackgroundResource(R.drawable.botao_arredondado_cor_primaria_light);

            botaoCalibracao.setEnabled(true);
            botaoCalibracao.setBackgroundResource(R.drawable.botao_arredondado_cor_primaria_light);

            botaoRegistros.setEnabled(true);
            botaoRegistros.setBackgroundResource(R.drawable.botao_arredondado_cor_primaria_light);

            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundResource(R.drawable.botao_arredondado_cor_primaria_light);
        }

        botaoCalibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityAtividades.this, ActivityCalibracao.class);
                startActivity(it);
            }
        });

        botaoRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (osSelecionada.getSTATUS_NUM() == 2) {
                    if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                .setTitle("Não é possível abrir o módulo.")
                                .setMessage("Não há nenhum registro cadastrado")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }else{
                        Intent it = new Intent(ActivityAtividades.this, ActivityListaRegistros.class);
                        startActivity(it);
                    }
                } else {
                    if (checaCalibracao() == false) {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                .setTitle("Calibração Não Encontrada.")
                                .setMessage("Não Há Calibração No Dia Atual, Deseja Continuar Mesmo Assim?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent it = new Intent(ActivityAtividades.this, ActivityListaRegistros.class);
                                        if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                                            it = new Intent(ActivityAtividades.this, ActivityRegistros.class);
                                        };
                                        startActivity(it);
                                    }
                                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        Intent it = new Intent(ActivityAtividades.this, ActivityListaRegistros.class);
                        if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                            it = new Intent(ActivityAtividades.this, ActivityRegistros.class);
                        }
                        ;
                        startActivity(it);
                    }
                }
            }
        });

        botaoQualidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checaCalibracao() == false && osSelecionada.getSTATUS_NUM()!=2) {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                            .setTitle("Calibração Não Encontrada.")
                            .setMessage("Não Há Calibração No Dia Atual, Deseja Continuar Mesmo Assim?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(ActivityAtividades.this, ActivityQualidade.class);
                                    startActivity(it);
                                }
                            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                } else {
                    Intent it = new Intent(ActivityAtividades.this, ActivityQualidade.class);
                    startActivity(it);
                }
            }
        });

        if (osSelecionada.getSTATUS_NUM() != 2) {
            Toast.makeText(ActivityAtividades.this, "Toque no número do talhão para mostra-lo no mapa",
                    Toast.LENGTH_LONG).show();
        }
        if (osSelecionada.getSTATUS_NUM() == 1) {
            botaoFinalizarOs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread t1 = new Thread() {
                        @Override
                        public void run() {
                            AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                    .setTitle("Finalizar Atividade")
                                    .setMessage("Deseja Finalizar a Atividade Atual?")
                                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            List<INDICADORES_SUBSOLAGEM>listaVerion = dao.listaIndicadoresSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), osSelecionada.getID_ATIVIDADE());

                                            if(listaVerion == null || listaVerion.isEmpty()) {
                                                AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                                        .setTitle("Erro!")
                                                        .setMessage("Os dados do sistema de precisão não estão preenchidos!")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                            }
                                                        }).create();
                                                dialog.show();
                                            }else{
                                            salvar();
                                        }}
                                    }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).create();
                            dialog.show();
                        }
                    };
                    t1.run();
                }
            });
        }


        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoFinalizarOs.setText("Editar");
            if (usuarioLogado.getNIVEL_ACESSO() != 2) {
                botaoFinalizarOs.setClickable(false);
                botaoFinalizarOs.setVisibility(View.GONE);
            } else {
                botaoFinalizarOs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Thread t1 = new Thread() {
                            @Override
                            public void run() {
                                AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                        .setTitle("Editar Atividade")
                                        .setMessage("Deseja Editar a Atividade Atual?")
                                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                abreDialogoEdicaoAtividade();
                                            }
                                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        }).create();
                                dialog.show();
                            }
                        };
                        t1.run();
                    }
                });
            }
        }

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityAtividades.this, ActivityMain.class);
                startActivity(it);
            }
        });

        talhaoOs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (talhao != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(talhao, 16));
                }
            }
        });

        Intent it = getIntent();
        if (it.hasExtra("erroAbrirRegistros")) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                    .setTitle("Erro!")
                    .setMessage("Houve um erro ao abrir o módulo de registros, favor comunicar ao responsável.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }

        if (it.hasExtra("erroAbrirCalibracao")) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                    .setTitle("Erro!")
                    .setMessage("Houve um erro ao abrir o módulo de calibrações, favor comunicar ao responsável.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }

        if (it.hasExtra("erroAbrirQualidade")) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                    .setTitle("Erro!")
                    .setMessage("Houve um erro ao abrir o módulo de qualidade.\n" +
                            "Favor checar com o responsável se todos os indicadores estão cadastrados no banco de dados.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }


    }


    /*
    * Método responsável por salvar a finalização de uma atividade, atualizar o atributo "UPDATED_AT" e gerar um
    * registro de log
    */
    public void salvar() {
        osSelecionada.setSTATUS_NUM(2);
        osSelecionada.setSTATUS("Finalizado");

        osSelecionada.setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
        dao.update(osSelecionada);

        FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                usuarioLogado.getEMAIL(), "OS Atividade", "Finalizou",
                String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        dao.insert(registroLog);


        Toast.makeText(ActivityAtividades.this, "Atividade Finalizada Com Sucesso!", Toast.LENGTH_LONG).show();
        Intent it = new Intent(ActivityAtividades.this, ActivityMain.class);
        startActivity(it);
    }

    /*
    * Método responsável por abrir o diálogo para editar uma atividade
    * Após informar uma justificativa válida o status da atividade é alterado de "Finalizado" para "Andamento"
    * Em seguida é gerado um registro de log
    */
    public void abreDialogoEdicaoAtividade() {
        abriuDialogoJustificativaEdicaoOs = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityAtividades.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_atividade_edita_os, null);
        valorDialogoJustificativaEdicaoOs = mView.findViewById(R.id.justificativa_edicao_os);
        Button botaoOk = mView.findViewById(R.id.botao_ok_edicao_os);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoJustificativaEdicaoOs") != null) {
                if (auxSavedInstanceState.getString("valorDialogoJustificativaEdicaoOs").length() > 0) {
                    valorDialogoJustificativaEdicaoOs.setText(auxSavedInstanceState.getString("valorDialogoJustificativaEdicaoOs"));
                }
            }
        }

        mBuilder.setView(mView);
        dialogoEdicaoOs = mBuilder.create();
        dialogoEdicaoOs.show();

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valorDialogoJustificativaEdicaoOs.getText().toString();

                if (str.trim().length() < 3)
                    valorDialogoJustificativaEdicaoOs.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    String obs = osSelecionada.getOBSERVACAO();
                    String pegaObs = "";
                    if (obs != null || obs.length() > 0) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorDialogoJustificativaEdicaoOs.getText().toString()));
                    osSelecionada.setOBSERVACAO(obs);
                    osSelecionada.setSTATUS("Andamento");
                    osSelecionada.setSTATUS_NUM(1);

                    osSelecionada.setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
                    dao.update(osSelecionada);

                    FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                            usuarioLogado.getEMAIL(), "OS Atividade", "Editou",
                            String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
                    dao.insert(registroLog);

                    startActivity(new Intent(ActivityAtividades.this, ActivityAtividades.class));
                    dialogoEdicaoOs.dismiss();
                }
            }
        });

        dialogoEdicaoOs.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoJustificativaEdicaoOs = false;
            }
        });

        dialogoEdicaoOs.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                auxSavedInstanceState = null;
                osSelecionada = dao.selecionaOs(osSelecionada.getID_ATIVIDADE());
            }
        });
    }

    /*
     * Método responsável por abrir o diálogo para informar uma justificativa para a área realizada de uma atividade
     * ser diferente da área programada
     * Após informar uma justificativa válida a atividade será salva e uma linha informando a justificativa
     * será adicionada no atributo OBSERVACAO
     */
    public void abreDialogoJustificativaAreaRealizada() {
        abriuDialogoAreaRealizada = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityAtividades.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_atividade_justificativa_area_realizada, null);
        valorDialogoAreaRealizada = mView.findViewById(R.id.justificativa_area_realizada);
        Button botaoOk = mView.findViewById(R.id.botao_ok_justificativa_area_realizada);
        TextView texto = mView.findViewById(R.id.textview_dialogo_justificativa_area_realizada);
        if (osSelecionada.getAREA_PROGRAMADA() > osSelecionada.getAREA_REALIZADA())
            texto.setText("Justifique por que a área realizada é menor que a área programada.");
        if (osSelecionada.getAREA_PROGRAMADA() < osSelecionada.getAREA_REALIZADA())
            texto.setText("Justifique por que a área realizada é maior que a área programada.");


        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoAreaRealizada") != null) {
                if (auxSavedInstanceState.getString("valorDialogoAreaRealizada").length() > 0) {
                    valorDialogoAreaRealizada.setText(auxSavedInstanceState.getString("valorDialogoAreaRealizada"));
                }
            }
        }

        mBuilder.setView(mView);
        dialogoAreaRealizada = mBuilder.create();
        dialogoAreaRealizada.show();

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valorDialogoAreaRealizada.getText().toString();

                if (str.trim().length() < 3)
                    valorDialogoAreaRealizada.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    String obs = osSelecionada.getOBSERVACAO();
                    String pegaObs = "";
                    if (obs != null || obs.length() > 0) pegaObs = obs + "\n";
                    if (osSelecionada.getAREA_PROGRAMADA() > osSelecionada.getAREA_REALIZADA())
                        obs = pegaObs.concat("Atividade finalizada com a área realizada menor que a área programada em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorDialogoAreaRealizada.getText().toString()));
                    ;
                    if (osSelecionada.getAREA_PROGRAMADA() < osSelecionada.getAREA_REALIZADA())
                        obs = pegaObs.concat("Atividade finalizada com a área realizada maior que a área programada em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorDialogoAreaRealizada.getText().toString()));

                    osSelecionada.setOBSERVACAO(obs);
                    salvar();
                }
            }
        });

        dialogoAreaRealizada.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoAreaRealizada = false;
            }
        });

        dialogoAreaRealizada.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                auxSavedInstanceState = null;
                osSelecionada = dao.selecionaOs(osSelecionada.getID_ATIVIDADE());
            }
        });
    }

    /*
     * Sobrescrita do método de seleção de item do menu de navegação localizado na lateral da tela
    */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                Intent it1 = new Intent(this, ActivityDashboard.class);
                startActivity(it1);
                break;

            case R.id.atividades:
                Intent it2 = new Intent(this, ActivityMain.class);
                startActivity(it2);
                break;

            case R.id.calculadora:
                Intent it3 = new Intent(this, CalculadoraMain.class);
                startActivity(it3);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /*
     * Método responsável por Definir os parâmetros para inicialização do mapa mostrado na tela
     * Parâmetro de entrada: item do tipo GoogleMap para iniciazalização
    */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GEO_LOCALIZACAO geoLocalizacao = dao.selecionaGeoLocal(osSelecionada.getTALHAO());
        if (geoLocalizacao != null) {
            talhao = new LatLng(geoLocalizacao.getLATITUDE(), geoLocalizacao.getLONGITUDE());
        }

        if (talhao != null) {
            desenharCirculo(talhao);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(talhao));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (talhao != null) {
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(talhao, 16));
            mMap.setMaxZoomPreference(14.0f);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }


    /*
     * Método responsável por desenhar um circulo no talhão em que a atividade será executada
     * Parâmetro de entrada: variável tipo LatLng indicando a posição do talhão
    */
    private void desenharCirculo(LatLng posicao) {
        double raio = 150.0;
        int corLinha = 0xffff0000;
        int corShade = 0x44ff0000;

        CircleOptions circleOptions = new CircleOptions().center(posicao)
                .radius(raio).fillColor(corShade).strokeColor(corLinha).strokeWidth(8);
        Circle mCircle = mMap.addCircle(circleOptions);
        MarkerOptions markerOptions = new MarkerOptions().position(posicao);
        mMap.addMarker(markerOptions.title("Talhão " + osSelecionada.getTALHAO()));
    }

    /*
     * Método responsável por checar se há uma calibração registrada na data e turno atuais
     * Retorna: true se houver a calibração na data e turno atuais ou false caso contrário
     */
    private boolean checaCalibracao() {
        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = dao.checaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                ferramentas.formataDataDb(ferramentas.dataAtual()), checaTurno());

        if (calibragem_subsolagem == null) return false;

        return true;
    }

    /*
     * Método responsável por verificar se há calibração ou apontamento ou coleta de ponto cadastrados
    */
    @SuppressLint("ResourceAsColor")
    private void checaCalibragemRegistro() {
        List<CALIBRAGEM_SUBSOLAGEM> listaCalib = dao.listaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADES_DIA> listaOsAtiDia = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADE_INSUMOS_DIA> listaInsDia = dao.checaOsInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        listaPonto = dao.listaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        if (listaPonto.size() > 0) qtdPontos = listaPonto.get(listaPonto.size() - 1).getPONTO();
        else qtdPontos = 0;

        if (listaCalib.size() > 0 || listaOsAtiDia.size() > 0 || listaInsDia.size() > 0 || qtdPontos > 0) {
            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundResource(R.drawable.botao_arredondado_cor_primaria_light);
        }
    }

    /*
     * Salva uma instância da tela para reconstrução ao alterar entre modo paisagem e retrato ou vice-versa
    */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (abriuDialogoAreaRealizada == true)
            outState.putString("valorDialogoAreaRealizada", valorDialogoAreaRealizada.getText().toString());
        outState.putBoolean("abriuDialogoAreaRealizada", abriuDialogoAreaRealizada);
        if (abriuDialogoAreaRealizada == true) dialogoAreaRealizada.dismiss();


        if (abriuDialogoJustificativaEdicaoOs == true)
            outState.putString("valorDialogoJustificativaEdicaoOs", valorDialogoJustificativaEdicaoOs.getText().toString());
        outState.putBoolean("abriuDialogoJustificativaEdicaoOs", abriuDialogoJustificativaEdicaoOs);
        if (abriuDialogoJustificativaEdicaoOs == true) dialogoEdicaoOs.dismiss();
    }

    /*
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
    */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
