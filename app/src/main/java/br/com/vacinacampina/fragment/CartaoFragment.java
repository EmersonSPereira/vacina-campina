package br.com.vacinacampina.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.VacinaDetalheActivity;
import br.com.vacinacampina.adapter.AdapterParentes;
import br.com.vacinacampina.adapter.AdapterVacina;
import br.com.vacinacampina.config.RecyclerItemClickListener;
import br.com.vacinacampina.model.Parente;
import br.com.vacinacampina.service.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartaoFragment extends Fragment {

    public static final String STRING_VAZIA = "";
    private TextView textViewNome, textViewParentesco;
    private CircleImageView circleImageView;
    private RecyclerView recyclerViewParentes;
    private AdapterParentes adapterParentes;
    private List<Parente> parentes = new ArrayList<>();
    public CartaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Parente parente = new Parente();
        parente.setNome("Emerson");
        parente.setParentesco("Filho");
        parentes.add(parente);
        parentes.add(parente);
        parentes.add(parente);
        parentes.add(parente);
        parentes.add(parente);
        parentes.add(parente);
        parente.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWosspj6-4TbCp5j231kBZMpVtehIMyLfgeo2jYbstUklx0mHZ&s");
        View view = inflater.inflate(R.layout.fragment_cartao, container, false);
        textViewNome = view.findViewById(R.id.textView_nome_cartao);
        textViewParentesco= view.findViewById(R.id.textView_parentesco);
        circleImageView = view.findViewById(R.id.imageView_foto_cartao);
        recyclerViewParentes = view.findViewById(R.id.recycleView_Parentes);


        Glide.with(view).load(UsuarioService.getUsuarioLogado().getPhotoUrl()).into(circleImageView);
        textViewNome.setText(UsuarioService.getUsuarioLogado().getDisplayName());
        textViewParentesco.setText(STRING_VAZIA);
        configurarRecycleView(view);
        

        return view;
    }

    private void configurarRecycleView(View view) {
        //Configurando Recycleview

        recyclerViewParentes.setHasFixedSize(true);
        recyclerViewParentes.setLayoutManager(new LinearLayoutManager(getContext()));


        adapterParentes = new AdapterParentes(parentes, getActivity());
        recyclerViewParentes.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL));
        recyclerViewParentes.setAdapter(adapterParentes);

        recyclerViewParentes.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewParentes, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                startActivity(new Intent(getContext(), VacinaDetalheActivity.class).putExtra(VACINA,vacinas.get(position)));

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }

}
