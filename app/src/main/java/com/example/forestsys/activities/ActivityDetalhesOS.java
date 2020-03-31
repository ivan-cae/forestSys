package com.example.forestsys.activities;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;

import com.example.forestsys.ApplicationTodos;
import com.example.forestsys.DAO;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
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

import java.util.Date;

import static com.example.forestsys.activities.ActivityCalibragem.checaTurno;
import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;


public class ActivityDetalhesOS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private final int PERMISSAO_LOCALIZACAO = 99;
    private DrawerLayout drawer;
    private Button iniciarColeta;

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
    ImageButton voltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_os);
        setTitle(nomeEmpresaPref);
        ApplicationTodos applicationTodos = new ApplicationTodos();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        idOs = findViewById(R.id.id_os_detalhes);
        talhaoOs = findViewById(R.id.talhao_os_detalhes);
        cicloOs = findViewById(R.id.ciclo_os_detalhes);
        setorOs = findViewById(R.id.setor_os_detalhes);
        obsOs = findViewById(R.id.obs_os_detalhes);
        statusOs = findViewById(R.id.status_os_detalhes);
        areaOs = findViewById(R.id.area_os_detalhes);
        manejoOs = findViewById(R.id.manejo_os_detalhes);
        madeiraOs = findViewById(R.id.madeira_os_detalhes);

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


        iniciarColeta = findViewById(R.id.botao_iniciar_coleta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalhes);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(/*usuarioLogado.getValue().getEMAIL()*/"a");

        drawer = findViewById(R.id.drawer_layout_detalhes);

        NavigationView navigationView = findViewById(R.id.nav_view_detalhes);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        iniciarColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModelCALIBRAGEM_SUBSOLAGEM viewModelCALIBRAGEM_subsolagem = new ViewModelCALIBRAGEM_SUBSOLAGEM(getApplication());

                CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = viewModelCALIBRAGEM_subsolagem.checaCalibragem(osSelecionada.getID_ATIVIDADE(),
                                DateFormat.format("dd-MM-yyyy", new Date()).toString(), checaTurno());

                if(calibragem_subsolagem == null){
                    Intent it = new Intent(ActivityDetalhesOS.this, ActivityCalibragem.class);
                    startActivity(it);
                }

                if(calibragem_subsolagem != null) {
                    Intent it = new Intent(ActivityDetalhesOS.this, ActivityContinuarOs.class);
                    startActivity(it);
                }



            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityDetalhesOS.this, ActivityMain.class);
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

    //checa as permissões de localização
    //retorna true se a permissão for concedida e false se não for
    public boolean checarPermissaodeLocalizacao() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(this)
                        .setTitle("Fornecer permissão")
                        .setMessage("Fornecer permissão para localizar dispositivo")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(ActivityDetalhesOS.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSAO_LOCALIZACAO);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSAO_LOCALIZACAO);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        }
}
