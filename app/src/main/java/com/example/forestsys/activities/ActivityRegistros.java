package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS_DIA;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES_DIA;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.FragmentoRendimento.auxiliar;
import static com.example.forestsys.activities.FragmentoRendimento.hh;
import static com.example.forestsys.activities.FragmentoRendimento.hm;
import static com.example.forestsys.activities.FragmentoRendimento.hme;
import static com.example.forestsys.activities.FragmentoRendimento.ho;
import static com.example.forestsys.activities.FragmentoRendimento.hoe;
import static com.example.forestsys.activities.FragmentoRendimento.obs;
import static com.example.forestsys.activities.FragmentoRendimento.area;
import static com.example.forestsys.activities.FragmentoRendimento.posicaoPrestador;
import static com.example.forestsys.activities.FragmentoRendimento.posicaoResponsavel;
import static java.sql.Types.NULL;

public class ActivityRegistros extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
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

    public static TextView dataApontamento;
    private ImageButton voltar;
    private Button botaoSalvar;
    private Button botaoInsumos;
    private Button botaoApontamento;
    private ImageButton botaoDatePicker;
    public static ViewModelO_S_ATIVIDADES_DIA viewModelOSAtividadesDia;
    private DataHoraAtual dataHoraAtual;
    public static O_S_ATIVIDADES_DIA oSAtividadesDiaAtual;
    public static String dataDoApontamento;
    public static List<Join_OS_INSUMOS> listaJoinOsInsumosSelecionados;
    public static boolean primeiraReg;
    public static boolean primeiraIns;

    BaseDeDados baseDeDados;
    DAO dao;
    private int abaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        setTitle(nomeEmpresaPref);

        dataHoraAtual = new DataHoraAtual();
        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();

        Toolbar toolbar = findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());


       baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        idOs = findViewById(R.id.id_os_continuar);
        dataApontamento = findViewById(R.id.data_apontamento);
        talhaoOs = findViewById(R.id.talhao_os_continuar);
        cicloOs = findViewById(R.id.ciclo_os_continuar);
        setorOs = findViewById(R.id.setor_os_continuar);
        obsOs = findViewById(R.id.obs_os_continuar);
        statusOs = findViewById(R.id.status_os_continuar);
        areaOs = findViewById(R.id.area_prog_os_continuar);
        manejoOs = findViewById(R.id.manejo_os_continuar);
        madeiraOs = findViewById(R.id.madeira_os_continuar);
        botaoDatePicker = findViewById(R.id.botao_date_picker);
        descricao = findViewById(R.id.descricao_os_continuar);
        dataProgramada  = findViewById(R.id.data_prog_os_continuar);
        areaRealizada = findViewById(R.id.area_realizada_os_continuar);

        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));
        cicloOs.setText(String.valueOf(osSelecionada.getCICLO()));
        setorOs.setText(String.valueOf(osSelecionada.getID_SETOR()));
        obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        statusOs.setText(String.valueOf("Andamento"));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace(".", ","));
        manejoOs.setText(String.valueOf(osSelecionada.getID_MANEJO()));
        dataProgramada.setText(osSelecionada.getDATA_PROGRAMADA());
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()));

        String temMadeira = "NÃO";
        if(osSelecionada.getMADEIRA_NO_TALHAO()==1) temMadeira = "SIM";
        madeiraOs.setText(String.valueOf(temMadeira));

        descricao.setText(dao.selecionaAtividade(osSelecionada.getID_ATIVIDADE()).getDESCRICAO());


        botaoApontamento = findViewById(R.id.botao_apontamentos_geral);
        botaoInsumos = findViewById(R.id.botao_apontamentos_insumos);
        voltar = findViewById(R.id.botao_continuar_voltar);
        botaoSalvar = findViewById(R.id.botao_prosseguir_continuar);


        viewModelOSAtividadesDia = new ViewModelO_S_ATIVIDADES_DIA(getApplication());

        if (oSAtividadesDiaAtual == null) {
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


            dataDoApontamento = dataHoraAtual.dataAtual();
        }else dataDoApontamento = oSAtividadesDiaAtual.getDATA();

        dataApontamento.setText(dataDoApontamento);

        drawer = findViewById(R.id.drawer_layout_continuar);
        NavigationView navigationView = findViewById(R.id.nav_view_continuar);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        abaSelecionada = 1;

        primeiraReg = true;
        primeiraIns = true;


        abaSelecionada=1;

        botaoApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(abaSelecionada!=1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                            new FragmentoRendimento()).commit();
                    abaSelecionada = 1;
                }
                }
        });

        botaoInsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abaSelecionada != 2) {
                    auxiliar.setAREA_REALIZADA(area);
                    auxiliar.setHO_ESCAVADEIRA(hoe);
                    auxiliar.setHO(ho);
                    auxiliar.setHM_ESCAVADEIRA(hme);
                    auxiliar.setHM(hm);
                    auxiliar.setHH(hh);
                    auxiliar.setDATA(dataDoApontamento);
                    auxiliar.setID_PRESTADOR(posicaoPrestador);
                    auxiliar.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                    auxiliar.setID_RESPONSAVEL(posicaoResponsavel);
                    auxiliar.setOBSERVACAO(obs);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                            new FragmentoInsumos()).commit();
                    abaSelecionada = 2;
                }
            }
        });


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salva();
                }
        });


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                        .setTitle("Voltar Para a Listagem de Registros?")
                        .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                 area="";
                                 ho="";
                                 hm="";
                                 hh="";
                                 hoe="";
                                 hme="";
                                 obs="";

                                auxiliar = null;
                                oSAtividadesDiaAtual = null;
                                primeiraReg = true;
                                primeiraIns = false;
                                listaJoinOsInsumosSelecionados = null;
                                Intent it = new Intent(ActivityRegistros.this, ActivityListagemRegistros.class);
                                startActivity(it);
                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();

            }
        });

        botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                new FragmentoRendimento()).commit();
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

            setarData(auxDia, auxMes, auxAno);
        }


        //Método auxiliar para setar a data no textview
        public void setarData(String dia, String mes, String ano) {
            dataDoApontamento=(dia + "/" + mes + "/" + ano).trim();
            dataApontamento.setText(dataDoApontamento);
        }
    }

    public void salva(){
        boolean converte = true;
        double testeAreaRealizada;
        if(area.contains(","))area = area.replace(',', '.');
        try{
            testeAreaRealizada = Double.valueOf(area);
        }catch (NumberFormatException | NullPointerException nf){
            converte = false;
            testeAreaRealizada = 0;
        }

        if(converte = false){
            AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                    .setTitle("Erro!")
                    .setMessage("O campo 'Área Realizada' contém um valor inválido.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        }

        if((testeAreaRealizada + osSelecionada.getAREA_REALIZADA()) > osSelecionada.getAREA_PROGRAMADA()) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityRegistros.this)
                    .setTitle("Erro!")
                    .setMessage("A área realizada não pode ser maior que a área programada")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        }else{
        if(oSAtividadesDiaAtual!=null && oSAtividadesDiaAtual.getDATA() != dataDoApontamento) {
            O_S_ATIVIDADES_DIA aux = oSAtividadesDiaAtual;

            dao.apagaOsAtividadeInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), aux.getDATA());

            viewModelOSAtividadesDia.delete(aux);
        }
        double auxAreaRealizada = osSelecionada.getAREA_REALIZADA();
        osSelecionada.setAREA_REALIZADA(auxAreaRealizada+testeAreaRealizada);
        dao.update(osSelecionada);


        oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();

        oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        oSAtividadesDiaAtual.setDATA(dataDoApontamento.trim());
        oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
        oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);


        if (area != null){
            if(area.contains(","))area = area.replace(',', '.');
            oSAtividadesDiaAtual.setAREA_REALIZADA(area);
        }
        else{
            oSAtividadesDiaAtual.setAREA_REALIZADA(null);
            testeAreaRealizada = 0;
        }


        if(hoe!=null){
            if(hoe.contains(","))hoe = hoe.replace(',', '.');
            oSAtividadesDiaAtual.setHO_ESCAVADEIRA(hoe);
        }
        else oSAtividadesDiaAtual.setHO_ESCAVADEIRA(null);

        if(ho!=null){
            if(ho.contains(","))ho = ho.replace(',', '.');
            oSAtividadesDiaAtual.setHO(ho);
        }
        else oSAtividadesDiaAtual.setHO(null);

        if(hm!=null){
            if(hm.contains(","))hm = hm.replace(',', '.');
            oSAtividadesDiaAtual.setHM(hm);
        }
        else oSAtividadesDiaAtual.setHM(null);

        if(hh!=null){
            if(hh.contains(","))hh = hh.replace(',', '.');
            oSAtividadesDiaAtual.setHH(hh);
        }
        else oSAtividadesDiaAtual.setHH(null);

        if(hme!=null){
            if(hme.contains(","))hme = hme.replace(',', '.');
            oSAtividadesDiaAtual.setHM_ESCAVADEIRA(hme);
        }
        else oSAtividadesDiaAtual.setHM_ESCAVADEIRA(null);

        if(obs!=null)oSAtividadesDiaAtual.setOBSERVACAO(obs);
        else oSAtividadesDiaAtual.setOBSERVACAO(null);

        viewModelOSAtividadesDia.insert(oSAtividadesDiaAtual);


        if(!listaJoinOsInsumosSelecionados.isEmpty()) {
            Join_OS_INSUMOS persiste;

            for (int i = 0; i < listaJoinOsInsumosSelecionados.size(); i++) {
                persiste = listaJoinOsInsumosSelecionados.get(i);
                dao.insert(new O_S_ATIVIDADE_INSUMOS_DIA(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataDoApontamento,
                        persiste.getID_INSUMO(), persiste.getQTD_APLICADO()));
            }
        }
            Intent it = new Intent(ActivityRegistros.this, ActivityListagemRegistros.class);
            Toast.makeText(getApplicationContext(), "Registro Salvo com sucesso!", Toast.LENGTH_LONG).show();
            startActivity(it);
        }
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
                break;

            case R.id.cadastrar_conta:
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
