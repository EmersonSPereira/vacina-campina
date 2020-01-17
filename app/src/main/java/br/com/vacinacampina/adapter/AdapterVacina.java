package br.com.vacinacampina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.model.Vacina;

public class AdapterVacina extends RecyclerView.Adapter<AdapterVacina.MyviewHolder> {

    public static final String PREVINE = "Previne ";
    private List<Vacina> vacinas;
    private Context context;

    public AdapterVacina(List<Vacina> vacinas, Context context) {
        this.vacinas = vacinas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vacinas,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Vacina vacina = vacinas.get(position);

        holder.nome.setText(vacina.getNome());
        holder.descricao.setText(PREVINE + vacina.getPrevine());

    }

    @Override
    public int getItemCount() {
        return vacinas.size();
    }

    public class  MyviewHolder extends  RecyclerView.ViewHolder {

        TextView nome;
        TextView descricao;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            nome =  itemView.findViewById(R.id.textView_nome_vacina);
            descricao = itemView.findViewById(R.id.textView_desc_vacina);
        }
    }
}
