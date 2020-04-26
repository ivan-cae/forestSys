package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.repositorios.RepositorioPrestadores;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES_DIA;

import java.util.ArrayList;

import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.ActivityRegistros.dataDoApontamento;
import static com.example.forestsys.activities.ActivityRegistros.oSAtividadesDiaAtual;
import static com.example.forestsys.activities.ActivityRegistros.primeiraReg;
import static com.example.forestsys.activities.ActivityRegistros.viewModelOSAtividadesDia;

public class FragmentoRendimento extends Fragment {
    private static TextView dataApontamento;
    private Spinner spinnerResponsavel;
    private Spinner spinnerPrestador;
    private EditText areaRealizadaApontamento;
    private EditText HOEscavadeiraApontamento;
    private EditText HOApontamento;
    private EditText HMApontamento;
    private EditText HHApontamento;
    private EditText obsApontamento;
    private EditText HMEscavadeiraApontamento;

    public static int posicaoResponsavel;
    public static int posicaoPrestador;

    public static String area;
    public static String ho;
    public static String hm;
    public static String hh;
    public static String hoe;
    public static String hme;
    public static String obs;

    public static O_S_ATIVIDADES_DIA auxiliar;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_rendimento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataApontamento = getView().findViewById(R.id.data_fragmento_apontamento);
        spinnerResponsavel = getView().findViewById(R.id.spinner_responsavel_apontamento);
        spinnerPrestador = getView().findViewById(R.id.spinner_prestador_apontamento);
        areaRealizadaApontamento = getView().findViewById(R.id.area_realizada_apontamento);
        HOEscavadeiraApontamento = getView().findViewById(R.id.hora_operador_escavadeira_apontamento);
        HOApontamento = getView().findViewById(R.id.hora_operador_apontamento);
        HMApontamento = getView().findViewById(R.id.hora_maquina_apontamento);
        HHApontamento = getView().findViewById(R.id.hora_homem_apontamento);
        HMEscavadeiraApontamento = getView().findViewById(R.id.hora_maquina_escavadeira_apontamento);
        obsApontamento = getView().findViewById(R.id.obs_apontamento);

        RepositorioUsers repositorioUsers = new RepositorioUsers(getActivity().getApplication());

        RepositorioPrestadores repositorioPrestadores = new RepositorioPrestadores(getActivity().getApplication());

        viewModelOSAtividadesDia = new ViewModelO_S_ATIVIDADES_DIA(getActivity().getApplication());
        ArrayList<PRESTADORES> prestadores = new ArrayList<>(repositorioPrestadores.listaPrestadores());

        ArrayList<GGF_USUARIOS> usuarios = new ArrayList<>(repositorioUsers.listaUsuarios());


        ArrayAdapter<PRESTADORES> adapterPrestadores = new ArrayAdapter<PRESTADORES>(getActivity(),
                android.R.layout.simple_spinner_item, prestadores);
        adapterPrestadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrestador.setAdapter(adapterPrestadores);


        ArrayAdapter<GGF_USUARIOS> adapterUsuarios = new ArrayAdapter<GGF_USUARIOS>(getActivity(),
                android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResponsavel.setAdapter(adapterUsuarios);

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
                obs=s.toString();
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
             public void afterTextChanged(Editable s){
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
                position++;
                posicaoPrestador = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicaoPrestador = 1;
            }
        });

        spinnerResponsavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position++;
                posicaoResponsavel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicaoResponsavel = 1;
            }
        });

        if(primeiraReg == true){
            auxiliar = oSAtividadesDiaAtual;
            primeiraReg = false;
        }

        if (auxiliar == null) {
            dataApontamento.setText(dataDoApontamento);
            auxiliar = new O_S_ATIVIDADES_DIA();
        } else{
            populaInfo(auxiliar);
        }
    }

    public String checaTextView(TextView t, String str) {
        String s1 = t.getText().toString().trim();
        if(s1.isEmpty()) return null;
        char []c = s1.toCharArray();
        if (s1.length()>0) {
            if (s1 == "," || c[s1.length() - 1] == ',' || c[0] == ',' || contaVirgula(s1, ',') > 1) {
                t.setError("Valor incorreto!");
                str = "";
            } else {
                str = s1;
                t.setError(null);
            }
        }

        Log.e("teste", str);
            return str;
}

    public int contaVirgula(String s, char c) {
        return s.length()==0 ? 0 : (s.charAt(0)==c ? 1 : 0) + contaVirgula(s.substring(1),c);
    }

    //Mostra as informações do apontamento nos seus respectivos campos
    public void populaInfo(O_S_ATIVIDADES_DIA osAtv) {
        posicaoPrestador = osAtv.getID_PRESTADOR();
        posicaoResponsavel = osAtv.getID_RESPONSAVEL();
        dataApontamento.setText(osAtv.getDATA());

        spinnerResponsavel.setSelection(osAtv.getID_RESPONSAVEL() - 1, true);
        spinnerPrestador.setSelection(osAtv.getID_PRESTADOR() - 1, true);

        if (osAtv.getAREA_REALIZADA() != null)
            areaRealizadaApontamento.setText(osAtv.getAREA_REALIZADA().replace(".", ","));

        if (osAtv.getHO()!=null)
            HOApontamento.setText((osAtv.getHO()).replace(".", ","));

        if (osAtv.getHH()!=null)
            HHApontamento.setText((osAtv.getHH()).replace(".", ","));

        if (osAtv.getHM()!=null)
            HMApontamento.setText((osAtv.getHM()).replace(".", ","));

        if (osAtv.getHM_ESCAVADEIRA()!=null)
            HMEscavadeiraApontamento.setText((osAtv.getHM_ESCAVADEIRA()).replace(".", ","));

        if (osAtv.getHO_ESCAVADEIRA()!=null)
            HOEscavadeiraApontamento.setText((osAtv.getHO_ESCAVADEIRA()).replace(".", ","));

        if (osAtv.getOBSERVACAO()!=null)
            obsApontamento.setText(osAtv.getOBSERVACAO());
    }
}