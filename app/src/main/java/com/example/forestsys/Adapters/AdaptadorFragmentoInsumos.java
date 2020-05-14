package com.example.forestsys.Adapters;

import android.content.Context;
import android.graphics.Color;
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

import static java.sql.Types.NULL;

public class AdaptadorFragmentoInsumos extends RecyclerView.Adapter<AdaptadorFragmentoInsumos.FragmentoInsumosHolder> {

    private List<Join_OS_INSUMOS> insumos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AdaptadorFragmentoInsumos.FragmentoInsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragmento_insumos, parent, false);

        return new AdaptadorFragmentoInsumos.FragmentoInsumosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFragmentoInsumos.FragmentoInsumosHolder holder, int position) {
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
        holder.QTDApl.setText(String.valueOf(insumo.getQTD_APLICADO()).replace(".", ","));

        holder.QTDRec.setText(String.valueOf(insumo.getQTD_HA_RECOMENDADO()).replace('.', ','));
        if(insumo.getQTD_APLICADO()!= NULL && insumo.getQTD_APLICADO()>0){
            holder.dif.setText(String.valueOf(diferencaPercentual(insumo.getQTD_HA_RECOMENDADO(), insumo.getQTD_APLICADO())).replace('.', ','));
            if(diferencaPercentual(insumo.getQTD_HA_RECOMENDADO(), insumo.getQTD_APLICADO()) >5) holder.dif.setBackgroundColor(Color.parseColor("#FF0000"));
            else holder.dif.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return insumos.size();
    }

    public void setInsumos(List<Join_OS_INSUMOS> insumos) {
        this.insumos = insumos;
        notifyDataSetChanged();
    }

    class FragmentoInsumosHolder extends RecyclerView.ViewHolder {
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
        private TextView QTDApl;
        private TextView Un;
        private TextView QTDRec;
        private TextView dif;


        public FragmentoInsumosHolder(@NonNull View itemView) {
            super(itemView);
            descricao = itemView.findViewById(R.id.item_fragmento_insumos_descricao);
            N = itemView.findViewById(R.id.item_fragmento_insumos_n);
            P205 = itemView.findViewById(R.id.item_fragmento_insumos_p205);
            K20 = itemView.findViewById(R.id.item_fragmento_insumos_k20);
            CAO = itemView.findViewById(R.id.item_fragmento_insumos_cao);
            MGO = itemView.findViewById(R.id.item_fragmento_insumos_mgo);
            B = itemView.findViewById(R.id.item_fragmento_insumos_b);
            ZN = itemView.findViewById(R.id.item_fragmento_insumos_zn);
            S = itemView.findViewById(R.id.item_fragmento_insumos_s);
            CU = itemView.findViewById(R.id.item_fragmento_insumos_cu);
            AF = itemView.findViewById(R.id.item_fragmento_insumos_af);
            MN = itemView.findViewById(R.id.item_fragmento_insumos_mn);
            QTDApl = itemView.findViewById(R.id.item_fragmento_insumos_qtd_apl);
            Un = itemView.findViewById(R.id.item_fragmento_insumos_un);
            QTDRec = itemView.findViewById(R.id.item_fragmento_insumos_qtd_rec);
            dif = itemView.findViewById(R.id.item_fragmento_insumos_dif);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(insumos.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Join_OS_INSUMOS joinOsInsumos);
    }

    public void setOnItemClickListener(AdaptadorFragmentoInsumos.OnItemClickListener listener) {
        this.listener = listener;
    }

    private static Double diferencaPercentual(Double qtdRec, Double qtdApl) {
        Double calculo =  (1-(qtdApl/qtdRec))*100;
        DecimalFormat df = new DecimalFormat(".00");
        return Math.abs(Double.valueOf(df.format(calculo).replace(',', '.')));
    }
}

