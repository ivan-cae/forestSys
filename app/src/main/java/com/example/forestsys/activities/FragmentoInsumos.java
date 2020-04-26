package com.example.forestsys.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.forestsys.Adapters.AdaptadorFragmentoInsumos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.NDSpinner;
import com.example.forestsys.R;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;

import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityRegistros.dataDoApontamento;
import static com.example.forestsys.activities.ActivityRegistros.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.ActivityRegistros.primeiraIns;

public class FragmentoInsumos extends Fragment {
    private NDSpinner spinnerInsumos;
    private RecyclerView recyclerView;
    private TextView dataInsumos;
    private static List<Join_OS_INSUMOS> listaJoinOsInsumos;
    private BaseDeDados baseDeDados;
    private DAO dao;
    private int cont;
    private AdaptadorFragmentoInsumos adaptador;
    private ArrayAdapter<Join_OS_INSUMOS> adapterInsumos;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragmento_insumos, container, false);

        recyclerView = root.findViewById(R.id.recycler_lista_insumos);

        adaptador = new AdaptadorFragmentoInsumos();
        recyclerView.setAdapter(adaptador);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseDeDados = BaseDeDados.getInstance(getContext());
        dao = baseDeDados.dao();
        dataInsumos = getView().findViewById(R.id.data_fragmento_insumos);
        spinnerInsumos = getView().findViewById(R.id.spinner_fragmento_insumos);

        dataInsumos.setText(dataDoApontamento);

        listaJoinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        if(!dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), listaJoinOsInsumos.get(0).getDATA()).isEmpty())
            listaJoinOsInsumos = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), listaJoinOsInsumos.get(0).getDATA());

        if(primeiraIns == true){
            listaJoinOsInsumosSelecionados = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataDoApontamento);
            primeiraIns = false;
        }

        adaptador.setInsumos(listaJoinOsInsumosSelecionados);

        cont = 0;

        adapterInsumos = new ArrayAdapter<Join_OS_INSUMOS>(getActivity(),
                android.R.layout.simple_spinner_item, listaJoinOsInsumos);
        adapterInsumos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerInsumos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(cont==0)spinnerInsumos.setAdapter(adapterInsumos);
                return false;
            }
        });

        spinnerInsumos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(cont>0){
                            position++;
                            Join_OS_INSUMOS aux = (Join_OS_INSUMOS) parent.getSelectedItem();
                            if(aux.getID_INSUMO() == 0) aux.setID_INSUMO(dao.consultaDesc(aux.getDESCRICAO()));
                            Log.e("Teste Insumo", String.valueOf(aux.getID_INSUMO()));
                            boolean jaTem = false;
                            for(int i = 0; i<listaJoinOsInsumosSelecionados.size(); i++){
                                    if(listaJoinOsInsumosSelecionados.get(i).getID_INSUMO() == aux.getID_INSUMO()) jaTem = true;
                            }
                                if(jaTem==true){
                                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                            .setTitle("Erro!")
                                            .setMessage("Insumo Já Aplicado.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                }
                                            }).create();
                                    dialog.show();
                                }
                                else{
                                    abreDialogoQtdAlicada(aux);
                                }
                            }
                        cont++;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        adaptador.setOnItemClickListener(new AdaptadorFragmentoInsumos.OnItemClickListener() {
            @Override
            public void onItemClick(Join_OS_INSUMOS joinOsInsumos) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Editar")
                        .setMessage("Deseja Editar a Quantidade Aplicada?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                abreDialogoQtdAlicada(joinOsInsumos);
                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
            }
        });
    }



    public void abreDialogoQtdAlicada(Join_OS_INSUMOS insere) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialogo_registros_insumos, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_insumos);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_insumos);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valor.getText().toString();
                str = str.replace(',', '.');
                char[] s = str.toCharArray();
                int contador = 0;
                for(int i = 0; i<s.length; i++){
                    if(s[i] =='.') contador++;
                }
                if(contador>1 || s[s.length-1]=='.' || s[0]=='.') valor.setError("Valor inválido");
                else{
                insere.setQTD_APLICADO(Double.valueOf(str));
                if(!listaJoinOsInsumosSelecionados.contains(insere)) listaJoinOsInsumosSelecionados.add(insere);
                else{
                    int id = listaJoinOsInsumosSelecionados.indexOf(insere);
                    listaJoinOsInsumosSelecionados.set(id, insere);
                }
                adaptador.setInsumos(listaJoinOsInsumosSelecionados);
                dialog.dismiss();
            }
            }
        });
    }
}
