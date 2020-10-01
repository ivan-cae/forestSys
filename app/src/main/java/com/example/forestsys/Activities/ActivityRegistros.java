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

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.example.forestsys.ViewModels.ViewModelO_S_ATIVIDADES_DIA;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.FragmentoInsumos.obsInsumo1;
import static com.example.forestsys.Activities.FragmentoInsumos.obsInsumo2;
import static com.example.forestsys.Activities.FragmentoRendimento.HHApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HMApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.HOApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.areaRealizadaApontamento;
import static com.example.forestsys.Activities.FragmentoRendimento.posicaoPrestador;
import static com.example.forestsys.Activities.FragmentoRendimento.posicaoResponsavel;

import static com.example.forestsys.Activities.ActivityAtividades.hh;
import static com.example.forestsys.Activities.ActivityAtividades.hm;
import static com.example.forestsys.Activities.ActivityAtividades.hme;
import static com.example.forestsys.Activities.ActivityAtividades.ho;
import static com.example.forestsys.Activities.ActivityAtividades.hoe;
import static com.example.forestsys.Activities.ActivityAtividades.obs;
import static com.example.forestsys.Activities.ActivityAtividades.area;
import static java.sql.Types.NULL;

public class ActivityRegistros extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView talhaoOs;
    private TextView statusOs;
    private TextView areaOs;
    private TextView areaRealizada;
    public static TextView dataApontamento;
    private ImageButton voltar;
    private Button botaoSalvar;
    private ImageButton botaoDatePicker;
    public static ViewModelO_S_ATIVIDADES_DIA viewModelOSAtividadesDia;
    private Ferramentas ferramentas;
    public static String dataDoApontamento;
    private String acaoInativoAtividade = null;

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
    public static String[] pegaDescInsumos;
    private List<O_S_ATIVIDADES_DIA> listaAtividades;
    private List<O_S_ATIVIDADE_INSUMOS> insumos_dia = new ArrayList<O_S_ATIVIDADE_INSUMOS>();

    private FragmentoInsumos fragmentoInsumos;
    private FragmentoRendimento fragmentoRendimento;

    private AlertDialog dialogoEdicaoRec;
    private boolean abriuDialogoEdicao;
    public static Bundle auxSavedInstanceState;
    private EditText valorJustificativa;

    private double testeAreaRealizada;
    private boolean erro;
    private boolean erroInsumos;
    double auxAreaRealizadaOs;
    double auxAreaRealizadaDia;
    private String outraAuxkk;
    private double novaArea;
    private double areaAnterior;
    private boolean erroGeral = false;
    private boolean edicaoReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auxSavedInstanceState = savedInstanceState;
        inicializacao();

        if (savedInstanceState != null) {
            dataDoApontamento = savedInstanceState.getString("data");
            dataApontamento.setText(dataDoApontamento);
            acaoInativoAtividade = savedInstanceState.getString("acaoInativoAtividade");
            abriuDialogoEdicao = savedInstanceState.getBoolean("abriuDialogoEdicao");
            editouRegistro = savedInstanceState.getBoolean("editouRegistro");
            if (abriuDialogoEdicao == true) abreDialogoEdicaoReg();
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

                double ins1 = dao.qtdAplicadaTodosInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumos_dia.get(0).getID_INSUMO());
                double ins2 = dao.qtdAplicadaTodosInsumos(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumos_dia.get(1).getID_INSUMO());

                totalHh.setText(String.valueOf(hhAux).replace(".", ","));
                totalHo.setText(String.valueOf(hoAux).replace(".", ","));
                totalHm.setText(String.valueOf(hmAux).replace(".", ","));
                totalHoe.setText(String.valueOf(hoeAux).replace(".", ","));
                totalHme.setText(String.valueOf(hmeAux).replace(".", ","));
                totalArea.setText(String.valueOf(areaAux).replace(".", ","));
                totalInsumo1.setText(String.valueOf(ins1).replace(".", ","));
                totalInsumo2.setText(String.valueOf(ins2).replace(".", ","));

                if (!listaAtividades.isEmpty()) {
                    /*if (diferencaPercentual(insumos_dia.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1) > 5 ||
                            diferencaPercentual(insumos_dia.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1) < -5)
                        difInsumo1.setBackgroundColor(Color.parseColor("#FF0000"));
                    else difInsumo1.setBackgroundColor(Color.parseColor("#BDB8B8"));

                    if (diferencaPercentual(insumos_dia.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2) > 5 ||
                            diferencaPercentual(insumos_dia.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2) < -5)
                        difInsumo2.setBackgroundColor(Color.parseColor("#FF0000"));
                    else difInsumo2.setBackgroundColor(Color.parseColor("#BDB8B8"));
                    */
                    difInsumo1.setText(String.valueOf(diferencaPercentual(insumos_dia.get(0).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins1)));
                    difInsumo2.setText(String.valueOf(diferencaPercentual(insumos_dia.get(1).getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA(), ins2)));
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
        return Double.valueOf(df.format(calculo).replace(',', '.'));
    }

    //Verifica se é possível converter o valor de uma string para double, retorna o valor convertido se possível converter.
    //Se não for possível, retorna 0.
    //Parâmetro de entrada: uma String
    public double checaConversao(String numero) {
        double teste;
        try {
            teste = Double.parseDouble(numero);
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

        abriuDialogoEdicao = false;
        edicaoReg = false;

        if (osSelecionada.getSTATUS_NUM() != 2)
            if (listaAtividades != null)
                if (!listaAtividades.isEmpty())
                    Toast.makeText(this, "Toque o registro para edita-lo", Toast.LENGTH_LONG).show();

        Toolbar toolbar = findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());


        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        listaAtividades = dao.listaAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        insumos_dia = dao.listaInsumosatividade(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        insumo1 = findViewById(R.id.registros_insumo1);
        insumo2 = findViewById(R.id.registros_insumo2);
        dataApontamento = findViewById(R.id.data_apontamento);
        talhaoOs = findViewById(R.id.talhao_os_continuar);
        statusOs = findViewById(R.id.status_os_continuar);
        areaOs = findViewById(R.id.area_prog_os_continuar);
        botaoDatePicker = findViewById(R.id.botao_date_picker);
        areaRealizada = findViewById(R.id.area_realizada_os_continuar);

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

        insumo1.setText(joinOsInsumos.get(0).getDESCRICAO());
        insumo2.setText(joinOsInsumos.get(1).getDESCRICAO());
        pegaDescInsumos = new String[2];

        pegaDescInsumos[0] = insumo1.getText().toString();
        pegaDescInsumos[1] = insumo2.getText().toString();


        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));

        statusOs.setText(String.valueOf(osSelecionada.getSTATUS()));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()).replace(".", ","));

        voltar = findViewById(R.id.botao_continuar_voltar);
        botaoSalvar = findViewById(R.id.botao_prosseguir_continuar);

        recyclerView = findViewById(R.id.registros_lista);

        adaptador = new AdaptadorApontamentos();
        adaptador.setApontamentos(listaAtividades);
        recyclerView.setAdapter(adaptador);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //if (editouRegistro) botaoDatePicker.setEnabled(false);
        viewModelOSAtividadesDia = new ViewModelO_S_ATIVIDADES_DIA(getApplication());

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

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        String diferenca = format.format(Double.parseDouble(String.valueOf(d)));
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
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (algumItemPreenchido() == false) {
                    area = "";
                    ho = "";
                    hm = "";
                    hh = "";
                    hoe = "";
                    hme = "";
                    obs = "";

                    oSAtividadesDiaAtual = null;
                    Intent it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
                        startActivity(it);
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                                .setTitle("Voltar Para a tela da atividade?")
                                .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        area = "";
                                        ho = "";
                                        hm = "";
                                        hh = "";
                                        hoe = "";
                                        hme = "";
                                        obs = "";

                                        oSAtividadesDiaAtual = null;
                                        Intent it = new Intent(ActivityRegistros.this, ActivityAtividades.class);
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

        adaptador.setOnItemClickListener(new AdaptadorApontamentos.OnItemClickListener() {
            @Override
            public void onItemClick(O_S_ATIVIDADES_DIA oSAtividadesDia) {
                if (osSelecionada.getSTATUS_NUM() != 2) {
                    new AlertDialog.Builder(ActivityRegistros.this)
                            .setTitle("Editar")
                            .setMessage("Deseja Alterar o Registro?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editouRegistro = true;
                                    oSAtividadesDiaAtual = dao.selecionaOsAtividadesDia(oSAtividadesDia.getID_PROGRAMACAO_ATIVIDADE(), oSAtividadesDia.getDATA());
                                    Intent it = new Intent(ActivityRegistros.this, ActivityRegistros.class);
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
        t.run();
    }

    //Abre o diálogo para edição dos registros
    public void abreDialogoEdicaoReg() {
        abriuDialogoEdicao = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_editar_registro, null);
        valorJustificativa = mView.findViewById(R.id.valor_dialogo_editar_registro);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_editar_registro);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorJustificativa") != null) {
                if (!auxSavedInstanceState.getString("valorJustificativa").isEmpty()) {
                    valorJustificativa.setText(auxSavedInstanceState.getString("valorJustificativa"));
                }
            }
        }

        mBuilder.setView(mView);
        dialogoEdicaoRec = mBuilder.create();
        dialogoEdicaoRec.setCanceledOnTouchOutside(false);
        dialogoEdicaoRec.show();

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valorJustificativa.getText().toString();

                if (str.trim().length() < 3)
                    valorJustificativa.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    String pegaObs = "";
                    if (!obs.isEmpty()) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorJustificativa.getText().toString()));
                    chamaSalvar();
                    dialogoEdicaoRec.dismiss();
                }
            }
        });

        dialogoEdicaoRec.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoEdicao = false;
            }
        });

        dialogoEdicaoRec.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(ActivityRegistros.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
            }
        });
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
            String pattern = ("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

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

            try {
                Date date1 = sdf.parse(auxTeste);
                Date date2 = sdf.parse((ferramentas.formataDataTextView(osSelecionada.getDATA_PROGRAMADA())));

                if (date1.before(date2)) {
                    temErro = true;
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Erro!")
                            .setMessage("A data selecionada não pode ser antes da data programada da atividade.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

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
        }
    }

    //testa as condições para conformidade
    public void testaConformidade() {
        erroGeral = false;

        testeAreaRealizada = 0;
        erro = false;
        erroInsumos = false;

        if (area != null && !area.isEmpty()) {
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
        }
        if (posicaoResponsavel == -1) {
            erro = true;
            erroGeral = true;
        }

        if (erroNaString(hme) == true) {
            hme = "";
        }

        if (erroNaString(hoe) == true) {
            hoe = "";
        }

        if (listaJoinOsInsumosSelecionados.size() != 2) {
            erroInsumos = true;
            erroGeral = true;
        }

        if (listaJoinOsInsumosSelecionados.size() == 2) {
            if (listaJoinOsInsumosSelecionados.get(0).getQTD_APLICADO() == 0) {
                erroGeral = true;
                erroInsumos = true;
            }

            if (listaJoinOsInsumosSelecionados.get(1).getQTD_APLICADO() == 0) {
                erroGeral = true;
                erroInsumos = true;
            }
        }

        if (erro == true) {
            AlertDialog dialogoRegistros = new AlertDialog.Builder(ActivityRegistros.this)
                    .setTitle("Erro!")
                    .setMessage("Um ou mais itens na aba de registros contém erros." + "\n" +
                            "Somente os campos Hora Operador Escavadeira e Hora Máquina Escavadeira " +
                            "não são obrigatórios.")
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

            if (posicaoResponsavel != oSAtividadesDiaAtual.getID_RESPONSAVEL())
                edicaoReg = true;

            if (posicaoPrestador != oSAtividadesDiaAtual.getID_PRESTADOR()) edicaoReg = true;

            if (!ho.equals(oSAtividadesDiaAtual.getHO())) edicaoReg = true;

            if (!hm.equals(oSAtividadesDiaAtual.getHM())) edicaoReg = true;

            if (!hh.equals(oSAtividadesDiaAtual.getHH())) edicaoReg = true;

            if (!area.equals(oSAtividadesDiaAtual.getAREA_REALIZADA())) edicaoReg = true;

            if (!hoe.isEmpty() && !hoe.equals(oSAtividadesDiaAtual.getHO_ESCAVADEIRA()))
                edicaoReg = true;

            if (!hme.isEmpty() && !hme.equals(oSAtividadesDiaAtual.getHM_ESCAVADEIRA()))
                edicaoReg = true;

            if (edicaoReg == true) {
                acaoInativoAtividade = "EDICAO";
            }
        }
    }

    //Persiste os dados nos EditTexts no DB ?
    public void salva() {

        if (oSAtividadesDiaAtual != null && oSAtividadesDiaAtual.getDATA() != dataDoApontamento) {
            O_S_ATIVIDADES_DIA aux = oSAtividadesDiaAtual;

            dao.apagaOsAtividadeInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), aux.getDATA());

            viewModelOSAtividadesDia.delete(aux);
        }

        auxAreaRealizadaOs = osSelecionada.getAREA_REALIZADA();

        if (oSAtividadesDiaAtual != null && oSAtividadesDiaAtual.getAREA_REALIZADA() != null && testeAreaRealizada == 0 || testeAreaRealizada == NULL) {
            if (oSAtividadesDiaAtual != null) {
                outraAuxkk = oSAtividadesDiaAtual.getAREA_REALIZADA();
                if (outraAuxkk != null && outraAuxkk.contains(","))
                    outraAuxkk = outraAuxkk.replace(',', '.');
                auxAreaRealizadaDia = Double.valueOf(outraAuxkk);
                osSelecionada.setAREA_REALIZADA(auxAreaRealizadaOs - auxAreaRealizadaDia);
            }
        } else osSelecionada.setAREA_REALIZADA(auxAreaRealizadaOs + novaArea);

        oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();

        if (hoe.contains(",")) hoe = hoe.replace(',', '.');
        if (ho.contains(",")) ho = ho.replace(',', '.');
        if (hm.contains(",")) hm = hm.replace(',', '.');
        if (hh.contains(",")) hh = hh.replace(',', '.');

        if (hme != null) {
            if (hme.contains(",")) hme = hme.replace(',', '.');
        }

        if (hoe != null) {
            if (hoe.contains(",")) hoe = hoe.replace(',', '.');
        }

        oSAtividadesDiaAtual.setAREA_REALIZADA(area);
        oSAtividadesDiaAtual.setHH(hh);
        oSAtividadesDiaAtual.setHM(hm);
        oSAtividadesDiaAtual.setHO(ho);
        oSAtividadesDiaAtual.setHM_ESCAVADEIRA(hme);
        oSAtividadesDiaAtual.setHO_ESCAVADEIRA(hoe);

        oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
        oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
        oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        oSAtividadesDiaAtual.setDATA(ferramentas.formataDataDb(dataDoApontamento));
        if (acaoInativoAtividade != null)
            oSAtividadesDiaAtual.setACAO_INATIVO(acaoInativoAtividade);

        if (obs != null) {
            if (!obs.isEmpty()) oSAtividadesDiaAtual.setOBSERVACAO(obs);
        }

        if (editouRegistro == false) {
            listaJoinOsInsumosSelecionados.get(0).setOBSERVACAO(obsInsumo1.getText().toString());
            listaJoinOsInsumosSelecionados.get(1).setOBSERVACAO(obsInsumo2.getText().toString());
        }

        for (int i = 0; i < listaJoinOsInsumosSelecionados.size(); i++) {
            Join_OS_INSUMOS persiste = listaJoinOsInsumosSelecionados.get(i);
            dao.insert(new O_S_ATIVIDADE_INSUMOS_DIA(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), ferramentas.formataDataDb(dataDoApontamento),
                    persiste.getID_INSUMO(), persiste.getQTD_APLICADO(), null, '0', persiste.getOBSERVACAO()));
        }

        dao.update(osSelecionada);
        viewModelOSAtividadesDia.insert(oSAtividadesDiaAtual);

        if (osSelecionada.getSTATUS_NUM() == 0) {
            osSelecionada.setSTATUS("Andamento");
            osSelecionada.setSTATUS_NUM(1);
            osSelecionada.setDATA_INICIAL(ferramentas.formataDataDb(ferramentas.dataAtual()));
            dao.update(osSelecionada);
        }

        edicaoReg = false;
        editouRegistro = false;
        oSAtividadesDiaAtual = null;
        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();
        area = "";
        ho = "";
        hm = "";
        hh = "";
        hoe = "";
        hme = "";
        obs = "";

        Intent it = new Intent(ActivityRegistros.this, ActivityRegistros.class);
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
        if(osSelecionada.getSTATUS_NUM() == 2) return false;

        if (posicaoResponsavel != -1) return true;
        if (posicaoPrestador != -1) return true;

        if (area != null) {
            if (area.length()!=0) return true;
        }
        if (ho != null) {
            if (ho.length()!=0) return true;
        }
        if (hm != null) {
            if (hm.length()!=0) return true;
        }
        if (hh != null) {
            if (hh.length()!=0) return true;
        }
        if (hoe != null) {
            if (hoe.length()!=0) return true;
        }
        if (hme != null) {
            if (hme.length()!=0) return true;
        }
        if (obs != null) {
            if (obs.length()!=0) return true;
        }

        if(listaJoinOsInsumosSelecionados.size()>0) {
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
        if (str.isEmpty()) return true;
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
        if (abriuDialogoEdicao) dialogoEdicaoRec.cancel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", dataDoApontamento);
        outState.putString("acaoInativoAtividade", acaoInativoAtividade);

        outState.putBoolean("abriuDialogoEdicao", abriuDialogoEdicao);
        outState.putBoolean("editouRegistro", editouRegistro);


        if (abriuDialogoEdicao == true) {
            if (valorJustificativa != null) {
                if (!valorJustificativa.getText().toString().isEmpty()) {
                    outState.putString("valorJustificativa", valorJustificativa.getText().toString());
                }
            }
        }


        if (abriuDialogoEdicao == true) dialogoEdicaoRec.dismiss();
    }
}
