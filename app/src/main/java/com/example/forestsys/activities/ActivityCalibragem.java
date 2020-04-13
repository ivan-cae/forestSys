package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;

import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.LOG;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.repositorios.RepositorioImplementos;
import com.example.forestsys.repositorios.RepositorioMaquinas;
import com.example.forestsys.repositorios.RepositorioOPERADORES;
import com.example.forestsys.viewModels.ViewModelCALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.viewModels.ViewModelMAQUINA_IMPLEMENTO;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static java.lang.StrictMath.toIntExact;
import static java.sql.Types.NULL;

public class ActivityCalibragem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView idOs;

    private TextView p1_a1;
    private TextView p1_a2;
    private TextView p1_a3;
    private TextView p1_a4;
    private TextView p1_a5;

    private TextView p1Media;


    private TextView p2_a1;
    private TextView p2_a2;
    private TextView p2_a3;
    private TextView p2_a4;
    private TextView p2_a5;

    private TextView p2Media;


    private TextView dif1_p1;
    private TextView dif2_p1;
    private TextView dif3_p1;
    private TextView dif4_p1;
    private TextView mediaDifP2;

    private TextView dif1_p2;
    private TextView dif2_p2;
    private TextView dif3_p2;
    private TextView dif4_p2;
    private TextView mediaDifP1;

    private TextView mediaProduto1;
    private TextView mediaProduto2;
    private TextView desvioProduto1;
    private TextView desvioProduto2;


    private Spinner spinnerOperador;
    private Spinner spinnerMaquina;
    private Spinner spinnerImplemento;

    private TextView osTalhao;
    private TextView osData;
    private TextView osTurno;

    private ImageButton botaoMediaP1;
    private ImageButton botaoMediaP2;
    private ImageButton botaoConfirma;
    private ImageButton botaoVoltar;

    boolean todosConformeP1 = false;
    boolean todosConformeP2 = false;

    private ObjectAnimator objAnim;

    private int atualP2;
    private int atualP1;

    private String texto;

    private Double[] amostrasP1 = new Double[5];
    private Double[] amostrasP2 = new Double[5];

    private int posicaoImplemento;
    private int posicaoMaquina;
    private int posicaoOperador;

    private Double mediaPercentualp1;
    private Double mediaGeralp1;

    private Double mediaPercentualp2;
    private Double mediaGeralp2;

    private String valorCorreto;

    private int corrigirP1 = 0;

    private int corrigirP2 = 0;

    BaseDeDados baseDeDados;
    DAO dao;

    RepositorioMaquinas repositorioMaquinas;

    RepositorioOPERADORES repositorioOperadores;

    RepositorioImplementos repositorioImplementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);
        setTitle(nomeEmpresaPref);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

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

        atualP1 = 1;
        atualP2 = 1;


        idOs = findViewById(R.id.id_os_calibragem);
        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));

        osTalhao.setText(osSelecionada.getTALHAO());

        botaoMediaP1 = findViewById(R.id.botao_calibragem_media_p1);

        botaoMediaP2 = findViewById(R.id.botao_calibragem_media_p2);

        osData.setText(DateFormat.format("dd/MM/yyyy", new Date()).toString());

        osTurno.setText(checaTurno());

        p1_a1.setFocusable(false);


        repositorioMaquinas = new RepositorioMaquinas(getApplication());

        repositorioOperadores = new RepositorioOPERADORES(getApplication());

        repositorioImplementos = new RepositorioImplementos(getApplication());

        ArrayList<MAQUINAS> maquinas = new ArrayList<>(repositorioMaquinas.listaMaquinas());

        ArrayList<OPERADORES> operadores = new ArrayList<>(repositorioOperadores.listaOperadores());

        ArrayList<IMPLEMENTOS> implementos = new ArrayList<>(repositorioImplementos.listaImplementos());

        ArrayAdapter<MAQUINAS> adapterMaquinas = new ArrayAdapter<MAQUINAS>(this,
                android.R.layout.simple_spinner_item, maquinas);
        adapterMaquinas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaquina.setAdapter(adapterMaquinas);

        ArrayAdapter<OPERADORES> adapterUsuarios = new ArrayAdapter<OPERADORES>(this,
                android.R.layout.simple_spinner_item, operadores);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperador.setAdapter(adapterUsuarios);


        ArrayAdapter<IMPLEMENTOS> adapterImplementos = new ArrayAdapter<IMPLEMENTOS>(this,
                android.R.layout.simple_spinner_item, implementos);
        adapterImplementos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImplemento.setAdapter(adapterImplementos);

        spinnerImplemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) position = 1;
                else position += 1;
                posicaoImplemento = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicaoImplemento = 1;
            }
        });

        spinnerMaquina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) position = 1;
                else position += 1;
                posicaoMaquina = position;
                if(checaMaquinaCalibracao(posicaoMaquina) == true) caixaAlertaMaquina();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicaoMaquina = 1;
            }
        });

        spinnerOperador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) position = 1;
                else position += 1;
                posicaoOperador = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicaoOperador = 1;
            }
        });



        for (int i = 0; i < 5; i++) {
            amostrasP1[i] = 0.0;
            amostrasP2[i] = 0.0;
        }

        p1_a1.setClickable(false);

        p1_a2.setClickable(false);

        p1_a3.setClickable(false);

        p1_a4.setClickable(false);

        p1_a5.setClickable(false);


        p2_a1.setClickable(false);

        p2_a2.setClickable(false);

        p2_a3.setClickable(false);

        p2_a4.setClickable(false);

        p2_a5.setClickable(false);


        botaoMediaP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corrigirP1 = 0;
                corrigirP2 = 0;


                if (atualP1 <= 5) abreDialogoP1();

                if (atualP1 == 1) {
                    p1_a1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP1 = 1;
                            abreDialogoP1();
                        }
                    });
                }

                if (atualP1 == 2) {
                    p1_a2.setCursorVisible(true);
                    p1_a2.setClickable(true);
                    p1_a2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP1 = 2;
                            abreDialogoP1();
                        }
                    });
                }

                if (atualP1 == 3) {
                    p1_a3.setCursorVisible(true);
                    p1_a3.setClickable(true);
                    p1_a3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP1 = 3;
                            abreDialogoP1();
                        }
                    });
                }

                if (atualP1 == 4) {
                    p1_a4.setCursorVisible(true);
                    p1_a4.setClickable(true);
                    p1_a4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP1 = 4;
                            abreDialogoP1();
                        }
                    });
                }

                if (atualP1 == 5) {
                    p1_a5.setCursorVisible(true);
                    p1_a5.setClickable(true);
                    p1_a5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP1 = 5;
                            abreDialogoP1();
                        }
                    });
                }
            }
        });


        botaoMediaP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corrigirP1 = 0;
                corrigirP2 = 0;


                if (atualP2 <= 5) abreDialogoP2();

                if (atualP2 == 1) {
                    p2_a1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP2 = 1;
                            abreDialogoP2();
                        }
                    });
                }

                if (atualP2 == 2) {
                    p2_a2.setCursorVisible(true);
                    p2_a2.setClickable(true);
                    p2_a2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP2 = 2;
                            abreDialogoP2();
                        }
                    });
                }

                if (atualP2 == 3) {
                    p2_a3.setCursorVisible(true);
                    p2_a3.setClickable(true);
                    p2_a3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP2 = 3;
                            abreDialogoP2();
                        }
                    });
                }

                if (atualP2 == 4) {
                    p2_a4.setCursorVisible(true);
                    p2_a4.setClickable(true);
                    p2_a4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP2 = 4;
                            abreDialogoP2();
                        }
                    });
                }

                if (atualP2 == 5) {
                    p2_a5.setCursorVisible(true);
                    p2_a5.setClickable(true);
                    p2_a5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            corrigirP2 = 5;
                            abreDialogoP2();
                        }
                    });
                }
            }
        });





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calibragem);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(/*usuarioLogado.getValue().getEMAIL()*/"a");

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
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
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
                if (checaMaquinaCalibracao(posicaoMaquina) == true) caixaAlertaMaquina();
                else {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityCalibragem.this)
                            .setTitle("Concluir")
                            .setMessage("Deseja Concluir a Calibragem?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (posicaoImplemento == 0) posicaoImplemento++;
                                    if (posicaoMaquina == 0) posicaoMaquina++;
                                    if (posicaoOperador == 0) posicaoOperador++;

                                    ViewModelCALIBRAGEM_SUBSOLAGEM viewModelCALIBRAGEM_subsolagem = new ViewModelCALIBRAGEM_SUBSOLAGEM(getApplication());


                                    List<MAQUINA_IMPLEMENTO> listaMI = dao.listaMaquinaImplemento();
                                    int id = listaMI.size();
                                    id++;

                                    MAQUINA_IMPLEMENTO maquinaImplemento = new MAQUINA_IMPLEMENTO(id, posicaoMaquina, posicaoImplemento);
                                    dao.insert(maquinaImplemento);

                                    Double mediaP1;
                                    Double mediaP2;
                                    Double desvioP1;
                                    Double desvioP2;

                                    mediaP1 = Double.valueOf(p1Media.getText().toString());
                                    mediaP2 = Double.valueOf(p2Media.getText().toString());
                                    desvioP1 = Double.valueOf(desvioProduto1.getText().toString());
                                    desvioP2 = Double.valueOf(desvioProduto2.getText().toString());

                                    CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = new CALIBRAGEM_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                                            DateFormat.format("dd/MM/yyyy", new Date()).toString(), checaTurno(), id,
                                            posicaoOperador, mediaP1, desvioP1, mediaP2, desvioP2);

                                    viewModelCALIBRAGEM_subsolagem.insert(calibragem_subsolagem);

                                    Intent it = new Intent(ActivityCalibragem.this, ActivityContinuarOs.class);
                                    startActivity(it);
                                }
                            }).

                                    setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).create();
                    dialog.show();
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


    //Abre caixa de diálogo para preencher amostras do produto 1
    public void abreDialogoP1() {
        todosConformeP1 = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibragem, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_calibragem);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_calbragem);

        p1_a1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String[] s = new String[2];
                String aux;
                aux = String.valueOf(valor.getText());

                int cont = 0;

                if (aux.isEmpty()) valor.setError("Digite 3 Casas Decimais!");
                if (!aux.isEmpty() && aux.length() < 5) valor.setError("Digite 3 Casas Decimais!");
                //else if (!aux.isEmpty() && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                if (!aux.isEmpty() && aux.length() >= 5) {
                    for (int i = 0; i < aux.length(); i++) {
                        char[] c = aux.toCharArray();
                        if (c[i] == ',') cont++;
                    }
                }
                if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1) {
                    s = aux.split("\\,");
                    if (s[1].length() < 3 || s[1].length() > 3) {
                        valor.setError("Digite 3 Casas Decimais!");
                    }

                    if (s[1].length() == 3) {
                        valorCorreto = valor.getText().toString();
                        valorCorreto = valorCorreto.replace(',', '.');

                        if (corrigirP1 == 0) {
                            if (atualP1 == 1) {
                                amostrasP1[0] = Double.valueOf(valorCorreto);
                                p1_a1.setText(valorCorreto);
                            }
                            if (atualP1 == 2) {
                                amostrasP1[1] = Double.valueOf(valorCorreto);
                                p1_a2.setText(valorCorreto);
                            }
                            if (atualP1 == 3) {
                                amostrasP1[2] = Double.valueOf(valorCorreto);
                                p1_a3.setText(valorCorreto);
                            }
                            if (atualP1 == 4) {
                                amostrasP1[3] = Double.valueOf(valorCorreto);
                                p1_a4.setText(valorCorreto);
                            }
                            if (atualP1 == 5) {
                                amostrasP1[4] = Double.valueOf(valorCorreto);
                                p1_a5.setText(valorCorreto);
                            }
                            atualP1++;
                        }

                        if (s[1].length() == 3 && corrigirP1 != 0) {
                            valorCorreto = valor.getText().toString();
                            valorCorreto = valorCorreto.replace(',', '.');


                            if (corrigirP1 == 1) {
                                amostrasP1[0] = Double.valueOf(valorCorreto);
                                p1_a1.setText(valorCorreto);
                            }
                            if (corrigirP1 == 2) {
                                amostrasP1[1] = Double.valueOf(valorCorreto);
                                p1_a2.setText(valorCorreto);
                            }
                            if (corrigirP1 == 3) {
                                amostrasP1[2] = Double.valueOf(valorCorreto);
                                p1_a3.setText(valorCorreto);
                            }
                            if (corrigirP1 == 4) {
                                amostrasP1[3] = Double.valueOf(valorCorreto);
                                p1_a4.setText(valorCorreto);
                            }
                            if (corrigirP1 == 5) {
                                amostrasP1[4] = Double.valueOf(valorCorreto);
                                p1_a5.setText(valorCorreto);
                            }
                        }
                        dialog.dismiss();
                    }
                } else valor.setError("Digite 3 Casas Decimais!");


                //testando p1

                    if (diferencaPercentual(amostrasP1[0], amostrasP1[1]) > 5.00 || diferencaPercentual(amostrasP1[0], amostrasP1[1]) < -5.00) {
                        dif1_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;
                    } else {
                        dif1_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[0], amostrasP1[1]).isInfinite()) || diferencaPercentual(amostrasP1[0], amostrasP1[1]).isNaN()) )
                        dif1_p1.setText("");
                    else
                        dif1_p1.setText(String.valueOf(((diferencaPercentual(amostrasP1[0], amostrasP1[1])))));


                    if (diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.00 || diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.00) {
                        dif2_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;
                    } else {
                        dif2_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[1], amostrasP1[2]).isNaN() || diferencaPercentual(amostrasP1[1], amostrasP1[2]).isInfinite())) )
                        dif2_p1.setText("");
                    else
                        dif2_p1.setText(String.valueOf(((diferencaPercentual(amostrasP1[1], amostrasP1[2])))));


                    if (diferencaPercentual(amostrasP1[2], amostrasP1[3]) >  5.00 || diferencaPercentual(amostrasP1[2], amostrasP1[3]) < -5.00) {
                        dif3_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;

                    } else {
                        dif3_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[2], amostrasP1[3]).isInfinite() || diferencaPercentual(amostrasP1[2], amostrasP1[3]).isNaN())))
                        dif3_p1.setText("");
                    else
                        dif3_p1.setText(String.valueOf(((diferencaPercentual(amostrasP1[2], amostrasP1[3])))));


                    if (diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.00 || diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.00) {
                        dif4_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;

                    } else {
                        dif4_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[3], amostrasP1[4]).isInfinite() || diferencaPercentual(amostrasP1[3], amostrasP1[4]).isNaN())))
                        dif4_p1.setText("");
                    else
                        dif4_p1.setText(String.valueOf(((diferencaPercentual(amostrasP1[3], amostrasP1[4])))));



                //testando p2
                if (diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.00 || diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.00) {
                    todosConformeP2 = false;
                }
                if (diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.00 || diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.00) {
                    todosConformeP2 = false;
                }
                if (diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.00 || diferencaPercentual(amostrasP2[2], amostrasP2[3]) < -5.00) {
                    todosConformeP2 = false;
                }
                if (diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.00 || diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.00) {
                    todosConformeP2 = false;
                }


                mediaPercentualp1 = (
                        diferencaPercentual(amostrasP1[0], amostrasP1[1]) +
                                diferencaPercentual(amostrasP1[1], amostrasP1[2]) +
                                diferencaPercentual(amostrasP1[2], amostrasP1[3]) +
                                diferencaPercentual(amostrasP1[3], amostrasP1[4])) / 4;

                Double maiorDif = 0.0;
                int posicaoMaiorDif = 0;

                if (atualP1 > 5) {
                    for (int i = 0; i < 4; i++) {
                        if (Math.abs(diferencaPercentual(amostrasP1[i], amostrasP1[i + 1])) > maiorDif) {
                            maiorDif = Math.abs(diferencaPercentual(amostrasP1[i], amostrasP1[i + 1]));
                            posicaoMaiorDif = i + 1;
                        }
                    }

                    if (maiorDif > 5.0) {
                        if (posicaoMaiorDif == 1) {
                            p1_a2.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif1_p1.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 2) {
                            p1_a3.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif2_p1.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 3){
                            p1_a4.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif3_p1.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 4) {
                            p1_a5.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif4_p1.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                mediaGeralp1 = (amostrasP1[0] + amostrasP1[1] + amostrasP1[2] + amostrasP1[3] + amostrasP1[4]) / 5;
                mediaGeralp1 = arredonda3Casas(mediaGeralp1);

                if (mediaPercentualp1.isInfinite() || mediaPercentualp1.isNaN()) mediaDifP1.setText("");
                else {
                    mediaDifP1.setText(String.valueOf((arredonda2Casas(mediaPercentualp1))));
                }

                p1Media.setText(String.valueOf((mediaGeralp1)));
                mediaProduto1.setText(String.valueOf((mediaGeralp1)));
                Double aux1 = arredonda2Casas(desvioPadrao(amostrasP1));
                desvioProduto1.setText(String.valueOf((aux1)));

                botaoConfirma.setVisibility(View.INVISIBLE);

                if(atualP1>5 && atualP2>5 && todosConformeP1 == true && todosConformeP2 == true) {
                    botaoConfirma.setVisibility(View.VISIBLE);
                    pulseAnimation(botaoConfirma);
                }
            }
        });
    }


    //Abre caixa de diálogo para preencher amostras do produto
    public void abreDialogoP2() {
        todosConformeP2 = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibragem, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_calibragem);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_calbragem);

        p2_a1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p2_a2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p2_a3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p2_a4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p2_a5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String[] s = new String[2];
                String aux;
                aux = String.valueOf(valor.getText());

                int cont = 0;

                if (aux.isEmpty()) valor.setError("Digite 3 Casas Decimais!");
                if (!aux.isEmpty() && aux.length() < 5) valor.setError("Digite 3 Casas Decimais!");
                //else if (!aux.isEmpty() && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                if (!aux.isEmpty() && aux.length() >= 5) {
                    for (int i = 0; i < aux.length(); i++) {
                        char[] c = aux.toCharArray();
                        if (c[i] == ',') cont++;
                    }
                }
                if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1) {
                    s = aux.split("\\,");
                    if (s[1].length() < 3 || s[1].length() > 3) {
                        valor.setError("Digite 3 Casas Decimais!");
                    }

                    if (s[1].length() == 3) {
                        valorCorreto = valor.getText().toString();
                        valorCorreto = valorCorreto.replace(',', '.');

                        if (corrigirP2 == 0) {
                            if (atualP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                p2_a1.setText(valorCorreto);
                            }
                            if (atualP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                p2_a2.setText(valorCorreto);
                            }
                            if (atualP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                p2_a3.setText(valorCorreto);
                            }
                            if (atualP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                p2_a4.setText(valorCorreto);
                            }
                            if (atualP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                p2_a5.setText(valorCorreto);
                            }
                            atualP2++;
                        }

                        if (s[1].length() == 3 && corrigirP2 != 0) {
                            valorCorreto = valor.getText().toString();
                            valorCorreto = valorCorreto.replace(',', '.');


                            if (corrigirP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                p2_a1.setText(valorCorreto);
                            }
                            if (corrigirP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                p2_a2.setText(valorCorreto);
                            }
                            if (corrigirP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                p2_a3.setText(valorCorreto);
                            }
                            if (corrigirP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                p2_a4.setText(valorCorreto);
                            }
                            if (corrigirP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                p2_a5.setText(valorCorreto);
                            }
                        }
                        dialog.dismiss();
                    }
                } else valor.setError("Digite 3 Casas Decimais!");


                    if (diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.00 || diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.00) {
                        dif1_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;
                    } else {
                        dif1_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[0], amostrasP2[1]).isInfinite() || diferencaPercentual(amostrasP2[0], amostrasP2[1]).isNaN())))
                        dif1_p2.setText("");
                    else
                        dif1_p2.setText(String.valueOf(((diferencaPercentual(amostrasP2[0], amostrasP2[1])))));


                    if (diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.00 || diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.00) {
                        dif2_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif2_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[1], amostrasP2[2]).isInfinite() || diferencaPercentual(amostrasP2[1], amostrasP2[2]).isNaN())))
                        dif2_p2.setText("");
                    else
                        dif2_p2.setText(String.valueOf(((diferencaPercentual(amostrasP2[1], amostrasP2[2])))));


                    if (diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.00 || diferencaPercentual(amostrasP2[2], amostrasP2[3]) < -5.00) {
                        dif3_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif3_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[2], amostrasP2[3]).isNaN() || diferencaPercentual(amostrasP2[2], amostrasP2[3]).isInfinite())))
                        dif3_p2.setText("");
                    else
                        dif3_p2.setText(String.valueOf(((diferencaPercentual(amostrasP2[2], amostrasP2[3])))));


                    if (diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.00 || diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.00) {
                        dif4_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif4_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[3], amostrasP2[4]).isNaN() || diferencaPercentual(amostrasP2[3], amostrasP2[4]).isInfinite())))
                        dif4_p2.setText("");
                    else
                        dif4_p2.setText(String.valueOf(((diferencaPercentual(amostrasP2[3], amostrasP2[4])))));

                //testando p1

                if (diferencaPercentual(amostrasP1[0], amostrasP1[1]) > 5.00 || diferencaPercentual(amostrasP1[0], amostrasP1[1]) < -5.00) {
                    todosConformeP1 = false;
                }

                if (diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.00 || diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.00) {
                    todosConformeP1 = false;
                }

                if (diferencaPercentual(amostrasP1[2], amostrasP1[3]) > 5.00 || diferencaPercentual(amostrasP1[2], amostrasP1[3]) < -5.00) {
                    todosConformeP1 = false;
                }

                if (diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.00 || diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.00) {
                    todosConformeP1 = false;
                }

                mediaPercentualp2 = (
                        diferencaPercentual(amostrasP2[0], amostrasP2[1]) +
                                diferencaPercentual(amostrasP2[1], amostrasP2[2]) +
                                diferencaPercentual(amostrasP2[2], amostrasP2[3]) +
                                diferencaPercentual(amostrasP2[3], amostrasP2[4])) / 4;

                Double maiorDif = 0.0;
                int posicaoMaiorDif = 0;

                if (atualP2 > 5) {
                    for (int i = 0; i < 4; i++) {
                        if (Math.abs(diferencaPercentual(amostrasP2[i], amostrasP2[i + 1])) > maiorDif) {
                            maiorDif = Math.abs(diferencaPercentual(amostrasP2[i], amostrasP2[i + 1]));
                            posicaoMaiorDif = i + 1;
                        }
                    }

                    if (maiorDif > 5.0) {
                        if (posicaoMaiorDif == 1) {
                            p2_a2.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif1_p2.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 2) {
                            p2_a3.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif2_p2.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 3){
                            p2_a4.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif3_p2.setTextColor(Color.parseColor("#FF0000"));
                        }else if (posicaoMaiorDif == 4) {
                            p2_a5.setBackgroundColor(Color.parseColor("#FF0000"));
                            dif4_p2.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                mediaGeralp2 = (amostrasP2[0] + amostrasP2[1] + amostrasP2[2] + amostrasP2[3] + amostrasP2[4]) / 5;
                mediaGeralp2 = arredonda3Casas(mediaGeralp2);

                if ((mediaPercentualp2).isNaN() || mediaPercentualp2.isInfinite()) mediaDifP2.setText("");
                else {

                    mediaDifP2.setText(String.valueOf((arredonda2Casas(mediaPercentualp2))));
                }

                p2Media.setText(String.valueOf((mediaGeralp2)));
                mediaProduto2.setText(String.valueOf((mediaGeralp2)));
                Double aux1 = arredonda2Casas(desvioPadrao(amostrasP2));
                desvioProduto2.setText(String.valueOf((aux1)));

                botaoConfirma.setVisibility(View.INVISIBLE);

                if (atualP1>5 && atualP2>5 &&todosConformeP1 == true && todosConformeP2 == true) {
                    botaoConfirma.setVisibility(View.VISIBLE);
                    pulseAnimation(botaoConfirma);
                }
            }
        });
    }

    //Checa se há uma calibração para a máquina selecionada na data e turno atuais
    //Parâmetro de entrada: Descrição da máquina selecionada
    public boolean checaMaquinaCalibracao(int s){
        int checagem = dao.joinCalibragemMaquinaImplemento(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                DateFormat.format("dd/MM/yyyy", new Date()).toString(), checaTurno(),s);
        if(checagem != NULL) return true;
        return false;
    }

    //Cria uma animação pulsante no botão de conclusão da calibragem
    public void pulseAnimation(ImageButton btnObj) {
        objAnim = ObjectAnimator.ofPropertyValuesHolder(btnObj, PropertyValuesHolder.ofFloat("scaleX", 1.5f), PropertyValuesHolder.ofFloat("scaleY", 1.5f));
        objAnim.setDuration(300);
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);
        objAnim.setRepeatMode(ObjectAnimator.REVERSE);
        objAnim.start();
    }


    //Arredonda um número do tipo double para que tenha 1 casa decimal
    //Parâmetro de entrada: um Double
    private static Double arredonda1Casa(Double media) {
        DecimalFormat df = new DecimalFormat(".0");
        return Double.valueOf(df.format(media));
    }


    //Arredonda um número do tipo double para que tenha  casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda2Casas(Double media) {
        DecimalFormat df = new DecimalFormat(".00");
        return Double.valueOf(df.format(media));
    }


    //Arredonda um número do tipo double para que tenha 3 casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda3Casas(Double media) {
        DecimalFormat df = new DecimalFormat(".000");
        return Double.valueOf(df.format(media));
    }

    //formula= (1-(amostra atual/amostra anterior))/100
    //Calcula a diferença percentual entre dois números do tipo Double
    //Parâmetro de entrada: um Double
    private static Double diferencaPercentual(Double anterior, Double atual) {
        Double calculo =  ((anterior - atual) / anterior) * 100.0;//(1-(atual/anterior))/100;
        DecimalFormat df = new DecimalFormat(".00");
        return Double.valueOf(df.format(calculo));
    }


    //Calcula o desvio padrão de um array do tipo Double
    //Parâmetro de entrada: um array do tipo Double
    public static Double desvioPadrao(Double[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Null 'data' array.");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Zero length 'data' array.");
        }
        Double avg = calculaMedia(data, false);
        Double sum = 0.0;

        for (int counter = 0; counter < data.length; counter++) {
            Double diff = Double.valueOf(data[counter]) - avg;
            sum = sum + diff * diff;
        }
        return Math.sqrt(sum / (data.length - 1));
    }

    //Calcula a média entre os números em um array do tipo Double
    //Parâmetro de entrada: um array do tipo Double
    public static Double calculaMedia(Double[] values, boolean includeNullAndNaN) {

        if (values == null) {
            throw new IllegalArgumentException("Null 'values' argument.");
        }
        Double sum = 0.0;
        Double current;
        int counter = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Double.valueOf(current = values[i]);
            } else {
                current = Double.NaN;
            }
            if (includeNullAndNaN || !Double.isNaN(current)) {
                sum = sum + current;
                counter++;
            }
        }
        Double result = (sum / counter);
        return result;
    }

    //Checa se o turno atual é manhã ou tarde baseado na hora atual
    public static String checaTurno() {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String horaAtual = DateFormat.format("HH:mm", new Date()).toString();
        String meioDia = "12:00";
        try {
            Date date1 = sdf.parse(horaAtual);
            Date date2 = sdf.parse(meioDia);

            if (date1.before(date2)) {
                return "Manha";
            } else {
                return "Tarde";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Erro";
    }

    //Mostra uma caixa de alerta caso a máquina selecionada já foi calibrada no turno atual
    public void caixaAlertaMaquina(){
        AlertDialog dialog = new AlertDialog.Builder(ActivityCalibragem.this)
                .setTitle("ERRO")
                .setMessage(repositorioMaquinas.selecionaMaquina(posicaoMaquina).toString()+" JÁ CALIBRADA NO TURNO ATUAL!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }

    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}