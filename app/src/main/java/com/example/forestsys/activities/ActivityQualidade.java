package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.ATIVIDADE_INDICADORES;
import com.example.forestsys.classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityQualidade extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private TextView talhao;
    private TextView data;
    private TextView status;
    private TextView area;
    private TextView areaRealizada;
    private TextView manejo;
    private DataHoraAtual dataHoraAtual;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private ImageButton botaoVerion;
    private ImageButton botaoPonto;
    private List<Join_OS_INSUMOS> joinOsInsumos;
    private EditText mediaEditP1;
    private EditText mediaEditP2;
    private EditText desvioEditP1;
    private EditText desvioEditP2;
    private TextView pontosRealizados;
    private List<AVAL_PONTO_SUBSOLAGEM> listaPonto;
    private ImageButton botaoVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualidade);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();
        dataHoraAtual = new DataHoraAtual();

        talhao = findViewById(R.id.qualidade_talhao);
        data = findViewById(R.id.qualidade_data);
        status = findViewById(R.id.qualidade_status);
        area = findViewById(R.id.qualidade_area);
        areaRealizada = findViewById(R.id.qualidade_area_realizada);
        manejo = findViewById(R.id.qualidade_manejo);
        botaoVerion = findViewById(R.id.qualidade_botao_verion);
        botaoPonto = findViewById(R.id.qualidade_botao_ponto);
        pontosRealizados = findViewById(R.id.qualidade_pontos_realizados);
        botaoVoltar = findViewById(R.id.botao_qualidade_voltar);

        talhao.setText(osSelecionada.getTALHAO());
        data.setText(dataHoraAtual.dataAtual());
        status.setText(osSelecionada.getSTATUS());
        area.setText(osSelecionada.getDATA_PROGRAMADA());
        areaRealizada.setText(String.valueOf(osSelecionada.getAREA_REALIZADA()));
        manejo.setText(osSelecionada.getID_MANEJO());

        joinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

         listaPonto = dao.listaAvalSubsolagem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

         if(listaPonto.isEmpty()) pontosRealizados.setText("0");
         else pontosRealizados.setText(String.valueOf(listaPonto.get(listaPonto.size()-1).getPONTO()));
        botaoVerion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreDialogoverion();
            }
        });

        botaoPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreDialogoPonto();
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                    .setTitle("Voltar")
                    .setMessage("Deseja Para a Tela da Atividade?")
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
                                       });


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

    //Abre caixa de diálogo para preenchimento dos dados verion
    public void abreDialogoverion() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_verion, null);

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

        Button botaoRegistrar = (Button) mView.findViewById(R.id.dialogo_qualidade_verion_botao_registrar);

        List<ATIVIDADE_INDICADORES> atividadeIndicadores = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "S");

        mediaP1Nome.setText(joinOsInsumos.get(0).getDESCRICAO()+ " - P1");
        mediaP2Nome.setText(joinOsInsumos.get(1).getDESCRICAO()+ " - P2");
        desvioP1Nome.setText(joinOsInsumos.get(0).getDESCRICAO()+ " - P1");
        desvioP2Nome.setText(joinOsInsumos.get(1).getDESCRICAO()+ " - P2");

        letraItem1.setText(atividadeIndicadores.get(0).getREFERENCIA());
        letraItem2.setText(atividadeIndicadores.get(1).getREFERENCIA());
        letraItem3.setText(atividadeIndicadores.get(2).getREFERENCIA());
        letraItem4.setText(atividadeIndicadores.get(3).getREFERENCIA());

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                boolean todosEditCorretos = true;

                if (converteu(mediaEditP1)==false) todosEditCorretos=false;
                if (converteu(mediaEditP2)==false) todosEditCorretos=false;
                if (converteu(desvioEditP1)==false) todosEditCorretos=false;
                if (converteu(desvioEditP2)==false) todosEditCorretos=false;

                if(todosEditCorretos==false){
                    AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                            .setTitle("Erro!")
                            .setMessage("Um ou mais campos contém valores inválidos, favor corrigir.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }
                else dialog.dismiss();
            }
        });
    }

    //Abre caixa de diálogo para preenchimento dos dados do ponto
    public void abreDialogoPonto() {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_qualidade_ponto, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        TextView numeroPonto = mView.findViewById(R.id.dialogo_qualidade_ponto_numero);
        numeroPonto.setText(String.valueOf(listaPonto.size()+1));

        TextView textItem1 = mView.findViewById(R.id.dialogo_qualidade_ponto_item1);
        TextView textItem2 = mView.findViewById(R.id.dialogo_qualidade_ponto_item2);
        TextView textItem3 = mView.findViewById(R.id.dialogo_qualidade_ponto_item3);
        TextView textItem4 = mView.findViewById(R.id.dialogo_qualidade_ponto_item4);
        TextView textItem5 = mView.findViewById(R.id.dialogo_qualidade_ponto_item5);
        TextView textItem6 = mView.findViewById(R.id.dialogo_qualidade_ponto_item6);
        TextView textItem7 = mView.findViewById(R.id.dialogo_qualidade_ponto_item7);
        TextView textItem8 = mView.findViewById(R.id.dialogo_qualidade_ponto_item8);
        TextView textItem9 = mView.findViewById(R.id.dialogo_qualidade_ponto_item9);
        TextView textItem10 = mView.findViewById(R.id.dialogo_qualidade_ponto_item10);

        TextView textLetra1 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item1);
        TextView textLetra2 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item2);
        TextView textLetra3 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item3);
        TextView textLetra4 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item4);
        TextView textLetra5 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item5);
        TextView textLetra6 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item6);
        TextView textLetra7 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item7);
        TextView textLetra8 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item8);
        TextView textLetra9 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item9);
        TextView textLetra10 = mView.findViewById(R.id.dialogo_qualidade_ponto_letra_item10);

        EditText editItem1 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item1);
        EditText editItem2 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item2);
        EditText editItem3 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item3);
        EditText editItem4 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item4);
        CheckBox editItem5 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item5);
        EditText editItem6 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item6);
        CheckBox editItem7 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item7);
        CheckBox editItem8 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item8);
        CheckBox editItem9 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item9);
        EditText editItem10 = mView.findViewById(R.id.dialogo_qualidade_ponto_edit_item10);


        Button botaoRegistrar = (Button) mView.findViewById(R.id.dialogo_qualidade_ponto_botao_registrar);
        List<ATIVIDADE_INDICADORES> atividadeIndicadores = dao.listaAtividadeIndicadores(osSelecionada.getID_ATIVIDADE(), "N");

        textItem1.setText(atividadeIndicadores.get(0).getDESCRICAO());
        textItem2.setText(atividadeIndicadores.get(1).getDESCRICAO());
        textItem3.setText(atividadeIndicadores.get(2).getDESCRICAO());
        textItem4.setText(atividadeIndicadores.get(3).getDESCRICAO());
        textItem5.setText(atividadeIndicadores.get(4).getDESCRICAO());
        textItem6.setText(atividadeIndicadores.get(5).getDESCRICAO());
        textItem7.setText(atividadeIndicadores.get(6).getDESCRICAO());
        textItem8.setText(atividadeIndicadores.get(7).getDESCRICAO());
        textItem9.setText(atividadeIndicadores.get(8).getDESCRICAO());
        textItem10.setText(atividadeIndicadores.get(9).getDESCRICAO());

        textLetra1.setText(atividadeIndicadores.get(0).getREFERENCIA());
        textLetra2.setText(atividadeIndicadores.get(1).getREFERENCIA());
        textLetra3.setText(atividadeIndicadores.get(2).getREFERENCIA());
        textLetra4.setText(atividadeIndicadores.get(3).getREFERENCIA());
        textLetra5.setText(atividadeIndicadores.get(4).getREFERENCIA());
        textLetra6.setText(atividadeIndicadores.get(5).getREFERENCIA());
        textLetra7.setText(atividadeIndicadores.get(6).getREFERENCIA());
        textLetra8.setText(atividadeIndicadores.get(7).getREFERENCIA());
        textLetra9.setText(atividadeIndicadores.get(8).getREFERENCIA());
        textLetra10.setText(atividadeIndicadores.get(9).getREFERENCIA());

        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                boolean todosEditCorretos = true;

                if(converteu(editItem1)==false) todosEditCorretos=false;
                if(converteu(editItem2)==false) todosEditCorretos=false;
                if(converteu(editItem3)==false) todosEditCorretos=false;
                if(converteu(editItem4)==false) todosEditCorretos=false;
                if(converteu(editItem6)==false) todosEditCorretos=false;
                if(converteu(editItem10)==false) todosEditCorretos=false;

                if(todosEditCorretos==false){
                    AlertDialog dialog = new AlertDialog.Builder(ActivityQualidade.this)
                            .setTitle("Erro!")
                            .setMessage("Um ou mais campos contém valores inválidos, favor corrigir.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }else {
                    double check = 0;

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(0).getID_INDICADOR(),
                            Double.valueOf(editItem1.getText().toString().replace(',', '.')), 0, 0));

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(1).getID_INDICADOR(),
                            Double.valueOf(editItem2.getText().toString().replace(',', '.')), 0, 0));

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(2).getID_INDICADOR(),
                            Double.valueOf(editItem3.getText().toString().replace(',', '.')), 0, 0));

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(3).getID_INDICADOR(),
                            Double.valueOf(editItem4.getText().toString().replace(',', '.')), 0, 0));

                    if(editItem5.isChecked()) check=1;
                    else check = 0;
                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(4).getID_INDICADOR(),
                            check, 0, 0));

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(5).getID_INDICADOR(),
                            Double.valueOf(editItem6.getText().toString().replace(',', '.')), 0, 0));

                    if(editItem7.isChecked()) check=1;
                    else check = 0;
                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(6).getID_INDICADOR(),
                            check, 0, 0));

                    if(editItem8.isChecked()) check=1;
                    else check = 0;
                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(7).getID_INDICADOR(),
                            check, 0, 0));

                    if(editItem9.isChecked()) check=1;
                    else check = 0;
                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(8).getID_INDICADOR(),
                            check, 0, 0));

                    dao.insert(new AVAL_PONTO_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(data.getText().toString()),
                            listaPonto.size()+1, osSelecionada.getID_ATIVIDADE(), atividadeIndicadores.get(9).getID_INDICADOR(),
                            Double.valueOf(editItem10.getText().toString().replace(',', '.')), 0, 0));

                     Intent it = new Intent(ActivityQualidade.this, ActivityQualidade.class);
                     startActivity(it);
                     dialog.dismiss();
                }
                }
        });
    }

    private boolean converteu(EditText valor){
            try {
                double teste = Double.parseDouble(valor.getText().toString().replace(',', '.'));
            }
            catch (NumberFormatException | NullPointerException n) {
                valor.setError("");
                return false;
            }
        return true;
    }

}