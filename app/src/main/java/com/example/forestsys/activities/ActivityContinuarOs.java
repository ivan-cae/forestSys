package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.google.android.material.navigation.NavigationView;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.FragmentoApontamento.oSAtividadesDiaAtual;
//import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityContinuarOs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView idOs;
    private TextView talhaoOs;
    private TextView cicloOs;
    private TextView setorOs;
    private TextView obsOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView manejoOs;
    private TextView madeiraOs;
    private ImageButton voltar;
    private Button botaoSalvar;
    private Button botaoAvaliacao;
    private Button botaoApontamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuar_os);
        setTitle(nomeEmpresaPref);

        Toolbar toolbar = findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(/*usuarioLogado.getValue().getEMAIL()*/"a");

        idOs = findViewById(R.id.id_os_continuar);
        talhaoOs = findViewById(R.id.talhao_os_continuar);
        cicloOs = findViewById(R.id.ciclo_os_continuar);
        setorOs = findViewById(R.id.setor_os_continuar);
        obsOs = findViewById(R.id.obs_os_continuar);
        statusOs = findViewById(R.id.status_os_continuar);
        areaOs = findViewById(R.id.area_os_continuar);
        manejoOs = findViewById(R.id.manejo_os_continuar);
        madeiraOs = findViewById(R.id.madeira_os_continuar);

        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));
        cicloOs.setText(String.valueOf(osSelecionada.getCICLO()));
        setorOs.setText(String.valueOf(osSelecionada.getID_SETOR()));
        obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        statusOs.setText(String.valueOf("Andamento"));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()));
        manejoOs.setText(String.valueOf(osSelecionada.getID_MANEJO()));
        madeiraOs.setText(String.valueOf(osSelecionada.getMADEIRA_NO_TALHAO()));


        botaoApontamento = findViewById(R.id.botao_apontamento);
        botaoAvaliacao = findViewById(R.id.botao_avalicao);
        voltar = findViewById(R.id.botao_continuar_voltar);
        botaoSalvar = findViewById(R.id.botao_prosseguir_continuar);

        drawer = findViewById(R.id.drawer_layout_continuar);
        NavigationView navigationView = findViewById(R.id.nav_view_continuar);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        botaoApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                        new FragmentoApontamento()).commit();
            }
        });

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oSAtividadesDiaAtual == null) {
                    Intent it = new Intent(ActivityContinuarOs.this, ActivityMain.class);
                    startActivity(it);
                }

                if (oSAtividadesDiaAtual != null) {
                    if (oSAtividadesDiaAtual.getID_PROGRAMACAO_ATIVIDADE() != null && !oSAtividadesDiaAtual.getDATA().trim().isEmpty() &&
                            oSAtividadesDiaAtual.getID_RESPONSAVEL() != null && oSAtividadesDiaAtual.getID_PRESTADOR() != null &&
                            !oSAtividadesDiaAtual.getHO_ESCAVADEIRA().trim().isEmpty() && !oSAtividadesDiaAtual.getHO().trim().isEmpty() &&
                            !oSAtividadesDiaAtual.getHM_ESCAVADEIRA().trim().isEmpty() && !oSAtividadesDiaAtual.getHM().trim().isEmpty() &&
                            !oSAtividadesDiaAtual.getHH().trim().isEmpty() && oSAtividadesDiaAtual.getAREA_REALIZADA() != null) {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityContinuarOs.this)
                                .setTitle("Concluir")
                                .setMessage("Deseja Concluir a Ordem de Serviço?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent it = new Intent(ActivityContinuarOs.this, ActivityMain.class);
                                        startActivity(it);
                                    }
                                })
                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        Intent it = new Intent(ActivityContinuarOs.this, ActivityMain.class);
                        startActivity(it);
                    }
                }
            }
        });


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityContinuarOs.this, ActivityMain.class);
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
