package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.forestsys.Adapters.AdaptadorCalibracao;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ActivityListagemCalibracao extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private Button adicionarCalibracao;
    private ImageButton botaoVoltar;
    private TextView os;
    private TextView status;
    private TextView descricao;
    private TextView area;
    private RecyclerView recyclerView;
    private AdaptadorCalibracao adaptador;
    private List<CALIBRAGEM_SUBSOLAGEM> calibragens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_calibracao);

        setTitle(nomeEmpresaPref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_listagem_calibracao);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_listagem_calibracao);

        NavigationView navigationView = findViewById(R.id.nav_view_listagem_calibracao);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        botaoVoltar = findViewById(R.id.botao_listagem_calibracao_voltar);
        os = findViewById(R.id.listagem_calibracao_idos);
        status = findViewById(R.id.listagem_calibracao_status);
        descricao = findViewById(R.id.listagem_calibracao_descricao);
        area = findViewById(R.id.listagem_calibracao_area);

        os.setText(osSelecionada.getID_PROGRAMACAO_ATIVIDADE().toString());
        status.setText(osSelecionada.getSTATUS());
        area.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        descricao.setText(dao.selecionaAtividade(osSelecionada.getID_ATIVIDADE()).getDESCRICAO());

        calibragens = dao.listaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        recyclerView = findViewById(R.id.recycler_calibracao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorCalibracao();
        recyclerView.setAdapter(adaptador);

        adaptador.setCalibragem(calibragens);

        adicionarCalibracao = findViewById(R.id.botao_adicionar_calibracao);
        if(osSelecionada.getSTATUS_NUM()==2){
            adicionarCalibracao.setEnabled(false);
            adicionarCalibracao.setBackgroundResource(android.R.drawable.btn_default);
        }
        adicionarCalibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemCalibracao.this, ActivityCalibracao.class);
                startActivity(it);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemCalibracao.this, ActivityAtividades.class);
                startActivity(it);
            }
        });
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
        }
    }
}
