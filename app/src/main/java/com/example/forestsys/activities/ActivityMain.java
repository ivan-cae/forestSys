package com.example.forestsys.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorOs;
import com.example.forestsys.R;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static O_S_ATIVIDADES osSelecionada;

    //private ViewModelO_S_ATIVIDADES viewModelOs;
    private List<O_S_ATIVIDADES> listaOs;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private AdaptadorOs adaptador;
    private ImageButton botaoMainVoltar;
    private TextView ordenaSetor;
    private TextView ordenaTalhao;
    private TextView ordenaStatus;
    private TextView ordenaPrioridade;
    private TextView ordenaData;
    private BaseDeDados baseDeDados;
    private DAO dao;
    private boolean ordenaDataBool = false;
    private boolean ordenaSetorBool = false;
    private boolean ordenaTalhaoBool = false;
    private boolean ordenaStatusBool = false;
    private boolean ordenaPrioridadeBool = false;

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


        recyclerView = findViewById(R.id.lista_os);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        adaptador = new AdaptadorOs();
        listaOs = dao.listaOsDataAsc();
        adaptador.setOrdens(listaOs);
        recyclerView.setAdapter(adaptador);

        /*viewModelOs = ViewModelProviders.of(this).get(ViewModelO_S_ATIVIDADES.class);
        viewModelOs.getTodasOS().observe(this, new Observer<List<O_S_ATIVIDADES>>() {
            @Override
            public void onChanged(@Nullable List<O_S_ATIVIDADES> ordemServicos) {
                adaptador.setOrdens(ordemServicos);
            }
        });
        */

        ordenaTalhao = findViewById(R.id.main_talhao);
        ordenaSetor = findViewById(R.id.main_setor);
        ordenaPrioridade = findViewById(R.id.main_prioridade);
        ordenaStatus = findViewById(R.id.main_status);
        ordenaData = findViewById(R.id.main_data);

        ordenaTalhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ordenaTalhaoBool==false) listaOs = dao.listaOsTalhaoDesc();
                else listaOs = dao.listaOsTalhaoAsc();

                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaTalhaoBool = !ordenaTalhaoBool;
            }
        });

        ordenaSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ordenaSetorBool==false) listaOs = dao.listaOsSetorDesc();
                else listaOs = dao.listaOsSetorAsc();

                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaSetorBool = !ordenaSetorBool;
            }
        });

        ordenaPrioridade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ordenaPrioridadeBool==false) listaOs = dao.listaOsPrioridadeDesc();
                else listaOs = dao.listaOsPrioridadeAsc();

                adaptador.setOrdens(listaOs);
                recyclerView.setAdapter(adaptador);

                ordenaPrioridadeBool = !ordenaPrioridadeBool;
            }
        });

        ordenaStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ordenaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ordenaDataBool==false) listaOs = dao.listaOsDataDesc();
                else listaOs = dao.listaOsDataAsc();

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

        SearchView sv=(SearchView) findViewById(R.id.searchview);

        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptador.getFilter().filter(s);
                return false;
            }
        });

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    //Abre caixa de diálogo perguntando se o usuário deseja realmente voltar para a tela anterior
    public void dialogoVoltar(){
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
                .setNegativeButton("NÃO",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
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
                        .setNegativeButton("NÃO",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
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
}