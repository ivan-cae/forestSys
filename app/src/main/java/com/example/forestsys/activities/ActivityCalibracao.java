package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorCalibracao;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.NDSpinner;
import com.example.forestsys.R;
import com.example.forestsys.calculadora.i.CalculadoraMain;

import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.OPERADORES;
import com.example.forestsys.classes.join.Join_MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.forestsys.activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static java.sql.Types.NULL;

public class ActivityCalibracao extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    private TextView mediaDifP1;

    private TextView dif1_p2;
    private TextView dif2_p2;
    private TextView dif3_p2;
    private TextView dif4_p2;
    private TextView mediaDifP2;

    private TextView desvioProduto1;
    private TextView desvioProduto2;


    private NDSpinner spinnerOperador;
    private NDSpinner spinnerMaquinaImplemento;

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
    private int corrigirP1 = 0;
    private int corrigirP2 = 0;

    private Double[] amostrasP1 = new Double[5];
    private Double[] amostrasP2 = new Double[5];


    private Double mediaPercentualp1;
    private Double mediaGeralp1;

    private Double mediaPercentualp2;
    private Double mediaGeralp2;

    private String valorCorreto;

    private BaseDeDados baseDeDados;
    private DAO dao;

    private DataHoraAtual dataHoraAtual;

    private RecyclerView recyclerView;
    private AdaptadorCalibracao adaptador;
    private List<CALIBRAGEM_SUBSOLAGEM> calibragens;

    private int idMaquinaImplemento=-1;
    private int posicaoOperador=-1;
    private int contSpinnerOperador;
    private int contSpinnerMaquinaImplemento;

    private List<Join_OS_INSUMOS> joinOsCalibracaoInsumos;
    private TextView nomeProduto1;
    private TextView nomeProduto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializacao();
    }

    public void cliqueP1(){
        corrigirP1 = 0;
        corrigirP2 = 0;

        if (atualP1 <= 5) abreDialogoP1();

        if (atualP1 == 2) {
            p1_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 1;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 == 3) {
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

        if (atualP1 == 4) {
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

        if (atualP1 == 5) {
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

        if (atualP1 > 5) {
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

    public void cliqueP2(){
        corrigirP1 = 0;
        corrigirP2 = 0;


        if (atualP2 <= 5) abreDialogoP2();

        if (atualP2 == 2) {
            p2_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 1;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 == 3) {
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

        if (atualP2 == 4) {
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

        if (atualP2 == 5) {
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

        if (atualP2 > 5) {
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

    //Abre a caixa de diálogo perguntando se o usuário deseja sair da tela e avisando que ele perderá o que não foi salvo
                public void dialogoFechar(){
            AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                    .setTitle("Voltar Para a Tela da Atividade?")
                    .setMessage("Caso clique em SIM, você perderá os dados da calibração!")
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent it = new Intent(ActivityCalibracao.this, ActivityAtividades.class);
                            startActivity(it);
                        }
                    }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
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
                AlertDialog dialogoDash = new AlertDialog.Builder(ActivityCalibracao.this)
                        .setTitle("Abrir a Dashboard?")
                        .setMessage("Caso clique em SIM, você perderá os dados da calibração!")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent it = new Intent(ActivityCalibracao.this, ActivityDashboard.class);
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
                AlertDialog dialogoAtividades = new AlertDialog.Builder(ActivityCalibracao.this)
                        .setTitle("Voltar Para a Listagem de Atividades?")
                        .setMessage("Caso clique em SIM, você perderá os dados da calibração!")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent it = new Intent(ActivityCalibracao.this, ActivityMain.class);
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


    //Abre caixa de diálogo para preencher amostras do produto 1
    public void abreDialogoP1() {
        todosConformeP1 = true;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibracao, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_calibragem);
        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input = s.toString();

                if (!input.isEmpty()) {
                    DecimalFormat format = new DecimalFormat("##,###");
                    input = input.replace(",", "");
                    String newPrice = format.format(Double.parseDouble(input));

                    valor.removeTextChangedListener(this);

                    valor.setText(newPrice.replace('.',','));
                    valor.setSelection(newPrice.length());

                    valor.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });

        final TextView numAmostra = mView.findViewById(R.id.dialogo_calib_amostra);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_calbragem);
        if(corrigirP1==0) numAmostra.setText(String.valueOf(atualP1));
        else numAmostra.setText(String.valueOf(corrigirP1));
        p1_a1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        p1_a5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] s = new String[2];
                String aux;
                aux = String.valueOf(valor.getText());
                boolean naoPermiteCentena = false;
                int cont = 0;

                if (aux.isEmpty()) valor.setError("Valor não pode ser vazio!");
                if(!aux.isEmpty() && !aux.contains(",") && aux.length()>2){
                    naoPermiteCentena = true;
                }
                if(!aux.isEmpty() && !aux.contains(",")){
                    aux = aux.concat(",000");
                    Log.e("completa automatico", aux);
                }

                    if (!aux.isEmpty() && aux.length() < 5 && aux.contains(","))
                        valor.setError("Digite 3 Casas Decimais!");
                    //else if (!aux.isEmpty() && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                    if (!aux.isEmpty() && aux.length() >= 5) {
                        for (int i = 0; i < aux.length(); i++) {
                            char[] c = aux.toCharArray();
                            if (c[i] == ',') cont++;
                        }
                    }
                    if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1 && naoPermiteCentena==false) {
                        s = aux.split("\\,");
                        if (s[1].length() < 3 || s[1].length() > 3) {
                            valor.setError("Digite 3 Casas Decimais!");
                        }
                        if (s[1].length() == 3) {
                            valorCorreto = aux;
                            valorCorreto = valorCorreto.replace(',', '.');

                            if (corrigirP1 == 0) {
                                if (atualP1 == 1) {
                                    amostrasP1[0] = Double.valueOf(valorCorreto);
                                    p1_a1.setText(valorCorreto.replace('.', ','));
                                }
                                if (atualP1 == 2) {
                                    amostrasP1[1] = Double.valueOf(valorCorreto);
                                    p1_a2.setText(valorCorreto.replace('.', ','));
                                }
                                if (atualP1 == 3) {
                                    amostrasP1[2] = Double.valueOf(valorCorreto);
                                    p1_a3.setText(valorCorreto.replace('.', ','));
                                }
                                if (atualP1 == 4) {
                                    amostrasP1[3] = Double.valueOf(valorCorreto);
                                    p1_a4.setText(valorCorreto.replace('.', ','));
                                }
                                if (atualP1 == 5) {
                                    amostrasP1[4] = Double.valueOf(valorCorreto);
                                    p1_a5.setText(valorCorreto.replace('.', ','));
                                }
                                atualP1++;
                                cliqueP1();
                            }

                            if (s[1].length() == 3 && corrigirP1 != 0) {
                                valorCorreto = aux;
                                valorCorreto = valorCorreto.replace(',', '.');


                                if (corrigirP1 == 1) {
                                    amostrasP1[0] = Double.valueOf(valorCorreto);
                                    p1_a1.setText(valorCorreto.replace('.', ','));
                                }
                                if (corrigirP1 == 2) {
                                    amostrasP1[1] = Double.valueOf(valorCorreto);
                                    p1_a2.setText(valorCorreto.replace('.', ','));
                                }
                                if (corrigirP1 == 3) {
                                    amostrasP1[2] = Double.valueOf(valorCorreto);
                                    p1_a3.setText(valorCorreto.replace('.', ','));
                                }
                                if (corrigirP1 == 4) {
                                    amostrasP1[3] = Double.valueOf(valorCorreto);
                                    p1_a4.setText(valorCorreto.replace('.', ','));
                                }
                                if (corrigirP1 == 5) {
                                    amostrasP1[4] = Double.valueOf(valorCorreto);
                                    p1_a5.setText(valorCorreto.replace('.', ','));
                                }
                            }
                            dialog.dismiss();
                        }
                    } if(naoPermiteCentena==true) valor.setError("Valor incorreto: Permitido unidade e dezena.");


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
                        dif1_p1.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP1[0], amostrasP1[1])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.00 || diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.00) {
                        dif2_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;
                    } else {
                        dif2_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[1], amostrasP1[2]).isNaN() || diferencaPercentual(amostrasP1[1], amostrasP1[2]).isInfinite())) )
                        dif2_p1.setText("");
                    else
                        dif2_p1.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP1[1], amostrasP1[2])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP1[2], amostrasP1[3]) >  5.00 || diferencaPercentual(amostrasP1[2], amostrasP1[3]) < -5.00) {
                        dif3_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;

                    } else {
                        dif3_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[2], amostrasP1[3]).isInfinite() || diferencaPercentual(amostrasP1[2], amostrasP1[3]).isNaN())))
                        dif3_p1.setText("");
                    else
                        dif3_p1.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP1[2], amostrasP1[3])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.00 || diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.00) {
                        dif4_p1.setTextColor(Color.parseColor("#FF9800"));

                        todosConformeP1 = false;

                    } else {
                        dif4_p1.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP1[3], amostrasP1[4]).isInfinite() || diferencaPercentual(amostrasP1[3], amostrasP1[4]).isNaN())))
                        dif4_p1.setText("");
                    else
                        dif4_p1.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP1[3], amostrasP1[4])))).replace('.', ','));



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
                    mediaDifP1.setText(String.valueOf((-1*arredonda1Casa(mediaPercentualp1))).replace('.', ','));
                }

                p1Media.setText(String.valueOf((mediaGeralp1)).replace('.', ','));
                Double aux1 = arredonda2Casas(desvioPadrao(amostrasP1));
                desvioProduto1.setText(String.valueOf((aux1)).replace('.', ','));

                botaoConfirma.setVisibility(View.INVISIBLE);

                if(atualP1>5 && atualP2>5 && todosConformeP1 == true && todosConformeP2 == true && posicaoOperador!=-1 && idMaquinaImplemento!=-1) {
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
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibracao, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_calibragem);
        final TextView numAmostra = mView.findViewById(R.id.dialogo_calib_amostra);
        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input = s.toString();

                if (!input.isEmpty()) {
                    DecimalFormat format = new DecimalFormat("##,###");
                    input = input.replace(",", "");
                    String newPrice = format.format(Double.parseDouble(input));

                    valor.removeTextChangedListener(this);

                    valor.setText(newPrice.replace('.',','));
                    valor.setSelection(newPrice.length());

                    valor.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
        if(corrigirP2==0) numAmostra.setText(String.valueOf(atualP2));
        else numAmostra.setText(String.valueOf(corrigirP2));

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
                boolean naoPermiteCentena = false;
                int cont = 0;

                if (aux.isEmpty()) valor.setError("Valor não pode ser vazio!");
                if(!aux.isEmpty() && !aux.contains(",") && aux.length()>2){
                    naoPermiteCentena = true;
                }
                if(!aux.isEmpty() && !aux.contains(",")){
                    aux = aux.concat(",000");
                    Log.e("completa automatico", aux);
                }

                if (!aux.isEmpty() && aux.length() < 5 && aux.contains(","))
                    valor.setError("Digite 3 Casas Decimais!");
                //else if (!aux.isEmpty() && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                if (!aux.isEmpty() && aux.length() >= 5) {
                    for (int i = 0; i < aux.length(); i++) {
                        char[] c = aux.toCharArray();
                        if (c[i] == ',') cont++;
                    }
                }
                if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1 && naoPermiteCentena==false) {
                    s = aux.split("\\,");
                    if (s[1].length() < 3 || s[1].length() > 3) {
                        valor.setError("Digite 3 Casas Decimais!");
                    }

                    if (s[1].length() == 3) {
                        valorCorreto = aux;
                        valorCorreto = valorCorreto.replace(',', '.');

                        if (corrigirP2 == 0) {
                            if (atualP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                p2_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                p2_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                p2_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                p2_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                p2_a5.setText(valorCorreto.replace('.', ','));
                            }
                            atualP2++;
                            cliqueP2();
                        }

                        if (s[1].length() == 3 && corrigirP2 != 0) {
                            valorCorreto = aux;
                            valorCorreto = valorCorreto.replace(',', '.');


                            if (corrigirP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                p2_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                p2_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                p2_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                p2_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                p2_a5.setText(valorCorreto.replace('.', ','));
                            }
                        }
                        dialog.dismiss();
                    }
                } if(naoPermiteCentena==true) valor.setError("Valor incorreto: Permitido unidade e dezena.");


                    if (diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.00 || diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.00) {
                        dif1_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;
                    } else {
                        dif1_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[0], amostrasP2[1]).isInfinite() || diferencaPercentual(amostrasP2[0], amostrasP2[1]).isNaN())))
                        dif1_p2.setText("");
                    else
                        dif1_p2.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP2[0], amostrasP2[1])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.00 || diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.00) {
                        dif2_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif2_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[1], amostrasP2[2]).isInfinite() || diferencaPercentual(amostrasP2[1], amostrasP2[2]).isNaN())))
                        dif2_p2.setText("");
                    else
                        dif2_p2.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP2[1], amostrasP2[2])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.00 || diferencaPercentual(amostrasP2[2], amostrasP2[3]) < -5.00) {
                        dif3_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif3_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[2], amostrasP2[3]).isNaN() || diferencaPercentual(amostrasP2[2], amostrasP2[3]).isInfinite())))
                        dif3_p2.setText("");
                    else
                        dif3_p2.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP2[2], amostrasP2[3])))).replace('.', ','));


                    if (diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.00 || diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.00) {
                        dif4_p2.setTextColor(Color.parseColor("#FF9800"));
                        todosConformeP2 = false;

                    } else {
                        dif4_p2.setTextColor(Color.parseColor("#32CD32"));
                    }
                    if (((diferencaPercentual(amostrasP2[3], amostrasP2[4]).isNaN() || diferencaPercentual(amostrasP2[3], amostrasP2[4]).isInfinite())))
                        dif4_p2.setText("");
                    else
                        dif4_p2.setText(String.valueOf(-1*arredonda1Casa((diferencaPercentual(amostrasP2[3], amostrasP2[4])))).replace('.', ','));

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

                    mediaDifP2.setText(String.valueOf((-1*arredonda1Casa(mediaPercentualp2))).replace('.', ','));
                }

                p2Media.setText(String.valueOf((mediaGeralp2)).replace('.', ','));
                Double aux1 = arredonda2Casas(desvioPadrao(amostrasP2));
                desvioProduto2.setText(String.valueOf((aux1)).replace('.', ','));

                botaoConfirma.setVisibility(View.GONE);

                if (atualP1>5 && atualP2>5 &&todosConformeP1 == true && todosConformeP2 == true && posicaoOperador!=-1 && idMaquinaImplemento!=-1) {
                    botaoConfirma.setVisibility(View.VISIBLE);
                    pulseAnimation(botaoConfirma);
                }
            }
        });
    }

    //Checa se há uma calibração para a máquina selecionada na data e turno atuais
    //Parâmetro de entrada: Descrição da máquina selecionada
    public boolean checaMaquinaImplementoCalibracao(int s){
        int checagem = dao.checaMaquinaImplemento(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                dataHoraAtual.dataAtual(), checaTurno(), s);
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
        DecimalFormat df = new DecimalFormat(".#");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    //Arredonda um número do tipo double para que tenha  casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda2Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    //Arredonda um número do tipo double para que tenha 3 casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda3Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.###");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }

    //formula= (1-(amostra atual/amostra anterior))/100
    //Calcula a diferença percentual entre dois números do tipo Double
    //Parâmetro de entrada: um Double
    private static Double diferencaPercentual(Double anterior, Double atual) {
        Double calculo =  (1-(atual/anterior))*100;//((anterior - atual) / anterior) * 100.0
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(calculo).replace(',', '.'));
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
        AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                .setTitle("ERRO")
                .setMessage("MAQUINA JÁ CALIBRADA NO TURNO ATUAL!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }

    public void inicializacao(){
        setContentView(R.layout.activity_calibracao);
        setTitle(nomeEmpresaPref);

        dataHoraAtual = new DataHoraAtual();

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        joinOsCalibracaoInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        nomeProduto1 = findViewById(R.id.calibracao_produto1);
        nomeProduto2 = findViewById(R.id.calibracao_produto2);

        spinnerOperador = findViewById(R.id.spinner_operador_calibragem);
        spinnerMaquinaImplemento = findViewById(R.id.spinner_maquina_implemento_calibragem);

        osData = findViewById(R.id.data_os_calibragem);
        osTalhao = findViewById(R.id.talhao_os_calibragem);
        osTurno = findViewById(R.id.turno_os_calibragem);

        recyclerView = findViewById(R.id.recycler_calibracao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new AdaptadorCalibracao();
        recyclerView.setAdapter(adaptador);

        calibragens = dao.listaCalibragem(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
        adaptador.setCalibragem(calibragens);

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

        nomeProduto1.setText(joinOsCalibracaoInsumos.get(0).getDESCRICAO());
        nomeProduto2.setText(joinOsCalibracaoInsumos.get(1).getDESCRICAO());

        idOs = findViewById(R.id.id_os_calibragem);
        idOs.setText(String.valueOf(osSelecionada.getID_PROGRAMACAO_ATIVIDADE()));

        osTalhao.setText(osSelecionada.getTALHAO());

        botaoMediaP1 = findViewById(R.id.botao_calibragem_media_p1);

        botaoMediaP2 = findViewById(R.id.botao_calibragem_media_p2);

        osData.setText(dataHoraAtual.dataAtual());

        osTurno.setText(checaTurno());

        p1_a1.setFocusable(false);


        ArrayList<Join_MAQUINA_IMPLEMENTO> maquinasImplementos = new ArrayList<>(dao.listaJoinMaquinaImplemento());

        ArrayList<OPERADORES> operadores = new ArrayList<>(dao.todosOperadores());


        ArrayAdapter<Join_MAQUINA_IMPLEMENTO> adapterMaquinaImplementos = new ArrayAdapter<Join_MAQUINA_IMPLEMENTO>(this,
                android.R.layout.simple_spinner_item, maquinasImplementos);
        adapterMaquinaImplementos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaquinaImplemento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(contSpinnerMaquinaImplemento ==0)spinnerMaquinaImplemento.setAdapter(adapterMaquinaImplementos);
                return false;
            }
        });


        ArrayAdapter<OPERADORES> adapterOperadores = new ArrayAdapter<OPERADORES>(this,
                android.R.layout.simple_spinner_item, operadores);
        adapterOperadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(contSpinnerOperador ==0)spinnerOperador.setAdapter(adapterOperadores);
                return false;
            }
        });

        spinnerMaquinaImplemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerMaquinaImplemento > 0){
                    idMaquinaImplemento = maquinasImplementos.get(position).getID_MAQUINA_IMPLEMENTO();
                    if (atualP1>5 && atualP2>5 &&todosConformeP1 == true && todosConformeP2 == true && posicaoOperador!=-1 && idMaquinaImplemento!=-1) {
                        botaoConfirma.setVisibility(View.VISIBLE);
                        pulseAnimation(botaoConfirma);
                    }
                    if (checaMaquinaImplementoCalibracao(maquinasImplementos.get(position).getID_MAQUINA_IMPLEMENTO()) == true)
                        caixaAlertaMaquina();
                }
                contSpinnerMaquinaImplemento ++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spinnerOperador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(contSpinnerOperador>0) {
                    posicaoOperador = operadores.get(position).getID_OPERADORES();
                    if (atualP1>5 && atualP2>5 &&todosConformeP1 == true && todosConformeP2 == true && posicaoOperador!=-1 && idMaquinaImplemento!=-1) {
                        botaoConfirma.setVisibility(View.VISIBLE);
                        pulseAnimation(botaoConfirma);
                    }
                }
                contSpinnerOperador++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
                cliqueP1();
            }
        });


        botaoMediaP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueP2();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calibragem);

        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle(usuarioLogado.getDESCRICAO());

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
                dialogoFechar();
            }
        });

        botaoConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checaMaquinaImplementoCalibracao(idMaquinaImplemento) == true) caixaAlertaMaquina();
                else {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                            .setTitle("Concluir")
                            .setMessage("Deseja Concluir a Calibração?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Double mediaP1;
                                    Double mediaP2;
                                    Double desvioP1;
                                    Double desvioP2;

                                    mediaP1 = Double.valueOf(p1Media.getText().toString().replace(',','.'));
                                    mediaP2 = Double.valueOf(p2Media.getText().toString().replace(',','.'));
                                    desvioP1 = Double.valueOf(desvioProduto1.getText().toString().replace(',','.'));
                                    desvioP2 = Double.valueOf(desvioProduto2.getText().toString().replace(',','.'));

                                    CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = new CALIBRAGEM_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                                            dataHoraAtual.dataAtual(), checaTurno(), idMaquinaImplemento,
                                            posicaoOperador, mediaP1, desvioP1, mediaP2, desvioP2);

                                    dao.insert(calibragem_subsolagem);

                                    osSelecionada.setSTATUS("Andamento");
                                    osSelecionada.setSTATUS_NUM(1);
                                    dao.update(osSelecionada);
                                    Toast.makeText(getApplicationContext(), "Calibração Salva com sucesso!", Toast.LENGTH_LONG).show();
                                    Intent it = new Intent(ActivityCalibracao.this, ActivityCalibracao.class);
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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_calibracao);
inicializacao();
        } else {
            setContentView(R.layout.activity_calibracao);
            inicializacao();
        }
    }

    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}