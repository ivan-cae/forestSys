package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityListagemApontamentos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private Button realizarApontamento;
    private ImageButton botaoVoltar;
    private TextView os;
    private TextView status;
    private TextView descricao;
    private TextView area;
    private RecyclerView recyclerView;
    private AdaptadorApontamentos adaptador;
    private List<O_S_ATIVIDADES_DIA> apontamentos;
    private DataHoraAtual dataHoraAtual;

    private TextView totalHo;
    private TextView totalHh;
    private TextView totalHm;
    private TextView totalHoe;
    private TextView totalHme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_apontamentos);

        dataHoraAtual = new DataHoraAtual();
        setTitle(nomeEmpresaPref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_listagem_apontamentos);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_listagem_apontamentos);

        NavigationView navigationView = findViewById(R.id.nav_view_listagem_apontamentos);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        botaoVoltar = findViewById(R.id.botao_listagem_apontamentos_voltar);
        os = findViewById(R.id.listagem_apontamentos_idos);
        status = findViewById(R.id.listagem_apontamentos_status);
        descricao = findViewById(R.id.listagem_apontamentos_descricao);
        area = findViewById(R.id.listagem_apontamentos_area);

        totalHo = findViewById(R.id.listagem_apontamentos_total_ho);
        totalHh = findViewById(R.id.listagem_apontamentos_total_hh);
        totalHm = findViewById(R.id.listagem_apontamentos_total_hm);
        totalHoe = findViewById(R.id.listagem_apontamentos_total_hoe);
        totalHme = findViewById(R.id.listagem_apontamentos_total_hme);

        os.setText(osSelecionada.getID_PROGRAMACAO_ATIVIDADE().toString());

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        apontamentos = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        recyclerView = findViewById(R.id.listagem_apontamentos_recycler_apontamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorApontamentos();
        recyclerView.setAdapter(adaptador);

        adaptador.setApontamentos(apontamentos);
        double ho=0;
        double hh=0;
        double hm =0;
        double hoe=0;
        double hme=0;

        for(int i = 0; i<apontamentos.size(); i++){
            ho+=checaConversao(apontamentos.get(i).getHO());
            hh+=checaConversao(apontamentos.get(i).getHH());
            hm+=checaConversao(apontamentos.get(i).getHM());
            hoe+=checaConversao(apontamentos.get(i).getHO_ESCAVADEIRA());
            hme+=checaConversao(apontamentos.get(i).getHM_ESCAVADEIRA());
        }

        totalHh.setText(String.valueOf(hh));
        totalHo.setText(String.valueOf(ho));
        totalHm.setText(String.valueOf(hm));
        totalHoe.setText(String.valueOf(hoe));
        totalHme.setText(String.valueOf(hme));


        realizarApontamento = findViewById(R.id.botao_realizar_apontamento);
        boolean temApontamento = false;

        for(int i = 0; i<apontamentos.size(); i++){
            if(apontamentos.get(i).getDATA().trim().equals(dataHoraAtual.dataAtual().trim())){
                temApontamento = true;
            }
            Log.e("Teste Data", apontamentos.get(i).getDATA() + dataHoraAtual.dataAtual()+" "+ String.valueOf(temApontamento));
        }

        if(temApontamento == false){
            realizarApontamento.setText("Realizar Apontamento");
        }else{
            realizarApontamento.setText("Alterar Apontamento");
        }

        realizarApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemApontamentos.this, ActivityApontamentos.class);
                startActivity(it);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemApontamentos.this, ActivityAtividades.class);
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

    public double checaConversao(String numero){
        double teste;
        try
        {
            teste = Double.parseDouble(numero);
        }
        catch(NumberFormatException e)
        {
            teste = 0;
        }
        return teste;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}