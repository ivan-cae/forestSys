package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.forestsys.assets.NDSpinner;
import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.repositorios.RepositorioPrestadores;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES_DIA;

import java.util.ArrayList;

import static com.example.forestsys.activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.activities.ActivityAtividades.hh;
import static com.example.forestsys.activities.ActivityAtividades.hm;
import static com.example.forestsys.activities.ActivityAtividades.hme;
import static com.example.forestsys.activities.ActivityAtividades.ho;
import static com.example.forestsys.activities.ActivityAtividades.hoe;
import static com.example.forestsys.activities.ActivityAtividades.oSAtividadesDiaAtual;
import static com.example.forestsys.activities.ActivityAtividades.obs;
import static com.example.forestsys.activities.ActivityAtividades.area;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.ActivityRegistros.viewModelOSAtividadesDia;

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

    private RepositorioUsers repositorioUsers;
    private RepositorioPrestadores repositorioPrestadores;
    private ArrayList<PRESTADORES> prestadores;
    private ArrayList<GGF_USUARIOS> usuarios;
    ArrayAdapter<PRESTADORES> adapterPrestadores;
    ArrayAdapter<GGF_USUARIOS> adapterUsuarios;

    int contSpinnerPrestador = 0;
    int contSpinnerResponsavel = 0;

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

        repositorioUsers = new RepositorioUsers(getActivity().getApplication());

        repositorioPrestadores = new RepositorioPrestadores(getActivity().getApplication());

        viewModelOSAtividadesDia = new ViewModelO_S_ATIVIDADES_DIA(getActivity().getApplication());
        prestadores = new ArrayList<>(repositorioPrestadores.listaPrestadores());

        usuarios = new ArrayList<>(repositorioUsers.listaUsuarios());


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
            }

            @Override
            public void afterTextChanged(Editable s) {
                obs = s.toString();
            }
        });

        areaRealizadaApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                area = checaTextView(areaRealizadaApontamento, area);
            }
        });

        HOEscavadeiraApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hoe = checaTextView(HOEscavadeiraApontamento, hoe);
            }
        });

        HOApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ho = checaTextView(HOApontamento, ho);
            }
        });

        HMApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hm = checaTextView(HMApontamento, hm);

            }
        });

        HHApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hh = checaTextView(HHApontamento, hh);

            }
        });

        HMEscavadeiraApontamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hme = checaTextView(HMEscavadeiraApontamento, hme);
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
    public String checaTextView(TextView t, String str) {
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
            obsApontamento.setText(osAtv.getOBSERVACAO());
            obs = obsApontamento.getText().toString();
        } else obs = "";
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (osSelecionada.getSTATUS_NUM() != 2) {
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
}