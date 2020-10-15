package com.example.forestsys.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.forestsys.Activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static O_S_ATIVIDADES osSelecionada;

    private List<O_S_ATIVIDADES> listaOs;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private AdaptadorOs adaptador;
    private ImageButton botaoMainVoltar;
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
    private String[] filtragemPrioridade = new String[]{"Nenhuma", "Baixa", "Média", "Alta"};
    private int posicaoSpinnerPrioridade;
    private String dadosSearchView;
    String setaAsc = " ▲";
    String setaDesc = " ▼";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(nomeEmpresaPref);

        osSelecionada = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        botaoMainVoltar = findViewById(R.id.botao_main_voltar);

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

                posicaoSpinnerPrioridade=position;

                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);
                ordenaData.setText("Período Prog."+setaDesc);
                ordenaSetor.setText("Setor"+setaDesc);
                ordenaTalhao.setText("Talhão"+setaDesc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPrioridade.setSelection(posicaoSpinnerPrioridade);

        ordenaTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenaSetor.setText("Setor"+setaDesc);
                ordenaData.setText("Período Prog."+setaDesc);
                if (posicaoSpinnerPrioridade == 0) {
                    if (ordenaTalhaoBool == false){
                        listaOs = dao.listaOsTalhaoSemPrioridadeDesc();
                        ordenaTalhao.setText("Talhão"+setaDesc);
                }                    else {
                    listaOs = dao.listaOsTalhaoSemPrioridadeAsc();
                        ordenaTalhao.setText("Talhão"+setaAsc);
                    }
                } else {
                    if (ordenaTalhaoBool == false) {
                        listaOs = dao.listaOsTalhaoDesc(posicaoSpinnerPrioridade);
                        ordenaTalhao.setText("Talhão"+setaDesc);
                    }else{
                        listaOs = dao.listaOsTalhaoAsc(posicaoSpinnerPrioridade);
                        ordenaTalhao.setText("Talhão"+setaAsc);
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
                ordenaTalhao.setText("Talhão"+setaDesc);
                ordenaData.setText("Período Prog."+setaDesc);
                if(posicaoSpinnerPrioridade==0){
                    if (ordenaSetorBool == false) {
                        listaOs = dao.listaOsSetorSemPrioridadeDesc();
                        ordenaSetor.setText("Setor"+setaDesc);
                    }else{
                        listaOs = dao.listaOsSetorSemPrioridadeAsc();
                        ordenaSetor.setText("Setor"+setaAsc);
                    }
                }else {
                    if (ordenaSetorBool == false) {
                        listaOs = dao.listaOsSetorDesc(posicaoSpinnerPrioridade);
                        ordenaSetor.setText("Setor"+setaDesc);
                    }else{
                        listaOs = dao.listaOsSetorAsc(posicaoSpinnerPrioridade);
                        ordenaSetor.setText("Setor"+setaAsc);
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
                ordenaSetor.setText("Setor"+setaDesc);
                ordenaTalhao.setText("Talhão"+setaDesc);
                if(posicaoSpinnerPrioridade==0){
                    if (ordenaDataBool == false) {
                        listaOs = dao.listaOsDataSemPrioridadeDesc();
                        ordenaData.setText("Período Prog."+setaDesc);
                    }else {
                        listaOs = dao.listaOsDataSemPrioridadeAsc();
                        ordenaData.setText("Período Prog."+setaAsc);
                    }
                }else {
                    if (ordenaDataBool == false) {
                        listaOs = dao.listaOsDataDesc(posicaoSpinnerPrioridade);
                        ordenaData.setText("Período Prog."+setaDesc);
                    }else {
                        listaOs = dao.listaOsDataAsc(posicaoSpinnerPrioridade);
                        ordenaData.setText("Período Prog."+setaAsc);
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

        botaoMainVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoVoltar();
            }
        });
    }

    //Abre caixa de diálogo perguntando se o usuário deseja realmente voltar para a tela anterior
    public void dialogoVoltar() {
        new AlertDialog.Builder(this)
                .setTitle("SAIR")
                .setMessage("Deseja fechar o aplicativo ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(ActivityMain.this, ActivityLogin.class);
                        boolean fechou = true;
                        it.putExtra("fechar", fechou);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_main_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:
                AlertDialog dialog = new AlertDialog.Builder(ActivityMain.this)
                        .setTitle("Sincronizar com o servidor irá reiniciar o aplicativo")
                        .setMessage("Deseja continuar?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityMain.this, ActivityInicializacao.class);
                                startActivity(it);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("posicaoSpinnerPrioridade", posicaoSpinnerPrioridade);

        dadosSearchView = sv.getQuery().toString();
        outState.putString("dadosSearchView", dadosSearchView);

        sv.setQuery("", false);
    }
}