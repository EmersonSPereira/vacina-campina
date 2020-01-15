package br.com.vacinacampina.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

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

import br.com.vacinacampina.R;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.service.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditarContaActivity extends AppCompatActivity {

    public static final int SELECAO_GALERIA = 200;
    private CircleImageView circleImageViewFoto;
    private EditText editTextNome;
    private Button buttonAlterFoto, buttonSalvar;
    private ProgressBar progressBar;
    private Bitmap imagem ;
    private ProgressBar progressBar_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);

        circleImageViewFoto = findViewById(R.id.profile_image_editar);
        editTextNome = findViewById(R.id.editText_nome_editar);
        buttonAlterFoto = findViewById(R.id.button_alterar_foto);
        buttonSalvar = findViewById(R.id.button_salvar_edicao);
        progressBar = findViewById(R.id.progressBar_editar);
        progressBar_foto = findViewById(R.id.progressBar_carregar_foto_2);


        FirebaseConfig.getUsuarioLogado().getPhotoUrl();

        Uri fotoUrl = FirebaseConfig.getUsuarioLogado().getPhotoUrl();

        if(fotoUrl != null ) {
            progressBar_foto.setVisibility(View.VISIBLE);
            Glide.with(EditarContaActivity.this).load(fotoUrl).listener(new RequestListener<Drawable>() {
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

        editTextNome.setText(FirebaseConfig.getUsuarioLogado().getDisplayName());


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
        progressBar.setVisibility(View.VISIBLE);
        buttonSalvar.setEnabled(false);
        if (imagem != null) {
            //recupera dados da imagem para o firebase
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 70, arrayOutputStream);
            byte[] dadosImagem = arrayOutputStream.toByteArray();

            //salva imagem no firebase

            final StorageReference imagemRef = FirebaseConfig.getStorageReference().child("Imagens")
                    .child("Perfil")
                    .child(FirebaseConfig.getAuth().getCurrentUser().getUid() + ".jpeg");

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
                            // Continue with the task to get the download URL
                            return imagemRef.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                              progressBar.setVisibility(View.GONE);
                                UsuarioService.atualizarUsuario(editTextNome.getText().toString(),task.getResult(),EditarContaActivity.this);

                                finish();


                            }
                        }
                    });
                }
            });

        }else {

            progressBar.setVisibility(View.GONE);
            UsuarioService.atualizarUsuario(editTextNome.getText().toString(),EditarContaActivity.this);
            Toast.makeText(EditarContaActivity.this,
                    getResources().getString(R.string.sucesso_atualizar_dados),
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }



}
