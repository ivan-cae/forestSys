package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.google.android.material.navigation.NavigationView;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityIniciarColeta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private TextView idOs;
    private Button qualidade;
    private Button calibragem;
    private Button apontamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_coleta);
        setTitle(nomeEmpresaPref);

        qualidade = findViewById(R.id.botao_qualidade);
        calibragem = findViewById(R.id.botao_calibragem);
        apontamento = findViewById(R.id.botao_apontamento);

        Toolbar toolbar = findViewById(R.id.toolbar_coleta);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getEMAIL());


        idOs = findViewById(R.id.id_os_coleta);
        idOs.setText(String.valueOf(osSelecionada.getId()));


        drawer = findViewById(R.id.drawer_layout_coleta);
        NavigationView navigationView = findViewById(R.id.nav_view_coleta);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        calibragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityIniciarColeta.this, ActivityCalibragem.class);
                startActivity(it);
            }
        });
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater i = getMenuInflater();
            i.inflate(R.menu.menu_action_bar, menu);

            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:

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
            super.onBackPressed();
        }
    }
}
