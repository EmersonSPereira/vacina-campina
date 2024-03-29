package br.com.vacinacampina.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import br.com.vacinacampina.R;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Parente;
import br.com.vacinacampina.service.ParenteService;
import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroParenteActivity extends AppCompatActivity {
    public static final int SELECAO_GALERIA = 200;
    public static final String PARENTE = "Parente";
    public static final String IMAGENS = "Imagens";
    public static final String PERFIL = "Perfil";
    public static final String JPEG = ".jpeg";
    public static final String PARENTE_SALVO_COM_SUCESSO = "Parente Salvo com Sucesso";
    public static final String POR_FAVOR_ESCOLHA_UM_GRAU_DE_PARENTESCO = "Por favor escolha um grau de parentesco";
    public static final String GRAU_DE_PARENTESCO = "Grau de Parentesco";
    public static final String POR_FAVOR_DIGITE_UM_NOME_VÁLIDO = "Por favor digite um nome válido";
    private CircleImageView circleImageViewFoto;
    private EditText editTextNome;
    private Button buttonAlterFoto, buttonSalvar;
    private Bitmap imagem ;
    private ProgressBar progressBarFoto;
    private ProgressBar progressBarCadastroParente;
    private Spinner spinnerGrauParentesco;
    Parente parente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_parente);

        circleImageViewFoto = findViewById(R.id.parente_image_editar);
        editTextNome = findViewById(R.id.editText_nome_parente_editar);
        buttonAlterFoto = findViewById(R.id.button_alterar_foto_parente);
        buttonSalvar = findViewById(R.id.button_salvar_edicao_parente);
        progressBarFoto = findViewById(R.id.progressBar_carregar_foto_parente);
        progressBarCadastroParente = findViewById(R.id.progressBar_cadastro_parente);

        spinnerGrauParentesco = findViewById(R.id.spinner_parente);

        parente = new Parente();

        Bundle bundle = getIntent().getExtras();
        if( bundle != null) {
            parente = (Parente) bundle.getSerializable(PARENTE);
            if (parente.getUrlFoto() != null && !parente.getUrlFoto().isEmpty()) {
                progressBarFoto.setVisibility(View.VISIBLE);
                Glide.with(CadastroParenteActivity.this).load(parente.getUrlFoto()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarFoto.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarFoto.setVisibility(View.GONE);
                        return false;
                    }
                }).into(circleImageViewFoto);
            }

            editTextNome.setText(parente.getNome());
            spinnerGrauParentesco.setSelection(new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.lista_parentes))).indexOf(parente.getParentesco()));
        }



        buttonAlterFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(galeria.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(galeria, SELECAO_GALERIA);
                }
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoes();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // verificando se imagem foi carregada
        if(resultCode == RESULT_OK){


            try {

                switch (requestCode){
                    case SELECAO_GALERIA:
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                }

                if(imagem != null){
                    //altera imagem no imageView
                    circleImageViewFoto.setImageBitmap(imagem);

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void salvarAlteracoes() {
        buttonSalvar.setEnabled(false);
        progressBarCadastroParente.setVisibility(View.VISIBLE);
        if (imagem != null && validarCampos()) {
            //recupera dados da imagem para o firebase
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 70, arrayOutputStream);
            byte[] dadosImagem = arrayOutputStream.toByteArray();

            //salva imagem no firebase

            final StorageReference imagemRef = FirebaseConfig.getStorageReference().child(IMAGENS)
                    .child(PERFIL)
                    .child(PARENTE)
                    .child(FirebaseConfig.getAuth().getCurrentUser().getUid())
                    .child(UUID.randomUUID().toString() + JPEG);

            final UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();

                            }
                            return imagemRef.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                parente.setNome(editTextNome.getText().toString());
                                parente.setUrlFoto(task.getResult().toString());
                                parente.setParentesco(spinnerGrauParentesco.getSelectedItem().toString());
                                ParenteService.salvarAtualizarParente(parente);
                                progressBarCadastroParente.setVisibility(View.GONE);
                                Toast.makeText(CadastroParenteActivity.this, PARENTE_SALVO_COM_SUCESSO, Toast.LENGTH_LONG).show();
                                CadastroParenteActivity.super.onBackPressed();

                            }
                        }
                    });
                }
            });

        }else {

            if (validarCampos()) {
                parente.setNome(editTextNome.getText().toString());
                parente.setParentesco(spinnerGrauParentesco.getSelectedItem().toString());

                ParenteService.salvarAtualizarParente(parente);
                progressBarCadastroParente.setVisibility(View.GONE);
                Toast.makeText(CadastroParenteActivity.this, PARENTE_SALVO_COM_SUCESSO, Toast.LENGTH_LONG).show();
                CadastroParenteActivity.super.onBackPressed();
            } else if(spinnerGrauParentesco.getSelectedItem().toString().equals(GRAU_DE_PARENTESCO)) {
                buttonSalvar.setEnabled(true);
                progressBarCadastroParente.setVisibility(View.GONE);
                Toast.makeText(CadastroParenteActivity.this, POR_FAVOR_ESCOLHA_UM_GRAU_DE_PARENTESCO, Toast.LENGTH_LONG).show();
            }else {
                buttonSalvar.setEnabled(true);
                progressBarCadastroParente.setVisibility(View.GONE);
                Toast.makeText(CadastroParenteActivity.this, POR_FAVOR_DIGITE_UM_NOME_VÁLIDO, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validarCampos() {
        return !spinnerGrauParentesco.getSelectedItem().toString().equals(GRAU_DE_PARENTESCO) && !editTextNome.getText().toString().isEmpty();
    }


}

