package com.example.forestsys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.forestsys.classes.join.Join_OS_INSUMOS;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorInsumos extends RecyclerView.Adapter<com.example.forestsys.AdaptadorInsumos.InsumosHolder>{
    
        private List<Join_OS_INSUMOS> insumos = new ArrayList<>();
        private DAO dao;
        Context context = ApplicationTodos.getAppContext();

        @NonNull
        @Override
        public com.example.forestsys.AdaptadorInsumos.InsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lista_insumos, parent, false);

            return new com.example.forestsys.AdaptadorInsumos.InsumosHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.forestsys.AdaptadorInsumos.InsumosHolder holder, int position) {

            BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
            dao = baseDeDados.dao();

            Join_OS_INSUMOS insumo = insumos.get(position);

            holder.descricao.setText(String.valueOf(insumo.getDESCRICAO()));
            holder.N.setText(String.valueOf(insumo.getNUTRIENTE_N()));
            holder.P205.setText(String.valueOf(insumo.getNUTRIENTE_P2O5()));
            holder.K20.setText(String.valueOf(insumo.getNUTRIENTE_K2O()));
            holder.CAO.setText(String.valueOf(insumo.getNUTRIENTE_CAO()));
            holder.MGO.setText(String.valueOf(insumo.getNUTRIENTE_MGO()));
            holder.B.setText(String.valueOf(insumo.getNUTRIENTE_B()));
            holder.ZN.setText(String.valueOf(insumo.getNUTRIENTE_ZN()));
            holder.S.setText(String.valueOf(insumo.getNUTRIENTE_S()));
            holder.CU.setText(String.valueOf(insumo.getNUTRIENTE_CU()));
            holder.AF.setText(String.valueOf(insumo.getNUTRIENTE_AF()));
            holder.MN.setText(String.valueOf(insumo.getNUTRIENTE_MN()));
            holder.Un.setText(String.valueOf(insumo.getUND_MEDIDA()));
            holder.Dose.setText(String.valueOf(insumo.getQTD_HA_RECOMENDADO()));
            holder.QTDApl.setText(String.valueOf(insumo.getQTD_HA_APLICADO()));
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
