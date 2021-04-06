package com.example.forestsys.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityAtividades.area;
import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo1;
import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo2;
import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.erroPrestadorBool;
import static com.example.forestsys.Activities.ActivityAtividades.hh;
import static com.example.forestsys.Activities.ActivityAtividades.hm;
import static com.example.forestsys.Activities.ActivityAtividades.hme;
import static com.example.forestsys.Activities.ActivityAtividades.ho;
import static com.example.forestsys.Activities.ActivityAtividades.hoe;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityAtividades.obs;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.FragmentoInsumos.obsInsumo1;
import static com.example.forestsys.Activities.FragmentoInsumos.obsInsumo2;
import static com.example.forestsys.Activities.FragmentoRendimento.HHApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HMApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HMEscavadeiraApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HOApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HOEscavadeiraApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.areaRealizadaApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.erroPrestador;
import static com.example.forestsys.Activities.FragmentoRendimento.obsApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.posicaoPrestador;
import static com.example.forestsys.Adapters.AdaptadorFragmentoInsumos.insumoConforme1;
import static com.example.forestsys.Adapters.AdaptadorFragmentoInsumos.insumoConforme2;
import static java.sql.Types.NULL;

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

    private TextView tituloBarraProgresso;
    private ProgressBar barraProgresso;

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
        return teste;
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

                totalHh.setText(String.valueOf(hhAux).replace(".", ","));
                totalHo.setText(String.valueOf(hoAux).replace(".", ","));
                totalHm.setText(String.valueOf(hmAux).replace(".", ","));
                totalHoe.setText(String.valueOf(hoeAux).replace(".", ","));
                totalHme.setText(String.valueOf(hmeAux).replace(".", ","));
                totalArea.setText(String.valueOf(areaAux).replace(".", ","));
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


        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

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

        barraProgresso = findViewById(R.id.barra_progresso_lista_registros);
        tituloBarraProgresso = findViewById(R.id.titulo_barra_percentual);

        double percentual = (osSelecionada.getAREA_REALIZADA()/osSelecionada.getAREA_PROGRAMADA())*100;
        barraProgresso.setProgress((int) percentual);

        DecimalFormat df = new DecimalFormat("###.##");
        String percentualFormatado = df.format(percentual).replace(',', '.');

        tituloBarraProgresso.setText(percentualFormatado+"%");
        insumo1.setText(joinOsInsumos.get(0).getDESCRICAO());
        insumo2.setText(joinOsInsumos.get(1).getDESCRICAO());

        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));

        statusOs.setText(String.valueOf(osSelecionada.getSTATUS()));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace(".", ","));
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
