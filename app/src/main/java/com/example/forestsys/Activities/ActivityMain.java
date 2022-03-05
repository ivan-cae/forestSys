package com.example.forestsys.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorOs;
import com.example.forestsys.Assets.ClienteWeb;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.R;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

import static com.example.forestsys.Activities.ActivityInicializacao.HOST_PORTA;
import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Assets.ClienteWeb.contadorDeErros;
import static com.example.forestsys.Assets.ClienteWeb.finalizouSinc;
import static com.example.forestsys.Activities.ActivityInicializacao.conectado;

/*
 * Activity responsavel por mostrar a tela principal com a listagem de atividades e fazer suas interações
 */
public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static O_S_ATIVIDADES osSelecionada;

    private List<O_S_ATIVIDADES> listaOs;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private AdaptadorOs adaptador;
    private TextView ordenaSetor;

    private TextView ordenaTalhao;
    private TextView ordenaData;
    private BaseDeDados baseDeDados;
    private DAO dao;
    private boolean ordenaDataBool = false;
    private boolean ordenaSetorBool = false;
    private boolean ordenaTalhaoBool = false;
    private SearchView sv = null;
    private NDSpinner spinnerPrioridade;
    private String[] filtragemPrioridade = new String[]{"Todas", "Baixa", "Média", "Alta", "Nenhuma"};
    private Integer posicaoSpinnerPrioridade;
    private String dadosSearchView;
    private String setaAsc = " ▲";
    private String setaDesc = " ▼";
    private ProgressDialog dialogoProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(nomeEmpresaPref);

        dialogoProgresso = new ProgressDialog(ActivityMain.this);
        dialogoProgresso.setTitle("Sincronizando com o servidor");
        dialogoProgresso.setMessage("Aguarde um momento...");
        dialogoProgresso.setCancelable(false);
        dialogoProgresso.setCanceledOnTouchOutside(false);

        osSelecionada = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_main);
        NavigationView navigationView = findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ordenaTalhao = findViewById(R.id.main_talhao);
        ordenaSetor = findViewById(R.id.main_setor);
        ordenaData = findViewById(R.id.main_data);

        recyclerView = findViewById(R.id.lista_os);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        adaptador = new AdaptadorOs();
        listaOs = dao.listaOsDataSemPrioridadeAsc();

        for(int i =0; i <listaOs.size(); i++) {
            ferramentas.calculaAreaRealizada(listaOs.get(i).getID_PROGRAMACAO_ATIVIDADE(), getApplicationContext());
        }

        adaptador.setOrdens(listaOs);
        recyclerView.setAdapter(adaptador);

        sv = (SearchView) findViewById(R.id.searchview);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        spinnerPrioridade = findViewById(R.id.main_spinner_prioridade);
        ArrayAdapter adapterPioridade = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filtragemPrioridade);
        adapterPioridade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridade.setAdapter(adapterPioridade);

        dadosSearchView = "";
        posicaoSpinnerPrioridade = 0;

        if (savedInstanceState != null) {
            posicaoSpinnerPrioridade = savedInstanceState.getInt("posicaoSpinnerPrioridade");
            dadosSearchView = savedInstanceState.getString("dadosSearchView");
        }

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adaptador.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptador.getFilter().filter(s);
                return true;
            }
        });

        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.setQuery(dadosSearchView, true);
            }
        });


        spinnerPrioridade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    listaOs = dao.filtraPrioridade(position);
                }
                if (position == 0) {
                    listaOs = dao.listaOsDataSemPrioridadeAsc();
                }

                posicaoSpinnerPrioridade = position;

                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);
                ordenaData.setText(setaDesc + "Período Prog.");
                ordenaSetor.setText(setaDesc + "Setor");
                ordenaTalhao.setText(setaDesc + "Talhão");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPrioridade.setSelection(posicaoSpinnerPrioridade);

        ordenaTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenaSetor.setText(setaDesc + "Setor");
                ordenaData.setText(setaDesc + "Período Prog.");
                if (posicaoSpinnerPrioridade == 0) {
                    if (ordenaTalhaoBool == false) {
                        listaOs = dao.listaOsTalhaoSemPrioridadeDesc();
                        ordenaTalhao.setText(setaDesc + "Talhão");
                    } else {
                        listaOs = dao.listaOsTalhaoSemPrioridadeAsc();
                        ordenaTalhao.setText(setaAsc + "Talhão");
                    }
                } else {
                    if (ordenaTalhaoBool == false) {
                        listaOs = dao.listaOsTalhaoDesc(posicaoSpinnerPrioridade);
                        ordenaTalhao.setText(setaDesc + "Talhão");
                    } else {
                        listaOs = dao.listaOsTalhaoAsc(posicaoSpinnerPrioridade);
                        ordenaTalhao.setText(setaAsc + "Talhão");
                    }
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaTalhaoBool = !ordenaTalhaoBool;
            }
        });

        ordenaSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenaTalhao.setText(setaDesc + "Talhão");
                ordenaData.setText(setaDesc + "Período Prog.");
                if (posicaoSpinnerPrioridade == 0) {
                    if (ordenaSetorBool == false) {
                        listaOs = dao.listaOsSetorSemPrioridadeDesc();
                        ordenaSetor.setText(setaDesc + "Setor");
                    } else {
                        listaOs = dao.listaOsSetorSemPrioridadeAsc();
                        ordenaSetor.setText(setaAsc + "Setor");
                    }
                } else {
                    if (ordenaSetorBool == false) {
                        listaOs = dao.listaOsSetorDesc(posicaoSpinnerPrioridade);
                        ordenaSetor.setText(setaDesc + "Setor");
                    } else {
                        listaOs = dao.listaOsSetorAsc(posicaoSpinnerPrioridade);
                        ordenaSetor.setText(setaAsc + "Setor");
                    }
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);
                ordenaSetorBool = !ordenaSetorBool;
            }
        });

        ordenaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenaSetor.setText(setaDesc + "Setor");
                ordenaTalhao.setText(setaDesc + "Talhão");
                if (posicaoSpinnerPrioridade == 0) {
                    if (ordenaDataBool == false) {
                        listaOs = dao.listaOsDataSemPrioridadeDesc();
                        ordenaData.setText(setaDesc + "Período Prog.");
                    } else {
                        listaOs = dao.listaOsDataSemPrioridadeAsc();
                        ordenaData.setText(setaAsc + "Período Prog.");
                    }
                } else {
                    if (ordenaDataBool == false) {
                        listaOs = dao.listaOsDataDesc(posicaoSpinnerPrioridade);
                        ordenaData.setText(setaDesc + "Período Prog.");
                    } else {
                        listaOs = dao.listaOsDataAsc(posicaoSpinnerPrioridade);
                        ordenaData.setText(setaAsc + "Período Prog.");
                    }
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaDataBool = !ordenaDataBool;
            }
        });

        ordenaData.performClick();

        adaptador.setOnItemClickListener(new AdaptadorOs.OnItemClickListener() {
            @Override
            public void onItemClick(O_S_ATIVIDADES classeOs) {

                osSelecionada = classeOs;
                Intent it = new Intent(ActivityMain.this, ActivityAtividades.class);
                startActivity(it);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_main_action_bar, menu);
        return true;
    }

    /*
     * Método responsável por verificar o resultado da sincronização
     * Podendo mostrar um dialogo de falha caso a sincronização não seja bem sucedida
     * Um dialogo avisando que não há conexão com a rede caso detecte tal problema
     * Um  dialogo avisando que não foi possível se conectar ao banco de dados
     * Um Toast avisando que a sincronização foi bem sucedida
     */
    @SuppressLint("StaticFieldLeak")
    private void checaFimDaSinc() {
        if (ferramentas.temRede(getApplicationContext()) == true) {

            dialogoProgresso.show();

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    while (finalizouSinc == false) {
                    }
                    return null;
                }

                @SuppressLint("StaticFieldLeak")
                @Override
                protected void onPostExecute(Void aVoid) {
                    if (conectado == true) {
                        if (dialogoProgresso.isShowing()) {
                            dialogoProgresso.dismiss();
                        }
                        String s = "Sincronizado com " + HOST_PORTA;
                        //if(contadorDeErros>0) s = "Houveram erros na sincronização, favor comunicar ao responsável.";
                        Toast.makeText(ActivityMain.this, s, Toast.LENGTH_LONG).show();
                        Intent it = new Intent(ActivityMain.this, ActivityMain.class);
                        startActivity(it);
                    } else {
                        if (dialogoProgresso.isShowing()) {
                            dialogoProgresso.dismiss();
                        }
                        @SuppressLint("StaticFieldLeak") AlertDialog dialog = new AlertDialog.Builder(ActivityMain.this)
                                .setTitle("Erro de conexão com o host.")
                                .setMessage("Possíveis causas:\n" +
                                        "Dispositivo offline\n" +
                                        "Servidor offline\n" +
                                        "Host Não encontrado\n" +
                                        "Porta não encontrada")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                    super.onPostExecute(aVoid);
                }
            }.execute();

        } else {
            AlertDialog dialog = new AlertDialog.Builder(ActivityMain.this)
                    .setTitle("Erro de rede.")
                    .setMessage("Não há conexão com a rede.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        }
    }

    /*
     * Sobrescrita do método de seleção de item na barra de ação localizada na parte superior da tela
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:
                AlertDialog dialog = new AlertDialog.Builder(ActivityMain.this)
                        .setTitle("Sincronizar")
                        .setMessage("Deseja sincronizar com o servidor ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClienteWeb clienteWeb = null;
                                try {
                                    clienteWeb = new ClienteWeb(getApplicationContext());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                                            usuarioLogado.getEMAIL(), "Atividades", "Sincronizou", null);
                                    dao.insert(registroLog);

                                    clienteWeb.sincronizaWebService();
                                } catch (Exception e) {
                                    finalizouSinc = true;
                                    conectado = false;
                                    e.printStackTrace();
                                }

                                checaFimDaSinc();
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
                return true;
            case R.id.logout:
                new AlertDialog.Builder(this)
                        .setTitle("LOGOUT")
                        .setMessage("Deslogar usuário ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityMain.this, ActivityLogin.class);
                                boolean deslogou = true;
                                it.putExtra("deslogar", deslogou);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    /*
     * SObrescrita do método onDestroy  para que feche o diálogo de título "SAIR" antes de fechar
     o aplicativo
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogoProgresso.isShowing()) {
            dialogoProgresso.dismiss();
        }
    }

    /*
     * Salva uma instância da tela para reconstrução ao alterar entre modo paisagem e retrato ou vice-versa
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("posicaoSpinnerPrioridade", posicaoSpinnerPrioridade);

        dadosSearchView = sv.getQuery().toString();
        outState.putString("dadosSearchView", dadosSearchView);

        sv.setQuery("", false);
    }
}