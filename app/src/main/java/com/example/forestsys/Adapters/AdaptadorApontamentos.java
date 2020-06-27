package com.example.forestsys.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forestsys.assets.ApplicationTodos;
import com.example.forestsys.assets.BaseDeDados;
import com.example.forestsys.assets.DAO;
import com.example.forestsys.assets.DataHoraAtual;
import com.example.forestsys.R;
import com.example.forestsys.classes.GGF_USUARIOS;
import com.example.forestsys.classes.O_S_ATIVIDADES_DIA;
import com.example.forestsys.classes.O_S_ATIVIDADE_INSUMOS;
import com.example.forestsys.classes.PRESTADORES;

import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class AdaptadorApontamentos extends RecyclerView.Adapter<AdaptadorApontamentos.ApontamentosHolder>{

    private List<O_S_ATIVIDADES_DIA> apontamentos = new ArrayList<>();
    private DAO dao;
    private OnItemClickListener listener;
    private Context context = ApplicationTodos.getAppContext();
    private DataHoraAtual dataHoraAtual;

    @NonNull
    @Override
    public AdaptadorApontamentos.ApontamentosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_registros, parent, false);

        return new AdaptadorApontamentos.ApontamentosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorApontamentos.ApontamentosHolder holder, int position) {

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        List<O_S_ATIVIDADE_INSUMOS> insumos_dia = new ArrayList<O_S_ATIVIDADE_INSUMOS>();
        insumos_dia = dao.listaInsumosatividade(osSelecionada.getID_PROGRAMACAO_ATIVIDADE());

        O_S_ATIVIDADES_DIA oSAtividadesDia = apontamentos.get(position);

        GGF_USUARIOS ggf_usuarios = dao.selecionaUser(oSAtividadesDia.getID_RESPONSAVEL());
        PRESTADORES prestadores = dao.selecionaPrestador(oSAtividadesDia.getID_PRESTADOR());

        dataHoraAtual = new DataHoraAtual();
        holder.data.setText((DataHoraAtual.formataDataTextView(oSAtividadesDia.getDATA())));
        holder.responsavel.setText((ggf_usuarios.getDESCRICAO()));
        holder.prestador.setText(prestadores.getDESCRICAO());
        holder.area.setText(oSAtividadesDia.getAREA_REALIZADA());

        holder.insumo1.setText(String.valueOf(dao.qtdAplInsDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumos_dia.get(0).getID_INSUMO(),
                apontamentos.get(position).getDATA())));
        holder.insumo2.setText(String.valueOf(dao.qtdAplInsDia(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumos_dia.get(1).getID_INSUMO(),
                apontamentos.get(position).getDATA())));

        if(oSAtividadesDia.getHO()!=null)holder.ho.setText(oSAtividadesDia.getHO().replace('.', ','));
        if(oSAtividadesDia.getHM()!=null)holder.hm.setText(oSAtividadesDia.getHM().replace('.', ','));
        if(oSAtividadesDia.getHH()!=null)holder.hh.setText(oSAtividadesDia.getHH().replace('.', ','));
        if(oSAtividadesDia.getHO_ESCAVADEIRA()!=null)holder.hoe.setText(oSAtividadesDia.getHO_ESCAVADEIRA().replace('.', ','));
        if(oSAtividadesDia.getHM_ESCAVADEIRA()!=null)holder.hme.setText(oSAtividadesDia.getHM_ESCAVADEIRA().replace('.', ','));
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
        TextView area;
        TextView insumo1;
        TextView insumo2;


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
            area = itemView.findViewById(R.id.item_apontamento_area);
            insumo1 = itemView.findViewById(R.id.item_apontamento_insumo1);
            insumo2 = itemView.findViewById(R.id.item_apontamento_insumo2);


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