package com.example.forestsys.Activities;

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
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorCalibracao;

import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.Classes.IMPLEMENTOS;
import com.example.forestsys.Classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.R;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.Calculadora.CalculadoraMain;

import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.MAQUINAS;
import com.example.forestsys.Classes.OPERADORES;
import com.example.forestsys.Classes.Joins.Join_MAQUINA_IMPLEMENTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;

import static java.sql.Types.NULL;

/*
 * Activity responsavel por mostrar a tela de calibração e fazer suas interações
 */
public class ActivityCalibracao extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private TextView P1_a1;
    private TextView P1_a2;
    private TextView P1_a3;
    private TextView P1_a4;
    private TextView P1_a5;

    private TextView P1Media;


    private TextView P2_a1;
    private TextView P2_a2;
    private TextView P2_a3;
    private TextView P2_a4;
    private TextView P2_a5;

    private TextView P2Media;


    private TextView dif1_P1;
    private TextView dif2_P1;
    private TextView dif3_P1;
    private TextView dif4_P1;
    private TextView mediaDifP1;

    private TextView dif1_P2;
    private TextView dif2_P2;
    private TextView dif3_P2;
    private TextView dif4_P2;
    private TextView mediaDifP2;

    private TextView desvioP1;
    private TextView desvioP2;


    private NDSpinner spinnerOperador;
    private NDSpinner spinnerMaquina;
    private NDSpinner spinnerImplemento;

    private TextView osTalhao;
    private TextView osData;
    private TextView osTurno;

    private FloatingActionButton botaoMediaP1;
    private FloatingActionButton botaoMediaP2;
    private Button botaoConfirma;
    private ImageButton botaoVoltar;

    private boolean todosConformeP1;
    private boolean todosConformeP2;

    private ObjectAnimator objAnim;

    private int atualP2;
    private int atualP1;
    private int corrigirP1;
    private int corrigirP2;

    private double[] amostrasP1;
    private double[] amostrasP2;


    private Double mediaPercentualP1;
    private Double mediaGeralP1;

    private Double mediaPercentualP2;
    private Double mediaGeralP2;

    private String valorCorreto;

    private BaseDeDados baseDeDados;
    private DAO dao;

    private RecyclerView recyclerView;
    private AdaptadorCalibracao adaptador;
    private List<CALIBRAGEM_SUBSOLAGEM> calibragens;

    private Integer idMaquina;
    private Integer idOperador;
    private Integer idImplemento;

    private int contSpinnerOperador;
    private int contSpinnerMaquina;
    private int contSpinnerImplemento;

    private TextView nomeProduto1;
    private TextView nomeProduto2;

    private ArrayAdapter<OPERADORES> adapterOperadores;
    private ArrayAdapter<MAQUINAS> adapterMaquinas;
    private ArrayAdapter<Join_MAQUINA_IMPLEMENTO> adapterImplemento;

    private ArrayList<MAQUINAS> maquinas;
    private ArrayList<OPERADORES> operadores;
    private ArrayList<Join_MAQUINA_IMPLEMENTO> implementos;

    private int posicaoOperador;
    private int posicaoMaquina;
    private int posicaoImplemento;

    private boolean mudouOrientacao;
    private Bundle auxSavedInstanceState = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auxSavedInstanceState = savedInstanceState;

        try {
            inicializacao();
        } catch (Exception e) {
            e.printStackTrace();
            Intent it = new Intent(ActivityCalibracao.this, ActivityAtividades.class);
            it.putExtra("erroAbrirCalibracao", true);
            startActivity(it);
        }

        if (savedInstanceState != null) {
            P1_a1.setText(savedInstanceState.getString("p1_a1"));
            P1_a2.setText(savedInstanceState.getString("p1_a2"));
            P1_a3.setText(savedInstanceState.getString("p1_a3"));
            P1_a4.setText(savedInstanceState.getString("p1_a4"));
            P1_a5.setText(savedInstanceState.getString("p1_a5"));

            P2_a1.setText(savedInstanceState.getString("p2_a1"));
            P2_a2.setText(savedInstanceState.getString("p2_a2"));
            P2_a3.setText(savedInstanceState.getString("p2_a3"));
            P2_a4.setText(savedInstanceState.getString("p2_a4"));
            P2_a5.setText(savedInstanceState.getString("p2_a5"));

            dif1_P1.setText(savedInstanceState.getString("dif1_p1"));
            dif2_P1.setText(savedInstanceState.getString("dif2_p1"));
            dif3_P1.setText(savedInstanceState.getString("dif3_p1"));
            dif4_P1.setText(savedInstanceState.getString("dif4_p1"));

            dif1_P2.setText(savedInstanceState.getString("dif1_p2"));
            dif2_P2.setText(savedInstanceState.getString("dif2_p2"));
            dif3_P2.setText(savedInstanceState.getString("dif3_p2"));
            dif4_P2.setText(savedInstanceState.getString("dif4_p2"));

            P1Media.setText(savedInstanceState.getString("p1Media"));
            P2Media.setText(savedInstanceState.getString("p2Media"));

            mediaDifP1.setText(savedInstanceState.getString("mediaDifP1"));
            mediaDifP2.setText(savedInstanceState.getString("mediaDifP2"));

            desvioP1.setText(savedInstanceState.getString("desvioProduto1"));
            desvioP2.setText(savedInstanceState.getString("desvioProduto2"));

            atualP1 = savedInstanceState.getInt("atualP1");
            atualP2 = savedInstanceState.getInt("atualP2");
            corrigirP1 = savedInstanceState.getInt("corrigirP1");
            corrigirP2 = savedInstanceState.getInt("corrigirP2");

            mediaPercentualP1 = savedInstanceState.getDouble("mediaPercentualp1");
            mediaPercentualP2 = savedInstanceState.getDouble("mediaPercentualp2");
            mediaGeralP1 = savedInstanceState.getDouble("mediaGeralp1");
            mediaGeralP2 = savedInstanceState.getDouble("mediaGeralp2");

            amostrasP1 = savedInstanceState.getDoubleArray("amostrasP1");
            amostrasP2 = savedInstanceState.getDoubleArray("amostrasP2");

            idOperador = savedInstanceState.getInt("idOperador");
            idMaquina = savedInstanceState.getInt("idMaquina");
            idImplemento = savedInstanceState.getInt("idImplemento");

            posicaoOperador = savedInstanceState.getInt("posicaoOperador");
            posicaoImplemento = savedInstanceState.getInt("posicaoImplemento");
            posicaoMaquina = savedInstanceState.getInt("posicaoMaquina");

            if (idMaquina != -1) {
                spinnerMaquina.setAdapter(adapterMaquinas);
                spinnerMaquina.setSelection(posicaoMaquina);
            }


            if (idOperador != -1) {
                spinnerOperador.setAdapter(adapterOperadores);
                spinnerOperador.setSelection(posicaoOperador);
            }
            if (idOperador != -1)
                mudouOrientacao = true;

            testaCliqueP1();
            testaCliqueP2();

            if (atualP1 > 5) testaP1();
            if (atualP2 > 5) testaP2();
        }
    }

    /*
     * Método chamado ao clicar no botão "+" para o produto 1
     * Usado para abrir o diálogo onde será digitado o valor da amostra atual
    */
    public void cliqueP1() {
        corrigirP1 = 0;
        corrigirP2 = 0;
        testaCliqueP1();
        if (atualP1 <= 5) abreDialogoP1();
    }

    /*
     * Método responsável por liberar a edição das amostras no produto 1
     * A liberação é feita quando a amostra já foi preenchida anteriormente
    */
    public void testaCliqueP1() {
        if (atualP1 >= 2) {
            P1_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 1;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 >= 3) {
            P1_a2.setCursorVisible(true);
            P1_a2.setClickable(true);
            P1_a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 2;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 >= 4) {
            P1_a3.setCursorVisible(true);
            P1_a3.setClickable(true);
            P1_a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 3;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 >= 5) {
            P1_a4.setCursorVisible(true);
            P1_a4.setClickable(true);
            P1_a4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 4;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 > 5) {
            P1_a5.setCursorVisible(true);
            P1_a5.setClickable(true);
            P1_a5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 5;
                    abreDialogoP1();
                }
            });
        }
    }

    /*
     * Método chamado ao clicar no botão "+" para o produto 2
     * Usado para abrir o diálogo onde será digitado o valor da amostra atual
     */
    public void cliqueP2() {
        corrigirP1 = 0;
        corrigirP2 = 0;
        testaCliqueP2();
        if (atualP2 <= 5) abreDialogoP2();
    }

    /*
     * Método responsável por liberar a edição das amostras no produto 2
     * A liberação é feita quando a amostra já foi preenchida anteriormente
     */
    public void testaCliqueP2() {
        if (atualP2 >= 2) {
            P2_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 1;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 >= 3) {
            P2_a2.setCursorVisible(true);
            P2_a2.setClickable(true);
            P2_a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 2;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 >= 4) {
            P2_a3.setCursorVisible(true);
            P2_a3.setClickable(true);
            P2_a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 3;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 >= 5) {
            P2_a4.setCursorVisible(true);
            P2_a4.setClickable(true);
            P2_a4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 4;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 > 5) {
            P2_a5.setCursorVisible(true);
            P2_a5.setClickable(true);
            P2_a5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 5;
                    abreDialogoP2();

                }
            });
        }
    }

    /*
     * Método responsável por abrir a caixa de diálogo perguntando se o usuário deseja sair da tela e avisando
     * que ele perderá o que não foi salvo
    */
    public void dialogoFechar() {
        AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                .setTitle("Voltar Para a Tela da Atividade?")
                .setMessage("Caso clique em sim, a calibração em andamento será descartada.")
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

    /*
     * Método usado para verificar se algum dos campos na tela já foi preenchido pelo usuário
     * Retorna: False se não houver nenhum item preenchido ou true se houver
    */
    public boolean algumItemPreenchido() {
        if (osSelecionada.getSTATUS_NUM() == 2) return false;
        if (P1_a1.getText().toString().length() == 0 && P2_a1.getText().toString().length() == 0 && idImplemento == -1 && idMaquina == -1
                && idOperador == -1) return false;

        return true;
    }

    /*
     * Sobrescrita do método de seleção de item do menu de navegação localizado na lateral da tela
    */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                if (algumItemPreenchido() == false) {
                    Intent it = new Intent(ActivityCalibracao.this, ActivityDashboard.class);
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityCalibracao.this, ActivityDashboard.class);
                        startActivity(it);
                    } else {
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
                    }
                }
                break;

            case R.id.atividades:
                if (algumItemPreenchido() == false) {
                    Intent it = new Intent(ActivityCalibracao.this, ActivityMain.class);
                    startActivity(it);
                } else {
                    if (osSelecionada.getSTATUS_NUM() == 2) {
                        Intent it = new Intent(ActivityCalibracao.this, ActivityMain.class);
                        startActivity(it);
                    } else {
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

    /*
     * Método responsável por abrir a caixa de diálogo para preencher amostras do produto 1
    */
    public void abreDialogoP1() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibracao, null);
        final EditText valorP1 = mView.findViewById(R.id.valor_dialogo_calibragem);
        valorP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ferramentas.mascaraVirgula(valorP1, s, 3, "99.999", count, before);
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });

        final TextView numAmostra = mView.findViewById(R.id.dialogo_calib_amostra);
        Button botaoOk = mView.findViewById(R.id.botao_ok_dialogo_calbragem);
        if (corrigirP1 == 0) numAmostra.setText(String.valueOf(atualP1));
        else numAmostra.setText(String.valueOf(corrigirP1));

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] s = new String[2];
                String aux;
                aux = String.valueOf(valorP1.getText());
                boolean naoPermiteCentena = false;
                int cont = 0;

                if (aux.length() == 0) valorP1.setError("Valor não pode ser vazio!");
                if (aux.length() > 0 && !aux.contains(",") && aux.length() > 2) {
                    naoPermiteCentena = true;
                }
                if (aux.length() > 0 && !aux.contains(",")) {
                    aux = aux.concat(",000");

                }

                if (aux.length() > 0 && aux.length() < 5 && aux.contains(","))
                    valorP1.setError("Digite 3 Casas Decimais!");

                //else if (aux.length()>0 && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                if (aux.length() > 0 && aux.length() >= 5) {
                    for (int i = 0; i < aux.length(); i++) {
                        char[] c = aux.toCharArray();
                        if (c[i] == ',') cont++;
                    }
                }

                boolean valorZerado = false;
                if (aux.length() > 0) {
                    double d = Double.valueOf(aux.replace(',', '.'));
                    if (d == 0) {
                        valorZerado = true;
                        valorP1.setError("Não permitido valor zerado");
                    }
                }

                if (aux.length() > 0 && aux.length() >= 5 && aux.contains(",") &&
                        cont == 1 && naoPermiteCentena == false && valorZerado == false) {
                    s = aux.split("\\,");
                    if (s[1].length() < 3 || s[1].length() > 3) {
                        valorP1.setError("Digite 3 Casas Decimais!");
                    }
                    if (s[1].length() == 3) {
                        valorCorreto = aux;
                        valorCorreto = valorCorreto.replace(',', '.');

                        if (corrigirP1 == 0) {
                            if (atualP1 == 1) {
                                amostrasP1[0] = Double.valueOf(valorCorreto);
                                P1_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP1 == 2) {
                                amostrasP1[1] = Double.valueOf(valorCorreto);
                                P1_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP1 == 3) {
                                amostrasP1[2] = Double.valueOf(valorCorreto);
                                P1_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP1 == 4) {
                                amostrasP1[3] = Double.valueOf(valorCorreto);
                                P1_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP1 == 5) {
                                amostrasP1[4] = Double.valueOf(valorCorreto);
                                P1_a5.setText(valorCorreto.replace('.', ','));
                            }
                            atualP1++;
                            cliqueP1();
                        }

                        if (s[1].length() == 3 && corrigirP1 != 0) {
                            valorCorreto = aux;
                            valorCorreto = valorCorreto.replace(',', '.');


                            if (corrigirP1 == 1) {
                                amostrasP1[0] = Double.valueOf(valorCorreto);
                                P1_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP1 == 2) {
                                amostrasP1[1] = Double.valueOf(valorCorreto);
                                P1_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP1 == 3) {
                                amostrasP1[2] = Double.valueOf(valorCorreto);
                                P1_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP1 == 4) {
                                amostrasP1[3] = Double.valueOf(valorCorreto);
                                P1_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP1 == 5) {
                                amostrasP1[4] = Double.valueOf(valorCorreto);
                                P1_a5.setText(valorCorreto.replace('.', ','));
                            }
                        }
                        dialog.dismiss();
                    }
                }
                if (naoPermiteCentena == true)
                    valorP1.setError("Valor incorreto: Permitido unidades e dezenas.");
                if (atualP1 > 5) testaP1();
                if (atualP2 > 5) testaP2();
            }
        });
    }

    /*
     * Método responsável por verificr as regras de conformidade para o produto 1
    */
    public void testaP1() {

        todosConformeP1 = true;
        if (ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1]) > 5.00 || ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1]) < -5.00) {
            dif1_P1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;
        } else {
            dif1_P1.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1]).isInfinite()) || ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1]).isNaN()))
            dif1_P1.setText("");
        else
            dif1_P1.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.00 || ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.00) {
            dif2_P1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;
        } else {
            dif2_P1.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2]).isNaN() || ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2]).isInfinite())))
            dif2_P1.setText("");
        else
            dif2_P1.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3]) > 5.00 || ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3]) < -5.00) {
            dif3_P1.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP1 = false;
        } else {
            dif3_P1.setTextColor(Color.parseColor("#FF1A9C1A"));

        }
        if (((ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3]).isInfinite() || ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3]).isNaN())))
            dif3_P1.setText("");
        else
            dif3_P1.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.00 || ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.00) {
            dif4_P1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;

        } else {
            dif4_P1.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4]).isInfinite() || ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4]).isNaN())))
            dif4_P1.setText("");
        else
            dif4_P1.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4])))).replace('.', ','));


        mediaPercentualP1 = (
                ferramentas.diferencaPercentual(amostrasP1[0], amostrasP1[1]) +
                        ferramentas.diferencaPercentual(amostrasP1[1], amostrasP1[2]) +
                        ferramentas.diferencaPercentual(amostrasP1[2], amostrasP1[3]) +
                        ferramentas.diferencaPercentual(amostrasP1[3], amostrasP1[4])) / 4;

        Double maiorDif = 0.0;
        int posicaoMaiorDif = 0;

        if (atualP1 >= 5) {
            for (int i = 0; i < 4; i++) {
                if (Math.abs(ferramentas.diferencaPercentual(amostrasP1[i], amostrasP1[i + 1])) > maiorDif) {
                    maiorDif = Math.abs(ferramentas.diferencaPercentual(amostrasP1[i], amostrasP1[i + 1]));
                    posicaoMaiorDif = i + 1;
                }
            }

            if (maiorDif > 5.0) {
                if (posicaoMaiorDif == 1) {
                    dif1_P1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 2) {
                    dif2_P1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 3) {
                    dif3_P1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 4) {
                    dif4_P1.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }

        mediaGeralP1 = (amostrasP1[0] + amostrasP1[1] + amostrasP1[2] + amostrasP1[3] + amostrasP1[4]) / 5;
        mediaGeralP1 = arredonda3Casas(mediaGeralP1);

        if (mediaPercentualP1.isInfinite() || mediaPercentualP1.isNaN()) mediaDifP1.setText("");
        else {
            mediaDifP1.setText(String.valueOf((-1 * arredonda1Casa(mediaPercentualP1))).replace('.', ','));
        }

        P1Media.setText(String.valueOf((mediaGeralP1)).replace('.', ','));
        Double aux1 = arredonda2Casas(desvioPadrao(amostrasP1));
        desvioP1.setText(String.valueOf((aux1)).replace('.', ','));
    }

    /*
     * Método responsável por verificr as regras de conformidade para o produto 2
    */
    public void testaP2() {

        todosConformeP2 = true;
        if (ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.00 || ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.00) {
            dif1_P2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;

        } else {
            dif1_P2.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1]).isInfinite()) || ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1]).isNaN()))
            dif1_P2.setText("");
        else
            dif1_P2.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.00 || ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.00) {
            dif2_P2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;
        } else {
            dif2_P2.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2]).isNaN() || ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2]).isInfinite())))
            dif2_P2.setText("");
        else
            dif2_P2.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.00 || ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3]) < -5.00) {
            dif3_P2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;
        } else {
            dif3_P2.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3]).isInfinite() || ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3]).isNaN())))
            dif3_P2.setText("");
        else
            dif3_P2.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3])))).replace('.', ','));


        if (ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.00 || ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.00) {
            dif4_P2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;
        } else {
            dif4_P2.setTextColor(Color.parseColor("#FF1A9C1A"));
        }
        if (((ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4]).isInfinite() || ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4]).isNaN())))
            dif4_P2.setText("");
        else
            dif4_P2.setText(String.valueOf(-1 * arredonda1Casa((ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4])))).replace('.', ','));

        mediaPercentualP2 = (
                ferramentas.diferencaPercentual(amostrasP2[0], amostrasP2[1]) +
                        ferramentas.diferencaPercentual(amostrasP2[1], amostrasP2[2]) +
                        ferramentas.diferencaPercentual(amostrasP2[2], amostrasP2[3]) +
                        ferramentas.diferencaPercentual(amostrasP2[3], amostrasP2[4])) / 4;

        Double maiorDif = 0.0;
        int posicaoMaiorDif = 0;

        if (atualP2 >= 5) {
            for (int i = 0; i < 4; i++) {
                if (Math.abs(ferramentas.diferencaPercentual(amostrasP2[i], amostrasP2[i + 1])) > maiorDif) {
                    maiorDif = Math.abs(ferramentas.diferencaPercentual(amostrasP2[i], amostrasP2[i + 1]));
                    posicaoMaiorDif = i + 1;
                }
            }

            if (maiorDif > 5.0) {
                if (posicaoMaiorDif == 1) {
                    dif1_P2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 2) {
                    dif2_P2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 3) {
                    dif3_P2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 4) {
                    dif4_P2.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }

        mediaGeralP2 = (amostrasP2[0] + amostrasP2[1] + amostrasP2[2] + amostrasP2[3] + amostrasP2[4]) / 5;
        mediaGeralP2 = arredonda3Casas(mediaGeralP2);

        if (mediaPercentualP2.isInfinite() || mediaPercentualP2.isNaN()) mediaDifP2.setText("");
        else {
            mediaDifP2.setText(String.valueOf((-1 * arredonda1Casa(mediaPercentualP2))).replace('.', ','));
        }

        P2Media.setText(String.valueOf((mediaGeralP2)).replace('.', ','));
        Double aux1 = arredonda2Casas(desvioPadrao(amostrasP2));
        desvioP2.setText(String.valueOf((aux1)).replace('.', ','));
    }

    /*
     * Método responsável por abrir a caixa de diálogo para preencher amostras do produto 1
    */
    public void abreDialogoP2() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialogo_calibracao, null);
        final EditText valorP2 = mView.findViewById(R.id.valor_dialogo_calibragem);
        final TextView numAmostra = mView.findViewById(R.id.dialogo_calib_amostra);
        valorP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ferramentas.mascaraVirgula(valorP2, s, 3, "99.999", count, before);
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
        if (corrigirP2 == 0) numAmostra.setText(String.valueOf(atualP2));
        else numAmostra.setText(String.valueOf(corrigirP2));

        Button botaoOk = mView.findViewById(R.id.botao_ok_dialogo_calbragem);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String[] s = new String[2];
                String aux;
                aux = String.valueOf(valorP2.getText());
                boolean naoPermiteCentena = false;
                int cont = 0;

                if (aux.length() == 0) valorP2.setError("Valor não pode ser vazio!");
                if (aux.length() > 0 && !aux.contains(",") && aux.length() > 2) {
                    naoPermiteCentena = true;
                }
                if (aux.length() > 0 && !aux.contains(",")) {
                    aux = aux.concat(",000");
                }

                if (aux.length() > 0 && aux.length() < 5 && aux.contains(","))
                    valorP2.setError("Digite 3 Casas Decimais!");
                //else if (aux.length()>0 && aux.length() > 5) valor.setError("Digite 3 Casas Decimais!");
                if (aux.length() > 0 && aux.length() >= 5) {
                    for (int i = 0; i < aux.length(); i++) {
                        char[] c = aux.toCharArray();
                        if (c[i] == ',') cont++;
                    }
                }

                boolean valorZerado = false;
                if (aux.length() > 0) {
                    double d = Double.valueOf(aux.replace(',', '.'));
                    if (d == 0) {
                        valorZerado = true;
                        valorP2.setError("Não permitido valor zerado");
                    }
                }

                if (aux.length() > 0 && aux.length() >= 5 && aux.contains(",") &&
                        cont == 1 && naoPermiteCentena == false && valorZerado == false) {
                    s = aux.split("\\,");
                    if (s[1].length() < 3 || s[1].length() > 3) {
                        valorP2.setError("Digite 3 Casas Decimais!");
                    }

                    if (s[1].length() == 3) {
                        valorCorreto = aux;
                        valorCorreto = valorCorreto.replace(',', '.');

                        if (corrigirP2 == 0) {
                            if (atualP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                P2_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                P2_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                P2_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                P2_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (atualP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                P2_a5.setText(valorCorreto.replace('.', ','));
                            }
                            atualP2++;
                            cliqueP2();
                        }

                        if (s[1].length() == 3 && corrigirP2 != 0) {
                            valorCorreto = aux;
                            valorCorreto = valorCorreto.replace(',', '.');


                            if (corrigirP2 == 1) {
                                amostrasP2[0] = Double.valueOf(valorCorreto);
                                P2_a1.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 2) {
                                amostrasP2[1] = Double.valueOf(valorCorreto);
                                P2_a2.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 3) {
                                amostrasP2[2] = Double.valueOf(valorCorreto);
                                P2_a3.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 4) {
                                amostrasP2[3] = Double.valueOf(valorCorreto);
                                P2_a4.setText(valorCorreto.replace('.', ','));
                            }
                            if (corrigirP2 == 5) {
                                amostrasP2[4] = Double.valueOf(valorCorreto);
                                P2_a5.setText(valorCorreto.replace('.', ','));
                            }
                        }
                        dialog.dismiss();
                    }
                }
                if (naoPermiteCentena == true)
                    valorP2.setError("Valor incorreto: Permitido unidade e dezena.");

                if (atualP2 > 5) testaP2();
                if (atualP1 > 5) testaP1();
            }
        });
    }

    /*
     * Método responsável por checar se há uma calibração para a máquina e implemento selecionados
     na data e turno atuais
     * Parâmetro de entrada: idMaquinaImplemento
     * retorna: False se não há calibração para a maquina e implemento selecionados ou true se há uma calibração
    */
    public boolean checaMaquinaImplemento(Integer idMaquinaImplemento) {

        Integer checagem = dao.checaMaquinaImplemento(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                ferramentas.formataDataDb(ferramentas.dataAtual()), checaTurno(), idMaquinaImplemento);
        if (checagem != null) {
            if (checagem > 0) return true;
        }
        return false;
    }


    /*
     * Método responsável por arredondar um número do tipo Double para que tenha 1 casa decimal
     * Parâmetro de entrada: um Double
     * Retorna: Um tipo Double com somente uma casa decimal
    */
    private static Double arredonda1Casa(Double media) {
        DecimalFormat df = new DecimalFormat(".#");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    /*
     * Método responsável por arredondar um número do tipo Double para que tenha 2 casas decimais
     * Parâmetro de entrada: um Double
     * Retorna: Um tipo Double com somente duas casas decimais
     */
    private static Double arredonda2Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    /*
     * Método responsável por arredondar um número do tipo Double para que tenha 3 casas decimais
     * Parâmetro de entrada: um Double
     * Retorna: Um tipo Double com somente três casas decimais
     */
    private static Double arredonda3Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.###");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }



    /*
     * Método responsável por calcular o desvio padrão de um array do tipo Double
     * Parâmetro de entrada: um array do tipo Double
     * Retorna: O desvio padrão
    */
    public static Double desvioPadrao(double[] data) {
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

    /*
     * Método responsável por calcular a média entre os números em um array do tipo Double
     * Parâmetro de entrada: um array do tipo Double e um boolean auxiliar para verificar se o array é valido
     * Retorna: A média entre os valores contidos no array
    */
    public static Double calculaMedia(double[] valores, boolean incluirNullNan) {
        if (valores == null) {
            throw new IllegalArgumentException("Valores nulos");
        }
        Double soma = 0.0;
        Double atual;
        int cont = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] != NULL) {
                Double.valueOf(atual = valores[i]);
            } else {
                atual = Double.NaN;
            }
            if (incluirNullNan || !Double.isNaN(atual)) {
                soma += atual;
                cont++;
            }
        }
        Double resultado = (soma / cont);
        return resultado;
    }

    /*
     * Método responsável por checar se o turno atual é manhã ou tarde baseado na hora atual
     * Retorna: Uma string contendo o turno atual
    */
    public static String checaTurno() {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String horaAtual = DateFormat.format("HH:mm", new Date()).toString();
        String meioDia = "12:00";
        try {
            Date date1 = sdf.parse(horaAtual);
            Date date2 = sdf.parse(meioDia);

            if (date1.before(date2)) {
                return "MANHA";
            } else {
                return "TARDE";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Erro";
    }

    /*
     * Método responsável por mostrar uma caixa de alerta caso a máquina selecionada já foi calibrada no turno atual
    */
    public void caixaAlertaMaquinaImplemento() {
        AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                .setTitle("ERRO")
                .setMessage("Conjunto já calibrado no turno atual")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }

    /*
     * Método responsável por testar as conformidades para liberar o botão de confirmação
     * Retorna: True se todos os campos estão conforme ou false caso contrário
    */
    public boolean testaConfirmacao() {
        if (atualP1 >= 5 && atualP2 >= 5) {
            testaP1();
            testaP2();
            if (todosConformeP1 == true && todosConformeP2 == true) {
                if (idOperador != -1 && idMaquina != -1 && idImplemento != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Método responsável por inicializar todos os itens na tela e seta valores de variáveis
    */
    @SuppressLint("ResourceAsColor")
    public void inicializacao() {
        setContentView(R.layout.activity_calibracao);
        setTitle(nomeEmpresaPref);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        amostrasP1 = new double[5];
        amostrasP2 = new double[5];

        todosConformeP1 = false;
        todosConformeP2 = false;

        nomeProduto1 = findViewById(R.id.calibracao_produto1);
        nomeProduto2 = findViewById(R.id.calibracao_produto2);

        spinnerOperador = findViewById(R.id.spinner_operador_calibragem);
        spinnerMaquina = findViewById(R.id.spinner_maquina_calibragem);
        spinnerImplemento = findViewById(R.id.spinner_implemento_calibragem);

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

        desvioP1 = findViewById(R.id.desvio_produto1);
        desvioP2 = findViewById(R.id.desvio_produto2);

        P1_a1 = findViewById(R.id.p1_amostra_1);
        P1_a2 = findViewById(R.id.p1_amostra_2);
        P1_a3 = findViewById(R.id.p1_amostra_3);
        P1_a4 = findViewById(R.id.p1_amostra_4);
        P1_a5 = findViewById(R.id.p1_amostra_5);
        P1Media = findViewById(R.id.p1_media);

        P2_a1 = findViewById(R.id.p2_amostra_1);
        P2_a2 = findViewById(R.id.p2_amostra_2);
        P2_a3 = findViewById(R.id.p2_amostra_3);
        P2_a4 = findViewById(R.id.p2_amostra_4);
        P2_a5 = findViewById(R.id.p2_amostra_5);
        P2Media = findViewById(R.id.p2_media);

        dif1_P1 = findViewById(R.id.dif1_p1);
        dif2_P1 = findViewById(R.id.dif2_p1);
        dif3_P1 = findViewById(R.id.dif3_p1);
        dif4_P1 = findViewById(R.id.dif4_p1);
        mediaDifP1 = findViewById(R.id.media_dif_p1);

        dif1_P2 = findViewById(R.id.dif1_p2);
        dif2_P2 = findViewById(R.id.dif2_p2);
        dif3_P2 = findViewById(R.id.dif3_p2);
        dif4_P2 = findViewById(R.id.dif4_p2);
        mediaDifP2 = findViewById(R.id.media_dif_p2);

        botaoConfirma = findViewById(R.id.botao_calibragem_confirma);
        botaoVoltar = findViewById(R.id.botao_calibragem_voltar);

        atualP1 = 1;
        atualP2 = 1;

        corrigirP1 = 0;
        corrigirP2 = 0;

        idOperador = -1;
        idMaquina = -1;
        idImplemento = -1;

        contSpinnerOperador = 0;
        contSpinnerMaquina = 0;
        contSpinnerImplemento = 0;

        mediaGeralP1 = 0.0;
        mediaPercentualP1 = 0.0;
        mediaGeralP2 = 0.0;
        mediaPercentualP2 = 0.0;

        nomeProduto1.setText(joinOsInsumos.get(0).getDESCRICAO());
        nomeProduto2.setText(joinOsInsumos.get(1).getDESCRICAO());

        osTalhao.setText(osSelecionada.getTALHAO());

        botaoMediaP1 = findViewById(R.id.botao_calibragem_media_p1);

        botaoMediaP2 = findViewById(R.id.botao_calibragem_media_p2);

        osData.setText(ferramentas.dataAtual());

        osTurno.setText(checaTurno());

        P1_a1.setFocusable(false);
        P2_a1.setFocusable(false);

        maquinas = new ArrayList<>(dao.listaMaquinas());

        operadores = new ArrayList<>(dao.todosOperadores());

        adapterOperadores = new ArrayAdapter<OPERADORES>(this,
                android.R.layout.simple_spinner_item, operadores);
        adapterOperadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMaquinas = new ArrayAdapter<MAQUINAS>(this,
                android.R.layout.simple_spinner_item, maquinas);
        adapterMaquinas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerOperador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerOperador == 0) spinnerOperador.setAdapter(adapterOperadores);
                return false;
            }
        });


        spinnerMaquina.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerMaquina == 0) spinnerMaquina.setAdapter(adapterMaquinas);
                return false;
            }
        });

        spinnerImplemento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerImplemento == 0)
                    spinnerImplemento.setAdapter(adapterImplemento);
                return false;
            }
        });

        spinnerOperador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerOperador > 0) {
                    idOperador = operadores.get(position).getID_OPERADORES();
                    posicaoOperador = position;
                }
                contSpinnerOperador++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerMaquina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerImplemento.setAdapter(null);
                if (contSpinnerMaquina > 0) {
                    idMaquina = maquinas.get(position).getID_MAQUINA();
                    contSpinnerImplemento = 0;
                    idImplemento = -1;
                    implementos = new ArrayList<>(dao.listaJoinMaquinaImplemento(maquinas.get(position).getID_MAQUINA()));
                    adapterImplemento = new ArrayAdapter<Join_MAQUINA_IMPLEMENTO>(ActivityCalibracao.this,
                            android.R.layout.simple_spinner_item, implementos);
                    adapterImplemento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    posicaoMaquina = position;

                    if (contSpinnerMaquina == 1 && auxSavedInstanceState != null) {
                        idMaquina = auxSavedInstanceState.getInt("idMaquina");
                        idImplemento = auxSavedInstanceState.getInt("idImplemento");
                        posicaoMaquina = auxSavedInstanceState.getInt("posicaoMaquina");
                        posicaoImplemento = auxSavedInstanceState.getInt("posicaoImplemento");

                        if (idImplemento != -1) {
                            spinnerImplemento.setAdapter(adapterImplemento);
                            spinnerImplemento.setSelection(posicaoImplemento);
                        }
                    }
                }
                contSpinnerMaquina++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerImplemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerImplemento > 0) {
                    Integer posicao = position;
                    if (checaMaquinaImplemento(implementos.get(posicao).getID_MAQUINA_IMPLEMENTO()) == true) {
                        spinnerImplemento.setAdapter(null);
                        contSpinnerImplemento = -1;
                        idImplemento = -1;
                        caixaAlertaMaquinaImplemento();
                    } else {
                        idImplemento = implementos.get(position).getID_MAQUINA_IMPLEMENTO();
                        posicaoImplemento = position;
                    }
                }
                contSpinnerImplemento++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int i = 0; i < 5; i++) {
            amostrasP1[i] = 0.0;
            amostrasP2[i] = 0.0;
        }

        if (osSelecionada.getSTATUS_NUM() == 2) {
            botaoMediaP1.setEnabled(false);
            botaoMediaP2.setEnabled(false);
            botaoConfirma.setEnabled(false);

            spinnerMaquina.setEnabled(false);
            spinnerOperador.setEnabled(false);
            spinnerImplemento.setEnabled(false);

            botaoMediaP1.setVisibility(GONE);
            botaoMediaP2.setVisibility(GONE);
            botaoConfirma.setVisibility(GONE);
        }

        P1_a1.setClickable(false);

        P1_a2.setClickable(false);

        P1_a3.setClickable(false);

        P1_a4.setClickable(false);

        P1_a5.setClickable(false);


        P2_a1.setClickable(false);

        P2_a2.setClickable(false);

        P2_a3.setClickable(false);

        P2_a4.setClickable(false);

        P2_a5.setClickable(false);


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
                if (osSelecionada.getSTATUS_NUM() == 2) {
                    Intent it = new Intent(ActivityCalibracao.this, ActivityAtividades.class);
                    startActivity(it);
                } else {
                    if (algumItemPreenchido() == true) dialogoFechar();
                    else {
                        Intent it = new Intent(ActivityCalibracao.this, ActivityAtividades.class);
                        startActivity(it);
                    }
                }
            }
        });

        botaoConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checaMaquinaImplemento(idImplemento) == true)
                    caixaAlertaMaquinaImplemento();
                else {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                            .setTitle("Concluir")
                            .setMessage("Deseja Concluir a Calibração?")
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (testaConfirmacao() == true) {
                                        Double mediaP1;
                                        Double mediaP2;
                                        Double desvioProd1;
                                        Double desvioProd2;

                                        mediaP1 = Double.valueOf(P1Media.getText().toString().replace(',', '.'));
                                        mediaP2 = Double.valueOf(P2Media.getText().toString().replace(',', '.'));
                                        desvioProd1 = Double.valueOf(ActivityCalibracao.this.desvioP1.getText().toString().replace(',', '.'));
                                        desvioProd2 = Double.valueOf(desvioP2.getText().toString().replace(',', '.'));

                                        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = new CALIBRAGEM_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                                                ferramentas.formataDataDb(ferramentas.dataAtual()), checaTurno(), idImplemento,
                                                idOperador, mediaP1, desvioProd1, mediaP2, desvioProd2);

                                        boolean inseriu = false;
                                        try {
                                            dao.insert(calibragem_subsolagem);
                                            inseriu = true;
                                        } catch (SQLiteConstraintException | NullPointerException ex) {
                                            inseriu = false;
                                            AlertDialog dialogoErro = new AlertDialog.Builder(ActivityCalibracao.this)
                                                    .setTitle("Erro")
                                                    .setMessage("Houve um problema ao salvar a calibração.")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                        }
                                                    }).create();
                                            dialogoErro.show();
                                        }

                                        if (osSelecionada.getSTATUS_NUM() == 0) {
                                            osSelecionada.setSTATUS("Andamento");
                                            osSelecionada.setSTATUS_NUM(1);
                                            osSelecionada.setDATA_INICIAL(ferramentas.formataDataDb(ferramentas.dataAtual()));

                                            osSelecionada.setUPDATED_AT(ferramentas.dataHoraMinutosSegundosAtual());
                                            dao.update(osSelecionada);
                                        }

                                        if (inseriu == true) {

                                            MAQUINA_IMPLEMENTO maquinaImplemento = dao.selecionaMaquinaImplemento(idImplemento);
                                            MAQUINAS maquinas = dao.selecionaMaquina(maquinaImplemento.getID_MAQUINA());
                                            IMPLEMENTOS implementos = dao.selecionaImplemento(maquinaImplemento.getID_IMPLEMENTO());
                                            OPERADORES operador = dao.selecionaOperador(idOperador);
                                            try {
                                                FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                                                        usuarioLogado.getEMAIL(), "Calibração", "Inseriu",
                                                        String.valueOf("OS: " + osSelecionada.getID_PROGRAMACAO_ATIVIDADE()
                                                                + " | Talhão: " + osSelecionada.getTALHAO()
                                                                + " | Maquina: " + maquinas.getDESCRICAO()
                                                                + " | Operador: " + operador.getDESCRICAO()
                                                                + " | Implemento: " + implementos.getDESCRICAO()
                                                                + " | P1A1: " + P1_a1.getText().toString()
                                                                + " | P1A2: " + P1_a2.getText().toString()
                                                                + " | P1A3: " + P1_a3.getText().toString()
                                                                + " | P1A4: " + P1_a4.getText().toString()
                                                                + " | P1A5: " + P1_a5.getText().toString()
                                                                + " | DesvioP1: " + desvioP1.getText().toString()
                                                                + " | MediaP1: " + P1Media.getText().toString()
                                                                + " | P2A1: " + P2_a1.getText().toString()
                                                                + " | P2A2: " + P2_a2.getText().toString()
                                                                + " | P2A3: " + P2_a3.getText().toString()
                                                                + " | P2A4: " + P2_a4.getText().toString()
                                                                + " | P2A5: " + P2_a5.getText().toString()
                                                                + " | DesvioP2: " + desvioP2.getText().toString()
                                                                + " | MediaP2: " + P2Media.getText().toString()).replace('.', ','));
                                                dao.insert(registroLog);
                                            } catch (Exception e) {
                                                //Log.wtf("Erro ao registrar log", "Print stack");
                                                e.printStackTrace();
                                            }
                                        }
                                        Toast.makeText(getApplicationContext(), "Calibração Salva com sucesso!", Toast.LENGTH_LONG).show();
                                        Intent it = new Intent(ActivityCalibracao.this, ActivityCalibracao.class);
                                        startActivity(it);
                                    } else {
                                        AlertDialog dialogoErro = new AlertDialog.Builder(ActivityCalibracao.this)
                                                .setTitle("Erro")
                                                .setMessage("É necessário preencher informações incompletas ou corrigir um ou mais erros na calibração antes de prosseguir. \n" +
                                                        "Favor checar todos os itens antes de tentar concluir a calibração novamente.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                    }
                                                }).create();
                                        dialogoErro.show();

                                        if (P1_a1.length() == 0) {
                                            P1_a1.setError("");
                                        } else {
                                            P1_a1.setError(null);
                                        }

                                        if (P1_a2.length() == 0) {
                                            P1_a2.setError("");
                                        } else {
                                            P1_a2.setError(null);
                                        }

                                        if (P1_a3.length() == 0) {
                                            P1_a3.setError("");
                                        } else {
                                            P1_a3.setError(null);
                                        }

                                        if (P1_a4.length() == 0) {
                                            P1_a4.setError("");
                                        } else {
                                            P1_a4.setError(null);
                                        }

                                        if (P1_a5.length() == 0) {
                                            P1_a5.setError("");
                                        } else {
                                            P1_a5.setError(null);
                                        }


                                        if (P2_a1.length() == 0) {
                                            P2_a1.setError("");
                                        } else {
                                            P2_a1.setError(null);
                                        }

                                        if (P2_a2.length() == 0) {
                                            P2_a2.setError("");
                                        } else {
                                            P2_a2.setError(null);
                                        }

                                        if (P2_a3.length() == 0) {
                                            P2_a3.setError("");
                                        } else {
                                            P2_a3.setError(null);
                                        }

                                        if (P2_a4.length() == 0) {
                                            P2_a4.setError("");
                                        } else {
                                            P2_a4.setError(null);
                                        }

                                        if (P2_a5.length() == 0) {
                                            P2_a5.setError("");
                                        } else {
                                            P2_a5.setError(null);
                                        }


                                        TextView textImplemento = findViewById(R.id.calib_text_implemento);
                                        TextView textMaquina = findViewById(R.id.calib_text_maquina);
                                        TextView textOperador = findViewById(R.id.calib_text_operador);

                                        if (idImplemento == -1) {
                                            textImplemento.setError("");
                                        } else {
                                            textImplemento.setError(null);
                                        }

                                        if (idMaquina == -1) {
                                            textMaquina.setError("");
                                        } else {
                                            textMaquina.setError(null);
                                        }

                                        if (idOperador == -1) {
                                            textOperador.setError("");
                                        } else {
                                            textOperador.setError(null);
                                        }
                                    }
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

        mudouOrientacao = false;
    }

    /*
     * Salva uma instância da tela para reconstrução ao alterar entre modo paisagem e retrato ou vice-versa
    */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("p1_a1", P1_a1.getText().toString());
        outState.putString("p1_a2", P1_a2.getText().toString());
        outState.putString("p1_a3", P1_a3.getText().toString());
        outState.putString("p1_a4", P1_a4.getText().toString());
        outState.putString("p1_a5", P1_a5.getText().toString());

        outState.putString("p2_a1", P2_a1.getText().toString());
        outState.putString("p2_a2", P2_a2.getText().toString());
        outState.putString("p2_a3", P2_a3.getText().toString());
        outState.putString("p2_a4", P2_a4.getText().toString());
        outState.putString("p2_a5", P2_a5.getText().toString());

        outState.putString("dif1_p1", dif1_P1.getText().toString());
        outState.putString("dif2_p1", dif2_P1.getText().toString());
        outState.putString("dif3_p1", dif3_P1.getText().toString());
        outState.putString("dif4_p1", dif4_P1.getText().toString());

        outState.putString("dif1_p2", dif1_P2.getText().toString());
        outState.putString("dif2_p2", dif2_P2.getText().toString());
        outState.putString("dif3_p2", dif3_P2.getText().toString());
        outState.putString("dif4_p2", dif4_P2.getText().toString());

        outState.putDoubleArray("amostrasP1", amostrasP1);
        outState.putDoubleArray("amostrasP2", amostrasP2);

        outState.putString("p1Media", P1Media.getText().toString());
        outState.putString("p2Media", P2Media.getText().toString());

        outState.putString("mediaDifP1", mediaDifP1.getText().toString());
        outState.putString("mediaDifP2", mediaDifP2.getText().toString());


        outState.putString("desvioProduto1", desvioP1.getText().toString());
        outState.putString("desvioProduto2", desvioP2.getText().toString());

        outState.putInt("atualP1", atualP1);
        outState.putInt("atualP2", atualP2);
        outState.putInt("corrigirP1", corrigirP1);
        outState.putInt("corrigirP2", corrigirP2);

        outState.putDouble("mediaGeralp1", mediaGeralP1);
        outState.putDouble("mediaGeralp2", mediaGeralP2);
        outState.putDouble("mediaPercentualp1", mediaPercentualP1);
        outState.putDouble("mediaPercentualp2", mediaPercentualP2);

        outState.putInt("idMaquina", idMaquina);
        outState.putInt("idImplemento", idImplemento);
        outState.putInt("idOperador", idOperador);

        outState.putInt("posicaoImplemento", posicaoImplemento);
        outState.putInt("posicaoMaquina", posicaoMaquina);
        outState.putInt("posicaoOperador", posicaoOperador);
    }

    /*
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}