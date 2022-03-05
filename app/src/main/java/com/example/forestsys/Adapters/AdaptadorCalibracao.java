package com.example.forestsys.Adapters;

import static com.example.forestsys.Activities.ActivityInicializacao.ferramentas;

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
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.R;
import com.example.forestsys.Classes.CALIBRAGEM_SUBSOLAGEM;
import com.example.forestsys.Classes.IMPLEMENTOS;
import com.example.forestsys.Classes.MAQUINAS;
import com.example.forestsys.Classes.MAQUINA_IMPLEMENTO;
import com.example.forestsys.Classes.OPERADORES;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de Calibrações exibida na ActivityCalibracao
 */
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

        holder.data.setText((ferramentas.formataDataTextView(calibragem.getDATA())));
        holder.turno.setText((calibragem.getTURNO()));
        holder.maquina.setText(aux2.getDESCRICAO());
        holder.implemento.setText(aux3.getDESCRICAO());
        holder.operador.setText(aux4.getDESCRICAO());
        holder.p1Desvio.setText(String.valueOf(calibragem.getP1_DESVIO()).replace(".", ","));
        holder.p2Desvio.setText(String.valueOf(calibragem.getP2_DESVIO()).replace(".", ","));
        holder.p1Media.setText(String.valueOf(calibragem.getP1_MEDIA()).replace(".", ","));
        holder.p2Media.setText(String.valueOf(calibragem.getP2_MEDIA()).replace(".", ","));
    }

    /*
         * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
         tratado pelo Adapter
         */
    @Override
    public int getItemCount() {
        return calibragens.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setCalibragem(List<CALIBRAGEM_SUBSOLAGEM> calibragens) {
        this.calibragens = calibragens;
        notifyDataSetChanged();
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class CalibragemHolder extends RecyclerView.ViewHolder {
        TextView data;
        TextView turno;
        TextView maquina;
        TextView implemento;
        TextView operador;
        TextView p1Media;
        TextView p2Media;
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
            p1Media = itemView.findViewById(R.id.item_calibragem_p1_media);
            p2Media = itemView.findViewById(R.id.item_calibragem_p2_media);
        }
    }
}
