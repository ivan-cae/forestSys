package com.example.forestsys.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
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
import com.example.forestsys.classes.join.Join_OS_INSUMOS;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.forestsys.activities.ActivityMain.osSelecionada;

public class AdaptadorInsumos extends RecyclerView.Adapter<AdaptadorInsumos.InsumosHolder>{
    
        private List<Join_OS_INSUMOS> insumos = new ArrayList<>();
        private DAO dao;
        Context context = ApplicationTodos.getAppContext();

        @NonNull
        @Override
        public AdaptadorInsumos.InsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lista_insumos, parent, false);

            return new AdaptadorInsumos.InsumosHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorInsumos.InsumosHolder holder, int position) {

            BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
            dao = baseDeDados.dao();

            Join_OS_INSUMOS insumo = insumos.get(position);

            holder.descricao.setText(String.valueOf(insumo.getDESCRICAO()));
            holder.N.setText(String.valueOf(insumo.getNUTRIENTE_N()).replace(".", ","));
            holder.P205.setText(String.valueOf(insumo.getNUTRIENTE_P2O5()).replace(".", ","));
            holder.K20.setText(String.valueOf(insumo.getNUTRIENTE_K2O()).replace(".", ","));
            holder.CAO.setText(String.valueOf(insumo.getNUTRIENTE_CAO()).replace(".", ","));
            holder.MGO.setText(String.valueOf(insumo.getNUTRIENTE_MGO()).replace(".", ","));
            holder.B.setText(String.valueOf(insumo.getNUTRIENTE_B()).replace(".", ","));
            holder.ZN.setText(String.valueOf(insumo.getNUTRIENTE_ZN()).replace(".", ","));
            holder.S.setText(String.valueOf(insumo.getNUTRIENTE_S()).replace(".", ","));
            holder.CU.setText(String.valueOf(insumo.getNUTRIENTE_CU()).replace(".", ","));
            holder.AF.setText(String.valueOf(insumo.getNUTRIENTE_AF()).replace(".", ","));
            holder.MN.setText(String.valueOf(insumo.getNUTRIENTE_MN()).replace(".", ","));
            holder.Un.setText(String.valueOf(insumo.getUND_MEDIDA()).replace(".", ","));
            holder.Dose.setText(String.valueOf(insumo.getQTD_HA_RECOMENDADO()).replace(".", ","));

            double acumulador=0;
            List <Double> listaDouble = dao.todosQTDApl(osSelecionada.getID_PROGRAMACAO_ATIVIDADE(), insumo.getID_INSUMO());

            for(int i = 0; i<listaDouble.size(); i++){
                    acumulador+=listaDouble.get(i);
            }

            DecimalFormat df = new DecimalFormat(".000");


            acumulador = acumulador/osSelecionada.getAREA_PROGRAMADA();
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
            private TextView N;
            private TextView P205;
            private TextView K20;
            private TextView CAO;
            private TextView MGO;
            private TextView B;
            private TextView ZN;
            private TextView S;
            private TextView CU;
            private TextView AF;
            private TextView MN;
            private TextView Dose;
            private TextView QTDApl;
            private TextView Un;


            public InsumosHolder(@NonNull View itemView) {
                super(itemView);
                descricao = itemView.findViewById(R.id.item_insumos_descricao);
                N = itemView.findViewById(R.id.item_insumos_n);
                P205 = itemView.findViewById(R.id.item_insumos_p205);
                K20 = itemView.findViewById(R.id.item_insumos_k20);
                CAO = itemView.findViewById(R.id.item_insumos_cao);
                MGO = itemView.findViewById(R.id.item_insumos_mgo);
                B = itemView.findViewById(R.id.item_insumos_b);
                ZN = itemView.findViewById(R.id.item_insumos_zn);
                S = itemView.findViewById(R.id.item_insumos_s);
                CU = itemView.findViewById(R.id.item_insumos_cu);
                AF = itemView.findViewById(R.id.item_insumos_af);
                MN = itemView.findViewById(R.id.item_insumos_mn);
                Dose = itemView.findViewById(R.id.item_insumos_dose);
                QTDApl = itemView.findViewById(R.id.item_insumos_qtd_apl);
                Un = itemView.findViewById(R.id.item_insumos_un);
            }
        }
}
