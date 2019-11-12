package com.example.forestsys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorOs extends RecyclerView.Adapter<AdaptadorOs.OsHolder>{
    private List<ClasseOs> ordens = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public OsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_os, parent, false);
        return new OsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OsHolder holder, int position) {
        ClasseOs ordem = ordens.get(position);
        holder.status.setText(ordem.getStatus().toString());
        holder.numero.setText(String.valueOf(ordem.getId()));
    }

    @Override
    public int getItemCount() {
        return ordens.size();
    }

    public void setOrdens(List<ClasseOs> ordens) {
        this.ordens = ordens;
        notifyDataSetChanged();
    }

    class OsHolder extends RecyclerView.ViewHolder {
        private TextView status;
        private TextView numero;

        public OsHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero_item_lista);
            status = itemView.findViewById(R.id.status_item_lista);

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
}