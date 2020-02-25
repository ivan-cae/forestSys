package com.example.forestsys.activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.example.forestsys.AdaptadorOs;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.google.android.material.navigation.NavigationView;
import java.util.List;

import com.example.forestsys.classes.O_S_ATIVIDADES;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES;


import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static O_S_ATIVIDADES osSelecionada;

    private ViewModelO_S_ATIVIDADES viewModelOs;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private AdaptadorOs adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(nomeEmpresaPref);

        osSelecionada = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(/*usuarioLogado.getValue().getEMAIL()*/"a");

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


        adaptador = new AdaptadorOs();
        recyclerView.setAdapter(adaptador);

        viewModelOs = ViewModelProviders.of(this).get(ViewModelO_S_ATIVIDADES.class);
        viewModelOs.getTodasOS().observe(this, new Observer<List<O_S_ATIVIDADES>>() {
            @Override
            public void onChanged(@Nullable List<O_S_ATIVIDADES> ordemServicos) {
                adaptador.setOrdens(ordemServicos);
            }
        });


        adaptador.setOnItemClickListener(new AdaptadorOs.OnItemClickListener() {
            @Override
            public void onItemClick(O_S_ATIVIDADES classeOs) {
                    osSelecionada = classeOs;
                    Intent it = new Intent(ActivityMain.this, ActivityCalibragem.class);
                    //it.putExtra("abrir_os", classeOs);
                    startActivity(it);
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

            case R.id.cadastrar_conta:
                Intent it2 = new Intent(this, ActivityMain.class);
                startActivity(it2);
                break;

            case R.id.config_login:
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
        } else {
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
    }
}