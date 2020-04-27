package com.example.forestsys.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Adapters.AdaptadorInsumos;
import com.example.forestsys.ApplicationTodos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.PermissionUtils;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
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
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private DrawerLayout drawer;
    private Button botaoCalibracao;
    private Button botaoQualidade;
    private Button botaoRegistros;
    private Button botaoFinalizarOs;

    private TextView idOs;
    private TextView talhaoOs;
    private TextView cicloOs;
    private TextView setorOs;
    private TextView obsOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView manejoOs;
    private TextView madeiraOs;
    private TextView descricao;
    private TextView areaRealizada;
    private TextView dataProgramada;

    private GoogleMap mMap;
    private ImageButton voltar;
    private RecyclerView recyclerView;
    private AdaptadorInsumos adaptador;

    private List<Join_OS_INSUMOS> joinOsInsumos;
    private DataHoraAtual dataHoraAtual;

    BaseDeDados baseDeDados;
    DAO dao;

    private boolean mPermissionDenied = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


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

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();
        Log.e("id", String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        joinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        //if(!dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), joinOsInsumos.get(0).getDATA()).isEmpty())
        //joinOsInsumos = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), joinOsInsumos.get(0).getDATA());

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
        areaOs = findViewById(R.id.os_detalhes_area_prog);
        manejoOs = findViewById(R.id.os_detalhes_manejo);
        madeiraOs = findViewById(R.id.os_detalhes_madeira);
        descricao = findViewById(R.id.os_detalhes_descricao);
        dataProgramada = findViewById(R.id.os_detalhes_data_prog);
        areaRealizada = findViewById(R.id.os_detalhes_area_realizada);


        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));
        cicloOs.setText(String.valueOf(osSelecionada.getCICLO()));
        setorOs.setText(String.valueOf(osSelecionada.getID_SETOR()));
        obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        statusOs.setText(osSelecionada.getSTATUS());
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        manejoOs.setText(String.valueOf(osSelecionada.getID_MANEJO()));
        dataProgramada.setText(osSelecionada.getDATA_PROGRAMADA());
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()));

        String temMadeira = "NÃO";
        if (osSelecionada.getMADEIRA_NO_TALHAO() == 1) temMadeira = "SIM";
        madeiraOs.setText(temMadeira);

        descricao.setText(dao.selecionaAtividade(osSelecionada.getID_ATIVIDADE()).getDESCRICAO());

        voltar = findViewById(R.id.botao_detalhes_voltar);

        botaoFinalizarOs = findViewById(R.id.botao_finalizar_os);
        botaoCalibracao = findViewById(R.id.botao_detalhes_calibracao);
        botaoQualidade = findViewById(R.id.botao_detalhes_qualidade);
        botaoRegistros = findViewById(R.id.botao_apontamentos_geral);

        checaCalibracao();


        if (osSelecionada.getSTATUS_NUM() != 2) checaCalibragemRegistro();

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

        botaoRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityAtividades.this, ActivityListagemRegistros.class);
                startActivity(it);
            }
        });

        botaoFinalizarOs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                        .setTitle("Finalizar Atividade")
                        .setMessage("Deseja Finalizar a Atividade Atual?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<O_S_ATIVIDADES_DIA> listaOsAtiDia = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

                                boolean temProblema = false;
                                for (int j = 0; j < listaOsAtiDia.size(); j++) {
                                    if(listaOsAtiDia.get(j).getHO_ESCAVADEIRA() == null) listaOsAtiDia.get(j).setHO_ESCAVADEIRA("0");
                                    if(listaOsAtiDia.get(j).getHM_ESCAVADEIRA() == null) listaOsAtiDia.get(j).setHM_ESCAVADEIRA("0");

                                    if (listaOsAtiDia.get(j).getHM() == null || listaOsAtiDia.get(j).getHO() == null
                                            || listaOsAtiDia.get(j).getHH() == null || listaOsAtiDia.get(j).getAREA_REALIZADA() == null) {
                                        temProblema = true;
                                    }

                                    dao.update(listaOsAtiDia.get(j));
                                }
                                if (temProblema) {
                                    AlertDialog dialog = new AlertDialog.Builder(ActivityAtividades.this)
                                            .setTitle("Erro!")
                                            .setMessage("Faltam informações nos Registros, favor corrigir")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                }
                                            })
                                            .create();
                                    dialog.show();
                                } else {
                                    osSelecionada.setSTATUS_NUM(2);
                                    osSelecionada.setSTATUS("Finalizada");
                                    osSelecionada.setDATA_FINAL(dataHoraAtual.dataAtual());
                                    dao.update(osSelecionada);
                                    Toast.makeText(ActivityAtividades.this, "Atividade Finalizada Com Sucesso!", Toast.LENGTH_LONG).show();
                                    Intent it = new Intent(ActivityAtividades.this, ActivityMain.class);
                                    startActivity(it);
                                }
                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
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
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();


         //LatLng talhao1 = new LatLng(-30.662593, -52.8119419);
        //LatLng minhaLoc = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());

        //desenharCirculo(talhao1);
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(talhao1));


        mMap.setMyLocationEnabled(true);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(minhaLoc));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(minhaLoc, 16));
        //mMap.setMinZoomPreference(1.f);
        //mMap.setMaxZoomPreference(14.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
*/

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

        onMyLocationButtonClick();
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(this, "Localização Atual:\n" + location, Toast.LENGTH_LONG).show();
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    //Desenha um circulo no marcador do mapa
    //Parâmetro de entrada: variável tipo LatLng indicando a posição do marcador
    private void desenharCirculo(LatLng posicao) {
        double raio = 100.0;
        int corLinha = 0xffff0000;
        int corShade = 0x44ff0000;

        CircleOptions circleOptions = new CircleOptions().center(posicao)
                .radius(raio).fillColor(corShade).strokeColor(corLinha).strokeWidth(8);
        Circle mCircle = mMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(posicao);
        //mMap.addMarker(markerOptions.title("Talhao 1"));
    }

    //Checa se há uma calibração naquela data e turno
    private void checaCalibracao() {
        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = dao.checaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                dataHoraAtual.dataAtual(), checaTurno());

        if (calibragem_subsolagem == null) {
            botaoRegistros.setEnabled(false);
            botaoQualidade.setEnabled(false);
        }

        if (calibragem_subsolagem != null || osSelecionada.getSTATUS_NUM()==2) {
            botaoRegistros.setEnabled(true);
            botaoRegistros.setBackgroundColor(Color.parseColor("#75A9EB"));

            botaoQualidade.setEnabled(true);
            botaoQualidade.setBackgroundColor(Color.parseColor("#75A9EB"));
        }
    }

    private void checaCalibragemRegistro() {


        List<CALIBRAGEM_SUBSOLAGEM> listaCalib = dao.listaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADES_DIA> listaOsAtiDia = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<O_S_ATIVIDADE_INSUMOS_DIA> listaInsDia = dao.checaOsInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        if (listaCalib == null || listaCalib.isEmpty()) {
            botaoFinalizarOs.setEnabled(false);
        }

        if (!listaCalib.isEmpty() && !listaOsAtiDia.isEmpty() && !listaInsDia.isEmpty()) {
            botaoFinalizarOs.setVisibility(View.VISIBLE);
            botaoFinalizarOs.setEnabled(true);
            botaoFinalizarOs.setBackgroundColor(Color.parseColor("#32CD32"));
        }

    }

    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
