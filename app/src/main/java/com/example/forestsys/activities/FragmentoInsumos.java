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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestsys.Adapters.AdaptadorFragmentoInsumos;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;

import java.util.List;

import static com.example.forestsys.activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.activities.ActivityRegistros.dataDoApontamento;
import static com.example.forestsys.activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.activities.ActivityMain.osSelecionada;
import static com.example.forestsys.activities.ActivityRegistros.pegaDescInsumos;
import static com.example.forestsys.activities.ActivityAtividades.insumoInsere;
import static com.example.forestsys.activities.ActivityAtividades.area;

public class FragmentoInsumos extends Fragment {

    private RecyclerView recyclerView;
    private static List<Join_OS_INSUMOS> listaJoinOsInsumos;
    private BaseDeDados baseDeDados;
    private DAO dao;
    private AdaptadorFragmentoInsumos adaptador;
    private ArrayAdapter<Join_OS_INSUMOS> adapterInsumos;
    private Ferramentas ferramentas;
    public static EditText obsInsumo1;
    public static EditText obsInsumo2;
    private TextView nomeInsumo1;
    private TextView nomeInsumo2;

    private EditText valorDialogoQtd;
    private EditText valorDialogoEdicao;

    private boolean abriuDialogo;
    private boolean abriuDialogoEdicao;
    private Bundle auxSavedInstanceState;
    private AlertDialog dialogoEdicao;
    private AlertDialog dialogoQtdAplicada;
    private double auxValorDialogoQtd;
    private boolean cancelouDialogoEdicao;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auxSavedInstanceState = savedInstanceState;
        View root = inflater.inflate(R.layout.fragmento_insumos, container, false);

        recyclerView = root.findViewById(R.id.recycler_lista_insumos);

        adaptador = new AdaptadorFragmentoInsumos();
        recyclerView.setAdapter(adaptador);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ferramentas = new Ferramentas();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (osSelecionada.getSTATUS_NUM() != 2) {
            abriuDialogo = false;
            abriuDialogoEdicao = false;
            cancelouDialogoEdicao = false;
            baseDeDados = BaseDeDados.getInstance(getContext());
            dao = baseDeDados.dao();
            obsInsumo1 = getView().findViewById(R.id.obs_insumo1);
            obsInsumo2 = getView().findViewById(R.id.obs_insumo2);
            nomeInsumo1 = getView().findViewById(R.id.text_fragmento_insumo1);
            nomeInsumo2 = getView().findViewById(R.id.text_fragmento_insumo2);

            if (editouRegistro == true) {
                obsInsumo2.setFocusable(false);
                obsInsumo1.setFocusable(false);
            }

            listaJoinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

            if (editouRegistro && savedInstanceState == null)
                listaJoinOsInsumosSelecionados = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), ferramentas.formataDataDb(dataDoApontamento));


            nomeInsumo1.setText("Observação - " + pegaDescInsumos[0] + ":");
            nomeInsumo2.setText("Observação - " + pegaDescInsumos[1] + ":");

            if (!listaJoinOsInsumosSelecionados.isEmpty()) {
                if (listaJoinOsInsumosSelecionados.size() >= 1)
                    obsInsumo1.setText(listaJoinOsInsumosSelecionados.get(0).getOBSERVACAO());
                if (listaJoinOsInsumosSelecionados.size() >= 2)
                    obsInsumo2.setText(listaJoinOsInsumosSelecionados.get(1).getOBSERVACAO());
            } else {
                listaJoinOsInsumosSelecionados = listaJoinOsInsumos;

                listaJoinOsInsumosSelecionados.get(0).setQTD_APLICADO(0);
                listaJoinOsInsumosSelecionados.get(1).setQTD_APLICADO(0);

                listaJoinOsInsumosSelecionados.get(0).setID_INSUMO(dao.selecionaInsumoPorRm(listaJoinOsInsumos.get(0).getID_INSUMO_RM()));
                listaJoinOsInsumosSelecionados.get(1).setID_INSUMO(dao.selecionaInsumoPorRm(listaJoinOsInsumos.get(1).getID_INSUMO_RM()));
            }

            setInsumos();

            adapterInsumos = new ArrayAdapter<Join_OS_INSUMOS>(getActivity(),
                    android.R.layout.simple_spinner_item, listaJoinOsInsumos);
            adapterInsumos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if (savedInstanceState != null) {
                obsInsumo1.setText(savedInstanceState.getString("obsInsumo1"));
                obsInsumo2.setText(savedInstanceState.getString("obsInsumo2"));

                abriuDialogo = savedInstanceState.getBoolean("abriuDialogo");
                abriuDialogoEdicao = savedInstanceState.getBoolean("abriuDialogoEdicao");

                if (abriuDialogo == true) abreDialogoQtdAplicada(insumoInsere);
                if (abriuDialogoEdicao == true)
                    abreDialogoEdicaoIns(savedInstanceState.getDouble("auxValorDialogoQtd"));
            }


            adaptador.setOnItemClickListener(new AdaptadorFragmentoInsumos.OnItemClickListener() {
                @Override
                public void onItemClick(Join_OS_INSUMOS joinOsInsumos) {
                    String auxArea = area;
                    if(area.contains(",")) auxArea = auxArea.replace(",", ".");

                    if(auxArea.isEmpty() || auxArea=="" || Double.valueOf(auxArea)==0 || Double.valueOf(auxArea)==null){
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Erro")
                                .setMessage("É Necessário preencher Corretamente a Área Antes de Adicionar Insumos.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                }else{
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Editar")
                                .setMessage("Deseja Adicionar a Quantidade Aplicada para " + joinOsInsumos.getDESCRICAO() + "?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        abreDialogoQtdAplicada(joinOsInsumos);
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
        }
    }

    //Poe a virgula automaticamente como separador decimal dos números inseridos nas caixas de texto
    //parâmetros de entrada: Uma instância de uma caixa de texto, um double representando a qtd_ha_recomendado para o insumo,
    //a string contendo os valores inseridos na caixa de texto
    public void mascaraVirgula(EditText edit, double qtd_ha_rec , CharSequence s){
        int tamanho;
        String input;

        tamanho = edit.length();
        input = s.toString();

        if (!input.isEmpty()) {

            String qtdrec = String.valueOf(Double.valueOf(area.replace(',', '.')) * qtd_ha_rec);

            String[] antesDaVirgula = qtdrec.replace('.', ',').split(",");

            edit.setFilters(new InputFilter[]{
                    new InputFilter.LengthFilter(antesDaVirgula[0].length() + 3)});

            char[] aux = input.toCharArray();
            if((tamanho+1) != antesDaVirgula[0].length() && aux[tamanho - 1] == ','){
                edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                if(tamanho == antesDaVirgula[0].length()+1) {
                    char []charAux = input.toCharArray();
                    String stringAux = String.valueOf(charAux[tamanho-1]);
                    input = input.substring(0, tamanho-1);
                    edit.setText(input + ","+ stringAux);
                    edit.setSelection(edit.getText().toString().length());
                }
            }
        }
    }

    //Abre o diálogo para preencher a quantidade aplicada de um insumo.
    //Parâmetro de entrada: instância da classe Join_OS_INSUMOS
    public void abreDialogoQtdAplicada(Join_OS_INSUMOS insere) {
        abriuDialogo = true;
        insumoInsere = insere;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialogo_registros_insumos, null);
        valorDialogoQtd = mView.findViewById(R.id.valor_dialogo_insumos);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_insumos);
        TextView titulo = mView.findViewById(R.id.textview_dialogo_insumos);
        titulo.setText("Digite A Quantidade Aplicada" + "\n" +insumoInsere.getDESCRICAO());

        valorDialogoQtd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mascaraVirgula(valorDialogoQtd, insere.getQTD_HA_RECOMENDADO(), s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoQtd") != null) {
                if (!auxSavedInstanceState.getString("valorDialogoQtd").isEmpty()) {
                    valorDialogoQtd.setText(auxSavedInstanceState.getString("valorDialogoQtd"));
                }
            }
        }

        mBuilder.setView(mView);
        dialogoQtdAplicada = mBuilder.create();
        dialogoQtdAplicada.setCanceledOnTouchOutside(false);
        dialogoQtdAplicada.show();

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valorDialogoQtd.getText().toString();
                str = str.replace(',', '.');
                char[] s = str.toCharArray();
                int contador = 0;
                for (int i = 0; i < s.length; i++) {
                    if (s[i] == '.') contador++;
                }
                if (str == null || str.isEmpty() || contador > 1 || s[s.length - 1] == '.' || s[0] == '.' || Double.valueOf(str.replace(',', '.')) == 0.0) {
                    valorDialogoQtd.setError("Valor inválido");
                } else {
                    int id = listaJoinOsInsumosSelecionados.indexOf(insumoInsere);
                    if (editouRegistro == false) {
                        insumoInsere.setQTD_APLICADO(Double.valueOf(str));
                        listaJoinOsInsumosSelecionados.set(id, insumoInsere);
                        setInsumos();
                    }


                    if (editouRegistro == true) {
                        abreDialogoEdicaoIns(Double.valueOf(str));
                    }

                    abriuDialogo = false;
                    dialogoQtdAplicada.dismiss();
                }
            }
        });

        dialogoQtdAplicada.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogo = false;
            }
        });

        dialogoQtdAplicada.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getActivity(), "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                auxSavedInstanceState = null;
                abriuDialogo = false;
            }
        });
    }

    //Abre diálogo para edição da quantidade do insumo
    //Método de entrada: uma instância da classe Join_OS_INSUMOS
    public void abreDialogoEdicaoIns(double valor) {
        abriuDialogoEdicao = true;
        auxValorDialogoQtd = valor;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialogo_editar_insumos, null);
        valorDialogoEdicao = mView.findViewById(R.id.valor_dialogo_editar_insumos);
        Button botaoOk = (Button) mView.findViewById(R.id.botao_ok_dialogo_editar_insumos);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoEdicao") != null) {
                if (!auxSavedInstanceState.getString("valorDialogoEdicao").isEmpty()) {
                    valorDialogoEdicao.setText(auxSavedInstanceState.getString("valorDialogoEdicao"));
                }
            }
        }


        mBuilder.setView(mView);
        dialogoEdicao = mBuilder.create();
        dialogoEdicao.setCanceledOnTouchOutside(false);
        dialogoEdicao.show();

        botaoOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String str = valorDialogoEdicao.getText().toString();
                int id = listaJoinOsInsumosSelecionados.indexOf(insumoInsere);
                if (str.trim().length() < 3)
                    valorDialogoEdicao.setError("Justificativa deve ter mais de 2 caracteres.");
                else {
                    insumoInsere.setQTD_APLICADO(valor);
                    String obs = listaJoinOsInsumosSelecionados.get(id).getOBSERVACAO();
                    String pegaObs = "";
                    if (!obs.isEmpty() || obs != null) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorDialogoEdicao.getText().toString()));
                    insumoInsere.setOBSERVACAO(obs);
                    insumoInsere.setACAO_INATIVO("EDICAO");

                    listaJoinOsInsumosSelecionados.set(id, insumoInsere);
                    setInsumos();
                    dialogoEdicao.dismiss();
                }
            }
        });

        dialogoEdicao.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                auxSavedInstanceState = null;
                abriuDialogoEdicao = false;
                cancelouDialogoEdicao = true;
            }
        });

        dialogoEdicao.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getActivity(), "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                cancelouDialogoEdicao = true;
                auxSavedInstanceState = null;
                abriuDialogoEdicao = false;
            }
        });
    }

    public void setInsumos(){
        adaptador.setInsumos(listaJoinOsInsumosSelecionados);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (osSelecionada.getSTATUS_NUM() != 2) {
            super.onSaveInstanceState(outState);
            String str1 = obsInsumo1.getText().toString();
            String str2 = obsInsumo2.getText().toString();

            if (str1.isEmpty() || str1 == null) str1 = "";
            outState.putString("obsInsumo1", str1);

            if (str2.isEmpty() || str2 == null) str2 = "";
            outState.putString("obsInsumo2", str2);

            outState.putBoolean("abriuDialogoEdicao", abriuDialogoEdicao);
            outState.putBoolean("abriuDialogo", abriuDialogo);

            if (abriuDialogo) {
                outState.putString("valorDialogoQtd", valorDialogoQtd.getText().toString());
                dialogoQtdAplicada.dismiss();
            }
            if (abriuDialogoEdicao) {
                outState.putString("valorDialogoEdicao", valorDialogoEdicao.getText().toString());
                outState.putDouble("auxValorDialogoQtd", auxValorDialogoQtd);
                outState.putBoolean("cancelouDialogoEdicao", cancelouDialogoEdicao);
                dialogoEdicao.dismiss();
            }
        }
    }

    public void onBackPressed() {
        if (abriuDialogoEdicao) dialogoEdicao.cancel();
        if (abriuDialogo) dialogoQtdAplicada.cancel();
    }
}
