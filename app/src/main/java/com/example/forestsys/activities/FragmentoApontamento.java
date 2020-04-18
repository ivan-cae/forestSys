package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.repositorios.RepositorioPrestadores;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES_DIA;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class FragmentoApontamento extends Fragment {
    private ImageButton botaoDatePicker;
    private Button botaoSalvarAponamento;
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


    private ViewModelO_S_ATIVIDADES_DIA viewModelO_s_atividades_dia;

    public static O_S_ATIVIDADES_DIA oSAtividadesDiaAtual;
    private int posicaoResponsavel;
    private int posicaoPrestador;
    private DataHoraAtual dataHoraAtual;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_apontamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataHoraAtual = new DataHoraAtual();
        //botaoDatePicker = getView().findViewById(R.id.botao_date_picker);
        botaoSalvarAponamento = getView().findViewById(R.id.botao_salvar_fragmento_apontamento);
        dataApontamento = getView().findViewById(R.id.data_apontamento);
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


        viewModelO_s_atividades_dia = new ViewModelO_S_ATIVIDADES_DIA(getActivity().getApplication());

        oSAtividadesDiaAtual = viewModelO_s_atividades_dia.selecionaOsAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                dataHoraAtual.dataAtual());

        if (oSAtividadesDiaAtual == null) {
            Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);
            mes += 1;

            String a = String.valueOf(ano);
            String m = String.valueOf(mes);
            String d = String.valueOf(dia);

            if (mes < 10) m = "0" + m;

            if (dia < 10) d = "0" + d;

            dataApontamento.setText(dataHoraAtual.dataAtual());
        } else populaInfo();

        botaoSalvarAponamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(checaTextView(areaRealizadaApontamento) == false ||checaTextView( HOEscavadeiraApontamento)==false
                    || checaTextView( HOApontamento)==false || checaTextView(HMApontamento)==false || checaTextView(HHApontamento)==false
                ||checaTextView(HMEscavadeiraApontamento)==false){
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Erro!")
                            .setMessage("Um ou mais valores incorretos localizados, seus campos serão zerados!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                }
                else{*/
                if (posicaoPrestador == 0) posicaoPrestador++;
                if (posicaoResponsavel == 0) posicaoResponsavel++;

                if (oSAtividadesDiaAtual == null) {
                    oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();
                    String s;

                    oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                    oSAtividadesDiaAtual.setDATA(dataApontamento.getText().toString().trim());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
                    s = areaRealizadaApontamento.getText().toString();
                    if (!s.trim().isEmpty())
                        oSAtividadesDiaAtual.setAREA_REALIZADA((s));
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);
                    oSAtividadesDiaAtual.setHO_ESCAVADEIRA((HOEscavadeiraApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHO((HOApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHM((HMApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHH((HHApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHM_ESCAVADEIRA((HMEscavadeiraApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setOBSERVACAO(obsApontamento.getText().toString());
                    viewModelO_s_atividades_dia.insert(oSAtividadesDiaAtual);
                } else if (oSAtividadesDiaAtual != null && oSAtividadesDiaAtual.getDATA().trim() == dataApontamento.getText().toString().trim()) {
                    String s;
                    oSAtividadesDiaAtual.setDATA(dataApontamento.getText().toString());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
                    s = areaRealizadaApontamento.getText().toString();
                    if (!s.trim().isEmpty())
                        oSAtividadesDiaAtual.setAREA_REALIZADA((s));
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);
                    oSAtividadesDiaAtual.setHO_ESCAVADEIRA((HOEscavadeiraApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHO((HOApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHM((HMApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHH((HHApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setHM_ESCAVADEIRA((HMEscavadeiraApontamento.getText().toString()));
                    oSAtividadesDiaAtual.setOBSERVACAO(obsApontamento.getText().toString());
                    viewModelO_s_atividades_dia.update(oSAtividadesDiaAtual);
            }}
        });

        /*botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });*/
    }

    public boolean checaTextView(TextView textView){
        String valor = textView.getText().toString();
        char[] s = valor.toCharArray();
        int contador = 0;
        for(int i = 0; i<s.length; i++){
            if(s[i] =='.') contador++;
        }
        if(s.length<1 || s[s.length-1]=='.' || s[0]=='.') {
            textView.setText("0");
            return false;
        }
    return true;
    }

    //Mostra as informações do apontamento nos seus respectivos campos
    public void populaInfo() {
        posicaoPrestador = oSAtividadesDiaAtual.getID_PRESTADOR();
        posicaoResponsavel = oSAtividadesDiaAtual.getID_RESPONSAVEL();

        dataApontamento.setText(oSAtividadesDiaAtual.getDATA());

        spinnerResponsavel.setSelection(oSAtividadesDiaAtual.getID_RESPONSAVEL() - 1, true);
        spinnerPrestador.setSelection(oSAtividadesDiaAtual.getID_PRESTADOR() - 1, true);

        if (oSAtividadesDiaAtual.getAREA_REALIZADA() != null)
            areaRealizadaApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getAREA_REALIZADA()));

        if (!oSAtividadesDiaAtual.getHO().toString().trim().isEmpty())
            HOApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHO()));

        if (!oSAtividadesDiaAtual.getHH().toString().trim().isEmpty())
            HHApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHH()));

        if (!oSAtividadesDiaAtual.getHM().toString().trim().isEmpty())
            HMApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHM()));

        if (!oSAtividadesDiaAtual.getHM_ESCAVADEIRA().toString().trim().isEmpty())
            HMEscavadeiraApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHM_ESCAVADEIRA()));

        if (!oSAtividadesDiaAtual.getHO_ESCAVADEIRA().toString().trim().isEmpty())
            HOEscavadeiraApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHO_ESCAVADEIRA()));

        if (!oSAtividadesDiaAtual.getOBSERVACAO().trim().isEmpty())
            obsApontamento.setText(oSAtividadesDiaAtual.getOBSERVACAO());
    }


    //Classe responsável pela criação do calendário
    public static class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        //Sobrescrita do método onCreateDialog, nele são definidos os parâmetros do calendário quando aberto
        @NonNull
        @Override
        public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {

            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);
            int m = mes;
            mes += 1;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, dia, mes, ano);

            datePickerDialog.getDatePicker().init(ano, m, dia, null); //System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }


        //Sobrescrita do método onDateSet, nele é setada a data selecionada no respectivo textview
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear += 1;

            String a = String.valueOf(year);
            String m = String.valueOf(monthOfYear);
            String d = String.valueOf(dayOfMonth);

            if (monthOfYear < 10) m = "0" + m;

            if (dayOfMonth < 10) d = "0" + d;

            String auxDia = String.valueOf(d);
            String auxMes = String.valueOf(m);
            String auxAno = String.valueOf(year);

            setarData(auxDia, auxMes, auxAno);
        }


        //Método auxiliar para setar a data no textview
        public void setarData(String dia, String mes, String ano) {
            dataApontamento.setText(dia + "/" + mes + "/" + ano);
        }
    }
}
