package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.PRESTADORES;
import com.example.forestsys.repositorios.RepositorioImplementos;
import com.example.forestsys.repositorios.RepositorioMaquinas;
import com.example.forestsys.repositorios.RepositorioPrestadores;
import com.example.forestsys.repositorios.RepositorioUsers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentoApontamento extends Fragment{
    private ImageButton botaoDatePicker;
    private static TextView dataApontamento;
    private Spinner spinnerResponsavel;
    private Spinner spinnerPrestador;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragmento_apontamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botaoDatePicker = getView().findViewById(R.id.botao_date_picker);
        dataApontamento = getView().findViewById(R.id.data_apontamento);
        spinnerResponsavel = getView().findViewById(R.id.spinner_responsavel_apontamento);
        spinnerPrestador = getView().findViewById(R.id.spinner_prestador_apontamento);

        botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });


        RepositorioUsers repositorioUsers = new RepositorioUsers(getActivity().getApplication());

        RepositorioPrestadores repositorioPrestadores = new RepositorioPrestadores(getActivity().getApplication());


        ArrayList<PRESTADORES> prestadores = new ArrayList<>(repositorioPrestadores.listaPrestadores());

        ArrayList<GGF_USUARIOS> usuarios = new ArrayList<>(repositorioUsers.listaUsuarios());


        ArrayAdapter<PRESTADORES> adapterMaquinas = new ArrayAdapter<PRESTADORES>(getActivity(),
                android.R.layout.simple_spinner_item, prestadores);
        adapterMaquinas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrestador.setAdapter(adapterMaquinas);


        ArrayAdapter<GGF_USUARIOS> adapterUsuarios = new ArrayAdapter<GGF_USUARIOS>(getActivity(),
                android.R.layout.simple_spinner_item, usuarios);
        adapterUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResponsavel.setAdapter(adapterUsuarios);
    }

    public static class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);

            return new DatePickerDialog(getActivity(), this, dia, mes+1, ano);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String auxDia = String.valueOf(dayOfMonth);
            String auxMes = String.valueOf(monthOfYear);
            String auxAno = String.valueOf(year);
            setarData(auxDia, auxMes, auxAno);
        }

        public void setarData(String dia, String mes, String ano) {

            //dataApontamento.setText("aaaaa");
            dataApontamento.setText(dia+"/"+mes+"/"+ano);
        }
    }
}
