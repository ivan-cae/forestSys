package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.repositorios.RepositorioImplementos;
import com.example.forestsys.repositorios.RepositorioMaquinas;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.example.forestsys.viewModels.ViewModelMaquinas;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class ActivityCalibragem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView idOs;

    private EditText p1_a1;
    private EditText p1_a2;
    private EditText p1_a3;
    private EditText p1_a4;
    private EditText p1_a5;

    private TextView p1Media;


    private EditText p2_a1;
    private EditText p2_a2;
    private EditText p2_a3;
    private EditText p2_a4;
    private EditText p2_a5;

    private TextView p2Media;


    private TextView dif1_p1;
    private TextView dif2_p1;
    private TextView dif3_p1;
    private TextView dif4_p1;
    private TextView mediaDifP1;

    private TextView dif1_p2;
    private TextView dif2_p2;
    private TextView dif3_p2;
    private TextView dif4_p2;
    private TextView mediaDifP2;

    private TextView mediaProduto1;
    private TextView mediaProduto2;
    private TextView desvioProduto1;
    private TextView desvioProduto2;


    Spinner spinnerOperador;
    Spinner spinnerMaquina;
    Spinner spinnerImplemento;

    TextView osTalhao;
    TextView osData;
    TextView osTurno;

    ImageButton botaoConfirma;
    ImageButton botaoVoltar;

    boolean todosConformeP1=false;
    boolean todosConformeP2=false;

    ObjectAnimator objAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);
        setTitle(nomeEmpresaPref);

        spinnerOperador = findViewById(R.id.spinner_operador_calibragem);
        spinnerMaquina = findViewById(R.id.spinner_maquina_calibragem);
        spinnerImplemento = findViewById(R.id.spinner_implemento_calibragem);

        osData = findViewById(R.id.data_os_calibragem);
        osTalhao = findViewById(R.id.talhao_os_calibragem);
        osTurno = findViewById(R.id.turno_os_calibragem);

        mediaProduto1 = findViewById(R.id.media_produto1);
        mediaProduto2 = findViewById(R.id.media_produto2);

        desvioProduto1 = findViewById(R.id.desvio_produto1);
        desvioProduto2 = findViewById(R.id.desvio_produto2);

        p1_a1 = findViewById(R.id.p1_amostra_1);
        p1_a2 = findViewById(R.id.p1_amostra_2);
        p1_a3 = findViewById(R.id.p1_amostra_3);
        p1_a4 = findViewById(R.id.p1_amostra_4);
        p1_a5 = findViewById(R.id.p1_amostra_5);
        p1Media = findViewById(R.id.p1_media);

        p2_a1 = findViewById(R.id.p2_amostra_1);
        p2_a2 = findViewById(R.id.p2_amostra_2);
        p2_a3 = findViewById(R.id.p2_amostra_3);
        p2_a4 = findViewById(R.id.p2_amostra_4);
        p2_a5 = findViewById(R.id.p2_amostra_5);
        p2Media = findViewById(R.id.p2_media);

        dif1_p1 = findViewById(R.id.dif1_p1);
        dif2_p1 = findViewById(R.id.dif2_p1);
        dif3_p1 = findViewById(R.id.dif3_p1);
        dif4_p1 = findViewById(R.id.dif4_p1);
        mediaDifP1 = findViewById(R.id.media_dif_p1);

        dif1_p2 = findViewById(R.id.dif1_p2);
        dif2_p2 = findViewById(R.id.dif2_p2);
        dif3_p2 = findViewById(R.id.dif3_p2);
        dif4_p2 = findViewById(R.id.dif4_p2);
        mediaDifP2 = findViewById(R.id.media_dif_p2);

        botaoConfirma = findViewById(R.id.botao_calibragem_confirma);
        botaoVoltar = findViewById(R.id.botao_calibragem_voltar);

        Button botaoMediaP1;
        Button botaoMediaP2;


        idOs = findViewById(R.id.id_os_calibragem);
        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));

        osTalhao.setText(osSelecionada.getTALHAO());

        botaoMediaP1 = findViewById(R.id.botao_calibragem_media_p1);

        botaoMediaP2 = findViewById(R.id.botao_calibragem_media_p2);


        botaoMediaP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todosConformeP1 = true;
                double mediaPercentual;
                double mediaGeral;

                Double[] amostrasP1 = new Double[5];

                if (p1_a1.getText().toString().isEmpty()) {
                    amostrasP1[0] = 0.0;
                } else {
                    amostrasP1[0] = Double.valueOf(String.valueOf(p1_a1.getText()));
                }

                if (p1_a2.getText().toString().isEmpty()) {
                    amostrasP1[1] = 0.0;
                } else {
                    amostrasP1[1] = Double.valueOf(String.valueOf(p1_a2.getText()));
                }

                if (p1_a3.getText().toString().isEmpty()) {
                    amostrasP1[2] = 0.0;
                } else {
                    amostrasP1[2] = Double.valueOf(String.valueOf(p1_a3.getText()));
                }

                if (p1_a4.getText().toString().isEmpty()) {
                    amostrasP1[3] = 0.0;
                } else {
                    amostrasP1[3] = Double.valueOf(String.valueOf(p1_a4.getText()));
                }

                if (p1_a5.getText().toString().isEmpty()) {
                    amostrasP1[4] = 0.0;
                } else {
                    amostrasP1[4] = Double.valueOf(String.valueOf(p1_a5.getText()));
                }


                if (diferencaPercentual(amostrasP1[0], amostrasP1[1]) > 5.0 || diferencaPercentual(amostrasP1[0], amostrasP1[1]) < -5.0) {
                    dif1_p1.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP1 = false;
                } else {
                    dif1_p1.setTextColor(Color.parseColor("#32CD32"));
                }
                dif1_p1.setText(String.valueOf((diferencaPercentual(amostrasP1[0], amostrasP1[1]))));


                if (diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.0 || diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.0) {
                    dif2_p1.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP1 = false;
                } else {
                    dif2_p1.setTextColor(Color.parseColor("#32CD32"));
                }
                dif2_p1.setText(String.valueOf((diferencaPercentual(amostrasP1[1], amostrasP1[2]))));


                if (diferencaPercentual(amostrasP1[2], amostrasP1[3]) > 5.0 || diferencaPercentual(amostrasP1[2], amostrasP1[2]) < -5.0) {
                    dif3_p1.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP1 = false;
                } else {
                    dif3_p1.setTextColor(Color.parseColor("#32CD32"));
                }
                dif3_p1.setText(String.valueOf((diferencaPercentual(amostrasP1[2], amostrasP1[3]))));


                if (diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.0 || diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.0) {
                    dif4_p1.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP1 = false;
                } else {
                    dif4_p1.setTextColor(Color.parseColor("#32CD32"));
                }
                dif4_p1.setText(String.valueOf((diferencaPercentual(amostrasP1[3], amostrasP1[4]))));


                mediaPercentual = (diferencaPercentual(amostrasP1[0], amostrasP1[1]) + diferencaPercentual(amostrasP1[1], amostrasP1[2]) +
                        diferencaPercentual(amostrasP1[2], amostrasP1[3]) + diferencaPercentual(amostrasP1[3], amostrasP1[4])) / 4;

                mediaGeral = (amostrasP1[0] + amostrasP1[1] + amostrasP1[2] + amostrasP1[3] + amostrasP1[4]) / 5;

                p1Media.setText(String.valueOf((mediaGeral)));
                mediaProduto1.setText(String.valueOf((mediaGeral)));
                mediaDifP1.setText(String.valueOf((mediaPercentual)));
                double aux = desvioPadrao(amostrasP1);
                desvioProduto1.setText(String.valueOf((aux)));

                if(todosConformeP1==true &&todosConformeP2 ==true){
                    botaoConfirma.setVisibility(View.VISIBLE);
                    pulseAnimation(botaoConfirma);
                }
                if(todosConformeP1==false ||todosConformeP2 ==false)botaoConfirma.setVisibility(View.INVISIBLE);
            }
        });


        botaoMediaP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todosConformeP2 = true;
                double mediaPercentual;
                double mediaGeral;

                Double[] amostrasP2 = new Double[5];

                if (p2_a1.getText().toString().isEmpty()) {
                    amostrasP2[0] = 0.0;
                }
                amostrasP2[0] = Double.valueOf(String.valueOf(p2_a1.getText()));

                if (p2_a2.getText().toString().isEmpty()
                ) {
                    amostrasP2[1] = 0.0;
                }
                amostrasP2[1] = Double.valueOf(String.valueOf(p2_a2.getText()));


                if (p2_a3.getText().toString().isEmpty()) {
                    amostrasP2[2] = 0.0;
                }
                amostrasP2[2] = Double.valueOf(String.valueOf(p2_a3.getText()));


                if (p2_a4.getText().toString().isEmpty()) {
                    amostrasP2[3] = 0.0;
                }
                amostrasP2[3] = Double.valueOf(String.valueOf(p2_a4.getText()));

                if (p2_a5.getText().toString().isEmpty()) {
                    amostrasP2[4] = 0.0;
                }
                amostrasP2[4] = Double.valueOf(String.valueOf(p2_a5.getText()));


                if (diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.0 || diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.0) {
                    dif1_p2.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP2 = false;
                } else {
                    dif1_p2.setTextColor(Color.parseColor("#32CD32"));
                }
                dif1_p2.setText(String.valueOf((diferencaPercentual(amostrasP2[0], amostrasP2[1]))));


                if (diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.0 || diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.0) {
                    dif2_p2.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP2 = false;
                } else {
                    dif2_p2.setTextColor(Color.parseColor("#32CD32"));
                }
                dif2_p2.setText(String.valueOf((diferencaPercentual(amostrasP2[1], amostrasP2[2]))));


                if (diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.0 || diferencaPercentual(amostrasP2[2], amostrasP2[2]) < -5.0) {
                    dif3_p2.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP2 = false;
                } else {
                    dif3_p2.setTextColor(Color.parseColor("#32CD32"));
                }
                dif3_p2.setText(String.valueOf((diferencaPercentual(amostrasP2[2], amostrasP2[3]))));


                if (diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.0 || diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.0) {
                    dif4_p2.setTextColor(Color.parseColor("#FF0000"));
                    todosConformeP2 = false;
                } else {
                    dif4_p2.setTextColor(Color.parseColor("#32CD32"));
                }
                dif4_p2.setText(String.valueOf((diferencaPercentual(amostrasP2[3], amostrasP2[4]))));


                mediaPercentual = (diferencaPercentual(amostrasP2[0], amostrasP2[1]) + diferencaPercentual(amostrasP2[1], amostrasP2[2]) +
                        diferencaPercentual(amostrasP2[2], amostrasP2[3]) + diferencaPercentual(amostrasP2[3], amostrasP2[4])) / 4;

                mediaGeral = (amostrasP2[0] + amostrasP2[1] + amostrasP2[2] + amostrasP2[3] + amostrasP2[4]) / 5;

                p2Media.setText(String.valueOf((mediaGeral)));
                mediaDifP2.setText(String.valueOf((mediaPercentual)));
                mediaProduto2.setText(String.valueOf((mediaGeral)));
                double aux = desvioPadrao(amostrasP2);

                desvioProduto2.setText(String.valueOf(aux));

                if(todosConformeP1==true &&todosConformeP2 ==true){
                    botaoConfirma.setVisibility(View.VISIBLE);
                    pulseAnimation(botaoConfirma);
                }
                if(todosConformeP1==false ||todosConformeP2 ==false)botaoConfirma.setVisibility(View.INVISIBLE);
            }
        });




        RepositorioMaquinas repositorioMaquinas = new RepositorioMaquinas(getApplication());

        RepositorioUsers repositorioUsers = new RepositorioUsers(getApplication());

        RepositorioImplementos repositorioImplementos = new RepositorioImplementos(getApplication());


        ArrayList<MAQUINAS> maquinas = new ArrayList<>(repositorioMaquinas.listaMaquinas());

        ArrayList<GGF_USUARIOS> usuarios = new ArrayList<>(repositorioUsers.listaUsuarios());

        ArrayList<IMPLEMENTOS> implementos = new ArrayList<>(repositorioImplementos.listaImplementos());


        ArrayAdapter<MAQUINAS> adapterMaquinas = new ArrayAdapter<MAQUINAS>(this,
                android.R.layout.simple_spinner_item, maquinas);
        adapterMaquinas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaquina.setAdapter(adapterMaquinas);


        ArrayAdapter<GGF_USUARIOS> adapterUsuarios = new ArrayAdapter<GGF_USUARIOS>(this,
                android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperador.setAdapter(adapterUsuarios);


        ArrayAdapter<IMPLEMENTOS> adapterImplementos = new ArrayAdapter<IMPLEMENTOS>(this,
                android.R.layout.simple_spinner_item, implementos);
        adapterImplementos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImplemento.setAdapter(adapterImplementos);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calibragem);

        setSupportActionBar(toolbar);

        getSupportActionBar().
                setSubtitle(/*usuarioLogado.getValue().getEMAIL()*/"a");

        drawer = findViewById(R.id.drawer_layout_calibragem);

        NavigationView navigationView = findViewById(R.id.nav_view_calibragem);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActivityCalibragem.this)
                        .setTitle("Voltar Para o Início?")
                        .setMessage("Caso clique em SIM, você perderá os dados da calibragem!")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityCalibragem.this, ActivityMain.class);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
            }
        });

        botaoConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActivityCalibragem.this)
                        .setTitle("Concluir")
                        .setMessage("Deseja Concluir a Calibragem?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityCalibragem.this, ActivityContinuarOs.class);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
            }
        });
    }

    public void pulseAnimation(ImageButton btnObj) {
        objAnim = ObjectAnimator.ofPropertyValuesHolder(btnObj, PropertyValuesHolder.ofFloat("scaleX", 1.5f), PropertyValuesHolder.ofFloat("scaleY", 1.5f));
        objAnim.setDuration(300);
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);
        objAnim.setRepeatMode(ObjectAnimator.REVERSE);
        objAnim.start();
    }

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

    private static double arredondar(double media) {
        return Math.round(media * 100.0) / 100.0;
    }

    private static double diferencaPercentual(double v1, double v2) {
        double calculo = ((v2 - v1) / v1) * 100;
        return arredondar(calculo);
    }

    public static double desvioPadrao(Double[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Null 'data' array.");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Zero length 'data' array.");
        }
        double avg = calculateMean(data, false);
        double sum = 0.0;

        for (int counter = 0; counter < data.length; counter++) {
            double diff = data[counter].doubleValue() - avg;
            sum = sum + diff * diff;
        }
        return Math.sqrt(sum / (data.length - 1));
    }

    public static double calculateMean(Double[] values,
                                       boolean includeNullAndNaN) {

        if (values == null) {
            throw new IllegalArgumentException("Null 'values' argument.");
        }
        double sum = 0.0;
        double current;
        int counter = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                current = values[i].doubleValue();
            } else {
                current = Double.NaN;
            }
            if (includeNullAndNaN || !Double.isNaN(current)) {
                sum = sum + current;
                counter++;
            }
        }
        double result = (sum / counter);
        return result;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
