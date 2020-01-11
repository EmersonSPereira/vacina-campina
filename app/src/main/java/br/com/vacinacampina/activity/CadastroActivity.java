package br.com.vacinacampina.activity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.vacinacampina.R;
import br.com.vacinacampina.activity.login.LoginActivity;
import br.com.vacinacampina.config.FirebaseConfig;
import br.com.vacinacampina.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    public static final String O_CAMPO_NOME_NÃO_PODE_SER_VAZIO = "O campo nome não pode ser vazio.";
    public static final String O_CAMPO_EMAIL_NÃO_PODE_SER_VAZIO = "O campo email não pode ser vazio.";
    public static final String O_CAMPO_SENHA_NÃO_PODE_SER_VAZIO = "O campo senha não pode ser vazio.";
    public static final String UTILIZE_UMA_SENHA_MAIS_FORTE = "Utilize uma senha mais forte!";
    public static final String POR_FAVOR_DIGITE_UM_EMAIL_VALIDO = "Por favor digite um email valido!";
    public static final String JÁ_EXISTE_UM_USUÁRIO_COM_ESETE_EMAIL_CADASTRADO = "Já existe um usuário com esete email cadastrado!";
    public static final String ERRO_AO_CADASTRAR_VERIFIQUE_OS_DADOS_E_TENTE_NOVAMENTE = "Erro ao cadastrar, verifique os dados e tente novamente.";
    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editText_nome);
        campoEmail = findViewById(R.id.editText_email_login);
        campoSenha = findViewById(R.id.editText_senha_login);
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
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException authWeakPasswordException){
                                Toast.makeText(CadastroActivity.this
                                        , UTILIZE_UMA_SENHA_MAIS_FORTE, Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(CadastroActivity.this
                                        , POR_FAVOR_DIGITE_UM_EMAIL_VALIDO, Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthUserCollisionException userCollisionException){
                                Toast.makeText(CadastroActivity.this
                                        , JÁ_EXISTE_UM_USUÁRIO_COM_ESETE_EMAIL_CADASTRADO, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(CadastroActivity.this
                                        , ERRO_AO_CADASTRAR_VERIFIQUE_OS_DADOS_E_TENTE_NOVAMENTE, Toast.LENGTH_LONG).show();
                            }

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
