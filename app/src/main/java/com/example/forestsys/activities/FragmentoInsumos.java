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

import java.util.List;

import static com.example.forestsys.activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.activities.ActivityRegistros.dataDoApontamento;
import static com.example.forestsys.activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.ActivityRegistros.pegaDescInsumos;


public class FragmentoInsumos extends Fragment {

    private NDSpinner spinnerInsumos;
    private RecyclerView recyclerView;
    private static List<Join_OS_INSUMOS> listaJoinOsInsumos;
    private BaseDeDados baseDeDados;
    private DAO dao;
    private int contSpinnerInsumos;
    private AdaptadorFragmentoInsumos adaptador;
    private ArrayAdapter<Join_OS_INSUMOS> adapterInsumos;
    private DataHoraAtual dataHoraAtual;
    public static EditText obsInsumo1;
    public static EditText obsInsumo2;
    private TextView nomeInsumo1;
    private TextView nomeInsumo2;

    public boolean editouAmbos;
    private int editouUm;
    private int contEdicao;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragmento_insumos, container, false);

        recyclerView = root.findViewById(R.id.recycler_lista_insumos);

        adaptador = new AdaptadorFragmentoInsumos();
        recyclerView.setAdapter(adaptador);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        dataHoraAtual = new DataHoraAtual();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editouUm = 0;
        editouAmbos = false;
        contEdicao = 0;
        baseDeDados = BaseDeDados.getInstance(getContext());
        dao = baseDeDados.dao();
        spinnerInsumos = getView().findViewById(R.id.spinner_fragmento_insumos);
        obsInsumo1 = getView().findViewById(R.id.obs_insumo1);
        obsInsumo2 = getView().findViewById(R.id.obs_insumo2);
        nomeInsumo1 = getView().findViewById(R.id.text_fragmento_insumo1);
        nomeInsumo2 = getView().findViewById(R.id.text_fragmento_insumo2);

        if(editouRegistro == true){
            obsInsumo2.setFocusable(false);
            obsInsumo1.setFocusable(false);
        }

        if(savedInstanceState!=null){
            editouUm = savedInstanceState.getInt("editouUm");
            editouAmbos = savedInstanceState.getBoolean("editouAmbos");
            contEdicao = savedInstanceState.getInt("contEdicao");

             obsInsumo1.setText(savedInstanceState.getString("obsInsumo1"));
             obsInsumo2.setText(savedInstanceState.getString("obsInsumo2"));

        }
            listaJoinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        if (editouRegistro) listaJoinOsInsumosSelecionados = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), dataHoraAtual.formataDataDb(dataDoApontamento));

        nomeInsumo1.setText("Observação - "+pegaDescInsumos[0]+":");
        nomeInsumo2.setText("Observação - "+pegaDescInsumos[1]+":");

        if(!listaJoinOsInsumosSelecionados.isEmpty()) {
            obsInsumo1.setText(listaJoinOsInsumosSelecionados.get(0).getOBSERVACAO());
            obsInsumo2.setText(listaJoinOsInsumosSelecionados.get(1).getOBSERVACAO());
        }
        adaptador.setInsumos(listaJoinOsInsumosSelecionados);

        contSpinnerInsumos = 0;

        adapterInsumos = new ArrayAdapter<Join_OS_INSUMOS>(getActivity(),
                android.R.layout.simple_spinner_item, listaJoinOsInsumos);
        adapterInsumos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerInsumos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contSpinnerInsumos == 0) spinnerInsumos.setAdapter(adapterInsumos);
                return false;
            }
        });

        spinnerInsumos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (contSpinnerInsumos > 0) {
                    position++;
                    Join_OS_INSUMOS aux = (Join_OS_INSUMOS) parent.getSelectedItem();
                    if (aux.getID_INSUMO() == 0)
                        aux.setID_INSUMO(dao.consultaDesc(aux.getDESCRICAO()));
                    boolean jaTem = false;
                    for (int i = 0; i < listaJoinOsInsumosSelecionados.size(); i++) {
                        if (listaJoinOsInsumosSelecionados.get(i).getID_INSUMO() == aux.getID_INSUMO())
                            jaTem = true;
                    }
                    if (jaTem == true) {
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Erro!")
                                .setMessage("Insumo Já Aplicado.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    } else {
                        abreDialogoQtdAlicada(aux);
                    }
                }
                contSpinnerInsumos++;
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
                for (int i = 0; i < s.length; i++) {
                    if (s[i] == '.') contador++;
                }
                if (contador > 1 || s[s.length - 1] == '.' || s[0] == '.')
                    valor.setError("Valor inválido");
                else {
                    insere.setQTD_APLICADO(Double.valueOf(str));
                    if (!listaJoinOsInsumosSelecionados.contains(insere))
                        listaJoinOsInsumosSelecionados.add(insere);
                    else {
                        int id = listaJoinOsInsumosSelecionados.indexOf(insere);
                        listaJoinOsInsumosSelecionados.set(id, insere);
                    }
                    if (editouRegistro == true){
                        abreDialogoEdicaoIns1(insere);
                    }

                    adaptador.setInsumos(listaJoinOsInsumosSelecionados);
                    dialog.dismiss();
                }
            }
        });
    }

    public void abreDialogoEdicaoIns1(Join_OS_INSUMOS insere) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialogo_editar_insumos, null);
        final EditText valor = mView.findViewById(R.id.valor_dialogo_editar_insumos);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_editar_insumos);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valor.getText().toString();

                if (str.trim().length() < 3)
                    valor.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    int id = listaJoinOsInsumosSelecionados.indexOf(insere);

                    String obs = listaJoinOsInsumosSelecionados.get(id).getOBSERVACAO();
                    String pegaObs="";
                    if(!obs.isEmpty() || obs!=null) pegaObs = obs+"\n";
                    obs = pegaObs.concat("Editado em "+ dataHoraAtual.dataAtual()+" ás "+dataHoraAtual.horaAtual()+". Justificativa: "+(valor.getText().toString()));
                    insere.setOBSERVACAO(obs);
                    insere.setACAO_INATIVO("EDICAO");
                    listaJoinOsInsumosSelecionados.set(id, insere);

                    adaptador.setInsumos(listaJoinOsInsumosSelecionados);
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String str1 = obsInsumo1.getText().toString();
        String str2 = obsInsumo2.getText().toString();

        if(str1.isEmpty() || str1 == null) str1="";
        outState.putString("obsInsumo1", str1);

        if(str2.isEmpty() || str2 == null)str2="";
        outState.putString("obsInsumo2", str2);

        outState.putInt("editouUm", editouUm);
        outState.putBoolean("editouAmbos", editouAmbos);
        outState.putInt("contEdicao", contEdicao);
    }
}
