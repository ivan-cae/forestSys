package com.example.forestsys.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorOs;
import com.example.forestsys.R;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.assets.NDSpinner;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;


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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPrioridade.setSelection(posicaoSpinnerPrioridade);

        ordenaTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicaoSpinnerPrioridade == 0) {
                    if (ordenaTalhaoBool == false)
                        listaOs = dao.listaOsTalhaoSemPrioridadeDesc();
                    else listaOs = dao.listaOsTalhaoSemPrioridadeAsc();
                } else {
                    if (ordenaTalhaoBool == false)
                        listaOs = dao.listaOsTalhaoDesc(posicaoSpinnerPrioridade);
                    else listaOs = dao.listaOsTalhaoAsc(posicaoSpinnerPrioridade);
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaTalhaoBool = !ordenaTalhaoBool;
            }
        });

        ordenaSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicaoSpinnerPrioridade==0){
                    if (ordenaSetorBool == false)
                        listaOs = dao.listaOsSetorSemPrioridadeDesc();
                    else listaOs = dao.listaOsSetorSemPrioridadeAsc();
                }else {
                    if (ordenaSetorBool == false)
                        listaOs = dao.listaOsSetorDesc(posicaoSpinnerPrioridade);
                    else listaOs = dao.listaOsSetorAsc(posicaoSpinnerPrioridade);
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);
                ordenaSetorBool = !ordenaSetorBool;

            }
        });

        ordenaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicaoSpinnerPrioridade==0){
                    if (ordenaDataBool == false)
                        listaOs = dao.listaOsDataSemPrioridadeDesc();
                    else listaOs = dao.listaOsDataSemPrioridadeAsc();
                }else {
                    if (ordenaDataBool == false)
                        listaOs = dao.listaOsDataDesc(posicaoSpinnerPrioridade);
                    else listaOs = dao.listaOsDataAsc(posicaoSpinnerPrioridade);
                }
                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaDataBool = !ordenaDataBool;
            }
        });


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