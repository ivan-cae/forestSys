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
import static com.example.forestsys.activities.FragmentoApontamento.hh;
import static com.example.forestsys.activities.FragmentoApontamento.hm;
import static com.example.forestsys.activities.FragmentoApontamento.hme;
import static com.example.forestsys.activities.FragmentoApontamento.ho;
import static com.example.forestsys.activities.FragmentoApontamento.hoe;
import static com.example.forestsys.activities.FragmentoApontamento.obs;
import static com.example.forestsys.activities.FragmentoApontamento.area;
import static com.example.forestsys.activities.FragmentoApontamento.posicaoPrestador;
import static com.example.forestsys.activities.FragmentoApontamento.posicaoResponsavel;
//import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityApontamentos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    public static TextView dataApontamento;
    private ImageButton voltar;
    private Button botaoProsseguir;
    private Button botaoInsumos;
    private Button botaoApontamento;
    private ImageButton botaoDatePicker;
    public static ViewModelO_S_ATIVIDADES_DIA viewModelOSAtividadesDia;
    private DataHoraAtual dataHoraAtual;
    public static O_S_ATIVIDADES_DIA oSAtividadesDiaAtual;
    public static String dataDoApontamento;
    public static List<Join_OS_INSUMOS> listaJoinOsInsumosSelecionados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apontamentos);
        setTitle(nomeEmpresaPref);

        dataHoraAtual = new DataHoraAtual();
        listaJoinOsInsumosSelecionados = new ArrayList<Join_OS_INSUMOS>();

        Toolbar toolbar = findViewById(R.id.toolbar_continuar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        idOs = findViewById(R.id.id_os_continuar);
        dataApontamento = findViewById(R.id.data_apontamento);
        talhaoOs = findViewById(R.id.talhao_os_continuar);
        cicloOs = findViewById(R.id.ciclo_os_continuar);
        setorOs = findViewById(R.id.setor_os_continuar);
        obsOs = findViewById(R.id.obs_os_continuar);
        statusOs = findViewById(R.id.status_os_continuar);
        areaOs = findViewById(R.id.area_os_continuar);
        manejoOs = findViewById(R.id.manejo_os_continuar);
        madeiraOs = findViewById(R.id.madeira_os_continuar);
        botaoDatePicker = findViewById(R.id.botao_date_picker);

        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));
        talhaoOs.setText(String.valueOf(osSelecionada.getTALHAO()));
        cicloOs.setText(String.valueOf(osSelecionada.getCICLO()));
        setorOs.setText(String.valueOf(osSelecionada.getID_SETOR()));
        obsOs.setText(String.valueOf(osSelecionada.getOBSERVACAO()));
        statusOs.setText(String.valueOf("Andamento"));
        areaOs.setText(String.valueOf(osSelecionada.getAREA_PROGRAMADA()));
        manejoOs.setText(String.valueOf(osSelecionada.getID_MANEJO()));
        madeiraOs.setText(String.valueOf(osSelecionada.getMADEIRA_NO_TALHAO()));


        botaoApontamento = findViewById(R.id.botao_apontamentos_geral);
        botaoInsumos = findViewById(R.id.botao_apontamentos_insumos);
        voltar = findViewById(R.id.botao_continuar_voltar);
        botaoProsseguir = findViewById(R.id.botao_prosseguir_continuar);


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

        botaoApontamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                        new FragmentoApontamento()).commit();
            }
        });

        botaoInsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmento_continuar,
                        new FragmentoInsumos()).commit();
            }
        });


        botaoProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
                DAO dao = baseDeDados.dao();

                    if(oSAtividadesDiaAtual!=null && oSAtividadesDiaAtual.getDATA() != dataDoApontamento) {
                        O_S_ATIVIDADES_DIA aux = oSAtividadesDiaAtual;

                        dao.apagaOsAtividadeInsumosDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), aux.getDATA());

                        viewModelOSAtividadesDia.delete(aux);
                        oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();
                    }

                    oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();
                    oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                    oSAtividadesDiaAtual.setDATA(dataDoApontamento.trim());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);

                    if (area != null)oSAtividadesDiaAtual.setAREA_REALIZADA(area);
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);

                    if(hoe!=null)oSAtividadesDiaAtual.setHO_ESCAVADEIRA(hoe);
                    else oSAtividadesDiaAtual.setHO_ESCAVADEIRA(null);

                    if(ho!=null)oSAtividadesDiaAtual.setHO(ho);
                    else oSAtividadesDiaAtual.setHO(null);

                    if(hm!=null)oSAtividadesDiaAtual.setHM(hm);
                    else oSAtividadesDiaAtual.setHM(null);

                    if(hh!=null)oSAtividadesDiaAtual.setHH(hh);
                    else oSAtividadesDiaAtual.setHH(null);

                    if(hme!=null)oSAtividadesDiaAtual.setHM_ESCAVADEIRA(hme);
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

                Intent it = new Intent(ActivityApontamentos.this, ActivityListagemApontamentos.class);
                Toast.makeText(getApplicationContext(), "Apontamento Salvo com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(it);
                }
        });


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(ActivityApontamentos.this)
                        .setTitle("Voltar Para a Listagem de Apontamentos?")
                        .setMessage("Caso clique em SIM, você perderá os dados não salvos!")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityApontamentos.this, ActivityListagemApontamentos.class);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
