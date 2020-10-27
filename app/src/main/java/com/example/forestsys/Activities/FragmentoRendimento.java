package com.example.forestsys.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.R;
import com.example.forestsys.Classes.GGF_USUARIOS;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.Classes.PRESTADORES;

import java.util.ArrayList;

import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.hh;
import static com.example.forestsys.Activities.ActivityAtividades.hm;
import static com.example.forestsys.Activities.ActivityAtividades.hme;
import static com.example.forestsys.Activities.ActivityAtividades.ho;
import static com.example.forestsys.Activities.ActivityAtividades.hoe;
import static com.example.forestsys.Activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.Activities.ActivityAtividades.obs;
import static com.example.forestsys.Activities.ActivityAtividades.area;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityRegistros.auxSavedInstanceState;

public class FragmentoRendimento extends Fragment {
    public static NDSpinner spinnerResponsavel;
    public static NDSpinner spinnerPrestador;
    public static EditText areaRealizadaApontamento;
    public static EditText HOEscavadeiraApontamento;
    public static EditText HOApontamento;
    public static EditText HMApontamento;
    public static EditText HHApontamento;
    public static EditText HMEscavadeiraApontamento;
    public static EditText obsApontamento;


    public static int posicaoResponsavel = -1;
    public static int posicaoPrestador = -1;

    private ArrayList<PRESTADORES> prestadores;
    private ArrayList<GGF_USUARIOS> usuarios;
    private ArrayAdapter<PRESTADORES> adapterPrestadores;
    private ArrayAdapter<GGF_USUARIOS> adapterUsuarios;

    private int contSpinnerPrestador = 0;
    private int contSpinnerResponsavel = 0;

    private BaseDeDados baseDeDados;
    private DAO dao;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_rendimento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (osSelecionada.getSTATUS_NUM() != 2) {
            inicializacao();
            if (editouRegistro == true) {
                obsApontamento.setFocusable(false);

                int maior = usuarios.size();
                if (prestadores.size() > maior) maior = prestadores.size();
                for (int i = 0; i < maior; i++) {
                    if (i < usuarios.size()) {
                        if (usuarios.get(i).getID_USUARIO() == oSAtividadesDiaAtual.getID_RESPONSAVEL())
                            posicaoResponsavel = usuarios.get(i).getID_USUARIO();
                        spinnerResponsavel.setSelection(posicaoResponsavel - 1);
                    }

                    if (i < prestadores.size()) {
                        if (prestadores.get(i).getID_PRESTADOR() == oSAtividadesDiaAtual.getID_PRESTADOR())
                            posicaoPrestador = prestadores.get(i).getID_PRESTADOR();
                        spinnerPrestador.setSelection(posicaoPrestador - 1);
                    }
                }
                spinnerPrestador.setAdapter(adapterPrestadores);
                spinnerPrestador.setSelection(posicaoPrestador - 1);

                spinnerResponsavel.setAdapter(adapterUsuarios);
                spinnerResponsavel.setSelection(posicaoResponsavel - 1);
            } else obsApontamento.setFocusable(true);


            if (savedInstanceState != null) {
                posicaoResponsavel = savedInstanceState.getInt("posicaoResponsavel");
                posicaoPrestador = savedInstanceState.getInt("posicaoPrestador");

                if (posicaoResponsavel != -1) {
                    spinnerResponsavel.setAdapter(adapterUsuarios);
                    spinnerResponsavel.setSelection(posicaoResponsavel);
                }
                if (posicaoPrestador != -1) {
                    spinnerPrestador.setAdapter(adapterPrestadores);
                    spinnerPrestador.setSelection(posicaoPrestador);
                }
            }if(auxSavedInstanceState==null && editouRegistro==false){
                areaRealizadaApontamento.getText().clear();
                HOEscavadeiraApontamento.getText().clear();
                HOApontamento.getText().clear();
                HMApontamento.getText().clear();
                HHApontamento.getText().clear();
                HMEscavadeiraApontamento.getText().clear();
                obsApontamento.getText().clear();
                posicaoPrestador = -1;
                posicaoResponsavel = -1;
            }
        }
    }

    //Poe a virgula automaticamente como separador decimal dos números inseridos nas caixas de texto
    //parâmetros de entrada: Uma instância de uma caixa de texto, um inteiro representando quantos números virão antes da virgula,
    //umaa string contendo os valores inseridos na caixa de texto
    public void mascaraVirgula(EditText edit, int antesDaVirgula, CharSequence s) {
        int tamanho;
        String input;

        tamanho = edit.length();
        input = s.toString();

        if (!input.isEmpty()) {

            edit.setFilters(new InputFilter[]{
                    new InputFilter.LengthFilter(antesDaVirgula + 3)});

            char[] aux = input.toCharArray();
            if ((tamanho + 1) != antesDaVirgula && aux[tamanho - 1] == ',') {
                edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                if (tamanho == antesDaVirgula + 1) {
                    char[] charAux = input.toCharArray();
                    String stringAux = String.valueOf(charAux[tamanho - 1]);
                    input = input.substring(0, tamanho - 1);
                    edit.setText(input + "," + stringAux);
                    edit.setSelection(edit.getText().toString().length());
                }
            }
        }
    }


    //inicialização dos itens na tela e variáveis
    public void inicializacao() {
        spinnerResponsavel = getView().findViewById(R.id.spinner_responsavel_apontamento);
        spinnerPrestador = getView().findViewById(R.id.spinner_prestador_apontamento);
        areaRealizadaApontamento = getView().findViewById(R.id.area_realizada_apontamento);
        HOEscavadeiraApontamento = getView().findViewById(R.id.hora_operador_escavadeira_apontamento);
        HOApontamento = getView().findViewById(R.id.hora_operador_apontamento);
        HMApontamento = getView().findViewById(R.id.hora_maquina_apontamento);
        HHApontamento = getView().findViewById(R.id.hora_homem_apontamento);
        HMEscavadeiraApontamento = getView().findViewById(R.id.hora_maquina_escavadeira_apontamento);
        obsApontamento = getView().findViewById(R.id.obs_apontamento);

        baseDeDados = BaseDeDados.getInstance(getContext());
        dao = baseDeDados.dao();

        prestadores = new ArrayList<>(dao.listaPrestadores());

        usuarios = new ArrayList<>(dao.listaUsuarios());


        adapterPrestadores = new ArrayAdapter<PRESTADORES>(getActivity(),
                android.R.layout.simple_spinner_item, prestadores);
        adapterPrestadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        adapterUsuarios = new ArrayAdapter<GGF_USUARIOS>(getActivity(),
                android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerResponsavel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerResponsavel == 0) spinnerResponsavel.setAdapter(adapterUsuarios);
                return false;
            }
        });

        spinnerPrestador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerPrestador == 0) spinnerPrestador.setAdapter(adapterPrestadores);
                return false;
            }
        });

        obsApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                obs = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        areaRealizadaApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                area = checaTextView(areaRealizadaApontamento, area, 0);
                FragmentoInsumos frag = (FragmentoInsumos) getActivity().getSupportFragmentManager().findFragmentById(R.id.registro_fragmento_insumos);
                frag.setInsumos();

                String[] antesDaVirgula = String.valueOf(osSelecionada.getAREA_PROGRAMADA()).replace('.', ',').split(",");
                mascaraVirgula(areaRealizadaApontamento, antesDaVirgula[0].length(), s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        HOEscavadeiraApontamento.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hoe = checaTextView(HOEscavadeiraApontamento, hoe, 0);
                mascaraVirgula(HOEscavadeiraApontamento, 2, s);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        HOApontamento.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ho = checaTextView(HOApontamento, ho, 0);
                mascaraVirgula(HOApontamento, 2, s);
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        HMApontamento.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hm = checaTextView(HMApontamento, hm, 0);
                mascaraVirgula(HMApontamento, 2, s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        HMEscavadeiraApontamento.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hme = checaTextView(HMEscavadeiraApontamento, hme, 0);
                mascaraVirgula(HMEscavadeiraApontamento, 2, s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        HHApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hh = checaTextView(HHApontamento, hh, 300);
                mascaraVirgula(HHApontamento, 3, s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spinnerPrestador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerPrestador > 0) {
                    position++;
                    posicaoPrestador = position;
                }
                contSpinnerPrestador++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerResponsavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerResponsavel > 0) {
                    position++;
                    posicaoResponsavel = position;
                }
                contSpinnerResponsavel++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if (oSAtividadesDiaAtual != null) populaInfo(oSAtividadesDiaAtual);


        if (osSelecionada.getSTATUS_NUM() == 2) {
            spinnerResponsavel.setEnabled(false);
            spinnerResponsavel.setVisibility(View.GONE);

            spinnerPrestador.setEnabled(false);
            spinnerPrestador.setVisibility(View.GONE);

            areaRealizadaApontamento.setEnabled(false);
            HOEscavadeiraApontamento.setEnabled(false);
            HOApontamento.setEnabled(false);
            HMApontamento.setEnabled(false);
            HHApontamento.setEnabled(false);
            HMEscavadeiraApontamento.setEnabled(false);
            obsApontamento.setEnabled(false);

            areaRealizadaApontamento.setVisibility(View.GONE);
            HOEscavadeiraApontamento.setVisibility(View.GONE);
            HOApontamento.setVisibility(View.GONE);
            HMApontamento.setVisibility(View.GONE);
            HHApontamento.setVisibility(View.GONE);
            HMEscavadeiraApontamento.setVisibility(View.GONE);
            obsApontamento.setVisibility(View.GONE);
        }
    }

    //Checa se o valor de um TextView pode ser convertido para double
    //parâmetro de entrada: TextView e uma String
    //parâmetro de saída: null se o valor não puder ser convertido, a própria string se o valor puder ser convertido
    public String checaTextView(TextView t, String str, double limite) {
        String s1 = t.getText().toString().trim();
        if (s1.isEmpty()) return null;
        char[] c = s1.toCharArray();
        if (s1.length() > 0) {
            if (s1 == "," || c[s1.length() - 1] == ',' || c[0] == ',' || contaVirgula(s1, ',') > 1) {
                t.setError("Valor incorreto! O registro não será salvo.");
                str = "";
            } else {
                str = s1;
                t.setError(null);
                if (limite != 0) {
                    double teste = 0;
                    try {
                        teste = Double.parseDouble(str.replace(',', '.'));
                    } catch (NumberFormatException | NullPointerException n) {
                        teste = 0;
                    }
                    if (teste > limite) {
                        t.setError("Valor Acima do permitido! O registro não será salvo.");
                        str = "";
                    }
                }
            }
        }
        return str;
    }

    //Conta quantas indicências de um caractere há em uma String.
    //Parâmetros de entrada: uma String, um Char
    //Retorna quantidade de indicências do caracter
    public int contaVirgula(String s, char c) {
        return s.length() == 0 ? 0 : (s.charAt(0) == c ? 1 : 0) + contaVirgula(s.substring(1), c);
    }

    //Mostra as informações do apontamento nos seus respectivos campos
    public void populaInfo(O_S_ATIVIDADES_DIA osAtv) {
        posicaoPrestador = osAtv.getID_PRESTADOR();
        posicaoResponsavel = osAtv.getID_RESPONSAVEL();

        spinnerResponsavel.setSelection(osAtv.getID_RESPONSAVEL() - 1, true);
        spinnerPrestador.setSelection(osAtv.getID_PRESTADOR() - 1, true);

        if (osAtv.getAREA_REALIZADA() != null) {
            areaRealizadaApontamento.setText(osAtv.getAREA_REALIZADA().replace(".", ","));
            area = areaRealizadaApontamento.getText().toString();
        } else area = "";

        if (osAtv.getHO() != null) {
            HOApontamento.setText((osAtv.getHO()).replace(".", ","));
            ho = HOApontamento.getText().toString();
        } else ho = "";

        if (osAtv.getHH() != null) {
            HHApontamento.setText((osAtv.getHH()).replace(".", ","));
            hh = HHApontamento.getText().toString();
        } else hh = "";

        if (osAtv.getHM() != null) {
            HMApontamento.setText((osAtv.getHM()).replace(".", ","));
            hm = HMApontamento.getText().toString();
        } else hm = "";

        if (osAtv.getHM_ESCAVADEIRA() != null) {
            HMEscavadeiraApontamento.setText((osAtv.getHM_ESCAVADEIRA()).replace(".", ","));
            hme = HMEscavadeiraApontamento.getText().toString();
        } else hme = "";

        if (osAtv.getHO_ESCAVADEIRA() != null) {
            HOEscavadeiraApontamento.setText((osAtv.getHO_ESCAVADEIRA()).replace(".", ","));
            hoe = HOEscavadeiraApontamento.getText().toString();
        } else hoe = "";

        if (osAtv.getOBSERVACAO() != null) {
            if(osAtv.getOBSERVACAO()!=null) obsApontamento.setText(osAtv.getOBSERVACAO());
            obs = obsApontamento.getText().toString();
        } else obs = "";
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString("area", area);
        outState.putString("hoe", hoe);
        outState.putString("ho", ho);
        outState.putString("hm", hm);
        outState.putString("hh", hh);
        outState.putString("hme", hme);
        outState.putString("obs", obs);

        outState.putInt("posicaoPrestador", posicaoPrestador);
        outState.putInt("posicaoResponsavel", posicaoResponsavel);

    }
}
