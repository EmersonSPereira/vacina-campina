package br.com.vacinacampina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.fragment.CartaoFragment;
import br.com.vacinacampina.model.Parente;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterParentes extends RecyclerView.Adapter<AdapterParentes.MyviewHolder> {

    private String nome ;
    private String parentesco;
    private String urlFoto;
    private List<Parente> Parentes;
    private Context context;

    public AdapterParentes(List<Parente> Parentes, Context context) {
        this.Parentes = Parentes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterParentes.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterParentes.MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cartao,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterParentes.MyviewHolder holder, int position) {

        Parente parente = Parentes.get(position);

       holder.textViewNome.setText(parente.getNome());
       holder.textViewParentesco.setText(parente.getParentesco());
       if(parente.getUrlFoto() != null && !parente.getUrlFoto().isEmpty()) {
           Glide.with(context).load(parente.getUrlFoto()).into(holder.imageView);
       }else {
           holder.imageView.setImageResource(R.drawable.profile);
       }

    }

    @Override
    public int getItemCount() {
        return Parentes.size();
    }

    public class  MyviewHolder extends  RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView textViewNome;
        TextView textViewParentesco;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_foto_cartao);
            textViewNome =  itemView.findViewById(R.id.textView_nome_cartao);
            textViewParentesco = itemView.findViewById(R.id.textView_parentesco);
        }
    }
}
