package com.example.forestsys.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.forestsys.Assets.ApplicationTodos;
import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.R;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.Activities.ActivityMain.osSelecionada;

public class AdaptadorInsumos extends RecyclerView.Adapter<AdaptadorInsumos.InsumosHolder> {

    private List<Join_OS_INSUMOS> insumos = new ArrayList<>();
    private DAO dao;
    Context context = ApplicationTodos.getAppContext();

    @NonNull
    @Override
    public AdaptadorInsumos.InsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atividade_lista_insumos, parent, false);

        return new AdaptadorInsumos.InsumosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorInsumos.InsumosHolder holder, int position) {

        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        Join_OS_INSUMOS insumo = insumos.get(position);


        holder.descricao.setText(String.valueOf(insumo.getDESCRICAO()));
        holder.qtdRecomendada.setText(String.valueOf(insumo.getQTD_HA_RECOMENDADO()));

        BigDecimal QTD_PROGRAMADA = new BigDecimal(insumo.getQTD_HA_RECOMENDADO() * osSelecionada.getAREA_PROGRAMADA()).setScale(2, BigDecimal.ROUND_UP);
        DecimalFormat format = new DecimalFormat(".##");

        String s;
        s = format.format(QTD_PROGRAMADA).replace(',', '.');
        double qtdProg = Double.parseDouble(s);
        holder.qtdProgramada.setText(String.valueOf(qtdProg).replace(".", ","));

        String rec = "Não";
        try{
            if(insumo.getRECOMENDACAO() == 1){
                    rec = "Sim";
                }
        }catch(Exception ex){
            rec = "Não";
            ex.printStackTrace();
        }

        holder.recomendado.setText(rec);

        double acumulador = 0;
        List<Double> listaDouble = dao.todosQTDApl(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumo.getID_INSUMO());

        for (int i = 0; i < listaDouble.size(); i++) {
            acumulador += listaDouble.get(i);
        }

        DecimalFormat df = new DecimalFormat(".000");


        //acumulador = acumulador/osSelecionada.getAREA_PROGRAMADA();
        acumulador = Double.valueOf(df.format(acumulador).replace(',', '.'));
        holder.QTDApl.setText(String.valueOf(acumulador).replace(".", ","));
    }

    @Override
    public int getItemCount() {
        return insumos.size();
    }

    public void setInsumos(List<Join_OS_INSUMOS> insumos) {
        this.insumos = insumos;
        notifyDataSetChanged();
    }

    class InsumosHolder extends RecyclerView.ViewHolder {
        private TextView descricao;
        private TextView recomendado;
        private TextView qtdRecomendada;
        private TextView qtdProgramada;
        private TextView QTDApl;


        public InsumosHolder(@NonNull View itemView) {
            super(itemView);
            descricao = itemView.findViewById(R.id.item_insumos_descricao);
            recomendado = itemView.findViewById(R.id.item_insumos_rec);
            qtdRecomendada = itemView.findViewById(R.id.item_insumos_qtd_rec);
            qtdProgramada = itemView.findViewById(R.id.item_insumos_qtd_prog);
            QTDApl = itemView.findViewById(R.id.item_insumos_qtd_apl);
        }
    }
}
