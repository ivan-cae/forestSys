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
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static com.example.forestsys.Activities.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.Activities.ActivityLogin.usuarioLogado;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static java.sql.Types.NULL;

public class ActivityCalibracao extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

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
    private NDSpinner spinnerMaquina;
    private NDSpinner spinnerMaquinaImplemento;

    private TextView osTalhao;
    private TextView osData;
    private TextView osTurno;

    private ImageButton botaoMediaP1;
    private ImageButton botaoMediaP2;
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


    private Double mediaPercentualp1;
    private Double mediaGeralp1;

    private Double mediaPercentualp2;
    private Double mediaGeralp2;

    private String valorCorreto;

    private BaseDeDados baseDeDados;
    private DAO dao;

    private Ferramentas ferramentas;

    private RecyclerView recyclerView;
    private AdaptadorCalibracao adaptador;
    private List<CALIBRAGEM_SUBSOLAGEM> calibragens;

    private int idMaquina;
    private int idOperador;
    private int idMaquinaImplemento;

    private int contSpinnerOperador;
    private int contSpinnerMaquina;
    private int contSpinnerMaquinaImplemento;

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

        inicializacao();

        if (savedInstanceState != null) {
            p1_a1.setText(savedInstanceState.getString("p1_a1"));
            p1_a2.setText(savedInstanceState.getString("p1_a2"));
            p1_a3.setText(savedInstanceState.getString("p1_a3"));
            p1_a4.setText(savedInstanceState.getString("p1_a4"));
            p1_a5.setText(savedInstanceState.getString("p1_a5"));

            p2_a1.setText(savedInstanceState.getString("p2_a1"));
            p2_a2.setText(savedInstanceState.getString("p2_a2"));
            p2_a3.setText(savedInstanceState.getString("p2_a3"));
            p2_a4.setText(savedInstanceState.getString("p2_a4"));
            p2_a5.setText(savedInstanceState.getString("p2_a5"));

            dif1_p1.setText(savedInstanceState.getString("dif1_p1"));
            dif2_p1.setText(savedInstanceState.getString("dif2_p1"));
            dif3_p1.setText(savedInstanceState.getString("dif3_p1"));
            dif4_p1.setText(savedInstanceState.getString("dif4_p1"));

            dif1_p2.setText(savedInstanceState.getString("dif1_p2"));
            dif2_p2.setText(savedInstanceState.getString("dif2_p2"));
            dif3_p2.setText(savedInstanceState.getString("dif3_p2"));
            dif4_p2.setText(savedInstanceState.getString("dif4_p2"));

            p1Media.setText(savedInstanceState.getString("p1Media"));
            p2Media.setText(savedInstanceState.getString("p2Media"));

            mediaDifP1.setText(savedInstanceState.getString("mediaDifP1"));
            mediaDifP2.setText(savedInstanceState.getString("mediaDifP2"));

            desvioProduto1.setText(savedInstanceState.getString("desvioProduto1"));
            desvioProduto2.setText(savedInstanceState.getString("desvioProduto2"));

            atualP1 = savedInstanceState.getInt("atualP1");
            atualP2 = savedInstanceState.getInt("atualP2");
            corrigirP1 = savedInstanceState.getInt("corrigirP1");
            corrigirP2 = savedInstanceState.getInt("corrigirP2");

            mediaPercentualp1 = savedInstanceState.getDouble("mediaPercentualp1");
            mediaPercentualp2 = savedInstanceState.getDouble("mediaPercentualp2");
            mediaGeralp1 = savedInstanceState.getDouble("mediaGeralp1");
            mediaGeralp2 = savedInstanceState.getDouble("mediaGeralp2");

            amostrasP1 = savedInstanceState.getDoubleArray("amostrasP1");
            amostrasP2 = savedInstanceState.getDoubleArray("amostrasP2");

            idOperador = savedInstanceState.getInt("idOperador");
            idMaquina = savedInstanceState.getInt("idMaquina");
            idMaquinaImplemento = savedInstanceState.getInt("idMaquinaImplemento");

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

    //Método chamado ao clicar no botão "+" para o produto 1
    public void cliqueP1() {
        corrigirP1 = 0;
        corrigirP2 = 0;
        testaCliqueP1();
        if (atualP1 <= 5) abreDialogoP1();
    }

    //Libera a edição das amostras no produto 1
    public void testaCliqueP1() {
        if (atualP1 >= 2) {
            p1_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP1 = 1;
                    abreDialogoP1();
                }
            });
        }

        if (atualP1 >= 3) {
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

        if (atualP1 >= 4) {
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

        if (atualP1 >= 5) {
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

    //Método chamado ao clicar no botão "+" para o produto 2
    public void cliqueP2() {
        corrigirP1 = 0;
        corrigirP2 = 0;
        testaCliqueP2();
        if (atualP2 <= 5) abreDialogoP2();
    }

    //Libera a edição das amostras no produto 2
    public void testaCliqueP2() {
        if (atualP2 >= 2) {
            p2_a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrigirP2 = 1;
                    abreDialogoP2();
                }
            });
        }

        if (atualP2 >= 3) {
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

        if (atualP2 >= 4) {
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

        if (atualP2 >= 5) {
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

    //Retorna false se não houver nenhum item preenchido ou true se houver
    public boolean algumItemPreenchido() {
        if(osSelecionada.getSTATUS_NUM()==2) return false;
        if (p1_a1.getText().toString().isEmpty() && p2_a1.getText().toString().isEmpty() && idMaquinaImplemento == -1 && idMaquina == -1
                && idOperador == -1) return false;

        return true;
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
                if (algumItemPreenchido() == false) {
                    Intent it = new Intent(ActivityCalibracao.this, ActivityDashboard.class);
                    startActivity(it);
                }else{
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
                }else{
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
                }}
                break;
            case R.id.calculadora:
                Intent it3 = new Intent(this, CalculadoraMain.class);
                startActivity(it3);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    //Abre caixa de diálogo para preencher amostras do produto 1
    public void abreDialogoP1() {
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
                    String novoValor = format.format(Double.parseDouble(input));

                    valor.removeTextChangedListener(this);

                    valor.setText(novoValor.replace('.', ','));
                    valor.setSelection(novoValor.length());

                    valor.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });

        final TextView numAmostra = mView.findViewById(R.id.dialogo_calib_amostra);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_calbragem);
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
                aux = String.valueOf(valor.getText());
                boolean naoPermiteCentena = false;
                int cont = 0;

                if (aux.isEmpty()) valor.setError("Valor não pode ser vazio!");
                if (!aux.isEmpty() && !aux.contains(",") && aux.length() > 2) {
                    naoPermiteCentena = true;
                }
                if (!aux.isEmpty() && !aux.contains(",")) {
                    aux = aux.concat(",000");

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
                if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1 && naoPermiteCentena == false) {
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
                }
                if (naoPermiteCentena == true)
                    valor.setError("Valor incorreto: Permitido unidades e dezenas.");
                if (atualP1 > 5) testaP1();
                if (atualP2 > 5) testaP2();
            }
        });
    }

    //Verifica as regras de conformidade para o produto 1
    public void testaP1() {

        if (diferencaPercentual(amostrasP1[0], amostrasP1[1]) > 5.00 || diferencaPercentual(amostrasP1[0], amostrasP1[1]) < -5.00) {
            dif1_p1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;
        } else {
            dif1_p1.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP1 = true;
        }
        if (((diferencaPercentual(amostrasP1[0], amostrasP1[1]).isInfinite()) || diferencaPercentual(amostrasP1[0], amostrasP1[1]).isNaN()))
            dif1_p1.setText("");
        else
            dif1_p1.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP1[0], amostrasP1[1])))).replace('.', ','));


        if (diferencaPercentual(amostrasP1[1], amostrasP1[2]) > 5.00 || diferencaPercentual(amostrasP1[1], amostrasP1[2]) < -5.00) {
            dif2_p1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;
        } else {
            dif2_p1.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP1 = true;
        }
        if (((diferencaPercentual(amostrasP1[1], amostrasP1[2]).isNaN() || diferencaPercentual(amostrasP1[1], amostrasP1[2]).isInfinite())))
            dif2_p1.setText("");
        else
            dif2_p1.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP1[1], amostrasP1[2])))).replace('.', ','));


        if (diferencaPercentual(amostrasP1[2], amostrasP1[3]) > 5.00 || diferencaPercentual(amostrasP1[2], amostrasP1[3]) < -5.00) {
            dif3_p1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;

        } else {
            dif3_p1.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP1 = true;
        }
        if (((diferencaPercentual(amostrasP1[2], amostrasP1[3]).isInfinite() || diferencaPercentual(amostrasP1[2], amostrasP1[3]).isNaN())))
            dif3_p1.setText("");
        else
            dif3_p1.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP1[2], amostrasP1[3])))).replace('.', ','));


        if (diferencaPercentual(amostrasP1[3], amostrasP1[4]) > 5.00 || diferencaPercentual(amostrasP1[3], amostrasP1[4]) < -5.00) {
            dif4_p1.setTextColor(Color.parseColor("#FF0000"));

            todosConformeP1 = false;

        } else {
            dif4_p1.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP1 = true;
        }
        if (((diferencaPercentual(amostrasP1[3], amostrasP1[4]).isInfinite() || diferencaPercentual(amostrasP1[3], amostrasP1[4]).isNaN())))
            dif4_p1.setText("");
        else
            dif4_p1.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP1[3], amostrasP1[4])))).replace('.', ','));


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

        if (atualP1 >= 5) {
            for (int i = 0; i < 4; i++) {
                if (Math.abs(diferencaPercentual(amostrasP1[i], amostrasP1[i + 1])) > maiorDif) {
                    maiorDif = Math.abs(diferencaPercentual(amostrasP1[i], amostrasP1[i + 1]));
                    posicaoMaiorDif = i + 1;
                }
            }

            if (maiorDif > 5.0) {
                if (posicaoMaiorDif == 1) {
                    dif1_p1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 2) {
                    dif2_p1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 3) {
                    dif3_p1.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 4) {
                    dif4_p1.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }

        mediaGeralp1 = (amostrasP1[0] + amostrasP1[1] + amostrasP1[2] + amostrasP1[3] + amostrasP1[4]) / 5;
        mediaGeralp1 = arredonda3Casas(mediaGeralp1);

        if (mediaPercentualp1.isInfinite() || mediaPercentualp1.isNaN()) mediaDifP1.setText("");
        else {
            mediaDifP1.setText(String.valueOf((-1 * arredonda1Casa(mediaPercentualp1))).replace('.', ','));
        }

        p1Media.setText(String.valueOf((mediaGeralp1)).replace('.', ','));
        Double aux1 = arredonda2Casas(desvioPadrao(amostrasP1));
        desvioProduto1.setText(String.valueOf((aux1)).replace('.', ','));
    }

    //Abre caixa de diálogo para preencher amostras do produto
    public void abreDialogoP2() {
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
                    String novoValor = format.format(Double.parseDouble(input));

                    valor.removeTextChangedListener(this);

                    valor.setText(novoValor.replace('.', ','));
                    valor.setSelection(novoValor.length());

                    valor.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
        if (corrigirP2 == 0) numAmostra.setText(String.valueOf(atualP2));
        else numAmostra.setText(String.valueOf(corrigirP2));

        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_calbragem);

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
                if (!aux.isEmpty() && !aux.contains(",") && aux.length() > 2) {
                    naoPermiteCentena = true;
                }
                if (!aux.isEmpty() && !aux.contains(",")) {
                    aux = aux.concat(",000");
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
                if (!aux.isEmpty() && aux.length() >= 5 && aux.contains(",") && cont == 1 && naoPermiteCentena == false) {
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
                }
                if (naoPermiteCentena == true)
                    valor.setError("Valor incorreto: Permitido unidade e dezena.");
                if (atualP1 > 5) testaP1();
                if (atualP2 > 5) testaP2();
            }
        });
    }

    //Verifica as regras de conformidade para o produto 2
    public void testaP2() {


        if (diferencaPercentual(amostrasP2[0], amostrasP2[1]) > 5.00 || diferencaPercentual(amostrasP2[0], amostrasP2[1]) < -5.00) {
            dif1_p2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;
        } else {
            dif1_p2.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP2 = true;
        }
        if (((diferencaPercentual(amostrasP2[0], amostrasP2[1]).isInfinite() || diferencaPercentual(amostrasP2[0], amostrasP2[1]).isNaN())))
            dif1_p2.setText("");
        else
            dif1_p2.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP2[0], amostrasP2[1])))).replace('.', ','));


        if (diferencaPercentual(amostrasP2[1], amostrasP2[2]) > 5.00 || diferencaPercentual(amostrasP2[1], amostrasP2[2]) < -5.00) {
            dif2_p2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;

        } else {
            dif2_p2.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP2 = true;
        }
        if (((diferencaPercentual(amostrasP2[1], amostrasP2[2]).isInfinite() || diferencaPercentual(amostrasP2[1], amostrasP2[2]).isNaN())))
            dif2_p2.setText("");
        else
            dif2_p2.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP2[1], amostrasP2[2])))).replace('.', ','));


        if (diferencaPercentual(amostrasP2[2], amostrasP2[3]) > 5.00 || diferencaPercentual(amostrasP2[2], amostrasP2[3]) < -5.00) {
            dif3_p2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;

        } else {
            dif3_p2.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP2 = true;
        }
        if (((diferencaPercentual(amostrasP2[2], amostrasP2[3]).isNaN() || diferencaPercentual(amostrasP2[2], amostrasP2[3]).isInfinite())))
            dif3_p2.setText("");
        else
            dif3_p2.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP2[2], amostrasP2[3])))).replace('.', ','));


        if (diferencaPercentual(amostrasP2[3], amostrasP2[4]) > 5.00 || diferencaPercentual(amostrasP2[3], amostrasP2[4]) < -5.00) {
            dif4_p2.setTextColor(Color.parseColor("#FF0000"));
            todosConformeP2 = false;

        } else {
            dif4_p2.setTextColor(Color.parseColor("#32CD32"));
            todosConformeP2 = true;
        }
        if (((diferencaPercentual(amostrasP2[3], amostrasP2[4]).isNaN() || diferencaPercentual(amostrasP2[3], amostrasP2[4]).isInfinite())))
            dif4_p2.setText("");
        else
            dif4_p2.setText(String.valueOf(-1 * arredonda1Casa((diferencaPercentual(amostrasP2[3], amostrasP2[4])))).replace('.', ','));


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

        if (atualP2 >= 5) {
            for (int i = 0; i < 4; i++) {
                if (Math.abs(diferencaPercentual(amostrasP2[i], amostrasP2[i + 1])) > maiorDif) {
                    maiorDif = Math.abs(diferencaPercentual(amostrasP2[i], amostrasP2[i + 1]));
                    posicaoMaiorDif = i + 1;
                }
            }

            if (maiorDif > 5.0) {
                if (posicaoMaiorDif == 1) {
                    dif1_p2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 2) {
                    dif2_p2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 3) {
                    dif3_p2.setTextColor(Color.parseColor("#FF0000"));
                } else if (posicaoMaiorDif == 4) {
                    dif4_p2.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }

        mediaGeralp2 = (amostrasP2[0] + amostrasP2[1] + amostrasP2[2] + amostrasP2[3] + amostrasP2[4]) / 5;
        mediaGeralp2 = arredonda3Casas(mediaGeralp2);

        if ((mediaPercentualp2).isNaN() || mediaPercentualp2.isInfinite()) mediaDifP2.setText("");
        else {

            mediaDifP2.setText(String.valueOf((-1 * arredonda1Casa(mediaPercentualp2))).replace('.', ','));
        }

        p2Media.setText(String.valueOf((mediaGeralp2)).replace('.', ','));
        Double aux1 = arredonda2Casas(desvioPadrao(amostrasP2));
        desvioProduto2.setText(String.valueOf((aux1)).replace('.', ','));
    }

    //Checa se há uma calibração para a máquina selecionada na data e turno atuais
    //Parâmetro de entrada: Descrição da máquina selecionada
    public boolean checaMaquinaImplemento(int s) {
        int checagem = dao.checaMaquinaImplemento(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                ferramentas.formataDataDb(ferramentas.dataAtual()), checaTurno(), s);
        if (checagem > 0) return true;
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


    //Arredonda um número do tipo Double para que tenha 1 casa decimal
    //Parâmetro de entrada: um Double
    private static Double arredonda1Casa(Double media) {
        DecimalFormat df = new DecimalFormat(".#");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    //Arredonda um número do tipo Double para que tenha  casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda2Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }


    //Arredonda um número do tipo Double para que tenha 3 casas decimais
    //Parâmetro de entrada: um Double
    private static Double arredonda3Casas(Double media) {
        DecimalFormat df = new DecimalFormat("###.###");
        return Double.valueOf(df.format(media).replace(',', '.'));
    }

    //formula= (1-(amostra atual/amostra anterior))/100
    //Calcula a diferença percentual entre dois números do tipo Double
    //Parâmetro de entrada: dois Doubles
    private static Double diferencaPercentual(Double anterior, Double atual) {
        Double calculo = (1 - (atual / anterior)) * 100;//((anterior - atual) / anterior) * 100.0
        DecimalFormat df = new DecimalFormat("###.##");
        if (Double.valueOf(df.format(calculo).replace(',', '.')) == 0.0) return -0.0;
        return Double.valueOf(df.format(calculo).replace(',', '.'));
    }


    //Calcula o desvio padrão de um array do tipo Double
    //Parâmetro de entrada: um array do tipo Double
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

    //Calcula a média entre os números em um array do tipo Double
    //Parâmetro de entrada: um array do tipo Double
    public static Double calculaMedia(double[] values, boolean includeNullAndNaN) {

        if (values == null) {
            throw new IllegalArgumentException("Null 'values' argument.");
        }
        Double sum = 0.0;
        Double current;
        int counter = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != NULL) {
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
    public void caixaAlertaMaquinaImplemento() {
        AlertDialog dialog = new AlertDialog.Builder(ActivityCalibracao.this)
                .setTitle("ERRO")
                .setMessage("MAQUINA JÁ CALIBRADA PARA ESTE IMPLEMENTO NO TURNO ATUAL.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }

    //Testa as conformidades para liberar o botão de confirmação
    public boolean testaConfirmacao() {
        if (atualP1 >= 5 && atualP2 >= 5) {
            testaP1();
            testaP2();
            if (todosConformeP1 == true && todosConformeP2 == true) {
                if (idOperador != -1 && idMaquina != -1 && idMaquinaImplemento != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    //Inicializa todos os itens na tela e seta valores de variáveis
    public void inicializacao() {
        setContentView(R.layout.activity_calibracao);
        setTitle(nomeEmpresaPref);

        ferramentas = new Ferramentas();

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
        spinnerMaquinaImplemento = findViewById(R.id.spinner_implemento_calibragem);

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

        corrigirP1 = 0;
        corrigirP2 = 0;

        idOperador = -1;
        idMaquina = -1;
        idMaquinaImplemento = -1;

        contSpinnerOperador = 0;
        contSpinnerMaquina = 0;
        contSpinnerMaquinaImplemento = 0;

        mediaGeralp1 = 0.0;
        mediaPercentualp1 = 0.0;
        mediaGeralp2 = 0.0;
        mediaPercentualp2 = 0.0;

        nomeProduto1.setText(joinOsInsumos.get(0).getDESCRICAO());
        nomeProduto2.setText(joinOsInsumos.get(1).getDESCRICAO());

        osTalhao.setText(osSelecionada.getTALHAO());

        botaoMediaP1 = findViewById(R.id.botao_calibragem_media_p1);

        botaoMediaP2 = findViewById(R.id.botao_calibragem_media_p2);

        osData.setText(ferramentas.dataAtual());

        osTurno.setText(checaTurno());

        p1_a1.setFocusable(false);


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

        spinnerMaquinaImplemento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerMaquinaImplemento == 0)
                    spinnerMaquinaImplemento.setAdapter(adapterImplemento);
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
                spinnerMaquinaImplemento.setAdapter(null);
                if (contSpinnerMaquina > 0) {
                    idMaquina = maquinas.get(position).getID_MAQUINA();
                    contSpinnerMaquinaImplemento = 0;
                    idMaquinaImplemento = -1;
                    implementos = new ArrayList<>(dao.listaJoinMaquinaImplemento(maquinas.get(position).getID_MAQUINA()));
                    adapterImplemento = new ArrayAdapter<Join_MAQUINA_IMPLEMENTO>(ActivityCalibracao.this,
                            android.R.layout.simple_spinner_item, implementos);
                    adapterImplemento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    posicaoMaquina = position;

                    if (contSpinnerMaquina == 1 && auxSavedInstanceState != null) {
                        idMaquina = auxSavedInstanceState.getInt("idMaquina");
                        idMaquinaImplemento = auxSavedInstanceState.getInt("idMaquinaImplemento");
                        posicaoMaquina = auxSavedInstanceState.getInt("posicaoMaquina");
                        posicaoImplemento = auxSavedInstanceState.getInt("posicaoImplemento");

                        if (idMaquinaImplemento != -1) {
                            spinnerMaquinaImplemento.setAdapter(adapterImplemento);
                            spinnerMaquinaImplemento.setSelection(posicaoImplemento);
                        }
                    }
                }
                contSpinnerMaquina++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerMaquinaImplemento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerMaquinaImplemento > 0) {
                    if (checaMaquinaImplemento(implementos.get(position).getID_MAQUINA_IMPLEMENTO()) == true) {
                        spinnerMaquinaImplemento.setAdapter(null);
                        contSpinnerMaquinaImplemento = -1;
                        idMaquinaImplemento = -1;
                        caixaAlertaMaquinaImplemento();
                    } else {
                        idMaquinaImplemento = implementos.get(position).getID_MAQUINA_IMPLEMENTO();
                        posicaoImplemento = position;
                    }
                }
                contSpinnerMaquinaImplemento++;
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
            spinnerMaquinaImplemento.setEnabled(false);

            botaoMediaP1.setVisibility(GONE);
            botaoMediaP2.setVisibility(GONE);
            botaoConfirma.setVisibility(GONE);
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
                if (checaMaquinaImplemento(idMaquina) == true)
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
                                        Double desvioP1;
                                        Double desvioP2;

                                        mediaP1 = Double.valueOf(p1Media.getText().toString().replace(',', '.'));
                                        mediaP2 = Double.valueOf(p2Media.getText().toString().replace(',', '.'));
                                        desvioP1 = Double.valueOf(desvioProduto1.getText().toString().replace(',', '.'));
                                        desvioP2 = Double.valueOf(desvioProduto2.getText().toString().replace(',', '.'));

                                        CALIBRAGEM_SUBSOLAGEM calibragem_subsolagem = new CALIBRAGEM_SUBSOLAGEM(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                                                ferramentas.formataDataDb(ferramentas.dataAtual()), checaTurno(), idMaquinaImplemento,
                                                idOperador, mediaP1, desvioP1, mediaP2, desvioP2);
                                        try {
                                            dao.insert(calibragem_subsolagem);
                                        }catch(SQLiteConstraintException | NullPointerException ex) {
                                            AlertDialog dialogoErro = new AlertDialog.Builder(ActivityCalibracao.this)
                                                    .setTitle("Erro 101")
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
                                            dao.update(osSelecionada);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("p1_a1", p1_a1.getText().toString());
        outState.putString("p1_a2", p1_a2.getText().toString());
        outState.putString("p1_a3", p1_a3.getText().toString());
        outState.putString("p1_a4", p1_a4.getText().toString());
        outState.putString("p1_a5", p1_a5.getText().toString());

        outState.putString("p2_a1", p2_a1.getText().toString());
        outState.putString("p2_a2", p2_a2.getText().toString());
        outState.putString("p2_a3", p2_a3.getText().toString());
        outState.putString("p2_a4", p2_a4.getText().toString());
        outState.putString("p2_a5", p2_a5.getText().toString());

        outState.putString("dif1_p1", dif1_p1.getText().toString());
        outState.putString("dif2_p1", dif2_p1.getText().toString());
        outState.putString("dif3_p1", dif3_p1.getText().toString());
        outState.putString("dif4_p1", dif4_p1.getText().toString());

        outState.putString("dif1_p2", dif1_p2.getText().toString());
        outState.putString("dif2_p2", dif2_p2.getText().toString());
        outState.putString("dif3_p2", dif3_p2.getText().toString());
        outState.putString("dif4_p2", dif4_p2.getText().toString());

        outState.putDoubleArray("amostrasP1", amostrasP1);
        outState.putDoubleArray("amostrasP2", amostrasP2);

        outState.putString("p1Media", p1Media.getText().toString());
        outState.putString("p2Media", p2Media.getText().toString());

        outState.putString("mediaDifP1", mediaDifP1.getText().toString());
        outState.putString("mediaDifP2", mediaDifP2.getText().toString());


        outState.putString("desvioProduto1", desvioProduto1.getText().toString());
        outState.putString("desvioProduto2", desvioProduto2.getText().toString());

        outState.putInt("atualP1", atualP1);
        outState.putInt("atualP2", atualP2);
        outState.putInt("corrigirP1", corrigirP1);
        outState.putInt("corrigirP2", corrigirP2);

        outState.putDouble("mediaGeralp1", mediaGeralp1);
        outState.putDouble("mediaGeralp2", mediaGeralp2);
        outState.putDouble("mediaPercentualp1", mediaPercentualp1);
        outState.putDouble("mediaPercentualp2", mediaPercentualp2);

        outState.putInt("idMaquina", idMaquina);
        outState.putInt("idMaquinaImplemento", idMaquinaImplemento);
        outState.putInt("idOperador", idOperador);

        outState.putInt("posicaoImplemento", posicaoImplemento);
        outState.putInt("posicaoMaquina", posicaoMaquina);
        outState.putInt("posicaoOperador", posicaoOperador);
    }

    //SObrescrita do método onBackPressed nativo do Android para que feche o menu de navegação lateral
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}