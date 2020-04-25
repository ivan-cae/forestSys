package com.example.forestsys.Adapters;

import android.content.Context;
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
import com.example.forestsys.classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.classes.IMPLEMENTOS;
import com.example.forestsys.classes.MAQUINAS;
import com.example.forestsys.classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.classes.OPERADORES;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCalibracao extends RecyclerView.Adapter<AdaptadorCalibracao.CalibragemHolder>{

    private List<CALIBRAGEM_SUBSOLAGEM> calibragens = new ArrayList<>();
    private DAO dao;
    Context context = ApplicationTodos.getAppContext();

    @NonNull
    @Override
    public AdaptadorCalibracao.CalibragemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_calibracao, parent, false);

        return new AdaptadorCalibracao.CalibragemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCalibracao.CalibragemHolder holder, int position) {

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        CALIBRAGEM_SUBSOLAGEM calibragem = calibragens.get(position);

        MAQUINA_IMPLEMENTO aux1 = dao.selecionaMaquinaImplemento(calibragem.getID_MAQUINA_IMPLEMENTO());
        MAQUINAS aux2 = dao.selecionaMaquina(aux1.getID_MAQUINA());
        IMPLEMENTOS aux3 = dao.selecionaImplemento(aux1.getID_IMPLEMENTO());
        OPERADORES aux4 = dao.selecionaOperador(calibragem.getID_OPERADOR());

        holder.data.setText((calibragem.getDATA()));
        holder.turno.setText((calibragem.getTURNO()));
        holder.maquina.setText(aux2.getDESCRICAO());
        holder.implemento.setText(aux3.getDESCRICAO());
        holder.operador.setText(aux4.getDESCRICAO());
        holder.p1Desvio.setText(String.valueOf(calibragem.getP1_DESVIO()).replace(".", ","));
        holder.p2Desvio.setText(String.valueOf(calibragem.getP2_DESVIO()).replace(".", ","));
    }

    @Override
    public int getItemCount() {
        return calibragens.size();
    }

    public void setCalibragem(List<CALIBRAGEM_SUBSOLAGEM> calibragens) {
        this.calibragens = calibragens;
        notifyDataSetChanged();
    }

    class CalibragemHolder extends RecyclerView.ViewHolder {
        TextView data;
        TextView turno;
        TextView maquina;
        TextView implemento;
        TextView operador;
        TextView p1Desvio;
        TextView p2Desvio;


        public CalibragemHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.item_calibragem_data);
            turno = itemView.findViewById(R.id.item_calibragem_turno);
            maquina = itemView.findViewById(R.id.item_calibragem_maquina);
            implemento = itemView.findViewById(R.id.item_calibragem_implemento);
            operador = itemView.findViewById(R.id.item_calibragem_operador);
            p1Desvio = itemView.findViewById(R.id.item_calibragem_p1_desvio);
            p2Desvio = itemView.findViewById(R.id.item_calibragem_p2_desvio);
        }
    }
}
