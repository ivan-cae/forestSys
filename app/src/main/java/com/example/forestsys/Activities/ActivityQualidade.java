package com.example.forestsys.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.forestsys.Adapters.AdaptadorCorrecaoQualidade;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.GGF_USUARIOS;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.R;
import com.example.forestsys.Assets.Formulas;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;
import com.example.forestsys.Classes.INDICADORES_SUBSOLAGEM;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityAtividades.editouPonto;
import static com.example.forestsys.Activities.ActivityAtividades.erroPrestadorBool;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityAtividades.listaCorrecoes;
import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityAtividades.listaPontosCorrecaoAux;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityAtividades.editouVerion;

public class ActivityQualidade extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView talhao;
    private TextView data;
    private TextView status;
    private TextView area;
    private TextView areaRealizada;
    private TextView manejo;
    private Ferramentas ferramentas;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private FloatingActionButton botaoVerion;
    private FloatingActionButton botaoPonto;
    private FloatingActionButton botaoEditarPonto;

    private Button botaoCorrecoes;

    private EditText mediaEditP1;
    private EditText mediaEditP2;
    private EditText desvioEditP1;
    private EditText desvioEditP2;

    private TextView pontosRealizados;

    private List<AVAL_PONTO_SUBSOLAGEM> listaPonto;
    private ImageButton botaoVoltar;
    private boolean dialogoVerionAberto;
    private boolean dialogoPontoAberto;
    private boolean dialogoCorrecaoAberto;
    private boolean dialogoEdicaoAberto;

    private EditText editItem1;
    private EditText editItem2;
    private EditText editItem3;
    private EditText editItem4;
    private CheckBox editItem5;
    private EditText editItem6;
    private CheckBox editItem7;
    private CheckBox editItem8;
    private CheckBox editItem9;
    private EditText editItem10;

    TextView textItem1;
    TextView textItem2;
    TextView textItem3;
    TextView textItem4;
    TextView textItem5;
    TextView textItem6;
    TextView textItem7;
    TextView textItem8;
    TextView textItem9;
    TextView textItem10;

    TextView textLetra1;
    TextView textLetra2;
    TextView textLetra3;
    TextView textLetra4;
    TextView textLetra5;
    TextView textLetra6;
    TextView textLetra7;
    TextView textLetra8;
    TextView textLetra9;
    TextView textLetra10;

    private TextView ncProfundidade;
    private TextView ncEstrondamento;
    private TextView ncFaixa;
    private TextView ncProfundidadeFosfato;
    private TextView ncPresencaFosfato;
    private TextView ncLargura;
    private TextView ncPresencaTorroes;
    private TextView ncEspelhamento;
    private TextView ncBolsoes;
    private TextView ncLocalizacao;

    private TextView ncPercProfundidade;
    private TextView ncPercEstrondamento;
    private TextView ncPercFaixa;
    private TextView ncPercProfundidadeFosfato;
    private TextView ncPercPresencaFosfato;
    private TextView ncPercLargura;
    private TextView ncPercPresencaTorroes;
    private TextView ncPercEspelhamento;
    private TextView ncPercBolsoes;
    private TextView ncPercLocalizacao;

    private TextView listaInsumoP1;
    private TextView listaInsumoP2;
    private TextView listaMediaP1;
    private TextView listaMediaP2;
    private TextView listaDesvioP1;
    private TextView listaDesvioP2;

    private TextView indicador1;
    private TextView indicador2;
    private TextView indicador3;
    private TextView indicador4;
    private TextView indicador5;
    private TextView indicador6;
    private TextView indicador7;
    private TextView indicador8;
    private TextView indicador9;
    private TextView indicador10;

    private Bundle auxSavedInstanceState;

    private int ultimoFocus;

    private AlertDialog dialogoVerion;
    private AlertDialog dialogoPonto;
    private AlertDialog dialogoCorrecao;
    private AlertDialog dialogoEdicao;

    private Formulas formulas;

    private int idProg;

    private boolean jaTemVerion = false;

    private List<INDICADORES_SUBSOLAGEM> listaVerion;
    double longitude;
    double latitude;
    private List<ATIVIDADE_INDICADORES> atividadeIndicadores;

    private RecyclerView recyclerView;
    private AdaptadorCorrecaoQualidade adaptador;
    private int qtdPontos = 0;

    public static TextView naoHaNCNaoTratada;
    public static FloatingActionButton botaoCorrecaoRegistrar;

    private PieChart graficoQualidade;

    private double nItensNconforme;
    private Integer itemSelecionadoSpinnerEdicao;

    private Integer numeroPontoAtual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(nomeEmpresaPref);
        auxSavedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_qualidade);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        calculaAreaRealizada(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        AVAL_SUBSOLAGEM avlSub = dao.selecionaAvalSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        List<ATIVIDADE_INDICADORES> atvIndicadoresPonto = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "N");
        List<ATIVIDADE_INDICADORES> atvIndicadoresVerion = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "S");

        boolean naoPodeAbrir = false;

        if (avlSub == null) naoPodeAbrir = true;
        if (atvIndicadoresPonto == null) naoPodeAbrir = true;
        if (atvIndicadoresPonto != null) {
            if (atvIndicadoresPonto.size() == 0) naoPodeAbrir = true;
        }

        if (atvIndicadoresVerion == null) naoPodeAbrir = true;
        if (atvIndicadoresVerion != null) {
            if (atvIndicadoresVerion.size() == 0) naoPodeAbrir = true;
        }

        if (naoPodeAbrir == true) {
            Intent it = new Intent(ActivityQualidade.this, ActivityAtividades.class);
            it.putExtra("erroAbrirQualidade", true);
            startActivity(it);
        }
        try {
            inicializacao();
        } catch (Exception e) {
            e.printStackTrace();
            Intent it = new Intent(ActivityQualidade.this, ActivityAtividades.class);
            it.putExtra("erroAbrirQualidade", true);
            startActivity(it);
        }

        if (savedInstanceState != null) {
            dialogoVerionAberto = savedInstanceState.getBoolean("dialogoVerionAberto");
            dialogoPontoAberto = savedInstanceState.getBoolean("dialogoPontoAberto");
            dialogoCorrecaoAberto = savedInstanceState.getBoolean("dialogoCorrecaoAberto");
            dialogoEdicaoAberto = savedInstanceState.getBoolean("dialogoEdicaoAberto");

            numeroPontoAtual = savedInstanceState.getInt("numeroPontoAtual");

            if (dialogoEdicaoAberto == true) {
                itemSelecionadoSpinnerEdicao = savedInstanceState.getInt("itemSelecionadoSpinnerEdicao");
            }
            if (dialogoPontoAberto == true) abreDialogoPonto(null);
            if (dialogoVerionAberto == true) abreDialogoVerion();
            if (dialogoCorrecaoAberto == true) abreDialogoCorrecao();
            if (dialogoEdicaoAberto == true) abreDialogoEdicao();
        }

        if (dialogoPontoAberto == false && dialogoVerionAberto == false && dialogoCorrecaoAberto == false && dialogoEdicaoAberto == false) {
            savedInstanceState = null;
            auxSavedInstanceState = null;
        }

        /*if (listaVerion.size() > 3) {
            botaoVerion.setVisibility(View.GONE);
        }
*/
        botaoVerion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean temPermissao = true;
                if (listaVerion.size() > 0) {
                    if (usuarioLogado.getNIVEL_ACESSO() == 0 &&
                            listaVerion.get(0).getFezSinc() == 1) {
                        temPermissao = false;
                        AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                                .setTitle("Erro")
                                .setMessage("O usuário não tem permissão para editar os dados do sistema de precisão")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }

                    if (temPermissao == true) {
                        jaTemVerion = true;
                        AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                                .setTitle("Aviso!")
                                .setMessage("Já existe um cadastro de dados do sistema de precisão, deseja edita-lo?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editouVerion = true;
                                        abreDialogoVerion();
                                    }
                                })
                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .create();
                        dialog.show();
                    }
                } else {
                    abreDialogoVerion();
                    editouVerion = false;
                }
            }
        });

        botaoPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreDialogoPonto(null);
            }
        });

        if (listaPonto.size() > 0) {
            botaoEditarPonto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abreDialogoEdicao();
                }
            });
        } else {
            botaoEditarPonto.setEnabled(false);
            botaoEditarPonto.setVisibility(GONE);

            TextView textoEditarPonto = findViewById(R.id.qualidade_text_edit_ponto);
            textoEditarPonto.setVisibility(GONE);
        }

        if (listaPonto.size() > 0) {
            qtdPontos = listaPonto.get(listaPonto.size() - 1).getPONTO();
        }

        if (osSelecionada.getSTATUS_NUM() == 1) {
            if (qtdPontos > 0) {
                botaoCorrecoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        List<AVAL_PONTO_SUBSOLAGEM> listaAux = new ArrayList<>();
                        if (auxSavedInstanceState != null) {
                            listaCorrecoes = listaPontosCorrecaoAux;
                        } else {
                            for (int i = 1; i < qtdPontos + 1; i++) {
                                listaAux = dao.listaPontoCorrecoes(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), osSelecionada.getID_ATIVIDADE(), i);
                                if (!listaCorrecoes.contains(listaAux))
                                    listaCorrecoes.add(listaAux);
                            }
                        }
                        abreDialogoCorrecao();
                    }
                });
            } else botaoCorrecoes.setVisibility(View.GONE);
        } else botaoCorrecoes.setVisibility(View.GONE);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (osSelecionada.getSTATUS_NUM() == 2) {
                    Intent it = new Intent(ActivityQualidade.this, ActivityAtividades.class);
                    startActivity(it);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                            .setTitle("Voltar")
                            .setMessage("Deseja voltar Para a Tela da Atividade?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent it = new Intent(ActivityQualidade.this, ActivityAtividades.class);
                                    startActivity(it);
                                }
                            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }
            }
        });


        if (osSelecionada.getSTATUS_NUM() == 2) {
            TextView textoAddPonto = findViewById(R.id.qualidade_text_add_ponto);
            textoAddPonto.setEnabled(false);
            textoAddPonto.setVisibility(GONE);

            botaoPonto.setEnabled(false);
            botaoPonto.setVisibility(GONE);

            botaoVerion.setEnabled(false);
            botaoVerion.setVisibility(GONE);

            botaoEditarPonto.setEnabled(false);
            botaoEditarPonto.setVisibility(GONE);

            TextView textoEditarPonto = findViewById(R.id.qualidade_text_edit_ponto);
            textoEditarPonto.setVisibility(GONE);
        }
    }

    private void calculaAreaRealizada(int id) {
        double calcula = 0;
        List<O_S_ATIVIDADES_DIA> listaReg = dao.listaAtividadesDia(id);
        for (int i = 0; i < listaReg.size(); i++) {
            String s = listaReg.get(i).getAREA_REALIZADA().replace(',', '.');
            double d = 0;
            try {
                d = Double.valueOf(s);
            } catch (Exception e) {
                e.printStackTrace();
                d = 0;
            }
            calcula += d;
        }
        O_S_ATIVIDADES atividade = dao.selecionaOs(id);

        BigDecimal bd = BigDecimal.valueOf(calcula);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        atividade.setAREA_REALIZADA(bd.doubleValue());
        osSelecionada.setAREA_REALIZADA(bd.doubleValue());

        dao.update(atividade);
        // Log.wtf("Area Realizada", String.valueOf(bd.doubleValue()));

    }

    private void inicializacao() {

        formulas = new Formulas();

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();
        ferramentas = new Ferramentas();
        dialogoPontoAberto = false;
        dialogoVerionAberto = false;
        dialogoCorrecaoAberto = false;
        dialogoEdicaoAberto = false;

        idProg = osSelecionada.getID_PROGRAMACAO_ATIVIDADE();

        talhao = findViewById(R.id.qualidade_talhao);
        data = findViewById(R.id.qualidade_data);
        status = findViewById(R.id.qualidade_status);
        area = findViewById(R.id.qualidade_area);
        areaRealizada = findViewById(R.id.qualidade_area_realizada);
        manejo = findViewById(R.id.qualidade_manejo);
        botaoVerion = findViewById(R.id.qualidade_botao_verion);
        botaoPonto = findViewById(R.id.qualidade_botao_ponto);
        botaoEditarPonto = findViewById(R.id.qualidade_botao_editar_ponto);
        botaoCorrecoes = findViewById(R.id.qualidade_botao_correcao);
        pontosRealizados = findViewById(R.id.qualidade_pontos_realizados);
        botaoVoltar = findViewById(R.id.botao_qualidade_voltar);

        ncProfundidade = findViewById(R.id.qualidade_nc_profundidade_subsolagem);
        ncEstrondamento = findViewById(R.id.qualidade_nc_largura_estrondamento);
        ncFaixa = findViewById(R.id.qualidade_nc_faixa);
        ncProfundidadeFosfato = findViewById(R.id.qualidade_nc_profundidade_fosfato);
        ncPresencaFosfato = findViewById(R.id.qualidade_nc_presenca_fosfato);
        ncLargura = findViewById(R.id.qualidade_nc_largura_linhas);
        ncPresencaTorroes = findViewById(R.id.qualidade_nc_presenca_torroes);
        ncEspelhamento = findViewById(R.id.qualidade_nc_espelhamento);
        ncBolsoes = findViewById(R.id.qualidade_nc_bolsoes);
        ncLocalizacao = findViewById(R.id.qualidade_nc_local);

        ncPercProfundidade = findViewById(R.id.qualidade_percentual_nc_profundidade_subsolagem);
        ncPercEstrondamento = findViewById(R.id.qualidade_percentual_nc_largura_estrondamento);
        ncPercFaixa = findViewById(R.id.qualidade_percentual_nc_faixa);
        ncPercProfundidadeFosfato = findViewById(R.id.qualidade_percentual_nc_profundidade_fosfato);
        ncPercPresencaFosfato = findViewById(R.id.qualidade_percentual_nc_presenca_fosfato);
        ncPercLargura = findViewById(R.id.qualidade_percentual_nc_largura_linhas);
        ncPercPresencaTorroes = findViewById(R.id.qualidade_percentual_nc_presenca_torroes);
        ncPercEspelhamento = findViewById(R.id.qualidade_percentual_nc_espelhamento);
        ncPercBolsoes = findViewById(R.id.qualidade_percentual_nc_bolsoes);
        ncPercLocalizacao = findViewById(R.id.qualidade_percentual_nc_local);

        listaInsumoP1 = findViewById(R.id.qualidade_insumo_p1);
        listaInsumoP2 = findViewById(R.id.qualidade_insumo_p2);
        listaMediaP1 = findViewById(R.id.qualidade_media_p1);
        listaMediaP2 = findViewById(R.id.qualidade_media_p2);
        ;
        listaDesvioP1 = findViewById(R.id.qualidade_desvio_padrao_p1);
        listaDesvioP2 = findViewById(R.id.qualidade_desvio_padrao_p2);

        talhao.setText(osSelecionada.getTALHAO());
        data.setText(ferramentas.dataAtual());
        status.setText(osSelecionada.getSTATUS());
        area.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()) + "ha");
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()) + "ha");
        manejo.setText(String.valueOf(dao.selecionaManejo(osSelecionada.getID_MANEJO()).getDESCRICAO()));

        graficoQualidade = findViewById(R.id.barra_progresso_lista_registros);

        indicador1 = findViewById(R.id.qualidade_indicador_1);
        indicador2 = findViewById(R.id.qualidade_indicador_2);
        indicador3 = findViewById(R.id.qualidade_indicador_3);
        indicador4 = findViewById(R.id.qualidade_indicador_4);
        indicador5 = findViewById(R.id.qualidade_indicador_5);
        indicador6 = findViewById(R.id.qualidade_indicador_6);
        indicador7 = findViewById(R.id.qualidade_indicador_7);
        indicador8 = findViewById(R.id.qualidade_indicador_8);
        indicador9 = findViewById(R.id.qualidade_indicador_9);
        indicador10 = findViewById(R.id.qualidade_indicador_10);

        AVAL_SUBSOLAGEM aval_subsolagem = dao.selecionaAvalSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        listaVerion = dao.listaIndicadoresSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), osSelecionada.getID_ATIVIDADE());

        atividadeIndicadores = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "N");

        indicador1.setText(atividadeIndicadores.get(0).getDESCRICAO());
        indicador2.setText(atividadeIndicadores.get(1).getDESCRICAO());
        indicador3.setText(atividadeIndicadores.get(2).getDESCRICAO());
        indicador4.setText(atividadeIndicadores.get(3).getDESCRICAO());
        indicador5.setText(atividadeIndicadores.get(4).getDESCRICAO());
        indicador6.setText(atividadeIndicadores.get(5).getDESCRICAO());
        indicador7.setText(atividadeIndicadores.get(6).getDESCRICAO());
        indicador8.setText(atividadeIndicadores.get(7).getDESCRICAO());
        indicador9.setText(atividadeIndicadores.get(8).getDESCRICAO());
        indicador10.setText(atividadeIndicadores.get(9).getDESCRICAO());

        graficoQualidade = findViewById(R.id.barra_progresso_qualidade);


        if (listaVerion.size() > 0) {
            listaInsumoP1.setText(joinOsInsumos.get(0).getDESCRICAO());
            listaInsumoP2.setText(joinOsInsumos.get(1).getDESCRICAO());

            if (listaVerion.size() == 1) {
                listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
            }
            if (listaVerion.size() == 2) {
                listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                listaDesvioP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));

            }
            if (listaVerion.size() == 3) {
                listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                listaDesvioP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                listaMediaP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));

            }
            if (listaVerion.size() == 4) {
                listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                listaDesvioP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                listaMediaP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));
                listaDesvioP2.setText(String.valueOf(listaVerion.get(3).getVALOR_INDICADOR()).replace('.', ','));
            }
        }

        listaPonto = dao.listaAvalPontoSubsolagem(idProg);

        if (listaPonto.size() == 0) {
            pontosRealizados.setText("0");
            graficoQualidade.setVisibility(View.INVISIBLE);
        } else {

            pontosRealizados.setText(String.valueOf(listaPonto.get(listaPonto.size() - 1).getPONTO()));

            int idReg = osSelecionada.getID_REGIONAL();
            int idSet = osSelecionada.getID_SETOR();
            String talhao = osSelecionada.getTALHAO();
            int ciclo = osSelecionada.getCICLO();
            int idManejo = osSelecionada.getID_MANEJO();

            CADASTRO_FLORESTAL cadastro_florestal = dao.selecionaCadFlorestal(idReg, idSet, talhao, ciclo, idManejo);

            String pegaEspacamento[] = dao.selecionaEspacamento(cadastro_florestal.getID_ESPACAMENTO()).getDESCRICAO().trim().replace(',', '.').split("X");
            double teste = 3;
            try {
                teste = Double.valueOf(pegaEspacamento[0]);
            } catch (NumberFormatException | NullPointerException n) {
                n.printStackTrace();
                teste = 3;
            }

            int nc1 = dao.qtdNaoConformeMenor(idProg, 1, aval_subsolagem.getPROFUNDIDADE());
            int nc2 = dao.qtdNaoConformeForaDaFaixa(idProg, 2, aval_subsolagem.getESTRONDAMENTO_LATERAL_INFERIOR(), aval_subsolagem.getESTRONDAMENTO_LATERAL_SUPERIOR());
            int nc3 = dao.qtdNaoConformeMenor(idProg, 3, aval_subsolagem.getFAIXA_SOLO_PREPARADA());
            int nc4 = dao.qtdNaoConformeForaDaFaixa(idProg, 4, aval_subsolagem.getPROFUNDIDADE_ADUBO_INFERIOR(), aval_subsolagem.getPROFUNDIDADE_ADUBO_SUPERIOR());
            int nc5 = dao.qtdNaoConformebool(idProg, 5);
            int nc6 = dao.qtdNaoConformeForaDaFaixa(idProg, 6, teste * 0.95, teste * 1.05);
            int nc7 = dao.qtdNaoConformebool(idProg, 7);
            int nc8 = dao.qtdNaoConformebool(idProg, 8);
            int nc9 = dao.qtdNaoConformebool(idProg, 9);
            int nc10 = dao.qtdNaoConformeForaDaFaixa(idProg, 10, aval_subsolagem.getLOCALIZACAO_INSUMO_INFERIOR(), aval_subsolagem.getLOCALIZACAO_INSUMO_SUPERIOR());

            double percNc1 = formulas.subsolagem_profundidade(nc1, dao.qtdIndicador(idProg, 1));
            double percNc2 = formulas.subsolagem_largura_estrondamento(nc2, dao.qtdIndicador(idProg, 1));
            double percNc3 = formulas.subsolagem_faixa_solo(nc3, dao.qtdIndicador(idProg, 3));
            double percNc4 = formulas.subsolagem_profundidade_adubo(nc4, dao.qtdIndicador(idProg, 4));
            double percNc5 = formulas.subsolagem_presenca_adubo(nc5, dao.qtdIndicador(idProg, 5));
            double percNc6 = formulas.subsolagem_espacamento(nc6, dao.qtdIndicador(idProg, 6));
            double percNc7 = formulas.subsolagem_torroes(nc7, dao.qtdIndicador(idProg, 7));
            double percNc8 = formulas.subsolagem_espelhamento_solo(nc8, dao.qtdIndicador(idProg, 8));
            double percNc9 = formulas.subsolagem_bolsoes_ar(nc9, dao.qtdIndicador(idProg, 9));
            double percNc10 = formulas.subsolagem_distancia_insumo(nc10, dao.qtdIndicador(idProg, 10));

            ncProfundidade.setText(String.valueOf(nc1).replace('.', ','));
            ncPercProfundidade.setText(String.valueOf(percNc1).replace('.', ','));
            if (percNc1 > 5.0)
                ncPercProfundidade.setTextColor(Color.parseColor("#FF0000"));

            ncEstrondamento.setText(String.valueOf(nc2).replace('.', ','));
            ncPercEstrondamento.setText(String.valueOf(percNc2).replace('.', ','));
            if (percNc2 > 5.0)
                ncPercEstrondamento.setTextColor(Color.parseColor("#FF0000"));

            ncFaixa.setText(String.valueOf(nc3).replace('.', ','));
            ncPercFaixa.setText(String.valueOf(percNc3).replace('.', ','));
            if (percNc3 > 5.0)
                ncPercFaixa.setTextColor(Color.parseColor("#FF0000"));

            ncProfundidadeFosfato.setText(String.valueOf(nc4).replace('.', ','));
            ncPercProfundidadeFosfato.setText(String.valueOf(percNc4).replace('.', ','));
            if (percNc4 > 5.0)
                ncPercProfundidadeFosfato.setTextColor(Color.parseColor("#FF0000"));

            ncPresencaFosfato.setText(String.valueOf(nc5).replace('.', ','));
            ncPercPresencaFosfato.setText(String.valueOf(percNc5).replace('.', ','));
            if (percNc5 > 5.0)
                ncPercPresencaFosfato.setTextColor(Color.parseColor("#FF0000"));

            ncLargura.setText(String.valueOf(nc6).replace('.', ','));
            ncPercLargura.setText(String.valueOf(percNc6).replace('.', ','));
            if (percNc6 > 5.0)
                ncPercLargura.setTextColor(Color.parseColor("#FF0000"));

            ncPresencaTorroes.setText(String.valueOf(nc7).replace('.', ','));
            ncPercPresencaTorroes.setText(String.valueOf(percNc7).replace('.', ','));
            if (percNc7 > 5.0)
                ncPercPresencaTorroes.setTextColor(Color.parseColor("#FF0000"));

            ncEspelhamento.setText(String.valueOf(nc8).replace('.', ','));
            ncPercEspelhamento.setText(String.valueOf(percNc8).replace('.', ','));
            if (percNc8 > 5.0)
                ncPercEspelhamento.setTextColor(Color.parseColor("#FF0000"));

            ncBolsoes.setText(String.valueOf(nc9).replace('.', ','));
            ncPercBolsoes.setText(String.valueOf(percNc9).replace('.', ','));
            if (percNc9 > 5.0)
                ncPercBolsoes.setTextColor(Color.parseColor("#FF0000"));

            ncLocalizacao.setText(String.valueOf(nc10).replace('.', ','));
            ncPercLocalizacao.setText(String.valueOf(percNc10).replace('.', ','));
            if (percNc10 > 5.0) {
                ncPercLocalizacao.setTextColor(Color.parseColor("#FF0000"));
            }

            nItensNconforme = (nc1 + nc2 + nc3 + nc4 + nc5 + nc6 + nc7 + nc8 + nc9 + nc10) * 100;
            double auxQtdPontos = listaPonto.get(listaPonto.size() - 1).getPONTO();
            double calculoPontosReg = auxQtdPontos * 10;

            //Log.wtf("Qtd itens nc", String.valueOf(nItensNconforme));
            //Log.wtf("Qtd pontos", String.valueOf(calculoPontosReg));

            float percentualQualidade;
            try {
                percentualQualidade = (float) (nItensNconforme / calculoPontosReg);
                //Log.wtf("Percentual Qualidade", String.valueOf(percentualQualidade));
            } catch (Exception e) {
                e.printStackTrace();
                percentualQualidade = 0;
                //Log.wtf("Percentual Qualidade", "deu erro: 0");
            }

            percentualQualidade = (100 - percentualQualidade);

            List<PieEntry> valoresRegistroApontamento = new ArrayList();
            valoresRegistroApontamento.add(new PieEntry(percentualQualidade, 0));
            valoresRegistroApontamento.add(new PieEntry(100 - percentualQualidade, 1));

            valoresRegistroApontamento.get(0).setLabel("Conforme");
            valoresRegistroApontamento.get(1).setLabel("Não Conforme");
            PieDataSet dataSetQualidade = new PieDataSet(valoresRegistroApontamento, null);

            dataSetQualidade.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
            dataSetQualidade.setValueFormatter(new PercentFormatter(graficoQualidade));
            //dataSetQualidade.setDrawValues(false);
            PieData dadosQualidade = new PieData(dataSetQualidade);

            dadosQualidade.setValueTextSize(14);
            dataSetQualidade.setColors(Color.rgb(255, 102, 0), Color.rgb(193, 37, 82));

            graficoQualidade.animateXY(2000, 2000);
            graficoQualidade.setMaxAngle(180);
            graficoQualidade.setRotationAngle(180);
            graficoQualidade.setCenterTextSize(14);
            graficoQualidade.setCenterText("Conformidade");
            graficoQualidade.setDrawSliceText(false);
            graficoQualidade.getDescription().setEnabled(false);
            graficoQualidade.setUsePercentValues(true);
            graficoQualidade.setClickable(false);
            graficoQualidade.setDrawCenterText(true);
            graficoQualidade.setEntryLabelTextSize(14);

            Legend legendaQualidade = graficoQualidade.getLegend();
            legendaQualidade.setFormSize(14);
            legendaQualidade.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legendaQualidade.setOrientation(Legend.LegendOrientation.VERTICAL);
            legendaQualidade.setTextSize(14);
            legendaQualidade.setDrawInside(false);

            graficoQualidade.setData(dadosQualidade);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_qualidade);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        drawer = findViewById(R.id.drawer_layout_qualidade);

        NavigationView navigationView = findViewById(R.id.nav_view_qualidade);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        itemSelecionadoSpinnerEdicao = 0;
    }

    //Poe a virgula automaticamente como separador decimal dos números inseridos nas caixas de teste
    //parâmetros de entrada: Uma instância de uma caixa de texto, um inteiro representando o índice da lista de indicadores onde está armazenado
    //limite superiorn inferior e casas decimais permitidos para o indicador, a string contendo os valores inseridos na caixa de texto
    /*public void mascaraVirgula(EditText edit, int i, CharSequence s) {
        int tamanho;
        String input;
        String[] antesDaVirgula;

        tamanho = edit.getText().toString().length();
        input = s.toString();

        if (input.length() > 0) {

            antesDaVirgula = String.valueOf(atividadeIndicadores.get(i).getLIMITE_SUPERIOR())
                    .replace('.', ',').split(",");

            edit.setFilters(new InputFilter[]{
                    new InputFilter.LengthFilter(antesDaVirgula[0].length() + atividadeIndicadores.get(i).getCASAS_DECIMAIS() + 1)});

            char[] aux = input.toCharArray();
            if ((tamanho + 1) != antesDaVirgula[0].length() && aux[tamanho - 1] == ',') {
                edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                if (tamanho == antesDaVirgula[0].length() + 1) {
                    char[] charAux = input.toCharArray();
                    String stringAux = String.valueOf(charAux[tamanho - 1]);
                    input = input.substring(0, tamanho - 1);
                    edit.setText(input + "," + stringAux);
                    edit.setSelection(edit.getText().toString().length());
                }
            }
        }
    }
*/

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

    //Abre caixa de diálogo para preenchimento dos dados verion
    public void abreDialogoVerion() {
        try {
            dialogoVerionAberto = true;
            String acao = "Inseriu";

            if (editouVerion == true) {
                acao = "Editou";
            } else {
                acao = "Inseriu";
            }

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_verion, null);

            List<ATIVIDADE_INDICADORES> atividadeIndicadores = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "S");

            TextView mediaP1Nome;
            TextView mediaP2Nome;
            TextView desvioP1Nome;
            TextView desvioP2Nome;

            TextView letraItem1;
            TextView letraItem2;
            TextView letraItem3;
            TextView letraItem4;

            mediaP1Nome = mView.findViewById(R.id.dialogo_qualidade_verion_nome_media_p1);
            mediaP2Nome = mView.findViewById(R.id.dialogo_qualidade_verion_nome_media_p2);
            desvioP1Nome = mView.findViewById(R.id.dialogo_qualidade_verion_nome_desvio_padrao_p1);
            desvioP2Nome = mView.findViewById(R.id.dialogo_qualidade_verion_nome_desvio_padrao_p2);

            letraItem1 = mView.findViewById(R.id.dialogo_qualidade_verion_letra_item1);
            letraItem2 = mView.findViewById(R.id.dialogo_qualidade_verion_letra_item2);
            letraItem3 = mView.findViewById(R.id.dialogo_qualidade_verion_letra_item3);
            letraItem4 = mView.findViewById(R.id.dialogo_qualidade_verion_letra_item4);

            mediaEditP1 = mView.findViewById(R.id.dialogo_qualidade_verion_media_p1);
            mediaEditP2 = mView.findViewById(R.id.dialogo_qualidade_verion_media_p2);
            desvioEditP1 = mView.findViewById(R.id.dialogo_qualidade_verion_desvio_padrao_p1);
            desvioEditP2 = mView.findViewById(R.id.dialogo_qualidade_verion_desvio_padrao_p2);

            FloatingActionButton botaoRegistrar = mView.findViewById(R.id.dialogo_qualidade_verion_botao_registrar);
            FloatingActionButton botaoCancelar = mView.findViewById(R.id.dialogo_qualidade_verion_botao_cancelar);

            mediaP1Nome.setText(atividadeIndicadores.get(0).getREFERENCIA() + "-" + joinOsInsumos.get(0).getDESCRICAO());
            desvioP1Nome.setText(atividadeIndicadores.get(1).getREFERENCIA() + "-" + joinOsInsumos.get(0).getDESCRICAO());
            mediaP2Nome.setText(atividadeIndicadores.get(2).getREFERENCIA() + "-" + joinOsInsumos.get(1).getDESCRICAO());
            desvioP2Nome.setText(atividadeIndicadores.get(3).getREFERENCIA() + "-" + joinOsInsumos.get(1).getDESCRICAO());

            letraItem1.setText(atividadeIndicadores.get(0).getUNIDADE_MEDIDA());
            letraItem2.setText(atividadeIndicadores.get(1).getUNIDADE_MEDIDA());
            letraItem3.setText(atividadeIndicadores.get(2).getUNIDADE_MEDIDA());
            letraItem4.setText(atividadeIndicadores.get(3).getUNIDADE_MEDIDA());


            if (jaTemVerion == true) {
                if (listaVerion.size() > 0) {
                    listaInsumoP1.setText(joinOsInsumos.get(0).getDESCRICAO());
                    listaInsumoP2.setText(joinOsInsumos.get(1).getDESCRICAO());

                    if (listaVerion.size() == 1) {
                        mediaEditP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                    }
                    if (listaVerion.size() == 2) {
                        mediaEditP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                        desvioEditP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));

                    }
                    if (listaVerion.size() == 3) {
                        mediaEditP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                        desvioEditP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                        mediaEditP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));

                    }
                    if (listaVerion.size() == 4) {
                        mediaEditP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                        desvioEditP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                        mediaEditP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));
                        desvioEditP2.setText(String.valueOf(listaVerion.get(3).getVALOR_INDICADOR()).replace('.', ','));
                    }
                }
            }

            mediaEditP1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) ultimoFocus = v.findFocus().getId();
                }
            });

            mediaEditP2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) ultimoFocus = v.findFocus().getId();
                }
            });

            desvioEditP1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) ultimoFocus = v.findFocus().getId();
                }
            });

            desvioEditP2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) ultimoFocus = v.findFocus().getId();
                }
            });

            if (auxSavedInstanceState == null) {
                mediaEditP1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(0).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(mediaEditP1, s, atividadeIndicadores.get(0).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                desvioEditP1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(1).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(desvioEditP1, s, atividadeIndicadores.get(1).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                });

                mediaEditP2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(2).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(mediaEditP2, s, atividadeIndicadores.get(2).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                desvioEditP2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(3).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(desvioEditP2, s, atividadeIndicadores.get(3).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                mediaEditP1.requestFocus();
            }

            if (dialogoVerionAberto == true && auxSavedInstanceState != null) {
                ultimoFocus = auxSavedInstanceState.getInt("ultimoFocus");
                EditText auxEdit = mView.findViewById(ultimoFocus);
                if (auxEdit != null) auxEdit.requestFocus();

                mediaEditP1 = mView.findViewById(R.id.dialogo_qualidade_verion_media_p1);
                mediaEditP2 = mView.findViewById(R.id.dialogo_qualidade_verion_media_p2);
                desvioEditP1 = mView.findViewById(R.id.dialogo_qualidade_verion_desvio_padrao_p1);
                desvioEditP2 = mView.findViewById(R.id.dialogo_qualidade_verion_desvio_padrao_p2);

                if (auxSavedInstanceState.getString("mediaEditP1") != null)
                    if (auxSavedInstanceState.getString("mediaEditP1").length() > 0)
                        mediaEditP1.setText(auxSavedInstanceState.getString("mediaEditP1"));

                if (auxSavedInstanceState.getString("mediaEditP2") != null)
                    if (auxSavedInstanceState.getString("mediaEditP2").length() > 0)
                        mediaEditP2.setText(auxSavedInstanceState.getString("mediaEditP2"));

                if (auxSavedInstanceState.getString("desvioEditP1") != null)
                    if (auxSavedInstanceState.getString("desvioEditP1").length() > 0)
                        desvioEditP1.setText(auxSavedInstanceState.getString("desvioEditP1"));

                if (auxSavedInstanceState.getString("desvioEditP2") != null)
                    if (auxSavedInstanceState.getString("desvioEditP2").length() > 0)
                        desvioEditP2.setText(auxSavedInstanceState.getString("desvioEditP2"));

                mediaEditP1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(0).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(mediaEditP1, s, atividadeIndicadores.get(0).getCASAS_DECIMAIS(), referencia, count, before);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                desvioEditP1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(1).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(desvioEditP1, s, atividadeIndicadores.get(1).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                });

                mediaEditP2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(2).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(mediaEditP2, s, atividadeIndicadores.get(2).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                desvioEditP2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String referencia = String.valueOf(atividadeIndicadores.get(3).getLIMITE_SUPERIOR());
                        ferramentas.mascaraVirgula(desvioEditP2, s, atividadeIndicadores.get(3).getCASAS_DECIMAIS(), referencia, count, before);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            mBuilder.setView(mView);
            dialogoVerion = mBuilder.create();
            dialogoVerion.show();
            dialogoVerion.setCanceledOnTouchOutside(false);
            dialogoVerion.setCancelable(false);

            botaoCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogoVerion.cancel();
                }
            });

            String finalAcao = acao;
            botaoRegistrar.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    boolean todosEditCorretos = true;

                    if (converteuPrecisao(mediaEditP1) == false) todosEditCorretos = false;
                    if (converteuPrecisao(mediaEditP2) == false) todosEditCorretos = false;
                    if (converteuPrecisao(desvioEditP1) == false) todosEditCorretos = false;
                    if (converteuPrecisao(desvioEditP2) == false) todosEditCorretos = false;

                    if (todosEditCorretos == false) {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                                .setTitle("Erro!")
                                .setMessage("Um ou mais campos contém valores inválidos, favor corrigir.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        try {
                            if (listaVerion.size() == 0) {
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 11, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(mediaEditP1.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 12, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP1.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 13, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(mediaEditP2.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 14, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP2.getText().toString().replace(',', '.')), 0));
                            }

                            if (listaVerion.size() == 1) {
                                listaVerion.get(0).setVALOR_INDICADOR(Double.valueOf(mediaEditP1.getText().toString().replace(',', '.')));
                                dao.update(listaVerion.get(0));

                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 12, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP1.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 13, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(mediaEditP2.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 14, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP2.getText().toString().replace(',', '.')), 0));
                            }

                            if (listaVerion.size() == 2) {
                                listaVerion.get(0).setVALOR_INDICADOR(Double.valueOf(mediaEditP1.getText().toString().replace(',', '.')));
                                listaVerion.get(1).setVALOR_INDICADOR(Double.valueOf(desvioEditP1.getText().toString().replace(',', '.')));
                                dao.update(listaVerion.get(0));
                                dao.update(listaVerion.get(1));

                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 13, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(mediaEditP2.getText().toString().replace(',', '.')), 0));
                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 14, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP2.getText().toString().replace(',', '.')), 0));
                            }

                            if (listaVerion.size() == 3) {
                                listaVerion.get(0).setVALOR_INDICADOR(Double.valueOf(mediaEditP1.getText().toString().replace(',', '.')));
                                listaVerion.get(1).setVALOR_INDICADOR(Double.valueOf(desvioEditP1.getText().toString().replace(',', '.')));
                                listaVerion.get(2).setVALOR_INDICADOR(Double.valueOf(mediaEditP2.getText().toString().replace(',', '.')));

                                dao.update(listaVerion.get(0));
                                dao.update(listaVerion.get(1));
                                dao.update(listaVerion.get(2));

                                dao.insert(new INDICADORES_SUBSOLAGEM(idProg, osSelecionada.getID_ATIVIDADE(), 14, ferramentas.formataDataDb(ferramentas.dataAtual()), Double.valueOf(desvioEditP2.getText().toString().replace(',', '.')), 0));
                            }

                            if (listaVerion.size() == 4) {
                                listaVerion.get(0).setVALOR_INDICADOR(Double.valueOf(mediaEditP1.getText().toString().replace(',', '.')));
                                listaVerion.get(1).setVALOR_INDICADOR(Double.valueOf(desvioEditP1.getText().toString().replace(',', '.')));
                                listaVerion.get(2).setVALOR_INDICADOR(Double.valueOf(mediaEditP2.getText().toString().replace(',', '.')));
                                listaVerion.get(3).setVALOR_INDICADOR(Double.valueOf(desvioEditP2.getText().toString().replace(',', '.')));

                                for (int i = 0; i < 4; i++) {
                                    dao.update(listaVerion.get(i));
                                    //Log.wtf("Valor Indicador "+ String.valueOf(listaVerion.get(i).getID_INDICADOR()),
                                    //String.valueOf(listaVerion.get(i).getVALOR_INDICADOR()));
                                }
                            }

                            listaInsumoP1.setText(joinOsInsumos.get(0).getDESCRICAO());
                            listaInsumoP2.setText(joinOsInsumos.get(1).getDESCRICAO());

                            listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                            listaDesvioP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                            listaMediaP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));
                            listaDesvioP2.setText(String.valueOf(listaVerion.get(3).getVALOR_INDICADOR()).replace('.', ','));

                        /*if (listaVerion.size() > 3) {
                            botaoVerion.setVisibility(View.GONE);
                        }*/

                            if (osSelecionada.getSTATUS_NUM() == 0) {
                                osSelecionada.setSTATUS("Andamento");
                                osSelecionada.setSTATUS_NUM(1);
                                osSelecionada.setDATA_INICIAL(ferramentas.formataDataDb(ferramentas.dataAtual()));

                                Ferramentas ferramentas = new Ferramentas();
                                osSelecionada.setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
                                dao.update(osSelecionada);
                            }

                            Ferramentas ferramentas = new Ferramentas();
                            FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                                    usuarioLogado.getEMAIL(), "Avaliação Qualidade", finalAcao + " Dados no sistema de precisão",
                                    String.valueOf("OS: " + osSelecionada.getID_PROGRAMACAO_ATIVIDADE() + " | Talhão: " + osSelecionada.getTALHAO()
                                            + " | Área: " + osSelecionada.getAREA_PROGRAMADA()
                                            + " | Área Realizada: " + osSelecionada.getAREA_REALIZADA()
                                            + " | P1Media: " + listaVerion.get(0).getVALOR_INDICADOR()
                                            + " | P1Desvio: " + listaVerion.get(1).getVALOR_INDICADOR()
                                            + " | P2Media: " + listaVerion.get(2).getVALOR_INDICADOR()
                                            + " | P1Desvio: " + listaVerion.get(3).getVALOR_INDICADOR()).replace('.', ','));
                            dao.insert(registroLog);

                            editouVerion = false;
                            dialogoVerionAberto = false;
                            dialogoVerion.dismiss();

                        } catch (Exception ex) {
                            listaVerion = dao.listaIndicadoresSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), osSelecionada.getID_ATIVIDADE());

                            listaMediaP1.setText(String.valueOf(listaVerion.get(0).getVALOR_INDICADOR()).replace('.', ','));
                            listaDesvioP1.setText(String.valueOf(listaVerion.get(1).getVALOR_INDICADOR()).replace('.', ','));
                            listaMediaP2.setText(String.valueOf(listaVerion.get(2).getVALOR_INDICADOR()).replace('.', ','));
                            listaDesvioP2.setText(String.valueOf(listaVerion.get(3).getVALOR_INDICADOR()).replace('.', ','));

                            ex.printStackTrace();
                            dialogoVerion.dismiss();

                            /*
                            for(int i = 0; i<listaVerion.size(); i++){
                                listaVerion.get(i).setVALOR_INDICADOR(0);
                                dao.update(listaVerion.get(i));
                            }
                            AlertDialog dialogoErro = new AlertDialog.Builder(ActivityQualidade.this)
                                    .setTitle("Erro")
                                    .setMessage("Houve um problema ao salvar a avaliação.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).create();
                            dialogoErro.show();
                             */
                        }

                    }
                }
            });

            dialogoVerion.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogoVerionAberto = false;
                    editouVerion = false;
                    auxSavedInstanceState = null;
                }
            });

            dialogoVerion.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    editouVerion = false;
                    dialogoVerionAberto = false;
                    auxSavedInstanceState = null;
                    Toast.makeText(ActivityQualidade.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            dialogoVerion.dismiss();
            AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                    .setTitle("Erro!")
                    .setMessage("Houve um problema ao abrir a tela de coleta do sistema de precisão.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        }
    }

    public void abreDialogoCorrecao() {

        dialogoCorrecaoAberto = true;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_correcao_ponto, null);
        FloatingActionButton botaoCorrecaoCancelar;
        mBuilder.setView(mView);

        naoHaNCNaoTratada = mView.findViewById(R.id.dialogo_correcao_nao_ha_nc);
        botaoCorrecaoRegistrar = mView.findViewById(R.id.dialogo_correcao_salvar);
        botaoCorrecaoCancelar = mView.findViewById(R.id.dialogo_correcao_cancelar);

        recyclerView = mView.findViewById(R.id.lista_correcao_ponto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorCorrecaoQualidade();

        recyclerView.setAdapter(adaptador);
        if (auxSavedInstanceState == null) listaCorrecoes = checaCorrecoes(listaCorrecoes);

        if (listaCorrecoes.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                    .setTitle("Erro")
                    .setMessage("Não há correções para serem feitas.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        } else {
            dialogoCorrecao = mBuilder.create();
            dialogoCorrecao.show();
            dialogoCorrecao.setCanceledOnTouchOutside(false);
            dialogoCorrecao.setCancelable(false);
            dialogoCorrecao.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    auxSavedInstanceState = null;
                    dialogoCorrecaoAberto = false;
                    listaPontosCorrecaoAux = new ArrayList<>();
                    listaCorrecoes = new ArrayList<>();
                    Toast.makeText(ActivityQualidade.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                }
            });

            dialogoCorrecao.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    auxSavedInstanceState = null;
                    dialogoCorrecaoAberto = false;
                }
            });

            botaoCorrecaoCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogoCorrecao.cancel();
                }
            });
            adaptador.setCorrecao(listaCorrecoes);
            botaoCorrecaoRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tam = listaPontosCorrecaoAux.size();
                    for (int i = 0; i < tam; i++) {
                        List<AVAL_PONTO_SUBSOLAGEM> ponto = listaPontosCorrecaoAux.get(i);
                        int tamPonto = ponto.size();
                        for (int j = 0; j < tamPonto; j++) {
                            if (ponto.get(j).getNC_TRATADA() == 1) {
                                FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                                        usuarioLogado.getEMAIL(), "Avaliação Qualidade", "Corrigiu o ponto " + ponto.get(j).getPONTO(),
                                        String.valueOf("OS: " + osSelecionada.getID_PROGRAMACAO_ATIVIDADE()
                                                + " | Talhão: " + osSelecionada.getTALHAO()
                                                + " | Área: " + osSelecionada.getAREA_PROGRAMADA()
                                                + " | Área Realizada: " + osSelecionada.getAREA_REALIZADA()
                                                + " | NC Tratada para o indicador: " + dao.descricaoIndicador(osSelecionada.getID_ATIVIDADE(), ponto.get(j).getID_INDICADOR())
                                        ).replace('.', ','));
                                dao.insert(registroLog);
                            }
                            dao.update(ponto.get(j));
                        }
                    }
                    listaPontosCorrecaoAux = new ArrayList<>();
                    listaCorrecoes = new ArrayList<>();
                    auxSavedInstanceState = null;
                    dialogoCorrecaoAberto = false;
                    Intent it = new Intent(ActivityQualidade.this, ActivityQualidade.class);
                    startActivity(it);
                    dialogoCorrecao.dismiss();
                }
            });
        }
    }

    public List<List<AVAL_PONTO_SUBSOLAGEM>> checaCorrecoes(List<List<AVAL_PONTO_SUBSOLAGEM>> pontos) {
        int tam = pontos.size();
        List<List<AVAL_PONTO_SUBSOLAGEM>> aux = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            List<AVAL_PONTO_SUBSOLAGEM> ponto = pontos.get(i);
            if (ponto.size() > 0) {
                boolean temCorrecao = false;
                int idReg = osSelecionada.getID_REGIONAL();
                int idSet = osSelecionada.getID_SETOR();
                String talhao = osSelecionada.getTALHAO();
                int ciclo = osSelecionada.getCICLO();
                int idManejo = osSelecionada.getID_MANEJO();
                int nPonto = ponto.get(0).getPONTO();
                CADASTRO_FLORESTAL cadastro_florestal = dao.selecionaCadFlorestal(idReg, idSet, talhao, ciclo, idManejo);

                String pegaEspacamento[] = dao.selecionaEspacamento(cadastro_florestal.getID_ESPACAMENTO()).getDESCRICAO().trim().replace(',', '.').split("X");
                double teste;
                try {
                    teste = Double.valueOf(pegaEspacamento[0]);
                } catch (NumberFormatException | NullPointerException n) {
                    teste = 3;
                }

                int idProg = osSelecionada.getID_PROGRAMACAO_ATIVIDADE();

                AVAL_SUBSOLAGEM aval_subsolagem = dao.selecionaAvalSubsolagem(idProg);

                boolean nc1 = dao.valorNaoConformeMenor(idProg, 1, aval_subsolagem.getPROFUNDIDADE(), nPonto);
                boolean nc2 = dao.valorNaoConformeForaDaFaixa(idProg, 2, aval_subsolagem.getESTRONDAMENTO_LATERAL_INFERIOR(), aval_subsolagem.getESTRONDAMENTO_LATERAL_SUPERIOR(), nPonto);
                boolean nc3 = dao.valorNaoConformeMenor(idProg, 3, aval_subsolagem.getFAIXA_SOLO_PREPARADA(), nPonto);
                boolean nc4 = dao.valorNaoConformeForaDaFaixa(idProg, 4, aval_subsolagem.getPROFUNDIDADE_ADUBO_INFERIOR(), aval_subsolagem.getPROFUNDIDADE_ADUBO_SUPERIOR(), nPonto);
                boolean nc5 = dao.valorNaoConformebool(idProg, 5, nPonto);
                boolean nc6 = dao.valorNaoConformeForaDaFaixa(idProg, 6, teste * 0.95, teste * 1.05, nPonto);
                boolean nc7 = dao.valorNaoConformebool(idProg, 7, nPonto);
                boolean nc8 = dao.valorNaoConformebool(idProg, 8, nPonto);
                boolean nc9 = dao.valorNaoConformebool(idProg, 9, nPonto);
                boolean nc10 = dao.valorNaoConformeForaDaFaixa(idProg, 10, aval_subsolagem.getLOCALIZACAO_INSUMO_INFERIOR(), aval_subsolagem.getLOCALIZACAO_INSUMO_SUPERIOR(), nPonto);

                if (nc1 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(0).getID_ATIVIDADE(), ponto.get(0).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc2 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(1).getID_ATIVIDADE(), ponto.get(1).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc3 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(2).getID_ATIVIDADE(), ponto.get(2).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc4 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(3).getID_ATIVIDADE(), ponto.get(3).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc5 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(4).getID_ATIVIDADE(), ponto.get(4).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc6 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(5).getID_ATIVIDADE(), ponto.get(5).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc7 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(6).getID_ATIVIDADE(), ponto.get(6).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc8 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(7).getID_ATIVIDADE(), ponto.get(7).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc9 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(8).getID_ATIVIDADE(), ponto.get(8).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (nc10 == true) {
                    if (dao.indicadorCorrigivel(ponto.get(9).getID_ATIVIDADE(), ponto.get(9).getID_INDICADOR()) == 1) {
                        temCorrecao = true;
                    }
                }

                if (temCorrecao == true) {
                    aux.add(ponto);
                }
            }
        }
        return aux;
    }

    //Abre caixa de diálogo para edição dos dados de um ponto
    public void abreDialogoEdicao() {
        dialogoEdicaoAberto = true;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_edicao_ponto, null);

        NDSpinner spinnerEdicaoPonto = mView.findViewById(R.id.spinner_dialogo_edicao_ponto);
        Button botaoConfirmar = mView.findViewById(R.id.dialogo_confirmar_edicao_ponto);
        Button botaoCancelar = mView.findViewById(R.id.dialogo_cancelar_edicao_ponto);

        mBuilder.setView(mView);

        dialogoEdicao = mBuilder.create();
        dialogoEdicao.show();
        dialogoEdicao.setCanceledOnTouchOutside(false);
        dialogoEdicao.setCancelable(false);

        ArrayAdapter<AVAL_PONTO_SUBSOLAGEM> adapterPonto;

        adapterPonto = new ArrayAdapter<AVAL_PONTO_SUBSOLAGEM>(this,
                android.R.layout.simple_spinner_item, listaPonto);
        adapterPonto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEdicaoPonto.setAdapter(adapterPonto);

        spinnerEdicaoPonto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSelecionadoSpinnerEdicao = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemSelecionadoSpinnerEdicao = 0;
            }
        });

        if (itemSelecionadoSpinnerEdicao == null) itemSelecionadoSpinnerEdicao = 0;

        spinnerEdicaoPonto.setSelection(itemSelecionadoSpinnerEdicao);


        itemSelecionadoSpinnerEdicao = spinnerEdicaoPonto.getSelectedItemPosition();


        dialogoEdicao.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                auxSavedInstanceState = null;
                dialogoEdicaoAberto = false;
                Toast.makeText(ActivityQualidade.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
            }
        });

        dialogoEdicao.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                dialogoEdicaoAberto = false;
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelecionadoSpinnerEdicao = 0;
                numeroPontoAtual = null;
                dialogoEdicao.cancel();
            }
        });

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuarioLogado.getNIVEL_ACESSO() == 0 &&
                        listaPonto.get(itemSelecionadoSpinnerEdicao).getUPDATED_AT() != null) {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                            .setTitle("Erro")
                            .setMessage("O usuário não tem permissão para editar o ponto selecionado")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                } else {
                    dialogoEdicaoAberto = false;
                    abreDialogoPonto(listaPonto.get(itemSelecionadoSpinnerEdicao));
                }
            }
        });
    }


    //Abre caixa de diálogo para preenchimento dos dados do ponto
    public void abreDialogoPonto(AVAL_PONTO_SUBSOLAGEM ponto) {
        dialogoPontoAberto = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_ponto, null);

        TextView numeroPonto = mView.findViewById(R.id.dialogo_qualidade_ponto_numero);

        textItem1 = mView.findViewById(R.id.dialogo_qualidade_ponto_item1);
        textItem2 = mView.findViewById(R.id.dialogo_qualidade_ponto_item2);
        textItem3 = mView.findViewById(R.id.dialogo_qualidade_ponto_item3);
        textItem4 = mView.findViewById(R.id.dialogo_qualidade_ponto_item4);
        textItem5 = mView.findViewById(R.id.dialogo_qualidade_ponto_item5);
        textItem6 = mView.findViewById(R.id.dialogo_qualidade_ponto_item6);
        textItem7 = mView.findViewById(R.id.dialogo_qualidade_ponto_item7);
        textItem8 = mView.findViewById(R.id.dialogo_qualidade_ponto_item8);
        textItem9 = mView.findViewById(R.id.dialogo_qualidade_ponto_item9);
        textItem10 = mView.findViewById(R.id.dialogo_qualidade_ponto_item10);

        textLetra1 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item1);
        textLetra2 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item2);
        textLetra3 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item3);
        textLetra4 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item4);
        textLetra5 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item5);
        textLetra6 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item6);
        textLetra7 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item7);
        textLetra8 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item8);
        textLetra9 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item9);
        textLetra10 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item10);

        editItem1 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item1);
        editItem2 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item2);
        editItem3 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item3);
        editItem4 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item4);
        editItem5 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item5);
        editItem6 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item6);
        editItem7 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item7);
        editItem8 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item8);
        editItem9 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item9);
        editItem10 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item10);

        FloatingActionButton botaoRegistrar = mView.findViewById(R.id.dialogo_qualidade_ponto_botao_registrar);
        FloatingActionButton botaoCancelar = mView.findViewById(R.id.botao_cancelar_dialogo_qualidade);
        atividadeIndicadores = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "N");

        if (numeroPontoAtual == null) {
            numeroPontoAtual = (listaPonto.size() + 1);
        }

        if (ponto == null) {
            editouPonto = false;
            numeroPonto.setText(String.valueOf(numeroPontoAtual));
        } else {
            editouPonto = true;
            dialogoEdicao.dismiss();
            numeroPontoAtual = ponto.getPONTO();
            numeroPonto.setText(String.valueOf(numeroPontoAtual));
            editItem1.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(0).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));

            editItem2.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(1).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));

            editItem3.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(2).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));

            editItem4.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(3).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));

            if (dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(4).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR() == 1) {
                editItem5.setChecked(true);
            } else {
                editItem5.setChecked(false);
            }

            editItem6.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(5).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));

            if (dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(6).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR() == 1) {
                editItem7.setChecked(true);
            } else {
                editItem7.setChecked(false);
            }

            if (dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(7).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR() == 1) {
                editItem8.setChecked(true);
            } else {
                editItem8.setChecked(false);
            }

            if (dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(8).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR() == 1) {
                editItem9.setChecked(true);
            } else {
                editItem9.setChecked(false);
            }

            editItem10.setText(String.valueOf(dao.selecionaAvalPontoSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                    atividadeIndicadores.get(9).getID_INDICADOR(), ponto.getPONTO()).getVALOR_INDICADOR()).replace('.', ','));
        }


        textItem1.setText(atividadeIndicadores.get(0).getREFERENCIA() + "-" + atividadeIndicadores.get(0).getDESCRICAO());
        textItem2.setText(atividadeIndicadores.get(1).getREFERENCIA() + "-" + atividadeIndicadores.get(1).getDESCRICAO());
        textItem3.setText(atividadeIndicadores.get(2).getREFERENCIA() + "-" + atividadeIndicadores.get(2).getDESCRICAO());
        textItem4.setText(atividadeIndicadores.get(3).getREFERENCIA() + "-" + atividadeIndicadores.get(3).getDESCRICAO());
        textItem5.setText(atividadeIndicadores.get(4).getREFERENCIA() + "-" + atividadeIndicadores.get(4).getDESCRICAO());
        textItem6.setText(atividadeIndicadores.get(5).getREFERENCIA() + "-" + atividadeIndicadores.get(5).getDESCRICAO());
        textItem7.setText(atividadeIndicadores.get(6).getREFERENCIA() + "-" + atividadeIndicadores.get(6).getDESCRICAO());
        textItem8.setText(atividadeIndicadores.get(7).getREFERENCIA() + "-" + atividadeIndicadores.get(7).getDESCRICAO());
        textItem9.setText(atividadeIndicadores.get(8).getREFERENCIA() + "-" + atividadeIndicadores.get(8).getDESCRICAO());
        textItem10.setText(atividadeIndicadores.get(9).getREFERENCIA() + "-" + atividadeIndicadores.get(9).getDESCRICAO());

        textLetra1.setText(atividadeIndicadores.get(0).getUNIDADE_MEDIDA());
        textLetra2.setText(atividadeIndicadores.get(1).getUNIDADE_MEDIDA());
        textLetra3.setText(atividadeIndicadores.get(2).getUNIDADE_MEDIDA());
        textLetra4.setText(atividadeIndicadores.get(3).getUNIDADE_MEDIDA());
        //textLetra5.setText(atividadeIndicadores.get(4).getUNIDADE_MEDIDA());
        textLetra6.setText(atividadeIndicadores.get(5).getUNIDADE_MEDIDA());
        //textLetra7.setText(atividadeIndicadores.get(6).getUNIDADE_MEDIDA());
        //textLetra8.setText(atividadeIndicadores.get(7).getUNIDADE_MEDIDA());
        //textLetra9.setText(atividadeIndicadores.get(8).getUNIDADE_MEDIDA());
        textLetra10.setText(atividadeIndicadores.get(9).getUNIDADE_MEDIDA());

        editItem1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });

        editItem2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });

        editItem3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });

        editItem4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });

        editItem6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });

        editItem10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) ultimoFocus = v.findFocus().getId();
            }
        });


        if (auxSavedInstanceState == null) {
            editItem1.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(0).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem1, s, atividadeIndicadores.get(0).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem2.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(1).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem2, s, atividadeIndicadores.get(1).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem3.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(2).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem3, s, atividadeIndicadores.get(2).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem4.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(3).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem4, s, atividadeIndicadores.get(3).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem6.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(5).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem6, s, atividadeIndicadores.get(5).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            editItem10.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(9).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem10, s, atividadeIndicadores.get(9).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem1.requestFocus();
        }

        if (dialogoPontoAberto == true && auxSavedInstanceState != null) {
            ultimoFocus = auxSavedInstanceState.getInt("ultimoFocus");
            EditText auxEdit = mView.findViewById(ultimoFocus);
            if (auxEdit != null) auxEdit.requestFocus();

            editItem1 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item1);
            editItem2 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item2);
            editItem3 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item3);
            editItem4 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item4);
            editItem5 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item5);
            editItem6 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item6);
            editItem7 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item7);
            editItem8 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item8);
            editItem9 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item9);
            editItem10 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item10);

            editItem1.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(0).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem1, s, atividadeIndicadores.get(0).getCASAS_DECIMAIS(), referencia, count, before);

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem2.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(1).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem2, s, atividadeIndicadores.get(1).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem3.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(2).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem3, s, atividadeIndicadores.get(2).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem4.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(3).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem4, s, atividadeIndicadores.get(3).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editItem6.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(5).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem6, s, atividadeIndicadores.get(5).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            editItem10.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String referencia = String.valueOf(atividadeIndicadores.get(9).getLIMITE_SUPERIOR());
                    ferramentas.mascaraVirgula(editItem10, s, atividadeIndicadores.get(9).getCASAS_DECIMAIS(), referencia, count, before);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            if (auxSavedInstanceState.getBoolean("editItem5") == true) editItem5.setChecked(true);
            if (auxSavedInstanceState.getBoolean("editItem7") == true) editItem7.setChecked(true);
            if (auxSavedInstanceState.getBoolean("editItem8") == true) editItem8.setChecked(true);
            if (auxSavedInstanceState.getBoolean("editItem9") == true) editItem9.setChecked(true);

            if (auxSavedInstanceState.getString("editItem1") != null)
                if (auxSavedInstanceState.getString("editItem1").length() > 0)
                    editItem1.setText(auxSavedInstanceState.getString("editItem1"));

            if (auxSavedInstanceState.getString("editItem2") != null)
                if (auxSavedInstanceState.getString("editItem2").length() > 0)
                    editItem2.setText(auxSavedInstanceState.getString("editItem2"));

            if (auxSavedInstanceState.getString("editItem3") != null)
                if (auxSavedInstanceState.getString("editItem3").length() > 0)
                    editItem3.setText(auxSavedInstanceState.getString("editItem3"));

            if (auxSavedInstanceState.getString("editItem4") != null)
                if (auxSavedInstanceState.getString("editItem4").length() > 0)
                    editItem4.setText(auxSavedInstanceState.getString("editItem4"));

            if (auxSavedInstanceState.getString("editItem6") != null)
                if (auxSavedInstanceState.getString("editItem6").length() > 0)
                    editItem6.setText(auxSavedInstanceState.getString("editItem6"));

            if (auxSavedInstanceState.getString("editItem10") != null)
                if (auxSavedInstanceState.getString("editItem10").length() > 0)
                    editItem10.setText(auxSavedInstanceState.getString("editItem10"));
        }

        mBuilder.setView(mView);

        dialogoPonto = mBuilder.create();
        dialogoPonto.show();
        dialogoPonto.setCanceledOnTouchOutside(false);
        dialogoPonto.setCancelable(false);

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoPonto.cancel();
                numeroPontoAtual = null;
                itemSelecionadoSpinnerEdicao = 0;
            }
        });


        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                boolean todosEditCorretos = true;

                if (converteuPonto(editItem1, atividadeIndicadores.get(0).getLIMITE_INFERIOR(), atividadeIndicadores.get(0).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;
                if (converteuPonto(editItem2, atividadeIndicadores.get(1).getLIMITE_INFERIOR(), atividadeIndicadores.get(1).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;
                if (converteuPonto(editItem3, atividadeIndicadores.get(2).getLIMITE_INFERIOR(), atividadeIndicadores.get(2).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;
                if (converteuPonto(editItem4, atividadeIndicadores.get(3).getLIMITE_INFERIOR(), atividadeIndicadores.get(3).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;
                if (converteuPonto(editItem6, atividadeIndicadores.get(5).getLIMITE_INFERIOR(), atividadeIndicadores.get(5).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;
                if (converteuPonto(editItem10, atividadeIndicadores.get(9).getLIMITE_INFERIOR(), atividadeIndicadores.get(9).getLIMITE_SUPERIOR()) == false)
                    todosEditCorretos = false;

                if (todosEditCorretos == false) {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                            .setTitle("Erro!")
                            .setMessage("Um ou mais campos contém valores inválidos, favor corrigir.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                } else {
                    if (ActivityCompat.checkSelfPermission(ActivityQualidade.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityQualidade.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    if (ActivityCompat.checkSelfPermission(ActivityQualidade.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityQualidade.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    longitude = 0;
                    latitude = 0;

                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }

                    if (location == null) {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                                .setTitle("Falha de Comunicação com o GPS")
                                .setMessage("Não foi possível salvar as coordenadas de localização do dispositivo.\nAs coordenadas serão zeradas")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        salvaPonto(ponto);
                                    }
                                }).create();
                        dialog.show();
                    } else salvaPonto(ponto);
                }
            }
        });

        dialogoPonto.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        dialogoPonto.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogoPontoAberto = false;
                editouPonto = false;
                auxSavedInstanceState = null;
                Toast.makeText(ActivityQualidade.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvaPonto(AVAL_PONTO_SUBSOLAGEM ponto) {
        double check = 0;
        String dataAtual = null;

        if (ponto == null) {
            dataAtual = ferramentas.formataDataDb(data.getText().toString());
        } else {
            dataAtual = ponto.getDATA();
        }
        try {
            /*DecimalFormat df = new DecimalFormat(".####");
            String s = df.format(String.valueOf(latitude));
            latitude = Double.valueOf(s);

            s = df.format(String.valueOf(longitude));
            longitude = Double.valueOf(s);
*/

            BigDecimal bd = BigDecimal.valueOf(latitude);
            bd = bd.setScale(5, RoundingMode.HALF_EVEN);
            latitude = bd.doubleValue();

            bd = BigDecimal.valueOf(longitude);
            bd = bd.setScale(5, RoundingMode.HALF_EVEN);
            longitude = bd.doubleValue();

            //Log.wtf("Latitude", String.valueOf(latitude));
            //Log.wtf("Longitude", String.valueOf(longitude));

            if (ponto == null) {
                AVAL_PONTO_SUBSOLAGEM insere;
                insere = new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual, numeroPontoAtual
                        , osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(0).getID_INDICADOR(),
                        Double.valueOf(editItem1.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0);

                dao.insert(insere);


                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(1).getID_INDICADOR(),
                        Double.valueOf(editItem2.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(2).getID_INDICADOR(),
                        Double.valueOf(editItem3.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(3).getID_INDICADOR(),
                        Double.valueOf(editItem4.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0));

                if (editItem5.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(4).getID_INDICADOR(),
                        check, latitude, longitude, 0, null, 0));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(5).getID_INDICADOR(),
                        Double.valueOf(editItem6.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0));

                if (editItem7.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(6).getID_INDICADOR(),
                        check, latitude, longitude, 0, null, 0));

                if (editItem8.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(7).getID_INDICADOR(),
                        check, latitude, longitude, 0, null, 0));

                if (editItem9.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(8).getID_INDICADOR(),
                        check, latitude, longitude, 0, null, 0));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(9).getID_INDICADOR(),
                        Double.valueOf(editItem10.getText().toString().replace(',', '.')), latitude, longitude, 0, null, 0));
            } else {
                List<AVAL_PONTO_SUBSOLAGEM> todosIndicadoresPonto =
                        dao.todosIndicadoresPonto(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), numeroPontoAtual);
                AVAL_PONTO_SUBSOLAGEM insere;
                Integer fazPut = 0;

                if (todosIndicadoresPonto.get(0).getUPDATED_AT() != null) {
                    fazPut = 1;
                }

                insere = new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual, numeroPontoAtual
                        , osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(0).getID_INDICADOR(),
                        Double.valueOf(editItem1.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(0).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(0).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(0).getNC_TRATADA(),
                        todosIndicadoresPonto.get(0).getUPDATED_AT(), fazPut);

                dao.insert(insere);


                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(1).getID_INDICADOR(),
                        Double.valueOf(editItem2.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(1).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(1).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(1).getNC_TRATADA(),
                        todosIndicadoresPonto.get(1).getUPDATED_AT(), fazPut));


                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(2).getID_INDICADOR(),
                        Double.valueOf(editItem3.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(2).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(2).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(2).getNC_TRATADA(),
                        todosIndicadoresPonto.get(2).getUPDATED_AT(), fazPut));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(3).getID_INDICADOR(),
                        Double.valueOf(editItem4.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(3).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(3).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(3).getNC_TRATADA(),
                        todosIndicadoresPonto.get(3).getUPDATED_AT(), fazPut));

                if (editItem5.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(4).getID_INDICADOR(),
                        check,
                        todosIndicadoresPonto.get(4).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(4).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(4).getNC_TRATADA(),
                        todosIndicadoresPonto.get(4).getUPDATED_AT(), fazPut));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(5).getID_INDICADOR(),
                        Double.valueOf(editItem6.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(5).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(5).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(5).getNC_TRATADA(),
                        todosIndicadoresPonto.get(5).getUPDATED_AT(), fazPut));

                if (editItem7.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(6).getID_INDICADOR(),
                        check,
                        todosIndicadoresPonto.get(6).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(6).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(6).getNC_TRATADA(),
                        todosIndicadoresPonto.get(6).getUPDATED_AT(), fazPut));

                if (editItem8.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(7).getID_INDICADOR(),
                        check,
                        todosIndicadoresPonto.get(7).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(7).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(7).getNC_TRATADA(),
                        todosIndicadoresPonto.get(7).getUPDATED_AT(), fazPut));

                if (editItem9.isChecked()) check = 1;
                else check = 0;
                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(8).getID_INDICADOR(),
                        check,
                        todosIndicadoresPonto.get(8).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(8).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(8).getNC_TRATADA(),
                        todosIndicadoresPonto.get(8).getUPDATED_AT(), fazPut));

                dao.insert(new AVAL_PONTO_SUBSOLAGEM(idProg, dataAtual,
                        numeroPontoAtual, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(9).getID_INDICADOR(),
                        Double.valueOf(editItem10.getText().toString().replace(',', '.')),
                        todosIndicadoresPonto.get(9).getCOORDENADA_X(),
                        todosIndicadoresPonto.get(9).getCOORDENADA_Y(),
                        todosIndicadoresPonto.get(9).getNC_TRATADA(),
                        todosIndicadoresPonto.get(9).getUPDATED_AT(), fazPut));
            }


            String acao = "Inseriu";

            if (editouPonto == true) {
                acao = "Editou";
            } else {
                acao = "Inseriu";
            }

            String valorItem5 = "Não";
            if (editItem5.isChecked()) valorItem5 = "Sim";

            String valorItem7 = "Não";
            if (editItem7.isChecked()) valorItem5 = "Sim";

            String valorItem8 = "Não";
            if (editItem8.isChecked()) valorItem5 = "Sim";

            String valorItem9 = "Não";
            if (editItem9.isChecked()) valorItem5 = "Sim";


            Ferramentas ferramentas = new Ferramentas();
            FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                    usuarioLogado.getEMAIL(), "Avaliação Qualidade", acao + " Ponto " + numeroPontoAtual,
                    String.valueOf("OS: " + osSelecionada.getID_PROGRAMACAO_ATIVIDADE() + " | Talhão: " + osSelecionada.getTALHAO()
                            + " | Área: " + osSelecionada.getAREA_PROGRAMADA()
                            + " | Área Realizada: " + osSelecionada.getAREA_REALIZADA()
                            + " | " + textItem1.getText().toString() + ": " + editItem1.getText().toString()
                            + " | " + textItem2.getText().toString() + ": " + editItem2.getText().toString()
                            + " | " + textItem3.getText().toString() + ": " + editItem3.getText().toString()
                            + " | " + textItem4.getText().toString() + ": " + editItem4.getText().toString()
                            + " | " + textItem5.getText().toString() + ": " + valorItem5
                            + " | " + textItem6.getText().toString() + ": " + editItem6.getText().toString()
                            + " | " + textItem7.getText().toString() + ": " + valorItem7
                            + " | " + textItem8.getText().toString() + ": " + valorItem8
                            + " | " + textItem9.getText().toString() + ": " + valorItem9
                            + " | " + textItem10.getText().toString() + ": " + editItem10.getText().toString()
                    ).replace('.', ','));
            dao.insert(registroLog);

            if (osSelecionada.getSTATUS_NUM() == 0) {
                osSelecionada.setSTATUS("Andamento");
                osSelecionada.setSTATUS_NUM(1);
                osSelecionada.setDATA_INICIAL(ferramentas.formataDataDb(ferramentas.dataAtual()));

                Ferramentas ferramentasAux = new Ferramentas();
                osSelecionada.setUPDATED_AT(ferramentasAux.dataHoraMinutosSegundosAtual());
                dao.update(osSelecionada);
            }

            dialogoPontoAberto = false;
            editouPonto = false;
            Intent it = new Intent(ActivityQualidade.this, ActivityQualidade.class);
            startActivity(it);
            dialogoPonto.dismiss();
        } catch (SQLiteConstraintException | NullPointerException ex) {
            dialogoPonto.dismiss();
            ex.printStackTrace();
            AlertDialog dialogoErro = new AlertDialog.Builder(ActivityQualidade.this)
                    .setTitle("Erro")
                    .setMessage("Houve um problema ao salvar o ponto.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialogoErro.show();
        }
    }

    //Verifica se o valor em um EditText no dialog de registro de ponto é válido
    private boolean converteuPonto(EditText valor, int limiteInf, int limiteSup) {
        double teste;
        try {
            teste = Double.valueOf(valor.getText().toString().replace(',', '.'));
        } catch (NumberFormatException | NullPointerException n) {
            valor.setError("Valor Inválido");
            return false;
        }
        if (teste > limiteSup || teste < limiteInf) {
            valor.setError("Valor Fora da Faixa:\nMínimo: " + String.valueOf(limiteInf) + "\nMáximo: " + String.valueOf(limiteSup));
            return false;
        }
        return true;
    }

    //Verifica se o valor em um EditText no dialog de registro de ponto é válido
    private boolean converteuPrecisao(EditText valor) {
        double teste;
        try {
            teste = Double.valueOf(valor.getText().toString().replace(',', '.'));
        } catch (NumberFormatException | NullPointerException n) {
            valor.setError("Valor Inválido");
            return false;
        }

        return true;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dialogoPontoAberto", dialogoPontoAberto);
        outState.putBoolean("dialogoVerionAberto", dialogoVerionAberto);
        outState.putBoolean("dialogoCorrecaoAberto", dialogoCorrecaoAberto);
        outState.putBoolean("dialogoEdicaoAberto", dialogoEdicaoAberto);
        if (dialogoEdicaoAberto == true) {
            outState.putInt("itemSelecionadoSpinnerEdicao", itemSelecionadoSpinnerEdicao);
        }

        if (dialogoPontoAberto == true) {
            boolean item5 = false;
            boolean item7 = false;
            boolean item8 = false;
            boolean item9 = false;

            if (editItem5.isChecked()) item5 = true;
            if (editItem7.isChecked()) item7 = true;
            if (editItem8.isChecked()) item8 = true;
            if (editItem9.isChecked()) item9 = true;

            outState.putString("editItem1", editItem1.getText().toString());
            outState.putString("editItem2", editItem2.getText().toString());
            outState.putString("editItem3", editItem3.getText().toString());
            outState.putString("editItem4", editItem4.getText().toString());
            outState.putBoolean("editItem5", item5);
            outState.putString("editItem6", editItem6.getText().toString());
            outState.putBoolean("editItem7", item7);
            outState.putBoolean("editItem8", item8);
            outState.putBoolean("editItem9", item9);
            outState.putString("editItem10", editItem10.getText().toString());

            outState.putInt("ultimoFocus", ultimoFocus);

            outState.putInt("numeroPontoAtual", numeroPontoAtual);
        }

        if (dialogoVerionAberto == true) {
            outState.putString("mediaEditP1", mediaEditP1.getText().toString());
            outState.putString("mediaEditP2", mediaEditP2.getText().toString());
            outState.putString("desvioEditP1", desvioEditP1.getText().toString());
            outState.putString("desvioEditP2", desvioEditP2.getText().toString());

            outState.putInt("ultimoFocus", ultimoFocus);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogoVerionAberto == true) dialogoVerion.dismiss();
        if (dialogoPontoAberto == true) dialogoPonto.dismiss();
        if (dialogoCorrecaoAberto == true) dialogoCorrecao.dismiss();
        if (dialogoEdicaoAberto == true) dialogoEdicao.dismiss();
    }

    @Override
    public void onBackPressed() {
    }
}