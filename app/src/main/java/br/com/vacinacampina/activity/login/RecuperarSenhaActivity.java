package br.com.vacinacampina.activity.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.vacinacampina.R;
import br.com.vacinacampina.config.FirebaseConfig;

public class RecuperarSenhaActivity extends AppCompatActivity {
    public static final String COR_BTN_DESABILITADO = "#FF92B6DB";
    public static final String O_E_MAIL_NÃO_PODE_SER_VAZIO = "O E-mail não pode ser vazio";
    public static final String FALHA_AO_ENVIAR_E_MAIL_VERIFIQUE_SUA_CONEXÃO_E_TENTE_NOVAMENTE = "Falha ao enviar e-mail, verifique sua conexão e tente novamente.";
    public static final String FALHA_AO_ENVIAR_E_MAIL_USUÁRIO_NÃO_CADASTRADO = "Falha ao enviar e-mail, usuário não cadastrado.";
    public static final String E_MAIL_ENVIADO_COM_SUCESSO = "E-mail enviado com sucesso!";
    private EditText campoEmail;
    private TextView textoRetornar;
    private ProgressBar progressBar;
    private Button buttonRecuperarSenha;
    private FloatingActionButton buttonRetornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        progressBar = findViewById(R.id.progressBar_recuperar_senha);
        campoEmail = findViewById(R.id.editText_email_recuperar_senha);
        buttonRecuperarSenha = findViewById(R.id.button_recuperar_senha);
        buttonRetornar = findViewById(R.id.floatingActionButton_retornar_login);
        this.textoRetornar = findViewById(R.id.textView_voltar_login);

        buttonRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecuperarSenhaActivity.this, LoginActivity.class));
            }
        });
        buttonRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarEmailRecuperacao();
            }
        });
    }

    private void enviarEmailRecuperacao() {
        if (!validaCampoVazio(campoEmail.getText().toString())) {
            progressBar.setVisibility(View.VISIBLE);
            buttonRecuperarSenha.setEnabled(false);
            buttonRecuperarSenha.setBackgroundColor(Color.parseColor(COR_BTN_DESABILITADO));
            FirebaseConfig.getAuth().sendPasswordResetEmail(campoEmail.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RecuperarSenhaActivity.this, E_MAIL_ENVIADO_COM_SUCESSO,
                                Toast.LENGTH_LONG).show();

                        textoRetornar.setVisibility(View.VISIBLE);
                        buttonRetornar.show();

                    } else{
                        buttonRecuperarSenha.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonRecuperarSenha.setEnabled(true);
                        buttonRecuperarSenha.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                        try {
                            throw  task.getException();
                        }catch (FirebaseAuthInvalidUserException firebaseAuthInvalidUserException){
                            Toast.makeText(RecuperarSenhaActivity.this,
                                    FALHA_AO_ENVIAR_E_MAIL_USUÁRIO_NÃO_CADASTRADO, Toast.LENGTH_LONG).show();
                        }catch (Exception e) {
                            Toast.makeText(RecuperarSenhaActivity.this,
                                    FALHA_AO_ENVIAR_E_MAIL_VERIFIQUE_SUA_CONEXÃO_E_TENTE_NOVAMENTE, Toast.LENGTH_LONG).show();
                        }





                    }

                }
            });
        }else {
            Toast.makeText(RecuperarSenhaActivity.this, O_E_MAIL_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validaCampoVazio(String campo) {
        return campo.isEmpty();
    }
}
