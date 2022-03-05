package com.example.forestsys.Adapters;

import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.Assets.ApplicationTodos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.Classes.O_S_ATIVIDADES;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de atividades exibida na ActivityMain e realizar suas interações
 */
public class AdaptadorOs extends RecyclerView.Adapter<AdaptadorOs.OsHolder> implements Filterable {
    private List<O_S_ATIVIDADES> ordens = new ArrayList<>();
    private List<O_S_ATIVIDADES> ordensFiltradas;
    private OnItemClickListener listener;
    private int corFundo;
    private O_S_ATIVIDADES ordem;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private Context context = ApplicationTodos.getAppContext();

    @NonNull
    @Override
    public OsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_os, parent, false);

        return new OsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OsHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        ordem = ordens.get(position);

        // aberta-verde
        if (ordem.getSTATUS_NUM() == 0) holder.iconeStatus.setBackgroundResource(R.drawable.status_nao_iniciado);
        //andamento-bege
        if (ordem.getSTATUS_NUM() == 1) holder.iconeStatus.setBackgroundResource(R.drawable.status_andamento);
        //azul-finalizada
        if (ordem.getSTATUS_NUM() == 2) holder.iconeStatus.setBackgroundResource(R.drawable.status_finalizado);


        holder.status.setText(ordem.getSTATUS());
        holder.setor.setText(String.valueOf(dao.selecionaSetor(ordem.getID_SETOR()).getDESCRICAO()));
        holder.data.setText(String.valueOf(ferramentas.formataDataTextView(ordem.getDATA_PROGRAMADA())));
        holder.talhao.setText(String.valueOf(ordem.getTALHAO()));
        holder.area.setText(String.valueOf(ordem.getAREA_PROGRAMADA()).replace(".", ","));
        holder.manejo.setText(dao.selecionaManejo(ordem.getID_MANEJO()).getDESCRICAO());

        String temMadeira = "NÃO";
        if (ordem.getMADEIRA_NO_TALHAO() == 1) temMadeira = "SIM";
        holder.madeira.setText(temMadeira);
        String prio = "Baixa";

        if (ordem.getPRIORIDADE() == 2) prio = "Normal";
        if (ordem.getPRIORIDADE() == 3) prio = "Alta";
        if (ordem.getPRIORIDADE() == 4) prio = "Nenhuma";
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return ordens.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setOrdens(List<O_S_ATIVIDADES> ordens) {
        this.ordens = ordens;
        ordensFiltradas = new ArrayList<>(ordens);
        notifyDataSetChanged();
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class OsHolder extends RecyclerView.ViewHolder {
        private TextView status;
        private TextView madeira;
        private TextView setor;
        private TextView talhao;
        private TextView area;
        private TextView data;
        private TextView manejo;
        private ImageView iconeStatus;

        public OsHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status_item_lista);
            setor = itemView.findViewById(R.id.setor_item_lista);
            talhao = itemView.findViewById(R.id.talhao_item_lista);
            area = itemView.findViewById(R.id.area_item_lista);
            data = itemView.findViewById(R.id.data_item_lista);
            madeira = itemView.findViewById(R.id.madeira_item_lista);
            manejo = itemView.findViewById(R.id.manejo_item_lista);
            iconeStatus = itemView.findViewById(R.id.icone_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(ordens.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(O_S_ATIVIDADES ordemServico);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /*
     * Sobrescrita do método getFilter para que retorne o filtro criado abaixo ou invés de utilizar o padrão
    */
    @Override
    public Filter getFilter() {
        return filtro;
    }

    /*
     * Classe responsável por efetuar a busca(ou filtragem) de acordo com os parâmetros definidos através
     da sobreescrita do método performFiltering
     Retorna: Um filtro personalizado que nesse caso efetua a busca na lista tratada pelo Adapter a partir
     dos atributos "PERIODO_PROGRAMADO", "SETOR" ou "TALHAO" da tabela O_S_ATIVIDADES
    */
    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<O_S_ATIVIDADES> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(ordensFiltradas);
            } else {
                String filtro = constraint.toString().toLowerCase();
                for (O_S_ATIVIDADES os : ordensFiltradas) {
                    if (String.valueOf(os.getTALHAO().toLowerCase()).contains(filtro)) {
                        listaFiltrada.add(os);
                    } else if (String.valueOf(dao.selecionaSetor(os.getID_SETOR()).getDESCRICAO().toLowerCase()).contains(filtro)) {
                        listaFiltrada.add(os);
                    } else if (String.valueOf(ferramentas.formataDataTextView(os.getDATA_PROGRAMADA().toLowerCase())).contains(filtro)) {
                        listaFiltrada.add(os);
                    } else if (String.valueOf(os.getPRIORIDADE()).toLowerCase().contains(filtro)) {
                        listaFiltrada.add(os);
                    }
                }
            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ordens.clear();
            if (results.values != null) {
                ordens.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
