package com.example.forestsys.activities;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorInsumos;
import com.example.forestsys.ApplicationTodos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.example.forestsys.viewModels.ViewModelCALIBRAGEM_SUBSOLAGEM;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.forestsys.activities.ActivityCalibracao.checaTurno;
import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;


public class ActivityAtividades extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private final int PERMISSAO_LOCALIZACAO = 99;
    private DrawerLayout drawer;
    private Button botaoCalibracao;
    private Button botaoQualidade;
    private Button botaoApontamentos;

    private TextView idOs;
    private TextView talhaoOs;
    private TextView cicloOs;
    private TextView setorOs;
    private TextView obsOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView manejoOs;
    private TextView madeiraOs;

    private GoogleMap mMap;
    private ImageButton voltar;
    private RecyclerView recyclerView;
    private AdaptadorInsumos adaptador;

    private List<Join_OS_INSUMOS> joinOsInsumos;
    private DataHoraAtual dataHoraAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);
        setTitle(nomeEmpresaPref);
        ApplicationTodos applicationTodos = new ApplicationTodos();
        dataHoraAtual = new DataHoraAtual();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();
        joinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        recyclerView = findViewById(R.id.os_detalhes_recycler_insumos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorInsumos();
        recyclerView.setAdapter(adaptador);

        adaptador.setInsumos(joinOsInsumos);

        idOs = findViewById(R.id.os_detalhes_id);
        talhaoOs = findViewById(R.id.os_detalhes_talhao);
        cicloOs = findViewById(R.id.os_detalhes_ciclo);
        setorOs = findViewById(R.id.os_detalhes_setor);
        obsOs = findViewById(R.id.os_detalhes_obs);
        statusOs = findViewById(R.id.os_detalhes_status);
        areaOs = findViewById(R.id.os_detalhes_area);
        manejoOs = findViewById(R.id.os_detalhes_manejo);
        madeiraOs = findViewById(R.id.os_detalhes_madeira);

        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));
        cicloOs.setText(String.valueOf(osSelecionada.getCICLO()));
        setorOs.setText(String.valueOf(osSelecionada.getID_SETOR()));
        obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        statusOs.setText(String.valueOf("Andamento"));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()));
        manejoOs.setText(String.valueOf(osSelecionada.getID_MANEJO()));
        madeiraOs.setText(String.valueOf(osSelecionada.getMADEIRA_NO_TALHAO()));


        voltar = findViewById(R.id.botao_detalhes_voltar);


        botaoCalibracao = findViewById(R.id.botao_detalhes_calibracao);
        botaoQualidade = findViewById(R.id.botao_detalhes_qualidade);
        botaoApontamentos = findViewById(R.id.botao_apontamentos_geral);

        checaCalibracao();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_detalhes);

        NavigationView navigationView = findViewById(R.id.nav_view_detalhes);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        botaoCalibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ActivityAtividades.this, ActivityListagemCalibracao.class);
                startActivity(it);
                }
        });

        botaoApontamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityAtividades.this, ActivityListagemApontamentos.class);
                startActivity(it);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityAtividades.this, ActivityMain.class);
                startActivity(it);
            }
        });
    }


    //Adiciona o botão de atualização a barra de ação
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_action_bar, menu);
        return true;
    }


    //Trata a seleção do botão de atualização
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Sobreescrita do método de seleção de item do menu de navegação localizado na lateral da tela
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

    //Método que define os parâmetros do mapa e marcadores
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng talhao1 = new LatLng(-30.662593,-52.8119419);


        desenharCirculo(talhao1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(talhao1));
        mMap.setMyLocationEnabled(true);
        //mMap.setMinZoomPreference(1.f);
        //mMap.setMaxZoomPreference(14.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    //Desenha um circulo no marcador do mapa
    //Parâmetro de entrada: variável tipo LatLng indicando a posição do marcador
    private void desenharCirculo(LatLng posicao){
        double raio = 100.0;
        int corLinha = 0xffff0000;
        int corShade = 0x44ff0000;

        CircleOptions circleOptions = new CircleOptions().center(posicao)
                .radius(raio).fillColor(corShade).strokeColor(corLinha).strokeWidth(8);
        Circle mCircle = mMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(posicao);
        mMap.addMarker(markerOptions.title("Talhao 1"));
    }

    //Checa se há uma calibração naquela data e turno
    private void checaCalibracao(){
        ViewModelCALIBRAGEM_SUBSOLAGEM viewModelCALIBRAGEM_subsolagem = new ViewModelCALIBRAGEM_SUBSOLAGEM(getApplication());

        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = viewModelCALIBRAGEM_subsolagem.checaCalibragem(osSelecionada.getID_ATIVIDADE(),
                dataHoraAtual.dataAtual(), checaTurno());

        if(calibragem_subsolagem == null){
            botaoApontamentos.setEnabled(false);
            botaoQualidade.setEnabled(false);
        }

        if(calibragem_subsolagem != null) {
            botaoApontamentos.setEnabled(true);
            botaoApontamentos.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoQualidade.setEnabled(true);
            botaoQualidade.setBackgroundColor(Color.parseColor("#75A9EB"));
        }
    }
    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        }
}
