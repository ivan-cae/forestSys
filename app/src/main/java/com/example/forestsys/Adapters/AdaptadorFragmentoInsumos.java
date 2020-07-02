package com.example.forestsys.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.forestsys.R;
import com.example.forestsys.classes.join.Join_OS_INSUMOS;
import java.util.ArrayList;
import java.util.List;

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

        if(insumo.getQTD_APLICADO()!=0.0) holder.QTDApl.setText(String.valueOf(insumo.getQTD_APLICADO()).replace(".", ","));

        holder.descricao.setText(String.valueOf(insumo.getDESCRICAO()));
        holder.QTDRec.setText(String.valueOf(insumo.getQTD_HA_RECOMENDADO()).replace('.', ','));
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
        private TextView QTDApl;
        private TextView QTDRec;

        public FragmentoInsumosHolder(@NonNull View itemView) {
            super(itemView);
            descricao = itemView.findViewById(R.id.item_fragmento_insumos_descricao);
            QTDApl = itemView.findViewById(R.id.item_fragmento_insumos_qtd_apl);
            QTDRec = itemView.findViewById(R.id.item_fragmento_insumos_qtd_rec);

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
}

