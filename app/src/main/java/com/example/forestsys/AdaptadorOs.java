package com.example.forestsys;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class AdaptadorOs extends RecyclerView.Adapter<AdaptadorOs.OsHolder> implements Filterable {
    private List<ClasseOs> ordens = new ArrayList<>();
    private List<ClasseOs> ordensFiltradas;
    private OnItemClickListener listener;
    private DAO dao;
    Context context = ApplicationTodos.getAppContext();


    @NonNull
    @Override
    public OsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_os, parent, false);

        return new OsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OsHolder holder, int position) {

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        ClasseOs ordem = ordens.get(position);

        holder.status.setText(ordem.getStatus().toString());
        holder.numero.setText(String.valueOf(ordem.getId()));
        holder.atividade.setText(String.valueOf(ordem.getId_atividade()));
        holder.setor.setText(dao.selecionaSetor(ordem.getId_setor()).getSetor());
        holder.talhao.setText(String.valueOf(ordem.getId_talhao()));
        holder.area.setText(String.valueOf(ordem.getArea()));
        holder.data.setText("");
        holder.atualizacao.setText("");
    }

    @Override
    public int getItemCount() {
        return ordens.size();
    }

    public void setOrdens(List<ClasseOs> ordens) {
        this.ordens = ordens;
        ordensFiltradas = new ArrayList<>(ordens);
        notifyDataSetChanged();
    }

    class OsHolder extends RecyclerView.ViewHolder {
        private TextView status;
        private TextView numero;
        private TextView atividade;
        private TextView setor;
        private TextView talhao;
        private TextView area;
        private TextView data;
        private TextView atualizacao;


        public OsHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero_item_lista);
            status = itemView.findViewById(R.id.status_item_lista);
            atividade = itemView.findViewById(R.id.atividade_item_lista);
            setor = itemView.findViewById(R.id.setor_item_lista);
            talhao = itemView.findViewById(R.id.talhao_item_lista);
            area = itemView.findViewById(R.id.area_item_lista);
            data = itemView.findViewById(R.id.data_item_lista);
            atualizacao = itemView.findViewById(R.id.atualizacao_item_lista);

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
        void onItemClick(ClasseOs ordemServico);
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
            List<ClasseOs> listaFiltrada = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                listaFiltrada.addAll(ordensFiltradas);
            }else{
                String filtro = constraint.toString();
                for(ClasseOs os : ordensFiltradas){
                    if(String.valueOf(os.getId()).startsWith(filtro)){
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
            ordens.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}