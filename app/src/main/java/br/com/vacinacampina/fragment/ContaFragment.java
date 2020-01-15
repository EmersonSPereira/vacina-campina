package br.com.vacinacampina.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.EditarContaActivity;
import br.com.vacinacampina.activity.TelaPrincipalActivity;
import br.com.vacinacampina.activity.login.LoginActivity;
import br.com.vacinacampina.adapter.AdapterOpcoes;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.config.RecyclerItemClickListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContaFragment extends Fragment {


    private RecyclerView recyclerViewOpcoes;
    private TextView textViewEmail,textViewNome;
    private CircleImageView circleImageViewFoto;
    private ProgressBar progressBar_foto;

    public ContaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_conta, container, false);

        textViewNome = root.findViewById(R.id.textView_nome);
        textViewEmail = root.findViewById(R.id.textView_exibir_email);
        circleImageViewFoto = root.findViewById(R.id.profile_image);
        progressBar_foto = root.findViewById(R.id.progressBar_carregar_foto_1);

        carregarDados();


        recyclerViewOpcoes = root.findViewById(R.id.recycleView_opcoes);

        //configurar Adapter
        AdapterOpcoes adapterOpcoes = new AdapterOpcoes(montaOpçõesMenu());

        //configurar recycle view
        recyclerViewOpcoes.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOpcoes.setLayoutManager(layoutManager);
        recyclerViewOpcoes.addItemDecoration(new DividerItemDecoration(root.getContext(), LinearLayout.VERTICAL));
        recyclerViewOpcoes.setAdapter(adapterOpcoes);

        recyclerViewOpcoes.addOnItemTouchListener(new RecyclerItemClickListener(root.getContext(),
                recyclerViewOpcoes,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        switch (position){
                            case 0 :
                                startActivity(new Intent(getActivity() ,EditarContaActivity.class));
                                break;
                            case 1 :
                                FirebaseConfig.getAuth().signOut();
                                startActivity(new Intent(getActivity() , LoginActivity.class));
                                break;
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

        return root;
    }

    private void carregarDados() {
        textViewNome.setText(FirebaseConfig.getUsuarioLogado().getDisplayName());
        textViewEmail.setText(FirebaseConfig.getUsuarioLogado().getEmail());

        Uri fotoUrl = FirebaseConfig.getUsuarioLogado().getPhotoUrl();

        if(fotoUrl != null ) {
            progressBar_foto.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(fotoUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar_foto.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar_foto.setVisibility(View.GONE);
                    return false;
                }
            }).into(circleImageViewFoto);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarDados();
    }

    private List<String> montaOpçõesMenu(){
        List opções = new ArrayList();
        opções.add(getString(R.string.editar_dados));
        opções.add(getString(R.string.sair));

        return  opções;
    }

}
