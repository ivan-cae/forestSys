package com.example.forestsys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.R;
import com.example.forestsys.Calculadora.CalculadoraMain;
import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;

public class ActivityDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    TextView abertas;
    TextView andamento;
    TextView finalizadas;
    TextView mediaHH;
    TextView mediaHM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle(nomeEmpresaPref);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dash);

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

        abertas = findViewById(R.id.dash_abertas);
        andamento = findViewById(R.id.dash_andamento);
        finalizadas = findViewById(R.id.dash_finalizadas);
        mediaHH = findViewById(R.id.dash_media_hh);
        mediaHM = findViewById(R.id.dash_media_hm);

        drawer = findViewById(R.id.drawer_layout_dash);
        NavigationView navigationView = findViewById(R.id.nav_view_dash);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        List<O_S_ATIVIDADES> listaTodasOs = dao.todasOs();
        List<O_S_ATIVIDADES_DIA> listaReg = dao.todasOsAtividadesDia();

        int contAbertas = 0;
        int contAndamento = 0;
        int contfinalizadas = 0;

        for(int i = 0; i<listaTodasOs.size(); i++){
            if(listaTodasOs.get(i).getSTATUS_NUM() == 0) contAbertas++;
            if(listaTodasOs.get(i).getSTATUS_NUM() == 1) contAndamento++;
            if(listaTodasOs.get(i).getSTATUS_NUM() == 2) contfinalizadas++;
        }

        abertas.setText(String.valueOf(contAbertas));
        andamento.setText(String.valueOf(contAndamento));
        finalizadas.setText(String.valueOf(contfinalizadas));
        int contasValidadas = contAndamento+contfinalizadas;

        Double acumuladorHH = 0.0;
        Double acumuladorHM = 0.0;

        for(int i = 0; i<listaReg.size(); i++){
            O_S_ATIVIDADES aux = dao.selecionaOs(listaReg.get(i).getID_PROGRAMACAO_ATIVIDADE());
            if(aux.getSTATUS_NUM() != 0){
                try {
                    if (listaReg.get(i).getHH() == null || Double.valueOf(listaReg.get(i).getHH()).isNaN())
                        listaReg.get(i).setHH("0");
                    if (listaReg.get(i).getHM() == null || Double.valueOf(listaReg.get(i).getHM()).isNaN())
                        listaReg.get(i).setHM("0");
                }catch(NumberFormatException n){
                    listaReg.get(i).setHH("0");
                    listaReg.get(i).setHM("0");
                }
                acumuladorHH += Double.valueOf(listaReg.get(i).getHH());
                acumuladorHM += Double.valueOf(listaReg.get(i).getHM());
                }
        }

        DecimalFormat df = new DecimalFormat(".00");

        acumuladorHH= acumuladorHH/contasValidadas;
        if(acumuladorHH.isNaN() || acumuladorHH.isInfinite() || acumuladorHH==null) acumuladorHH = 0.0;
        acumuladorHH = Double.valueOf(df.format(acumuladorHH).replace(',', '.'));

        acumuladorHM= acumuladorHM/contasValidadas;
        if(acumuladorHM.isNaN() || acumuladorHM.isInfinite() || acumuladorHM==null) acumuladorHM = 0.0;
        acumuladorHM = Double.valueOf(df.format(acumuladorHM).replace(',', '.'));

        mediaHH.setText(String.valueOf(acumuladorHH));
        mediaHM.setText(String.valueOf(acumuladorHM));
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
