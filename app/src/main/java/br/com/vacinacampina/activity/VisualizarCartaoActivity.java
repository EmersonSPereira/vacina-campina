package br.com.vacinacampina.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.adapter.AdapterCartao;
import br.com.vacinacampina.adapter.AdapterParentes;
import br.com.vacinacampina.config.RecyclerItemClickListener;
import br.com.vacinacampina.model.Cartao;
import br.com.vacinacampina.model.Parente;
import br.com.vacinacampina.service.CartaoService;
import br.com.vacinacampina.service.ParenteService;

public class VisualizarCartaoActivity extends AppCompatActivity {

    public static final String PARENTE = "Parente";
    public static final String VACINA = "vacina";
    public static final String ID_PARENTE = "idParente";
    private AdapterCartao adapterCartao;
    private RecyclerView recyclerViewCartaoVacinas;
    private ProgressBar progressBarListarCartao;
    private List<Cartao> cartoes = new ArrayList<>();
    private Parente parente;
    private ImageView imageViewFotoCartao;
    private TextView textViewNomeCartao;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_cartao);
        toolbar = findViewById(R.id.toolbar);
        recyclerViewCartaoVacinas = findViewById(R.id.recyclerView_cartao_vacina);
        progressBarListarCartao = findViewById(R.id.progressBar_listar_cartao);
        imageViewFotoCartao = toolbar.findViewById(R.id.imageView_foto_cartao_vacina);
        textViewNomeCartao = toolbar.findViewById(R.id.textview_nome5);
        configurarRecycleView();





        Bundle bundle = getIntent().getExtras();
        if( bundle != null) {
            parente = (Parente) bundle.getSerializable(PARENTE);
            CartaoService.listarcartoes(cartoes,adapterCartao, progressBarListarCartao, parente.getId());
            textViewNomeCartao.setText(parente.getNome());
            if(parente.getUrlFoto() != null && !parente.getUrlFoto().isEmpty())
                Glide.with(this).load(parente.getUrlFoto()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoCartao);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        CartaoService.listarcartoes(cartoes,adapterCartao,progressBarListarCartao,parente.getId());
    }

    private void configurarRecycleView() {
        //Configurando Recycleview

        recyclerViewCartaoVacinas.setHasFixedSize(true);
        recyclerViewCartaoVacinas.setLayoutManager(new LinearLayoutManager(this));


        adapterCartao = new AdapterCartao(cartoes, this);
        recyclerViewCartaoVacinas.setAdapter(adapterCartao);

        recyclerViewCartaoVacinas.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewCartaoVacinas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(VisualizarCartaoActivity.this, EditarVacinaActivity.class).putExtra(VACINA,cartoes.get(position)).putExtra(ID_PARENTE, parente.getId()));
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
