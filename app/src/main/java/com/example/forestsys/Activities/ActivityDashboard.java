package com.example.forestsys.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Formulas;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.GEO_LOCALIZACAO;
import com.example.forestsys.R;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;

public class ActivityDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DrawerLayout drawer;
    private TextView abertas;
    private TextView andamento;
    private TextView finalizadas;
    private TextView mediaHH;
    private TextView mediaHM;
    private TextView mediaHO;
    private PieChart graficoAvaliacao;
    private PieChart graficoApontamento;
    GoogleMap mMap;

    private BaseDeDados baseDeDados;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle(nomeEmpresaPref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dash);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        abertas = findViewById(R.id.dash_abertas);
        andamento = findViewById(R.id.dash_andamento);
        finalizadas = findViewById(R.id.dash_finalizadas);
        mediaHH = findViewById(R.id.dash_media_hh);
        mediaHM = findViewById(R.id.dash_media_hm);
        mediaHO = findViewById(R.id.dash_media_ho);

        drawer = findViewById(R.id.drawer_layout_dash);
        NavigationView navigationView = findViewById(R.id.nav_view_dash);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        List<O_S_ATIVIDADES> listaTodasOs = dao.todasOs();
        List<O_S_ATIVIDADES_DIA> listaReg = dao.todasOsAtividadesDia();

        int contAbertas = 0;
        int contAndamento = 0;
        int contfinalizadas = 0;

        for (int i = 0; i < listaTodasOs.size(); i++) {
            if (listaTodasOs.get(i).getSTATUS_NUM() == 0) contAbertas++;
            if (listaTodasOs.get(i).getSTATUS_NUM() == 1) contAndamento++;
            if (listaTodasOs.get(i).getSTATUS_NUM() == 2) contfinalizadas++;
        }

        abertas.setText(String.valueOf(contAbertas));
        andamento.setText(String.valueOf(contAndamento));
        finalizadas.setText(String.valueOf(contfinalizadas));
        int contasValidadas = contAndamento + contfinalizadas;

        Double acumuladorHH = 0.0;
        Double acumuladorHO = 0.0;
        Double acumuladorHM = 0.0;


        for (int i = 0; i < listaReg.size(); i++) {
            O_S_ATIVIDADES aux = dao.selecionaOs(listaReg.get(i).getID_PROGRAMACAO_ATIVIDADE());
            if (aux.getSTATUS_NUM() != 0) {
                try {
                    if (listaReg.get(i).getHH() == null || Double.valueOf(listaReg.get(i).getHH()).isNaN()) {
                        listaReg.get(i).setHH("0");
                    }
                    if (listaReg.get(i).getHO() == null || Double.valueOf(listaReg.get(i).getHO()).isNaN()) {
                        listaReg.get(i).setHO("0");
                    }
                    if (listaReg.get(i).getHM() == null || Double.valueOf(listaReg.get(i).getHM()).isNaN()) {
                        listaReg.get(i).setHM("0");
                    }
                } catch (NumberFormatException n) {
                    listaReg.get(i).setHO("0");
                    listaReg.get(i).setHM("0");
                    listaReg.get(i).setHH("0");
                }
                acumuladorHH += Double.valueOf(listaReg.get(i).getHH());
                acumuladorHM += Double.valueOf(listaReg.get(i).getHM());
                acumuladorHO += Double.valueOf(listaReg.get(i).getHO());
            }
        }

        DecimalFormat df = new DecimalFormat(".00");

        acumuladorHH = acumuladorHH / contasValidadas;
        if (acumuladorHH.isNaN() || acumuladorHH.isInfinite() || acumuladorHH == null)
            acumuladorHH = 0.0;
        acumuladorHH = Double.valueOf(df.format(acumuladorHH).replace(',', '.'));

        acumuladorHM = acumuladorHM / contasValidadas;
        if (acumuladorHM.isNaN() || acumuladorHM.isInfinite() || acumuladorHM == null)
            acumuladorHM = 0.0;
        acumuladorHM = Double.valueOf(df.format(acumuladorHM).replace(',', '.'));

        acumuladorHO = acumuladorHO / contasValidadas;
        if (acumuladorHO.isNaN() || acumuladorHO.isInfinite() || acumuladorHO == null)
            acumuladorHO = 0.0;
        acumuladorHO = Double.valueOf(df.format(acumuladorHO).replace(',', '.'));

        mediaHH.setText(String.valueOf(acumuladorHH));
        mediaHM.setText(String.valueOf(acumuladorHM));
        mediaHO.setText(String.valueOf(acumuladorHO));

        List <PieEntry>valoresRegistroAvaliacao = new ArrayList();

        graficoAvaliacao = findViewById(R.id.dash_grafico_avaliacao);
        graficoApontamento = findViewById(R.id.dash_grafico_apontamento);

        float totalAreaRealizada=0;
        float totalAreaProgramada=0;

        Integer nc1 =0;
        Integer nc2 =0;
        Integer nc3 =0;
        Integer nc4 =0;
        Integer nc5 =0;
        Integer nc6 =0;
        Integer nc7 =0;
        Integer nc8 =0;
        Integer nc9 =0;
        Integer nc10 =0;

        int qtdTodosPontos = 0;
        List<AVAL_PONTO_SUBSOLAGEM> todosPontos = dao.todosPontos();
        if(todosPontos!=null){
            qtdTodosPontos = todosPontos.size();
        }

        for(int i = 0; i<listaTodasOs.size(); i++){
            totalAreaProgramada+=listaTodasOs.get(i).getAREA_PROGRAMADA();
            totalAreaRealizada+=listaTodasOs.get(i).getAREA_REALIZADA();

            int idReg = listaTodasOs.get(i).getID_REGIONAL();
            int idSet = listaTodasOs.get(i).getID_SETOR();
            String talhao = listaTodasOs.get(i).getTALHAO();
            int ciclo = listaTodasOs.get(i).getCICLO();
            int idManejo = listaTodasOs.get(i).getID_MANEJO();

            Integer idProg = listaTodasOs.get(i).getID_PROGRAMACAO_ATIVIDADE();
            AVAL_SUBSOLAGEM aval_subsolagem = dao.selecionaAvalSubsolagem(listaTodasOs.get(i).getID_PROGRAMACAO_ATIVIDADE());
            CADASTRO_FLORESTAL cadastro_florestal = dao.selecionaCadFlorestal(idReg, idSet, talhao, ciclo, idManejo);
            double teste = 3;
            try {
                String pegaEspacamento[] = dao.selecionaEspacamento(cadastro_florestal.getID_ESPACAMENTO()).getDESCRICAO().trim().replace(',', '.').split("X");
                teste = Double.parseDouble(pegaEspacamento[0]);
            } catch (NumberFormatException | NullPointerException n) {
                n.printStackTrace();
                teste = 3;
            }

             nc1 += dao.qtdNaoConformeMenor(idProg, 1, aval_subsolagem.getPROFUNDIDADE());
             nc2 += dao.qtdNaoConformeForaDaFaixa(idProg, 2, aval_subsolagem.getESTRONDAMENTO_LATERAL_INFERIOR(), aval_subsolagem.getESTRONDAMENTO_LATERAL_SUPERIOR());
             nc3 += dao.qtdNaoConformeMenor(idProg, 3, aval_subsolagem.getFAIXA_SOLO_PREPARADA());
             nc4 += dao.qtdNaoConformeForaDaFaixa(idProg, 4, aval_subsolagem.getPROFUNDIDADE_ADUBO_INFERIOR(), aval_subsolagem.getPROFUNDIDADE_ADUBO_SUPERIOR());
             nc5 += dao.qtdNaoConformebool(idProg, 5);
             nc6 += dao.qtdNaoConformeForaDaFaixa(idProg, 6, teste * 0.95, teste * 1.05);
             nc7 += dao.qtdNaoConformebool(idProg, 7);
             nc8 += dao.qtdNaoConformebool(idProg, 8);
             nc9 += dao.qtdNaoConformebool(idProg, 9);
             nc10 += dao.qtdNaoConformeForaDaFaixa(idProg, 10, aval_subsolagem.getLOCALIZACAO_INSUMO_INFERIOR(), aval_subsolagem.getLOCALIZACAO_INSUMO_SUPERIOR());
        }

        float diferencaAreaTotal = (100*totalAreaRealizada)/totalAreaProgramada;
        float percAreaNaoRealizada = 100-diferencaAreaTotal;

        valoresRegistroAvaliacao.add(new PieEntry(diferencaAreaTotal, 0));
        valoresRegistroAvaliacao.add(new PieEntry(percAreaNaoRealizada, 1));

        valoresRegistroAvaliacao.get(0).setLabel("Conforme");
        valoresRegistroAvaliacao.get(1).setLabel("Não Conforme");
        PieDataSet dataSetAvaliacao = new PieDataSet(valoresRegistroAvaliacao, null);

        //dataSetAvaliacao.setDrawValues(false);
        dataSetAvaliacao.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSetAvaliacao.setValueFormatter(new PercentFormatter(graficoAvaliacao));
        PieData dadosAvaliacao = new PieData(dataSetAvaliacao);

        dadosAvaliacao.setValueTextSize(14);

        dataSetAvaliacao.setColors(ColorTemplate.COLORFUL_COLORS);

        graficoAvaliacao.animateXY(2000, 2000);
        graficoAvaliacao.setMaxAngle(180);
        graficoAvaliacao.setRotationAngle(180);
        graficoAvaliacao.setCenterTextSize(14);
        graficoAvaliacao.setCenterText("Conformidade Total Das Avaliações");
        graficoAvaliacao.setDrawSliceText(false);
        graficoAvaliacao.getDescription().setEnabled(false);
        graficoAvaliacao.setUsePercentValues(true);
        graficoAvaliacao.setClickable(false);
        graficoAvaliacao.setDrawCenterText(true);
        graficoAvaliacao.setEntryLabelTextSize(14);

        Legend legendaAvaliacao = graficoAvaliacao.getLegend();
        legendaAvaliacao.setFormSize(14);
        legendaAvaliacao.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legendaAvaliacao.setOrientation(Legend.LegendOrientation.VERTICAL);
        legendaAvaliacao.setTextSize(14);
        legendaAvaliacao.setDrawInside(false);

        graficoAvaliacao.setData(dadosAvaliacao);


        float totalNc = nc1+nc2+nc3+nc4+nc5+nc6+nc7+nc8+nc9+nc10;

        float percNaoConforme = (100*totalNc)/qtdTodosPontos;

        float percConforme = 100 - percNaoConforme;



        List <PieEntry>valoresRegistroApontamento = new ArrayList();
        valoresRegistroApontamento.add(new PieEntry(percConforme, 0));
        valoresRegistroApontamento.add(new PieEntry(percNaoConforme, 1));

        valoresRegistroApontamento.get(0).setLabel("Realizado");
        valoresRegistroApontamento.get(1).setLabel("Não Realizado");
        PieDataSet dataSetApontamento = new PieDataSet(valoresRegistroApontamento, null);

        dataSetApontamento.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSetApontamento.setValueFormatter(new PercentFormatter(graficoApontamento));
        //dataSetApontamento.setDrawValues(false);
        PieData dadosApontamento = new PieData(dataSetApontamento);

        dadosApontamento.setValueTextSize(14);
        dataSetApontamento.setColors(ColorTemplate.COLORFUL_COLORS);

        graficoApontamento.animateXY(2000, 2000);
        graficoApontamento.setMaxAngle(180);
        graficoApontamento.setRotationAngle(180);
        graficoApontamento.setCenterTextSize(14);
        graficoApontamento.setCenterText("Área Total Dos Apontamentos");
        graficoApontamento.setDrawSliceText(false);
        graficoApontamento.getDescription().setEnabled(false);
        graficoApontamento.setUsePercentValues(true);
        graficoApontamento.setClickable(false);
        graficoApontamento.setDrawCenterText(true);
        graficoApontamento.setEntryLabelTextSize(14);

        Legend legendaApontamento = graficoApontamento.getLegend();
        legendaApontamento.setFormSize(14);
        legendaApontamento.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legendaApontamento.setOrientation(Legend.LegendOrientation.VERTICAL);
        legendaApontamento.setTextSize(14);
        legendaApontamento.setDrawInside(false);

        graficoApontamento.setData(dadosApontamento);
    }

    //Define parâmetros para inicialização do mapa
    //Parâmetro de entrada: item do tipo GoogleMap para iniciazalização
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<O_S_ATIVIDADES> todasOsAtividades = dao.todasOs();
        LatLng talhao = null;
        LatLngBounds.Builder b = new LatLngBounds.Builder();

        if (todasOsAtividades != null) {
            for (int i = 0; i < todasOsAtividades.size(); i++) {
                GEO_LOCALIZACAO nomeTalhao = dao.selecionaGeoLocal(todasOsAtividades.get(i).getTALHAO());
                talhao = new LatLng(nomeTalhao.getLATITUDE(), nomeTalhao.getLONGITUDE());
                b.include(talhao);

                BitmapDescriptor icone = desenhaIconeDrawable(R.drawable.status_nao_iniciado);
                if(todasOsAtividades.get(i).getSTATUS_NUM()==0){
                    icone = desenhaIconeDrawable(R.drawable.status_nao_iniciado);
                }

                if(todasOsAtividades.get(i).getSTATUS_NUM()==1){
                    icone = desenhaIconeDrawable(R.drawable.status_andamento);
                }

                if(todasOsAtividades.get(i).getSTATUS_NUM()==2){
                    icone = desenhaIconeDrawable(R.drawable.status_finalizado);
                }

                desenharCirculo(talhao, nomeTalhao.getTALHAO(), icone);
            }
        }


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

        if (todasOsAtividades != null) {
            LatLngBounds bounds = b.build();
            mMap.setMyLocationEnabled(true);
            //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
            mMap.setMaxZoomPreference(100.0f);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
        }
    }

    //Gera dinamicamente um ícone do tipo BitmapDescriptor
    //Parâmetro de entrada: A id de um Drawable
    //Parâmetro de saída: Um ícone do tipo BitmapDescriptor
    private BitmapDescriptor desenhaIconeDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            int h = 30;
            int w = 30;

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }

    //Desenha um marcador no mapa com o ícone correspondente ao status da atividade relacionada ao talhão
    //Parâmetro de entrada: variável tipo LatLng indicando a posição do marcador, uma string com o nome do
    // talhão e um BitmapDescriptor contendo ícone a ser desenhado no marcador
    private void desenharCirculo(LatLng posicao, String nome, BitmapDescriptor icone) {
        double raio = 75.0;
        CircleOptions circleOptions = new CircleOptions().center(posicao)
                .radius(raio).strokeWidth(8);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(posicao)
                .icon(icone)
                .title("Talhão " + nome);
        mMap.addMarker(markerOptions);
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


    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
