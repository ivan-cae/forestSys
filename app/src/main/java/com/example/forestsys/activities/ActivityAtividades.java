package com.example.forestsys.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorInsumos;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.assets.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.INDICADORES_SUBSOLAGEM;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityCalibracao.checaTurno;
import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;


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
    private DataHoraAtual dataHoraAtual;

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

    public static boolean editouInsumo1;
    public static boolean editouInsumo2;
    public static Join_OS_INSUMOS insumoInsere;

    public static List<Join_OS_INSUMOS> listaJoinOsInsumosSelecionados;

    private boolean abriuDialogoJustificativaEdicaoOs;
    private EditText valorDialogoJustificativaEdicaoOs;
    private AlertDialog dialogoEdicaoOs;

    private AlertDialog dialogoAreaRealizada;
    private boolean abriuDialogoAreaRealizada;
    private EditText valorDialogoAreaRealizada;

    private Bundle auxSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        abriuDialogoJustificativaEdicaoOs = false;
        abriuDialogoAreaRealizada = false;
        setContentView(R.layout.activity_atividades);
        setTitle(nomeEmpresaPref);

        dataHoraAtual = new DataHoraAtual();
        oSAtividadesDiaAtual = null;
        editouRegistro = false;
        editouInsumo1 = false;
        editouInsumo2 = false;
        insumoInsere = null;


        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();

        area = "";
        ho = "";
        hm = "";
        hh = "";
        hoe = "";
        hme = "";
        obs = "";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();
        joinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

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

        if (osSelecionada.getOBSERVACAO() != null) obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));

        statusOs.setText(osSelecionada.getSTATUS());
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ",") + "ha");
        manejoOs.setText(String.valueOf(dao.selecionaManejo(osSelecionada.getID_MANEJO()).getDESCRICAO()));
        dataProgramada.setText(dataHoraAtual.formataDataTextView(osSelecionada.getDATA_PROGRAMADA()));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()) + "ha");

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

        if (osSelecionada.isEDITOU() == false) checaCalibracao();

        if (osSelecionada.getSTATUS_NUM() != 2) {
            if (osSelecionada.isEDITOU() == false) checaCalibragemRegistro();
        }

        if (osSelecionada.getSTATUS_NUM() == 2) {
            if (osSelecionada.isEDITOU() == false) {
                botaoFinalizarOs.setVisibility(View.VISIBLE);
                botaoFinalizarOs.setEnabled(true);
                botaoFinalizarOs.setBackgroundColor(Color.parseColor("#32CD32"));
            }
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

        if (osSelecionada.isEDITOU() == true) {
            botaoQualidade.setEnabled(true);
            botaoQualidade.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoCalibracao.setEnabled(true);
            botaoCalibracao.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoRegistros.setEnabled(true);
            botaoRegistros.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundColor(Color.parseColor("#32CD32"));
        }

        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoQualidade.setEnabled(true);
            botaoQualidade.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoCalibracao.setEnabled(true);
            botaoCalibracao.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoRegistros.setEnabled(true);
            botaoRegistros.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundColor(Color.parseColor("#32CD32"));
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
                if(checaCalibracao()==false){
                    AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                            .setTitle("Calibração Não Encontrada.")
                            .setMessage("Não Há Calibração No Dia Atual, Deseja Continuar Mesmo Assim?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(ActivityAtividades.this, ActivityRegistros.class);
                                    startActivity(it);
                                }
                            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }else{
                    Intent it = new Intent(ActivityAtividades.this, ActivityRegistros.class);
                    startActivity(it);
                }
            }
        });

        botaoQualidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checaCalibracao()==false){
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
                }else{
                    Intent it = new Intent(ActivityAtividades.this, ActivityQualidade.class);
                    startActivity(it);
                }
            }
        });

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
                                            List<O_S_ATIVIDADES_DIA> listaOsAtiDia = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                                            List<O_S_ATIVIDADE_INSUMOS_DIA> listaOsInsDia = dao.checaOsInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                                            boolean temProblemaNosRegs = false;
                                            boolean erroGeral = false;
                                            for (int j = 0; j < listaOsAtiDia.size(); j++) {
                                                if (listaOsAtiDia.get(j).getHO_ESCAVADEIRA() == null)
                                                    listaOsAtiDia.get(j).setHO_ESCAVADEIRA("0");
                                                if (listaOsAtiDia.get(j).getHM_ESCAVADEIRA() == null)
                                                    listaOsAtiDia.get(j).setHM_ESCAVADEIRA("0");
                                                if (listaOsAtiDia.get(j).getHM() == null || listaOsAtiDia.get(j).getHO() == null
                                                        || listaOsAtiDia.get(j).getHH() == null || listaOsAtiDia.get(j).getAREA_REALIZADA() == null) {
                                                    temProblemaNosRegs = true;
                                                    erroGeral = true;
                                                }
                                                dao.update(listaOsAtiDia.get(j));
                                                if (listaOsAtiDia.size() != listaOsInsDia.size()) {
                                                    temProblemaNosRegs = true;
                                                    erroGeral = true;
                                                }
                                            }
                                            if (temProblemaNosRegs) {
                                                AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                                        .setTitle("Erro!")
                                                        .setMessage("Faltam informações nos Registros, favor corrigir")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                            }
                                                        })
                                                        .create();
                                                dialog.show();
                                            }
                                            if (temProblemaNosRegs == false) {
                                                if (osSelecionada.getAREA_PROGRAMADA() > osSelecionada.getAREA_REALIZADA() || osSelecionada.getAREA_PROGRAMADA() < osSelecionada.getAREA_REALIZADA()) {
                                                    erroGeral = true;
                                                    abreDialogoJustificativaAreaRealizada();
                                                }
                                            }
                                            if (erroGeral == false) {
                                                salvar();
                                            }
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


        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoFinalizarOs.setText("Editar");
            if(usuarioLogado.getNIVEL_ACESSO()!=2){
                botaoFinalizarOs.setClickable(false);
                botaoFinalizarOs.setVisibility(View.GONE);
            }else {
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(talhao, 16));
            }
        });


    }

    public void salvar() {
        osSelecionada.setSTATUS_NUM(2);
        osSelecionada.setSTATUS("Finalizado");
        osSelecionada.setDATA_FINAL(dataHoraAtual.formataDataDb(dataHoraAtual.dataAtual()));
        osSelecionada.setEDITOU(false);
        dao.update(osSelecionada);
        Toast.makeText(ActivityAtividades.this, "Atividade Finalizada Com Sucesso!", Toast.LENGTH_LONG).show();
        Intent it = new Intent(ActivityAtividades.this, ActivityMain.class);
        startActivity(it);
    }

    public void abreDialogoEdicaoAtividade() {
        abriuDialogoJustificativaEdicaoOs = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityAtividades.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_edita_os, null);
        valorDialogoJustificativaEdicaoOs = mView.findViewById(R.id.justificativa_edicao_os);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_edicao_os);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoJustificativaEdicaoOs") != null) {
                if (!auxSavedInstanceState.getString("valorDialogoJustificativaEdicaoOs").isEmpty()) {
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
                    if (!obs.isEmpty() || obs != null) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + dataHoraAtual.dataAtual() + " ás " + dataHoraAtual.horaAtual() + ". Justificativa: " + (valorDialogoJustificativaEdicaoOs.getText().toString()));
                    osSelecionada.setOBSERVACAO(obs);
                    osSelecionada.setSTATUS("Andamento");
                    osSelecionada.setSTATUS_NUM(1);
                    osSelecionada.setEDITOU(true);
                    dao.update(osSelecionada);

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
            }
        });
    }


    //Abre diálogo para justificar por que a area realizada é menor que a área realizada da atividade
    public void abreDialogoJustificativaAreaRealizada() {
        abriuDialogoAreaRealizada = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityAtividades.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_justificativa_area_realizada, null);
        valorDialogoAreaRealizada = mView.findViewById(R.id.justificativa_area_realizada);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_justificativa_area_realizada);
        TextView texto = mView.findViewById(R.id.textview_dialogo_justificativa_area_realizada);
        if (osSelecionada.getAREA_PROGRAMADA() > osSelecionada.getAREA_REALIZADA()) texto.setText("Justifique por que a área realizada é menor que a área programada.");
        if (osSelecionada.getAREA_PROGRAMADA() < osSelecionada.getAREA_REALIZADA()) texto.setText("Justifique por que a área realizada é maior que a área programada.");


        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoAreaRealizada") != null) {
                if (!auxSavedInstanceState.getString("valorDialogoAreaRealizada").isEmpty()) {
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
                    if (!obs.isEmpty() || obs != null) pegaObs = obs + "\n";
                    if (osSelecionada.getAREA_PROGRAMADA() > osSelecionada.getAREA_REALIZADA()) obs = pegaObs.concat("Atividade finalizada com a área realizada menor que a área programada em " + dataHoraAtual.dataAtual() + " ás " + dataHoraAtual.horaAtual() + ". Justificativa: " + (valorDialogoAreaRealizada.getText().toString()));;
                    if (osSelecionada.getAREA_PROGRAMADA() < osSelecionada.getAREA_REALIZADA()) obs = pegaObs.concat("Atividade finalizada com a área realizada maior que a área programada em " + dataHoraAtual.dataAtual() + " ás " + dataHoraAtual.horaAtual() + ". Justificativa: " + (valorDialogoAreaRealizada.getText().toString()));
                    osSelecionada.setOBSERVACAO(obs);
                    dao.update(osSelecionada);
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
            }
        });
    }


    //Adiciona o botão de atualização a barra de ação
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_action_bar, menu);
        return true;
    }


    //Trata a seleção do botão de atualização
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Sobreescrita do método de seleção de item do menu de navegação localizado na lateral da tela
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

    //Define parâmetros para inicialização do mapa
    //Parâmetro de entrada: item do tipo GoogleMap para iniciazalização
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        talhao = new LatLng(-16.939556, -43.434917);

        desenharCirculo(talhao);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(talhao));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(talhao, 16));
        //mMap.setMinZoomPreference(1.f);
        mMap.setMaxZoomPreference(14.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }


    //Desenha um circulo no marcador do mapa
    //Parâmetro de entrada: variável tipo LatLng indicando a posição do marcador
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

    //Checa se há uma calibração naquela data e turno
    private boolean checaCalibracao() {
        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = dao.checaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                dataHoraAtual.formataDataDb(dataHoraAtual.dataAtual()), checaTurno());

        if (calibragem_subsolagem == null) return false;

        return true;
    }

    //Verifica se há calibração, registro e qualidade cadastrados
    private void checaCalibragemRegistro() {
        List<CALIBRAGEM_SUBSOLAGEM> listaCalib = dao.listaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADES_DIA> listaOsAtiDia = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADE_INSUMOS_DIA> listaInsDia = dao.checaOsInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<AVAL_PONTO_SUBSOLAGEM> aval_subsolagems = dao.listaAvalSubsolagem(osSelecionada.getID_ATIVIDADE());
        List<INDICADORES_SUBSOLAGEM> indicadoresSubsolagems = dao.listaIndicadoresSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), osSelecionada.getID_ATIVIDADE());

        if (listaCalib == null || listaCalib.isEmpty()) {
            botaoFinalizarOs.setEnabled(false);
        }

        if (!listaCalib.isEmpty() && !listaOsAtiDia.isEmpty() && !listaInsDia.isEmpty() && !aval_subsolagems.isEmpty() && !indicadoresSubsolagems.isEmpty()) {
            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundColor(Color.parseColor("#32CD32"));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (abriuDialogoAreaRealizada == true)
            outState.putString("abriuDialogoAreaRealizada", valorDialogoAreaRealizada.getText().toString());
        outState.putBoolean("abriuDialogoAreaRealizada", abriuDialogoAreaRealizada);
        if (abriuDialogoAreaRealizada == true) dialogoAreaRealizada.dismiss();


        if (abriuDialogoJustificativaEdicaoOs == true)
            outState.putString("valorDialogoJustificativaEdicaoOs", valorDialogoJustificativaEdicaoOs.getText().toString());
        outState.putBoolean("abriuDialogoJustificativaEdicaoOs", abriuDialogoJustificativaEdicaoOs);
        if (abriuDialogoJustificativaEdicaoOs == true) dialogoEdicaoOs.dismiss();
    }



    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
