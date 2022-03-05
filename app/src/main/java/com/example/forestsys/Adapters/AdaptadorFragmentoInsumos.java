package com.example.forestsys.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.forestsys.R;
import com.example.forestsys.Classes.Joins.Join_OS_INSUMOS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import static com.example.forestsys.Activities.ActivityAtividades.area;
import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;
import static com.example.forestsys.R.color.secondaryDarkColor;
import static java.sql.Types.NULL;

/*
 * Adapter responsável por personalizar a lista de insumos exibida no FragmentoInsumos contido na ActivityRegistros
 e realizar suas interações
 */
public class AdaptadorFragmentoInsumos extends RecyclerView.Adapter<AdaptadorFragmentoInsumos.FragmentoInsumosHolder> {

    private List<Join_OS_INSUMOS> insumos = new ArrayList<>();
    private OnItemClickListener listener;
    public static boolean insumoConforme1 = false;
    public static boolean insumoConforme2 = false;

    @NonNull
    @Override
    public AdaptadorFragmentoInsumos.FragmentoInsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragmento_insumos, parent, false);

        return new AdaptadorFragmentoInsumos.FragmentoInsumosHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AdaptadorFragmentoInsumos.FragmentoInsumosHolder holder, int position) {
        Join_OS_INSUMOS insumo = insumos.get(position);

        if(insumo.getQTD_APLICADO()!=0.0) holder.QTDApl.setText(String.valueOf(insumo.getQTD_APLICADO()).replace(".", ","));

        holder.descricao.setText(String.valueOf(insumo.getDESCRICAO()));


        boolean rec = false;

            if(insumo.getQTD_HA_RECOMENDADO() != 0){
                rec = true;
                holder.itemView.setClickable(true);
                holder.itemView.setEnabled(true);

                if(area!=null && area!="" && area.length()>0){
                    String auxString = area;
                    if(auxString.contains(",")) auxString = auxString.replace(",", ".");

                    double auxDouble = NULL;
                    auxDouble = Double.valueOf(auxString);
                    if(auxDouble != NULL) {


                        double d = insumo.getQTD_HA_RECOMENDADO() * auxDouble;
                        BigDecimal bd = BigDecimal.valueOf(d);
                        bd = bd.setScale(2, RoundingMode.HALF_UP);

                        String s = String.valueOf(bd.doubleValue());

                        holder.QTDRec.setText(s);
                        if(ferramentas.diferencaPercentual((insumo.getQTD_HA_RECOMENDADO() * auxDouble), insumo.getQTD_APLICADO()) > 5.0000 ||
                                ferramentas.diferencaPercentual((insumo.getQTD_HA_RECOMENDADO() * auxDouble), insumo.getQTD_APLICADO()) < -5.0000){
                            if(holder.QTDApl.getText().toString().length()>0){
                                if(position == 0) insumoConforme1 = false;
                                if(position == 1) insumoConforme2 = false;
                                holder.QTDApl.setBackgroundColor(Color.parseColor("#FF0000"));
                                holder.QTDApl.setTextColor(Color.parseColor("#FFFFFFFF"));
                            }
                        }
                        else{
                            if(holder.QTDApl.getText().toString().length()>0){
                                if(position == 0) insumoConforme1 = true;
                                if(position == 1) insumoConforme2 = true;
                                holder.QTDApl.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                                holder.QTDApl.setTextColor(holder.descricao.getCurrentTextColor());

                            }
                        }
                    }
                }
            }else{
            rec= false;
        }


    if(rec == false){
        holder.itemView.setClickable(false);
        holder.itemView.setEnabled(false);
        holder.descricao.setTextColor(secondaryDarkColor);
        holder.QTDApl.setTextColor(secondaryDarkColor);
        holder.QTDRec.setTextColor(secondaryDarkColor);
        holder.QTDApl.setText("0,0");
        holder.QTDRec.setText("0,0");

            insumoConforme1 = true;
            insumoConforme2 = true;
    }
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return insumos.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setInsumos(List<Join_OS_INSUMOS> insumos) {
        this.insumos = insumos;
        notifyDataSetChanged();
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
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