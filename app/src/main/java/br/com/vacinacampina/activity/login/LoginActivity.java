package br.com.vacinacampina.activity.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.TelaPrincipalActivity;
import br.com.vacinacampina.activity.CadastroActivity;
import br.com.vacinacampina.config.FirebaseConfig;

public class LoginActivity extends AppCompatActivity {
    public static final String O_CAMPO_NOME_NÃO_PODE_SER_VAZIO = "O campo nome não pode ser vazio.";
    public static final String O_CAMPO_EMAIL_NÃO_PODE_SER_VAZIO = "O campo email não pode ser vazio.";
    public static final String O_CAMPO_SENHA_NÃO_PODE_SER_VAZIO = "O campo senha não pode ser vazio.";
    public static final String EMAIL_NÃO_CADASTRADO = "Email não cadastrado!";
    public static final String SENHA_INCORRETA = "Senha Incorreta!";
    public static final String ERRO_AO_REALIZAR_LOGIN_VERIFIQUE_OS_DADOS_E_TENTE_NOVAMENTE = "Erro ao realizar login, verifique os dados e tente novamente!";
    private EditText campoEmail,campoSenha;
    private Button buttonEntrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editText_email_login);
        campoSenha = findViewById(R.id.editText_senha_login);
        buttonEntrar = findViewById(R.id.button_entrar);
        progressBar = findViewById(R.id.progressBar_login);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos(campoEmail.getText().toString(), campoSenha.getText().toString())) {
                    logar();
                }
            }
        });
    }

    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Realiza o login na aplicação
     */
    private void logar() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseConfig.getAuth()
                .signInWithEmailAndPassword(campoEmail.getText().toString(), campoSenha.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, TelaPrincipalActivity.class));
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            try {
                                throw task.getException();

                            }catch (FirebaseAuthInvalidUserException authInvalidUserException){
                                Toast.makeText(LoginActivity.this,
                                        EMAIL_NÃO_CADASTRADO, Toast.LENGTH_LONG).show();
                            }catch (FirebaseAuthInvalidCredentialsException authInvalidCredentialsException){
                                Toast.makeText(LoginActivity.this,
                                        SENHA_INCORRETA, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this,
                                        ERRO_AO_REALIZAR_LOGIN_VERIFIQUE_OS_DADOS_E_TENTE_NOVAMENTE,
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    private Boolean validaCampos(String campoEmail, String campoSenha) {

        Boolean valido = true;
         if (campoEmail.isEmpty()){
            Toast.makeText(LoginActivity.this
                    , O_CAMPO_EMAIL_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
            valido = false;
        } else if (campoSenha.isEmpty()){
            Toast.makeText(LoginActivity.this
                    , O_CAMPO_SENHA_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
            valido = false;
        }
        return valido;
    }

    public void esqueceuSenha(View view){
        startActivity(new Intent(this, RecuperarSenhaActivity.class));
    }
}
