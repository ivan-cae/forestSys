package com.example.forestsys.Activities;

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
import android.text.method.ScrollingMovementMethod;
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
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo1;
import static com.example.forestsys.Activities.ActivityAtividades.editouInsumo2;
import static com.example.forestsys.Activities.ActivityAtividades.editouRegistro;
import static com.example.forestsys.Activities.ActivityAtividades.joinOsInsumos;
import static com.example.forestsys.Activities.ActivityRegistros.dataDoApontamento;
import static com.example.forestsys.Activities.ActivityAtividades.listaJoinOsInsumosSelecionados;
import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityAtividades.insumoInsere;
import static com.example.forestsys.Activities.ActivityAtividades.area;

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

            obsInsumo1.setMovementMethod(new ScrollingMovementMethod());
            obsInsumo2.setMovementMethod(new ScrollingMovementMethod());

            listaJoinOsInsumos = dao.listaJoinInsumoAtividades(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

            if (editouRegistro && savedInstanceState == null) {
                listaJoinOsInsumosSelecionados = dao.listaJoinInsumoAtividadesdia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), ferramentas.formataDataDb(dataDoApontamento));
            }

            if (listaJoinOsInsumosSelecionados.size()>0) {
                if (listaJoinOsInsumosSelecionados.size() >= 1)
                    obsInsumo1.setText(listaJoinOsInsumosSelecionados.get(0).getOBSERVACAO());
                if (listaJoinOsInsumosSelecionados.size() >= 2)
                    obsInsumo2.setText(listaJoinOsInsumosSelecionados.get(1).getOBSERVACAO());
            } else {
                listaJoinOsInsumosSelecionados = listaJoinOsInsumos;

                listaJoinOsInsumosSelecionados.get(0).setQTD_APLICADO(0.0);
                listaJoinOsInsumosSelecionados.get(1).setQTD_APLICADO(0.0);

                listaJoinOsInsumosSelecionados.get(0).setID_INSUMO(dao.selecionaInsumoPorRm(listaJoinOsInsumos.get(0).getID_INSUMO_RM()));
                listaJoinOsInsumosSelecionados.get(1).setID_INSUMO(dao.selecionaInsumoPorRm(listaJoinOsInsumos.get(1).getID_INSUMO_RM()));
            }


            if(listaJoinOsInsumosSelecionados.size() >1) {
                nomeInsumo1.setText("OBS: " + listaJoinOsInsumosSelecionados.get(0).getDESCRICAO());
                nomeInsumo2.setText("OBS: " + listaJoinOsInsumosSelecionados.get(1).getDESCRICAO());
            }

            if(listaJoinOsInsumosSelecionados.size() == 1) {
                nomeInsumo1.setText("OBS: " + listaJoinOsInsumosSelecionados.get(0).getDESCRICAO());
            }


            nomeInsumo1.setMovementMethod(new ScrollingMovementMethod());
            nomeInsumo2.setMovementMethod(new ScrollingMovementMethod());

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
                if (abriuDialogoEdicao == true) {
                    double d;
                    d = Double.valueOf(savedInstanceState.getDouble("auxValorDialogoQtd"));
                    abreDialogoEdicaoIns(d);
                }
                }


            adaptador.setOnItemClickListener(new AdaptadorFragmentoInsumos.OnItemClickListener() {
                @Override
                public void onItemClick(Join_OS_INSUMOS joinOsInsumos) {
                    String auxArea = area;
                    if(auxArea != null ){
                        if(auxArea.length()>0 && auxArea.contains(",")) auxArea = auxArea.replace(",", ".");
                    }

                    if(auxArea == null || auxArea.length()==0 || auxArea=="" || Double.valueOf(auxArea)==0 || Double.valueOf(auxArea)==null){
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Erro")
                                .setMessage("É Necessário Preencher Corretamente a Área Antes de Adicionar Insumos.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }else{
                        abreDialogoQtdAplicada(joinOsInsumos);
                    }
                }
            });
            if(obsInsumo2.length()==0 && editouRegistro ==false) obsInsumo1.setHint("Digite as observações aqui");
            if(obsInsumo2.length()==0 && editouRegistro ==false) obsInsumo2.setHint("Digite as observações aqui");
        }

        int recP1 = 0;
        int recP2 = 0;

        try{
            if(joinOsInsumos.get(0).getQTD_HA_RECOMENDADO() != 0){
                recP1 = 1;
            }
        }catch(Exception ex){
            recP1 = 0;
        }

        try{
            if(joinOsInsumos.get(1).getQTD_HA_RECOMENDADO() !=0){
                recP2 = 1;
            }
        }catch(Exception ex){
            recP2 = 0;
        }

        if(recP1 == 0){
            obsInsumo1.setVisibility(View.GONE);
            nomeInsumo1.setVisibility(View.GONE);
        }

        if(recP2 == 0){
            obsInsumo2.setVisibility(View.GONE);
            nomeInsumo2.setVisibility(View.GONE);
        }
    }

    //Poe a virgula automaticamente como separador decimal dos números inseridos nas caixas de texto
    //parâmetros de entrada: Uma instância de uma caixa de texto, um double representando a qtd_ha_recomendado para o insumo,
    //a string contendo os valores inseridos na caixa de texto
    public static void mascaraVirgula(EditText edit, CharSequence s, int casasDecimais, String valorReferencia,
                                      int contAtual, int contAnterior) {

        /*Log.e("Digitado", s.toString());
        Log.e("Anterior", String.valueOf(contAnterior));
        Log.e("Atual", String.valueOf(contAtual));
*/
        if(casasDecimais == 0) casasDecimais = 2;

        String input = s.toString();
        String semFormatar = input.replace(",", "").trim();
        int tamanho = semFormatar.length();
        String valorFinal = semFormatar;
        char[] separaSemFormatar = semFormatar.toCharArray();
        char[] separaFormatado = input.toCharArray();
        int nCasasAntesVirgula = 2;

        valorReferencia = valorReferencia.replace(".", ",");
        String[] antesDaVirgula = valorReferencia.split(",");
        nCasasAntesVirgula = antesDaVirgula[0].length();

        if(nCasasAntesVirgula<=5) nCasasAntesVirgula = 5;

        edit.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(nCasasAntesVirgula+3)});

        if (contAnterior != contAtual) {
            if (tamanho > casasDecimais-1) {
                if (tamanho == casasDecimais) {
                    if (contAnterior>contAtual && input.contains(",")) {
                        valorFinal = "";
                        valorFinal = (separaSemFormatar[0] + "" + separaSemFormatar[1]).trim();
                    }

                    if(contAnterior<contAtual && !input.contains(",")){
                        valorFinal = separaSemFormatar[0] + "," + separaSemFormatar[1];
                    }
                }
            }

            if (tamanho > casasDecimais) {
                valorFinal = "";
                for (int i = 0; i < tamanho; i++) {
                    valorFinal += separaSemFormatar[i];
                    if (i == (tamanho - (casasDecimais+1))) {
                        valorFinal += ",";
                    }

                }
            }
            edit.setText(valorFinal.trim());
            edit.setSelection(edit.length());
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
        Button botaoOk =  mView.findViewById(R.id.botao_ok_dialogo_insumos);
        TextView titulo = mView.findViewById(R.id.textview_dialogo_insumos);
        titulo.setText("Digite A Quantidade Aplicada" + "\n" +insumoInsere.getDESCRICAO());

        valorDialogoQtd.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DecimalFormat format = new DecimalFormat(".##");

                double areaDouble = Double.valueOf(area.replace(',', '.'));

                double mult = ((long) areaDouble * (long)insere.getQTD_HA_RECOMENDADO());
                //Log.e("Valor divisao", divisao);

                BigDecimal bigDecimal = new BigDecimal(mult).setScale(2, BigDecimal.ROUND_UP);

                String qtd_ha_rec  = new DecimalFormat(".##").format(bigDecimal).replace(',', '.');

                //BigDecimal =
                //String  = format.format(String.valueOf(Double.valueOf(area.replace(',', '.')) * insere.getQTD_HA_RECOMENDADO()));

                mascaraVirgula(valorDialogoQtd, s, 2, qtd_ha_rec, count, before);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoQtd") != null) {
                if (auxSavedInstanceState.getString("valorDialogoQtd").length()>0) {
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
                if (str == null || str.length()==0 || contador > 1 || s[s.length - 1] == '.' || s[0] == '.' || Double.valueOf(str.replace(',', '.')) == 0.0) {
                    valorDialogoQtd.setError("Valor inválido");
                } else {
                    int id = listaJoinOsInsumosSelecionados.indexOf(insumoInsere);
                    if (editouRegistro == false) {
                        double pegaQtdApl = 0;
                        try{
                            pegaQtdApl = Double.valueOf(str.replace(',','.'));
                        }catch(Exception e){
                            pegaQtdApl = 0.0;
                            e.printStackTrace();
                        }
                        insumoInsere.setQTD_APLICADO(pegaQtdApl);
                        listaJoinOsInsumosSelecionados.set(id, insumoInsere);
                        setInsumos();
                    }


                    if (editouRegistro == true) {
                        double d;
                        d = Double.valueOf(str.replace(',', '.'));
                        abreDialogoEdicaoIns(d);
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


    //Abre diálogo para justificar a edição da quantidade do insumo
    //Método de entrada: um double, esse double é a quantidade aplicada na edição do insumo
    public void abreDialogoEdicaoIns(double valor) {
        abriuDialogoEdicao = true;
        auxValorDialogoQtd = valor;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialogo_frag_insumos_edicao_insumos, null);
        valorDialogoEdicao = mView.findViewById(R.id.valor_dialogo_editar_insumos);
        Button botaoOk =  mView.findViewById(R.id.botao_ok_dialogo_editar_insumos);

        if (auxSavedInstanceState != null) {
            if (auxSavedInstanceState.getString("valorDialogoEdicao") != null) {
                if (auxSavedInstanceState.getString("valorDialogoEdicao").length()>0) {
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
                    double pegaQtdApl = 0;
                    try{
                        pegaQtdApl = valor;
                    }catch(Exception e){
                        pegaQtdApl = 0.0;
                    }
                    insumoInsere.setQTD_APLICADO(pegaQtdApl);

                    String obs = listaJoinOsInsumosSelecionados.get(id).getOBSERVACAO();
                    String pegaObs = "";
                    if(obs != null)
                        if (obs.length()>0) pegaObs = obs + "\n";
                    obs = pegaObs.concat("Editado em " + ferramentas.dataAtual() + " ás " + ferramentas.horaAtual() + ". Justificativa: " + (valorDialogoEdicao.getText().toString()));
                    insumoInsere.setOBSERVACAO(obs);
                    if(id==0) editouInsumo1 = true;
                    if(id==1) editouInsumo2 = true;
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
        if (osSelecionada != null && osSelecionada.getSTATUS_NUM() != 2) {
            super.onSaveInstanceState(outState);
            String str1 = obsInsumo1.getText().toString();
            String str2 = obsInsumo2.getText().toString();

            if (str1.length()==0 || str1 == null) str1 = "";
            outState.putString("obsInsumo1", str1);

            if (str2.length()==0 || str2 == null) str2 = "";
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
