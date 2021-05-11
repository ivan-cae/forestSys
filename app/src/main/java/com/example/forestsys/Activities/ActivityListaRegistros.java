package com.example.forestsys.Activities;

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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;

public class ActivityListaRegistros extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView talhaoOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView areaRealizada;
    private TextView dataProgramada;
    private ImageButton voltar;
    private FloatingActionButton botaoAdicionarRegistro;

    private BaseDeDados baseDeDados;
    private static DAO dao;
    private RecyclerView recyclerView;
    private AdaptadorApontamentos adaptador;

    private TextView totalHo;
    private TextView totalHh;
    private TextView totalHm;
    private TextView totalHoe;
    private TextView totalHme;
    private TextView totalArea;
    private TextView totalInsumo1;
    private TextView totalInsumo2;
    private TextView difInsumo1;
    private TextView difInsumo2;

    private double hoAux = 0;
    private double hhAux = 0;
    private double hmAux = 0;
    private double hoeAux = 0;
    private double hmeAux = 0;
    private double areaAux = 0;


    private TextView insumo1;
    private TextView insumo2;
    private List<O_S_ATIVIDADES_DIA> listaAtividades;
    private List<O_S_ATIVIDADE_INSUMOS> atividade_insumos = new ArrayList<O_S_ATIVIDADE_INSUMOS>();

    private Integer idAtividadeDia = null;
    private String acaoInativo = null;
    private String regDescarregado = "N";

    private boolean insumosNaoConforme = false;
    private String pattern;
    private static SimpleDateFormat sdf;

    private PieChart graficoListaReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros);
            inicializacao();
    }

    //Verifica se é possível converter o valor de uma string para double, retorna o valor convertido se possível converter.
    //Se não for possível, retorna 0.
    //Parâmetro de entrada: uma String
    public double checaConversao(String numero) {
        double teste;
        try {
            teste = Double.valueOf(numero);
        } catch (NumberFormatException | NullPointerException n) {
            teste = 0;
        }

        BigDecimal bd = BigDecimal.valueOf(teste);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    //Calcula os totalizadores na lista de registros
    public void calculaTotais() {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < listaAtividades.size(); i++) {
                    hoAux += checaConversao(listaAtividades.get(i).getHO());
                    hhAux += checaConversao(listaAtividades.get(i).getHH());
                    hmAux += checaConversao(listaAtividades.get(i).getHM());
                    hoeAux += checaConversao(listaAtividades.get(i).getHO_ESCAVADEIRA());
                    hmeAux += checaConversao(listaAtividades.get(i).getHM_ESCAVADEIRA());
                    areaAux += checaConversao(listaAtividades.get(i).getAREA_REALIZADA());
                }

                double ins1 = dao.qtdAplicadaTodosInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), atividade_insumos.get(0).getID_INSUMO());
                double ins2 = dao.qtdAplicadaTodosInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), atividade_insumos.get(1).getID_INSUMO());

                totalHh.setText(String.valueOf(checaConversao(String.valueOf(hhAux))).replace(".", ","));
                totalHo.setText(String.valueOf(checaConversao(String.valueOf(hoAux))).replace(".", ","));
                totalHm.setText(String.valueOf(checaConversao(String.valueOf(hmAux))).replace(".", ","));
                totalHoe.setText(String.valueOf(checaConversao(String.valueOf(hoeAux))).replace(".", ","));
                totalHme.setText(String.valueOf(checaConversao(String.valueOf(hmeAux))).replace(".", ","));
                totalArea.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace(".", ","));
                totalInsumo1.setText(String.valueOf(ins1).replace(".", ","));
                totalInsumo2.setText(String.valueOf(ins2).replace(".", ","));

                if (listaAtividades.size() > 0) {
                    /*if (diferencaPercentual(insumos_dia.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1) > 5 ||
                            diferencaPercentual(insumos_dia.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1) < -5)
                        difInsumo1.setBackgroundColor(Color.parseColor("#FF0000"));
                    else difInsumo1.setBackgroundColor(Color.parseColor("#BDB8B8"));

                    if (diferencaPercentual(insumos_dia.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2) > 5 ||
                            diferencaPercentual(insumos_dia.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2) < -5)
                        difInsumo2.setBackgroundColor(Color.parseColor("#FF0000"));
                    else difInsumo2.setBackgroundColor(Color.parseColor("#BDB8B8"));
                    */
                    difInsumo1.setText(String.valueOf(diferencaPercentual(atividade_insumos.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1)));
                    difInsumo2.setText(String.valueOf(diferencaPercentual(atividade_insumos.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2)));
                }
            }
        };
        t.run();
    }

    //formula= (1-(amostra atual/amostra anterior))/100
    //Calcula a diferença percentual entre dois números do tipo Double
    //Parâmetro de entrada: dois Double
    //Retorna o resultado do cálculo
    private static Double diferencaPercentual(Double anterior, Double atual) {
        Double calculo = (1 - (atual / anterior)) * 100;//((anterior - atual) / anterior) * 100.0
        DecimalFormat df = new DecimalFormat("###.##");
        //Log.e("Diferenca percentual", df.format(calculo).replace(',', '.'));
        return Double.valueOf(df.format(calculo).replace(',', '.'));
    }


    //Inicializa todas as variáveis e todos os itens da tela
    public void inicializacao() {
        setTitle(nomeEmpresaPref);

        if (osSelecionada.getSTATUS_NUM() != 2)
            if (listaAtividades != null)
                if (listaAtividades.size() > 0)
                    Toast.makeText(this, "Toque o registro para edita-lo", Toast.LENGTH_LONG).show();

        Toolbar toolbar = findViewById(R.id.toolbar_lista_registros);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_lista_registros);
        NavigationView navigationView = findViewById(R.id.nav_view_lista_registros);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        calculaAreaRealizada(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        listaAtividades = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        atividade_insumos = dao.listaInsumosatividade(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        insumo1 = findViewById(R.id.registros_insumo1);
        insumo2 = findViewById(R.id.registros_insumo2);
        dataProgramada = findViewById(R.id.data_apontamento_lista_registros);
        talhaoOs = findViewById(R.id.talhao_lista_registros);
        statusOs = findViewById(R.id.status_lista_registros);
        areaOs = findViewById(R.id.area_prog_lista_registros);
        areaRealizada = findViewById(R.id.area_realizada_lista_registros);

        totalHo = findViewById(R.id.registros_total_ho);
        totalHh = findViewById(R.id.registros_total_hh);
        totalHm = findViewById(R.id.registros_total_hm);
        totalHoe = findViewById(R.id.registros_total_hoe);
        totalHme = findViewById(R.id.registros_total_hme);
        totalArea = findViewById(R.id.registros_total_area);
        totalInsumo1 = findViewById(R.id.registros_total_insumo1);
        totalInsumo2 = findViewById(R.id.registros_total_insumo2);
        difInsumo1 = findViewById(R.id.registros_dif_insumo1);
        difInsumo2 = findViewById(R.id.registros_dif_insumo2);

        graficoListaReg = findViewById(R.id.barra_progresso_lista_registros);

        float percentual = (float) ((osSelecionada.getAREA_REALIZADA()/osSelecionada.getAREA_PROGRAMADA())*100);

        List <PieEntry>valoresRegistroApontamento = new ArrayList();
        valoresRegistroApontamento.add(new PieEntry(percentual, 0));
        valoresRegistroApontamento.add(new PieEntry(100-percentual, 1));

        valoresRegistroApontamento.get(0).setLabel("Realizado");
        valoresRegistroApontamento.get(1).setLabel("Não Realizado");
        PieDataSet dataSetListaReg = new PieDataSet(valoresRegistroApontamento, null);

        dataSetListaReg.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSetListaReg.setValueFormatter(new PercentFormatter(graficoListaReg));
        //dataSetListaReg.setDrawValues(false);
        PieData dadosListaReg = new PieData(dataSetListaReg);

        dadosListaReg.setValueTextSize(14);
        dataSetListaReg.setColors(ColorTemplate.COLORFUL_COLORS);

        graficoListaReg.animateXY(2000, 2000);
        graficoListaReg.setMaxAngle(180);
        graficoListaReg.setRotationAngle(180);
        graficoListaReg.setCenterTextSize(14);
        graficoListaReg.setCenterText("Área Realizada");
        graficoListaReg.setDrawSliceText(false);
        graficoListaReg.getDescription().setEnabled(false);
        graficoListaReg.setUsePercentValues(true);
        graficoListaReg.setClickable(false);
        graficoListaReg.setDrawCenterText(true);
        graficoListaReg.setEntryLabelTextSize(14);

        Legend legendaListaReg = graficoListaReg.getLegend();
        legendaListaReg.setFormSize(14);
        legendaListaReg.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legendaListaReg.setOrientation(Legend.LegendOrientation.VERTICAL);
        legendaListaReg.setTextSize(14);
        legendaListaReg.setDrawInside(false);

        graficoListaReg.setData(dadosListaReg);


        insumo1.setText(joinOsInsumos.get(0).getDESCRICAO());
        insumo2.setText(joinOsInsumos.get(1).getDESCRICAO());

        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));

        statusOs.setText(String.valueOf(osSelecionada.getSTATUS()));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ",") + "ha");
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace(".", ",")+"ha");
        dataProgramada.setText(Ferramentas.formataDataTextView(osSelecionada.getDATA_PROGRAMADA()));
        voltar = findViewById(R.id.botao_voltar_lista_registros);
        botaoAdicionarRegistro = findViewById(R.id.botao_adicionar_registros);

        if(osSelecionada.getSTATUS_NUM()==2) botaoAdicionarRegistro.setVisibility(GONE);

        recyclerView = findViewById(R.id.registros_lista);

        adaptador = new AdaptadorApontamentos();
        adaptador.setApontamentos(listaAtividades);
        recyclerView.setAdapter(adaptador);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        calculaTotais();


        pattern = ("dd-MM-yyyy");
        sdf = new SimpleDateFormat(pattern);


        botaoAdicionarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityListaRegistros.this, ActivityRegistros.class);
                startActivity(it);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    oSAtividadesDiaAtual = null;
                    Intent it = new Intent(ActivityListaRegistros.this, ActivityAtividades.class);
                    startActivity(it);
                }
                    });


        adaptador.setOnItemClickListener(new AdaptadorApontamentos.OnItemClickListener() {
            @Override
            public void onItemClick(O_S_ATIVIDADES_DIA oSAtividadesDia) {
                if (osSelecionada.getSTATUS_NUM() != 2) {
                    new AlertDialog.Builder(ActivityListaRegistros.this)
                            .setTitle("Editar")
                            .setMessage("Deseja Alterar o Registro?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editouRegistro = true;
                                    oSAtividadesDiaAtual = dao.selecionaOsAtividadesDia(oSAtividadesDia.getID_PROGRAMACAO_ATIVIDADE(), oSAtividadesDia.getDATA());
                                    Intent it = new Intent(ActivityListaRegistros.this, ActivityRegistros.class);
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

    private void calculaAreaRealizada(int id){
        double calcula = 0;
        List <O_S_ATIVIDADES_DIA> listaReg = dao.listaAtividadesDia(id);
        for(int i=0; i <listaReg.size(); i++){
            String s = listaReg.get(i).getAREA_REALIZADA().replace(',', '.');
            double d = 0;
            try {
                d = Double.valueOf(s);
            }catch(Exception e){
                e.printStackTrace();
                d = 0;
            }
            calcula+=d;
        }
        O_S_ATIVIDADES atividade = dao.selecionaOs(id);

        BigDecimal bd = BigDecimal.valueOf(calcula);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        atividade.setAREA_REALIZADA(bd.doubleValue());
        osSelecionada.setAREA_REALIZADA(bd.doubleValue());
        dao.update(atividade);
        Log.e("Area Realizada", String.valueOf(bd.doubleValue()));
    }



    //Adiciona o botão de atualização a barra de ação
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_action_bar, menu);
        return true;
    }


    //Sobreescrita do método de seleção de item do menu de navegação localizado na lateral da tela
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                    Intent it = new Intent(ActivityListaRegistros.this, ActivityDashboard.class);
                    startActivity(it);
                break;

            case R.id.atividades:
                    oSAtividadesDiaAtual = null;
                    Intent it2 = new Intent(ActivityListaRegistros.this, ActivityMain.class);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
