package br.com.vacinacampina.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.vacinacampina.R;
import br.com.vacinacampina.adapter.AdapterCartao;
import br.com.vacinacampina.config.RecyclerItemClickListener;
import br.com.vacinacampina.model.Cartao;
import br.com.vacinacampina.model.Parente;
import br.com.vacinacampina.service.CartaoService;

import static br.com.vacinacampina.adapter.AdapterCartao.DOSE_NÃO_TOMADA;

public class VisualizarCartaoActivity extends AppCompatActivity {

    public static final String PARENTE = "Parente";
    public static final String VACINA = "vacina";
    public static final String ID_PARENTE = "idParente";
    public static final String FILE_ANDROID_ASSET = "file:///android_asset/";
    public static final String TEXT_HTML = "text/HTML";
    public static final String UTF_8 = "UTF-8";
    private AdapterCartao adapterCartao;
    private RecyclerView recyclerViewCartaoVacinas;
    private ProgressBar progressBarListarCartao;
    private List<Cartao> cartoes = new ArrayList<>();
    private Parente parente;
    private ImageView imageViewFotoCartao;
    private TextView textViewNomeCartao;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButtonPdf;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_cartao);
        toolbar = findViewById(R.id.toolbar);
        recyclerViewCartaoVacinas = findViewById(R.id.recyclerView_cartao_vacina);
        progressBarListarCartao = findViewById(R.id.progressBar_listar_cartao);
        imageViewFotoCartao = toolbar.findViewById(R.id.imageView_foto_cartao_vacina);
        textViewNomeCartao = toolbar.findViewById(R.id.textview_nome5);
        floatingActionButtonPdf = findViewById(R.id.floatingActionButton_gerar_pdf);


        floatingActionButtonPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    doWebViewPrint();

            }
        });

        configurarRecycleView();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parente = (Parente) bundle.getSerializable(PARENTE);
            CartaoService.listarcartoes(cartoes, adapterCartao, progressBarListarCartao, parente.getId());
            textViewNomeCartao.setText(parente.getNome());
            if (parente.getUrlFoto() != null && !parente.getUrlFoto().isEmpty())
                Glide.with(this).load(parente.getUrlFoto()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoCartao);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        CartaoService.listarcartoes(cartoes, adapterCartao, progressBarListarCartao, parente.getId());
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
                startActivity(new Intent(VisualizarCartaoActivity.this, EditarVacinaActivity.class).putExtra(VACINA, cartoes.get(position)).putExtra(ID_PARENTE, parente.getId()));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

    }

    private void doWebViewPrint() {
        progressBarListarCartao.setVisibility(View.VISIBLE);
        // Create a WebView object specifically for printing
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                progressBarListarCartao.setVisibility(View.GONE);
                mWebView = null;
            }
        });
        webView.loadDataWithBaseURL(FILE_ANDROID_ASSET, montarHtmlPdf().toString(), TEXT_HTML, UTF_8, null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private StringBuilder montarHtmlPdf() {
        StringBuilder htmlDocument = new StringBuilder()
        .append("<html>")
                .append("<head>")
                    .append("<link rel=\"stylesheet\" href=\"bootstrap.min.css\" >")
                .append("</head>")
                .append("<body>")
                    .append("<div class=\"container\">")
                        .append("<div class=\" mt-5 card\">")
                            .append(" <h1 class=\"card-header text-center \">Cartão de Vacina</h1>")
                                .append("<div class=\"container row row-cols-1 text-center mt-5\">")
                                    .append(" <div class=\"col container\">")
                                        .append("<img src=\"" + parente.getUrlFoto() + "\"alt=\" foto\" style=\"width:50px;height:60px;\">\n")
                                    .append("</div>")
                                    .append("<div class=\"col container align-items-center mt-3\">")
                                        .append(" <h4 class=\" ml-3\">" + parente.getNome() + "</h4>")
                                    .append(" </div>")
                                .append(" </div>")
                        .append(" </div>")
                    .append(" </div>")

                    .append("<div class=\"container\">")
                        .append("<div class=\"card mt-5\">")
                            .append("<table class=\"table\">")
                                .append("<thead>")
                                    .append("<tr>")
                                        .append("<th scope=\"col\">Vacina</th>")
                                        .append("<th scope=\"col\">Primeira Dose</th>")
                                        .append("<th scope=\"col\">Segunda Dose</th>")
                                        .append("<th scope=\"col\">Terceira Dose</th>")
                                    .append(" </tr>")
                                .append("</thead>")

                                .append("<tbody>");
                        for (Cartao cartao: montarCartaoParaImpressao()) {
                            htmlDocument.append("<tr>")
                                    .append(" <th scope=\"row\">"+ cartao.getNomeVacina() +"</th>");
                            if(cartao.getDoses().equals(3)) {
                                htmlDocument.append(" <td>" ).append( cartao.getDataPrimeiraDose() != null ? cartao.getDataPrimeiraDose() : DOSE_NÃO_TOMADA ).append( "</td>")
                                        .append(" <td>" ).append( cartao.getDataSegundaDose() != null ? cartao.getDataSegundaDose() : DOSE_NÃO_TOMADA ).append( "</td>")
                                        .append(" <td>" ).append( cartao.getDataTerceiraDose() != null ? cartao.getDataTerceiraDose() : DOSE_NÃO_TOMADA ).append( "</td>");
                            } else if(cartao.getDoses().equals(2)){
                                htmlDocument.append(" <td>" ).append( cartao.getDataPrimeiraDose() != null ? cartao.getDataPrimeiraDose() : DOSE_NÃO_TOMADA ).append( "</td>")
                                        .append(" <td>" ).append( cartao.getDataSegundaDose() != null ? cartao.getDataSegundaDose() : DOSE_NÃO_TOMADA ).append( "</td>")
                                        .append(" <td> ------- </td>");
                            } else {
                                htmlDocument.append(" <td>" ).append( cartao.getDataPrimeiraDose() != null ? cartao.getDataPrimeiraDose() : DOSE_NÃO_TOMADA ).append( "</td>")
                                        .append(" <td> ------- </td>")
                                        .append(" <td> ------- </td>");
                            }
                            htmlDocument.append("</tr>");
                        }
                htmlDocument.append("</tbody>")
            .append(" </table>")
        .append("</div>")
    .append("</div>")
.append("</body>")

.append("<footer class=\"page-footer\">")
    .append("<div class=\"container\">")
        .append("<div class=\"card\">")
            .append(" <div class=\"card-body text-center\">")
                .append(" Cartão gerado pelo App VacinaCampina")
            .append("</div>")
        .append("</div>")
    .append("</div>")
.append("</footer>")

.append("<style>")
    .append("img {border-radius: 50%;}")
    .append("footer {position:absolute;bottom:0;width:100%;}")
.append("</style>")

.append("</html>");
        return htmlDocument;
    }

    private List<Cartao> montarCartaoParaImpressao() {
        List<Cartao> cartoesImpressao = new ArrayList<>();
        for (Cartao cartao: cartoes ) {
            if(!cartoesImpressao.contains(cartao))
                cartoesImpressao.add(cartao);

        }
        return cartoesImpressao;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

    }

}
