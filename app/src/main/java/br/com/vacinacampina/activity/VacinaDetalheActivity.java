package br.com.vacinacampina.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.vacinacampina.R;
import br.com.vacinacampina.model.Vacina;
import br.com.vacinacampina.util.JustifiedTextView;

public class VacinaDetalheActivity extends AppCompatActivity {

    public static final String VACINA = "vacina";

    private TextView textViewNomeVacina;

   private  JustifiedTextView textViewPrevine, textViewComposicao, textViewIndicacao,
    textViewDoses, textViewAplicacao, textViewCuidados, textViewEfeitos;


    private Vacina vacina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina_detalhe);

        textViewNomeVacina = findViewById(R.id.textView_nome_vacina_cartao);
        textViewPrevine = findViewById(R.id.textView_previne);
        textViewComposicao = findViewById(R.id.textView_composicao);
        textViewIndicacao = findViewById(R.id.textView_indicacao);
        textViewDoses = findViewById(R.id.textView_doses);
        textViewAplicacao = findViewById(R.id.textView_aplicacao);
        textViewCuidados = findViewById(R.id.textView_cuidados);
        textViewEfeitos = findViewById(R.id.textView_efeitos);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            vacina = (Vacina) bundle.getSerializable(VACINA);

            textViewNomeVacina.setText(vacina.getNome());
            textViewPrevine.setText(vacina.getPrevine());
            textViewComposicao.setText(vacina.getComposicao());
            textViewIndicacao.setText(vacina.getIndicacao());
            textViewDoses.setText(vacina.getDoses());
            textViewAplicacao.setText(vacina.getAplicacao());
            textViewCuidados.setText(vacina.getCuidados());
            textViewEfeitos.setText(vacina.getEfeitos());


        }


    }
}
