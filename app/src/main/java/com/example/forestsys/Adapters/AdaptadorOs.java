package com.example.forestsys.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.assets.ApplicationTodos;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.classes.O_S_ATIVIDADES;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorOs extends RecyclerView.Adapter<AdaptadorOs.OsHolder> implements Filterable {
    private List<O_S_ATIVIDADES> ordens = new ArrayList<>();
    private List<O_S_ATIVIDADES> ordensFiltradas;
    private OnItemClickListener listener;
    private int corFundo;
    private O_S_ATIVIDADES ordem;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private Context context = ApplicationTodos.getAppContext();
    private Ferramentas ferramentas = new Ferramentas();

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

        //aberta-verde
        if (ordem.getSTATUS_NUM() == 0) corFundo = (Color.parseColor("#a9d18e"));
        //andamento-bege
        if (ordem.getSTATUS_NUM() == 1) corFundo = (Color.parseColor("#fff2cc"));
        //azul-finalizada
        if (ordem.getSTATUS_NUM() == 2) corFundo = (Color.parseColor("#9dc3e6"));

        holder.itemView.setBackgroundColor(corFundo);

        holder.setor.setText(String.valueOf(dao.selecionaSetor(ordem.getID_SETOR()).getDESCRICAO()));
        Ferramentas ferramentas = new Ferramentas();
        holder.data.setText(String.valueOf(ferramentas.formataDataTextView(ordem.getDATA_PROGRAMADA())));
        holder.talhao.setText(String.valueOf(ordem.getTALHAO()));
        holder.status.setText(ordem.getSTATUS());
        holder.area.setText(String.valueOf(ordem.getAREA_PROGRAMADA()).replace(".", ","));
        holder.manejo.setText(dao.selecionaManejo(ordem.getID_MANEJO()).getDESCRICAO());

        String temMadeira = "NÃO";
        if (ordem.getMADEIRA_NO_TALHAO() == 1) temMadeira = "SIM";
        holder.madeira.setText(temMadeira);
        String prio = "Baixa";

        if (ordem.getPRIORIDADE() == 2) prio = "Normal";
        if (ordem.getPRIORIDADE() == 3) prio = "Alta";
        holder.prioridade.setText(prio);
    }

    @Override
    public int getItemCount() {
        return ordens.size();
    }

    public void setOrdens(List<O_S_ATIVIDADES> ordens) {
        this.ordens = ordens;
        ordensFiltradas = new ArrayList<>(ordens);
        notifyDataSetChanged();
    }

    public List<O_S_ATIVIDADES> getOrdensFiltradas() {
        return ordensFiltradas;
    }

    class OsHolder extends RecyclerView.ViewHolder {
        private TextView status;
        private TextView madeira;
        private TextView setor;
        private TextView talhao;
        private TextView area;
        private TextView data;
        private TextView prioridade;
        private TextView manejo;


        public OsHolder(@NonNull View itemView) {
            super(itemView);


            status = itemView.findViewById(R.id.status_item_lista);
            setor = itemView.findViewById(R.id.setor_item_lista);
            talhao = itemView.findViewById(R.id.talhao_item_lista);
            area = itemView.findViewById(R.id.area_item_lista);
            data = itemView.findViewById(R.id.data_item_lista);
            prioridade = itemView.findViewById(R.id.prioridade_item_lista);
            madeira = itemView.findViewById(R.id.madeira_item_lista);
            manejo = itemView.findViewById(R.id.manejo_item_lista);

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

    @Override
    public Filter getFilter() {
        return filtro;
    }

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
            if(results.values!=null ){
                ordens.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
