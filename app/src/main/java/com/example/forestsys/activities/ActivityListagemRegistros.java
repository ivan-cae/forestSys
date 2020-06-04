package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.forestsys.activities.ActivityRegistros.oSAtividadesDiaAtual;
import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityListagemRegistros extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private Button realizarApontamento;
    private ImageButton botaoVoltar;
    private TextView os;
    private TextView status;
    private TextView descricao;
    private TextView area;
    private TextView talhao;
    private TextView madeiraTalhao;
    private TextView ciclo;
    private TextView setor;
    private TextView manejo;
    private TextView areaRealizada;
    private TextView dataProgramada;

    private RecyclerView recyclerView;
    private AdaptadorApontamentos adaptador;
    private List<O_S_ATIVIDADES_DIA> apontamentos;
    private DataHoraAtual dataHoraAtual;

    private TextView totalHo;
    private TextView totalHh;
    private TextView totalHm;
    private TextView totalHoe;
    private TextView totalHme;

    public static boolean editou;
    private double ho = 0;
    private double hh = 0;
    private double hm = 0;
    private double hoe = 0;
    private double hme = 0;
    private boolean temApontamento = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_registros);

        editou = false;
        oSAtividadesDiaAtual = null;
        dataHoraAtual = new DataHoraAtual();
        setTitle(nomeEmpresaPref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_listagem_apontamentos);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_listagem_apontamentos);

        NavigationView navigationView = findViewById(R.id.nav_view_listagem_apontamentos);
        navigationView.setNavigationItemSelectedListener(this);

        if(osSelecionada.getSTATUS_NUM()!=2) Toast.makeText(this, "Toque o registro para edita-lo", Toast.LENGTH_LONG).show();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        botaoVoltar = findViewById(R.id.botao_listagem_apontamentos_voltar);
        os = findViewById(R.id.listagem_apontamentos_idos);
        status = findViewById(R.id.listagem_apontamentos_status);
        descricao = findViewById(R.id.listagem_apontamentos_descricao);
        area = findViewById(R.id.listagem_apontamentos_area_prog);
        talhao = findViewById(R.id.listagem_apontamentos_talhao);
        madeiraTalhao = findViewById(R.id.listagem_apontamentos_madeira);
        setor = findViewById(R.id.listagem_apontamentos_setor);
        ciclo = findViewById(R.id.listagem_apontamentos_ciclo);
        manejo = findViewById(R.id.listagem_apontamentos_manejo);
        dataProgramada = findViewById(R.id.listagem_apontamentos_data_prog);
        areaRealizada = findViewById(R.id.listagem_apontamentos_area_realizada);
        realizarApontamento = findViewById(R.id.botao_realizar_apontamento);


        totalHo = findViewById(R.id.listagem_apontamentos_total_ho);
        totalHh = findViewById(R.id.listagem_apontamentos_total_hh);
        totalHm = findViewById(R.id.listagem_apontamentos_total_hm);
        totalHoe = findViewById(R.id.listagem_apontamentos_total_hoe);
        totalHme = findViewById(R.id.listagem_apontamentos_total_hme);

        os.setText(osSelecionada.getID_PROGRAMACAO_ATIVIDADE().toString());
        status.setText(osSelecionada.getSTATUS());
        area.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        talhao.setText(osSelecionada.getTALHAO());
        setor.setText(osSelecionada.getID_SETOR().toString());
        ciclo.setText(osSelecionada.getCICLO().toString());
        dataProgramada.setText(dataHoraAtual.formataDataTextView(osSelecionada.getDATA_PROGRAMADA()));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()));

        String temMadeira = "NÃO";
        if (osSelecionada.getMADEIRA_NO_TALHAO() == 1) temMadeira = "SIM";
        madeiraTalhao.setText(temMadeira);

        descricao.setText(dao.selecionaAtividade(osSelecionada.getID_ATIVIDADE()).getDESCRICAO());
        manejo.setText(osSelecionada.getID_MANEJO());

        apontamentos = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        recyclerView = findViewById(R.id.listagem_apontamentos_recycler_apontamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorApontamentos();
        recyclerView.setAdapter(adaptador);

        ho = 0;
         hh = 0;
         hm = 0;
         hoe = 0;
         hme = 0;
         temApontamento = false;

        adaptador.setApontamentos(apontamentos);
        Thread t = new Thread(){
            @Override
            public void run(){
                for (int i = 0; i < apontamentos.size(); i++) {
                    ho += checaConversao(apontamentos.get(i).getHO());
                    hh += checaConversao(apontamentos.get(i).getHH());
                    hm += checaConversao(apontamentos.get(i).getHM());
                    hoe += checaConversao(apontamentos.get(i).getHO_ESCAVADEIRA());
                    hme += checaConversao(apontamentos.get(i).getHM_ESCAVADEIRA());
                }

                totalHh.setText(String.valueOf(hh).replace(".", ","));
                totalHo.setText(String.valueOf(ho).replace(".", ","));
                totalHm.setText(String.valueOf(hm).replace(".", ","));
                totalHoe.setText(String.valueOf(hoe).replace(".", ","));
                totalHme.setText(String.valueOf(hme).replace(".", ","));




                for (int i = 0; i < apontamentos.size(); i++) {
                    if (apontamentos.get(i).getDATA().trim().equals(dataHoraAtual.dataAtual().trim())) {
                        temApontamento = true;
                    }
                }
            }
        };

        t.run();



        if (temApontamento == false && osSelecionada.getSTATUS_NUM()!=2) {
            realizarApontamento.setEnabled(true);
            realizarApontamento.setBackgroundColor(Color.parseColor("#75A9EB"));
        }

        realizarApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemRegistros.this, ActivityRegistros.class);
                startActivity(it);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListagemRegistros.this, ActivityAtividades.class);
                startActivity(it);
            }
        });

        adaptador.setOnItemClickListener(new AdaptadorApontamentos.OnItemClickListener() {
            @Override
            public void onItemClick(O_S_ATIVIDADES_DIA oSAtividadesDia) {
                if (osSelecionada.getSTATUS_NUM()!=2) {
                    new AlertDialog.Builder(ActivityListagemRegistros.this)
                            .setTitle("Editar")
                            .setMessage("Deseja Alterar o Registro?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editou = true;
                                    oSAtividadesDiaAtual = dao.selecionaOsAtividadesDia(oSAtividadesDia.getID_PROGRAMACAO_ATIVIDADE(), oSAtividadesDia.getDATA());
                                    Intent it = new Intent(ActivityListagemRegistros.this, ActivityRegistros.class);
                                    startActivity(it);
                                }
                            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create()
                            .show();
                }
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

    public double checaConversao(String numero) {
        double teste;
        try {
            teste = Double.parseDouble(numero);
        } catch (NumberFormatException | NullPointerException n) {
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