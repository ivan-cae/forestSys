package com.example.forestsys.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.ApplicationTodos;
import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.PRESTADORES;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorApontamentos extends RecyclerView.Adapter<AdaptadorApontamentos.ApontamentosHolder>{

    private List<O_S_ATIVIDADES_DIA> apontamentos = new ArrayList<>();
    private DAO dao;
    private OnItemClickListener listener;
    Context context = ApplicationTodos.getAppContext();

    @NonNull
    @Override
    public AdaptadorApontamentos.ApontamentosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_apontamentos, parent, false);

        return new AdaptadorApontamentos.ApontamentosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorApontamentos.ApontamentosHolder holder, int position) {

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        O_S_ATIVIDADES_DIA oSAtividadesDia = apontamentos.get(position);

        GGF_USUARIOS ggf_usuarios = dao.selecionaUser(oSAtividadesDia.getID_RESPONSAVEL());
        PRESTADORES prestadores = dao.selecionaPrestador(oSAtividadesDia.getID_PRESTADOR());
        holder.data.setText((oSAtividadesDia.getDATA()));
        holder.responsavel.setText((ggf_usuarios.getDESCRICAO()));
        holder.prestador.setText(prestadores.getDESCRICAO());
        holder.ho.setText(oSAtividadesDia.getHO());
        holder.hm.setText(oSAtividadesDia.getHM());
        holder.hh.setText(oSAtividadesDia.getHH());
        holder.hoe.setText(oSAtividadesDia.getHO_ESCAVADEIRA());
        holder.hme.setText(oSAtividadesDia.getHM_ESCAVADEIRA());
    }

    @Override
    public int getItemCount() {
        return apontamentos.size();
    }

    public void setApontamentos(List<O_S_ATIVIDADES_DIA> apontamentos) {
        this.apontamentos = apontamentos;
        notifyDataSetChanged();
    }

    class ApontamentosHolder extends RecyclerView.ViewHolder {
        TextView data;
        TextView responsavel;
        TextView prestador;
        TextView ho;
        TextView hh;
        TextView hm;
        TextView hoe;
        TextView hme;


        public ApontamentosHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.item_apontamento_data);
            responsavel = itemView.findViewById(R.id.item_apontamento_responsavel);
            prestador = itemView.findViewById(R.id.item_apontamento_prestador);
            ho = itemView.findViewById(R.id.item_apontamento_ho);
            hh = itemView.findViewById(R.id.item_apontamento_hh);
            hm = itemView.findViewById(R.id.item_apontamento_hm);
            hoe = itemView.findViewById(R.id.item_apontamento_hoe);
            hme = itemView.findViewById(R.id.item_apontamento_hme);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(apontamentos.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(O_S_ATIVIDADES_DIA oSAtividadesDia);
    }

    public void setOnItemClickListener(AdaptadorApontamentos.OnItemClickListener listener) {
        this.listener = listener;
    }
}