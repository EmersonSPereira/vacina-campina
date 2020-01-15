package br.com.vacinacampina.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vacinacampina.R;

public class AdapterOpcoes  extends RecyclerView.Adapter<AdapterOpcoes.MyViewHolder> {

    private List<String> opcoesList;
    public AdapterOpcoes(List<String> montaOpçõesMenu) {
        this.opcoesList = montaOpçõesMenu;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View opcoes = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_opcoes, parent, false);
        return new MyViewHolder(opcoes);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.opcao.setText(opcoesList.get(position));
    }

    @Override
    public int getItemCount() {
        return opcoesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView opcao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            opcao = itemView.findViewById(R.id.textViewo_opcao);
        }
    }
}
