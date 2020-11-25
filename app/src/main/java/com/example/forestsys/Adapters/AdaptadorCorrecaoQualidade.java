package com.example.forestsys.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.R;
import com.example.forestsys.Assets.ApplicationTodos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.AVAL_PONTO_SUBSOLAGEM;
import com.example.forestsys.Classes.AVAL_SUBSOLAGEM;
import com.example.forestsys.Classes.CADASTRO_FLORESTAL;

import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static com.example.forestsys.Activities.ActivityAtividades.listaPontosCorrecaoAux;
import static com.example.forestsys.Activities.ActivityQualidade.botaoCorrecaoRegistrar;
import static com.example.forestsys.Activities.ActivityQualidade.naoHaNCNaoTratada;

public class AdaptadorCorrecaoQualidade extends RecyclerView.Adapter<AdaptadorCorrecaoQualidade.CorrecaoQualidadeHolder> {
    private DAO dao;
    private Context context = ApplicationTodos.getAppContext();

    private List<List<AVAL_PONTO_SUBSOLAGEM>> pontos = new ArrayList<>();

    @NonNull
    @Override
    public AdaptadorCorrecaoQualidade.CorrecaoQualidadeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_dialogo_correcao_ponto, parent, false);

        return new AdaptadorCorrecaoQualidade.CorrecaoQualidadeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCorrecaoQualidade.CorrecaoQualidadeHolder holder, int position) {
        List<AVAL_PONTO_SUBSOLAGEM> ponto = pontos.get(position);

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        int idReg = osSelecionada.getID_REGIONAL();
        int idSet = osSelecionada.getID_SETOR();
        String talhao = osSelecionada.getTALHAO();
        int ciclo = osSelecionada.getCICLO();
        int idManejo = osSelecionada.getID_MANEJO();
        int nPonto = ponto.get(0).getPONTO();
        CADASTRO_FLORESTAL cadastro_florestal = dao.selecionaCadFlorestal(idReg, idSet, talhao, ciclo, idManejo);

        String pegaEspacamento[] = dao.selecionaEspacamento(cadastro_florestal.getID_ESPACAMENTO()).getDESCRICAO().trim().replace(',', '.').split("X");
        double teste;
        try {
            teste = Double.parseDouble(pegaEspacamento[0]);
        } catch (NumberFormatException | NullPointerException n) {
            teste = 3;
        }

        int idProg = osSelecionada.getID_PROGRAMACAO_ATIVIDADE();

        AVAL_SUBSOLAGEM aval_subsolagem = dao.selecionaAvalSubsolagem(idProg);

        boolean nc1 = dao.valorNaoConformeMenor(idProg, 1, aval_subsolagem.getPROFUNDIDADE(), nPonto);
        boolean nc2 = dao.valorNaoConformeForaDaFaixa(idProg, 2, aval_subsolagem.getESTRONDAMENTO_LATERAL_INFERIOR(), aval_subsolagem.getESTRONDAMENTO_LATERAL_SUPERIOR(), nPonto);
        boolean nc3 = dao.valorNaoConformeMenor(idProg, 3, aval_subsolagem.getFAIXA_SOLO_PREPARADA(), nPonto);
        boolean nc4 = dao.valorNaoConformeForaDaFaixa(idProg, 4, aval_subsolagem.getPROFUNDIDADE_ADUBO_INFERIOR(), aval_subsolagem.getPROFUNDIDADE_ADUBO_SUPERIOR(), nPonto);
        boolean nc5 = dao.valorNaoConformebool(idProg, 5, nPonto);
        boolean nc6 = dao.valorNaoConformeForaDaFaixa(idProg, 6, teste * 0.95, teste * 1.05, nPonto);
        boolean nc7 = dao.valorNaoConformebool(idProg, 7, nPonto);
        boolean nc8 = dao.valorNaoConformebool(idProg, 8, nPonto);
        boolean nc9 = dao.valorNaoConformebool(idProg, 9, nPonto);
        boolean nc10 = dao.valorNaoConformeForaDaFaixa(idProg, 10, aval_subsolagem.getLOCALIZACAO_INSUMO_INFERIOR(), aval_subsolagem.getLOCALIZACAO_INSUMO_SUPERIOR(), nPonto);

        if (nc1 == true) {
            if (dao.indicadorCorrigivel(ponto.get(0).getID_ATIVIDADE(), ponto.get(0).getID_INDICADOR()) == 1 && ponto.get(0).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);

                holder.item1.setVisibility(View.VISIBLE);
                holder.editItem1.setVisibility(View.VISIBLE);

                holder.coordItem1.setVisibility(View.VISIBLE);

                holder.item1.setText(String.valueOf(dao.descricaoIndicador(ponto.get(0).getID_ATIVIDADE(), ponto.get(0).getID_INDICADOR())));
                if (ponto.get(0).getNC_TRATADA() == 1) {
                    holder.editItem1.setChecked(true);
                    holder.editItem1.setEnabled(false);
                }

                holder.coordItem1.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(0).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(0).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(0).getPONTO()));

                holder.editItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem1.isChecked()) listaPontosCorrecaoAux.get(position).get(0).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(0).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc2 == true) {
            if (dao.indicadorCorrigivel(ponto.get(1).getID_ATIVIDADE(), ponto.get(1).getID_INDICADOR()) == 1 && ponto.get(1).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);

                holder.item2.setVisibility(View.VISIBLE);
                holder.editItem2.setVisibility(View.VISIBLE);

                holder.coordItem2.setVisibility(View.VISIBLE);

                holder.item2.setText(String.valueOf(dao.descricaoIndicador(ponto.get(1).getID_ATIVIDADE(), ponto.get(1).getID_INDICADOR())));
                if (ponto.get(1).getNC_TRATADA() == 1) {
                    holder.editItem2.setChecked(true);
                    holder.editItem2.setEnabled(false);
                }
                holder.coordItem2.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(1).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(1).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(1).getPONTO()));

                holder.editItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem2.isChecked()) listaPontosCorrecaoAux.get(position).get(1).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(1).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc3 == true) {
            if (dao.indicadorCorrigivel(ponto.get(2).getID_ATIVIDADE(), ponto.get(2).getID_INDICADOR()) == 1 && ponto.get(2).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item3.setVisibility(View.VISIBLE);
                holder.editItem3.setVisibility(View.VISIBLE);

                holder.coordItem3.setVisibility(View.VISIBLE);

                holder.item3.setText(String.valueOf(dao.descricaoIndicador(ponto.get(2).getID_ATIVIDADE(), ponto.get(2).getID_INDICADOR())));
                if (ponto.get(2).getNC_TRATADA() == 1){
                    holder.editItem3.setChecked(true);
                    holder.editItem3.setEnabled(false);
                }
                holder.coordItem3.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(2).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(2).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(2).getPONTO()));

                holder.editItem3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem3.isChecked()) listaPontosCorrecaoAux.get(position).get(2).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(2).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc4 == true) {
            if (dao.indicadorCorrigivel(ponto.get(3).getID_ATIVIDADE(), ponto.get(3).getID_INDICADOR()) == 1 && ponto.get(3).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item4.setVisibility(View.VISIBLE);
                holder.editItem4.setVisibility(View.VISIBLE);

                holder.coordItem4.setVisibility(View.VISIBLE);

                holder.item4.setText(String.valueOf(dao.descricaoIndicador(ponto.get(3).getID_ATIVIDADE(), ponto.get(3).getID_INDICADOR())));
                if (ponto.get(3).getNC_TRATADA() == 1) {
                    holder.editItem4.setChecked(true);
                    holder.editItem4.setEnabled(false);
                }
                holder.coordItem4.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(3).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(3).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(3).getPONTO()));

                holder.editItem4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem4.isChecked()) listaPontosCorrecaoAux.get(position).get(3).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(3).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc5 == true) {
            if (dao.indicadorCorrigivel(ponto.get(4).getID_ATIVIDADE(), ponto.get(4).getID_INDICADOR()) == 1 && ponto.get(4).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.editItem5.setVisibility(View.VISIBLE);

                holder.coordItem5.setVisibility(View.VISIBLE);

                holder.item5.setText(String.valueOf(dao.descricaoIndicador(ponto.get(4).getID_ATIVIDADE(), ponto.get(4).getID_INDICADOR())));
                if (ponto.get(4).getNC_TRATADA() == 1) {
                    holder.editItem5.setChecked(true);
                    holder.editItem5.setEnabled(false);
                }

                holder.coordItem5.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(4).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(4).getCOORDENADA_Y()))));
                holder.numeroPonto.setText(String.valueOf(ponto.get(4).getPONTO()));

                holder.editItem5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem5.isChecked()) listaPontosCorrecaoAux.get(position).get(4).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(4).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc6 == true) {
            if (dao.indicadorCorrigivel(ponto.get(5).getID_ATIVIDADE(), ponto.get(5).getID_INDICADOR()) == 1  && ponto.get(5).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item6.setVisibility(View.VISIBLE);
                holder.editItem6.setVisibility(View.VISIBLE);

                holder.coordItem6.setVisibility(View.VISIBLE);

                holder.item6.setText(String.valueOf(dao.descricaoIndicador(ponto.get(5).getID_ATIVIDADE(), ponto.get(5).getID_INDICADOR())));
                if (ponto.get(5).getNC_TRATADA() == 1){
                    holder.editItem6.setChecked(true);
                    holder.editItem6.setEnabled(false);
                }
                holder.coordItem6.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(5).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(5).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(5).getPONTO()));

                holder.editItem6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem6.isChecked()) listaPontosCorrecaoAux.get(position).get(5).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(5).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc7 == true) {
            if (dao.indicadorCorrigivel(ponto.get(6).getID_ATIVIDADE(), ponto.get(6).getID_INDICADOR()) == 1 && ponto.get(6).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item7.setVisibility(View.VISIBLE);
                holder.editItem7.setVisibility(View.VISIBLE);

                holder.coordItem7.setVisibility(View.VISIBLE);

                holder.item7.setText(dao.descricaoIndicador(ponto.get(6).getID_ATIVIDADE(), ponto.get(6).getID_INDICADOR()));
                if (ponto.get(6).getNC_TRATADA() == 1) {
                    holder.editItem7.setChecked(true);
                    holder.editItem7.setEnabled(false);
                }
                holder.coordItem7.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(6).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(6).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(6).getPONTO()));

                holder.editItem7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem7.isChecked()) listaPontosCorrecaoAux.get(position).get(6).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(6).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc8 == true) {
            if (dao.indicadorCorrigivel(ponto.get(7).getID_ATIVIDADE(), ponto.get(7).getID_INDICADOR()) == 1 && ponto.get(7).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item8.setVisibility(View.VISIBLE);
                holder.editItem8.setVisibility(View.VISIBLE);

                holder.coordItem8.setVisibility(View.VISIBLE);

                holder.item8.setText(dao.descricaoIndicador(ponto.get(7).getID_ATIVIDADE(), ponto.get(7).getID_INDICADOR()));
                if (ponto.get(7).getNC_TRATADA() == 1) {
                    holder.editItem8.setChecked(true);
                    holder.editItem8.setEnabled(false);
                    }
                holder.coordItem8.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(7).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(7).getCOORDENADA_Y()))));


                holder.numeroPonto.setText(String.valueOf(ponto.get(7).getPONTO()));

                holder.editItem8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem8.isChecked()) listaPontosCorrecaoAux.get(position).get(7).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(7).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc9 == true) {
            if (dao.indicadorCorrigivel(ponto.get(8).getID_ATIVIDADE(), ponto.get(8).getID_INDICADOR()) == 1 && ponto.get(8).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item9.setVisibility(View.VISIBLE);
                holder.editItem9.setVisibility(View.VISIBLE);

                holder.coordItem9.setVisibility(View.VISIBLE);

                holder.item9.setText(dao.descricaoIndicador(ponto.get(8).getID_ATIVIDADE(), ponto.get(8).getID_INDICADOR()));
                if (ponto.get(8).getNC_TRATADA() == 1) {
                    holder.editItem9.setChecked(true);
                    holder.editItem9.setEnabled(false);
                }
                holder.coordItem9.setText(formataLatLong(String.valueOf("Lat: " + ponto.get(8).getCOORDENADA_X())) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(8).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(8).getPONTO()));

                holder.editItem9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem9.isChecked()) listaPontosCorrecaoAux.get(position).get(8).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(8).setNC_TRATADA(0);
                    }
                });
            }
        }

        if (nc10 == true) {
            if (dao.indicadorCorrigivel(ponto.get(9).getID_ATIVIDADE(), ponto.get(9).getID_INDICADOR()) == 1 && ponto.get(9).getNC_TRATADA() == 0) {
                naoHaNCNaoTratada.setVisibility(View.GONE);
                botaoCorrecaoRegistrar.setVisibility(View.VISIBLE);

                holder.numeroPonto.setVisibility(View.VISIBLE);
                holder.textViewPonto.setVisibility(View.VISIBLE);
                holder.textViewCorrecao.setVisibility(View.VISIBLE);
                holder.textViewCoordenadas.setVisibility(View.VISIBLE);
                holder.item10.setVisibility(View.VISIBLE);
                holder.editItem10.setVisibility(View.VISIBLE);


                holder.item10.setText(String.valueOf(dao.descricaoIndicador(ponto.get(9).getID_ATIVIDADE(), ponto.get(9).getID_INDICADOR())));
                if (ponto.get(9).getNC_TRATADA() == 1) {
                    holder.editItem10.setChecked(true);
                    holder.editItem10.setEnabled(false);
                    }
                holder.coordItem10.setText("Lat: " + formataLatLong(String.valueOf((ponto.get(9).getCOORDENADA_X()))) + "\n Long: " + formataLatLong(String.valueOf((ponto.get(9).getCOORDENADA_Y()))));

                holder.numeroPonto.setText(String.valueOf(ponto.get(9).getPONTO()));

                holder.editItem10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.editItem10.isChecked()) listaPontosCorrecaoAux.get(position).get(9).setNC_TRATADA(1);
                        else listaPontosCorrecaoAux.get(position).get(9).setNC_TRATADA(0);
                    }
                });
            }
        }
    }

public String formataLatLong(String s){
//Log.e("LatLong", s);
        if(s.length()<5) return s.replace(".", ",")+"°";
        if(s.length()>5) {
            String[] antesDaVirgula = s.replace('.', ',').split(",");
            String aux1 = antesDaVirgula[0];
            String aux2 = antesDaVirgula[1];
            if(aux2.length()>4) aux2 = antesDaVirgula[1].substring(0, 4);
            aux1 += "," + aux2 + "°";
            return aux1;
        }
        return s;
}
    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public void setCorrecao(List<List<AVAL_PONTO_SUBSOLAGEM>> pontos) {
        this.pontos = pontos;
    }

    class CorrecaoQualidadeHolder extends RecyclerView.ViewHolder {

        private TextView numeroPonto;
        private TextView textViewPonto;
        private TextView textViewCorrecao;
        private TextView textViewCoordenadas;

        private TextView item1;
        private TextView item2;
        private TextView item3;
        private TextView item4;
        private TextView item5;
        private TextView item6;
        private TextView item7;
        private TextView item8;
        private TextView item9;
        private TextView item10;

        private CheckBox editItem1;
        private CheckBox editItem2;
        private CheckBox editItem3;
        private CheckBox editItem4;
        private CheckBox editItem5;
        private CheckBox editItem6;
        private CheckBox editItem7;
        private CheckBox editItem8;
        private CheckBox editItem9;
        private CheckBox editItem10;

        private TextView coordItem1;
        private TextView coordItem2;
        private TextView coordItem3;
        private TextView coordItem4;
        private TextView coordItem5;
        private TextView coordItem6;
        private TextView coordItem7;
        private TextView coordItem8;
        private TextView coordItem9;
        private TextView coordItem10;

        public CorrecaoQualidadeHolder(@NonNull View itemView) {
            super(itemView);

            listaPontosCorrecaoAux = pontos;

            numeroPonto = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_numero);
            textViewPonto = itemView.findViewById(R.id.dialogo_qualidade_ponto_textview);
            textViewCorrecao = itemView.findViewById(R.id.qualidade_correcao_textview_correcao);
            textViewCoordenadas = itemView.findViewById(R.id.qualidade_correcao_textview_coordenadas);

            item1 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item1);
            item2 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item2);
            item3 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item3);
            item4 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item4);
            item5 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item5);
            item6 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item6);
            item7 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item7);
            item8 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item8);
            item9 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item9);
            item10 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_item10);

            editItem1 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item1);
            editItem2 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item2);
            editItem3 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item3);
            editItem4 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item4);
            editItem5 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item5);
            editItem6 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item6);
            editItem7 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item7);
            editItem8 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item8);
            editItem9 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item9);
            editItem10 = itemView.findViewById(R.id.dialogo_qualidade_ponto_correcao_edit_item10);

            coordItem1 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_1);
            coordItem2 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_2);
            coordItem3 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_3);
            coordItem4 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_4);
            coordItem5 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_5);
            coordItem6 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_6);
            coordItem7 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_7);
            coordItem8 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_8);
            coordItem9 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_9);
            coordItem10 = itemView.findViewById(R.id.qualidade_correcao_coordenada_item_10);
        }
    }
}
