package com.example.forestsys.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.graphics.Color;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorApontamentos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.Classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;
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
import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo1;
import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo2;
import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.erroPrestadorBool;
import static com.example.forestsys.Activities.ActivityAtividades.insumoRec1;
import static com.example.forestsys.Activities.ActivityAtividades.insumoRec2;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
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

import static com.example.forestsys.Activities.ActivityAtividades.hh;
import static com.example.forestsys.Activities.ActivityAtividades.hm;
import static com.example.forestsys.Activities.ActivityAtividades.hme;
import static com.example.forestsys.Activities.ActivityAtividades.ho;
import static com.example.forestsys.Activities.ActivityAtividades.hoe;
import static com.example.forestsys.Activities.ActivityAtividades.obs;
import static com.example.forestsys.Activities.ActivityAtividades.area;
import static com.example.forestsys.Adapters.AdaptadorFragmentoInsumos.insumoConforme1;
import static com.example.forestsys.Adapters.AdaptadorFragmentoInsumos.insumoConforme2;
import static java.sql.Types.NULL;

public class ActivityRegistros extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView talhaoOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView areaRealizada;
    public static TextView dataApontamento;
    private FloatingActionButton voltar;
    private FloatingActionButton botaoSalvar;
    private ImageButton botaoDatePicker;
    private static Ferramentas ferramentas;
    public static String dataDoApontamento;

    private BaseDeDados baseDeDados;
    private static DAO dao;

    private double hoAux = 0;
    private double hhAux = 0;
    private double hmAux = 0;
    private double hoeAux = 0;
    private double hmeAux = 0;
    private double areaAux = 0;

    private List<O_S_ATIVIDADES_DIA> listaAtividades;
    private List<O_S_ATIVIDADE_INSUMOS> atividade_insumos = new ArrayList<O_S_ATIVIDADE_INSUMOS>();

    private FragmentoInsumos fragmentoInsumos;
    private FragmentoRendimento fragmentoRendimento;

    private AlertDialog dialogoEdicaoReg;
    private boolean abriuDialogoEdicaoReg;
    public static Bundle auxSavedInstanceState;
    private EditText valorEdicaoRegJustificativa;

    private AlertDialog dialogoQtdForaFaixa;
    private boolean abriuDialogoForaFaixa;
    private EditText valorJustificativa1;
    private EditText valorJustificativa2;

    private double testeAreaRealizada;
    private boolean erro;
    private boolean erroInsumos;
    private double novaArea;
    private double areaAnterior;
    private boolean erroGeral = false;
    private boolean edicaoReg = false;

    private Integer idAtividadeDia = null;
    private String acaoInativo = null;
    private String regDescarregado = "N";

    private boolean insumosNaoConforme = false;
    private String pattern;
    private static SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auxSavedInstanceState = savedInstanceState;
        try {
            inicializacao();
        } catch (Exception e) {
            e.printStackTrace();
            Intent it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
            it.putExtra("erroAbrirRegistros", true);
            startActivity(it);
        }

        if (savedInstanceState != null) {
            dataDoApontamento = savedInstanceState.getString("data");
            dataApontamento.setText(dataDoApontamento);
            idAtividadeDia = savedInstanceState.getInt("idAtividadeDia");
            if (idAtividadeDia == 0) {
                idAtividadeDia = null;
            }
            acaoInativo = savedInstanceState.getString("acaoInativo");
            regDescarregado = savedInstanceState.getString("regDescarregado");

            abriuDialogoEdicaoReg = savedInstanceState.getBoolean("abriuDialogoEdicaoReg");
            editouRegistro = savedInstanceState.getBoolean("editouRegistro");
            if (abriuDialogoEdicaoReg == true) abreDialogoEdicaoReg();

            abriuDialogoForaFaixa = savedInstanceState.getBoolean("abriuDialogoForaFaixa");
            if (abriuDialogoForaFaixa == true) abreDialogoQtdForaFaixa();

            savedInstanceState.getBoolean("insumosNaoConforme");
        }
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

    //Inicializa todas as variáveis e todos os itens da tela
    public void inicializacao() {
        setContentView(R.layout.activity_registros);
        setTitle(nomeEmpresaPref);

        ferramentas = new Ferramentas();

        abriuDialogoEdicaoReg = false;
        edicaoReg = false;

        if (osSelecionada.getSTATUS_NUM() != 2)
            if (listaAtividades != null)
                if (listaAtividades.size() > 0)
                    Toast.makeText(this, "Toque o registro para edita-lo", Toast.LENGTH_LONG).show();

        Toolbar toolbar = findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());


        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        listaAtividades = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        atividade_insumos = dao.listaInsumosatividade(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        dataApontamento = findViewById(R.id.data_apontamento);
        talhaoOs = findViewById(R.id.talhao_os_continuar);
        statusOs = findViewById(R.id.status_os_continuar);
        areaOs = findViewById(R.id.area_prog_os_continuar);
        botaoDatePicker = findViewById(R.id.botao_date_picker);
        areaRealizada = findViewById(R.id.area_realizada_os_continuar);

        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));

        statusOs.setText(String.valueOf(osSelecionada.getSTATUS()));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace(".", ","));

        voltar = findViewById(R.id.botao_continuar_voltar);
        botaoSalvar = findViewById(R.id.botao_prosseguir_continuar);

        //if (editouRegistro) botaoDatePicker.setEnabled(false);

        if (editouRegistro == false) {
            Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);
            mes += 1;

            String a = String.valueOf(ano);
            String m = String.valueOf(mes);
            String d = String.valueOf(dia);

            if (mes < 10) m = "0" + m;

            if (dia < 10) d = "0" + d;

            dataDoApontamento = ferramentas.dataAtual();
        } else
            dataDoApontamento = ferramentas.formataDataTextView(oSAtividadesDiaAtual.getDATA());

        dataApontamento.setText(dataDoApontamento);

        drawer = findViewById(R.id.drawer_layout_continuar);
        NavigationView navigationView = findViewById(R.id.nav_view_continuar);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (auxSavedInstanceState == null) {
            fragmentoInsumos = new FragmentoInsumos();
            getSupportFragmentManager().beginTransaction().replace(R.id.registro_fragmento_insumos,
                    fragmentoInsumos).commit();
        }

        if (auxSavedInstanceState == null) {
            fragmentoRendimento = new FragmentoRendimento();
            getSupportFragmentManager().beginTransaction().replace(R.id.registro_fragmento_rendimento,
                    fragmentoRendimento).commit();
        }


        calculaTotais();


        pattern = ("dd-MM-yyyy");
        sdf = new SimpleDateFormat(pattern);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auxTeste = dataDoApontamento;
                boolean dataAntesDaProgramada = false;

                //Pedaço de código responsável por checar se a data selecionada é posterior á programada
                /*try {
                    Date date1 = sdf.parse(auxTeste);
                    Date date2 = sdf.parse((ferramentas.formataDataTextView(osSelecionada.getDATA_PROGRAMADA())));

                    if (date1.before(date2)) {
                        dataAntesDaProgramada = true;
                        AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Erro!")
                                .setMessage("A data selecionada deve ser igual ou posterior a data programada da atividade.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                if (dataAntesDaProgramada == false) {
                    if (editouRegistro == false && dao.selecionaOsAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                            ferramentas.formataDataDb(dataDoApontamento)) != null) {

                        erroGeral = true;

                        AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Erro!")
                                .setMessage("A data selecionada já tem um registro cadastrado.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        testaConformidade();

                        if (erro == false && (novaArea + osSelecionada.getAREA_REALIZADA()) > osSelecionada.getAREA_PROGRAMADA()) {
                            DecimalFormat format = new DecimalFormat("###.#####");
                            Double d = (novaArea + osSelecionada.getAREA_REALIZADA()) - osSelecionada.getAREA_PROGRAMADA();
                            String diferenca = format.format(Double.valueOf(String.valueOf(d)));
                            diferenca.replace('.', ',');

                            AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                                    .setTitle("O total da área realizada é maior que a área programada")
                                    .setMessage("Diferença: " + diferenca + "\nDeseja salvar mesmo assim?")
                                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (erro == false && erroInsumos == false && erroGeral == false) {
                                                if (edicaoReg == false) chamaSalvar();
                                                else abreDialogoEdicaoReg();
                                            }
                                        }
                                    }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).create();
                            dialog.show();
                        }


                        if ((novaArea + osSelecionada.getAREA_REALIZADA()) <= osSelecionada.getAREA_PROGRAMADA()) {
                            if (erro == false && erroInsumos == false && erroGeral == false) {
                                if (edicaoReg == false) chamaSalvar();
                                else abreDialogoEdicaoReg();
                            }
                        }
                    }
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (algumItemPreenchido() == false) {
                    edicaoReg = false;
                    editouRegistro = false;
                    oSAtividadesDiaAtual = null;
                    editouInsumo1 = false;
                    editouInsumo2 = false;

                    listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();
                    area = "";
                    ho = "";
                    hm = "";
                    hh = "";
                    hoe = "";
                    hme = "";
                    obs = "";
                    obsInsumo1.setText("");
                    obsInsumo2.setText("");

                    insumoRec1 = false;
                    insumoRec2 = false;

                    oSAtividadesDiaAtual = null;
                    Intent it = new Intent(ActivityRegistros.this, ActivityListaRegistros.class);
                    if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                        it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
                    }
                    ;
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityRegistros.this, ActivityListaRegistros.class);
                        if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                            it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
                        }
                        ;
                        startActivity(it);
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Voltar para a listagem de registros?")
                                .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        edicaoReg = false;
                                        editouRegistro = false;
                                        oSAtividadesDiaAtual = null;
                                        editouInsumo1 = false;
                                        editouInsumo2 = false;

                                        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();
                                        area = "";
                                        ho = "";
                                        hm = "";
                                        hh = "";
                                        hoe = "";
                                        hme = "";
                                        obs = "";
                                        obsInsumo1.setText("");
                                        obsInsumo2.setText("");

                                        insumoRec1 = false;
                                        insumoRec2 = false;

                                        oSAtividadesDiaAtual = null;
                                        Intent it = new Intent(ActivityRegistros.this, ActivityListaRegistros.class);
                                        if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
                                            it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
                                        }
                                        ;
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
            }
        });

        botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoDatePicker.setEnabled(false);
            botaoDatePicker.setVisibility(GONE);

            botaoSalvar.setEnabled(false);
            botaoSalvar.setVisibility(GONE);
        }
    }

    //Chama a função salva().
    public void chamaSalvar() {
        Thread t = new Thread() {
            @Override
            public void run() {
                salva();
            }
        };
        boolean temForaFaixa = false;

        if (editouRegistro == true) {
            if (editouInsumo1 == true && insumoConforme1 == false) insumoConforme1 = false;
            else insumoConforme1 = true;

            if (editouInsumo2 == true && insumoConforme2 == false) insumoConforme2 = false;
            else insumoConforme2 = true;
        }

        if (insumoConforme1 == false || insumoConforme2 == false) {
            temForaFaixa = true;
        }

        if (temForaFaixa == true) {
            abreDialogoQtdForaFaixa();
        } else {
            t.run();
        }
    }


    //Abre diálogo para justificar a edição da quantidade do insumo
    //Método de entrada: um double, esse double é a quantidade aplicada na edição do insumo
    public void abreDialogoQtdForaFaixa() {
        abriuDialogoForaFaixa = true;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityRegistros.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_registros_insumo_fora_faixa, null);
        mBuilder.setView(mView);
        dialogoQtdForaFaixa = mBuilder.create();
        dialogoQtdForaFaixa.setContentView(mView);

        dialogoQtdForaFaixa.setCanceledOnTouchOutside(false);
        dialogoQtdForaFaixa.show();

        valorJustificativa1 = mView.findViewById(R.id.dialogo_frag_insumos_fora_faixa1);
        valorJustificativa2 = mView.findViewById(R.id.dialogo_frag_insumos_fora_faixa2);
        TextView titulo1 = mView.findViewById(R.id.dialogo_registros_insumos_titulo1);
        TextView titulo2 = mView.findViewById(R.id.dialogo_registros_insumos_titulo2);
        Button botaoOk = mView.findViewById(R.id.botao_ok_dialogo_insumo_fora_faixa);

        botaoOk.setClickable(true);
        botaoOk.setActivated(true);
        botaoOk.setEnabled(true);

        if (insumoConforme1 == false) {
            titulo1.setVisibility(View.VISIBLE);
            titulo1.setText("Digite a justificativa para o insumo " + listaJoinOsInsumosSelecionados.get(0).getDESCRICAO());
            valorJustificativa1.setVisibility(View.VISIBLE);
        }

        if (insumoConforme2 == false) {
            titulo2.setVisibility(View.VISIBLE);
            titulo2.setText("Digite a justificativa para o insumo " + listaJoinOsInsumosSelecionados.get(1).getDESCRICAO());
            valorJustificativa2.setVisibility(View.VISIBLE);
        }

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorJustificativa1") != null) {
                if (auxSavedInstanceState.getString("valorJustificativa1").length() > 0) {
                    valorJustificativa1.setText(auxSavedInstanceState.getString("valorJustificativa1"));
                }
            }

            if (auxSavedInstanceState.getString("valorJustificativa2") != null) {
                if (auxSavedInstanceState.getString("valorJustificativa2").length() > 0) {
                    valorJustificativa2.setText(auxSavedInstanceState.getString("valorJustificativa2"));
                }
            }
        }


        botaoOk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                boolean temErro = false;
                String obs1 = "";
                String obs2 = "";
                if (obsInsumo1.length() > 0) {
                    obs1 = obsInsumo1.getText().toString();
                }
                if (obsInsumo2.length() > 0) {
                    obs2 = obsInsumo2.getText().toString();
                }

                if (insumoConforme1 == false) {
                    String str1 = valorJustificativa1.getText().toString();
                    if (str1.trim().length() < 3) {
                        valorJustificativa1.setError("Justificativa deve ter mais de 2 caracteres.");
                        temErro = true;
                    } else {
                        String pegaObs = "";
                        if (obs1 != null) {
                            if (obs1.length() > 0) pegaObs = obs1 + "\n";
                        }
                        obs1 = pegaObs.concat("Aplicada quantidade fora da faixa em " + ferramentas.dataAtual() +
                                " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorJustificativa1.getText().toString()));
                    }
                }

                if (insumoConforme2 == false) {
                    String str2 = valorJustificativa2.getText().toString();
                    if (str2.trim().length() < 3) {
                        valorJustificativa2.setError("Justificativa deve ter mais de 2 caracteres.");
                        temErro = true;
                    } else {
                        String pegaObs = "";
                        if (obs2 != null) {
                            if (obs2.length() > 0) pegaObs = obs2 + "\n";
                        }
                        obs2 = pegaObs.concat("Aplicada quantidade fora da faixa em " + ferramentas.dataAtual() +
                                " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorJustificativa2.getText().toString()));
                    }
                }

                if (temErro == false) {
                    if (insumoConforme1 == false) {
                        listaJoinOsInsumosSelecionados.get(0).setOBSERVACAO(obs1);
                        insumosNaoConforme = true;
                    }

                    if (insumoConforme2 == false) {
                        listaJoinOsInsumosSelecionados.get(1).setOBSERVACAO(obs2);
                        insumosNaoConforme = true;
                    }

                    salva();
                    Log.e("Chamou a funcao salva()", "");
                    dialogoQtdForaFaixa.dismiss();
                    Log.e("Fechou o dialogo de insumos fora da faixa", "");
                }
            }
        });

        dialogoQtdForaFaixa.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoForaFaixa = false;
            }
        });

        dialogoQtdForaFaixa.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(ActivityRegistros.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                auxSavedInstanceState = null;
                abriuDialogoForaFaixa = false;
            }
        });
    }


    //Abre o diálogo para edição dos registros
    public void abreDialogoEdicaoReg() {
        abriuDialogoEdicaoReg = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityRegistros.this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_registros_editar_registro, null);
        valorEdicaoRegJustificativa = mView.findViewById(R.id.valor_dialogo_editar_registro);
        Button botaoOk = mView.findViewById(R.id.botao_ok_dialogo_editar_registro);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorJustificativa") != null) {
                if (auxSavedInstanceState.getString("valorJustificativa").length() > 0) {
                    valorEdicaoRegJustificativa.setText(auxSavedInstanceState.getString("valorJustificativa"));
                }
            }
        }

        mBuilder.setView(mView);
        dialogoEdicaoReg = mBuilder.create();
        dialogoEdicaoReg.setCanceledOnTouchOutside(false);

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = valorEdicaoRegJustificativa.getText().toString();

                if (str.trim().length() < 3)
                    valorEdicaoRegJustificativa.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    String pegaObs = "";
                    if (obs.length() > 0) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorEdicaoRegJustificativa.getText().toString()));
                    chamaSalvar();
                    dialogoEdicaoReg.dismiss();
                }
            }
        });

        dialogoEdicaoReg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoEdicaoReg = false;
            }
        });

        dialogoEdicaoReg.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(ActivityRegistros.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
            }
        });
        dialogoEdicaoReg.show();
    }

    public static class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        //Sobrescrita do método onCreateDialog, nele são definidos os parâmetros do calendário quando aberto
        @NonNull
        @Override
        public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {

            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);
            int m = mes;
            mes += 1;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, dia, mes, ano);

            datePickerDialog.getDatePicker().init(ano, m, dia, null); //System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }


        //Sobrescrita do método onDateSet, nele é setada a data selecionada no respectivo textview
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear += 1;

            String a = String.valueOf(year);
            String m = String.valueOf(monthOfYear);
            String d = String.valueOf(dayOfMonth);

            if (monthOfYear < 10) m = "0" + m;

            if (dayOfMonth < 10) d = "0" + d;

            String auxDia = String.valueOf(d);
            String auxMes = String.valueOf(m);
            String auxAno = String.valueOf(year);

            boolean temErro = false;
            String auxTeste = (auxDia + "-" + auxMes + "-" + auxAno).trim();

            Ferramentas ferramentas = new Ferramentas();
            if (!auxTeste.equals(dataDoApontamento)) {
                if (dao.selecionaOsAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), ferramentas.formataDataDb(auxTeste)) != null) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Erro!")
                            .setMessage("A data selecionada já tem um registro cadastrado.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                    temErro = true;
                }
            }

            //Pedaço de código responsável por checar se a data selecionada é posterior á programada
            /*try {
                Date date1 = sdf.parse(auxTeste);
                Date date2 = sdf.parse((ferramentas.formataDataTextView(osSelecionada.getDATA_PROGRAMADA())));

                if (date1.before(date2)) {
                    temErro = true;
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Erro!")
                            .setMessage("A data selecionada deve ser igual ou posterior a data programada da atividade.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            try {
                Date date1 = sdf.parse(auxTeste);
                Date date2 = sdf.parse(ferramentas.dataAtual());
                if (date1.after(date2)) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Erro!")
                            .setMessage("A data selecionada não pode ser após a data atual.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                    temErro = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (temErro == false) setarData(auxDia, auxMes, auxAno);
        }


        //Método auxiliar para setar a data no textview
        public void setarData(String dia, String mes, String ano) {
            dataDoApontamento = (dia + "-" + mes + "-" + ano).trim();
            dataApontamento.setText(dataDoApontamento);

            if (listaJoinOsInsumosSelecionados.size() < 0) {
                double recuperaQtdApl1 = listaJoinOsInsumosSelecionados.get(0).getQTD_APLICADO();
                double recuperaQtdApl2 = listaJoinOsInsumosSelecionados.get(1).getQTD_APLICADO();

                listaJoinOsInsumosSelecionados = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), ferramentas.formataDataDb(dataDoApontamento));

                listaJoinOsInsumosSelecionados.get(0).setQTD_APLICADO(recuperaQtdApl1);
                listaJoinOsInsumosSelecionados.get(1).setQTD_APLICADO(recuperaQtdApl2);
            }
        }
    }

    //testa as condições para conformidade
    public void testaConformidade() {
        erroGeral = false;

        testeAreaRealizada = 0;
        erro = false;
        erroInsumos = false;

        if (area != null && area.length() > 0) {
            try {
                if (area.contains(",")) area = area.replace(',', '.');
                testeAreaRealizada = Double.valueOf(area);
            } catch (NumberFormatException | NullPointerException nf) {
                testeAreaRealizada = NULL;
                erro = true;
                erroGeral = true;
            }
        }

        if (erroNaString(area) == true) {
            erro = true;
            erroGeral = true;
            areaRealizadaApontamento.setError("");
        }

        if (erroNaString(ho) == true) {
            erro = true;
            erroGeral = true;
            HOApontamento.setError("");
        }

        if (erroNaString(hm) == true) {
            erro = true;
            erroGeral = true;
            HMApontamento.setError("");
        }

        if (erroNaString(hh) == true) {
            erro = true;
            erroGeral = true;
            HHApontamento.setError("");
        }


        if (posicaoPrestador == -1) {
            erro = true;
            erroGeral = true;

            erroPrestador.setError("");
            erroPrestadorBool = true;
        } else {

            erroPrestador.setError(null);
            erroPrestadorBool = false;
        }

        if (erroNaString(hme) == true) {
            erro = true;
            erroGeral = true;
            hme = "";
            HMEscavadeiraApontamento.setError("");
        }

        if (erroNaString(hoe) == true) {
            erro = true;
            erroGeral = true;
            hoe = "";
            HOEscavadeiraApontamento.setError("");
        }

        /*if (posicaoResponsavel == -1) {
            erro = true;
            erroGeral = true;
        }*/

        if (listaJoinOsInsumosSelecionados.size() != 2) {
            erroInsumos = true;
            erroGeral = true;
        }


        try {
            if (listaJoinOsInsumosSelecionados.get(0).getRECOMENDACAO() == 1) {
                insumoRec1 = true;
            }
        } catch (Exception ex) {
            insumoRec1 = false;
            ex.printStackTrace();
        }

        try {
            if (listaJoinOsInsumosSelecionados.get(1).getRECOMENDACAO() == 1) {
                insumoRec2 = true;
            }
        } catch (Exception ex) {
            insumoRec2 = false;
            ex.printStackTrace();
        }


        if (listaJoinOsInsumosSelecionados.size() == 2) {

            if (insumoRec1 == true) {
                if (listaJoinOsInsumosSelecionados.get(0).getQTD_APLICADO() == 0) {
                    erroGeral = true;
                    erroInsumos = true;
                }
            }

            if (insumoRec2 == true) {
                if (listaJoinOsInsumosSelecionados.get(1).getQTD_APLICADO() == 0) {
                    erroGeral = true;
                    erroInsumos = true;
                }
            }
        }

        if (erro == true) {
            AlertDialog dialogoRegistros = new AlertDialog.Builder(ActivityRegistros.this)
                    .setTitle("Erro!")
                    .setMessage("Um ou mais itens na aba de registros contém erros.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialogoRegistros.show();
        }

        if (erroInsumos == true) {
            AlertDialog dialogoInsumos = new AlertDialog.Builder(ActivityRegistros.this)
                    .setTitle("Erro!")
                    .setMessage("A aplicação dos insumos não foi preenchida ou está parcialmente preenchida.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialogoInsumos.show();
        }

        //testar a área
        novaArea = 0;
        areaAnterior = 0;

        if (erro == false && oSAtividadesDiaAtual != null && testeAreaRealizada != 0) {
            if (oSAtividadesDiaAtual.getAREA_REALIZADA() != null) {
                String auxStr = oSAtividadesDiaAtual.getAREA_REALIZADA();
                if (auxStr.contains(",")) auxStr = auxStr.replace(',', '.');
                areaAnterior = Double.valueOf(auxStr);
            }
        }

        if (testeAreaRealizada != NULL || testeAreaRealizada != 0) {
            if (testeAreaRealizada > areaAnterior)
                novaArea = (testeAreaRealizada - areaAnterior);

            if (testeAreaRealizada < areaAnterior)
                novaArea = (testeAreaRealizada - areaAnterior);
        }


        if (erro == false && erroInsumos == false && editouRegistro == true) {
            edicaoReg = false;

            /*if (posicaoResponsavel != oSAtividadesDiaAtual.getID_RESPONSAVEL())
                edicaoReg = true;
*/

            if (posicaoPrestador != oSAtividadesDiaAtual.getID_PRESTADOR()) edicaoReg = true;

            if (!ho.equals(oSAtividadesDiaAtual.getHO())) edicaoReg = true;

            if (!hm.equals(oSAtividadesDiaAtual.getHM())) edicaoReg = true;

            if (!hh.equals(oSAtividadesDiaAtual.getHH())) edicaoReg = true;

            if (!area.equals(oSAtividadesDiaAtual.getAREA_REALIZADA())) edicaoReg = true;

            if (hoe.length() > 0 && !hoe.equals(oSAtividadesDiaAtual.getHO_ESCAVADEIRA()))
                edicaoReg = true;

            if (hme.length() > 0 && !hme.equals(oSAtividadesDiaAtual.getHM_ESCAVADEIRA()))
                edicaoReg = true;
        }
    }

    //Persiste os dados no DB
    @SuppressLint("LongLogTag")
    public void salva() {

        double somaQtdApl;
        double somaAreaRealizada;
        String dataAntesDoEdit = null;


        if (editouRegistro == true) {
            idAtividadeDia = oSAtividadesDiaAtual.getID();
            acaoInativo = "EDICAO";
            regDescarregado = oSAtividadesDiaAtual.getREGISTRO_DESCARREGADO();
            dataAntesDoEdit = oSAtividadesDiaAtual.getDATA();
            dao.delete(oSAtividadesDiaAtual);
        }

        oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();

        oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        oSAtividadesDiaAtual.setDATA(ferramentas.formataDataDb(dataDoApontamento));

        oSAtividadesDiaAtual.setACAO_INATIVO(acaoInativo);
        oSAtividadesDiaAtual.setID(idAtividadeDia);

        if (ho.contains(",")) ho = ho.replace(',', '.');
        if (hm.contains(",")) hm = hm.replace(',', '.');
        if (hh.contains(",")) hh = hh.replace(',', '.');

        if (hme != null) {
            if (hme.contains(",")) hme = hme.replace(',', '.');
        } else hme = "0";

        if (hoe != null) {
            if (hoe.contains(",")) hoe = hoe.replace(',', '.');
        } else hoe = "0";

        oSAtividadesDiaAtual.setAREA_REALIZADA(area.replace(',', '.'));
        oSAtividadesDiaAtual.setHH(hh.replace(',', '.'));
        oSAtividadesDiaAtual.setHM(hm.replace(',', '.'));
        oSAtividadesDiaAtual.setHO(ho.replace(',', '.'));
        oSAtividadesDiaAtual.setHM_ESCAVADEIRA(hme.replace(',', '.'));
        oSAtividadesDiaAtual.setHO_ESCAVADEIRA(hoe.replace(',', '.'));

        oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
        //oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
        oSAtividadesDiaAtual.setID_RESPONSAVEL(usuarioLogado.getID_USUARIO());

        if (obs != null) {
            if (obs.length() > 0) oSAtividadesDiaAtual.setOBSERVACAO(obs);
        }

        oSAtividadesDiaAtual.setREGISTRO_DESCARREGADO(regDescarregado);

        oSAtividadesDiaAtual.setEXPORT_PROXIMA_SINC(true);
        dao.insert(oSAtividadesDiaAtual);


        if (editouRegistro == false && insumosNaoConforme == false) {
            listaJoinOsInsumosSelecionados.get(0).setOBSERVACAO(obsInsumo1.getText().toString());
            listaJoinOsInsumosSelecionados.get(1).setOBSERVACAO(obsInsumo2.getText().toString());
        }

        String dataDepoisDoEdit = oSAtividadesDiaAtual.getDATA();
        if (editouRegistro == false) {
            dataAntesDoEdit = dataDepoisDoEdit;
        }

        try {
            for (int i = 0; i < listaJoinOsInsumosSelecionados.size(); i++) {
                Join_OS_INSUMOS persisteInsumosDia = listaJoinOsInsumosSelecionados.get(i);

                O_S_ATIVIDADE_INSUMOS editaAtividadeInsumos =
                        dao.selecionaAtividadeInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), persisteInsumosDia.getID_INSUMO());

                if (persisteInsumosDia.getREGISTRO_DESCARREGADO() == null ||
                        persisteInsumosDia.getREGISTRO_DESCARREGADO() == "null") {
                    persisteInsumosDia.setREGISTRO_DESCARREGADO("N");
                }

                if (editouRegistro == false) {
                    persisteInsumosDia.setACAO_INATIVO(null);
                    persisteInsumosDia.setID(null);
                }
                if (editouRegistro == true) {
                    persisteInsumosDia.setACAO_INATIVO("EDICAO");
                }

                dao.apagaOsAtividadeInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                        dataAntesDoEdit, persisteInsumosDia.getID_INSUMO());

                O_S_ATIVIDADE_INSUMOS_DIA insereInsumosDia = new O_S_ATIVIDADE_INSUMOS_DIA(persisteInsumosDia.getID(), osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                        dataDepoisDoEdit,
                        persisteInsumosDia.getID_INSUMO(), persisteInsumosDia.getQTD_APLICADO(), persisteInsumosDia.getACAO_INATIVO(),
                        persisteInsumosDia.getREGISTRO_DESCARREGADO(), persisteInsumosDia.getOBSERVACAO());

                insereInsumosDia.setEXPORT_PROXIMA_SINC(true);
                Log.e("Lista batendo ", String.valueOf(i) + " itens");


                if (i == 0) {
                    if (insumoConforme1 == true) {
                        if (editouInsumo1 == false) {
                            if (obsInsumo1.getText().toString().length() == 0) {
                                insereInsumosDia.setOBSERVACAO(null);
                            }

                            if (obsInsumo1.getText().toString().length() > 0) {
                                insereInsumosDia.setOBSERVACAO(obsInsumo1.getText().toString());
                            }
                        }
                    }
                }

                if (i == 1) {
                    if (insumoConforme2 == true) {
                        if (editouInsumo2 == false) {
                            if (obsInsumo2.getText().toString().length() == 0) {
                                insereInsumosDia.setOBSERVACAO(null);
                            }

                            if (obsInsumo2.getText().toString().length() > 0) {
                                insereInsumosDia.setOBSERVACAO(obsInsumo2.getText().toString());
                            }
                        }
                    }
                }


                dao.insert(insereInsumosDia);

                DecimalFormat format = new DecimalFormat(".##");
                BigDecimal qtdHaAplicado;
                somaAreaRealizada = dao.somaAreaRealizada(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

                somaQtdApl = dao.qtdAplicadaTodosInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), persisteInsumosDia.getID_INSUMO());
                String s = format.format(somaQtdApl).replace(',', '.');

                // Log.e("Valor somaAreaRealizada", String.valueOf(somaAreaRealizada));

                try {
                    somaQtdApl = Double.valueOf(s);
                    // Log.e("Valor somaQtdApl", s);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Log.e("Erro ao obter qtd aplicada", e.getMessage());
                    somaQtdApl = 1;
                }


                //Log.e("soma area | soma qtdApl", String.valueOf(somaAreaRealizada)+" | "+String.valueOf(somaQtdApl));

                String divisao = String.valueOf((long) somaQtdApl / (long) somaAreaRealizada);
                //Log.e("Valor divisao", divisao);

                qtdHaAplicado = new BigDecimal(divisao).setScale(2, BigDecimal.ROUND_UP);

                s = new DecimalFormat(".##").format(qtdHaAplicado).replace(',', '.');

                double converteQtdHaApl;
                try {
                    converteQtdHaApl = Double.valueOf(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    converteQtdHaApl = 1;
                }


                editaAtividadeInsumos.setQTD_HA_APLICADO(converteQtdHaApl);

                dao.update(editaAtividadeInsumos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        somaAreaRealizada = dao.somaAreaRealizada(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        osSelecionada.setAREA_REALIZADA(somaAreaRealizada);

        if (osSelecionada.getSTATUS_NUM() == 0) {
            osSelecionada.setSTATUS("Andamento");
            osSelecionada.setSTATUS_NUM(1);
            dao.update(osSelecionada);
        }

        listaAtividades = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        osSelecionada.setDATA_INICIAL(listaAtividades.get(listaAtividades.size() - 1).getDATA());
        osSelecionada.setDATA_FINAL(listaAtividades.get(0).getDATA());
        dao.update(osSelecionada);

        Log.e("Apontamento mais antigo", ferramentas.formataDataTextView(osSelecionada.getDATA_INICIAL()));
        Log.e("Apontamento mais recente", ferramentas.formataDataTextView(osSelecionada.getDATA_FINAL()));
        edicaoReg = false;
        editouRegistro = false;
        oSAtividadesDiaAtual = null;
        editouInsumo1 = false;
        editouInsumo2 = false;

        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();
        area = "";
        ho = "";
        hm = "";
        hh = "";
        hoe = "";
        hme = "";
        obs = "";
        obsInsumo1.setText("");
        obsInsumo2.setText("");
        insumoRec1 = false;
        insumoRec2 = false;

        Intent it = new Intent(ActivityRegistros.this, ActivityListaRegistros.class);
        if (dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()).isEmpty()) {
            it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
        }
        ;
        startActivity(it);
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

    //Retorna false se não houver nenhum item preenchido ou true se houver
    public boolean algumItemPreenchido() {
        if (osSelecionada.getSTATUS_NUM() == 2) return false;

        //if (posicaoResponsavel != -1) return true;
        if (posicaoPrestador != -1) return true;


        if (areaRealizadaApontamento != null) {
            if (areaRealizadaApontamento.length() != 0) return true;
        }
        if (HOApontamento != null) {
            if (HOApontamento.length() != 0) return true;
        }
        if (HMApontamento != null) {
            if (HMApontamento.length() != 0) return true;
        }
        if (HHApontamento != null) {
            if (HHApontamento.length() != 0) return true;
        }
        if (HOEscavadeiraApontamento != null) {
            if (HOEscavadeiraApontamento.length() != 0) return true;
        }
        if (HMEscavadeiraApontamento != null) {
            if (HMEscavadeiraApontamento.length() != 0) return true;
        }
        if (obsApontamento != null) {
            if (obsApontamento.length() != 0) return true;
        }

        if (listaJoinOsInsumosSelecionados.size() > 0) {
            if (listaJoinOsInsumosSelecionados.get(0).getQTD_APLICADO() != 0) return true;
            if (listaJoinOsInsumosSelecionados.get(1).getQTD_APLICADO() != 0) return true;
        }

        return false;
    }


    //Sobreescrita do método de seleção de item do menu de navegação localizado na lateral da tela
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                if (algumItemPreenchido() == false) {
                    area = "";
                    ho = "";
                    hm = "";
                    hh = "";
                    hoe = "";
                    hme = "";
                    obs = "";

                    insumoRec1 = false;
                    insumoRec2 = false;

                    oSAtividadesDiaAtual = null;

                    Intent it = new Intent(ActivityRegistros.this, ActivityDashboard.class);
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityRegistros.this, ActivityDashboard.class);
                        startActivity(it);
                    } else {
                        AlertDialog dialogoDash = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Abrir a Dashboard?")
                                .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent it = new Intent(ActivityRegistros.this, ActivityDashboard.class);
                                        startActivity(it);
                                    }
                                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialogoDash.show();
                    }
                }
                break;

            case R.id.atividades:
                if (algumItemPreenchido() == false) {
                    area = "";
                    ho = "";
                    hm = "";
                    hh = "";
                    hoe = "";
                    hme = "";
                    obs = "";

                    insumoRec1 = false;
                    insumoRec2 = false;

                    oSAtividadesDiaAtual = null;

                    Intent it = new Intent(ActivityRegistros.this, ActivityMain.class);
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityRegistros.this, ActivityMain.class);
                        startActivity(it);
                    } else {
                        AlertDialog dialogoAtividades = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Voltar Para a Listagem de Atividades?")
                                .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent it = new Intent(ActivityRegistros.this, ActivityMain.class);
                                        startActivity(it);
                                    }
                                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialogoAtividades.show();
                    }
                }
                break;

            case R.id.calculadora:
                Intent it3 = new Intent(this, CalculadoraMain.class);
                startActivity(it3);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Verifica se uma String pode ser convertida para double, se possível retorna true, senão retorna false.
    //Parâmetro de entrada: String
    public boolean erroNaString(String str) {
        if (str == null) return true;
        if (str.length() == 0) return true;
        char[] c = str.toCharArray();
        if (str.length() > 0) {
            if (str == "," || c[str.length() - 1] == ',' || c[0] == ',' || contaVirgula(str, ',') > 1) {
                return true;
            }
        }
        return false;
    }

    //Conta quantas indicências de um caractere há em uma String.
    //Parâmetros de entrada: uma String, um Char
    //Retorna quantidade de indicências do caracter
    public int contaVirgula(String s, char c) {
        return s.length() == 0 ? 0 : (s.charAt(0) == c ? 1 : 0) + contaVirgula(s.substring(1), c);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        fragmentoInsumos.onBackPressed();
        if (abriuDialogoEdicaoReg) dialogoEdicaoReg.cancel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", dataDoApontamento);
        outState.putString("acaoInativo", acaoInativo);
        if (idAtividadeDia == null) {
            idAtividadeDia = 0;
        }
        outState.putInt("idAtividadeDia", idAtividadeDia);
        outState.putString("regDescarregado", regDescarregado);

        outState.putBoolean("abriuDialogoEdicaoReg", abriuDialogoEdicaoReg);
        outState.putBoolean("abriuDialogoForaFaixa", abriuDialogoForaFaixa);

        outState.putBoolean("editouRegistro", editouRegistro);

        if (abriuDialogoEdicaoReg == true) {
            if (valorEdicaoRegJustificativa != null) {
                if (valorEdicaoRegJustificativa.getText().toString().length() > 0) {
                    outState.putString("valorEdicaoRegJustificativa", valorEdicaoRegJustificativa.getText().toString());
                }
            }

        }

        if (abriuDialogoForaFaixa == true) {
            if (valorJustificativa1 != null) {
                if (valorJustificativa1.getText().toString().length() > 0) {
                    outState.putString("valorJustificativa1", valorJustificativa1.getText().toString());
                }
            }

            if (valorJustificativa2 != null) {
                if (valorJustificativa2.getText().toString().length() > 0) {
                    outState.putString("valorJustificativa2", valorJustificativa2.getText().toString());
                }
            }
        }

        outState.putBoolean("insumosNaoConforme", insumosNaoConforme);

        if (abriuDialogoEdicaoReg == true) {
            if (dialogoEdicaoReg != null && dialogoEdicaoReg.isShowing())
                dialogoEdicaoReg.dismiss();
        }
        if (abriuDialogoForaFaixa == true) {
            if (dialogoQtdForaFaixa != null && dialogoQtdForaFaixa.isShowing())
                dialogoQtdForaFaixa.dismiss();
        }
    }
}
