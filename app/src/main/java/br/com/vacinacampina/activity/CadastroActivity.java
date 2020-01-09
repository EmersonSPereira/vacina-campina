package br.com.vacinacampina.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.vacinacampina.R;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    public static final String O_CAMPO_NOME_NÃO_PODE_SER_VAZIO = "O campo nome não pode ser vazio.";
    public static final String O_CAMPO_EMAIL_NÃO_PODE_SER_VAZIO = "O campo email não pode ser vazio.";
    public static final String O_CAMPO_SENHA_NÃO_PODE_SER_VAZIO = "O campo senha não pode ser vazio.";
    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editText_nome);
        campoEmail = findViewById(R.id.editText_email);
        campoSenha = findViewById(R.id.editText_senha);
        btnCadastrar = findViewById(R.id.button_cadastrar);
        progressBar = findViewById(R.id.progressBar_cadastro);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos(campoNome.getText().toString(),campoEmail.getText().toString(),campoSenha.getText().toString())) {
                    cadastrarUsuario();
                }
            }
        });

    }

    /**
     * Cadastra um usuário no sistema
     */
    private void cadastrarUsuario() {
        progressBar.setVisibility(View.VISIBLE);
        Usuario usuario = new Usuario(campoNome.getText().toString(),
                campoEmail.getText().toString(),
                campoSenha.getText().toString());
        FirebaseConfig.getAuth().createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            setContentView(R.layout.activity_sucesso_cadastro);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this
                                    , task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    /**
     *  valida os campos do formulario de cadastro
     * @param campoNome - campo a ser validado
     * @param campoEmail - campo a ser validado
     * @param campoSenha - campo a ser validado
     * @return {@link Boolean} - resultado da validação
     */
    private Boolean validaCampos(String campoNome, String campoEmail, String campoSenha) {

        Boolean valido = true;
        if(campoNome.isEmpty()){
            Toast.makeText(CadastroActivity.this
                    , O_CAMPO_NOME_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
            valido = false;
        }else if (campoEmail.isEmpty()){
            Toast.makeText(CadastroActivity.this
                    , O_CAMPO_EMAIL_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
            valido = false;
        } else if (campoSenha.isEmpty()){
            Toast.makeText(CadastroActivity.this
                    , O_CAMPO_SENHA_NÃO_PODE_SER_VAZIO, Toast.LENGTH_LONG).show();
            valido = false;
        }
        return valido;
    }

    public void btnEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
