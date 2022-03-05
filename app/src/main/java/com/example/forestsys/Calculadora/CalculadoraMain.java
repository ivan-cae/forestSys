package com.example.forestsys.Calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forestsys.R;

/*
 * Classe responsável por executar a calculadora do aplicativo
 */
public class CalculadoraMain extends AppCompatActivity {

    private TextView e1, e2;
    private long contador = 0;
    private String expressao = "";
    private String texto = "";
    private Double resultado = 0.0;
    private boolean percentual = false;
    private Double auxe1 = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        e1 =  findViewById(R.id.editText1);
        e2 =  findViewById(R.id.editText2);

        e2.setText("0");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num0:
                e2.setText(e2.getText() + "0");
                break;

            case R.id.num1:
                e2.setText(e2.getText() + "1");
                break;

            case R.id.num2:
                e2.setText(e2.getText() + "2");
                break;

            case R.id.num3:
                e2.setText(e2.getText() + "3");
                break;


            case R.id.num4:
                e2.setText(e2.getText() + "4");
                break;

            case R.id.num5:
                e2.setText(e2.getText() + "5");
                break;

            case R.id.num6:
                e2.setText(e2.getText() + "6");
                break;

            case R.id.num7:
                e2.setText(e2.getText() + "7");
                break;

            case R.id.num8:
                e2.setText(e2.getText() + "8");
                break;

            case R.id.num9:
                e2.setText(e2.getText() + "9");
                break;

            case R.id.botao_ponto:
                if (contador == 0 && e2.length() != 0) {
                    e2.setText(e2.getText() + ".");
                    contador++;
                }
                break;

            case R.id.botao_limpar:
                e1.setText("");
                e2.setText("");
                contador = 0;
                expressao = "";
                break;

            case R.id.botao_backspace:
                texto = e2.getText().toString();
                if (texto.length() > 0) {
                    if (texto.endsWith(".")) {
                        contador = 0;
                    }
                    String novoTexto = texto.substring(0, texto.length() - 1);
                    if (texto.endsWith(")")) {
                        char[] a = texto.toCharArray();
                        int pos = a.length - 2;
                        int contadorer = 1;
                        for (int i = a.length - 2; i >= 0; i--) {
                            if (a[i] == ')') {
                                contadorer++;
                            } else if (a[i] == '(') {
                                contadorer--;
                            }
                            else if (a[i] == '.') {
                                contador = 0;
                            }
                            if (contadorer == 0) {
                                pos = i;
                                break;
                            }
                        }
                        novoTexto = texto.substring(0, pos);
                    }
                    if (novoTexto.equals("-") || novoTexto.endsWith("sqrt")) {
                        novoTexto = "";
                    }
                    else if (novoTexto.endsWith("^"))
                        novoTexto = novoTexto.substring(0, novoTexto.length() - 1);

                    e2.setText(novoTexto);
                }
                break;

            case R.id.botao_adicao:
                operacao("+");
                break;

            case R.id.botao_subtracao:
                operacao("-");
                break;

            case R.id.botao_dividir:
                operacao("/");
                break;

            case R.id.botao_multiplica:
                operacao("*");
                break;

            case R.id.botao_raiz:
                if (e2.length() != 0) {
                    texto = e2.getText().toString();
                    e2.setText("sqrt(" + texto + ")");
                }
                break;

            case R.id.botao_quadrado:
                if (e2.length() != 0) {
                    texto = e2.getText().toString();
                    e2.setText("(" + texto + ")^2");
                }
                break;

            case R.id.botao_posneg:
                if (e2.length() != 0) {
                    String s = e2.getText().toString();
                    char arr[] = s.toCharArray();
                    if (arr[0] == '-')
                        e2.setText(s.substring(0, s.length()));
                    else
                        e2.setText("-" + s);
                }
                break;

            case R.id.botao_percentual:
                if (e2.length() != 0) {
                    String texto = e2.getText().toString();
                    String s = texto.substring(0,texto.length());
                    auxe1 = Double.valueOf(s);

                    e1.setText(e1.getText() + texto + "%");

                    e2.setText("");
                    contador = 0;
                    percentual = true;
                }
                break;

            case R.id.botao_igualdade:
                if (e2.length() != 0) {
                    texto = e2.getText().toString();
                    expressao = e1.getText().toString() + texto;
                }

                if (percentual == true) {
                    try {
                        Double aux2 = Double.valueOf(e2.getText().toString());
                        aux2 *= 0.01;
                        e2.setText(String.valueOf(aux2*auxe1));

                        e1.setText("");
                        auxe1=0.0;
                        percentual = false;
                    } catch (Exception e) {
                        e1.setText("");
                        e2.setText("");
                        Toast.makeText(this, "Operação Inválida!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
                }
                    e1.setText("");
                    if (expressao.length() == 0) expressao = "0.0";
                    try {
                        resultado = new ExtendedDoubleEvaluator().evaluate(expressao);

                        e2.setText(resultado + "");
                    } catch (Exception e) {
                        e1.setText("");
                        e2.setText("");
                        expressao = "";
                        Toast.makeText(this, "Operação Inválida!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;

            case R.id.botao_abre_parenteses:
                e1.setText(e1.getText() + "(");
                break;

            case R.id.botao_fecha_parenteses:
                e1.setText(e1.getText() + ")");
                break;
        }

    }

    //Recebe um operador e o mostra na tela junto com seu operando
    private void operacao(String op) {
        if (e2.length() != 0) {
            String texto = e2.getText().toString();
            e1.setText(e1.getText() + texto + op);
            e2.setText("");
            contador = 0;
        } else {
            String texto = e1.getText().toString();
            if (texto.length() > 0) {
                String novoTexto = texto.substring(0, texto.length() - 1) + op;
                e1.setText(novoTexto);
            }
        }
    }
}
