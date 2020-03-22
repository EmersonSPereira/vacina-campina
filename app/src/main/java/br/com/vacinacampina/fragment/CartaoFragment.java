package br.com.vacinacampina.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.CadastroParenteActivity;
import br.com.vacinacampina.activity.VacinaDetalheActivity;
import br.com.vacinacampina.activity.VisualizarCartaoActivity;
import br.com.vacinacampina.adapter.AdapterParentes;
import br.com.vacinacampina.adapter.AdapterVacina;
import br.com.vacinacampina.config.RecyclerItemClickListener;
import br.com.vacinacampina.model.Parente;
import br.com.vacinacampina.service.CartaoService;
import br.com.vacinacampina.service.ParenteService;
import br.com.vacinacampina.service.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartaoFragment extends Fragment {

    public static final String STRING_VAZIA = "";
    public static final String PARENTE = "Parente";
    public static final int EDITAR = 0;
    public static final int DELETAR = 1;
    private TextView textViewNome, textViewParentesco,textView5;
    private CircleImageView circleImageView;
    private RecyclerView recyclerViewParentes;
    private AdapterParentes adapterParentes;
    private List<Parente> parentes ;
    private FloatingActionButton buttonCadastrarParente;
    private ProgressBar progressBarParentes;
    private ImageView imageViewListaVazia;
    private CardView cardView;
    public CartaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartao, container, false);
        textViewNome = view.findViewById(R.id.textView_nome_cartao);
        textView5 = view.findViewById(R.id.textView5);
        textViewParentesco= view.findViewById(R.id.textView_parentesco);
        circleImageView = view.findViewById(R.id.imageView_foto_cartao);
        recyclerViewParentes = view.findViewById(R.id.recycleView_Parentes);
        buttonCadastrarParente = view.findViewById(R.id.button_add_parente);
        progressBarParentes = view.findViewById(R.id.progressBar_parentes);
        imageViewListaVazia = view.findViewById(R.id.imageView_Parentes_vazio);
        cardView = view.findViewById(R.id.include);
        parentes = new ArrayList<>();
        OneSignal.startInit(getContext()).init();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarCartaoUsuarioLogado();
            }
        });


        buttonCadastrarParente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CadastroParenteActivity.class));
            }
        });

        if(UsuarioService.getUsuarioLogado().getPhotoUrl() != null ){
            Glide.with(view).load(UsuarioService.getUsuarioLogado().getPhotoUrl()).into(circleImageView);

        }
        textViewNome.setText(UsuarioService.getUsuarioLogado().getDisplayName());
        textViewParentesco.setText("Parentes");
        configurarRecycleView(view);
        ParenteService.listarParentes(parentes,adapterParentes,progressBarParentes);

        adapterParentes.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {


            @Override
            public void onChanged() {
                super.onChanged();
                checarParentesVazio();
            }

            void checarParentesVazio(){
                if(adapterParentes.getItemCount() == 0){
                    textView5.setVisibility(View.VISIBLE);
                    imageViewListaVazia.setVisibility(View.VISIBLE);
                    progressBarParentes.setVisibility(View.GONE);
                }else {
                    textView5.setVisibility(View.GONE);
                    imageViewListaVazia.setVisibility(View.GONE);
                }

            }
        });
        

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ParenteService.listarParentes(parentes, adapterParentes, progressBarParentes);

    }

    private void configurarRecycleView(View view) {
        //Configurando Recycleview

        recyclerViewParentes.setHasFixedSize(true);
        recyclerViewParentes.setLayoutManager(new LinearLayoutManager(getContext()));


        adapterParentes = new AdapterParentes(parentes, getActivity());
        recyclerViewParentes.setAdapter(adapterParentes);

        recyclerViewParentes.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewParentes, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getContext(), VisualizarCartaoActivity.class).putExtra(PARENTE, parentes.get(position)));
            }

            @Override
            public void onLongItemClick(View view, int position) {
                final int posicao = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.lista_acoes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case EDITAR:
                                        startActivity(new Intent(getContext(), CadastroParenteActivity.class).putExtra(PARENTE, parentes.get(posicao)));
                                        break;
                                    case DELETAR:
                                        new AlertDialog.Builder(getActivity()).setMessage(R.string.remover_parente_msg)
                                                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        String idParente = parentes.get(posicao).getId();
                                                        ParenteService.excluirParente(idParente);
                                                        CartaoService.excluirCartao(idParente);
                                                        ParenteService.listarParentes(parentes, adapterParentes, progressBarParentes);
                                                    }
                                                })
                                                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                }).show();

                                }
                            }
                        });
                builder.show();


            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }

    private void carregarCartaoUsuarioLogado(){

        Parente usuarioLogado = new Parente();
        usuarioLogado.setId(UsuarioService.getUsuarioLogado().getUid());
        usuarioLogado.setNome(UsuarioService.getUsuarioLogado().getDisplayName());
        startActivity(new Intent(getContext(), VisualizarCartaoActivity.class).putExtra(PARENTE, usuarioLogado));

    }

}
