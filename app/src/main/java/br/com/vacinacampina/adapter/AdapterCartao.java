package br.com.vacinacampina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.model.Cartao;

public class AdapterCartao extends  RecyclerView.Adapter<AdapterCartao.MyviewHolder>{

        public static final String PREVINE = "Previne ";
    public static final String DOSE_NÃO_TOMADA = "Dose não tomada";
    public static final String NÃO_NECESSÁRIO = "Não necessário";
    private List<Cartao> cartoes;
        private Context context;

    public AdapterCartao(List<Cartao> cartoes, Context context) {
        this.cartoes = cartoes;
        this.context = context;
    }

        @NonNull
        @Override
        public AdapterCartao.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCartao.MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cartao_vacinas,parent,false));
    }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

            Cartao cartao = cartoes.get(position);

            if (cartao.getDoses().equals(1)) {
                holder.textViewDataTerceiraDose.setText(NÃO_NECESSÁRIO);
                holder.textViewDataSegundaDose.setText(NÃO_NECESSÁRIO);

                holder.imageViewStatusDose2.setImageResource(R.drawable.bloqueado);
                holder.imageViewStatusDose3.setImageResource(R.drawable.bloqueado);
            }
            if (cartao.getDoses().equals(2)) {
                holder.textViewDataTerceiraDose.setText(NÃO_NECESSÁRIO);

                holder.imageViewStatusDose3.setImageResource(R.drawable.bloqueado);

            }

            if(cartao.getDataPrimeiraDose() != null){
                holder.imageViewStatusDose1.setImageResource(R.drawable.sucesso);
            }
            if(cartao.getDataSegundaDose() != null){
                holder.imageViewStatusDose2.setImageResource(R.drawable.sucesso);
            }
            if(cartao.getDataTerceiraDose() != null){
                holder.imageViewStatusDose3.setImageResource(R.drawable.sucesso);
            }

            holder.textViewNomeVacina.setText(cartao.getNomeVacina());
            holder.textViewDataPrimeiraDose.setText
                    (cartao.getDataPrimeiraDose() != null ? cartao.getDataPrimeiraDose().toString() : DOSE_NÃO_TOMADA);
           if(cartao.getDoses().equals(2) || cartao.getDoses().equals(3)){
            holder.textViewDataSegundaDose.setText
                    (cartao.getDataSegundaDose() != null ? cartao.getDataSegundaDose().toString() : DOSE_NÃO_TOMADA);
           }
           if(cartao.getDoses().equals(3)){
            holder.textViewDataTerceiraDose.setText
                    (cartao.getDataTerceiraDose() != null ? cartao.getDataTerceiraDose().toString() : DOSE_NÃO_TOMADA);
           }


        }

        @Override
        public int getItemCount() {
        return cartoes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    public class  MyviewHolder extends  RecyclerView.ViewHolder {

            TextView textViewNomeVacina,textViewPrimeiraDose, textViewSegundaDose, textViewTerceiraDose,
            textViewDataPrimeiraDose, textViewDataSegundaDose, textViewDataTerceiraDose;
            ImageView imageViewStatusDose1, imageViewStatusDose2,imageViewStatusDose3;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);

                textViewNomeVacina =  itemView.findViewById(R.id.textView_nome_vacina_cartao);

                textViewPrimeiraDose = itemView.findViewById(R.id.textView_dose1);
                textViewSegundaDose = itemView.findViewById(R.id.textView_dose2);
                textViewTerceiraDose = itemView.findViewById(R.id.textView_dose3);

                textViewDataPrimeiraDose = itemView.findViewById(R.id.textViewDataDose1);
                textViewDataSegundaDose = itemView.findViewById(R.id.textViewDataDose2);
                textViewDataTerceiraDose = itemView.findViewById(R.id.textViewDataDose3);

                imageViewStatusDose1 = itemView.findViewById(R.id.imageView_status_dose1);
                imageViewStatusDose2 = itemView.findViewById(R.id.imageView_status_dose2);
                imageViewStatusDose3 = itemView.findViewById(R.id.imageView_status_dose3);


            }
        }
}
