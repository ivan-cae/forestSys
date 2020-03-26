package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.room.ColumnInfo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import android.widget.Toast;

import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.repositorios.RepositorioImplementos;
import com.example.forestsys.repositorios.RepositorioMaquinas;
import com.example.forestsys.repositorios.RepositorioPrestadores;
import com.example.forestsys.repositorios.RepositorioUsers;
import com.example.forestsys.viewModels.ViewModelO_S_ATIVIDADES_DIA;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static java.sql.Types.NULL;

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

    private  ViewModelO_S_ATIVIDADES_DIA viewModelO_s_atividades_dia;

    public static O_S_ATIVIDADES_DIA oSAtividadesDiaAtual;
    private int posicaoResponsavel;
    private int posicaoPrestador;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragmento_apontamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botaoDatePicker = getView().findViewById(R.id.botao_date_picker);
        botaoSalvarAponamento = getView().findViewById(R.id.botao_salvar_apontamento);
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
                position ++;
                posicaoPrestador = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerResponsavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position ++;
                posicaoResponsavel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        viewModelO_s_atividades_dia = new ViewModelO_S_ATIVIDADES_DIA(getActivity().getApplication());

        oSAtividadesDiaAtual = viewModelO_s_atividades_dia.selecionaOsAtividadesDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(),
                DateFormat.format("dd/MM/yyyy", new Date()).toString());

        if (oSAtividadesDiaAtual == null){
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

        dataApontamento.setText(String.valueOf(d) + "/" + String.valueOf(m) + "/" + String.valueOf(a));
    }else populaInfo();

        botaoSalvarAponamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(posicaoPrestador==0) posicaoPrestador++;
                if(posicaoResponsavel==0) posicaoResponsavel++;

                if(oSAtividadesDiaAtual==null) {
                    oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();
                    String s;

                    oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                    oSAtividadesDiaAtual.setDATA(dataApontamento.getText().toString());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
                    s = areaRealizadaApontamento.getText().toString();
                    if (!s.trim().isEmpty()) oSAtividadesDiaAtual.setAREA_REALIZADA(Double.valueOf(s));
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);
                    oSAtividadesDiaAtual.setHO_ESCAVADEIRA(HOEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHO(HOApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM(HMApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHH(HHApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM_ESCAVADEIRA(HMEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setOBSERVACAO(obsApontamento.getText().toString());
                    viewModelO_s_atividades_dia.insert(oSAtividadesDiaAtual);
                }
                if(oSAtividadesDiaAtual!=null && oSAtividadesDiaAtual.getDATA() == dataApontamento.getText().toString()){
                    String s;
                    oSAtividadesDiaAtual.setDATA(dataApontamento.getText().toString());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
                    s = areaRealizadaApontamento.getText().toString();
                    if (!s.trim().isEmpty()) oSAtividadesDiaAtual.setAREA_REALIZADA(Double.valueOf(s));
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);
                    oSAtividadesDiaAtual.setHO_ESCAVADEIRA(HOEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHO(HOApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM(HMApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHH(HHApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM_ESCAVADEIRA(HMEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setOBSERVACAO(obsApontamento.getText().toString());
                    viewModelO_s_atividades_dia.update(oSAtividadesDiaAtual);
                }else{
                    oSAtividadesDiaAtual = new O_S_ATIVIDADES_DIA();
                    String s;

                    oSAtividadesDiaAtual.setID_PROGRAMACAO_ATIVIDADE(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());
                    oSAtividadesDiaAtual.setDATA(dataApontamento.getText().toString());
                    oSAtividadesDiaAtual.setID_PRESTADOR(posicaoPrestador);
                    oSAtividadesDiaAtual.setID_RESPONSAVEL(posicaoResponsavel);
                    s = areaRealizadaApontamento.getText().toString();
                    if (!s.trim().isEmpty()) oSAtividadesDiaAtual.setAREA_REALIZADA(Double.valueOf(s));
                    else oSAtividadesDiaAtual.setAREA_REALIZADA(null);
                    oSAtividadesDiaAtual.setHO_ESCAVADEIRA(HOEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHO(HOApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM(HMApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHH(HHApontamento.getText().toString());
                    oSAtividadesDiaAtual.setHM_ESCAVADEIRA(HMEscavadeiraApontamento.getText().toString());
                    oSAtividadesDiaAtual.setOBSERVACAO(obsApontamento.getText().toString());
                    viewModelO_s_atividades_dia.insert(oSAtividadesDiaAtual);
                }
            }
        });

        botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });
    }

    public void populaInfo(){
        posicaoPrestador = oSAtividadesDiaAtual.getID_PRESTADOR();
        posicaoResponsavel = oSAtividadesDiaAtual.getID_RESPONSAVEL();

        dataApontamento.setText(oSAtividadesDiaAtual.getDATA());

        spinnerResponsavel.setSelection(oSAtividadesDiaAtual.getID_RESPONSAVEL() - 1, true);
        spinnerPrestador.setSelection(oSAtividadesDiaAtual.getID_PRESTADOR() - 1, true);

        if (oSAtividadesDiaAtual.getAREA_REALIZADA()!=null)
            areaRealizadaApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getAREA_REALIZADA()));

        if (!oSAtividadesDiaAtual.getHO().trim().isEmpty())
            HOApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHO()));

        if (!oSAtividadesDiaAtual.getHH().trim().isEmpty())
            HHApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHH()));

        if (!oSAtividadesDiaAtual.getHM().trim().isEmpty())
            HMApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHM()));

        if (!oSAtividadesDiaAtual.getHM_ESCAVADEIRA().trim().isEmpty())
            HMEscavadeiraApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHM_ESCAVADEIRA()));

        if (!oSAtividadesDiaAtual.getHO_ESCAVADEIRA().trim().isEmpty())
            HOEscavadeiraApontamento.setText(String.valueOf(oSAtividadesDiaAtual.getHO_ESCAVADEIRA()));

        if (!oSAtividadesDiaAtual.getOBSERVACAO().trim().isEmpty())
            obsApontamento.setText(oSAtividadesDiaAtual.getOBSERVACAO());
    }

    public static class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {

            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);
            mes += 1;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, dia, mes, ano);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }


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

        public void setarData(String dia, String mes, String ano) {
            dataApontamento.setText(dia + "/" + mes + "/" + ano);
        }
    }
}
